package pilger.diego.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pilger.diego.api.models.Loja;

import java.util.List;
import java.util.Set;

public interface LojaRepository extends JpaRepository<Loja, Long> {

    @Query("select l from Loja l where l.nome in :nomes")
    List<Loja> findAllByNomes(Set<String> nomes);

    @Transactional(readOnly = true)
    @Query(value = "select l.id, l.nome, sum(t.valor) " +
            "from loja l " +
            "left join transacao t on (l.id = t.id_loja) " +
            "where l.nome > :fromNome " +
            "group by l.id, l.nome " +
            "order by l.nome " +
            "limit :pageSize", nativeQuery = true)
    List<Object[]> findProximos50ComSaldo(String fromNome, int pageSize);
}
