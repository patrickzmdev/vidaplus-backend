package instituto.vidaplus.profissional.service.impl;

import instituto.vidaplus.documentacao.FinalidadeTratamento;
import instituto.vidaplus.endereco.dto.EnderecoDTO;
import instituto.vidaplus.endereco.service.CepService;
import instituto.vidaplus.exception.genericas.DadoUnicoException;
import instituto.vidaplus.exception.genericas.DataInvalidaException;
import instituto.vidaplus.profissional.dto.ProfissionalDTO;
import instituto.vidaplus.profissional.dto.ProfissionalResumoDTO;
import instituto.vidaplus.profissional.enums.EspecialidadeEnum;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProfissionalServiceImpl implements ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final ProfissionalCustomRepository profissionalCustomRepository;
    private final ValidadorCep validadorCep;
    private final ValidadorCpf validadorCpf;
    private final ValidadorEmail validadorEmail;
    private final ValidadorTelefone validadorTelefone;
    private final CepService cepService;
    private static final Logger logger = LoggerFactory.getLogger(ProfissionalServiceImpl.class);

    @Override
    @FinalidadeTratamento(descricao = "Cadastro de unidades hospitalares para atendimento",
            baseLegal = "Execução de contrato/Interesse legítimo")
    public ProfissionalDTO cadastrarProfissional(ProfissionalDTO profissionalDTO) {
        try{
            logger.info("Cadastrando profissional");
            if(profissionalDTO.getNome() == null || profissionalDTO.getNome().isEmpty()){
                throw new IllegalArgumentException("Nome do profissional é obrigatório");
            }
            if(profissionalDTO.getCpf() == null || profissionalDTO.getCpf().isEmpty()){
                throw new IllegalArgumentException("CPF do profissional é obrigatório");
            }

            if(profissionalDTO.getRegistro() == null || profissionalDTO.getRegistro().isEmpty()){
                throw new RegistroProfissionalObrigatorio("Registro do profissional é obrigatório");
            }

            if (profissionalRepository.existsByCpf(profissionalDTO.getCpf())) {
                throw new DadoUnicoException("CPF já cadastrado");
            }

            if (profissionalRepository.existsByEmail(profissionalDTO.getEmail())) {
                throw new DadoUnicoException("Email já cadastrado");
            }

            if (profissionalRepository.existsByTelefone(profissionalDTO.getTelefone())) {
                throw new DadoUnicoException("Telefone já cadastrado");
            }

            if (profissionalRepository.existsByRegistro(profissionalDTO.getRegistro())) {
                throw new DadoUnicoException("Registro já cadastrado");
            }

            if (profissionalDTO.getDataNascimento().isAfter(LocalDate.now())){
                throw new DataInvalidaException("O campo data de nascimento não pode ser maior que a data atual");
            }

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

            Profissional profissionalSalvo = profissionalRepository.save(profissional);
            logger.debug("Profissional salvo: {}", profissionalSalvo);
            return new ProfissionalDTO(profissionalSalvo);
        }catch (DataIntegrityViolationException ex){
                throw new RuntimeException("Erro ao cadastrar profissional: " + ex.getMessage());
        }
    }

    @Override
    public ProfissionalDTO editarProfissional(Long id, ProfissionalDTO profissionalDTO) {
        try {
            logger.info("Editando profissional com ID: {}", id);
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

            if (profissionalRepository.existsByCpfAndIdNot(profissionalDTO.getCpf(), id)) {
                throw new DadoUnicoException("CPF já cadastrado");
            }

            if (profissionalRepository.existsByEmailAndIdNot(profissionalDTO.getEmail(), id)) {
                throw new DadoUnicoException("Email já cadastrado");
            }

            if (profissionalRepository.existsByTelefoneAndIdNot(profissionalDTO.getTelefone(), id)) {
                throw new DadoUnicoException("Telefone já cadastrado");
            }

            if (profissionalRepository.existsByRegistroAndIdNot(profissionalDTO.getRegistro(), id)) {
                throw new DadoUnicoException("Registro já cadastrado");
            }

            if (profissionalDTO.getDataNascimento().isAfter(LocalDate.now())){
                throw new DataInvalidaException("O campo data de nascimento não pode ser maior que a data atual");
            }

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

            Profissional profissionalSalvo = profissionalRepository.save(profissionalExistente);
            logger.debug("Profissional editado: {}", profissionalSalvo);
            return new ProfissionalDTO(profissionalSalvo);

        } catch (DataIntegrityViolationException ex) {
                throw new RuntimeException("Erro ao cadastrar profissional: " + ex.getMessage());
        }
    }

    @Override
    public String excluirProfissional(Long id) {
        logger.info("Excluindo profissional: {}", id);
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException("Profissional não encontrado"));
        profissionalRepository.delete(profissional);
        logger.debug("Profissional excluído: {}", profissional);
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
    public Page<ProfissionalResumoDTO> buscarProfissionaisPorEspecialidade(EspecialidadeEnum especialidade, Pageable pageable) {
        return profissionalCustomRepository.findProfissionaisByEspecialidade(especialidade, pageable);
    }

    @Override
    public Page<ProfissionalResumoDTO> buscarProfissionaisPorCidade(String cidade, Pageable pageable) {
        return profissionalCustomRepository.findProfissionaisByCidadeContaining(cidade, pageable);
    }

    @Override
    public Page<ProfissionalResumoDTO> buscarProfissionaisPorEspecialidadeCidade(EspecialidadeEnum especialidade, String cidade, Pageable pageable) {
        return profissionalCustomRepository.findProfissionaisByEspecialidadeAndCidadeContaining(especialidade, cidade, pageable);
    }
}
