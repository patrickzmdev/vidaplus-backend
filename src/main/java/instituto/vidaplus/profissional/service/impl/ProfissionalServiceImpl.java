package instituto.vidaplus.profissional.service.impl;

import instituto.vidaplus.administrador.exception.AdministradorNaoEncontradoException;
import instituto.vidaplus.administrador.model.Administrador;
import instituto.vidaplus.administrador.repository.AdministradorRepository;
import instituto.vidaplus.endereco.dto.EnderecoDTO;
import instituto.vidaplus.endereco.service.CepService;
import instituto.vidaplus.exception.genericas.DadoUnicoException;
import instituto.vidaplus.profissional.dto.ProfissionalDTO;
import instituto.vidaplus.profissional.dto.ProfissionalResumoDTO;
import instituto.vidaplus.profissional.exception.ProfissionalNaoEncontradoException;
import instituto.vidaplus.profissional.exception.RegistroProfissionalObrigatorio;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.profissional.repository.ProfissionalCustomRepository;
import instituto.vidaplus.profissional.repository.ProfissionalRepository;
import instituto.vidaplus.profissional.service.ProfissionalService;
import instituto.vidaplus.utils.validador.ValidadorCep;
import instituto.vidaplus.utils.validador.ValidadorCpf;
import instituto.vidaplus.utils.validador.ValidadorEmail;
import instituto.vidaplus.utils.validador.ValidadorTelefone;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfissionalServiceImpl implements ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final AdministradorRepository administradorRepository;
    private final ProfissionalCustomRepository profissionalCustomRepository;
    private final ValidadorCep validadorCep;
    private final ValidadorCpf validadorCpf;
    private final ValidadorEmail validadorEmail;
    private final ValidadorTelefone validadorTelefone;
    private final CepService cepService;

    @Override
    public ProfissionalDTO cadastrarProfissional(Long administradorId, ProfissionalDTO profissionalDTO) {
        try{
            if(profissionalDTO.getNome() == null || profissionalDTO.getNome().isEmpty()){
                throw new IllegalArgumentException("Nome do profissional é obrigatório");
            }
            if(profissionalDTO.getCpf() == null || profissionalDTO.getCpf().isEmpty()){
                throw new IllegalArgumentException("CPF do profissional é obrigatório");
            }

            if(profissionalDTO.getRegistro() == null || profissionalDTO.getRegistro().isEmpty()){
                throw new RegistroProfissionalObrigatorio("Registro do profissional é obrigatório");
            }

            Administrador administrador = administradorRepository.findById(administradorId)
                    .orElseThrow(() -> new AdministradorNaoEncontradoException("Administrador não encontrado"));

            validadorCpf.validarCpf(profissionalDTO.getCpf());
            validadorEmail.validarEmail(profissionalDTO.getEmail());
            validadorTelefone.validarTelefone(profissionalDTO.getTelefone());
            validadorCep.validarCep(profissionalDTO.getCep());

            EnderecoDTO endereco = cepService.buscarEnderecoPorCep(profissionalDTO.getCep());

            Profissional profissional = new Profissional();
            profissional.setNome(profissionalDTO.getNome());
            profissional.setCpf(profissionalDTO.getCpf());
            profissional.setSexo(profissionalDTO.getSexo());
            profissional.setDataNascimento(profissionalDTO.getDataNascimento());
            profissional.setEmail(profissionalDTO.getEmail());
            profissional.setTelefone(profissionalDTO.getTelefone());
            profissional.setRegistro(profissionalDTO.getRegistro());
            profissional.setEspecialidade(profissionalDTO.getEspecialidade());
            profissional.setTipoProfissional(profissionalDTO.getTipoProfissional());
            profissional.setPermiteTelemedicina(profissionalDTO.getPermiteTelemedicina());
            profissional.setTokenTelemedicina(profissionalDTO.getTokenTelemedicina());
            profissional.setCep(profissionalDTO.getCep());
            profissional.setLogradouro(endereco.getLogradouro());
            profissional.setBairro(endereco.getBairro());
            profissional.setCidade(endereco.getLocalidade());
            profissional.setUf(endereco.getUf());
            profissional.setAdministrador(administrador);

            Profissional profissionalSalvo = profissionalRepository.save(profissional);
            return new ProfissionalDTO(profissionalSalvo);
        }catch (DataIntegrityViolationException ex){
            if (ex.getMessage().contains("cpf")) {
                throw new DadoUnicoException("CPF já cadastrado");
            } else if (ex.getMessage().contains("email")) {
                throw new DadoUnicoException("Email já cadastrado");
            } else if (ex.getMessage().contains("telefone")) {
                throw new DadoUnicoException("Telefone já cadastrado");
            } else if (ex.getMessage().contains("registro")) {
                throw new DadoUnicoException("Registro já cadastrado");
            } else {
                throw new RuntimeException("Erro ao cadastrar profissional: " + ex.getMessage());
            }
        }
    }

    @Override
    public ProfissionalDTO editarProfissional(Long id, ProfissionalDTO profissionalDTO) {
        try {
            Profissional profissionalExistente = profissionalRepository.findById(id)
                    .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));

            if (profissionalDTO.getNome() == null || profissionalDTO.getNome().isEmpty()) {
                throw new IllegalArgumentException("Nome do profissional é obrigatório");
            }
            if (profissionalDTO.getCpf() == null || profissionalDTO.getCpf().isEmpty()) {
                throw new IllegalArgumentException("CPF do profissional é obrigatório");
            }

            if (profissionalDTO.getRegistro() == null || profissionalDTO.getRegistro().isEmpty()) {
                throw new RegistroProfissionalObrigatorio("Registro do profissional é obrigatório");
            }

            Administrador administrador = administradorRepository.findById(profissionalDTO.getAdministradorId())
                    .orElseThrow(() -> new AdministradorNaoEncontradoException("Administrador não encontrado"));

            validadorCpf.validarCpf(profissionalDTO.getCpf());
            validadorEmail.validarEmail(profissionalDTO.getEmail());
            validadorTelefone.validarTelefone(profissionalDTO.getTelefone());
            validadorCep.validarCep(profissionalDTO.getCep());

            EnderecoDTO endereco = cepService.buscarEnderecoPorCep(profissionalDTO.getCep());

            profissionalExistente.setNome(profissionalDTO.getNome());
            profissionalExistente.setCpf(profissionalDTO.getCpf());
            profissionalExistente.setSexo(profissionalDTO.getSexo());
            profissionalExistente.setDataNascimento(profissionalDTO.getDataNascimento());
            profissionalExistente.setEmail(profissionalDTO.getEmail());
            profissionalExistente.setTelefone(profissionalDTO.getTelefone());
            profissionalExistente.setRegistro(profissionalDTO.getRegistro());
            profissionalExistente.setEspecialidade(profissionalDTO.getEspecialidade());
            profissionalExistente.setTipoProfissional(profissionalDTO.getTipoProfissional());
            profissionalExistente.setPermiteTelemedicina(profissionalDTO.getPermiteTelemedicina());
            profissionalExistente.setTokenTelemedicina(profissionalDTO.getTokenTelemedicina());
            profissionalExistente.setCep(profissionalDTO.getCep());
            profissionalExistente.setLogradouro(endereco.getLogradouro());
            profissionalExistente.setBairro(endereco.getBairro());
            profissionalExistente.setCidade(endereco.getLocalidade());
            profissionalExistente.setUf(endereco.getUf());
            profissionalExistente.setAdministrador(administrador);

            Profissional profissionalSalvo = profissionalRepository.save(profissionalExistente);
            return new ProfissionalDTO(profissionalSalvo);

        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("cpf")) {
                throw new DadoUnicoException("CPF já cadastrado");
            } else if (ex.getMessage().contains("email")) {
                throw new DadoUnicoException("Email já cadastrado");
            } else if (ex.getMessage().contains("telefone")) {
                throw new DadoUnicoException("Telefone já cadastrado");
            } else if (ex.getMessage().contains("registro")) {
                throw new DadoUnicoException("Registro já cadastrado");
            } else {
                throw new RuntimeException("Erro ao cadastrar profissional: " + ex.getMessage());
            }
        }
    }

    @Override
    public String excluirProfissional(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));
        profissionalRepository.delete(profissional);
        return "Profissional excluído com sucesso";
    }

    @Override
    public ProfissionalDTO buscarProfissional(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));
        return new ProfissionalDTO(profissional);
    }

    @Override
    public Page<ProfissionalResumoDTO> buscarProfissionaisPorNome(String nome, Pageable pageable) {
        return profissionalCustomRepository.findProfissionaisByNomeContaining(nome, pageable);
    }

    @Override
    public Page<ProfissionalDTO> buscarProfissionaisPorEspecialidade(String especialidade, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProfissionalDTO> buscarProfissionaisPorCidade(String cidade, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProfissionalDTO> buscarProfissionaisPorEspecialidadeCidade(String especialidade, String cidade, Pageable pageable) {
        return null;
    }
}
