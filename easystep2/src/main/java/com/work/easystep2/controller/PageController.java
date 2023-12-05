package com.work.easystep2.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.TravelerToken;
import com.work.easystep2.model.Vender;
import com.work.easystep2.model.VenderToken;
import com.work.easystep2.service.TravelerService;
import com.work.easystep2.service.TravelerTokenService;
import com.work.easystep2.service.VenderService;
import com.work.easystep2.service.VenderTokenService;

@Controller
public class PageController {
	
	@Autowired
	private TravelerTokenService tokenService;
	
	@Autowired
	private TravelerService trlsService;
	
	@Autowired
	private VenderService vedService;

	
	@Autowired
	private VenderTokenService vtokenService;
	
	// 收到信件用網址驗證是否存在（信箱）
	@GetMapping("/Traveler/action")
	public String TactionEmail(@RequestParam String token, Model model) {
		TravelerToken travelerToken=tokenService.findByToken(token);
		if (travelerToken == null) {
			model.addAttribute("emailmessage","信箱驗證無效");
			return "/Traveler/TravalerLog2";
		} else {
			Traveler traveler = travelerToken.getTraveler();
			//預設false
			if (!traveler.isEnable()) {
				//取得目前的時間
				Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
				//驗證取得token是否過期
				if (travelerToken.getExpiryDate().before(currentTimestamp)) {
					model.addAttribute("emailmessage","信箱驗證過期");
					return "/Traveler/TravalerLog2";
				} else {
					//更新
					traveler.setEnable(true);
					trlsService.updateTraveler(traveler);
					model.addAttribute("emailmessage","信箱驗證成功");
					return "/Traveler/TravalerLog2";
				}
			} else {
				model.addAttribute("emailmessage","信箱已被驗證，請登入");
				return "/Traveler/TravalerLog2";
			}
		}
	}

	// 收到信件用網址驗證是否存在（信箱）
	@GetMapping("/Vender/action")
	public String actionEmail(@RequestParam String token, Model model) {
		VenderToken venderToken = vtokenService.findByToken(token);
		if (venderToken == null) {
			model.addAttribute("emailmessage","信箱驗證無效");
			return "/Traveler/TravalerLog2";
		} else {
			Vender vender = venderToken.getVender();
			//預設false
			if (!vender.isEnable()) {
				//取得目前的時間
				Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
				//驗證取得token是否過期
				if (venderToken.getExpiryDate().before(currentTimestamp)) {
					model.addAttribute("emailmessage","信箱驗證過期");
					return "/Traveler/TravalerLog2";
				} else {
					//更新
					vender.setEnable(true);
					vedService.updateVender(vender);
					model.addAttribute("emailmessage","信箱驗證成功");
					return "/Traveler/TravalerLog2";
				}
			} else {
				model.addAttribute("emailmessage","信箱已被驗證，請登入");
				return "/Traveler/TravalerLog2";
			}
		}
	}
	
}
