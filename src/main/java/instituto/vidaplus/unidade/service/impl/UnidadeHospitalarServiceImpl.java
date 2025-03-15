package instituto.vidaplus.unidade.service.impl;

import instituto.vidaplus.administrador.exception.AdministradorNaoEncontradoException;
import instituto.vidaplus.administrador.model.Administrador;
import instituto.vidaplus.administrador.repository.AdministradorRepository;
import instituto.vidaplus.endereco.dto.EnderecoDTO;
import instituto.vidaplus.endereco.service.CepService;
import instituto.vidaplus.exception.genericas.DadoUnicoException;
import instituto.vidaplus.unidade.dto.UnidadeHospitalarDTO;
import instituto.vidaplus.unidade.exception.UnidadeHospitalarNaoEncontradaException;
import instituto.vidaplus.unidade.model.UnidadeHospitalar;
import instituto.vidaplus.unidade.repository.UnidadeHospitalarRepository;
import instituto.vidaplus.unidade.service.UnidadeHospitalarService;
import instituto.vidaplus.utils.validador.ValidadorCep;
import instituto.vidaplus.utils.validador.ValidadorTelefone;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnidadeHospitalarServiceImpl implements UnidadeHospitalarService {

    private final UnidadeHospitalarRepository unidadeHospitalarRepository;
    private final AdministradorRepository administradorRepository;
    private final CepService cepService;
    private final ValidadorCep validadorCep;
    private final ValidadorTelefone validadorTelefone;

    @Override
    public UnidadeHospitalarDTO cadastrarUnidadeHospitalar(Long administradorId, UnidadeHospitalarDTO unidadeHospitalarDTO) {
        try{
            Administrador administrador = administradorRepository.findById(administradorId)
                    .orElseThrow(() -> new AdministradorNaoEncontradoException("Administrador não encontrado"));

            validadorCep.validarCep(unidadeHospitalarDTO.getCep());
            validadorTelefone.validarTelefone(unidadeHospitalarDTO.getTelefone());

            EnderecoDTO enderecoDTO = cepService.buscarEnderecoPorCep(unidadeHospitalarDTO.getCep());

            UnidadeHospitalar unidadeHospitalar = new UnidadeHospitalar();
            unidadeHospitalar.setNome(unidadeHospitalarDTO.getNome());
            unidadeHospitalar.setCep(unidadeHospitalarDTO.getCep());
            unidadeHospitalar.setEmail(unidadeHospitalarDTO.getEmail());
            unidadeHospitalar.setTelefone(unidadeHospitalarDTO.getTelefone());
            unidadeHospitalar.setLogradouro(enderecoDTO.getLogradouro());
            unidadeHospitalar.setCidade(enderecoDTO.getLocalidade());
            unidadeHospitalar.setUf(enderecoDTO.getUf());
            unidadeHospitalar.setBairro(enderecoDTO.getBairro());
            unidadeHospitalar.setAdministrador(administrador);

            UnidadeHospitalar unidadeHospitalarSalva = unidadeHospitalarRepository.save(unidadeHospitalar);
            return new UnidadeHospitalarDTO(unidadeHospitalarSalva);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("telefone")) {
                throw new DadoUnicoException("Telefone já cadastrado");
            } else {
                throw new RuntimeException("Erro ao cadastrar unidade hospitalar: \" + ex.getMessage()");
            }
        }
    }

    @Override
    public UnidadeHospitalarDTO editarUnidadeHospitalar(Long id, UnidadeHospitalarDTO unidadeHospitalarDTO) {
        try{
            UnidadeHospitalar unidadeHospitalar = unidadeHospitalarRepository.findById(id)
                    .orElseThrow(() -> new UnidadeHospitalarNaoEncontradaException("Unidade hospitalar não encontrada"));

            Administrador administrador = administradorRepository.findById(unidadeHospitalarDTO.getAdministradorId())
                    .orElseThrow(() -> new AdministradorNaoEncontradoException("Administrador não encontrado"));

            validadorTelefone.validarTelefone(unidadeHospitalarDTO.getTelefone());
            validadorCep.validarCep(unidadeHospitalarDTO.getCep());

            EnderecoDTO enderecoDTO = cepService.buscarEnderecoPorCep(unidadeHospitalarDTO.getCep());

            unidadeHospitalar.setNome(unidadeHospitalarDTO.getNome());
            unidadeHospitalar.setCep(unidadeHospitalarDTO.getCep());
            unidadeHospitalar.setEmail(unidadeHospitalarDTO.getEmail());
            unidadeHospitalar.setTelefone(unidadeHospitalarDTO.getTelefone());
            unidadeHospitalar.setLogradouro(enderecoDTO.getLogradouro());
            unidadeHospitalar.setCidade(enderecoDTO.getLocalidade());
            unidadeHospitalar.setUf(enderecoDTO.getUf());
            unidadeHospitalar.setBairro(enderecoDTO.getBairro());
            unidadeHospitalar.setAdministrador(administrador);

            UnidadeHospitalar unidadeHospitalarSalva = unidadeHospitalarRepository.save(unidadeHospitalar);
            return new UnidadeHospitalarDTO(unidadeHospitalarSalva);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("telefone")) {
                throw new DadoUnicoException("Telefone já cadastrado");
            } else {
                throw new RuntimeException("Erro ao editar unidade hospitalar: \" + ex.getMessage()");
            }
        }
    }

    @Override
    public String excluirUnidadeHospitalar(Long id) {
        UnidadeHospitalar unidadeHospitalar = unidadeHospitalarRepository.findById(id)
                .orElseThrow(() -> new UnidadeHospitalarNaoEncontradaException("Unidade hospitalar não encontrada"));
        unidadeHospitalarRepository.delete(unidadeHospitalar);
        return "Unidade hospitalar excluída com sucesso";
    }

    @Override
    public UnidadeHospitalarDTO buscarUnidadeHospitalar(Long id) {
        UnidadeHospitalar unidadeHospitalar = unidadeHospitalarRepository.findById(id)
                .orElseThrow(() -> new UnidadeHospitalarNaoEncontradaException("Unidade hospitalar não encontrada"));
        return new UnidadeHospitalarDTO(unidadeHospitalar);
    }

    @Override
    public Page<UnidadeHospitalarDTO> buscarUnidadeHospitalarPorCidade(String cidade, Pageable pageable) {
        Page<UnidadeHospitalar> unidades = unidadeHospitalarRepository.findByCidadeContaining(cidade, pageable);
        return unidades.map(UnidadeHospitalarDTO::new);
    }
}
