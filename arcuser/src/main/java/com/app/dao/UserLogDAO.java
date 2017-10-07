package com.app.dao;

import com.app.common.JDBCBaseDao;
import com.app.dto.UserLogDTO;
import com.app.entity.Tbuserlog;
import com.qq.base.common.Page;
import com.qq.base.web.DwzPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserLogDAO {
	@Autowired
	private JDBCBaseDao JDBCBaseDao;

	private final static String SQLXML = "newSystem_sql.xml";

	public String addLog(Tbuserlog log) {
		return JDBCBaseDao.insertEntity(log);
	}

	public void deleteLog(Tbuserlog log) {
		JDBCBaseDao.deleteEntityByID(log);
	}

	public Page queryLogList(DwzPage pageInfo) {

		UserLogDTO log = (UserLogDTO) pageInfo.getFilterObj();

		Map map = new HashMap();

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where 1=1 ");
		if (StringUtils.isNotEmpty(log.getLogintime())) {

			whereSql.append(" and tlg.logintime like '%").append(log.getLogintime()).append("%'");
		}
		if (StringUtils.isNotEmpty(log.getLogouttime())) {

			whereSql.append(" and tlg.logouttime like '%").append(log.getLogouttime()).append("%'");
		}
		if (null != log.getUsername() && StringUtils.isNotEmpty(log.getUsername().trim())) {

			whereSql.append(" and u.username like '%").append(log.getUsername().trim()).append("%'");
		}
		if (null != log.getIp() && StringUtils.isNotEmpty(log.getIp().trim())) {

			whereSql.append(" and tlg.ip like '%").append(log.getIp().trim()).append("%'");
		}
		map.put("where", whereSql);
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "logmanage_queryLogList", map);

		return JDBCBaseDao.pagedQuery(sql, pageInfo);
	}


	/**
	 * 获取上次登录时间 add by steven 2010-10-19
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getLastLoginTime(String userid) {

		Map map = new HashMap();

		map.put("userid", userid);

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "logmanage_getLastLoginTime", map);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForList(sql);

		if (null == list || list.isEmpty()) {
			return null;
		}
		map = (Map) list.get(0);
		return map;
	}

	/**
	 * 保存用户登录次数 add by steven 2010-10-22
	 * 
	 * @param userid
	 * @return 登录次数
	 */
	@SuppressWarnings("unchecked")
	public String saveUserLoginCountLog(String userid) {
		Map map = new HashMap();
		map.put("userid", userid);
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "logmanage_saveUserLoginCountLogSelect", map);
		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForList(sql);
		int count = 0;
		if (null == list || list.size() == 0) {
			map = new HashMap();
			map.put("userid", userid);
			map.put("logincount", ++count);
			sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "logmanage_saveUserLoginCountLogInsert", map);
			JDBCBaseDao.getBaseJdbcTemplate().execute(sql);
		} else {
			Map result = (Map) list.get(0);
			count = Integer.parseInt(result.get("logincount").toString());
			map = new HashMap();
			map.put("logincount", ++count);
			map.put("userid", userid);

			sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "logmanage_saveUserLoginCountLogUpdate", map);
			JDBCBaseDao.getBaseJdbcTemplate().execute(sql);
		}
		return String.valueOf(count);
	}

	public void updateLog(Tbuserlog log) {

		JDBCBaseDao.updateEntityByID(log);

	}

	public Tbuserlog getTblogByID(Tbuserlog log) {

		return (Tbuserlog) JDBCBaseDao.queryEntityByID(log);
	}

	public Tbuserlog getTblogByAuthkey(String authkey) {
		Map map = new HashMap();
		map.put("authkey", authkey);
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "logmanage_getTblogByAuthkey", map);
		Tbuserlog log = null;
		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, Tbuserlog.class);

		if (!list.isEmpty()) {
			log = (Tbuserlog) list.get(0);
		}

		return log;
	}
}
