package com.work.easystep2.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="vender_information")
public class VenderInformation {
	
	@Id
	private Integer venderId;
	
	@Column(name="vender_Introduce")
	private String venderIntroduce;
	
	@Column(name="vender_workmonth")
	private String venderWorkmonth;
	
	@Column(name="vender_stay")
	private String venderStay;
	
	@Column(name="work_mans")
	private String workMans;
	
	@Column(name="vender_worktime")
	private String venderWorktime;
	
	@Column(name="write_update")
	private Date writeOrUpdate;
	@OneToOne
	@JoinColumn(name="vender_venderid" ,unique = true)
	@MapsId
	private Vender vender;
	public Integer getVenderId() {
		return venderId;
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
	public Date getWriteOrUpdate() {
		return writeOrUpdate;
	}
	public Vender getVender() {
		return vender;
	}
	public void setVenderId(Integer venderId) {
		this.venderId = venderId;
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
	public void setWriteOrUpdate(Date writeOrUpdate) {
		this.writeOrUpdate = writeOrUpdate;
	}
	public void setVender(Vender vender) {
		this.vender = vender;
	}

}
