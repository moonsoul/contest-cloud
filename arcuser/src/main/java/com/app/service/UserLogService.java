package com.app.service;

import com.app.common.DateUtil;
import com.app.common.Identities;
import com.app.dao.UserLogDAO;
import com.app.dto.UserDTO;
import com.app.entity.Tbuserlog;
import com.qq.base.common.Page;
import com.qq.base.web.DwzPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 *
 * 
 */
@Component
public class UserLogService {

	@Autowired
	private UserLogDAO userLogDAO;

	public String addLog(Tbuserlog log) {
		return userLogDAO.addLog(log);
	}

	public void deleteLog(Tbuserlog log) {
		userLogDAO.deleteLog(log);
	}

	public Page queryLogList(DwzPage info) {

		return userLogDAO.queryLogList(info);
	}

	public void updateLog(Tbuserlog log) {
		userLogDAO.updateLog(log);

	}

	public Tbuserlog getTblogByID(String logid) {
		Tbuserlog log = new Tbuserlog(logid);
		return userLogDAO.getTblogByID(log);
	}

	/**
	 * 保存登录用户的日志信息
	 */
	public void saveLog(UserDTO user) {
		Tbuserlog log = new Tbuserlog();
		log.setLogid(Identities.uuid2());
		log.setLogintime(DateUtil.dateToString(new Date(), "yyyy-mm-dd hh24:mi:ss"));
		log.setIp(user.getIp());
		log.setOperatorid(user.getUserid());
		userLogDAO.addLog(log);
	}

	/**
	 * 更新用户日志，主要应用与退出
	 */
	public void updateLog(UserDTO user) {
		Tbuserlog log = userLogDAO.getTblogByAuthkey(user.getAuthkey());

		if (null != log) {
			log.setLogouttime(DateUtil.dateToString(new Date(), "yyyy-mm-dd hh24:mi:ss"));
			userLogDAO.updateLog(log);
		}

	}

	/**
	 * 获取上次登录时间 add by steven 2010-10-19
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getLastLoginTime(String userid) {
		return userLogDAO.getLastLoginTime(userid);
	}

	/**
	 * 保存用户登录次数 add by steven 2010-10-22
	 * 
	 * @param userid
	 * @return 登录次数
	 */
	public String saveUserLoginCountLog(String userid) {
		return userLogDAO.saveUserLoginCountLog(userid);
	}

}
