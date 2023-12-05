package com.work.easystep2.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.work.easystep2.DTO.FeedbackDTO;
import com.work.easystep2.DTO.VenderInformationDTO;
import com.work.easystep2.DTO.VendersShowSimpleDTO;
import com.work.easystep2.model.VenderFeedback;
import com.work.easystep2.model.VenderInformation;
import com.work.easystep2.service.FileUploadImplService;
import com.work.easystep2.service.TravelerService;
import com.work.easystep2.service.VenderService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class VenderInformationController {

	@Autowired
	private VenderService vService;

	@Autowired
	private TravelerService tService;

	@Autowired
	private FileUploadImplService fiService;

	// Traveler

	// 使用者 新增評論 也是更新
	@PostMapping("/users/addFeedback/{venderId}")
	public ResponseEntity<?> addFeedback(@PathVariable Integer venderId, @RequestBody FeedbackDTO fb, HttpSession httpsession) {
		Integer travelerId = (Integer) httpsession.getAttribute("tralogin");

		VenderFeedback vf = new VenderFeedback(fb);
		vf.setTravelerAcct(tService.findacct(travelerId));
		vf.setVenderAcct(vService.findacct(venderId));
		VenderFeedback result = vService.findthisScore(vService.findacct(venderId), tService.findacct(travelerId));
		if (result != null) {
			vf.setVenderFId(result.getVenderFId());
		}
		vf.setWriteDate(new Date());
		vService.addScore(vf);

		return new ResponseEntity<String>("Y", HttpStatus.OK);
	}

	// 確認 旅者登入 // 待確認
	@GetMapping("/traveler/checklogin")
	public ResponseEntity<?> loginingcheck(HttpSession httpsession) throws JSONException {
		JSONObject responseJson = new JSONObject();
		if (httpsession.getAttribute("tralogin") != null) {
			responseJson.put("success", true);
			return new ResponseEntity<String>(responseJson.toString(), HttpStatus.OK);
		}
		responseJson.put("success", false);
		return new ResponseEntity<String>(responseJson.toString(), HttpStatus.OK);
	}

	// 用ID 找名字
	@GetMapping("/user/findName/{traId}")
	public ResponseEntity<?> findName(@PathVariable Integer traId) {
		String name = tService.findById(traId).getUsername();
		return new ResponseEntity<String>(name, HttpStatus.OK);
	}

	// Vender

	// 新增 廠商資訊 也是更新
	@PostMapping("/vender/addinformation")
	public ResponseEntity<?> addinformation(@RequestBody VenderInformation v, HttpSession session) {
		Integer venderId = (Integer) session.getAttribute("venderlogin");
		v.setVenderId(venderId);
		v.setWriteOrUpdate(new Date());
		vService.insertInfo(v);
		return new ResponseEntity<String>("Y", HttpStatus.OK);
	}

	// 用 ID 找 廠商資料
	@GetMapping("/vender/findinformation")
	public ResponseEntity<?> getinformation(HttpSession session) {
		Integer result = (Integer) session.getAttribute("venderlogin");
		VenderInformationDTO response = vService.findInfobyid(result);
		if (response != null) {
			return new ResponseEntity<VenderInformationDTO>(response, HttpStatus.OK);
		}
		return new ResponseEntity<String>("N", HttpStatus.OK);
	}

	// 傳 那個廠商的所有回饋
	@GetMapping("/venders/showfeedback/{venderId}")
	public ResponseEntity<?> showVFeedback(@PathVariable Integer venderId) {

		List<FeedbackDTO> response = vService.showVFeedback(venderId);
		return new ResponseEntity<List<FeedbackDTO>>(response, HttpStatus.OK);
	}

	// 新增廠商環境照片 簡易寫法
	@PostMapping("/1234")
	public ResponseEntity<String> addPhoto(@RequestParam("files") MultipartFile[] files, HttpSession session) throws IOException {
		Integer venderId = (Integer) session.getAttribute("venderlogin");
		fiService.addvenderFiles(Arrays.asList(files), venderId);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	// 查詢廠商所有環境照片
	@GetMapping("/vender/showAllphoto/{venderId}")
	public ResponseEntity<List<String>> showPhotos(@PathVariable Integer venderId) throws JSONException {
		List<String> response = vService.findImgsById(venderId);
		return new ResponseEntity<List<String>>(response, HttpStatus.OK);
	}

	// 查詢廠商照片 最多五張
	@GetMapping("/vender/showfivephoto/{venderId}")
	public ResponseEntity<List<String>> showfivePhotos(@PathVariable Integer venderId) throws JSONException {
		List<String> response = new ArrayList<>();
		List<String> re = vService.findImgsById(venderId);
		int i = 0;
		for (String string : re) {
			response.add(string);
			if (i == 4) {
				break;
			}
			i++;
		}
		return new ResponseEntity<List<String>>(response, HttpStatus.OK);
	}

	// 確認是否有無登入 // 待確認
	@GetMapping("/vender/checklogin")
	public ResponseEntity<?> loginingVcheck(HttpSession httpsession) throws JSONException {
		JSONObject responseJson = new JSONObject();
		if (httpsession.getAttribute("venderlogin") != null) {
			responseJson.put("success", true);
			return new ResponseEntity<String>(responseJson.toString(), HttpStatus.OK);
		}
		responseJson.put("success", false);
		return new ResponseEntity<String>(responseJson.toString(), HttpStatus.OK);
	}

	// 傳 廠商名+簡介+廠商更新資訊時間 分頁 + 一張照片
	@GetMapping("/venders/showAllVendersInfo/{pageNumber}")
	public ResponseEntity<?> showVendersInfo(@PathVariable Integer pageNumber) {
		List<VendersShowSimpleDTO> response = vService.showAllInfopage(pageNumber);
		return new ResponseEntity<List<VendersShowSimpleDTO>>(response, HttpStatus.OK);
	}

	// 傳 總共幾頁
	@GetMapping("/venders/showVendersInfoPage")
	public ResponseEntity<?> showTotalPage() {
		Integer response = vService.showInfoTotalPages();
		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}

	// 傳個別的簡單資訊
	@GetMapping("/vender/showSimpleVenderInfo/{venderId}")
	public ResponseEntity<?> showSimpleVenderInfo(@PathVariable Integer venderId) {
		VendersShowSimpleDTO response = vService.showSimpleInfo(venderId);
		return new ResponseEntity<VendersShowSimpleDTO>(response, HttpStatus.OK);
	}

	// 秀個別的全資訊
	@GetMapping("/vender/showVenderInfo/{venderId}")
	public ResponseEntity<?> showVenderInfo(@PathVariable Integer venderId) {
		VenderInformationDTO response = vService.showInfo(venderId);
		return new ResponseEntity<VenderInformationDTO>(response, HttpStatus.OK);
	}

	// 用ID 找名字
	@GetMapping("/vender/findName/{venderId}")
	public ResponseEntity<?> findVName(@PathVariable Integer venderId) {
		String name = vService.findName(venderId);
		return new ResponseEntity<String>(name, HttpStatus.OK);
	}

	// 秀 除了自己的其他廠商的簡單資訊
	@GetMapping("/vender/showotherinfo/{venderId}")
	public ResponseEntity<?> showOthersInfo(@PathVariable Integer venderId) {
		List<VendersShowSimpleDTO> response = vService.showOtherSimpleInfo(venderId);
		return new ResponseEntity<List<VendersShowSimpleDTO>>(response, HttpStatus.OK);
	}

}
