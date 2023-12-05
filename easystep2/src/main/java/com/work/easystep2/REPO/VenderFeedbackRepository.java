package com.work.easystep2.REPO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.VenderFeedback;

public interface VenderFeedbackRepository extends JpaRepository<VenderFeedback, Integer> {
	// 找這個廠商的所有回饋
	List<VenderFeedback> findByVenderAcct(String venderAcct);
	// 找旅者的所有評論
	List<VenderFeedback> findByTravelerAcct(String travelerAcct);
	// 查有沒有這筆資料
	VenderFeedback findByVenderAcctAndTravelerAcct(String venderAcct, String travelerAcct);
}
