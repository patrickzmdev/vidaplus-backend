package instituto.vidaplus.receita.service;

import instituto.vidaplus.receita.dto.ItemReceitaDTO;
import instituto.vidaplus.receita.dto.ItemReceitaInfo;

import java.util.List;

public interface ItemReceitaService {
    ItemReceitaDTO adicionarItemReceita(Long receitaId, ItemReceitaDTO itemReceitaDTO);
    String removerItemReceita(Long itemReceitaId);
    List<ItemReceitaInfo> listarItemPorReceita(Long receitaId);
}
