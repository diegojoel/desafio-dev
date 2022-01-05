package pilger.diego.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pilger.diego.api.dtos.LojaSaldoDto;
import pilger.diego.api.models.Loja;
import pilger.diego.api.repositories.LojaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = LojaController.REQUEST_MAPPING)
public class LojaController {

    private static Logger logger = LoggerFactory.getLogger(LojaController.class);

    static final String REQUEST_MAPPING = "/loja";

    @Autowired
    private LojaRepository lojaRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<LojaSaldoDto>> lojasComSaldo(@RequestParam Map<String, String> filtros) {
        final List<LojaSaldoDto> retorno = new ArrayList<>();

        long fromId = 0;
        try {
            fromId = Integer.parseInt(filtros.get("fromId"));
        } catch (Exception ignored) {
        }

        int pageSize = 50;
        try {
            pageSize = Integer.parseInt(filtros.get("pageSize"));
        } catch (Exception ignored) {
        }

        try {
            final String nomeLoja = lojaRepository.findById(fromId).orElse(new Loja()).getNome();
            final List<Object[]> lista = lojaRepository.findProximos50ComSaldo(Objects.toString(nomeLoja, ""), pageSize);

            for (Object[] registro : lista) {
                retorno.add(LojaSaldoDto.getObject(registro));
            }
            return ResponseEntity.ok(retorno);
        } catch (Exception e) {
            logger.error("Erro ao tentar o saldo das lojas", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
