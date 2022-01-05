package pilger.diego.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pilger.diego.api.models.Loja;
import pilger.diego.api.models.Transacao;
import pilger.diego.api.repositories.LojaRepository;
import pilger.diego.api.repositories.TransacaoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class AtomicPersistFileService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @Transactional(propagation = REQUIRES_NEW, rollbackFor = Exception.class)
    public void persist(final List<Transacao> transacoes) {
        final Map<String, Loja> lojasByNome = new HashMap<>();
        for (Transacao t : transacoes) {
            lojasByNome.put(t.getLoja().getNome(), t.getLoja());
        }
        checkAndPersistLojas(lojasByNome);

        for (Transacao t : transacoes) {
            t.setLoja(lojasByNome.get(t.getLoja().getNome()));
        }

        transacaoRepository.saveAllAndFlush(transacoes);
    }

    private void checkAndPersistLojas(final Map<String, Loja> lojasMap) {
        final List<Loja> lojas = lojaRepository.findAllByNomes(lojasMap.keySet());
        for (Loja l : lojas) {
            lojasMap.put(l.getNome(), l);
        }
        for (Map.Entry<String, Loja> e : lojasMap.entrySet()) {
            if (e.getValue().getId() == null) {
                e.setValue(lojaRepository.save(e.getValue()));
            }
        }
        lojaRepository.flush();
    }
}
