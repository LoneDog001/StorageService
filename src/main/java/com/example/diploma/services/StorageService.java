package com.example.diploma.services;

import com.example.diploma.dto.FileResponse;
import com.example.diploma.entities.File;
import com.example.diploma.exception.FileNameEmptyException;
import com.example.diploma.exception.FileNotExistsException;
import com.example.diploma.repositories.FileRepository;
import com.example.diploma.security.JwtTokenUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StorageService{
    private final FileRepository fileRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public StorageService(FileRepository fileRepository, JwtTokenUtils jwtTokenUtils) {
        this.fileRepository = fileRepository;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public List<FileResponse> getFiles(String authToken, int limit){
        String holder = jwtTokenUtils.getUserNameFromToken(authToken);
        List<File> fileList = fileRepository.findAllByHolder(holder).get();
        return fileList.stream()
                .map(fr-> new FileResponse(fr.getFileName(), fr.getSize()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void uploadFile(String authToken, String fileName, MultipartFile file) throws IOException {
        String holder = jwtTokenUtils.getUserNameFromToken(authToken);
        validFile(fileName, holder);
        fileRepository.save(new File(fileName, file.getContentType(), file.getSize(), file.getBytes(), holder));
    }

    public void deleteFile(String authToken, String fileName){
        String holder = jwtTokenUtils.getUserNameFromToken(authToken);
        validFile(fileName, holder);
        fileRepository.removeByFileNameAndHolder(fileName, holder);
    }

    public File downloadFile(String authToken, String fileName){
        String holder = jwtTokenUtils.getUserNameFromToken(authToken);
        validFile(fileName, holder);
        return fileRepository.findByFileNameAndHolder(fileName, holder);
    }

    public void renameFile(String authToken, String fileName, String newFileName){
        String holder = jwtTokenUtils.getUserNameFromToken(authToken);
        validFile(fileName, holder);
        validFile(newFileName, holder);
        fileRepository.renameFile(fileName, newFileName, holder);
    }

    private void validFile(String fileName, String holder) {
        if (fileName == null || fileName.isEmpty()) {
            throw new FileNameEmptyException("Filename is empty");
        }
        File file = fileRepository.findByFileNameAndHolder(fileName, holder);
        if(file == null){
            throw new FileNotExistsException("File not found");
        }
    }
}