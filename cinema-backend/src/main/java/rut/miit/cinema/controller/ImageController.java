package rut.miit.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rut.miit.cinema.dto.ImageDto;
import rut.miit.cinema.service.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = fileService.addImage(new ImageDto(file.getBytes(), file.getContentType(), file.getOriginalFilename()));
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload failed");
        }
    }

    @GetMapping("/{key}")
    public ResponseEntity<byte[]> getImage(@PathVariable String key) {
        ImageDto imageData = fileService.getImage(key);
        if (imageData == null || imageData.getData() == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(imageData.getContentType()));
        headers.setContentLength(imageData.getData().length);
        return new ResponseEntity<>(imageData.getData(), headers, HttpStatus.OK);
    }
}
