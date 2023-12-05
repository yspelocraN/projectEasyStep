package com.work.easystep2.controller;

import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.easystep2.model.Admin;
import com.work.easystep2.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/admin")
@CrossOrigin
public class AdminController {
	
	private static final String	adminAcctStr	= "adminAcct";
	private static final String	adminPwdStr		= "adminPwd";
	
	Admin			admin;
	
	@Autowired
	AdminService	adminServ;
	
	String	adminAcct	= "";
	String	adminPwd	= "";
	
	JSONObject		request		= new JSONObject();
	JSONObject		response	= new JSONObject();
	
	
	/* Administrator */
	/* (C)reate */
	@PostMapping("/createAdmin")
	public boolean createAdmin(@RequestBody String req) {
		return adminServ.createAdmin(req);
	}
	
	/* (R)ead */
	@PostMapping("/login")
	public boolean login(@RequestBody String req, HttpSession session) {
		request		= new JSONObject(req);
		adminAcct	= request.getString(adminAcctStr);
		adminPwd	= request.getString(adminPwdStr);
		admin		= adminServ.chkLogin(adminAcct, adminPwd);
		
		if (admin != null) {
			return true;
		}
		return false;
	}
	
//	@GetMapping("/chkLogin")
//	public boolean chkLogin(HttpSession session) {
//		adminDTO	= (AdminDTO)session.getAttribute(aDto);
//		
//		if (adminDTO != null) {
//			adminAcct	= adminDTO.getAdminAcct();
//			adminPwd	= adminDTO.getAdminPwd();
//			admin		= adminServ.chkLogin(adminAcct, adminPwd);
//			
//			if (admin != null) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	@GetMapping("/logout")
	public boolean logout(HttpServletRequest request) {
		HttpSession session	= request.getSession(false);
		
		if (session != null) {
			session.invalidate();
		}
		return true;
	}
	
	/* (U)pdate */
	@PostMapping("/editAdmin")
	public boolean editAdmin(@RequestBody String req, HttpSession session) {
		return adminServ.editAdminPwd(req);
	}
	
	/* (D)elete */
//	@PostMapping("/deleteAdmin")
//	public boolean deleteAdmin(@RequestBody String req, HttpSession session) {
//		adminDTO	=	(AdminDTO)session.getAttribute(aDto);
//		
//		return adminServ.deleteAdmin(req, adminDTO.getAdminLv());
//	}
	
	
	
	
	/* Vender info */
	@GetMapping("/venders")
	public String listAllVender() {
		return adminServ.findAllVender();
    }
	
	@DeleteMapping("/deleteVender/{vId}")
	public void deleteVender(@PathVariable("vId") Integer vId, HttpSession session) {
		adminServ.deleteVenderById(vId);
	}
	
	@GetMapping("/suspendVender/{vId}")
	public boolean suspendVender(@PathVariable("vId") Integer vId, HttpSession session) {
		return adminServ.suspendVenderAcct(vId);
	}
	
	@GetMapping("/restoreVender/{vId}")
	public boolean restoreVender(@PathVariable("vId") Integer vId, HttpSession session) {
		return adminServ.restoredVenderAcct(vId);
	}
	
	
	
	
	/* Traveler info */
	@GetMapping("/travelers")
	public String listAllTraveler() {
		return adminServ.findAllTraveler();
	}
	
	@DeleteMapping("/deleteTraveler/{tId}")
	public void deleteTraveler(@PathVariable("tId") Integer tId, HttpSession session) {
		adminServ.deleteTravelerById(tId);
	}
	
	@GetMapping("/suspendTraveler/{tId}")
	public boolean suspendTraveler(@PathVariable("tId") Integer tId, HttpSession session) {
		return adminServ.suspendTravelerAcct(tId);
	}
	
	@GetMapping("/restoreTraveler/{tId}")
	public boolean restoreTraveler(@PathVariable("tId") Integer tId, HttpSession session) {
		return adminServ.restoredTravelerAcct(tId);
	}
	
	
	
	
	/* Travel tip */
	/* (C)reate */
	@PostMapping("/createTip")
	public boolean createTip(@RequestBody String req, HttpSession session) {
		return adminServ.tipAdd(req);
	}
	
	/* (R)ead */
	@GetMapping("/tips")
	public String tips() {
		return adminServ.findAllTip();
	}
	
	@GetMapping("/tip/{id}")
	public String listTipById(@PathVariable("id") Integer id) {
		return adminServ.findById(id);
	}
	
	/* (U)pdate */
	@PostMapping("/editTip")
	public boolean editTip(@RequestBody String req, HttpSession session) {
		return adminServ.tipEdit(req);
	}
	
	@DeleteMapping("/deleteTip/{id}")
	public void deleteTip(@PathVariable("id") Integer id, HttpSession session) {
		adminServ.tipDeleteById(id);
	}
	
}
