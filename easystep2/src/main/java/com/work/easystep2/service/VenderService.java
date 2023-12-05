package com.work.easystep2.service;

import jakarta.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.work.easystep2.DTO.FeedbackDTO;
import com.work.easystep2.DTO.VenderInformationDTO;
import com.work.easystep2.DTO.VendersShowSimpleDTO;
import com.work.easystep2.REPO.TravelerPerImgRepository;
import com.work.easystep2.REPO.TravelerRepository;
import com.work.easystep2.REPO.VenderFeedbackRepository;
import com.work.easystep2.REPO.VenderImgRepository;
import com.work.easystep2.REPO.VenderInformationRepository;
import com.work.easystep2.REPO.VenderRepository;
import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.TravelerPerImg;
import com.work.easystep2.model.Vender;
import com.work.easystep2.model.VenderFeedback;
import com.work.easystep2.model.VenderImg;
import com.work.easystep2.model.VenderInformation;
import com.work.easystep2.model.VenderToken;

@Service
public class VenderService {

	@Autowired
	private VenderRepository vedRepo;

	@Autowired
	private PasswordEncoder pwdEncoder;

	@Autowired
	private VenderTokenService tokenService;

	@Autowired
	private VenderEmailService emailService;

	@Autowired
	private RandomStringGenerator randomService;
	
	@Autowired
	private TravelerPerImgRepository tpRepo;

	@Autowired
	private VenderFeedbackRepository vFRe;

	@Autowired
	private VenderInformationRepository vIRe;

	@Autowired
	private TravelerRepository tRepo;

	@Autowired
	private VenderImgRepository vPrepo;

	public Vender addVender3(Vender vender) throws MessagingException {
		String encoderPwd = pwdEncoder.encode(vender.getPwd());
		vender.setPwd(encoderPwd);
		vender.setEnable(true);

		Optional<Vender> saved = Optional.of(vedRepo.save(vender));
		if (saved.isPresent()) {
			Vender newVender = saved.get();
			return vedRepo.save(newVender);
		}

		return null;
	}

	public Boolean chkVenderAcct(String acct) {
		Optional<Vender> optional = Optional.ofNullable(vedRepo.findVenderByAcct(acct));
		return optional.isEmpty();
	}

	public Vender googleLogin(String sub) {
		Vender dbVender = vedRepo.findVenderBySub(sub);
		return dbVender != null ? dbVender : null;
	}

	public Vender addVender2(Vender vender) throws MessagingException {
		String encoderPwd = pwdEncoder.encode(vender.getPwd());
		vender.setPwd(encoderPwd);
		vender.setEnable(false);

		Optional<Vender> saved = Optional.of(vedRepo.save(vender));
		if (saved.isPresent()) {
			String token = UUID.randomUUID().toString();
			Vender newVender = saved.get();
			VenderToken newtoken = tokenService.save(newVender, token);
			newVender.setVenderToken(newtoken);
			newtoken.setVender(vender);
			emailService.sendHtmlMail2(newVender);
			return vedRepo.save(newVender);
		}

		return null;
	}

	public Vender addVender(Vender vender) {
		return vedRepo.save(vender);
	}

	public Vender checkLogin(String acct, String pwd) {
		Vender dbVender = vedRepo.findVenderByAcct(acct);
		if (dbVender != null && pwdEncoder.matches(pwd, dbVender.getPwd())) {
			return dbVender;
		}
		return null;
	}

	public Boolean checkPwd(Integer id, String pwd) {
		Optional<Vender> optional = vedRepo.findById(id);

		if (optional.isPresent()) {
			Vender vender = optional.get();
			if (pwdEncoder.matches(pwd, vender.getPwd())) {
				return true;
			}
		}
		return false;
	}

	public Boolean checkpwd(String acct, String email) throws MessagingException {
		Vender dbVender = vedRepo.findVenderByAcct(acct);
		if (dbVender != null && dbVender.isEnable() == true && dbVender.getEmail().equals(email)) {
			String newpwd = randomService.generateRandomString(6);
			dbVender.setPwd(pwdEncoder.encode(newpwd));

			vedRepo.save(dbVender);
			emailService.sendMailPwd(dbVender, newpwd);
			return true;
		}
		return false;
	}

