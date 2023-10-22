package com.example.diploma.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String type;
    private Long size;
    @Lob
    private byte[] content;
    private String holder;

    public File(String fileName, String type, Long size, byte[] content, String holder) {
        this.fileName = fileName;
        this.type = type;
        this.size = size;
        this.content = content;
        this.holder = holder;
    }
}
