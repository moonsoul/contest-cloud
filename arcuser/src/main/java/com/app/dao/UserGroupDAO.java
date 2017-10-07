package com.app.dao;

import com.app.common.JDBCBaseDao;
import com.app.entity.Tbgroup;
import com.app.entity.Tbusergroup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构管理数据层实现类，包括机构查询、新增、修改、删除、批量导入等功能
 */
@Component
public class UserGroupDAO {
	@Autowired
	private JDBCBaseDao JDBCBaseDao;

	private final static String SQLXML = "newGroupUser_sql.xml";

	/**
	 * 新增机构信息操作
	 * 
	 * @param tbusergroup
	 * @return
	 */
	public String addUserGroup(Tbusergroup tbusergroup) {

		return JDBCBaseDao.insertEntity(tbusergroup);
	}

	/**
	 * 修改机构信息操作
	 * 
	 * @param tbusergroup
	 */
	public void updateUserGroup(Tbusergroup tbusergroup) {

		JDBCBaseDao.updateEntityByID(tbusergroup);
	}

	/**
	 * 删除机构信息操作（逻辑删除操作）
	 * 
	 * @param tbusergroup
	 * @return
	 */
	public int deleteUserGroup(Tbusergroup tbusergroup) {
		StringBuffer whereSql = new StringBuffer();
		// 判断查询条件“用户编号”是否为空
		if (StringUtils.isNotEmpty(tbusergroup.getUgid().trim())) {
			whereSql.append(" AND ugid = '");
			whereSql.append(tbusergroup.getUgid().trim());
			whereSql.append("'");
		}

		if (StringUtils.isNotEmpty(tbusergroup.getUserid().trim())) {
			whereSql.append(" AND userid = '");
			whereSql.append(tbusergroup.getUserid().trim());
			whereSql.append("'");
		}

		if (StringUtils.isNotEmpty(tbusergroup.getGroupid().trim())) {
			whereSql.append(" AND groupid = '");
			whereSql.append(tbusergroup.getGroupid().trim());
			whereSql.append("'");
		}

		 Map map = new HashMap();
		 map.put("where", whereSql.toString());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usergroupmanage_deleteUserGroupByConf", map);

		int n = JDBCBaseDao.getBaseJdbcTemplate().update(sql);

		return n;
	}

	public List getUserGroupInfoByConf(Tbusergroup tbusergroup){
		StringBuffer whereSql = new StringBuffer();
		// 判断查询条件“用户编号”是否为空
		if (StringUtils.isNotEmpty(tbusergroup.getUgid().trim())) {
			whereSql.append(" AND ugid = '");
			whereSql.append(tbusergroup.getUgid().trim());
			whereSql.append("'");
		}

		if (StringUtils.isNotEmpty(tbusergroup.getUserid().trim())) {
			whereSql.append(" AND userid = '");
			whereSql.append(tbusergroup.getUserid().trim());
			whereSql.append("'");
		}

		if (StringUtils.isNotEmpty(tbusergroup.getGroupid().trim())) {
			whereSql.append(" AND groupid = '");
			whereSql.append(tbusergroup.getGroupid().trim());
			whereSql.append("'");
		}

		Map map = new HashMap();
		map.put("where", whereSql.toString());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usergroupmanage_getUserGroupInfoByConf", map);

		List<Tbusergroup> list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, Tbusergroup.class);

		return list;
	}
	/**
	 * 根据机构ID获得机构信息实体
	 *
	 */
	public Tbusergroup getUserGroupInfoById(String ugid) {

		Tbusergroup tbusergroup = new Tbusergroup();
		tbusergroup.setUgid(ugid);

		return (Tbusergroup) JDBCBaseDao.queryEntityByID(tbusergroup);
	}




	/**
	 * 判断机构是否处于被使用的状态（判断人员信息中是否正在使用该机构）
	 * 
	 * @param tbusergroup
	 * @return
	 */
	public boolean checkUserGroupExisted(Tbusergroup tbusergroup) {

		boolean bool = false;

		Map map = new HashMap();
		map.put("userid", tbusergroup.getUserid());
		map.put("groupid", tbusergroup.getGroupid());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usergroupmanage_checkUserGroupExisted", map);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForList(sql);

		if (null != list && list.size() > 0) {
			bool = true;
		}

		return bool;
	}

	/**
	 * 批量增加机构
	 * 
	 * @param sqls
	 * @return
	 */
	public int[] batchInsertUserGroups(String[] sqls) {

		return JDBCBaseDao.getBaseJdbcTemplate().batchInsert(sqls);
	}

	public List<Tbgroup> getGroupInfoByUserid(String userid){
		Map map = new HashMap();
		map.put("userid", userid);
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usergroupmanage_getGroupInfoByUserid", map);

		return JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, Tbgroup.class);
	}
}