	public Vender findVender(Integer id) {
		Optional<Vender> optional = vedRepo.findById(id);
		return optional.orElse(null);
	}

	public Vender updateVender(Vender vender) {
		Optional<Vender> optional = vedRepo.findById(vender.getVenderid());
		if (optional.isPresent()) {
			Vender newvender = optional.get();
			newvender.setConame(vender.getConame());
			newvender.setMobile(vender.getMobile());
			newvender.setStreet(vender.getStreet());
			newvender.setTel(vender.getTel());
			newvender.setUninum(vender.getUninum());
			newvender.setUsername(vender.getUsername());
			newvender.setZipno(vender.getZipno());
			return vedRepo.save(newvender);
		}
		return null;
	}

	public Vender updatePwd(Integer id, String pwd, String newPwd) {
		Optional<Vender> optional = vedRepo.findById(id);
		if (optional.isPresent()) {
			Vender vender = optional.get();
			if (pwdEncoder.matches(pwd, vender.getPwd())) {
				String encoderPwd = pwdEncoder.encode(newPwd);
				vender.setPwd(encoderPwd);
				return vedRepo.save(vender);
			}
		}
		return null;
	}

	public Vender insertInfo(VenderInformation vi) {
		Optional<Vender> findById = vedRepo.findById(vi.getVenderId());
		Vender vender = findById.orElse(null);
		if (vender != null) {
			vi.setVender(vender);
			vender.setVInfo(vi);
			return vedRepo.save(vender);
		}
		return null;
	}

	public VenderInformationDTO findInfobyid(Integer venderId) {
		Optional<VenderInformation> optional = vIRe.findById(venderId);
		if (optional.isPresent()) {
			VenderInformation result = optional.get();
			VenderInformationDTO viDTO = new VenderInformationDTO(result);
			return viDTO;
		}
		return null;
	}

	public Vender updateInfo(VenderInformation vi) {
		Optional<Vender> findById = vedRepo.findById(vi.getVenderId());
		Vender vender = findById.orElse(null);
		if (vender != null) {
			vender.setVInfo(vi);
			return vedRepo.save(vender);
		}
		return null;
	}

	public VenderFeedback addScore(VenderFeedback vf) {
		return vFRe.save(vf);
	}

	public VenderFeedback findthisScore(String va, String ta) {
		return vFRe.findByVenderAcctAndTravelerAcct(va, ta);
	}

	public VenderFeedback updateScore(VenderFeedback vf) {
		return vFRe.save(vf);
	}

	public List<FeedbackDTO> showVFeedback(Integer venderId) {
		String venderAcct = vedRepo.findById(venderId).map(Vender::getAcct).orElse(null);
		List<FeedbackDTO> fLDTO = new ArrayList<>();
		if (venderAcct != null) {
			List<VenderFeedback> result = vFRe.findByVenderAcct(venderAcct);
			for (VenderFeedback vf : result) {
				Traveler traResult = tRepo.findTravelerByAcct(vf.getTravelerAcct());
				TravelerPerImg tpResult = tpRepo.findByTravelerId(traResult.getTravelerid());
				Vender vResult = vedRepo.findVenderByAcct(venderAcct);

				FeedbackDTO feedbackDTO = new FeedbackDTO(vf);
				feedbackDTO.setTravelerName(traResult.getUsername());
				feedbackDTO.setVenderName(vResult.getUsername());
				feedbackDTO.setTravelerPhoto(tpResult.getPhotoUrl());
				fLDTO.add(feedbackDTO);
			}
		}
		return fLDTO;
	}

	public String findNameByAcct(String venderAcct) {
		Vender result = vedRepo.findVenderByAcct(venderAcct);

		/* Given from Chatgpt, need to change venderRepositroy */
//		return vedRepo.findVenderByAcct(venderAcct).map(Vender::getUsername).orElse(null);
		
		return result.getUsername();
	}

