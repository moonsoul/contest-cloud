package com.app.dao;

import com.app.common.ContainsKeys;
import com.app.common.JDBCBaseDao;
import com.app.dto.UserDTO;
import com.app.entity.Tbuser;
import com.app.utils.PubStringUtil;
import com.qq.base.common.Page;
import com.qq.base.web.PageCommon;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDAO {
	@Autowired
	private JDBCBaseDao JDBCBaseDao;

	private final static String SQLXML = "newGroupUser_sql.xml";


	//////
	public UserDTO getUserInfoByUsercode(Map usermap) {
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getUserInfoByUsercode", usermap);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, UserDTO.class);

		if (list.isEmpty()) {
			return null;
		} else {
			return (UserDTO) list.get(0);
		}
	}

	public UserDTO getUserInfoByUsercode(String usercode) {
		Map usermap = new HashMap();
		usermap.put("usercode", usercode);
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getUserInfoByUsercode", usermap);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, UserDTO.class);

		if (list.isEmpty()) {
			return null;
		} else {
			return (UserDTO) list.get(0);
		}
	}

	public String updateAuthkeyByUserid(Tbuser tbuser) {
		JDBCBaseDao.getBaseJdbcTemplate().updateEntity(tbuser);

		return ContainsKeys.RET_OK;
	}

	public UserDTO getUserInfoByUserid(Map usermap) {
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getUserInfoByUserid", usermap);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, UserDTO.class);

		if (list.isEmpty()) {
			return null;
		} else {
			return (UserDTO) list.get(0);
		}
	}

	public void resetpassword(Tbuser userdto) {
		Map map = new HashMap();
		map.put("userid", userdto.getUserid());
		map.put("password", userdto.getPassword());
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_resetpassword", map);
		JDBCBaseDao.getBaseJdbcTemplate().update(sql);
	}


	/**
	 * 条件查询用户记录信息
	 *
	 * @param info
	 * @return
	 */
	public Page queryUserList(PageCommon info) {

		UserDTO userdto = (UserDTO) info.getFilterObj();

		Map map = new HashMap();

		StringBuffer whereSql = new StringBuffer();

		// 判断查询条件“用户编号”是否为空
		if (StringUtils.isNotEmpty(userdto.getSearchusercode().trim())) {
			whereSql.append(" AND tu.usercode LIKE '%");
			whereSql.append(userdto.getSearchusercode().trim());
			whereSql.append("%'");
		}

		// 判断查询条件“用户姓名”是否为空
		if (StringUtils.isNotEmpty(userdto.getSearchusername().trim())) {
			whereSql.append(" AND tu.username LIKE '%");
			whereSql.append(userdto.getSearchusername().trim());
			whereSql.append("%'");
		}

		// 判断查询条件“所属机构”是否为空
		if (StringUtils.isNotEmpty(userdto.getSearchgroupid().trim()) && !"9001".equals(userdto.getSearchgroupid())) {
/*
			// 判断查询条件“是否包含所属子机构”是否为空
			if (StringUtils.isNotEmpty(userdto.getSearchchildflag().trim())) {
				// 判断是否为“包含”
				if (userdto.getSearchchildflag().trim().equals("1")) {
					map.put("tggroupfrom", "(SELECT groupid,groupname FROM tbgroup CONNECT"
							+ " BY PRIOR groupid = parentid START WITH groupid = '" + userdto.getSearchgroupid().trim()
							+ "')");
				} else {
					whereSql.append(" AND tu.groupid='");
					whereSql.append(userdto.getSearchgroupid().trim());
					whereSql.append("'");
					map.put("tggroupfrom", "tbgroup");
				}
			} else {
				whereSql.append(" AND tu.groupid='");
				whereSql.append(userdto.getSearchgroupid().trim());
				whereSql.append("'");
				map.put("tggroupfrom", "tbgroup");
			}*/

			whereSql.append(" AND tg.groupid='");
			whereSql.append(userdto.getSearchgroupid().trim());
			whereSql.append("'");
		}

		// 判断查询条件“有效状态”是否为空(激活状态)
		if (StringUtils.isNotEmpty(userdto.getSearchstatus().trim())) {
			whereSql.append(" AND tu.status=");
			whereSql.append(userdto.getSearchstatus().trim());
		}

		// 判断查询条件“用户类型”是否为空(激活状态)
		if (StringUtils.isNotEmpty(userdto.getSearchusertype())) {
			whereSql.append(" AND tu.usertype=");
			whereSql.append(userdto.getSearchusertype());
		}
		// 剔除超级管理员
		whereSql.append("  AND tu.userid !='9001' ");
		// 剔除已删除的记录
		whereSql.append(" AND tu.deletestatus=1 ");

		StringBuffer whereSql_ = new StringBuffer();
		// 排序(按时间倒序)
		whereSql_.append(" ORDER BY tu.userid DESC ");

		String orderDirection = "";
		if (StringUtils.isNotEmpty(userdto.getOrderField())) {
			if (StringUtils.isEmpty(userdto.getOrderDirection())) {
				orderDirection = "asc";
			} else {
				orderDirection = userdto.getOrderDirection();
			}

			whereSql_.append(" ,tu.").append(userdto.getOrderField()).append(" " + orderDirection);
		}

		map.put("wheresql", whereSql);
		map.put("wheresql_1", whereSql_);

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_pagedQuery", map);

		return JDBCBaseDao.pagedQuery(sql, info);
	}


	public String addUser(UserDTO userdto) {

		// 将email移动到主表且为必填项，同时将生日调整为选填项 modify by lianyg 2010-11-22
		Map usermap = new HashMap();
		usermap.put("userid", userdto.getUserid());
		usermap.put("usercode", userdto.getUsercode());
		usermap.put("username", userdto.getUsername());
		usermap.put("password", userdto.getPassword());
		usermap.put("photo", userdto.getPhoto());
		usermap.put("groupid", userdto.getGroupid());
		usermap.put("sex", userdto.getSex());
		usermap.put("birthday", PubStringUtil.isNull(userdto.getBirthday()) ? "-" : userdto.getBirthday());
		usermap.put("position", PubStringUtil.isNull(userdto.getPosition()) ? "-" : userdto.getPosition());
		usermap.put("deletestatus", userdto.getDeletestatus());
		usermap.put("creatorid", userdto.getCreatorid());
		usermap.put("createtime", userdto.getCreatetime());
		usermap.put("status", userdto.getStatus());
		usermap.put("email", PubStringUtil.isNull(userdto.getEmail()) ? "-" : userdto.getEmail());
		usermap.put("usertype", userdto.getUsertype());
		String usersql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_insertuser", usermap);
		int n = JDBCBaseDao.getBaseJdbcTemplate().update(usersql);

		/*
		Map attachmap = new HashMap();
		attachmap.put("userid", userdto.getUserid());
		attachmap.put("nation", userdto.getNation());
		attachmap.put("graduate", userdto.getGraduate());
		attachmap.put("education", userdto.getEducation());
		attachmap.put("speciality", userdto.getSpeciality());
		attachmap.put("degree", userdto.getDegree());
		attachmap.put("polity", userdto.getPolity());
		attachmap.put("phone", userdto.getPhone());
		attachmap.put("credentialstype", userdto.getCredentialstype());
		attachmap.put("credentialscode", userdto.getCredentialscode());
		attachmap.put("address", userdto.getAddress());
		attachmap.put("postalcode", userdto.getPostalcode());
		attachmap.put("boarddate", userdto.getBoarddate());
		attachmap.put("remark", userdto.getRemark());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_insertuserattach", attachmap);

		int n = JDBCBaseDao.getBaseJdbcTemplate().update(sql);*/

		return String.valueOf(n);
	}

	public int isDisplayByUserCode(String usercode) {
		Map attachmap = new HashMap();
		attachmap.put("usercode", usercode);

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_isDisplayByUserCode", attachmap);

		int n = JDBCBaseDao.getBaseJdbcTemplate().queryForInt(sql);
		return n;
	}

	public void updateUser(UserDTO userdto) {

		Map usermap = new HashMap();
		usermap.put("userid", userdto.getUserid());
		usermap.put("username", userdto.getUsername());
		usermap.put("photo", userdto.getPhoto());
		usermap.put("sex", userdto.getSex());
		usermap.put("birthday", PubStringUtil.isNull(userdto.getBirthday()) ? "-" : userdto.getBirthday());
		usermap.put("position", PubStringUtil.isNull(userdto.getPosition()) ? "-" : userdto.getPosition());
		usermap.put("modifytime", userdto.getModifytime());
		usermap.put("email", PubStringUtil.isNull(userdto.getEmail()) ? "-" : userdto.getEmail());
		usermap.put("groupid", userdto.getGroupid());
		usermap.put("usertype", userdto.getUsertype() == null ? Long.valueOf(0) : userdto.getUsertype());

		// 判断密码是否需要修改，如果需要修改，则将MD5转化后的密码更新到语句中
		if (StringUtils.isNotEmpty(userdto.getPassword())) {
			usermap.put("passwordsql", ",password='" + userdto.getPassword().trim() + "'");
		}
		// 如果密码没有修改，则语句补充为空
		else {
			usermap.put("passwordsql", "");
		}

		// 判断照片是否需要修改
		if (StringUtils.isNotEmpty(userdto.getPhoto())) {
			usermap.put("photosql", ",photo='" + userdto.getPhoto().trim() + "'");
		}
		// 如果照片没有修改，则语句补充为空
		else {
			usermap.put("photosql", "");
		}

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_updateuser", usermap);
		JDBCBaseDao.getBaseJdbcTemplate().update(sql);

		/*
		Map attachmap = new HashMap();
		attachmap.put("userid", userdto.getUserid());
		attachmap.put("nation", userdto.getNation());
		attachmap.put("graduate", userdto.getGraduate());
		attachmap.put("education", userdto.getEducation());
		attachmap.put("speciality", userdto.getSpeciality());
		attachmap.put("degree", userdto.getDegree());
		attachmap.put("polity", userdto.getPolity());
		attachmap.put("phone", userdto.getPhone());
		attachmap.put("credentialstype", userdto.getCredentialstype());
		attachmap.put("credentialscode", userdto.getCredentialscode());
		attachmap.put("address", userdto.getAddress());
		attachmap.put("postalcode", userdto.getPostalcode());
		attachmap.put("boarddate", userdto.getBoarddate());
		attachmap.put("remark", userdto.getRemark());

		String attachsql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_updateuserattach", attachmap);

		JDBCBaseDao.getBaseJdbcTemplate().update(attachsql);*/
	}

	public UserDTO getUserInfoById(UserDTO userdto) {

		Map map = new HashMap();
		map.put("userid", userdto.getUserid());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getUserInfoById", map);

		Map resultmap = JDBCBaseDao.getBaseJdbcTemplate().queryForMap(sql);

		userdto.setUserid((String) resultmap.get("USERID"));
		userdto.setUsercode((String) resultmap.get("USERCODE"));
		userdto.setUsername((String) resultmap.get("USERNAME"));
		userdto.setSex(((BigDecimal) resultmap.get("SEX")).longValue());
		userdto.setBirthday((String) resultmap.get("BIRTHDAY"));
		userdto.setPosition((String) resultmap.get("POSITION"));
//		userdto.setNation((String) resultmap.get("NATION"));
//		userdto.setGraduate((String) resultmap.get("GRADUATE"));
//		userdto.setEducation((String) resultmap.get("EDUCATION"));
//		userdto.setSpeciality((String) resultmap.get("SPECIALITY"));
//		userdto.setDegree((String) resultmap.get("DEGREE"));
//		userdto.setPolity((String) resultmap.get("POLITY"));
//		userdto.setPhone((String) resultmap.get("PHONE"));
//		userdto.setCredentialstype((String) resultmap.get("CREDENTIALSTYPE"));
//		userdto.setCredentialscode((String) resultmap.get("CREDENTIALSCODE"));
//		userdto.setAddress((String) resultmap.get("ADDRESS"));
//		userdto.setPostalcode((String) resultmap.get("POSTALCODE"));POSTALCODE
		userdto.setEmail((String) resultmap.get("EMAIL"));
		//userdto.setBoarddate((String) resultmap.get("BOARDDATE"));
		//userdto.setRemark((String) resultmap.get("REMARK"));
		userdto.setPhoto((String) resultmap.get("PHOTO"));
		userdto.setIntegration(Long.valueOf(resultmap.get("integration").toString()));
		userdto.setGroupid((String) resultmap.get("GROUPID"));
		userdto.setUsertype(Long.valueOf(resultmap.get("USERTYPE").toString()));

		return userdto;
	}

	public int deleteUser(Tbuser tbuser) {

		int numb = 0;

		if (!tbuser.getUserid().trim().equals("9001")) {
			// Map map = new HashMap();
			// map.put("userid", tbuser.getUserid());
			//
			//
			// String sql = super.getSqlFactory().getSql(SQLXML, "usermanage_deleteuserbyid", map);
			//
			// numb = super.getBaseJdbcTemplate().update(sql);
			// add by zhaoyuyang 20150518 物理删除
			String desql = "delete from tbuser where userid='" + tbuser.getUserid() + "'";
			JDBCBaseDao.getBaseJdbcTemplate().update(desql);
		}

		return numb;
	}

	public void updateUserJiFen(Tbuser user) {
		Map map = new BeanMap(user);
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_updateUserIntegration", map);
		JDBCBaseDao.getBaseJdbcTemplate().update(sql);
	}

	public int[] batchDeleteUsers(String[] sqls) {
		return JDBCBaseDao.getBaseJdbcTemplate().batchInsert(sqls);
	}

	/**
	 * 改变用户的激活状态（有效状态）
	 *
	 * @param tbuser
	 * @return
	 */
	public int changeUserStatus(Tbuser tbuser) {
		int numb = 0;
		if (!tbuser.getUserid().trim().equals("9001")) {
			Map map = new HashMap();
			map.put("userid", tbuser.getUserid());
			map.put("status", tbuser.getStatus());

			String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_changeUserStatus", map);
			numb = JDBCBaseDao.getBaseJdbcTemplate().update(sql);
		}

		return numb;
	}

	/**
	 * 根据用户ID获得用户信息实体（用于单条信息展示）
	 *
	 * @param tbuser
	 * @return
	 */
	public UserDTO getUserInfoByIdForDisplay(Tbuser tbuser) {
		Map map = new HashMap();
		map.put("userid", tbuser.getUserid());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getUserInfoByIdForDisplay", map);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, UserDTO.class);

		UserDTO dto = null;
		// 判断获得记录是否为空
		if (list.size() > 0) {
			dto = (UserDTO) list.get(0);
		}

		return dto;
	}

	public int[] batchInsertUsers(String[] sqls) {
		return JDBCBaseDao.getBaseJdbcTemplate().batchInsert(sqls);
	}

	public int[] batchInsertUsersAndUserGroups(String[] sqls) {
		return JDBCBaseDao.getBaseJdbcTemplate().batchInsert(sqls);
	}

	public Page getUserListPaiMing(PageCommon info) {
		UserDTO userdto = (UserDTO) info.getFilterObj();

		Map map = new HashMap();

		StringBuffer whereSql = new StringBuffer();

		// 判断查询条件“用户编号”是否为空
		if (StringUtils.isNotEmpty(userdto.getSearchusercode().trim())) {
			whereSql.append(" AND tt.usercode LIKE '%");
			whereSql.append(userdto.getSearchusercode().trim());
			whereSql.append("%'");
		}

		// 判断查询条件“用户姓名”是否为空
		if (StringUtils.isNotEmpty(userdto.getSearchusername().trim())) {
			whereSql.append(" AND tt.username LIKE '%");
			whereSql.append(userdto.getSearchusername().trim());
			whereSql.append("%'");
		}

		// 判断查询条件“所属机构”是否为空
		// if (StringUtils.isNotEmpty(userdto.getSearchgroupid().trim())) {
		// // 判断查询条件“是否包含所属子机构”是否为空
		// if (StringUtils.isNotEmpty(userdto.getSearchchildflag().trim())) {
		// // 判断是否为“包含”
		// if (userdto.getSearchchildflag().trim().equals("1")) {
		// map.put("tggroupfrom", "(SELECT groupid,groupname FROM tbgroup CONNECT"
		// + " BY PRIOR groupid = parentid START WITH groupid = '" + userdto.getSearchgroupid().trim()
		// + "')");
		// } else {
		// whereSql.append(" AND tu.groupid='");
		// whereSql.append(userdto.getSearchgroupid().trim());
		// whereSql.append("'");
		// map.put("tggroupfrom", "tbgroup");
		// }
		// } else {
		// whereSql.append(" AND tu.groupid='");
		// whereSql.append(userdto.getSearchgroupid().trim());
		// whereSql.append("'");
		// map.put("tggroupfrom", "tbgroup");
		// }
		// } else {
		// map.put("tggroupfrom", "tbgroup");
		// }

		// 判断查询条件“有效状态”是否为空(激活状态)
		if (StringUtils.isNotEmpty(userdto.getSearchstatus().trim())) {
			whereSql.append(" AND tt.status=");
			whereSql.append(userdto.getSearchstatus().trim());
		}

		// 剔除超级管理员
		// whereSql.append("  AND tu.userid !='9001' ");
		// 剔除已删除的记录
		// whereSql.append(" AND tu.deletestatus=1 ");
		// 排序(按时间倒序)
		// whereSql.append(" ORDER BY tu.createtime DESC ");

		// map.put("wheresql", whereSql);

		// 判断查询条件“所属机构”是否为空

		map.put("groupsql", whereSql);
		map.put("groupid", userdto.getSearchgroupid());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getUserListPaiMing", map);

		return JDBCBaseDao.pagedQuery(sql, info);
	}

	public Page getUserListPaiMingByBanJi(PageCommon info) {
		UserDTO userdto = (UserDTO) info.getFilterObj();

		Map map = new HashMap();

		StringBuffer whereSql = new StringBuffer();

		// 判断查询条件“用户编号”是否为空
		if (StringUtils.isNotEmpty(userdto.getSearchusercode().trim())) {
			whereSql.append(" AND tt.usercode LIKE '%");
			whereSql.append(userdto.getSearchusercode().trim());
			whereSql.append("%'");
		}

		// 判断查询条件“用户姓名”是否为空
		if (StringUtils.isNotEmpty(userdto.getSearchusername().trim())) {
			whereSql.append(" AND tt.username LIKE '%");
			whereSql.append(userdto.getSearchusername().trim());
			whereSql.append("%'");
		}

		// 判断查询条件“所属机构”是否为空
		// if (StringUtils.isNotEmpty(userdto.getSearchgroupid().trim())) {
		// // 判断查询条件“是否包含所属子机构”是否为空
		// if (StringUtils.isNotEmpty(userdto.getSearchchildflag().trim())) {
		// // 判断是否为“包含”
		// if (userdto.getSearchchildflag().trim().equals("1")) {
		// map.put("tggroupfrom", "(SELECT groupid,groupname FROM tbgroup CONNECT"
		// + " BY PRIOR groupid = parentid START WITH groupid = '" + userdto.getSearchgroupid().trim()
		// + "')");
		// } else {
		// whereSql.append(" AND tu.groupid='");
		// whereSql.append(userdto.getSearchgroupid().trim());
		// whereSql.append("'");
		// map.put("tggroupfrom", "tbgroup");
		// }
		// } else {
		// whereSql.append(" AND tu.groupid='");
		// whereSql.append(userdto.getSearchgroupid().trim());
		// whereSql.append("'");
		// map.put("tggroupfrom", "tbgroup");
		// }
		// } else {
		// map.put("tggroupfrom", "tbgroup");
		// }

		// 判断查询条件“有效状态”是否为空(激活状态)
		if (StringUtils.isNotEmpty(userdto.getSearchstatus().trim())) {
			whereSql.append(" AND tt.status=");
			whereSql.append(userdto.getSearchstatus().trim());
		}

		// 剔除超级管理员
		// whereSql.append("  AND tu.userid !='9001' ");
		// 剔除已删除的记录
		// whereSql.append(" AND tu.deletestatus=1 ");
		// 排序(按时间倒序)
		// whereSql.append(" ORDER BY tu.createtime DESC ");

		// map.put("wheresql", whereSql);

		// 判断查询条件“所属机构”是否为空

		map.put("groupsql", whereSql);
		map.put("groupid", userdto.getSearchgroupid());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getUserListPaiMingByBanJi", map);

		return JDBCBaseDao.pagedQuery(sql, info);
	}

	public Page getUserListPaiMingByNianJi(PageCommon info) {
		UserDTO userdto = (UserDTO) info.getFilterObj();

		Map map = new HashMap();

		StringBuffer whereSql = new StringBuffer();

		// 判断查询条件“用户编号”是否为空
		if (StringUtils.isNotEmpty(userdto.getSearchusercode().trim())) {
			whereSql.append(" AND tt.usercode LIKE '%");
			whereSql.append(userdto.getSearchusercode().trim());
			whereSql.append("%'");
		}

		// 判断查询条件“用户姓名”是否为空
		if (StringUtils.isNotEmpty(userdto.getSearchusername().trim())) {
			whereSql.append(" AND tt.username LIKE '%");
			whereSql.append(userdto.getSearchusername().trim());
			whereSql.append("%'");
		}

		// 判断查询条件“有效状态”是否为空(激活状态)
		if (StringUtils.isNotEmpty(userdto.getSearchstatus().trim())) {
			whereSql.append(" AND tt.status=");
			whereSql.append(userdto.getSearchstatus().trim());
		}

		// 剔除超级管理员
		// whereSql.append("  AND tu.userid !='9001' ");
		// 剔除已删除的记录
		// whereSql.append(" AND tu.deletestatus=1 ");
		// 排序(按时间倒序)
		// whereSql.append(" ORDER BY tu.createtime DESC ");

		// map.put("wheresql", whereSql);

		// 判断查询条件“所属机构”是否为空
		map.put("groupsql", whereSql);

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getUserListPaiMingByNianJi", map);

		return JDBCBaseDao.pagedQuery(sql, info);
	}

	public Long getUserPaiMingByID(String userid) {
		Map map = new HashMap();

		StringBuffer whereSql = new StringBuffer();
		map.put("userid", userid);
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getUserPaiMingByID", map);
		Long lo = JDBCBaseDao.getBaseJdbcTemplate().queryForLong(sql);
		return lo;
	}

	////////////////////
	public UserDTO getAdminAllInfoByUserInput(Map usermap) {

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getAdminAllInfoByInput", usermap);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, UserDTO.class);

		if (list.isEmpty()) {
			return null;
		} else {
			return (UserDTO) list.get(0);
		}
	}

	public UserDTO getExamStudentAllInfoByUserInput(Map usermap) {

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getExamStudentAllInfoByInput", usermap);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, UserDTO.class);

		if (list.isEmpty()) {
			return null;
		} else {
			return (UserDTO) list.get(0);
		}
	}
	
	
	public UserDTO getMonitorAllInfoByUserInput(Map usermap) {

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "usermanage_getMonitorAllInfoByInput", usermap);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, UserDTO.class);

		if (list.isEmpty()) {
			return null;
		} else {
			return (UserDTO) list.get(0);
		}
	}

}
