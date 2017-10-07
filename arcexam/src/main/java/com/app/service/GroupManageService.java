package com.app.service;

import com.app.common.BaseService;
import com.app.common.ContainsKeys;
import com.app.common.RetResult;
import com.app.utils.JsonMapper;
import com.qq.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构管理业务处理实现类，包括机构查询、新增、修改、删除、批量导入等功能
 * 
 * @author Lian
 * @create on 2010-09-26
 */
@Component
public class GroupManageService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(GroupManageService.class);

	/**
	 * 删除机构信息(单条记录逻辑删除操作)
	 *
	 */
	public RetResult removeGroup(Map map) {
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/deleteGroup", map, RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return ret;
	}

	/**
	 * 根据机构ID获得单条机构信息（用于修改记录时显示）
	 */
	public RetResult getGroupInfoByID(Map map) {
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/getGroupInfoByID", map, RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return ret;
	}

	/**
	 * 根据机构ID获得机构信息实体(用于显示机构详情)
	 */
	public RetResult getGroupInfoByIdForDisplay(Map map) {
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/getGroupInfoByIdForDisplay", map, RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return ret;
	}

	/**
	 * 机构Excel导入
	 *
	 * @throws BusinessException
	 */
	public RetResult importGroup(List excelBean, String creatorid) throws BusinessException {
		JsonMapper json = JsonMapper.nonEmptyMapper();
		String excelBeanstr = json.toJson(excelBean).toString();
		logger.info(excelBeanstr);

		Map map = new HashMap();
		map.put("excelBean", excelBeanstr);
		map.put("userid", creatorid);

		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/importGroup", map, RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return ret;
	}

	/**
	 * 移动部门所在的顺序
	 *
	 */
	public RetResult updateGroupLocation(Map dto) {
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/updateGroupLocation", dto, RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return  ret;
	}

	public RetResult queryAllCategory2TreeBeanList() {
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/queryAllCategory2TreeBeanList", new HashMap(), RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return ret;
	}

	public RetResult getShowAddData(Map map){
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/getShowAddData", map, RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return ret;
	}

	public RetResult addGroup(Map map){
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/addGroup", map, RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return ret;
	}

	public RetResult getShowUpdateData(Map map){
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/getShowUpdateData", map, RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return ret;
	}

	public RetResult updateGroup(Map map){
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/updateGroup", map, RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return ret;
	}

	public RetResult loadGroupTree(Map map){
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/loadGroupTree", map, RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return ret;
	}

	public RetResult queryZTreeList() {
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/queryZTreeList", new HashMap(), RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return  ret;
	}

	public RetResult queryZTreeList2LastNodeIsHidden() {
		RetResult ret = null;
		try {
			ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/group/queryZTreeList2LastNodeIsHidden", new HashMap(), RetResult.class);
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			ret = new RetResult(ContainsKeys.RET_ERR);
		}

		return  ret;
	}
}
