package com.app.service;

import com.app.dao.GroupDAO;
import com.app.dao.RedisCacheDAO;
import com.app.dao.UserDAO;
import com.app.dao.UserGroupDAO;
import com.app.entity.Tbusergroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 */
@Component
public class UserGroupManageService {
    private static final Logger logger = LoggerFactory.getLogger(UserGroupManageService.class);

    @Autowired
    private RedisCacheDAO redisCacheDAO;

    @Autowired
    private UserDAO userManageDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserGroupDAO userGroupDAO;

    /**
     * 新增机构信息
     *
     * @param tbusergroup
     * @return
     */
    public String saveUserGroup(Tbusergroup tbusergroup) {
        return userGroupDAO.addUserGroup(tbusergroup);
    }

    /**
     * 修改机构信息
     *
     * @param tbusergroup
     */
    public void updateUserGroup(Tbusergroup tbusergroup) {
        userGroupDAO.updateUserGroup(tbusergroup);
    }

    /**
     * 删除机构信息(单条记录逻辑删除操作)
     *
     * @param tbusergroup
     * @return
     */
    public int removeUserGroup(Tbusergroup tbusergroup) {
        return userGroupDAO.deleteUserGroup(tbusergroup);
    }

    /**
     * 根据机构ID获得单条机构信息（用于修改记录时显示）
     *
     * @param tbusergroup
     * @return
     */
    public List getUserGroupInfoByConf(Tbusergroup tbusergroup) {
        return userGroupDAO.getUserGroupInfoByConf(tbusergroup);
    }

    /**
     * 根据机构ID获得单条机构信息（用于系统登录时进行调用）
     *
     * @return
     */
    public Tbusergroup getUserGroupInfoByID(String ugid) {

        return userGroupDAO.getUserGroupInfoById(ugid);
    }


    public boolean checkUserGroupExisted(Tbusergroup tbusergroup){
        return userGroupDAO.checkUserGroupExisted(tbusergroup);
    }
}
