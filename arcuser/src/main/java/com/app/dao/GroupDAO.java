package com.app.dao;

import com.app.common.JDBCBaseDao;
import com.app.dto.GroupDTO;
import com.app.dto.GroupTreeDTO;
import com.app.entity.Tbgroup;
import com.app.utils.ZTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构管理数据层实现类，包括机构查询、新增、修改、删除、批量导入等功能
 * 
 * @author Lian
 * @create on 2010-09-27
 */
@Component
public class GroupDAO {
	@Autowired
	private JDBCBaseDao JDBCBaseDao;

	private final static String SQLXML = "newGroupUser_sql.xml";

	/**
	 * 新增机构信息操作
	 * 
	 * @param tbgroup
	 * @return
	 */
	public String addGroup(Tbgroup tbgroup) {

		return JDBCBaseDao.insertEntity(tbgroup);
	}

	/**
	 * 修改机构信息操作
	 * 
	 * @param tbgroup
	 */
	public void updateGroup(Tbgroup tbgroup) {

		JDBCBaseDao.updateEntityByID(tbgroup);
	}

	/**
	 * 删除机构信息操作（逻辑删除操作）
	 * 
	 * @param tbgroup
	 * @return
	 */
	public int deleteGroup(Tbgroup tbgroup) {

		// Map map = new HashMap();
		//
		// map.put("groupid", tbgroup.getGroupid());
		//
		// String sql = super.getSqlFactory().getSql(SQLXML, "groupmanage_deletegroupbyid", map);
		//
		// int n = super.getBaseJdbcTemplate().update(sql);
		// add by zhaoyuyang 20150518 物理删除
		String sql = "delete from tbgroup where groupid='" + tbgroup.getGroupid() + "'";
		int n = JDBCBaseDao.getBaseJdbcTemplate().update(sql);
		return n;
	}

	/**
	 * 根据机构ID获得机构信息实体
	 *
	 */
	public Tbgroup getGroupInfoById(String groupid) {

		Tbgroup tbgroup = new Tbgroup();
		tbgroup.setGroupid(groupid);

		return (Tbgroup) JDBCBaseDao.queryEntityByID(tbgroup);
	}

	/**
	 * 根据机构父节点ID获得新增机构的location
	 *
	 */
	public int getNextLocationByParentId(String parentidstr) {

		int nextlocationint = 1;

		Map map = new HashMap();

		map.put("parentid", parentidstr);

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_getNextLocationByParentId", map);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, GroupDTO.class);

		if (list.size() > 0) {
			nextlocationint = ((GroupDTO) list.get(0)).getLocation().intValue() + 1;
		}

		return nextlocationint;
	}

	/**
	 * 根据机构ID获得机构信息实体(用于显示机构详情)
	 * 
	 * @param tbgroup
	 * @return
	 */
	public GroupDTO getGroupInfoByIdForDisplay(Tbgroup tbgroup) {

		Map map = new HashMap();

		if (null == tbgroup.getGroupid() || StringUtils.isEmpty(tbgroup.getGroupid().trim())) {
			tbgroup.setGroupid("");
		}

		map.put("groupid", tbgroup.getGroupid());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_getGroupInfoByIdForDisplay", map);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, GroupDTO.class);

		GroupDTO dto = (GroupDTO) list.get(0);

		// 判断其所属类型
		if (dto.getType() == 1) {
			dto.setTypename("年级");
		} else {
			dto.setTypename("班级");
		}

		return dto;
	}

	/**
	 * 判断机构编号唯一性
	 * 
	 * @param tbgroup
	 * @return
	 */
	public boolean checkGroupCodeSole(Tbgroup tbgroup) {

		boolean bool = true;

		Map map = new HashMap();

		map.put("groupcode", tbgroup.getGroupcode());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_checkGroupCodeSole", map);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForList(sql);

		if (null != list && list.size() > 0) {
			bool = false;
		}

		return bool;
	}

	/**
	 * 判断机构是否处于被使用的状态（判断人员信息中是否正在使用该机构）
	 * 
	 * @param tbgroup
	 * @return
	 */
	public boolean checkGroupUsed(Tbgroup tbgroup) {

		boolean bool = false;

		Map map = new HashMap();

		map.put("groupid", tbgroup.getGroupid());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_checkGroupUsed", map);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForList(sql);

		if (null != list && list.size() > 0) {
			bool = true;
		}

		return bool;
	}

