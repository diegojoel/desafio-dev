package pilger.diego.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pilger.diego.api.models.Transacao;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Transactional(readOnly = true)
    @Query(value = "select t.tipo, sum(t.valor) " +
            "from transacao t " +
            "left join loja l on (l.id = t.id_loja) " +
            "where l.id = :idLoja " +
            "group by tipo " +
            "order by tipo",
            nativeQuery = true)
    List<Object[]> somaPorOperacaoPorLoja(long idLoja);

}
