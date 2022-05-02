package com.example.wear_shop.frontEndController;

import com.example.wear_shop.data.Entity.Communue;
import com.example.wear_shop.data.Entity.District;
import com.example.wear_shop.data.Entity.Province;
import com.example.wear_shop.repo.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {
    @Autowired
    private ProvinceRepository provinceRepository;


    @GetMapping("/address-list")
    public ResponseEntity<List<Province>> getListProvince() {

        List<Province> provinces = provinceRepository.findAll();
        return ResponseEntity.ok(provinces);


    }

    @GetMapping("/address-district")
    public ResponseEntity<List<District>> getDistrict(@RequestParam List<Long> proIds) {
        return ResponseEntity.ok(provinceRepository.findAllDistrict(proIds));
    }

    @GetMapping("/province")
    public ResponseEntity<String> getCommunue(@RequestParam Long proId) {
        return ResponseEntity.ok(provinceRepository.findById(proId).orElse(null).getProvinceName());
    }
}
