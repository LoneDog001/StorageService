package com.example.diploma.services;

import com.example.diploma.dto.FileResponse;
import com.example.diploma.entities.File;
import com.example.diploma.exception.FileNameEmptyException;
import com.example.diploma.exception.FileNotExistsException;
import com.example.diploma.repositories.FileRepository;
import com.example.diploma.security.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StorageServiceTest {
    @InjectMocks
    private StorageService storageService;
    @Mock
    FileRepository fileRepository;
    @Mock
    JwtTokenUtils jwtTokenUtils;
    private final File file = new File();
    private final List<File> fileList = new ArrayList<>();
    private final String HOLDER = "holder";
    private final String FILENAME = "filename";

    @Test
    void getFilesTest() {
        String token = UUID.randomUUID().toString();
        int limit = 1;
        file.setFileName(FILENAME);
        fileList.add(file);

        given(jwtTokenUtils.getUserNameFromToken(token)).willReturn(HOLDER);
        given(fileRepository.findAllByHolder(HOLDER)).willReturn(Optional.of(fileList));

        List<FileResponse> responseList = storageService.getFiles(token, limit);

        assertEquals(responseList.get(0).getFileName(), file.getFileName());
    }

    @Test
    void deleteFileTest(){
        String token = UUID.randomUUID().toString();

        given(jwtTokenUtils.getUserNameFromToken(token)).willReturn(HOLDER);

        assertThrows(FileNotExistsException.class, ()-> storageService.deleteFile(token, FILENAME));
    }

    @Test
    void downloadFileTest() {
        String token = UUID.randomUUID().toString();
        file.setFileName(FILENAME);

        given(jwtTokenUtils.getUserNameFromToken(token)).willReturn(HOLDER);
        given(fileRepository.findByFileNameAndHolder(FILENAME, HOLDER)).willReturn(file);

        File newFile = storageService.downloadFile(token, FILENAME);

        assertEquals(file.getFileName(), newFile.getFileName());
    }

    @Test
    void renameFileTest() {
        String token = UUID.randomUUID().toString();

        given(jwtTokenUtils.getUserNameFromToken(token)).willReturn(HOLDER);

        assertThrows(FileNotExistsException.class, ()-> storageService.renameFile(token, FILENAME, FILENAME));
        assertThrows(FileNameEmptyException.class, ()-> storageService.renameFile(token, null, FILENAME));
    }
}
