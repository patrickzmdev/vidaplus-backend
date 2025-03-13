package instituto.vidaplus.administrador.service.impl;

import instituto.vidaplus.administrador.exception.AdministradorNaoEncontradoException;
import instituto.vidaplus.administrador.model.Administrador;
import instituto.vidaplus.administrador.repository.AdministradorRepository;
import instituto.vidaplus.administrador.service.AdministradorService;
import instituto.vidaplus.endereco.dto.EnderecoDTO;
import instituto.vidaplus.endereco.service.CepService;
import instituto.vidaplus.internacao.model.Internacao;
import instituto.vidaplus.leito.model.Leito;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.profissional.model.Profissional;
import instituto.vidaplus.suprimento.model.Suprimento;
import instituto.vidaplus.utils.validador.ValidadorCep;
import instituto.vidaplus.utils.validador.ValidadorCpf;
import instituto.vidaplus.utils.validador.ValidadorEmail;
import instituto.vidaplus.utils.validador.ValidadorTelefone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final ValidadorCep validadorCep;
    private final ValidadorCpf validadorCpf;
    private final ValidadorEmail validadorEmail;
    private final ValidadorTelefone validadorTelefone;
    private final CepService cepService;


    @Override
    @Transactional
    public Administrador cadastrarAdministrador(Administrador administrador) {
        if(administrador.getNome() == null || administrador.getNome().isEmpty()){
            throw new IllegalArgumentException("Nome do administrador é obrigatório");
        }
        if(administrador.getCpf() == null || administrador.getCpf().isEmpty()){
            throw new IllegalArgumentException("CPF do administrador é obrigatório");
        }

        validadorCpf.validarCpf(administrador.getCpf());
        validadorEmail.validarEmail(administrador.getEmail());
        validadorTelefone.validarTelefone(administrador.getTelefone());
        validadorCep.validarCep(administrador.getCep());

        EnderecoDTO enderecoDTO = cepService.buscarEnderecoPorCep(administrador.getCep());
        administrador.setLogradouro(enderecoDTO.getLogradouro());
        administrador.setBairro(enderecoDTO.getBairro());
        administrador.setCidade(enderecoDTO.getLocalidade());
        administrador.setUf(enderecoDTO.getUf());

        return administradorRepository.save(administrador);
    }

    @Override
    public String editarAdministrador(Long administradorId, Administrador administrador) {
        Administrador administradorExistente = administradorRepository.findById(administradorId)
                .orElseThrow(() -> new AdministradorNaoEncontradoException("Administrador não encontrado"));

        validadorCpf.validarCpf(administrador.getCpf());
        validadorEmail.validarEmail(administrador.getEmail());
        validadorTelefone.validarTelefone(administrador.getTelefone());
        validadorCep.validarCep(administrador.getCep());

        administradorExistente.setNome(administrador.getNome());
        administradorExistente.setSexo(administrador.getSexo());
        administradorExistente.setCpf(administrador.getCpf());
        administradorExistente.setDataNascimento(administrador.getDataNascimento());
        administradorExistente.setEmail(administrador.getEmail());
        administradorExistente.setTelefone(administrador.getTelefone());
        EnderecoDTO enderecoDTO = cepService.buscarEnderecoPorCep(administrador.getCep());
        administradorExistente.setCep(administrador.getCep());
        administrador.setLogradouro(enderecoDTO.getLogradouro());
        administrador.setBairro(enderecoDTO.getBairro());
        administrador.setCidade(enderecoDTO.getLocalidade());
        administrador.setUf(enderecoDTO.getUf());

        administradorRepository.save(administradorExistente);
        return "Administrador editado com sucesso";
    }

    @Override
    public Administrador buscarAdministrador(Long administradorId) {
        return administradorRepository.findById(administradorId)
                .orElseThrow(() -> new AdministradorNaoEncontradoException("Administrador não encontrado"));
    }

    @Override
    public void excluirAdministrador(Long administradorId) {
        Administrador administrador = administradorRepository.findById(administradorId)
                .orElseThrow(() -> new AdministradorNaoEncontradoException("Administrador não encontrado"));
        administradorRepository.delete(administrador);

    }

    @Override
    public List<Paciente> listarPacientes() {
        return List.of();
    }

    @Override
    public List<Profissional> listarProfissionais() {
        return List.of();
    }

    @Override
    public List<Internacao> listarInternacoesAtivas() {
        return List.of();
    }

    @Override
    public List<Leito> listarTodosLeitos() {
        return List.of();
    }

    @Override
    public List<Leito> listarLeitosDisponiveis() {
        return List.of();
    }

    @Override
    public List<Leito> listarLeitosOcupados() {
        return List.of();
    }

    @Override
    public List<Leito> listarLeitosPorUnidade(Long unidadeId) {
        return List.of();
    }

    @Override
    public byte[] gerarRelatorioOcupacao(LocalDate dataInicio, LocalDate dataFim) {
        return new byte[0];
    }

    @Override
    public byte[] gerarRelatorioFinanceiro(LocalDate dataInicio, LocalDate dataFim) {
        return new byte[0];
    }

    @Override
    public byte[] gerarRelatorioAtendimentos(LocalDate dataInicio, LocalDate dataFim) {
        return new byte[0];
    }

    @Override
    public List<Suprimento> listarSuprimentos() {
        return List.of();
    }

    @Override
    public List<Suprimento> listarSuprimentosBaixoEstoque() {
        return List.of();
    }
}
