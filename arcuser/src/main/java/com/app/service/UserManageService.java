package com.app.service;

import com.app.common.ContainsKeys;
import com.app.common.Identities;
import com.app.dao.RedisCacheDAO;
import com.app.dao.UserDAO;
import com.app.dao.UserGroupDAO;
import com.app.dto.UserDTO;
import com.app.dto.UserImportDTO;
import com.app.entity.Tbuser;
import com.app.entity.Tbusergroup;
import com.app.utils.Encoding;
import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.qq.base.common.Page;
import com.qq.base.exception.BusinessException;
import com.qq.base.util.DateUtil;
import com.qq.base.util.ReflectionUtils;
import com.qq.base.web.PageCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by localuser on 2017/8/28.
 */
@Component
public class UserManageService {
    private static final Logger logger = LoggerFactory.getLogger(UserManageService.class);

    @Autowired
    private RedisCacheDAO redisCacheDAO;

    @Autowired
    private UserDAO userManageDAO;

    @Autowired
    private UserGroupDAO userGroupManageDAO;

    public UserDTO getUserInfoByUsercode(Map usermap) {
        return userManageDAO.getUserInfoByUsercode(usermap);
    }

    public UserDTO getUserInfoByUsercode(String usercode) {
        return userManageDAO.getUserInfoByUsercode(usercode);
    }

    public String updateAuthkeyByUserid(Tbuser tbuser) {
        return userManageDAO.updateAuthkeyByUserid(tbuser);
    }

    public UserDTO getUserByUsercode(String usercode) {
        UserDTO userret = null;
        if (StringUtils.isNotEmpty(usercode)) {
            userret = (UserDTO) redisCacheDAO.getRawCacheMapValue("user", usercode);
            if (null == userret) {
                Map usermap = new HashMap();
                usermap.put("usercode", usercode);
                userret = userManageDAO.getUserInfoByUsercode(usermap);
                if (null != userret) {
                    redisCacheDAO.setRawCacheMapValue("user", usercode, userret);
                }
            }
        }
        return userret;
    }

    public String updateUserAuthkey(String usercode, String authkey) {
        UserDTO user = (UserDTO) redisCacheDAO.getRawCacheMapValue("user", usercode);
        if (null == user) {
            Map usermap = new HashMap();
            usermap.put("usercode", usercode);
            user = userManageDAO.getUserInfoByUsercode(usermap);
            if (null == user) {
                return ContainsKeys.RET_ERR;
            }
        }

        user.setAuthkey(authkey);
        redisCacheDAO.setRawCacheMapValue("user", usercode, user);
        redisCacheDAO.setRawCacheMapValue("authkey", user.getUserid(), authkey);

        Tbuser tbuser = new Tbuser();
        tbuser.setUserid(user.getUserid());
        tbuser.setAuthkey(authkey);
        return userManageDAO.updateAuthkeyByUserid(tbuser);
    }

    public UserDTO getUserByUserid(String userid) {
        Map usermap = new HashMap();
        usermap.put("userid", userid);
        UserDTO userdto = userManageDAO.getUserInfoByUserid(usermap);
        List grouplist = userGroupManageDAO.getGroupInfoByUserid(userid);
        userdto.setGrouplist(grouplist);
        return userdto;
    }

    public void updatePassword(Tbuser tbuser) {
        UserDTO userret = null;
        userret = (UserDTO) redisCacheDAO.getRawCacheMapValue("user", tbuser.getUsercode());
        if (null == userret) {
            Map usermap = new HashMap();
            usermap.put("usercode", tbuser.getUsercode());
            userret = userManageDAO.getUserInfoByUsercode(usermap);
        }
        userManageDAO.resetpassword(tbuser);

        userret.setPassword(tbuser.getPassword());
        redisCacheDAO.setRawCacheMapValue("user", tbuser.getUsercode(), userret);
    }

    public Page queryUserList(PageCommon info) {
        return userManageDAO.queryUserList(info);
    }

    public String saveUser(UserDTO userdto) throws BusinessException {

        String returnstr = "";

        // 获得系统统一序列号
        String id = Identities.uuid2();

        Tbuser tbuser = new Tbuser();
        tbuser.setUsercode(userdto.getUsercode());

        int num = userManageDAO.isDisplayByUserCode(tbuser.getUsercode());
        // 判断用户编号唯一性
        // if (userManageDAO.checkUserCodeSole(tbuser)) {
        if (num <= 0) {
            userdto.setUserid(id);

            // 密码进行MD5加密，密码默认为用户的编号
            // Encoding encoding = new Encoding();
            userdto.setPassword(Encoding.encodeCmd(userdto.getUsercode()));
            // userdto.setCompanyid(userManageDAO.getCompanyIdByGroupID(userdto.getGroupid()));

            returnstr = userManageDAO.addUser(userdto);
        } else {
            logger.info("人员信息新增失败： " + "人员编号与现有编号冲突。");
            throw new BusinessException("人员信息新增失败： " + "人员编号与现有编号冲突。");
        }

        return returnstr;
    }

