package com.work.easystep2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.easystep2.REPO.ChatWithTravelerRepository;
import com.work.easystep2.REPO.ChatWithVenderRepository;
import com.work.easystep2.model.ChatWithTraveler;
import com.work.easystep2.model.ChatWithVender;

@Service
public class ChatService {

	@Autowired
	private ChatWithVenderRepository cRepo;
	@Autowired
	private ChatWithTravelerRepository cTRepo;

	// 秀出對話內容
	public List<ChatWithVender> showWhitChats(String venderAcct, String travelerAcct) {
		return cRepo.findByVenderAcctAndTravelerAcct(venderAcct, travelerAcct);
	}

	// 找 旅者聊過天的廠商
	public List<String> showChatVenders(String travelerAcct) {
		return cRepo.findVendersByTraveler(travelerAcct);
	}

	// 新增聊天內容
	public void startChat(ChatWithVender cwv) {
		cRepo.save(cwv);
	}

	// 找 廠商聊過天的旅者
	public List<String> showChatTra(String venderAcct) {
		return cRepo.findTravelersByVender(venderAcct);
	}

	// 找 旅者聊過天的廠商
	public List<String> showChatVen(String traAcct) {
		return cRepo.findVendersByTraveler(traAcct);
	}

	// 旅者

	// 秀旅者間的對話
	public List<ChatWithTraveler> showTtalks(Integer t1, Integer t2) {
		return cTRepo.findbetweenTalks(t1, t2);
	}

	// 找 旅者聊過天的旅者
	public List<Integer> findTothers(Integer t1) {
		List<Integer> large = cTRepo.findothersmall(t1);
		List<Integer> small = cTRepo.findotherlarge(t1);
		if (large.isEmpty()) {
			if (small.isEmpty()) {
				return null;
			}
			return small;
		}
		if (small.isEmpty()) {
			return large;
		}
		List<Integer> combinedList = new ArrayList<>(large);
		combinedList.addAll(small);
		return combinedList;
	}

	// 新增聊天內容
	public void startTchats(ChatWithTraveler c) {
		ChatWithTraveler result = c;

		if (c.getTravelerid1().intValue() > c.getTravelerid2().intValue()) {
			Integer d = c.getTravelerid1();
			result.setTravelerid1(c.getTravelerid2());
			result.setTravelerid2(d);
		}
		result.setWriteDatetime(new Date());
		cTRepo.save(result);
	}
	
}
