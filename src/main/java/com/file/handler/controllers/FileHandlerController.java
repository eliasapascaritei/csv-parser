package com.file.handler.controllers;

import com.file.handler.services.FileHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileHandlerController {

    private final FileHandlerService fileHandlerService;

    @Autowired
    public FileHandlerController(FileHandlerService fileHandlerService) {
        this.fileHandlerService = fileHandlerService;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Hello Stratify");
    }

    @PostMapping(path = "/upload", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> upload(@RequestPart MultipartFile file) throws IOException {
        fileHandlerService.uploadCsv(file);
        return ResponseEntity.ok("Csv file uploaded");
    }

}

