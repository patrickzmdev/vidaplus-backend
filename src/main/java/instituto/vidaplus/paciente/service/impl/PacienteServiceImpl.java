package instituto.vidaplus.paciente.service.impl;

import instituto.vidaplus.consulta.model.Consulta;
import instituto.vidaplus.consulta.repository.ConsultaRepository;
import instituto.vidaplus.endereco.dto.EnderecoDTO;
import instituto.vidaplus.endereco.service.CepService;
import instituto.vidaplus.exame.model.Exame;
import instituto.vidaplus.exame.repository.ExameRepository;
import instituto.vidaplus.paciente.exception.PacienteNaoEncontradoException;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.paciente.repository.PacienteRepository;
import instituto.vidaplus.paciente.service.PacienteService;
import instituto.vidaplus.prontuario.model.Prontuario;
import instituto.vidaplus.prontuario.repository.ProntuarioRepository;
import instituto.vidaplus.utils.validador.ValidadorCep;
import instituto.vidaplus.utils.validador.ValidadorCpf;
import instituto.vidaplus.utils.validador.ValidadorEmail;
import instituto.vidaplus.utils.validador.ValidadorTelefone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final ExameRepository exameRepository;
    private final ProntuarioRepository prontuarioRepository;
    private final ValidadorCep validadorCep;
    private final ValidadorCpf validadorCpf;
    private final ValidadorEmail validadorEmail;
    private final ValidadorTelefone validadorTelefone;
    private final CepService cepService;

    @Override
    @Transactional
    public Paciente cadastrarPaciente(Paciente paciente) {
        if (paciente.getNome() == null || paciente.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do paciente é obrigatório");
        }
        if (paciente.getCpf() == null || paciente.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF do paciente é obrigatório");
        }

        validadorCpf.validarCpf(paciente.getCpf());
        validadorEmail.validarEmail(paciente.getEmail());
        validadorTelefone.validarTelefone(paciente.getTelefone());
        validadorCep.validarCep(paciente.getCep());

        EnderecoDTO endereco = cepService.buscarEnderecoPorCep(paciente.getCep());
        paciente.setLogradouro(endereco.getLogradouro());
        paciente.setBairro(endereco.getBairro());
        paciente.setCidade(endereco.getLocalidade());
        paciente.setUf(endereco.getUf());

        return pacienteRepository.save(paciente);
    }

    @Override
    public String editarPaciente(Long pacienteId, Paciente paciente) {
        Paciente pacienteExistente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

        validadorCpf.validarCpf(paciente.getCpf());
        validadorEmail.validarEmail(paciente.getEmail());
        validadorTelefone.validarTelefone(paciente.getTelefone());
        validadorCep.validarCep(paciente.getCep());

        pacienteExistente.setNome(paciente.getNome());
        pacienteExistente.setCpf(paciente.getCpf());
        pacienteExistente.setDataNascimento(paciente.getDataNascimento());
        pacienteExistente.setEmail(paciente.getEmail());
        pacienteExistente.setTelefone(paciente.getTelefone());
        pacienteExistente.setPossuiPlanoDeSaude(paciente.getPossuiPlanoDeSaude());
        pacienteExistente.setPlanoDeSaudeNome(paciente.getPlanoDeSaudeNome());
        pacienteExistente.setReceberNotificacaoEmail(paciente.getReceberNotificacaoEmail());
        pacienteExistente.setAceitouTermosTelemedicina(paciente.getAceitouTermosTelemedicina());
        pacienteExistente.setTipoSanguineo(paciente.getTipoSanguineo());

        pacienteRepository.save(pacienteExistente);
        return "Paciente atualizado com sucesso";
    }

    @Override
    public Paciente buscarPaciente(Long pacienteId) {
        return pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
    }

    @Override
    public void excluirPaciente(Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com o ID: " + pacienteId));

        pacienteRepository.delete(paciente);
    }
}
