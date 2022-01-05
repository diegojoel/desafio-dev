package pilger.diego.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pilger.diego.api.dtos.TotalOperacaoLojaDto;
import pilger.diego.api.repositories.TransacaoRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = TransacaoController.REQUEST_MAPPING)
public class TransacaoController {

    static final String REQUEST_MAPPING = "/transacao";
    private static final String SOMA_POR_OPERACAO_POR_LOJA = "/soma-por-operacao-por-loja/{idLoja}";

    private static Logger logger = LoggerFactory.getLogger(ArquivoController.class);

    @Autowired
    private TransacaoRepository transacaoRepository;

    @RequestMapping(value = SOMA_POR_OPERACAO_POR_LOJA, method = RequestMethod.GET)
    public ResponseEntity<List<TotalOperacaoLojaDto>> somaPorOperacaoPorLoja(@PathVariable long idLoja) {
        final List<TotalOperacaoLojaDto> retorno = new ArrayList<>();
        try {
            final List<Object[]> lista = transacaoRepository.somaPorOperacaoPorLoja(idLoja);
            for (Object[] registro : lista) {
                retorno.add(TotalOperacaoLojaDto.getObject(registro));
            }
            return ResponseEntity.ok(retorno);
        } catch (Exception e) {
            logger.error("Erro ao somar as operações por loja", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
