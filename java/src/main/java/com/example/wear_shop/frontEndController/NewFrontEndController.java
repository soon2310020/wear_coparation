package com.example.wear_shop.frontEndController;

import com.example.wear_shop.data.Entity.New;
import com.example.wear_shop.repo.NewRepository;
import com.example.wear_shop.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/front-end/new")
public class NewFrontEndController {
    @Autowired
    private NewRepository newRepository;
    @Autowired
    private NewService newService;
    @GetMapping("/search")
    public ResponseEntity<Page<New>> getPage(@RequestParam(value = "name",required = false) String name , Pageable pageable)
    {
        return ResponseEntity.ok(newService.searchNew(name,pageable));

    }
    @GetMapping("/detail")
    public ResponseEntity<New> getNew(@RequestParam("id") Long id)
    {
        return ResponseEntity.ok(newRepository.getById(id));
    }

}