	/**
	 * 判断机构类型是否正确（父节点类型为“部门”时，其所属类型只能选择"部门"）
	 * 
	 * @param tbgroup
	 * @return
	 */
	public boolean checkGroupType(Tbgroup tbgroup) {

		boolean bool = true;
		String type = String.valueOf(tbgroup.getType());

		if (type.trim().equals("1")) {
			Map map = new HashMap();
			map.put("groupid", tbgroup.getParentid());

			String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_checkGroupType", map);
			List list = JDBCBaseDao.getBaseJdbcTemplate().queryForList(sql);

			if (null != list && list.size() > 0) {
				bool = false;
			}
		}

		return bool;
	}

	/**
	 * 判断部门节点下是否有子节点（用于在删除时进行判断，如果有子节点，则不能进行删除）
	 * 
	 * @param tbgroup
	 * @return
	 */
	public boolean checkGroupHasChild(Tbgroup tbgroup) {

		boolean bool = false;

		Map map = new HashMap();
		map.put("groupid", tbgroup.getGroupid());

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_checkGroupHasChild", map);
		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForList(sql);

		if (null != list && list.size() > 0) {
			bool = true;
		}

		return bool;
	}

	/**
	 * 根据根节点获得所有该根节点下的所有子节点（机构树节点）
	 * 
	 * @param id
	 * @return
	 */
	public List getGroupChildrenNode(String id) {

		Map map = new HashMap();
		map.put("parentid", id);

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_getGroupChildrenNode", map);

		return JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, GroupTreeDTO.class);
	}

	/**
	 * 根据父机构编号获得父机构ID
	 * 
	 * @param parentcodestr
	 * @return
	 */
	public String getParentIdByParentCode(String parentcodestr) {

		String parendidstr = "";

		Map map = new HashMap();
		map.put("groupcode", parentcodestr);

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_getParentIdByParentCode", map);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, GroupDTO.class);

		if (list.size() > 0) {
			GroupDTO dto = (GroupDTO) list.get(0);
			parendidstr = dto.getGroupid();
		}

		return parendidstr;
	}

	/**
	 * 根据机构编号查询得到机构实体 add by zhaoyuyang 2010.10.19
	 * 
	 * @param groupCode
	 * @return
	 */
	public Tbgroup getGroupByGroupCode(String groupCode) {

		Map map = new HashMap();
		map.put("groupcode", groupCode);

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_getGroupByGroupCode", map);

		List list = JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, Tbgroup.class);

		if (list.isEmpty()) {
			return null;
		} else {
			return (Tbgroup) list.get(0);
		}
	}

	/**
	 * 批量增加机构
	 * 
	 * @param sqls
	 * @return
	 */
	public int[] batchInsertGroups(String[] sqls) {

		return JDBCBaseDao.getBaseJdbcTemplate().batchInsert(sqls);
	}

	/**
	 * 移动部门所在的顺序
	 * 
	 * @param groupid
	 * @param parentid
	 * @param selfindex
	 * @param targetindex
	 */
	public void updateGroupLocation(String groupid, String parentid, int selfindex, int targetindex) {

		Map map = new HashMap();
		map.put("selfID", groupid);
		map.put("targetIndex", targetindex);
		map.put("parentID", parentid);
		map.put("selfIndex", selfindex);

		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_changeGroupLocation", map);

		JDBCBaseDao.getBaseJdbcTemplate().update(sql);
	}

	public List queryAllgroup() {
		Map map = new HashMap();
		map.put("where", " where deletestatus!=0");
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_queryAllgroup", map);
		return JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, Tbgroup.class);
	}

	public List queryZTreeList() {
		Map map = new HashMap();
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where deletestatus!=0 and groupid!='9001'");
		map.put("where", whereSql.toString());
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_queryZTreeList", map);
		return JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, ZTree.class);
	}

	public List queryZTreeList2LastNodeIsHidden() {
		Map map = new HashMap();
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where deletestatus!=0 and type !=2 ");
		map.put("where", whereSql.toString());
		String sql = JDBCBaseDao.getSqlFactory().getSql(SQLXML, "groupmanage_queryZTreeList2LastNodeIsHidden", map);
		return JDBCBaseDao.getBaseJdbcTemplate().queryForObjectList(sql, ZTree.class);
	}

}
