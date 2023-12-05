package com.work.easystep2.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.work.easystep2.DTO.ChatTraDTO;
import com.work.easystep2.DTO.ChatVenDTO;
import com.work.easystep2.model.ChatWithTraveler;
import com.work.easystep2.model.ChatWithVender;
import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.Vender;
import com.work.easystep2.service.ChatService;
import com.work.easystep2.service.TravelerService;
import com.work.easystep2.service.VenderService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin()
public class ChatController {

	@Autowired
	private ChatService cs;
	@Autowired
	private TravelerService tService;
	@Autowired
	private VenderService vs;

	// 查詢與特定廠商/旅者的所有對話 對象是廠商
	@GetMapping("/chatroom/showchats/{venderId}")
	public ResponseEntity<?> showChats(@PathVariable Integer venderId, HttpSession httpsession) {
		Integer travelerId = (Integer) httpsession.getAttribute("tralogin");
		String traAcct = tService.findacct(travelerId);
		String venAcct = vs.findacct(venderId);
		List<ChatWithVender> response = cs.showWhitChats(venAcct, traAcct);
		return new ResponseEntity<List<ChatWithVender>>(response, HttpStatus.OK);
	}

	// 對象是旅者 這
	@GetMapping("/chatroom/showVenchats/{traId}")
	public ResponseEntity<?> showVenChats(@PathVariable Integer traId, HttpSession httpsession) {
		Integer venderId = (Integer) httpsession.getAttribute("venderlogin");
		String traAcct = tService.findacct(traId);
		String venAcct = vs.findacct(venderId);
		List<ChatWithVender> response = cs.showWhitChats(venAcct, traAcct);
		return new ResponseEntity<List<ChatWithVender>>(response, HttpStatus.OK);
	}

	// 開始對話 旅者傳給廠商的
	@PostMapping("/chatroom/startChat/{venderId}")
	public ResponseEntity<?> traToVenChat(@PathVariable Integer venderId, @RequestBody String json,
			HttpSession httpsession) throws JSONException {
		String message = (String) new JSONObject(json).get("msg");
		Integer travelerId = (Integer) httpsession.getAttribute("tralogin");
		String traAcct = tService.findacct(travelerId);
		String venAcct = vs.findacct(venderId);

		ChatWithVender msg = new ChatWithVender();

		msg.setTravelerAcct(traAcct);
		msg.setVenderAcct(venAcct);
		msg.setSender(traAcct);
		msg.setMsg(message);
		cs.startChat(msg);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	// 開始對話 廠商傳給旅者的
	@PostMapping("/chatroom/startChatToTra/{travelerId}")
	public ResponseEntity<?> venToTraChat(@PathVariable Integer travelerId, @RequestBody String json,
			HttpSession httpsession) throws JSONException {
		String message = (String) new JSONObject(json).get("msg");
		Integer venderId = (Integer) httpsession.getAttribute("venderlogin");

		String traAcct = tService.findacct(travelerId);
		String venAcct = vs.findacct(venderId);

		ChatWithVender msg = new ChatWithVender();

		msg.setTravelerAcct(traAcct);
		msg.setVenderAcct(venAcct);
		msg.setSender(venAcct);
		msg.setMsg(message);
		cs.startChat(msg);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	// 廠商 找曾經講過話的旅者 5筆
	@GetMapping("/chatroom/findtras")
	public ResponseEntity<?> showTalkTraOnce(HttpSession httpsession) {
		Integer venderId = (Integer) httpsession.getAttribute("venderlogin");
		String venAcct = vs.findacct(venderId);
		List<ChatTraDTO> response = new ArrayList<>();
		List<String> showChatTra = cs.showChatTra(venAcct);
		int i = 0;
		for (String traAcct : showChatTra) {
			ChatTraDTO cDTO = new ChatTraDTO();
			Traveler result = tService.findByAcct(traAcct);
			cDTO.setTravelerId(result.getTravelerid());
			cDTO.setTravelerName(result.getUsername());
			response.add(cDTO);
			if (i == 4) {
				break;
			}
			i++;
		}
		return new ResponseEntity<List<ChatTraDTO>>(response, HttpStatus.OK);
	}

	// 旅者 找曾經講過話的廠商
	@GetMapping("/chatroom/findvens")
	public ResponseEntity<?> showTalkVemOnce(HttpSession httpsession) {
		Integer traId = (Integer) httpsession.getAttribute("tralogin");
		String traAcct = tService.findacct(traId);
		List<ChatVenDTO> response = new ArrayList<>();
		List<String> showChatTra = cs.showChatVen(traAcct);
		
		for (String venAcct : showChatTra) {
			ChatVenDTO cvDTO = new ChatVenDTO();
			Vender result = vs.findByVenderAcct(venAcct);
			cvDTO.setVenderId(result.getVenderid());
			cvDTO.setVenderName(result.getUsername());
			response.add(cvDTO);
		}
		return new ResponseEntity<List<ChatVenDTO>>(response, HttpStatus.OK);
	}

	// 開始對話 旅者對旅者
	@PostMapping("/chatTraroom/startChatToTra/{traId2}")
	public ResponseEntity<?> startTChat(@PathVariable Integer traId2, @RequestBody String json, HttpSession httpsession) throws JSONException {
		Integer traId1 = (Integer) httpsession.getAttribute("tralogin");
		String message = (String) new JSONObject(json).get("msg");
		
		ChatWithTraveler ct = new ChatWithTraveler();
		ct.setMsg(message);
		ct.setTravelerid1(traId1);
		ct.setTravelerid2(traId2);
		ct.setSenderid(traId1);
		cs.startTchats(ct);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	// 查詢兩人之間的對話 旅者對旅者
	@GetMapping("/chatTraroom/showchats/{traId2}")
	public ResponseEntity<?> showTandTChats(@PathVariable Integer traId2,HttpSession httpsession){
		Integer traId1 = (Integer) httpsession.getAttribute("tralogin");
		
		List<ChatWithTraveler> response = cs.showTtalks(traId2, traId1);
		
		return new ResponseEntity<List<ChatWithTraveler>>(response, HttpStatus.OK);
	}
	// 找曾經說過話的 旅者 自己是旅者
	@GetMapping("/chatTroom/findonceT")
	public ResponseEntity<?> showTonceTalks(HttpSession httpsession){
		Integer traId = (Integer) httpsession.getAttribute("tralogin");
		List<ChatTraDTO> response = new ArrayList<>();
		List<Integer> result = cs.findTothers(traId);
		for (Integer traveId : result) {
			Traveler tr = tService.findTraveler(traveId);
			ChatTraDTO cDTO = new ChatTraDTO();
			
			cDTO.setTravelerId(traveId);
			cDTO.setTravelerName(tr.getUsername());
			response.add(cDTO);
		}
		return new ResponseEntity<List<ChatTraDTO>>(response, HttpStatus.OK);
	}
	
}
