package com.work.easystep2.REPO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.Vender;
import com.work.easystep2.model.VenderToken;

public interface VenderTokenRepository extends JpaRepository<VenderToken, Long>{

	VenderToken findByToken(String token);
	VenderToken findByVender(Vender vender);
	
	/* 以使用者ID刪除 */
	void deleteVenderTokenByVenderVenderid(Integer venderId);
	
}
