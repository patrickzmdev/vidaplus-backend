package instituto.vidaplus.suprimento.service.impl;

import instituto.vidaplus.suprimento.dto.SuprimentoDTO;
import instituto.vidaplus.suprimento.exception.QuantidadeSuprimentoException;
import instituto.vidaplus.suprimento.exception.SuprimentoNaoEncontradoException;
import instituto.vidaplus.suprimento.model.Suprimento;
import instituto.vidaplus.suprimento.repository.SuprimentoRepository;
import instituto.vidaplus.suprimento.service.SuprimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuprimentoServiceImpl implements SuprimentoService {

    private final SuprimentoRepository suprimentoRepository;

    @Override
    public SuprimentoDTO cadastrarSuprimento(SuprimentoDTO suprimentoDTO) {

        if(suprimentoDTO.getQuantidade() <= 0) {
            throw new QuantidadeSuprimentoException("Quantidade inválida");
        }

        Suprimento suprimento = new Suprimento();
        suprimento.setNome(suprimentoDTO.getNome());
        suprimento.setQuantidade(suprimentoDTO.getQuantidade());
        suprimento.setUnidadeMedida(suprimentoDTO.getUnidadeMedida());

        suprimentoRepository.save(suprimento);
        return new SuprimentoDTO(suprimento);
    }

    @Override
    public SuprimentoDTO editarSuprimento(Long id, SuprimentoDTO suprimentoDTO) {
        Suprimento suprimentoExistente = suprimentoRepository.findById(id)
                .orElseThrow(() -> new SuprimentoNaoEncontradoException("Suprimento não encontrado"));

        if(suprimentoDTO.getQuantidade() <= 0) {
            throw new QuantidadeSuprimentoException("Quantidade inválida");
        }

        suprimentoExistente.setNome(suprimentoDTO.getNome());
        suprimentoExistente.setQuantidade(suprimentoDTO.getQuantidade());
        suprimentoExistente.setUnidadeMedida(suprimentoDTO.getUnidadeMedida());

        Suprimento suprimento = suprimentoRepository.save(suprimentoExistente);
        return new SuprimentoDTO(suprimento);
    }

    @Override
    public String excluirSuprimento(Long id) {
        Suprimento suprimento = suprimentoRepository.findById(id)
                .orElseThrow(() -> new SuprimentoNaoEncontradoException("Suprimento não encontrado"));
        suprimentoRepository.delete(suprimento);
        return "Suprimento excluído com sucesso";
    }

    @Override
    public SuprimentoDTO buscarSuprimento(Long id) {
        Suprimento suprimento = suprimentoRepository.findById(id)
                .orElseThrow(() -> new SuprimentoNaoEncontradoException("Suprimento não encontrado"));
        return new SuprimentoDTO(suprimento);
    }

    @Override
    public Page<SuprimentoDTO> listarSuprimentos(Pageable pageable) {
        Page<Suprimento> suprimentos = suprimentoRepository.findAll(pageable);
        return suprimentos.map(SuprimentoDTO::new);
    }

    @Override
    public Page<SuprimentoDTO> buscarSuprimentosPorNome(String nome, Pageable pageable) {
        Page<Suprimento> suprimentos = suprimentoRepository.findByNomeContainingIgnoreCase(nome, pageable);
        return suprimentos.map(SuprimentoDTO::new);
    }
}
