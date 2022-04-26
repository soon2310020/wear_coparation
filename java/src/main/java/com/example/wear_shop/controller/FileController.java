package com.example.wear_shop.controller;

import com.example.wear_shop.data.DTO.fileDTO.FileDto;
import com.example.wear_shop.data.DTO.fileDTO.FileFormDto;
import com.example.wear_shop.service.FileService;
import com.example.wear_shop.util.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/file")
public class FileController {


//    private static final String ENTITY_NAME = "File";

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    private ServletContext servletContext;

    @PostMapping("/upload")
    public ResponseEntity<FileDto> uploadFile(
            @Valid @RequestBody FileFormDto uploadFormDto
    ) throws URISyntaxException, IOException {
        FileDto dto = this.fileService.uploadFile(uploadFormDto);

        if (dto.getId() == null) dto.setId(0L);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDto> getFileById(@PathVariable Long id) throws IOException {
        FileDto fileDto = this.fileService.getFileById(id);

        return ResponseEntity.ok(fileDto);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFileById(@PathVariable Long id) throws IOException {
        FileDto fileDto = this.fileService.getFileById(id);
        File file = new File(fileDto.getFilePath());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(fileDto.getName(), this.servletContext);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="
                        + URLEncoder.encode(fileDto.getName(), StandardCharsets.UTF_8.toString()).replace("+", "%20"))
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) throws FileNotFoundException {
        this.fileService.deleteFileById(id);
    }

}
