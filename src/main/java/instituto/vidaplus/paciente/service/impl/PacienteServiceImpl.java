package instituto.vidaplus.paciente.service.impl;

import instituto.vidaplus.consulta.repository.ConsultaRepository;
import instituto.vidaplus.documentacao.FinalidadeTratamento;
import instituto.vidaplus.endereco.dto.EnderecoDTO;
import instituto.vidaplus.endereco.service.CepService;
import instituto.vidaplus.exame.repository.ExameRepository;
import instituto.vidaplus.exception.genericas.DadoUnicoException;
import instituto.vidaplus.exception.genericas.DataInvalidaException;
import instituto.vidaplus.paciente.dto.PacienteDTO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ValidadorCep validadorCep;
    private final ValidadorCpf validadorCpf;
    private final ValidadorEmail validadorEmail;
    private final ValidadorTelefone validadorTelefone;
    private final CepService cepService;
    private static final Logger log = LoggerFactory.getLogger(PacienteServiceImpl.class);

    @Override
    @Transactional
    @FinalidadeTratamento(descricao = "Cadastro de unidades hospitalares para atendimento",
            baseLegal = "Execução de contrato/Interesse legítimo")
    public PacienteDTO cadastrarPaciente(PacienteDTO pacienteDTO) {
        try {
            log.info("Cadastrando Paciente: {}", pacienteDTO);
            if (pacienteDTO.getNome() == null || pacienteDTO.getNome().isEmpty()) {
                throw new IllegalArgumentException("Nome do paciente é obrigatório");
            }
            if (pacienteDTO.getCpf() == null || pacienteDTO.getCpf().isEmpty()) {
                throw new IllegalArgumentException("CPF do paciente é obrigatório");
            }

            if(pacienteDTO.getDataNascimento().isAfter(LocalDate.now())){
                throw new DataInvalidaException("Data de nascimento não pode ser futura");
            }

            validadorCpf.validarCpf(pacienteDTO.getCpf());
            validadorEmail.validarEmail(pacienteDTO.getEmail());
            validadorTelefone.validarTelefone(pacienteDTO.getTelefone());
            validadorCep.validarCep(pacienteDTO.getCep());

            EnderecoDTO endereco = cepService.buscarEnderecoPorCep(pacienteDTO.getCep());

            Paciente paciente = new Paciente();
            paciente.setNome(pacienteDTO.getNome());
            paciente.setSexo(pacienteDTO.getSexo());
            paciente.setCpf(pacienteDTO.getCpf());
            paciente.setDataNascimento(pacienteDTO.getDataNascimento());
            paciente.setEmail(pacienteDTO.getEmail());
            paciente.setTelefone(pacienteDTO.getTelefone());
            paciente.setPossuiPlanoDeSaude(pacienteDTO.getPossuiPlanoDeSaude());
            paciente.setPlanoDeSaudeNome(pacienteDTO.getPlanoDeSaudeNome());
            paciente.setReceberNotificacaoEmail(pacienteDTO.getReceberNotificacaoEmail());
            paciente.setAceitouTermosTelemedicina(pacienteDTO.getAceitouTermosTelemedicina());
            paciente.setTipoSanguineo(pacienteDTO.getTipoSanguineo());
            paciente.setCep(pacienteDTO.getCep());
            paciente.setLogradouro(endereco.getLogradouro());
            paciente.setBairro(endereco.getBairro());
            paciente.setCidade(endereco.getLocalidade());
            paciente.setUf(endereco.getUf());

            Paciente pacienteSalvo = pacienteRepository.save(paciente);
            log.debug("Paciente: {}", pacienteSalvo);
            return new PacienteDTO(pacienteSalvo);
        }catch (DataIntegrityViolationException ex) {
            String errorMessage = ex.getMostSpecificCause().getMessage();
            if (errorMessage.contains("email")) {
                throw new DadoUnicoException("Email já cadastrado");
            } else if (errorMessage.contains("cpf")) {
                throw new DadoUnicoException("CPF já cadastrado");
            } else if (errorMessage.contains("telefone")) {
                throw new DadoUnicoException("Telefone já cadastrado");
            } else {
                throw new RuntimeException("Erro ao cadastrar paciente: " + errorMessage);
            }
        }
    }

    @Override
    public PacienteDTO editarPaciente(Long pacienteId, PacienteDTO pacienteAtualizado) {
        try {
            log.info("Editando Paciente: {}", pacienteId);
            Paciente pacienteExistente = pacienteRepository.findById(pacienteId)
                    .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

            if (pacienteAtualizado.getCpf() != null && !pacienteAtualizado.getCpf().equals(pacienteExistente.getCpf())) {
                if (pacienteRepository.existsByCpf(pacienteAtualizado.getCpf())) {
                    throw new DadoUnicoException("CPF já cadastrado");
                }
                pacienteExistente.setCpf(pacienteAtualizado.getCpf());
            }

            if (pacienteAtualizado.getEmail() != null && !pacienteAtualizado.getEmail().equals(pacienteExistente.getEmail())) {
                if (pacienteRepository.existsByEmail(pacienteAtualizado.getEmail())) {
                    throw new DadoUnicoException("Email já cadastrado");
                }
                pacienteExistente.setEmail(pacienteAtualizado.getEmail());
            }

            if (pacienteAtualizado.getTelefone() != null && !pacienteAtualizado.getTelefone().equals(pacienteExistente.getTelefone())) {
                if (pacienteRepository.existsByTelefone(pacienteAtualizado.getTelefone())) {
                    throw new DadoUnicoException("Telefone já cadastrado");
                }
                pacienteExistente.setTelefone(pacienteAtualizado.getTelefone());
            }

            if(pacienteAtualizado.getDataNascimento().isAfter(LocalDate.now())){
                throw new DataInvalidaException("Data de nascimento não pode ser futura");
            }

            validadorCpf.validarCpf(pacienteAtualizado.getCpf());
            validadorEmail.validarEmail(pacienteAtualizado.getEmail());
            validadorTelefone.validarTelefone(pacienteAtualizado.getTelefone());
            validadorCep.validarCep(pacienteAtualizado.getCep());
            EnderecoDTO endereco = cepService.buscarEnderecoPorCep(pacienteAtualizado.getCep());

            pacienteExistente.setNome(pacienteAtualizado.getNome());
            pacienteExistente.setSexo(pacienteAtualizado.getSexo());
            pacienteExistente.setCpf(pacienteAtualizado.getCpf());
            pacienteExistente.setDataNascimento(pacienteAtualizado.getDataNascimento());
            pacienteExistente.setEmail(pacienteAtualizado.getEmail());
            pacienteExistente.setTelefone(pacienteAtualizado.getTelefone());
            pacienteExistente.setPossuiPlanoDeSaude(pacienteAtualizado.getPossuiPlanoDeSaude());
            pacienteExistente.setPlanoDeSaudeNome(pacienteAtualizado.getPlanoDeSaudeNome());
            pacienteExistente.setReceberNotificacaoEmail(pacienteAtualizado.getReceberNotificacaoEmail());
            pacienteExistente.setAceitouTermosTelemedicina(pacienteAtualizado.getAceitouTermosTelemedicina());
            pacienteExistente.setTipoSanguineo(pacienteAtualizado.getTipoSanguineo());
            pacienteExistente.setCep(pacienteAtualizado.getCep());
            pacienteExistente.setLogradouro(endereco.getLogradouro());
            pacienteExistente.setBairro(endereco.getBairro());
            pacienteExistente.setCidade(endereco.getLocalidade());
            pacienteExistente.setUf(endereco.getUf());

            Paciente paciente = pacienteRepository.save(pacienteExistente);
            log.debug("Paciente: {}", paciente);
            return new PacienteDTO(paciente);
        }catch (DadoUnicoException ex){
            throw new DadoUnicoException(ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao editar paciente: " + ex.getMessage());
        }
    }

    @Override
    public PacienteDTO buscarPaciente(Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
        return new PacienteDTO(paciente);
    }

    @Override
    public String excluirPaciente(Long pacienteId) {
        log.info("Excluindo paciente: {}", pacienteId);
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com o ID: " + pacienteId));

        pacienteRepository.delete(paciente);
        log.debug("Paciente: {}", paciente);
        return "Paciente excluído com sucesso";
    }
}
