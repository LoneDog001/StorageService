package com.example.diploma.controllers;

import com.example.diploma.dto.FileResponse;
import com.example.diploma.entities.File;
import com.example.diploma.services.StorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class StorageController {
    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/list")
    public List<FileResponse> getAllFile(@RequestHeader("auth-token") String authToken,
                                         @RequestParam("limit") int limit) {
        return storageService.getFiles(authToken, limit);
    }

    @PostMapping("/file")
    public void uploadFile(@RequestHeader("auth-token") String authToken,
                           @RequestParam("files") String fileName,
                           @RequestBody MultipartFile file) throws IOException {
        storageService.uploadFile(authToken, fileName, file);
    }

    @PutMapping("/file")
    public void renameFile(@RequestHeader("auth-token") String authToken,
                           @RequestParam("files") String fileName,
                           @RequestBody Map<String, String> fileNameRequest) {
        storageService.renameFile(authToken, fileName, fileNameRequest.get("fileName"));
    }

    @DeleteMapping("/file")
    public void deleteFile(@RequestHeader("auth-token") String authToken,
                           @RequestParam("fileName") String fileName) {
        storageService.deleteFile(authToken, fileName);
    }

    @GetMapping("/file")
    public byte[] downloadFile(@RequestHeader("auth-token") String authToken,
                               @RequestParam("fileName") String fileName) {
        File file = storageService.downloadFile(authToken, fileName);
        return file.getContent();
    }
}