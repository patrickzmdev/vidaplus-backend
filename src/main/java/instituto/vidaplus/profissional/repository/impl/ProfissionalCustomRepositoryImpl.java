package instituto.vidaplus.profissional.repository.impl;

import instituto.vidaplus.profissional.dto.ProfissionalResumoDTO;
import instituto.vidaplus.profissional.enums.EspecialidadeEnum;
import instituto.vidaplus.profissional.repository.ProfissionalCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfissionalCustomRepositoryImpl implements ProfissionalCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Page<ProfissionalResumoDTO> findProfissionaisByNomeContaining(String nome, Pageable pageable) {
        String jpql = "SELECT NEW instituto.vidaplus.profissional.dto.ProfissionalResumoDTO(" +
                "p.id, p.nome, p.registro, p.tipoProfissional, p.especialidade, p.permiteTelemedicina, p.cidade, p.uf) " +
                "FROM Profissional p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))";

        TypedQuery<ProfissionalResumoDTO> query = entityManager.createQuery(jpql, ProfissionalResumoDTO.class)
                .setParameter("nome", nome)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        String countJpql = "SELECT COUNT(p) FROM Profissional p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))";
        Long total = entityManager.createQuery(countJpql, Long.class)
                .setParameter("nome", nome)
                .getSingleResult();

        List<ProfissionalResumoDTO> resultado = query.getResultList();
        return new PageImpl<>(resultado, pageable, total);

    }

    @Override
    public Page<ProfissionalResumoDTO> findProfissionaisByEspecialidade(EspecialidadeEnum especialidade, Pageable pageable) {
        String jqpl = "SELECT NEW instituto.vidaplus.profissional.dto.ProfissionalResumoDTO(" +
                "p.id, p.nome, p.registro, p.tipoProfissional, p.especialidade, p.permiteTelemedicina, p.cidade, p.uf) " +
                "FROM Profissional p WHERE p.especialidade = :especialidade";

        TypedQuery<ProfissionalResumoDTO> query = entityManager.createQuery(jqpl, ProfissionalResumoDTO.class)
                .setParameter("especialidade", especialidade)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        String countJpql = "SELECT COUNT(p) FROM Profissional p WHERE p.especialidade = :especialidade";
        Long total = entityManager.createQuery(countJpql, Long.class)
                .setParameter("especialidade", especialidade)
                .getSingleResult();

        List<ProfissionalResumoDTO> resultado = query.getResultList();
        return new PageImpl<>(resultado, pageable, total);
    }

    @Override
    public Page<ProfissionalResumoDTO> findProfissionaisByCidadeContaining(String cidade, Pageable pageable) {
        String jqpl = "SELECT NEW instituto.vidaplus.profissional.dto.ProfissionalResumoDTO(" +
                "p.id, p.nome, p.registro, p.tipoProfissional, p.especialidade, p.permiteTelemedicina, p.cidade, p.uf) " +
                "FROM Profissional p WHERE LOWER(p.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))";

        TypedQuery<ProfissionalResumoDTO> query = entityManager.createQuery(jqpl, ProfissionalResumoDTO.class)
                .setParameter("cidade", cidade)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        String countJpql = "SELECT COUNT(p) FROM Profissional p WHERE LOWER(p.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))";
        Long total = entityManager.createQuery(countJpql, Long.class)
                .setParameter("cidade", cidade)
                .getSingleResult();

        List<ProfissionalResumoDTO> resultado = query.getResultList();
        return new PageImpl<>(resultado, pageable, total);
    }

    @Override
    public Page<ProfissionalResumoDTO> findProfissionaisByEspecialidadeAndCidadeContaining(EspecialidadeEnum especialidade, String cidade, Pageable pageable) {
        String jqpl = "SELECT NEW instituto.vidaplus.profissional.dto.ProfissionalResumoDTO(" +
                "p.id, p.nome, p.registro, p.tipoProfissional, p.especialidade, p.permiteTelemedicina, p.cidade, p.uf) " +
                "FROM Profissional p WHERE p.especialidade = :especialidade AND LOWER(p.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))";

        TypedQuery<ProfissionalResumoDTO> query = entityManager.createQuery(jqpl, ProfissionalResumoDTO.class)
                .setParameter("especialidade", especialidade)
                .setParameter("cidade", cidade)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        String countJpql = "SELECT COUNT(p) FROM Profissional p WHERE p.especialidade = :especialidade AND LOWER(p.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))";
        Long total = entityManager.createQuery(countJpql, Long.class)
                .setParameter("especialidade", especialidade)
                .setParameter("cidade", cidade)
                .getSingleResult();

        List<ProfissionalResumoDTO> resultado = query.getResultList();
        return new PageImpl<>(resultado, pageable, total);
    }
}
