package com.example.wear_shop.repo;

import com.example.wear_shop.data.Entity.New;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewRepository extends JpaRepository<New,Long> {
    @Query("select distinct n from New n where :name = '' or n.title like %:name% ")
    public Page<New> getPage(@Param("name") String name, Pageable pageable);

}
