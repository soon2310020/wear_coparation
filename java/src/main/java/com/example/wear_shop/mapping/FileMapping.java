package com.example.wear_shop.mapping;

import com.example.wear_shop.data.DTO.fileDTO.FileDto;
import com.example.wear_shop.data.DTO.fileDTO.FileFormDto;
import com.example.wear_shop.data.Entity.FileEntity;
public interface FileMapping {

    FileDto mappingEntityToDto(FileEntity entity);
    FileEntity mappingFormDtoToEntity(FileFormDto formDto);
}
