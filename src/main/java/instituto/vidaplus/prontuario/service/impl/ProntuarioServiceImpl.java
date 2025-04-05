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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProntuarioServiceImpl implements ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final FormatadorData formatadorData;
    private static final Logger logger = LoggerFactory.getLogger(ProntuarioServiceImpl.class);

    @Override
    public ProntuarioDTO criarProntuario(Long consultaId, ProntuarioDTO prontuarioDTO) {
        logger.info("Criando prontuário para a consulta com ID: {}", consultaId);
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
        logger.debug("Prontuario: {}", prontuarioSalvo);
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
        logger.info("Atualizando prontuário com ID: {}", prontuarioId);
        Prontuario prontuario = prontuarioRepository.findById(prontuarioId)
                .orElseThrow(() -> new ProntuarioNaoEncontradoException("Prontuário não encontrado"));

        prontuario.setDescricao(prontuarioDTO.getDescricao());
        prontuario.setDiagnostico(prontuarioDTO.getDiagnostico());
        prontuario.setTratamentoIndicado(prontuarioDTO.getTratamentoIndicado());

        Prontuario prontuarioSalvo = prontuarioRepository.save(prontuario);
        logger.debug("Prontuario: {}", prontuarioSalvo);
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
        logger.info("Gerando relatório para o prontuário com ID: {}", prontuarioId);
        Prontuario prontuario = prontuarioRepository.findById(prontuarioId)
                .orElseThrow(() -> new ProntuarioNaoEncontradoException("Prontuário não encontrado"));

        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("pacienteNome", prontuario.getPaciente().getNome());
            parameters.put("profissionalNome", prontuario.getProfissional().getNome());
            parameters.put("dataRegistro", formatadorData.formatarData(prontuario.getDataRegistro()));
            parameters.put("descricao", prontuario.getDescricao());
            parameters.put("diagnostico", prontuario.getDiagnostico());
            parameters.put("tratamentoIndicado", prontuario.getTratamentoIndicado());

            InputStream reportStream = getClass().getResourceAsStream("/reports/prontuario.jrxml");
            if (reportStream == null) {
                throw new ErroAoGerarPdfException("Template de relatório não encontrado: /reports/prontuario.jrxml");
            }

            JasperReport jasperReport;
            try {
                InputStream compiledStream = getClass().getResourceAsStream("/reports/prontuario.jasper");
                if (compiledStream != null) {
                    jasperReport = (JasperReport) JRLoader.loadObject(compiledStream);
                } else {
                    jasperReport = JasperCompileManager.compileReport(reportStream);
                }
            } catch (JRException e) {
                throw new ErroAoGerarPdfException("Erro ao compilar o relatório: " + e.getMessage());
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);

            return out.toByteArray();
        } catch (Exception e) {
            throw new ErroAoGerarPdfException("Erro ao gerar relatório PDF: " + e.getMessage());
        }
    }
}