	public List<VendersShowSimpleDTO> showAllInfopage(Integer pageNumber) {
		PageRequest pgb = PageRequest.of(pageNumber - 1, 3, Sort.Direction.DESC, "writeOrUpdate");
		List<VendersShowSimpleDTO> resultDTO = new ArrayList<>();
		List<VenderInformation> result = vIRe.findAll(pgb).getContent();
		for (VenderInformation vi : result) {
			VendersShowSimpleDTO vDTO = new VendersShowSimpleDTO();
			Optional<Vender> option = vedRepo.findById(vi.getVenderId());
			List<VenderImg> vp = vPrepo.findByVenderid(vi.getVenderId());
			if (!vp.isEmpty()) {
				VenderImg firstVenderImg = vp.get(0);
				vDTO.setShowphoto(firstVenderImg.getVenderImg());
			}
			vDTO.setVenderId(vi.getVenderId());
			vDTO.setVenderIntroduce(vi.getVenderIntroduce());
			vDTO.setVenderName(option.map(Vender::getUsername).orElse(null));
			vDTO.setWriteOrUpdate(vi.getWriteOrUpdate());

			resultDTO.add(vDTO);
		}
		return resultDTO;
	}

	public Integer showInfoTotalPages() {
		PageRequest pgb = PageRequest.of(0, 3, Sort.Direction.DESC, "writeOrUpdate");
		return vIRe.findAll(pgb).getTotalPages();
	}

	public List<VendersShowSimpleDTO> showOtherSimpleInfo(Integer venderId) {
		List<VendersShowSimpleDTO> resultDTO = new ArrayList<>();
		List<VenderInformation> result = vIRe.otherVenders(venderId);
		int i = 0;
		for (VenderInformation vi : result) {
			VendersShowSimpleDTO vDTO = new VendersShowSimpleDTO();
			Optional<Vender> option = vedRepo.findById(vi.getVenderId());
			List<VenderImg> vp = vPrepo.findByVenderid(vi.getVenderId());
			if (!vp.isEmpty()) {
				VenderImg firstVenderImg = vp.get(0);
				vDTO.setShowphoto(firstVenderImg.getVenderImg());
			}
			vDTO.setVenderId(vi.getVenderId());
			vDTO.setVenderName(option.map(Vender::getUsername).orElse(null));
			vDTO.setWriteOrUpdate(vi.getWriteOrUpdate());
			resultDTO.add(vDTO);
			if (i == 2) {
				break;
			}
			i++;
		}
		return resultDTO;
	}

	public VendersShowSimpleDTO showSimpleInfo(Integer venderId) {
		VendersShowSimpleDTO vDTO = new VendersShowSimpleDTO();
		Optional<VenderInformation> ovi = vIRe.findById(venderId);
		Optional<Vender> option = vedRepo.findById(venderId);

		vDTO.setVenderId(venderId);
		vDTO.setVenderIntroduce(ovi.map(VenderInformation::getVenderIntroduce).orElse(null));
		vDTO.setVenderName(option.map(Vender::getUsername).orElse(null));
		vDTO.setWriteOrUpdate(ovi.map(VenderInformation::getWriteOrUpdate).orElse(null));

		return vDTO;
	}

	public VenderInformationDTO showInfo(Integer venderId) {
		VenderInformation vi = vIRe.findById(venderId).orElse(null);
		return vi != null ? new VenderInformationDTO(vi) : null;
	}

	public String findacct(Integer venderId) {
		return vedRepo.findById(venderId).map(Vender::getAcct).orElse(null);
	}

	public String findName(Integer venderId) {
		return vedRepo.findById(venderId).map(Vender::getUsername).orElse(null);
	}

	public Vender findByVenderAcct(String venAcct) {
		return vedRepo.findVenderByAcct(venAcct);
	}

	public List<String> findImgsById(Integer venderId) {
		List<String> result = new ArrayList<>();
		List<VenderImg> findByVenderid = vPrepo.findByVenderid(venderId);
		for (VenderImg venderImg : findByVenderid) {
			String photoUrl = venderImg.getVenderImg();
			result.add(photoUrl);
		}
		return result;
	}

}
