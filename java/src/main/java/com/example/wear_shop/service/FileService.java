package com.example.wear_shop.service;

import com.example.wear_shop.data.DTO.fileDTO.FileDto;
import com.example.wear_shop.data.DTO.fileDTO.FileFormDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {

    void deleteFileById(Long id) throws FileNotFoundException;
    FileDto uploadFile(FileFormDto uploadFormDto) throws IOException;
    List<Long> uploadFile (List<FileFormDto> uploadFormDtoList) throws IOException;
    FileDto getFileById(Long id) throws IOException;

}
