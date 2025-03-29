package instituto.vidaplus.leito.service.impl;

import instituto.vidaplus.internacao.exception.InternacaoNaoEncontradaException;
import instituto.vidaplus.internacao.model.Internacao;
import instituto.vidaplus.internacao.repository.InternacaoRepository;
import instituto.vidaplus.leito.dto.LeitoDTO;
import instituto.vidaplus.leito.exception.LeitoJaCadastradoException;
import instituto.vidaplus.leito.exception.LeitoNaoExistenteException;
import instituto.vidaplus.leito.model.Leito;
import instituto.vidaplus.leito.repository.LeitoRepository;
import instituto.vidaplus.leito.service.LeitoService;
import instituto.vidaplus.paciente.dto.PacienteDTO;
import instituto.vidaplus.paciente.model.Paciente;
import instituto.vidaplus.unidade.exception.UnidadeHospitalarNaoEncontradaException;
import instituto.vidaplus.unidade.model.UnidadeHospitalar;
import instituto.vidaplus.unidade.repository.UnidadeHospitalarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeitoServiceImpl implements LeitoService {

    private final LeitoRepository leitoRepository;
    private final UnidadeHospitalarRepository unidadeHospitalarRepository;
    private final InternacaoRepository internacaoRepository;

    @Override
    public LeitoDTO adicionarLeito(Long unidadeHospitalarId, LeitoDTO leitoDTO) {
        UnidadeHospitalar unidadeHospitalar = unidadeHospitalarRepository.findById(unidadeHospitalarId)
                .orElseThrow(() -> new UnidadeHospitalarNaoEncontradaException("Unidade Hospitalar não encontrada"));

        if(leitoRepository.existsByNumeroAndUnidadeHospitalarId(leitoDTO.getNumero(), unidadeHospitalarId)) {
            throw new LeitoJaCadastradoException("Já existe um leito com este número nesta unidade hospitalar");
        }

        Leito leito = new Leito();
        leito.setNumero(leitoDTO.getNumero());
        leito.setOcupado(false);
        leito.setUnidadeHospitalar(unidadeHospitalar);

        Leito leitoSalvo = leitoRepository.save(leito);
        return new LeitoDTO(leitoSalvo);
    }

    @Override
    public LeitoDTO buscarLeito(Long id) {
        Leito leito = leitoRepository.findById(id)
                .orElseThrow(() -> new LeitoNaoExistenteException("Leito não encontrado"));
        return new LeitoDTO(leito);
    }

    @Override
    public String deletarLeito(Long id) {
        Leito leito = leitoRepository.findById(id)
                .orElseThrow(() -> new LeitoNaoExistenteException("Leito não encontrado"));
        leitoRepository.delete(leito);
        return "Leito deletado com sucesso";
    }

    @Override
    public Boolean verificarLeitoDisponivel(Long id) {
        Leito leito = leitoRepository.findById(id)
                .orElseThrow(() -> new LeitoNaoExistenteException("Leito não encontrado"));
        return !leito.getOcupado();
    }

    @Override
    public PacienteDTO buscarPacienteInternadoPorLeito(Long leitoId, Long unidadeHospitalarId) {
        Leito leito = leitoRepository.findById(leitoId)
                .orElseThrow(() -> new LeitoNaoExistenteException("Leito não encontrado"));

        UnidadeHospitalar unidadeHospitalar = unidadeHospitalarRepository.findById(unidadeHospitalarId)
                .orElseThrow(() -> new UnidadeHospitalarNaoEncontradaException("Unidade Hospitalar não encontrada"));

        if(!leito.getOcupado()) {
            throw new IllegalArgumentException("Este leito não está ocupado");
        }

        Internacao internacao = internacaoRepository.findByLeitoIdAndAtivaTrue(leitoId)
                .orElseThrow(() -> new InternacaoNaoEncontradaException("Não foi possível encontrar a internação"));

        Paciente paciente = internacao.getPaciente();
        return new PacienteDTO(paciente);
    }

    @Override
    public Page<LeitoDTO> listarLeitosDisponiveis(Long unidadeHospitalarId) {
        unidadeHospitalarRepository.findById(unidadeHospitalarId)
                .orElseThrow(() -> new UnidadeHospitalarNaoEncontradaException("Unidade Hospitalar não encontrada"));

        Pageable pageable = PageRequest.of(0, 20);
        Page<Leito> leitos = leitoRepository.findByUnidadeHospitalarIdAndOcupadoFalse(unidadeHospitalarId, pageable);
        return leitos.map(LeitoDTO::new);
    }
}
