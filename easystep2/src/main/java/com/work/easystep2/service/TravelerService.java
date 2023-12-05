package com.work.easystep2.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.work.easystep2.model.Traveler;
import com.work.easystep2.REPO.TravelerRepository;
import com.work.easystep2.model.TravelerToken;

import jakarta.mail.MessagingException;

@Service
public class TravelerService {

    @Autowired
    private TravelerRepository trlRepo;

    @Autowired
    private PasswordEncoder pwdEncoder;

    @Autowired
    private TravelerTokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RandomStringGenerator randomService;

    // 新增會員（含加密）>google不寄信
    public Traveler addTraveler3(Traveler traveler) throws MessagingException {
        String encoderPwd = pwdEncoder.encode(traveler.getPwd());
        traveler.setPwd(encoderPwd);
        traveler.setEnable(true);
        Optional<Traveler> saved = Optional.of(trlRepo.save(traveler));
        if (saved.isPresent()) {
            Traveler newtraveler = saved.get();
            return trlRepo.save(newtraveler);
        }

        return null;
    }

    // 登入會員google
    public Traveler googleLogin(String sub) {
        Traveler dbTraveler = trlRepo.findTravelerBySub(sub);
        if (dbTraveler != null) {
            return dbTraveler;
        }
        return null;
    }

    public Traveler findById(Integer id) {
        Optional<Traveler> optional = trlRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public void deleteById(Integer id) {
        trlRepo.deleteById(id);
    }

    // 新增會員（含加密）測試信箱驗證版＿成功
    public Traveler addTraveler2(Traveler traveler) throws MessagingException {
        String encoderPwd = pwdEncoder.encode(traveler.getPwd());
        traveler.setPwd(encoderPwd);
        traveler.setEnable(false);
        Optional<Traveler> saved = Optional.of(trlRepo.save(traveler));
        if (saved.isPresent()) {
            String token = UUID.randomUUID().toString();
            Traveler newtraveler = saved.get();
            TravelerToken newtoken = tokenService.save(newtraveler, token);// 新增token
            newtraveler.setTravelerToken(newtoken);
            newtoken.setTraveler(newtraveler);
            emailService.sendHtmlMail2(newtraveler);// 發送信件(使用發送驗證連結第二種方式)
            return trlRepo.save(newtraveler);
        }

        return null;
    }

    // 新增會員（含加密）
    public Traveler addTraveler(Traveler traveler) {
        String encoderPwd = pwdEncoder.encode(traveler.getPwd());
        traveler.setPwd(encoderPwd);
        return trlRepo.save(traveler);
    }

    // 登入會員（含解密，就是和對合不合）
    public Traveler checkLogin(String acct, String pwd) {
        Traveler dbTraveler = trlRepo.findTravelerByAcct(acct);
        if (dbTraveler != null) {
            // 輸入者和資料庫的
            if (pwdEncoder.matches(pwd, dbTraveler.getPwd())) {
                return dbTraveler;
            }
        }
        return null;
    }

    // 忘記密碼：驗證帳號和信箱寄信
    public Boolean checkpwd(String acct, String email) throws MessagingException {
        Traveler dbTraveler = trlRepo.findTravelerByAcct(acct);
        if (dbTraveler != null && dbTraveler.isEnable() == true) {
            // 輸入者和資料庫的
            if (dbTraveler.getEmail().equals(email)) {
                String newpwd = randomService.generateRandomString(6);
                dbTraveler.setPwd(pwdEncoder.encode(newpwd));
                trlRepo.save(dbTraveler);
                emailService.sendMailPwd(dbTraveler, newpwd);
                ;
                return true;
            }
        }
        return false;
    }

    // 用id找用戶
    public Traveler findTraveler(Integer id) {
        Optional<Traveler> optional = trlRepo.findById(id);
        if (optional.isPresent()) {
            Traveler traveler = optional.get();
            return traveler;
        }

        return null;
    }

    // 確認帳號是否重複(沒有重複回傳＿真)
    public Boolean chkTravelerAcct(String acct) {
        Optional<Traveler> optionl = Optional.ofNullable(trlRepo.findTravelerByAcct(acct));
        if (optionl.isEmpty()) {
            return true;
        }
        return false;
    }

    // 更新會員資料
    public Traveler updateTraveler(Traveler traveler) {
        Optional<Traveler> optional = trlRepo.findById(traveler.getTravelerid());
        if (optional.isPresent()) {
            Traveler newtraveler = optional.get();
            newtraveler.setEcname(traveler.getEcname());
            newtraveler.setMobile(traveler.getMobile());
            newtraveler.setRelationship(traveler.getRelationship());
            newtraveler.setSexabbr(traveler.getSexabbr());
            newtraveler.setTel(traveler.getTel());
            newtraveler.setUsername(traveler.getUsername());
            return trlRepo.save(newtraveler);
        }
        return null;
    }

    // 修改會員密碼（含加密）
    public Traveler updatePwd(Integer id, String pwd, String newPwd) {
        Optional<Traveler> optional = trlRepo.findById(id);
        if (optional.isPresent()) {
            Traveler traveler = optional.get();
            if (pwdEncoder.matches(pwd, traveler.getPwd())) {
                String encoderPwd = pwdEncoder.encode(newPwd);
                traveler.setPwd(encoderPwd);
                return trlRepo.save(traveler);
            }
        }
        return null;
    }

    // 會員密碼是否正確
    public Boolean checkPwd(Integer id, String pwd) {
        Optional<Traveler> optional = trlRepo.findById(id);

        if (optional.isPresent()) {
            Traveler traveler = optional.get();
            // 輸入者和資料庫的
            if (pwdEncoder.matches(pwd, traveler.getPwd())) {
                return true;
            }
        }
        return false;
    }

    // 用ID 找帳號
    public String findacct(Integer travelerId) {
        return trlRepo.findById(travelerId).get().getAcct();
    }

    // 用帳號找那組資料
    public Traveler findByAcct(String traAcct) {
        return trlRepo.findTravelerByAcct(traAcct);
    }
	
}
