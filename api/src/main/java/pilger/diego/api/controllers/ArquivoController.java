package pilger.diego.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pilger.diego.api.services.FileService;
import pilger.diego.api.utils.FileParseResultEnum;

@RestController
@RequestMapping(value = ArquivoController.REQUEST_MAPPING)
public class ArquivoController {

    static final String REQUEST_MAPPING = "/file";
    private static final String UPLOAD = "/upload";

    private FileService fileService;

    @Autowired
    public ArquivoController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value = UPLOAD, method = RequestMethod.POST)
    public ResponseEntity<String> upload(@RequestBody final byte[] arquivo) {
        if (arquivo.length == 0) return ResponseEntity.badRequest().build();

        FileParseResultEnum parseResult = fileService.parseAndSave(arquivo);
        if (parseResult != FileParseResultEnum.OK) {
            return ResponseEntity.badRequest().body(parseResult.toString());
        }
        return ResponseEntity.ok().build();
    }
}
