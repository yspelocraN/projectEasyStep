package com.work.easystep2.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.easystep2.model.Vender;
import com.work.easystep2.model.VenderToken;
import com.work.easystep2.REPO.VenderTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class VenderTokenService {

	@Autowired
	private VenderTokenRepository tokeRep;
	
	
	@Transactional
	public VenderToken findByToken(String token){
		return tokeRep.findByToken(token);
	}
	
	
	@Transactional
	public VenderToken findByVender(Vender vender) {
		return tokeRep.findByVender(vender);
	}
	
	
	@Transactional
	public VenderToken save(Vender vender,String token) {//新增token
		VenderToken newtoken=new VenderToken(token,vender);
		//設定到期日截止為24小時
		newtoken.setExpiryDate(calculateExpiryDate(24*60));
		System.out.println(newtoken.getToken());
		return tokeRep.save(newtoken);//可能有錯
	}
	
	//calculate expiry date計算到期日
	private Date calculateExpiryDate(int expiryMinutes) {
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MINUTE,expiryMinutes);
		return new Date(cal.getTime().getTime());
	}

}
