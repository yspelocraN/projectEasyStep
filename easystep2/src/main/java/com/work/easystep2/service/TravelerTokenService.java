package com.work.easystep2.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.TravelerToken;
import com.work.easystep2.REPO.TravelerTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class TravelerTokenService {

    @Autowired
    private TravelerTokenRepository tokeRep;

    @Transactional
    public TravelerToken findByToken(String token) {
        return tokeRep.findByToken(token);
    }

    @Transactional
    public TravelerToken findByTraveler(Traveler traveler) {
        return tokeRep.findByTraveler(traveler);
    }

    @Transactional
    public TravelerToken save(Traveler traveler, String token) {//新增token
        TravelerToken newtoken = new TravelerToken(token, traveler);
        //設定到期日截止為24小時
        newtoken.setExpiryDate(calculateExpiryDate(24 * 60));
        System.out.println(newtoken.getToken());
        return tokeRep.save(newtoken);//可能有錯
    }

    //calculate expiry date計算到期日
    private Date calculateExpiryDate(int expiryMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryMinutes);
        return new Date(cal.getTime().getTime());
    }
	
}
