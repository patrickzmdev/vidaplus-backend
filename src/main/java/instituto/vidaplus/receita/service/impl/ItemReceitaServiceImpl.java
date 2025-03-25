package instituto.vidaplus.receita.service.impl;

import instituto.vidaplus.receita.dto.ItemReceitaDTO;
import instituto.vidaplus.receita.dto.ItemReceitaInfo;
import instituto.vidaplus.receita.exception.ItemReceitaNaoEncontradoException;
import instituto.vidaplus.receita.exception.ReceitaNaoEncontradaException;
import instituto.vidaplus.receita.model.ItemReceita;
import instituto.vidaplus.receita.model.Receita;
import instituto.vidaplus.receita.repository.ItemReceitaRepository;
import instituto.vidaplus.receita.repository.ReceitaRepository;
import instituto.vidaplus.receita.service.ItemReceitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemReceitaServiceImpl implements ItemReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ItemReceitaRepository itemReceitaRepository;

    @Override
    public ItemReceitaDTO adicionarItemReceita(Long receitaId, ItemReceitaDTO itemReceitaDTO) {
        Receita receita = receitaRepository.findById(receitaId)
                .orElseThrow(() -> new ReceitaNaoEncontradaException("Receita não encontrada"));
        ItemReceita itemReceita = new ItemReceita();
        itemReceita.setReceita(receita);
        itemReceita.setNome(itemReceitaDTO.getNome());
        itemReceita.setControlado(itemReceitaDTO.getControlado());
        itemReceita.setQuantidade(itemReceitaDTO.getQuantidade());
        ItemReceita itemReceitaSalvo = itemReceitaRepository.save(itemReceita);
        return new ItemReceitaDTO(itemReceitaSalvo);
    }

    @Override
    public String removerItemReceita(Long itemReceitaId) {
        ItemReceita itemReceita = itemReceitaRepository.findById(itemReceitaId)
                .orElseThrow(() -> new ItemReceitaNaoEncontradoException("Item de receita não encontrado"));

        itemReceitaRepository.delete(itemReceita);
        return "Item de receita removido com sucesso";
    }

    @Override
    public List<ItemReceitaInfo> listarItemPorReceita(Long receitaId) {
        receitaRepository.findById(receitaId)
                .orElseThrow(() -> new ReceitaNaoEncontradaException("Receita não encontrada"));
        return itemReceitaRepository.findItemReceitaInfo(receitaId);
    }
}
