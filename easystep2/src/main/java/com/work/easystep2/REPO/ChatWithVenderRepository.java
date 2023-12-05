package com.work.easystep2.REPO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.work.easystep2.model.ChatWithVender;

public interface ChatWithVenderRepository extends JpaRepository<ChatWithVender, Integer> {
	
	// 雙方的對話列
	List<ChatWithVender> findByVenderAcctAndTravelerAcct(String venderAcct, String travelerAcct);
	
	// 找 旅者聊過的廠商 最新的排下來
	@Query(value = "select vender_acct from chat_vender where traveler_Acct=:n group by vender_acct order by MAX(msgdatetime) desc",nativeQuery = true)
	List<String> findVendersByTraveler(@Param(value = "n")String travelerAcct);
	
	// 找 跟廠商聊過天的旅者 最新的排下來
	@Query(value = "select traveler_Acct from chat_vender where vender_acct=:n group by traveler_Acct order by MAX(msgdatetime) desc",nativeQuery = true)
	List<String> findTravelersByVender(@Param(value = "n")String venderacct);
	
}
