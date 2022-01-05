package pilger.diego.client.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {

    private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${pilger.diego.client.api-hostname}")
    private String apiHostname;

    @PostMapping("/uploadForm")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<Object> result = restTemplate.postForEntity("http://" + apiHostname + "/api/file/upload", file.getBytes(), Object.class);
            if (result.getStatusCodeValue() == 200) {
                redirectAttributes.addFlashAttribute("message",
                        "Arquivo enviado com sucesso! Filename: " + file.getOriginalFilename());
                return "redirect:/";
            }
        } catch (Exception e) {
            logger.error("Erro ao tentar fazer upload", e);
        }

        redirectAttributes.addFlashAttribute("message",
                "Erro ao tentar enviar arquivo!");

        return "redirect:/uploadForm";
    }

    @GetMapping("/uploadForm")
    public String uploadForm() {
        return "uploadForm";
    }
}
