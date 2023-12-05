package com.work.easystep2.DTO;

import com.work.easystep2.model.VenderInformation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenderInformationDTO {

	private String venderIntroduce;

	private String venderWorkmonth;

	private String venderStay;

	private String workMans;

	private String venderWorktime;
	
	public VenderInformationDTO() {
		
	}
	public VenderInformationDTO(VenderInformation vi) {
		this.venderIntroduce = vi.getVenderIntroduce();
		this.venderWorkmonth = vi.getVenderWorkmonth();
		this.venderStay = vi.getVenderStay();
		this.workMans = vi.getWorkMans();
		this.venderWorktime = vi.getVenderWorktime();
	}
	public String getVenderIntroduce() {
		return venderIntroduce;
	}
	public String getVenderWorkmonth() {
		return venderWorkmonth;
	}
	public String getVenderStay() {
		return venderStay;
	}
	public String getWorkMans() {
		return workMans;
	}
	public String getVenderWorktime() {
		return venderWorktime;
	}
	public void setVenderIntroduce(String venderIntroduce) {
		this.venderIntroduce = venderIntroduce;
	}
	public void setVenderWorkmonth(String venderWorkmonth) {
		this.venderWorkmonth = venderWorkmonth;
	}
	public void setVenderStay(String venderStay) {
		this.venderStay = venderStay;
	}
	public void setWorkMans(String workMans) {
		this.workMans = workMans;
	}
	public void setVenderWorktime(String venderWorktime) {
		this.venderWorktime = venderWorktime;
	}
	
}
