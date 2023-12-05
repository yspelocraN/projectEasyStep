package com.work.easystep2.REPO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	Admin	findAdminByAdminAcct(String acct);
	
	Admin	findAdminByAdminAcctAndAdminPwd(String acct, String pwd);
	
	void	deleteAdminByAdminAcct(String acct);
	
}