    public int isDisplayByUserCode(String usercode) {
        return userManageDAO.isDisplayByUserCode(usercode);
    }

    public void updateUser(UserDTO userdto) {
        // 判断密码是否为空，如果密码为空，则不对原有密码进行修改
        if (StringUtils.isNotEmpty(userdto.getPassword()) && !userdto.getPassword().trim().equals("null")) {
            // 密码进行MD5加密
            // Encoding encoding = new Encoding();
            userdto.setPassword(Encoding.encodeCmd(userdto.getPassword()));
        }

        userManageDAO.updateUser(userdto);
    }

    public UserDTO getUserInfoById(UserDTO userdto) {
        return userManageDAO.getUserInfoById(userdto);
    }

    public int deleteUser(Tbuser tbuser) {
        return userManageDAO.deleteUser(tbuser);
    }

    public void updateUserJiFen(Tbuser userdto) {
        userManageDAO.updateUserJiFen(userdto);
    }

    public void removeAllBatchUser(String userids[]) {
        String[] sql = new String[userids.length];
        // 循环遍历进行逻辑删除操作
        for (int m = 0; m < userids.length; m++) {
            Tbuser tbuser = new Tbuser();
            tbuser.setUserid(userids[m]);
            sql[m] = tbuser.entityDeleteSql();
            // 调用单个用户删除的操作方法
        }

        userManageDAO.batchDeleteUsers(sql);
    }

    public int changeUserStatus(Tbuser tbuser) {
        return userManageDAO.changeUserStatus(tbuser);
    }

    public UserDTO getUserInfoByIdForDisplay(Tbuser tbuser) {
        return userManageDAO.getUserInfoByIdForDisplay(tbuser);
    }

    public void importUser(UserImportDTO[] userExcelBean, UserDTO[] user, String creatorid) {
        List sqllist = new ArrayList();
        String nowtime = DateUtil.getOracleFormatDateStr19(new Date());
        for (int i = 0; i < userExcelBean.length; i++) {
            if (StringUtils.isNotEmpty(user[i].getUserid())) {
                continue;
            }
            ReflectionUtils.copyProperties(userExcelBean[i], user[i], new String[]{"sex"});
            user[i].setSex(userExcelBean[i].getSex().equals("男") ? new Long(1) : new Long(2));
            user[i].setPassword(Encoding.encodeCmd(user[i].getUsercode()));
            user[i].setCreatorid(creatorid);
            user[i].setCreatetime(nowtime);
            user[i].setBirthday("1980-01-01");
            user[i].setStatus(Long.valueOf(1));
            user[i].setUsertype(Long.valueOf(userExcelBean[i].getUsertype()));
            sqllist.add(user[i].entityInsertSql());
        }

        for (int i = 0; i < userExcelBean.length; i++) {
            if (StringUtils.isNotEmpty(user[i].getUserid()) && StringUtils.isNotEmpty(user[i].getGroupid())) {
                Tbusergroup tbusergroup = new Tbusergroup(Identities.uuid());
                tbusergroup.setGroupid(user[i].getGroupid());
                tbusergroup.setUserid(user[i].getUserid());
                tbusergroup.setCreatetime(nowtime);
                tbusergroup.setCreatorid(creatorid);

                sqllist.add(tbusergroup.entityInsertSql());
            }
        }

        String[] insertUsersqls = (String[])sqllist.toArray(new String[sqllist.size()]);

        // 开始执行数据库保存动作。
        userManageDAO.batchInsertUsersAndUserGroups(insertUsersqls);
    }

    public Page getUserListPaiMing(PageCommon info) {
        return userManageDAO.getUserListPaiMing(info);
    }

    public Page getUserListPaiMingByBanJi(PageCommon info) {
        return userManageDAO.getUserListPaiMingByBanJi(info);
    }

    public Page getUserListPaiMingByNianJi(PageCommon info) {
        return userManageDAO.getUserListPaiMingByNianJi(info);
    }

    public Long getUserPaiMingByID(String userid) {
        return userManageDAO.getUserPaiMingByID(userid);
    }

    /////////////////
    public UserDTO getAdminAllInfoByUserInput(Map usermap) {

        return userManageDAO.getAdminAllInfoByUserInput(usermap);
    }

    public UserDTO getMonitorAllInfoByUserInput(Map usermap) {

        return userManageDAO.getMonitorAllInfoByUserInput(usermap);
    }

    public UserDTO getExamStudentAllInfoByUserInput(Map usermap) {

        return userManageDAO.getExamStudentAllInfoByUserInput(usermap);
    }

}
