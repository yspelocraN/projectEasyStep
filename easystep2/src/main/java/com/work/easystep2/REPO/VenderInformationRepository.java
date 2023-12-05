package com.work.easystep2.REPO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.work.easystep2.model.VenderInformation;

public interface VenderInformationRepository extends JpaRepository<VenderInformation, Integer> {
	
	@Query(value = "select * from vender_information where vender_venderid != :n order by write_update desc", nativeQuery = true)
	List<VenderInformation> otherVenders(@Param(value = "n")Integer venderId);

}
