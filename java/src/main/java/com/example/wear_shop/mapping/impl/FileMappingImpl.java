package com.example.wear_shop.mapping.impl;

import com.example.wear_shop.data.DTO.fileDTO.FileDto;
import com.example.wear_shop.data.DTO.fileDTO.FileFormDto;
import com.example.wear_shop.data.Entity.FileEntity;
import com.example.wear_shop.mapping.FileMapping;
import org.springframework.stereotype.Component;

@Component
public class FileMappingImpl implements FileMapping {

    @Override
    public FileDto mappingEntityToDto(FileEntity entity) {
        FileDto dto = new FileDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setContent(entity.getContent());
        dto.setFilePath(entity.getFilePath());
        dto.setExtendsion(entity.getExtension());
        dto.setUrl(entity.getUrl());
        dto.setContentType(entity.getContentType());

        return dto;
    }

    @Override
    public FileEntity mappingFormDtoToEntity(FileFormDto formDto) {
        FileEntity entity = new FileEntity();

        entity.setName(formDto.getName());
        entity.setContent(formDto.getContent());
        entity.setFilePath(formDto.getFilePath());
        entity.setExtension(formDto.getExtendsion());
        entity.setUrl(formDto.getUrl());
        entity.setContentType(formDto.getContentType());

        return entity;
    }
}
