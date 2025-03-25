package instituto.vidaplus.receita.repository;

import instituto.vidaplus.receita.dto.ItemReceitaInfo;
import instituto.vidaplus.receita.model.ItemReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemReceitaRepository extends JpaRepository<ItemReceita, Long> {
    List<ItemReceita> findByReceitaId(Long receitaId);

    @Query(value = "SELECT ic.nome, ic.quantidade FROM itens_receita AS ic" +
            " JOIN receitas AS r ON ic.receita_id = r.id WHERE r.id = :receitaId", nativeQuery = true)
    List<ItemReceitaInfo> findItemReceitaInfo(Long receitaId);
}
