package instituto.vidaplus.receita.service.impl;

import instituto.vidaplus.consulta.exception.ConsultaNaoEncontradaException;
import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.consulta.repository.ConsultaRepository;
import instituto.vidaplus.exception.genericas.ErroAoGerarPdfException;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEncontradoException;
import instituto.vidaplus.profissional.repository.ProfissionalRepository;
import instituto.vidaplus.receita.dto.ItemReceitaInfo;
import instituto.vidaplus.receita.dto.ReceitaDTO;
import instituto.vidaplus.receita.exception.ReceitaNaoEncontradaException;
import instituto.vidaplus.receita.model.Receita;
import instituto.vidaplus.receita.repository.ReceitaRepository;
import instituto.vidaplus.receita.service.ItemReceitaService;
import instituto.vidaplus.receita.service.ReceitaService;
import instituto.vidaplus.utils.validador.FormatadorData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceitaServiceImpl implements ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final FormatadorData formatadorData;
    private final ItemReceitaService itemReceitaService;

    @Override
    public ReceitaDTO criarReceita(Long consultaId) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));

        Receita receita = new Receita();
        receita.setConsulta(consulta);
        receita.setPaciente(consulta.getPaciente());
        receita.setProfissional(consulta.getProfissional());
        receita.setDataEmissao(LocalDate.now());
        receita.setDataFimValidade(LocalDate.now().plusDays(30));

        Receita receitaSalva = receitaRepository.save(receita);
        return new ReceitaDTO(receitaSalva);
    }

    @Override
    public ReceitaDTO buscarReceitaPorId(Long receitaId) {
        Receita receita = receitaRepository.findById(receitaId)
                .orElseThrow(() -> new ReceitaNaoEncontradaException("Receita não encontrada"));

        return new ReceitaDTO(receita);
    }

    @Override
    public Page<ReceitaDTO> buscarReceitasPorPacienteId(Long pacienteId, Pageable pageable) {
        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
        Page<Receita> receitas = receitaRepository.findReceitasByPacienteId(pacienteId, pageable);
        return receitas.map(ReceitaDTO::new);
    }

    @Override
    public Page<ReceitaDTO> buscarReceitasPorProfissionalId(Long profissionalId, Pageable pageable) {
        profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));
        Page<Receita> receitas = receitaRepository.findReceitasByProfissionalId(profissionalId, pageable);
        return receitas.map(ReceitaDTO::new);
    }

    @Override
    public Page<ReceitaDTO> buscarReceitaPorConsultaId(Long consultaId, Pageable pageable) {
        consultaRepository.findById(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada"));

        Page<Receita> receitas = receitaRepository.findReceitaByConsultaId(consultaId, pageable);
        return receitas.map(ReceitaDTO::new);
    }

    @Override
    public Page<ReceitaDTO> buscarReceitasPorPeriodo(Long pacienteId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        Page<Receita> receitas = receitaRepository.findByPacienteIdAndDataEmissaoBetween(pacienteId, dataInicio, dataFim, pageable);
        return receitas.map(ReceitaDTO::new);
    }

    @Override
    public byte[] gerarRelatorio(Long receitaId) {
        Receita receita = receitaRepository.findById(receitaId)
                .orElseThrow(() -> new ReceitaNaoEncontradaException("Receita não encontrada"));

        List<ItemReceitaInfo> itensReceita = itemReceitaService.listarItemPorReceita(receitaId);

        StringBuilder itens = new StringBuilder();
        for(ItemReceitaInfo item : itensReceita) {
            itens.append(item.getNome()).append(" - ")
                    .append(item.getQuantidade())
                    .append("\n");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            String sb = "RECEITA MÉDICA\n\n" +
                    "Paciente: " + receita.getPaciente().getNome() + "\n" +
                    "Profissional: " + receita.getProfissional().getNome() + "\n" +
                    "Data: " + formatadorData.formatarData(receita.getDataEmissao()) + "\n\n" +
                    "Medicamentos:\n" + itens + "\n\n" +
                    "Data validade receita:\n" + formatadorData.formatarData(receita.getDataFimValidade());

            return sb.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new ErroAoGerarPdfException("Erro ao gerar PDF");
        }
    }
}
