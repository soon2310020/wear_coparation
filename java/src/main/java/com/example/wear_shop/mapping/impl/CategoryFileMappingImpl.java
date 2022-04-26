package com.example.wear_shop.mapping.impl;

import com.example.wear_shop.data.Entity.Category;
import com.example.wear_shop.data.Entity.Product;
import com.example.wear_shop.mapping.CategoryFileMapping;
import com.example.wear_shop.repo.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;
@Component
public class CategoryFileMappingImpl implements CategoryFileMapping {
    @Autowired
    private FileRepository fileRepository;
    @Override
    public void mappingFileForProduct(List<Product> products) {

        for (Product p: products) {
            if (p.getFile()!=null&&!p.getFile().isEmpty())
            {
                List<String> base64s = new ArrayList<String>();
                String fileString =p.getFile();
                String [] files = fileString.trim().split(" ");
                for (String fileId: files) {
                    Long id = Long.parseLong(fileId);
                    String base64 = fileRepository.getBase64ForFile(id);
                    base64s.add(base64);
                }
                p.setBase64File(base64s);
            }

        }
    }

    @Override
    public List<Category> mappingFileForCategory(List<Category> categories) {
        for (Category c: categories) {
            if (c.getFile()!=null&&!c.getFile().isEmpty())
            {
                List<String> base64s = new ArrayList<String>();
                String fileString =c.getFile();
                String [] files = fileString.trim().split(" ");
                    Long id = Long.parseLong(files[0]);
                    String base64 = fileRepository.getBase64ForFile(id);
                    base64s.add(base64);

                c.setBase64File(base64s);
            }

        }
        return categories;
    }
}
