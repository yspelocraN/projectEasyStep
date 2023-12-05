package com.work.easystep2.REPO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.work.easystep2.model.VenderTop10;

public interface VenderTop10Repository extends JpaRepository<VenderTop10, Integer>{

    @Query(value = "from VenderTop10 vt join vt.zip zip where zip.zipno = ?1")
    List<VenderTop10> findVenderByZipNo(String zipno);


    @Query(value = "from VenderTop10 vt join vt.zip zip where zip.city = ?1")
    List<VenderTop10> findVenderByZipCity(String city);


    @Query(value = "from VenderTop10 vt join vt.zip zip where zip.dist = ?1")
    List<VenderTop10> findVenderByZipDist(String dist);


    VenderTop10 findByVenderId(Integer venderid);

}
