package com.work.easystep2.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.work.easystep2.handler.StringValidator;
import com.work.easystep2.model.LikeVender;
import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.VenderTop10;
import com.work.easystep2.DTO.Top10DTo;
import com.work.easystep2.DTO.TravelerDTO;
import com.work.easystep2.service.LikeVenderService;
import com.work.easystep2.service.TravelerService;
import com.work.easystep2.service.VenderTop10Service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = {"/Traveler"})
@CrossOrigin()
public class TravelerControllerPostMan {

	@Autowired
	private TravelerService trlsService;
	
	@Autowired
	private StringValidator strValid;
	
	@Autowired
	private VenderTop10Service top10Service;
	
	@Autowired
	private LikeVenderService likeService;
	
	//新增收藏廠商
	@PostMapping("/addlike")
	public String addLikeVender(@RequestParam Integer travelerid,@RequestParam Integer venderid) {
		if(likeService.addLikeVender(travelerid, venderid) != null) {
			return "addYes";
		}
		return "addNo";
	}
		
	//刪除收藏廠商
	@DeleteMapping("/deletelike")
	public String deleteLikeVender(@RequestParam Integer travelerid,@RequestParam Integer venderid) {
		if(likeService.deleteOneVenderById(travelerid, venderid)) {
			return "deleteYes";
		}
		return "deleteNo";
	}
		
	//取得所有收藏的廠商
	@GetMapping("/getAllLike")
	public List<Top10DTo> getAllLikeVender(@RequestParam Integer travelerid) {
		List<Top10DTo> AlllistDTo = new ArrayList<>();
		List<LikeVender> likeVender = likeService.getLikeVender(travelerid);
		for (LikeVender lk : likeVender) {
			VenderTop10 likelist = top10Service.getLikeVender(lk.getVenderId());
			Top10DTo tDTO =  new Top10DTo(likelist);
			AlllistDTo.add(tDTO);
		}
		return AlllistDTo;
	}
		
		
	//刪除一次全部收藏廠商
	@DeleteMapping("/deleteAllLike")
	public String deleteAllLikeVender(@RequestParam Integer travelerid) {
		if(likeService.deleteAllVenderById(travelerid)) {
			return "deleteAllYes";
		}
		return "deleteAllNo";
	}
		
	
		
	
	// 註冊google
	@PostMapping("/add") 
	public String AddTraveler2(@RequestBody String json, HttpSession httpsession) throws MessagingException, JsonProcessingException, JSONException {
		JSONObject obj = new JSONObject(json);
		String acct = obj.getString("acct");
		String pwd = obj.getString("pwd");
		String repwd = obj.getString("repwd");
		String username = obj.getString("username");
		String tel = obj.getString("tel");
		String mobile = obj.getString("mobile");
		String email = obj.getString("email");
		String sexabbr = obj.getString("sexabbr");
		String ecName = obj.getString("ecName");
		String relationship = obj.getString("relationship");
		String sub = obj.getString("sub");

		// 正則表示式
		boolean acctChk = strValid.acctIsValid(acct);
		boolean pwdChk = strValid.pwdIsValid(pwd);
		boolean rePwdChk = strValid.pwdIsValid(repwd);
		boolean nameChk = strValid.nameIsValid(username);
		boolean mobiletChk = strValid.mobileIsValid(mobile);
		boolean emailChk = strValid.emailIsValid(email);
		
		boolean isNull = acct.equals("")|| acct == null || pwd.equals("") || pwd == null || mobile.equals("") || mobile == null
				|| sexabbr.equals("") || sexabbr == null || email.equals("")|| email == null
				|| username.equals("") || username == null ;
		
		boolean allChkSuccess= (!isNull) && (trlsService.chkTravelerAcct(acct)) && nameChk && acctChk &&
				pwdChk && rePwdChk && (pwd.equals(repwd))  && mobiletChk && emailChk;
		
		Traveler trl = new Traveler();
		trl.setAcct(acct);
		trl.setPwd(pwd);
		trl.setUsername(username);
		trl.setEcname(ecName);
		trl.setSexabbr(sexabbr);
		trl.setTel(tel);
		trl.setMobile(mobile);
		trl.setEmail(email);
		trl.setRelationship(relationship);
		trl.setEnable(false);

		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		
		
		if(sub.equals("")) {
			if(allChkSuccess) {
				//填寫資料後會寄信
				trlsService.addTraveler2(trl);
				responseJson.put("yes", "已經寄信到您的信箱，請在一小時內完成驗證否則失效");
				return responseJson.toString();
			}else {
				JSONObject allError = new JSONObject();
				if (isNull) {
					allError.put("isnull","表格請輸入完整");
				}
				if (trlsService.chkTravelerAcct(acct) == false) {
					allError.put("acct", "帳號已被註冊");
				}
				
				if (!nameChk) {
					allError.put("username", "使用者名稱不能包含空格");
				}
				
				if (!(acctChk)) {
					allError.put("acct", "帳號需6-15位英數字");
				}
				
				if (!pwdChk) {
					allError.put("pwd", "密碼需6-15位英數字");
				} else if (!rePwdChk) {
					allError.put("repwd", "再次確認密碼需6-15位英數字");
				} else if (!pwd.equals(repwd)) {
					allError.put("pwd", "密碼和再次確認密碼需相同");
				}
				
				if (!(mobiletChk)) {
					allError.put("mobile", "手機格式不正確");
				}
				if (!(emailChk)) {
					allError.put("email", "電子郵件格式不正確");
				}
				array = array.put(allError);
			}
			responseJson.put("no", array);
			return responseJson.toString();
		} else {//這邊在加入判別有無google是否存在
			if(allChkSuccess) {
				trl.setSub(sub);
				trlsService.addTraveler3(trl);
				responseJson.put("yes", "註冊成功");
				return responseJson.toString();	
			}else {
				JSONObject allError = new JSONObject();
				if (isNull) {
					allError.put("isnull","表格請輸入完整");
				}
				if (trlsService.chkTravelerAcct(acct) == false) {
					allError.put("acct", "帳號已被註冊");
				}
				
				if (!nameChk) {
					allError.put("username", "使用者名稱不能包含空格");
				}
				
				if (!(acctChk)) {
					allError.put("acct", "帳號需6-15位英數字");
				}
				
				if (!pwdChk) {
					allError.put("pwd", "密碼需6-15位英數字");
				} else if (!rePwdChk) {
					allError.put("repwd", "再次確認密碼需6-15位英數字");
				} else if (!pwd.equals(repwd)) {
					allError.put("pwd", "密碼和再次確認密碼需相同");
				}
				
				if (!(mobiletChk)) {
					allError.put("mobile", "手機格式不正確");
				}
				if (!(emailChk)) {
					allError.put("email", "電子郵件格式不正確");
				}
				array = array.put(allError);
			}
			responseJson.put("no", array);
			return responseJson.toString();
		}
	}
	
		
	//登入
	@PostMapping("/login")
	public String checklogin2(@RequestBody String json, HttpSession httpsession) throws JSONException {
		JSONObject obj = new JSONObject(json);
		String acct = obj.getString("acct");
		String pwd = obj.getString("pwd");
		Traveler result = trlsService.checkLogin(acct,pwd);
		
		if (result != null && result.isEnable() == true) {
			if (result.isEnable() == true) {//會再次驗證是否信箱被驗證
				TravelerDTO tDto = new TravelerDTO(result);
				httpsession.setAttribute("DTO", tDto);//存進session(埋id在畫面中)
				httpsession.setAttribute("traveler", "旅者登入成功");
				httpsession.setAttribute("tralogin", result.getTravelerid());
				String logInf=(String)httpsession.getAttribute("traveler");
				return logInf;
			} else {	
				return "登入失敗";
			}
		}
		return "登入失敗";
	}
		
	
	
