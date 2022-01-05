package pilger.diego.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping(value = "/error")
    public String handleError() {
        return "<center><h1>Erro! Falha no processamento.</h1></center>";
    }
}
