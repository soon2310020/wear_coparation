package com.example.wear_shop.mapping.impl;

import com.example.wear_shop.data.Entity.Category;
import com.example.wear_shop.data.Entity.Product;
import com.example.wear_shop.mapping.CategoryFileMapping;
import com.example.wear_shop.repo.FileRepository;
import com.example.wear_shop.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class CategoryFileMappingImpl implements CategoryFileMapping {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileService fileService;
    @Override
    public void mappingFileForProduct(List<Product> products) throws IOException {

        for (Product p: products) {
            if (p.getFile()!=null&&!p.getFile().isEmpty())
            {
                List<byte[]> base64s = new ArrayList<byte[]>();
                String fileString =p.getFile();
                String [] files = fileString.trim().split(" ");
                for (String fileId: files) {
                    Long id = Long.parseLong(fileId);
                    byte[] base64 = fileService.getFileById(id).getContent();
                    base64s.add(base64);
                }
                p.setBase64File(base64s);
            }

        }
    }

    @Override
    public List<Category> mappingFileForCategory(List<Category> categories) throws IOException {
        for (Category c: categories) {
            if (c.getFile()!=null&&!c.getFile().isEmpty())
            {
                List<byte[]> base64s = new ArrayList<byte[]>();
                String fileString =c.getFile();
                String [] files = fileString.trim().split(" ");
                    Long id = Long.parseLong(files[0]);
                    byte[] base64 = fileService.getFileById(id).getContent();
                    base64s.add(base64);

                c.setBase64File(base64s);
            }

        }
        return categories;
    }
}