	//google登入
	@PostMapping("/googlelogin")
	public String checklogin(@RequestBody String json, HttpSession httpsession) throws JSONException {
		JSONObject obj = new JSONObject(json);
		String sub = obj.getString("sub");
		Traveler result = trlsService.googleLogin(sub);
				
		if (result != null && result.isEnable() == true) {
			if (result.isEnable() == true) {//會再次驗證是否信箱被驗證
				TravelerDTO tDto = new TravelerDTO(result);
				httpsession.setAttribute("DTO", tDto);//存進session(埋id在畫面中)
				httpsession.setAttribute("traveler", "旅者登入成功");
				httpsession.setAttribute("tralogin", result.getTravelerid());
				String logInf = (String)httpsession.getAttribute("traveler");
				return logInf;			
			} else {	
				return "登入失敗";
			}
		}
		return "登入失敗";
	}
	
		
	//將更新資料存進session
	@GetMapping("/getlogin")
	@ResponseBody
	public TravelerDTO getSessionData2(HttpSession session) throws JsonProcessingException, JSONException {
		TravelerDTO traveler = (TravelerDTO)session.getAttribute("DTO");
		Integer id = traveler.getTravelerid();
		
		Traveler result = trlsService.findTraveler(id);
		if (result.isEnable() == true) {
			TravelerDTO tDto = new TravelerDTO(result);
			session.setAttribute("DTO", tDto);
			TravelerDTO traveler2 = (TravelerDTO)session.getAttribute("DTO");
			
			if (traveler2 != null) {
				return traveler2;
			} else {
				return null;
			}
		}
		return null;
	}
		
