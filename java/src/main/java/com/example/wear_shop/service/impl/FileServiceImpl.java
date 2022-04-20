package com.example.wear_shop.service.impl;

import com.example.wear_shop.data.DTO.fileDTO.FileDto;
import com.example.wear_shop.data.DTO.fileDTO.FileFormDto;
import com.example.wear_shop.data.Entity.FileEntity;
import com.example.wear_shop.mapping.FileMapping;
import com.example.wear_shop.repo.FileRepository;
import com.example.wear_shop.service.FileService;
import com.example.wear_shop.util.DateUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Value("${app.uploadFileDir}")
    private String uploadFileDir;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepository;

    private final FileMapping fileMapping;

    @Autowired
    public FileServiceImpl(
            FileRepository fileRepository,
            FileMapping fileMapping
    ) {
        this.fileRepository = fileRepository;
        this.fileMapping = fileMapping;
    }

    @Override
    public void deleteFileById(Long id) throws FileNotFoundException {
        LOGGER.debug("Rest request to delete File by {} " ,id);
        Optional<FileEntity> fileOptional = this.fileRepository.findById(id);

        if (!fileOptional.isPresent()) {
            throw new FileNotFoundException("File by id " + id + " was not found.");
        } else {
            FileEntity file = fileOptional.get();
            File tmp = new File(file.getFilePath());

            if (!tmp.delete()) LOGGER.debug("Failed to delete file {}", file.getFilePath());
            this.fileRepository.deleteById(id);
        }
    }

    @Override
    public FileDto uploadFile(FileFormDto uploadFormDto) throws IOException {
        LOGGER.debug("Rest request to upload File : {}", uploadFormDto);

        String dirPath = this.uploadFileDir + File.separator
                            + DateUtils.now("yyyyMMdd") + File.separator
                            + DateUtils.now("HHmmss");
        File dir = new File(dirPath);
        if(!dir.mkdirs()) LOGGER.debug("Failed to create folder {}", dirPath);

        String savedFilePath = dirPath + File.separator + uploadFormDto.getName();
        File file = new File(savedFilePath);
        file.createNewFile();
        if (uploadFormDto.getContent() != null) {
            org.apache.commons.io.FileUtils.writeByteArrayToFile(file, uploadFormDto.getContent());
        }
        uploadFormDto.setContent(null);
        uploadFormDto.setFilePath(savedFilePath);
        FileEntity entity = this.fileMapping.mappingFormDtoToEntity(uploadFormDto);

        return this.fileMapping.mappingEntityToDto(this.fileRepository.save(entity));
    }

    @Override
    public List<Long> uploadFile(List<FileFormDto> uploadFormDtoList) throws IOException {
        List<Long> fileIdList = new ArrayList<Long>();

        for (FileFormDto formDto : uploadFormDtoList) {
            FileEntity file = this.fileRepository.save(this.fileMapping.mappingFormDtoToEntity(formDto));
            fileIdList.add(file.getId());
        }

        return fileIdList;
    }

    @Override
    @Transactional(readOnly = true)
    public FileDto getFileById(Long id) throws IOException {
        LOGGER.debug("Rest request to get File by id {}", id);

        Optional<FileEntity> entityOptional = this.fileRepository.findById(id);

        if (!entityOptional.isPresent()) {
            throw new FileNotFoundException("File by id " + id + " was not found");
        }

        FileDto fileDto = this.fileMapping.mappingEntityToDto(entityOptional.get());
        File file = new File(fileDto.getFilePath());
        if (!file.exists()) {
            throw new FileNotFoundException("File by id " + id +
                    " was not found in path " + fileDto.getFilePath());
        }

        byte[] content = FileUtils.readFileToByteArray(file);
        fileDto.setContent(content);

        return fileDto;
    }

}
