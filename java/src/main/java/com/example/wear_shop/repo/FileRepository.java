package com.example.wear_shop.repo;

import com.example.wear_shop.data.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    @Query("select distinct f.content from FileEntity f where f.id = :id ")
    String getBase64ForFile(@Param("id") Long id );

}
