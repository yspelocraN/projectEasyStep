package com.work.easystep2.service;

import java.util.ArrayList;
import java.util.List;

import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.easystep2.REPO.AdminRepository;
import com.work.easystep2.REPO.AlbumRepository;
import com.work.easystep2.REPO.ArticleRepository;
import com.work.easystep2.REPO.LikeVenderRepository;
import com.work.easystep2.REPO.PhotoReopsitory;
import com.work.easystep2.REPO.TipsRepository;
import com.work.easystep2.REPO.TravelerPerImgRepository;
import com.work.easystep2.REPO.TravelerRepository;
import com.work.easystep2.REPO.TravelerTokenRepository;
import com.work.easystep2.REPO.VenderPerImgRepository;
import com.work.easystep2.REPO.VenderRepository;
import com.work.easystep2.REPO.VenderTokenRepository;
import com.work.easystep2.model.Admin;
import com.work.easystep2.model.PhotoAlbum;
import com.work.easystep2.model.Tips;
import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.Vender;
import jakarta.transaction.Transactional;

@Service
public class AdminService {
	
	private static final String adminAcctStr	= "adminAcct";
	private static final String adminPwdStr		= "adminPwd";
	private static final String tipIdStr		= "tipId";
	private static final String	tipTitleStr		= "title";
	private static final String	tipDetailsStr	= "details";
	
	Integer		id			= null;
	String		adminAcct	= "";
	String		adminPwd	= "";
	String		title		= "";
	String		details		= "";
	
	JSONObject	request		= new JSONObject();
	JSONArray	response	= new JSONArray();
	JSONObject	jElement	= new JSONObject();
	
	private	List<Vender>				venders		= new ArrayList<>();
	@Autowired
	private	VenderRepository			venderRepo;
	@Autowired
	private VenderTokenRepository		venderTokenRepo;
	@Autowired
	private	VenderPerImgRepository		venderPerImgRepo;
	
	private	List<Traveler>					travelers	= new ArrayList<>();
	private	List<PhotoAlbum>				album		= new ArrayList<>();
	@Autowired
	private	TravelerRepository				travelerRepo;
	@Autowired
	private	TravelerTokenRepository			travelerTokenRepo;
	@Autowired
	private	TravelerPerImgRepository		travelerPerImgRepo;
	@Autowired
	private	ArticleRepository				articleRepo;
	@Autowired
	private	AlbumRepository					albumRepo;
	@Autowired
	private	PhotoReopsitory					photoReop;
	
	@Autowired
	private	LikeVenderRepository	likeVenderRepo;
	
	private	List<Tips>		tip	= new ArrayList<>();
	@Autowired
	private	TipsRepository	tipRep;
	
	private	List<Admin>			admin	= new ArrayList<>();
	@Autowired
	private	AdminRepository		adminRepo;
	
	
	
	/* Administrator */
	/* (C)reate */
	public boolean createAdmin(String info) {
		request		= new JSONObject(info);
		adminAcct	= request.getString(adminAcctStr);
		adminPwd	= request.getString(adminPwdStr);
		admin.clear();
		admin.add(adminRepo.findAdminByAdminAcct(adminAcct));
		
		if (admin == null) {
			adminRepo.save(new Admin(adminAcct, adminPwd));
			return true;
		}
		return false;
	}
	
	/* (R)ead */
	public Admin chkLogin(String acct, String pwd) {
		return adminRepo.findAdminByAdminAcctAndAdminPwd(acct, pwd);
	}
	
	/* (U)pdate */
	public boolean editAdminPwd(String info) {
		request		= new JSONObject(info);
		adminAcct	= request.getString(adminAcctStr);
		adminPwd	= request.getString(adminPwdStr);
		admin.clear();
		admin.add(adminRepo.findAdminByAdminAcct(adminAcct));
		
		if (admin != null) {
			if (admin.get(0).getAdminPwd().equals(adminPwd) == false) {
				admin.get(0).setAdminPwd(adminPwd);
				adminRepo.save(admin.get(0));
				return true;
			}
		}
		return false;
	}
	public boolean forgotAdminPwd(String info) {
		request		= new JSONObject(info);
		adminAcct	= request.getString(adminAcctStr);
		adminPwd	= "Admin-123";
		admin.clear();
		admin.add(adminRepo.findAdminByAdminAcct(adminAcct));
		
		if (admin != null) {
			admin.get(0).setAdminPwd(adminPwd);
			adminRepo.save(admin.get(0));
			return true;
		}
		return false;
	}
	
