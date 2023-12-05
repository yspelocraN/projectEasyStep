package com.work.easystep2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.work.easystep2.model.Vender;
import com.work.easystep2.model.VenderTop10;
import com.work.easystep2.DTO.Top10DTo;
import com.work.easystep2.DTO.VenderDTO;
import com.work.easystep2.model.Zip;
import com.work.easystep2.service.VenderService;
import com.work.easystep2.service.VenderTop10Service;
import com.work.easystep2.service.ZipService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = {"/Vender"})
@CrossOrigin()
public class VenderController {

	@Autowired
	private VenderService vedService;

	@Autowired
	private StringValidator strValid;
	
	@Autowired
	private VenderTop10Service top10Service;
	
	@Autowired
	private ZipService zipService;
	
	
	//登入
	@PostMapping("/login") 
	public String checklogin(@RequestBody String json, HttpSession httpsession) throws JSONException{
		JSONObject obj = new JSONObject(json);
		String acct = obj.getString("acct");
		String pwd = obj.getString("pwd");
		Vender result = vedService.checkLogin(acct, pwd);
		
		if (result != null && result.isEnable() == true) {
			if(result.isEnable() == true) {
				Zip zip = zipService.findCity(result.getZipno());
				String city = zip.getCity();
				String dist = zip.getDist();
				VenderDTO vDto = new VenderDTO(result, city, dist);
				httpsession.setAttribute("DTO", vDto);
				httpsession.setAttribute("venderlogin", result.getVenderid());
				httpsession.setAttribute("vender", "廠商登入成功");
				String logInf = (String)httpsession.getAttribute("vender");
				return logInf;		
			} else {	
				return "登入失敗";
			}
		}
		return "登入失敗";
	}
	
	//登入
	@PostMapping("/googlelogin") 
	public String googlelogin(@RequestBody String json, HttpSession httpsession) throws JSONException{
		JSONObject obj = new JSONObject(json);
		String sub = obj.getString("sub");
		Vender result = vedService.googleLogin(sub);

		if (result != null && result.isEnable() == true) {
			if(result.isEnable() == true) {
				Zip zip = zipService.findCity(result.getZipno());
				String city = zip.getCity();
				String dist = zip.getDist();
				VenderDTO vDto = new VenderDTO(result,city,dist);
				httpsession.setAttribute("DTO", vDto);
				httpsession.setAttribute("venderlogin", result.getVenderid());
				httpsession.setAttribute("vender", "廠商登入成功");
				String logInf = (String)httpsession.getAttribute("vender");
				return logInf;		
			} else {	
				return "登入失敗";
			}
		}
		return "登入失敗";
	}
		
