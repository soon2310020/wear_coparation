package com.example.wear_shop.repo;

import com.example.wear_shop.data.Entity.Communue;
import com.example.wear_shop.data.Entity.District;
import com.example.wear_shop.data.Entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    @Query("select d from Province d " +
            "where d.provinceId = :id ")
    Province findByID(@Param("id") Long id);

//    @Query("select distinct p from Province p left outer join p.districts d left outer join d.communues c where " +
//            "(coalesce(:pid) is null or p.id in :pid) and" +
//            "(coalesce(:did) is null or d.id in :did) and " +
//            "(coalesce(:cid) is null or c.id in :cid) and p.isDeleted = 0 " +
//            "and d.isDeleted = 0 and c.isDeleted = 0")
//    List<Province> findAll(@Param("pid") List<Long> provinceIds,
//                           @Param("did") List<Long> districtIds,
//                           @Param("cid") List<Long> comunuesIds);
//    @Query("select distinct p from Province p left outer  join p.districts d left outer join d.communues c where " +
//            "(coalesce(:pid) is null or p.id in :pid) and " +
//            "(coalesce(:did) is null or d.id in :did ) and " +
//            "(coalesce(:cid) is null or c.id in :cid) and p.isDeleted <> 1 ")
//    List<Province> findAll(@Param("pid") List<Long> provinceIds,
//                           @Param("did") List<Long> districtIds,

    @Query("select distinct d from Province p join p.districts d where (coalesce(:proIds) is null or p.provinceId in :proIds ) " +
            "and d.isDelete <> 1")

    List<District> findAllDistrict(@Param("proIds") List<Long> proIds);

    @Query("select  distinct c from Province p join p.districts d join d.communues c " +
            "where (coalesce(:proIds) is null or p.provinceId in :proIds ) and " +
            "(coalesce(:disIds) is null or d.districtId in :disIds ) and c.isDelete <> 1")
    List<Communue> findAllComunue(@Param("disIds") List<Long> disIds,
                                  @Param("proIds") List<Long> proIds);
    @Query("select distinct p from Province p where p.isDelete <> 1")
    List<Province> findAll();

}
