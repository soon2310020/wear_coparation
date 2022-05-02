package com.example.wear_shop.service.impl;

import com.example.wear_shop.data.Entity.New;
import com.example.wear_shop.repo.NewRepository;
import com.example.wear_shop.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NewServiceImpl implements NewService {
@Autowired
private NewRepository newRepository;

    @Override
    public Page<New> searchNew(String name, Pageable pageable) {
        Page<New> news = newRepository.getPage(name,pageable);
        return news;
    }
}
