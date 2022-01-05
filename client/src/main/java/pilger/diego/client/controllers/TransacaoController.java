package pilger.diego.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class TransacaoController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${pilger.diego.client.api-hostname}")
    private String apiHostname;

    @GetMapping("/transacoes")
    public String lojasForm(Model model, @RequestParam String id) {
        ResponseEntity<List> result = restTemplate.getForEntity("http://" + apiHostname + "/api/transacao/soma-por-operacao-por-loja/" + id, List.class);

        model.addAttribute("transacoes", result.getBody());
        return "transacoes";
    }

}
