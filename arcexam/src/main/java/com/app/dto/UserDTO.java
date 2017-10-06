package com.app.dto;

import com.app.entity.Tbuser;

/**
 * 人员信息实体
 * 
 * @author yuanbei
 * @modify on 2016-8-29
 */
public class UserDTO extends Tbuser {
	// tbexamstudent
	private String examstuid;
	private String examid;
	private String roomid;
	private String begintime;
	private String endtime;
	private String ip;
	private Long ifchangeip;
	private String seatno;
	private Long addtime;
	private Long ifsubmit;
	private Long rightcount;
	private Long wrongcount;
	private Long nullcount;
	private Double examscore;
	private Long ticketstatus;
	private Long opennum;
	private Long practicetime;
	private String questionsort;
	private String creatorid;
	private String createtime;
	private Long kscc;

	private String roomno;
	private Long ifonline;
	
	// tbmonitor
	private String monitorid;

	public String getExamstuid() {
		return examstuid;
	}

	public void setExamstuid(String examstuid) {
		this.examstuid = examstuid;
	}

	public String getExamid() {
		return examid;
	}

	public void setExamid(String examid) {
		this.examid = examid;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getIfchangeip() {
		return ifchangeip;
	}

	public void setIfchangeip(Long ifchangeip) {
		this.ifchangeip = ifchangeip;
	}

	public String getSeatno() {
		return seatno;
	}

	public void setSeatno(String seatno) {
		this.seatno = seatno;
	}

	public Long getAddtime() {
		return addtime;
	}

	public void setAddtime(Long addtime) {
		this.addtime = addtime;
	}

	public Long getIfsubmit() {
		return ifsubmit;
	}

	public void setIfsubmit(Long ifsubmit) {
		this.ifsubmit = ifsubmit;
	}

	public Long getRightcount() {
		return rightcount;
	}

	public void setRightcount(Long rightcount) {
		this.rightcount = rightcount;
	}

	public Long getWrongcount() {
		return wrongcount;
	}

	public void setWrongcount(Long wrongcount) {
		this.wrongcount = wrongcount;
	}

	public Long getNullcount() {
		return nullcount;
	}

	public void setNullcount(Long nullcount) {
		this.nullcount = nullcount;
	}

	public Double getExamscore() {
		return examscore;
	}

	public void setExamscore(Double examscore) {
		this.examscore = examscore;
	}

	public Long getTicketstatus() {
		return ticketstatus;
	}

	public void setTicketstatus(Long ticketstatus) {
		this.ticketstatus = ticketstatus;
	}

	public Long getOpennum() {
		return opennum;
	}

	public void setOpennum(Long opennum) {
		this.opennum = opennum;
	}

	public Long getPracticetime() {
		return practicetime;
	}

	public void setPracticetime(Long practicetime) {
		this.practicetime = practicetime;
	}

	public String getQuestionsort() {
		return questionsort;
	}

	public void setQuestionsort(String questionsort) {
		this.questionsort = questionsort;
	}

	public String getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Long getKscc() {
		return kscc;
	}

	public void setKscc(Long kscc) {
		this.kscc = kscc;
	}

	public String getRoomno() {
		return roomno;
	}

	public void setRoomno(String roomno) {
		this.roomno = roomno;
	}

	public String getMonitorid() {
		return monitorid;
	}

	public void setMonitorid(String monitorid) {
		this.monitorid = monitorid;
	}

	public Long getIfonline() {
		return ifonline;
	}

	public void setIfonline(Long ifonline) {
		this.ifonline = ifonline;
	}

	

}
