package com.app.service;

import com.app.common.BaseService;
import com.app.common.ContainsKeys;
import com.app.common.Identities;
import com.app.common.RetResult;
import com.app.dto.UserDTO;
import com.app.entity.Tbuser;
import com.app.utils.JsonMapper;
import com.qq.base.exception.BusinessException;
import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserManageService extends BaseService {
    private final static Logger logger = LoggerFactory.getLogger(UserManageService.class);

    public RetResult doStuLogin(Map map) {
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/stuLogin", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    public RetResult doAdminLogin(Map map) {
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/adminLogin", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    public RetResult getUserByUsercode(Map map) {
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/userByUsercode", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    public RetResult updatePassword(Map map) {
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/updatePassword", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    public RetResult queryUserList(UserDTO user) {
        Map map = new BeanMap(user);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/queryUserList", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    public RetResult queryUserListDwz(UserDTO user) {
        Map map = new BeanMap(user);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/queryUserListDwz", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    /**
     * 新增用户信息（包括用户图片的上传及基本信息入库）
     *
     */
    public RetResult saveUser(UserDTO userdto) throws BusinessException {
        // 获得系统统一序列号
        String id = Identities.uuid2();
        userdto.setUserid(id);
        // userdto.setCompanyid(userManageDAO.getCompanyIdByGroupID(userdto.getGroupid()));

        Map map = new BeanMap(userdto);

        // 判断用户编号唯一性
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/saveUser", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    /**
     * 重置用户密码
     */
    public RetResult resetpassword(Tbuser userdto) {
        userdto.setPassword(Identities.getMD5(userdto.getUsercode()));
        Map map = new BeanMap(userdto);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/updatePassword", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    /**
     * 修改用户信息
     *
     */
    public RetResult updateUser(UserDTO userdto) {
        Map map = new BeanMap(userdto);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/updateUser", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    /**
     * 删除用户信息（逻辑删除）
     */
    public RetResult removeUser(Tbuser tbuser) {
        Map map = new BeanMap(tbuser);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/deleteUser", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    /**
     * 批量删除用户记录（逻辑删除）
     */
    public RetResult removeAllBatchUser(String ids) {
        Map map = new HashMap();
        map.put("ids", ids);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/removeAllBatchUser", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    /**
     * 改变用户的激活状态（有效状态）
     *
     * @param tbuser
     * @return
     */
    public RetResult updateUserStatus(Tbuser tbuser) {
        Map map = new BeanMap(tbuser);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/changeUserStatus", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    /**
     * 根据用户ID获得单条用户信息
     * @return
     */
    public RetResult getUserInfoByID(UserDTO userdto) {
        Map map = new BeanMap(userdto);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/getUserInfoById", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }


    /**
     * 根据用户ID获得单条用户信息
     *
     * @param tbuser
     * @return
     */
    public RetResult getUserByIDForDisplay(Tbuser tbuser) {
        Map map = new BeanMap(tbuser);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/getUserInfoByIdForDisplay", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    /**
     * 人员EXCEL导入 add by zhaoyuyang 2010.10.11
     * <p/>
     * modify by lian 2010-10-13 调整业务逻辑及入库操作等 modify bu lianyg 2010-10-28 增加对人员副表数据的插入操作，调整spring配置文件等
     *
     */
    public RetResult importUser(List excelBean, String creatorid) {
        JsonMapper json = JsonMapper.nonEmptyMapper();
        String excelBeanstr = json.toJson(excelBean).toString();
        logger.info(excelBeanstr);

        Map map = new HashMap();
        map.put("excelBean", excelBeanstr);
        map.put("userid", creatorid);

        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/importUser", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    /**
     * 判断字符串是否由字母或数字组成
     *
     * @param str
     * @return
     */
    public boolean checkNumbLetter(String str) {

        boolean bool = true;
        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            if (!((str.charAt(i) >= '0' && str.charAt(i) <= '9') || (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') || (str
                    .charAt(i) >= 'A' && str.charAt(i) <= 'Z'))) {
                // 如果为非字母或数字，则跳出循环
                bool = false;
                break;
            }
        }

        return bool;
    }


    public RetResult getUserPaiMingByID(UserDTO userdto) {
        Map map = new BeanMap(userdto);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/getUserPaiMingByID", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }


    public RetResult updateUserIntegration(Tbuser user) {
        Map map = new BeanMap(user);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/updateUserJiFen", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    public RetResult getUserListPaiMing(UserDTO user) {
        Map map = new BeanMap(user);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/getUserListPaiMing", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    public RetResult getUserListPaiMingByBanJi(UserDTO user) {
        Map map = new BeanMap(user);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/getUserListPaiMingByBanJi", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }

    public RetResult getUserListPaiMingByNianJi(UserDTO user) {
        Map map = new BeanMap(user);
        RetResult ret = null;
        try {
            ret = invokeRemotePost("http://127.0.0.1:8199/arcuser/user/getUserListPaiMingByNianJi", map, RetResult.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            ret = new RetResult(ContainsKeys.RET_ERR);
        }

        return ret;
    }
}
