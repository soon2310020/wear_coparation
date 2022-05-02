package com.example.wear_shop.controller;

import com.example.wear_shop.data.Entity.New;
import com.example.wear_shop.repo.NewRepository;
import com.example.wear_shop.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/new")
public class NewController {
    @Autowired
    private NewRepository newRepository;
    @Autowired
    private NewService newService;
    @PostMapping("/")
    public void addNews(@RequestBody New news)
    {

        news.setUpdateAt(Instant.now());
        newRepository.save(news);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<New>> getPage(@RequestParam(value = "name",required = false) String name , Pageable pageable)
    {
        return ResponseEntity.ok(newService.searchNew(name,pageable));

    }
    @GetMapping("/{id}")
    public ResponseEntity<New> getNew(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(newRepository.getById(id));
    }
    @DeleteMapping("/delete/{id}")
    public void deleteNew(@PathVariable("id") Long id)
    {
         Long i = id;
         newRepository.deleteById(i);
    }
}
