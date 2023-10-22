package com.example.diploma.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileResponse{
    private String fileName;
    private Long size;

    public FileResponse(String fileName, Long size) {
        this.fileName = fileName;
        this.size = size;
    }
}