	/* (D)elete */
	@Transactional
	public boolean deleteAdmin(String info, Integer lv) {
		request		= new JSONObject(info);
		adminAcct	= request.getString(adminAcctStr);
		
		if (admin != null) {
			if (lv < request.getInt("adminLv")) {
				adminRepo.deleteAdminByAdminAcct(adminAcct);
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/* Vender */
	public String findAllVender() {
		response	= new JSONArray();
		venders.clear();
		venders.addAll(venderRepo.findAll());
		
		for (int i = 0; i < venders.size(); i++) {
			jElement	= new JSONObject();
			
			jElement.put("venderId", venders.get(i).getVenderid());
			jElement.put("enable", venders.get(i).isEnable());
			jElement.put("venderAcct", venders.get(i).getAcct());
			jElement.put("venderName", venders.get(i).getUsername());
			jElement.put("tel", venders.get(i).getTel());
			jElement.put("mobile", venders.get(i).getMobile());
			jElement.put("zipno", venders.get(i).getZipno());
			jElement.put("street", venders.get(i).getStreet());
			jElement.put("email", venders.get(i).getEmail());
			jElement.put("coname", venders.get(i).getConame());
			jElement.put("uninum", venders.get(i).getUninum());
			jElement.put("googleId", venders.get(i).getSub());
			
			response.put(jElement);
		}
		System.out.println(response);
		return response.toString();
	}
	@Transactional
	public void deleteVenderById(Integer venderId) {
		likeVenderRepo.deleteByVenderId(venderId);
		venderTokenRepo.deleteVenderTokenByVenderVenderid(venderId);
		venderPerImgRepo.deleteByVenderVenderid(venderId);
		venderRepo.deleteVenderByVenderid(venderId);
	}
	
	
	
	/* Traveler */
	public String findAllTraveler() {
		response	= new JSONArray();
		travelers.clear();
		travelers.addAll(travelerRepo.findAll());
		
		for (int i = 0; i < travelers.size(); i++) {
			jElement	= new JSONObject();
			
			jElement.put("travelerId", travelers.get(i).getTravelerid());
			jElement.put("travelerAcct", travelers.get(i).getAcct());
			jElement.put("travelerName", travelers.get(i).getUsername());
			jElement.put("enable", travelers.get(i).isEnable());
			jElement.put("sexAbbr", travelers.get(i).getSexabbr());
			jElement.put("mobile", travelers.get(i).getMobile());
			jElement.put("email", travelers.get(i).getEmail());
			jElement.put("ecTel", travelers.get(i).getTel());
			jElement.put("ecName", travelers.get(i).getEcname());
			jElement.put("ecRel", travelers.get(i).getRelationship());
			jElement.put("googleId", travelers.get(i).getSub());
			
			response.put(jElement);
		}
		System.out.println(response);
		return response.toString();
	}
	
	@Transactional
	public void deleteTravelerById(Integer travelerId) {
		album.addAll(albumRepo.findAlbumIdByTravelerId(travelerId));
		
		for (int i = 0; i < album.size(); i++) {
			id	= album.get(i).getAlbumId();
			photoReop.deleteByAlbumId(id);
		}
		albumRepo.deleteByTravelerId(travelerId);
		articleRepo.deleteByTravelerId(travelerId);
		likeVenderRepo.deleteByTravelerId(travelerId);
		travelerTokenRepo.deleteByTravelerTravelerid(travelerId);
		travelerPerImgRepo.deleteByTravelerTravelerid(travelerId);
		travelerRepo.deleteTravelerByTravelerid(travelerId);
	}
	
	
	
	/* Travel tip */
	/* (C)reate */
	public boolean tipAdd(String req) {
		request = new JSONObject(req);
		title	= request.getString(tipTitleStr);
		details	= request.getString(tipDetailsStr);
		
		if (	title.isEmpty() == false
			&&	details.isEmpty() == false) {
			tipRep.save(new Tips(title, details));
			return true;
		}
		return false;
	}
	
	
	/* (R)ead */
	public String findAllTip() {
		response	= new JSONArray();
		tip.clear();
		tip.addAll(tipRep.findAll());
		
		for (int i = 0; i < tip.size(); i++) {
			jElement	= new JSONObject();
			
			jElement.put("tipId", tip.get(i).getTipId());
			jElement.put("title", tip.get(i).getTipTitle());
			jElement.put("details", tip.get(i).getTipDetails());
			
			response.put(jElement);
		}
		return response.toString();
	}
	
	public String findByTitle(String targetTitle) {
		response	= new JSONArray();
		tip.clear();
		tip.add(tipRep.findTipsByTitle(targetTitle));
		
		for (int i = 0; i < tip.size(); i++) {
			jElement	= new JSONObject();
			
			jElement.put("tipId", tip.get(i).getTipId());
			jElement.put("title", tip.get(i).getTipTitle());
			jElement.put("details", tip.get(i).getTipDetails());
			
			response.put(jElement);
		}
		return response.toString();
	}
	
	public String findById(Integer id) {
		response	= new JSONArray();
		tip.clear();
		tip.add(tipRep.findTipsById(id));
		
		for (int i = 0; i < tip.size(); i++) {
			jElement	= new JSONObject();
			
			jElement.put("tipId", tip.get(i).getTipId());
			jElement.put("title", tip.get(i).getTipTitle());
			jElement.put("details", tip.get(i).getTipDetails());
			
			response.put(jElement);
		}
		return response.toString();
	}
	
	
	/* (U)pdate */
	public boolean tipEdit(String req) {
		System.out.println("\n\ntipEdit Updata req : " + req + "\n\n");
		if (req.isEmpty() == false) {
			request = new JSONObject(req);
			id		= request.getInt(tipIdStr);
			title	= request.getString(tipTitleStr);
			details	= request.getString(tipDetailsStr);
			tip.clear();
			tip.add(tipRep.findTipsById(id));
			
			if (tip != null) {
				for (int i = 0; i < tip.size(); i++) {
					if (	tip.get(i).getTipTitle().equals(title) == false
						||	tip.get(i).getTipDetails().equals(details) == false) {
						tip.get(i).setTipTitle(title);
						tip.get(i).setTipDetails(details);
						
						tipRep.save(tip.get(i));
					}
				}
				return true;
			}
		}
		return false;
	}
	
	
	/* (D)elete */
	@Transactional
	public void tipDeleteById(Integer id) {
		tipRep.deleteById(id);
	}
	
	@Transactional
	public void tipDeleteByTitle(String targetTitle) {
		tipRep.deleteTipsByTitle(targetTitle);
	}
	
	
	
	
	/* Suspension */
	/* (C)reate */
	/* (R)ead */
	/* (U)pdate */
	public boolean suspendVenderAcct(Integer vId) {
		venders.clear();
		venders.add(venderRepo.findVenderByVenderid(vId));
		
		if (venders.get(0).isEnable() == true) {
			venders.get(0).setEnable(false);
			venderRepo.save(venders.get(0));
			return true;			
		}
		return false;
	}
	public boolean restoredVenderAcct(Integer vId) {
		venders.clear();
		venders.add(venderRepo.findVenderByVenderid(vId));
		
		if (venders.get(0).isEnable() == false) {
			venders.get(0).setEnable(true);
			venderRepo.save(venders.get(0));
			return true;			
		}
		return false;
	}

	public boolean suspendTravelerAcct(Integer tId) {
		travelers.clear();
		travelers.add(travelerRepo.findTravelerByTravelerid(tId));		
		
		if (travelers.get(0).isEnable() == true) {
			travelers.get(0).setEnable(false);
			travelerRepo.save(travelers.get(0));
			return true;			
		}
		return false;
	}
	public boolean restoredTravelerAcct(Integer tId) {
		travelers.clear();
		travelers.add(travelerRepo.findTravelerByTravelerid(tId));		
		
		if (travelers.get(0).isEnable() == false) {
			travelers.get(0).setEnable(true);
			travelerRepo.save(travelers.get(0));
			return true;			
		}
		return false;
	}
	/* (D)elete */
	
}
