package instituto.vidaplus.prontuario.service.impl;

import instituto.vidaplus.consulta.exception.ConsultaNaoEncontradaException;
import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.consulta.repository.ConsultaRepository;
import instituto.vidaplus.exception.genericas.ErroAoGerarPdfException;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEncontradoException;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.profissional.repository.ProfissionalRepository;
import instituto.vidaplus.prontuario.dto.ProntuarioDTO;
import instituto.vidaplus.prontuario.exception.ProntuarioNaoEncontradoException;
import instituto.vidaplus.prontuario.model.Prontuario;
import instituto.vidaplus.prontuario.repository.ProntuarioRepository;
import instituto.vidaplus.prontuario.service.ProntuarioService;
import instituto.vidaplus.utils.validador.FormatadorData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProntuarioServiceImpl implements ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final FormatadorData formatadorData;

    @Override
    public ProntuarioDTO criarProntuario(Long consultaId, ProntuarioDTO prontuarioDTO) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));

        Paciente paciente = consulta.getPaciente();
        Profissional profissional = consulta.getProfissional();

        Prontuario prontuario = new Prontuario();
        prontuario.setConsulta(consulta);
        prontuario.setPaciente(paciente);
        prontuario.setProfissional(profissional);
        prontuario.setDataRegistro(LocalDate.now());
        prontuario.setDescricao(prontuarioDTO.getDescricao());
        prontuario.setDiagnostico(prontuarioDTO.getDiagnostico());
        prontuario.setTratamentoIndicado(prontuarioDTO.getTratamentoIndicado());

        Prontuario prontuarioSalvo = prontuarioRepository.save(prontuario);
        return new ProntuarioDTO(prontuarioSalvo);
    }

    @Override
    public ProntuarioDTO buscarProntuarioPorId(Long prontuarioId) {
        Prontuario prontuario = prontuarioRepository.findById(prontuarioId)
                .orElseThrow(() -> new ProntuarioNaoEncontradoException("Prontuário não encontrado"));
        return new ProntuarioDTO(prontuario);
    }

    @Override
    public ProntuarioDTO atualizarProntuario(Long prontuarioId, ProntuarioDTO prontuarioDTO) {
        Prontuario prontuario = prontuarioRepository.findById(prontuarioId)
                .orElseThrow(() -> new ProntuarioNaoEncontradoException("Prontuário não encontrado"));

        prontuario.setDescricao(prontuarioDTO.getDescricao());
        prontuario.setDiagnostico(prontuarioDTO.getDiagnostico());
        prontuario.setTratamentoIndicado(prontuarioDTO.getTratamentoIndicado());

        Prontuario prontuarioSalvo = prontuarioRepository.save(prontuario);
        return new ProntuarioDTO(prontuarioSalvo);
    }

    @Override
    public Page<ProntuarioDTO> buscarProntuariosPorPacienteId(Long pacienteId, Pageable pageable) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
        Page<Prontuario> prontuarios = prontuarioRepository.findByPacienteId(pacienteId, pageable);
        return prontuarios.map(ProntuarioDTO::new);
    }

    @Override
    public Page<ProntuarioDTO> buscarProntuariosPorProfissionalId(Long profissionalId, Pageable pageable) {
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));
        Page<Prontuario> prontuarios = prontuarioRepository.findByProfissionalId(profissionalId, pageable);
        return prontuarios.map(ProntuarioDTO::new);
    }

    @Override
    public ProntuarioDTO buscarProntuarioPorConsultaId(Long consultaId) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));
        Prontuario prontuario = prontuarioRepository.findByConsultaId(consultaId);
        return new ProntuarioDTO(prontuario);
    }

    @Override
    public Page<ProntuarioDTO> buscarProntuariosPorPeriodo(Long pacienteId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
        Page<Prontuario> prontuarios = prontuarioRepository.findByPacienteIdAndDataRegistroBetween(pacienteId, dataInicio, dataFim, pageable);
        return prontuarios.map(ProntuarioDTO::new);
    }

    @Override
    public byte[] gerarRelatorio(Long prontuarioId) {
        Prontuario prontuario = prontuarioRepository.findById(prontuarioId)
                .orElseThrow(() -> new ProntuarioNaoEncontradoException("Prontuário não encontrado"));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            String sb = "PRONTUÁRIO MÉDICO\n\n" +
                    "Paciente: " + prontuario.getPaciente().getNome() + "\n" +
                    "Profissional: " + prontuario.getProfissional().getNome() + "\n" +
                    "Data: " + formatadorData.formatarData(prontuario.getDataRegistro()) + "\n\n" +
                    "Descrição:\n" + prontuario.getDescricao() + "\n\n" +
                    "Diagnóstico:\n" + prontuario.getDiagnostico() + "\n\n" +
                    "Tratamento:\n" + prontuario.getTratamentoIndicado();

            return sb.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new ErroAoGerarPdfException("Erro ao gerar PDF");
        }

    }
}
