package com.work.easystep2.REPO;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.work.easystep2.model.Zip;

public interface ZipRepository extends JpaRepository<Zip, String> {
	
	@Query(value="from Zip where zipno = ?1")//使用者帳戶查詢
	Zip findZipByZipno(String zipno);
	
	@Query("SELECT z FROM Zip z WHERE z.city LIKE %:keyword% OR z.dist LIKE %:keyword%")
	 List<Zip> searchByKeyword(@Param("keyword") String keyword);

}