	//登出
	@GetMapping("/travelerlogout")
	public String travelerlogout(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if (session != null) {
			// 使会话失效
			session.invalidate();
		}
		// 重定向到登录页面或其他页面
		return "ok"; // 这里假设登录页面的路径是 "/login"
	}
		
	
		
	//忘記密碼
	@PostMapping("/checkpwd")
	public String checkpwd(@RequestParam String acctPwd, @RequestParam String emailPwd, Model model) throws MessagingException {
		System.out.println(acctPwd);
		boolean isExist = trlsService.checkpwd(acctPwd, emailPwd);
		if (isExist == true) {
			return "已寄信到您的信箱";
		} else {	
			return "輸入錯誤";
		}
	}
	
//判斷有無值
//	@GetMapping("/Traveler/TravalerUpdate")
//    public String protectedPage(@SessionAttribute(name = "loginuser", required = false) String loginuser) {
//        if (loginuser == null) {
//            return "redirect:/Traveler/TravalerLog";
//        }
//        return "/Traveler/TravalerUpdate";
//    }
	
	//修改個人資料一（不含帳號、密碼、信箱），再次顯示修改過後的資料
	@PutMapping("/edit")
	public String submitUpdate(@RequestBody String json, HttpSession httpsession) throws JSONException {
		JSONObject obj = new JSONObject(json);
		Integer id = Integer.parseInt(obj.getString("id"));
		//下面這幾個要修改資料
		String username = obj.getString("username");
		String tel = obj.getString("tel");
		String mobile = obj.getString("mobile");
		String sexabbr = obj.getString("sexabbr");
		String ecName = obj.getString("ecName");
		String relationship = obj.getString("relationship");
		
		
		Traveler traveler=new Traveler();
		traveler.setTravelerid(id);
		traveler.setUsername(username);
		traveler.setSexabbr(sexabbr);
		traveler.setTel(tel);
		traveler.setMobile(mobile);
		traveler.setEcname(ecName);
		traveler.setRelationship(relationship);

	
		// 正則表示式
		boolean nameChk = strValid.nameIsValid(username);
		boolean telChk = strValid.telIsValid(tel);
		boolean mobiletChk = strValid.mobileIsValid(mobile);

		
		boolean isNull =  mobile.equals(" ")|| mobile == null || sexabbr.equals("") || sexabbr == null 
				|| username.equals(" ") || username == null;
		
		boolean allChkSuccess = (!isNull) && nameChk && telChk && mobiletChk;
		
		JSONObject responseJson=new JSONObject();
		JSONArray array=new JSONArray();
		
		if (allChkSuccess) {
			trlsService.updateTraveler(traveler);
			responseJson.put("yes", "基本資料修改成功");
			return responseJson.toString();
		} else {
			JSONObject allError = new JSONObject();
			if (isNull) {
				allError.put("isnull","表格請輸入完整");
			}
			if (!nameChk) {
				allError.put("username", "使用者名稱不能包含空格");
			}
			if (!(mobiletChk)) {
				allError.put("mobile", "手機格式不正確");
			}
			array = array.put(allError);
		}
		responseJson.put("no", array);
		return responseJson.toString();
	}
	
	//修改個人資料（針對帳密修改）
	@PutMapping("/editPwd")
	public String  UpdatePwd(@RequestBody String json) throws JSONException {
		JSONObject obj = new JSONObject(json);
		Integer id = Integer.parseInt(obj.getString("id"));
		//下面這幾個要修改資料
		String pwd = obj.getString("pwd");
		String newPwd = obj.getString("newPwd");
		String reNewPwd = obj.getString("reNewPwd");

		boolean pwdChkIsExist = trlsService.checkPwd(id,pwd);
		boolean newPwdChk = strValid.pwdIsValid(newPwd);
		JSONObject responseJson = new JSONObject();
		
		if( pwdChkIsExist && newPwdChk && (newPwd.equals(reNewPwd))) {
			trlsService.updatePwd(id, pwd, newPwd);
			responseJson.put("pwd", "修改成功");
			return responseJson.toString();
		} else {	
			if(!pwdChkIsExist) {
				responseJson.put("pwd", "原密碼輸入錯誤");
			}
			if (!newPwdChk) {
				responseJson.put("pwd", "新密碼需6-15位英數字");
			} 
			if (newPwd != reNewPwd) {
				responseJson.put("pwd", "新密碼和確認新密碼需相同");
			}			
			return responseJson.toString();
		}
	}
	
}
