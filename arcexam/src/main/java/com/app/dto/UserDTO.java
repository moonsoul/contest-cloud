package com.app.dto;

import com.app.entity.Tbuser;

import java.util.List;

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

	// 查询条件
	private String searchusercode = ""; // 查询条件：用户编号
	private String searchusername = ""; // 查询条件：用户姓名
	private String searchgroupid = ""; // 查询条件：所属机构
	private String searchchildflag = "0"; // 查询条件：是否包含子机构
	private String searchstatus = ""; // 查询条件：有效状态
	private String searchusertype = "";

	private Long deletestatus;

	private Long integration;
	private Long rank;
	private String orderField;
	private String orderDirection;

	private String groupname;

	private String groupid;

	private String remark;

	private List<GroupDTO> grouplist;

	public UserDTO() {
	}

	public UserDTO(String userid) {
		super(userid);
	}

	public List<GroupDTO> getGrouplist() {
		return grouplist;
	}

	public void setGrouplist(List<GroupDTO> grouplist) {
		this.grouplist = grouplist;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Long getIntegration() {
		return integration;
	}

	public void setIntegration(Long integration) {
		this.integration = integration;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}


	public Long getDeletestatus() {
		return deletestatus;
	}

	public void setDeletestatus(Long deletestatus) {
		this.deletestatus = deletestatus;
	}

	public String getSearchusercode() {
		return searchusercode;
	}

	public void setSearchusercode(String searchusercode) {
		this.searchusercode = searchusercode;
	}

	public String getSearchusername() {
		return searchusername;
	}

	public void setSearchusername(String searchusername) {
		this.searchusername = searchusername;
	}

	public String getSearchgroupid() {
		return searchgroupid;
	}

	public void setSearchgroupid(String searchgroupid) {
		this.searchgroupid = searchgroupid;
	}

	public String getSearchchildflag() {
		return searchchildflag;
	}

	public void setSearchchildflag(String searchchildflag) {
		this.searchchildflag = searchchildflag;
	}

	public String getSearchstatus() {
		return searchstatus;
	}

	public void setSearchstatus(String searchstatus) {
		this.searchstatus = searchstatus;
	}

	public String getSearchusertype() {
		return searchusertype;
	}

	public void setSearchusertype(String searchusertype) {
		this.searchusertype = searchusertype;
	}

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
