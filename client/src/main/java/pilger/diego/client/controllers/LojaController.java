package pilger.diego.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class LojaController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${pilger.diego.client.api-hostname}")
    private String apiHostname;

    @GetMapping("/")
    public String lojasForm(Model model, @Nullable @RequestParam String fromId) {
        if (fromId != null && fromId.trim().length() > 0) {
            fromId = "&fromId=" + fromId;
        }
        ResponseEntity<List> result = restTemplate.getForEntity("http://" + apiHostname + "/api/loja?pageSize=2" + Objects.toString(fromId, ""), List.class);
        List lista = new ArrayList<>();
        if (result.getStatusCodeValue() == 200) {
            lista = result.getBody();
        }
        model.addAttribute("lojas", lista);

        int ultimoId = 0;
        if (lista != null && !lista.isEmpty()) {
            try {
                ultimoId = Integer.parseInt(((Map) lista.get(lista.size() - 1)).get("id") + "");
            } catch (Exception ignored) {
            }
        }
        model.addAttribute("ultimoId", ultimoId);

        return "/lojas";
    }

}