	//google註冊
	@PostMapping("/add")
	public String AddVender(@RequestBody String json, HttpSession httpsession) throws MessagingException, JSONException {
		JSONObject obj = new JSONObject(json);
		String acct = obj.getString("acct");
		String pwd = obj.getString("pwd");
		String repwd = obj.getString("repwd");
		String username = obj.getString("username");
		String tel = obj.getString("tel");
		String mobile = obj.getString("mobile");
		String zipno = obj.getString("zipno");
		String street = obj.getString("street");
		String email = obj.getString("email");
		String coname = obj.getString("coname");
		String uninum = obj.getString("uninum");
		String sub = obj.getString("sub");
			
		Vender vender = new Vender();
		vender.setAcct(acct);
		vender.setPwd(pwd);
		vender.setConame(coname);
		vender.setTel(tel);
		vender.setMobile(mobile);
		vender.setStreet(street);
		vender.setUninum(uninum);
		vender.setUsername(username);
		vender.setEmail(email);
		vender.setEnable(false);
		vender.setZipno(zipno);


		// 正則表示式
		boolean acctChk = strValid.acctIsValid(acct);
		boolean pwdChk = strValid.pwdIsValid(pwd);
		boolean rePwdChk = strValid.pwdIsValid(repwd);
		boolean nameChk = strValid.nameIsValid(username);
		boolean coNameChk = strValid.nameIsValid(coname);
		boolean telChk = strValid.telIsValid(tel);
		boolean mobiletChk = strValid.mobileIsValid(mobile);
		boolean emailChk = strValid.emailIsValid(email);
			
			
		boolean isNull = acct.equals("") || acct == null || pwd.equals("") || pwd == null || repwd.equals("") || repwd == null
					|| mobile.equals("") || mobile == null|| tel.equals("") || tel == null || email.equals("") || email == null
					|| zipno.equals("") || zipno == null || street.equals("") || street == null
					|| username.equals("") || username == null || coname.equals("") || coname == null 
					|| uninum.equals("") || uninum == null;
			
		//若所有數值格式皆正確(含帳號使否重複)
		boolean allChkSuccess = (!isNull) && (vedService.chkVenderAcct(acct)) && nameChk && acctChk &&
					pwdChk && rePwdChk && (pwd.equals(repwd)) && telChk && mobiletChk && emailChk;
			
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
			
		if (sub.equals("")) {
			if (allChkSuccess) {
				vedService.addVender2(vender);
				responseJson.put("yes", "已經寄信到您的信箱，請在一小時內完成驗證否則失效");
				return responseJson.toString();
			} else {
				JSONObject allError = new JSONObject();
				if (isNull) {
					allError.put("isnull","表格請輸入完整");
				}
				if (vedService.chkVenderAcct(acct) == false) {
					allError.put("acct", "帳號已被註冊");
				}
				if (!(acctChk)){
					allError.put("acct","帳號需6-15位英數字");
				}
				if (!nameChk) {
					allError.put("username", "使用者名稱不能包含空格");
				}
				if (!coNameChk) {
					allError.put("coname", "廠商名稱不能包含空格");
				}
				if (!(telChk)) {
					allError.put("tel", "電話格式不正確");
				}
				if (!(mobiletChk)) {
					allError.put("mobile", "手機格式不正確");
				}
				if (!(emailChk)) {
					allError.put("email", "電子郵件格式不正確");
				}
				if (!pwdChk) {
					allError.put("pwd", "密碼需6-15位英數字");
				} else if (!rePwdChk) {
					allError.put("repwd", "再次確認密碼需6-15位英數字");
				} else if (!pwd.equals(repwd)) {
					allError.put("pwd", "密碼和再次確認密碼需相同");
				}
				array = array.put(allError);
			}
			responseJson.put("no", array);
			return responseJson.toString();
		} else {
			if (allChkSuccess) {
				vender.setSub(sub);
				vedService.addVender3(vender);
				responseJson.put("yes", "註冊成功");
				return responseJson.toString();
			} else {
				JSONObject allError = new JSONObject();
				if (isNull) {
					allError.put("isnull","表格請輸入完整");
				}
				if (vedService.chkVenderAcct(acct) == false) {
					allError.put("acct", "帳號已被註冊");
				}
				if(!(acctChk)){
					allError.put("acct","帳號需6-15位英數字");
				}
				if (!nameChk) {
					allError.put("username", "使用者名稱不能包含空格");
				}
				if (!coNameChk) {
					allError.put("coname", "廠商名稱不能包含空格");
				}
				if (!(telChk)) {
					allError.put("tel", "電話格式不正確");
				}
				if (!(mobiletChk)) {
					allError.put("mobile", "手機格式不正確");
				}
				if (!(emailChk)) {
					allError.put("email", "電子郵件格式不正確");			
				}
				if (!pwdChk) {
					allError.put("pwd", "密碼需6-15位英數字");
				} else if (!rePwdChk) {
					allError.put("repwd", "再次確認密碼需6-15位英數字");
				} else if (!pwd.equals(repwd)) {
					allError.put("pwd", "密碼和再次確認密碼需相同");
				}
				array = array.put(allError);
			}
			responseJson.put("no", array);
			return responseJson.toString();
		}
	}
	
	//將更新資料存進session
	@GetMapping("/getlogin")
	@ResponseBody
    public VenderDTO getSessionData2(HttpSession session) throws JsonProcessingException, JSONException {
		VenderDTO vender = (VenderDTO)session.getAttribute("DTO");
		Integer id = vender.getVenderid();
		
		Vender result = vedService.findVender(id);
		Zip zip = zipService.findCity(result.getZipno());
		String city = zip.getCity();
		String dist = zip.getDist();
		VenderDTO vDto = new VenderDTO(result,city,dist);
		session.setAttribute("DTO", vDto);
		VenderDTO vender2 = (VenderDTO)session.getAttribute("DTO");
		
		if (vender2 != null) {
			 return vender2;
		} else {
	    	 return null;
	    }
	}

	//登出
	@GetMapping("/venderlogout")
    public String venderlogout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
        
        if (session != null) {
            // 使会话失效
            session.invalidate();
        }
        // 重定向到登录页面或其他页面
        return "ok"; // 这里假设登录页面的路径是 "/login"
    }
	
	
	


	//註冊簡易版
	@PostMapping("/add2")
	public String AddVender2(@RequestBody Vender vender, @RequestParam String repwd) throws MessagingException, JSONException {
		String pwd = vender.getPwd();
		
		if (pwd.equals(repwd)) {
			return "註冊成功，已寄信";
		}
		return "註冊失敗";
	}
	
	
	//忘記密碼：需輸入帳號和信箱寄信（隨機給隨機六碼）
	@PostMapping("/checkpwd")
	public String checkpwd(@RequestParam String acctPwd, @RequestParam String emailPwd, Model model) throws MessagingException {
		System.out.println(acctPwd);
		boolean isExist = vedService.checkpwd(acctPwd, emailPwd);
		if (isExist == true) {
			return "已寄信到您的信箱";
		} else {	
			return "輸入錯誤";
		}
	}
	
	//修改資料(信箱和帳號都不做更動)
	@PutMapping("/edit")
	public String submitUpdate(@RequestBody String json) throws MessagingException, JSONException {
		JSONObject obj = new JSONObject(json);
		Integer id = Integer.parseInt(obj.getString("id"));
		//下面這幾個要修改資料
		String username = obj.getString("username");
		String tel = obj.getString("tel");
		String mobile = obj.getString("mobile");
		String zipno = obj.getString("zipno");
		String street = obj.getString("street");
		String coname = obj.getString("coname");
		String uninum = obj.getString("uninum");
		
		Vender vender = new Vender();
		vender.setVenderid(id);
		vender.setConame(coname);
		vender.setTel(tel);
		vender.setMobile(mobile);
		vender.setStreet(street);
		vender.setUninum(uninum);
		vender.setUsername(username);
		vender.setEnable(true);
		vender.setZipno(zipno);

		// 正則表示式
		boolean nameChk = strValid.nameIsValid(username);
		boolean coNameChk = strValid.nameIsValid(coname);
		boolean telChk = strValid.telIsValid(tel);
		boolean mobiletChk = strValid.mobileIsValid(mobile);
		
		
		boolean isNull =  mobile.equals("") || mobile == null
					|| tel.equals("") || tel == null || zipno.equals("") || zipno == null 
					|| street.equals("") || street == null|| username.equals("") || username == null 
					|| coname.equals("") || coname == null || uninum.equals("") || uninum == null;
		
		//若所有數值格式皆正確
		boolean allChkSuccess = (!isNull)  && nameChk && telChk && mobiletChk ;
		
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		
		if(allChkSuccess) {
			vedService.updateVender(vender);
			responseJson.put("yes", "基本資料修改成功");
			return responseJson.toString();
		}else {
			JSONObject allError = new JSONObject();
			if (isNull) {
				allError.put("isnull","表格請輸入完整");
			}
			if (!nameChk) {
				allError.put("username", "使用者名稱不能包含空格");
			}
			if (!coNameChk) {
				allError.put("coname", "公司名稱不能包含空格");
			}
			if (!(telChk)) {
				allError.put("tel", "電話格式不正確");
			}
			if (!(mobiletChk)) {
				allError.put("mobile", "手機格式不正確");
			}
			array = array.put(allError);
		}
		responseJson.put("no", array);
		return responseJson.toString();
	}
	
	//修改個人資料（針對帳密修改
	@PutMapping("/editPwd")
	public String  UpdatePwd(@RequestBody String json) throws JSONException {
		JSONObject obj = new JSONObject(json);
		Integer id = Integer.parseInt(obj.getString("id"));
		//下面這幾個要修改資料
		String pwd = obj.getString("pwd");
		String newPwd = obj.getString("newPwd");
		String reNewPwd = obj.getString("reNewPwd");
		

		boolean pwdChkIsExist = vedService.checkPwd(id,pwd);
		boolean newPwdChk = strValid.pwdIsValid(newPwd);
		JSONObject responseJson = new JSONObject();
		System.out.println("newPwd" + newPwd);
		System.out.println("reNewPwd" + reNewPwd);
		System.out.println("pwdChkIsExist" + pwdChkIsExist);
		System.out.println("newPwdChk" + newPwdChk);
		System.out.println("newPwd.equals(reNewPwd)" + newPwd.equals(reNewPwd));
		if (pwdChkIsExist && newPwdChk && (newPwd.equals(reNewPwd))) {
			vedService.updatePwd(id, pwd, newPwd);
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
	
	//北區，中區，南區，東區廠商
	@GetMapping("/getArea") 	
	public List<Top10DTo> getArea(@RequestParam String json) throws JSONException {
		
		List<VenderTop10> Alllist=new ArrayList<>();
		List<Top10DTo> AlllistDTo=new ArrayList<>();
		
		switch (json) {
		case "北部":
			String[] northernTaiwan = {
				    "台北市", "新北市", "基隆市", "桃園市", "新竹市", "新竹縣", "苗栗縣"
				};
			for (String north : northernTaiwan) {
			    List<VenderTop10> area1 = top10Service.getAllVender(north);
			    Alllist.addAll(area1);
			    
			}
			break;

		case "中部":
			String[] centralTaiwan = {
				    "台中市", "彰化縣", "南投縣", "雲林縣" , "嘉義市", "嘉義縣"
				};
			for (String central : centralTaiwan) {
			    List<VenderTop10> area1 = top10Service.getAllVender(central);
			    Alllist.addAll(area1);
			}
			break;

		case "南部":
			String[] southernTaiwan = {
				    "高雄市", "屏東縣", "台南市", "澎湖縣"
				};
			for (String southern : southernTaiwan) {
			    List<VenderTop10> area1 = top10Service.getAllVender(southern);
			    Alllist.addAll(area1);
			}
			break;

		case "東部":
			String[] easternTaiwan = {
					"花蓮縣", "台東縣", "宜蘭縣"
				};
			for (String eastern : easternTaiwan) {
			    List<VenderTop10> area1 = top10Service.getAllVender(eastern);
			    Alllist.addAll(area1);
			}
			break;

		case "全部":
			String[] allTaiwan = {
					"台北市", "新北市", "基隆市", "桃園市", "新竹市", "新竹縣", "苗栗縣","台中市", "彰化縣", "南投縣", "雲林縣",
					"花蓮縣", "台東縣", "宜蘭縣","高雄市", "屏東縣", "台南市", "嘉義市", "嘉義縣", "澎湖縣"
				};
			for (String all : allTaiwan) {
			    List<VenderTop10> area1 = top10Service.getAllVender(all);
			    Alllist.addAll(area1);
			}
			break;
		}
		for (VenderTop10 venderTop10 : Alllist) {
			Top10DTo top10 = new Top10DTo(venderTop10);
			AlllistDTo.add(top10);
		}
		return AlllistDTo;
	}
	
}
