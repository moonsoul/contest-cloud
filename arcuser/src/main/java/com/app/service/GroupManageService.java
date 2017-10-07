package com.app.service;

import com.app.common.BuildGroupCateLevel;
import com.app.dao.GroupDAO;
import com.app.dao.RedisCacheDAO;
import com.app.dao.UserDAO;
import com.app.dto.GroupDTO;
import com.app.dto.GroupImportDTO;
import com.app.entity.Tbgroup;
import com.app.utils.UtilDataTime;
import com.qq.base.exception.BusinessException;
import com.qq.base.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 机构管理业务处理实现类，包括机构查询、新增、修改、删除、批量导入等功能
 *
 * @author Lian
 * @create on 2010-09-26
 */
@Component
public class GroupManageService {
    private static final Logger logger = LoggerFactory.getLogger(GroupManageService.class);

    @Autowired
    private RedisCacheDAO redisCacheDAO;

    @Autowired
    private UserDAO userManageDAO;

    @Autowired
    private GroupDAO groupDAO;

    /**
     * 新增机构信息
     *
     * @param tbgroup
     * @return
     */
    public String saveGroup(Tbgroup tbgroup) throws BusinessException {

        String returnstr = "";

        // 判断机构编号唯一性
        if (groupDAO.checkGroupCodeSole(tbgroup)) {
            // 判断所选类型与父节点类型是否有冲突
            if (groupDAO.checkGroupType(tbgroup)) {
                returnstr = groupDAO.addGroup(tbgroup);
            } else {
                logger.info("机构信息新增失败： " + "类型与所选父节点的类型冲突。");
                throw new BusinessException("机构信息新增失败： " + "所选类型与父节点的类型冲突，班级下不能存在院校机构。");
            }
        } else {
            logger.info("机构信息新增失败： " + "机构编号与现有编号冲突。");
            throw new BusinessException("机构信息新增失败： " + "机构编号与现有编号冲突。");
        }

        return returnstr;
    }

    /**
     * 修改机构信息
     *
     * @param tbgroup
     */
    public void updateGroup(Tbgroup tbgroup) throws BusinessException {

        // 判断所选类型与父节点类型是否有冲突
        if (groupDAO.checkGroupType(tbgroup)) {
            groupDAO.updateGroup(tbgroup);
        } else {
            logger.info("机构信息新增失败： " + "类型与所选父节点的类型冲突。");
            throw new BusinessException("机构信息新增失败： " + "所选类型与父节点的类型冲突，班级下不能存在院校机构。");
        }
    }

    /**
     * 删除机构信息(单条记录逻辑删除操作)
     *
     * @param tbgroup
     * @return
     */
    public int removeGroup(Tbgroup tbgroup) throws BusinessException {

        int m = 0;
        // 判断要删除的机构节点下是否有子节点（如果没有子节点则可以进行删除）
        if (!groupDAO.checkGroupHasChild(tbgroup)) {
            // 判断机构是否处于被使用状态（与人员信息相关联）
            if (!groupDAO.checkGroupUsed(tbgroup)) {
                // 如果未被使用，则可以进行删除操作
                m = groupDAO.deleteGroup(tbgroup);
            } else {
                throw new BusinessException("该机构处于正在使用的状态，不能删除！");
            }
        } else {
            throw new BusinessException("该机构下有子节点，请先删除子节点后再删除该节点！");
        }

        return m;
    }

    /**
     * 根据机构ID获得单条机构信息（用于修改记录时显示）
     *
     * @param tbgroup
     * @return
     */
    public Tbgroup getGroupInfoByID(Tbgroup tbgroup) {

        String groupid = tbgroup.getGroupid();

        return groupDAO.getGroupInfoById(groupid);
    }

    /**
     * 根据机构父节点ID获得新增机构的location
     *
     */
    public int getNextLocationByParentId(String parentidstr) {

        return groupDAO.getNextLocationByParentId(parentidstr);
    }

    /**
     * 根据机构ID获得机构信息实体(用于显示机构详情)
     *
     * @param tbgroup
     * @return
     */
    public GroupDTO getGroupInfoByIdForDisplay(Tbgroup tbgroup) {
        return groupDAO.getGroupInfoByIdForDisplay(tbgroup);
    }

    /**
     * 根据机构ID获得单条机构信息（用于系统登录时进行调用）
     *
     * @return
     */
    public Tbgroup getGroupInfoByID(String groupidstr) {

        return groupDAO.getGroupInfoById(groupidstr);
    }

    /**
     * 根据根节点获得所有该根节点下的所有子节点（机构树节点）
     *
     * @param id
     * @return
     */
    public List getGroupChildrenNode(String id) {

        return groupDAO.getGroupChildrenNode(id);
    }

    /**
     * 根据机构编号查询得到机构实体 add by zhaoyuyang 2010.10.19
     *
     * @param groupCode
     * @return
     */
    public Tbgroup getGroupByGroupCode(String groupCode) {

        return groupDAO.getGroupByGroupCode(groupCode);
    }

    /**
     * 机构Excel导入
     *
     * @return
     * @throws BusinessException
     */
    public void importGroup(GroupImportDTO[] groupExcelBean, Tbgroup[] group, String creatorid) {
        String[] insertUsersqls = new String[groupExcelBean.length];
        for (int i = 0; i < groupExcelBean.length; i++) {
            ReflectionUtils.copyProperties(groupExcelBean[i], group[i], new String[]{"type"});
            group[i].setType(groupExcelBean[i].getType().equals("院系") ? new Long(1) : new Long(2));
            group[i].setDeletestatus(new Long(1));
            group[i].setCreatorid(creatorid);
            group[i].setCreatetime(UtilDataTime.getCurrentTime());
            insertUsersqls[i] = group[i].entityInsertSql();
        }
        // 开始执行数据库保存动作。
        groupDAO.batchInsertGroups(insertUsersqls);
    }

    /**
     * 移动部门所在的顺序
     */
    public void updateGroupLocation(GroupDTO dto) throws BusinessException {

        // 获得当前最大的排序位置
        int maxlocationint = getNextLocationByParentId(dto.getParentid()) - 1;

        // 判断所输入的移动位置是否大于最大的排序位置
        if (dto.getTargetIndex() > maxlocationint) {
            dto.setTargetIndex(maxlocationint);
        }

        groupDAO.updateGroupLocation(dto.getGroupid(), dto.getParentid(), dto.getLocation().intValue(),
                dto.getTargetIndex());
        // throw new BusinessException("移动机构位置成功！");
    }

    public List queryAllCategory2TreeBeanList() {
        List list = groupDAO.queryAllgroup();
        List list1 = BuildGroupCateLevel.getFunctionURLLevel1(list, "9001");
        return list1;
    }

    public List queryZTreeList() {
        return groupDAO.queryZTreeList();
    }


    public List queryZTreeList2LastNodeIsHidden() {
        // TODO Auto-generated method stub
        return groupDAO.queryZTreeList2LastNodeIsHidden();
    }

    public String getParentIdByParentCode(String parentcodestr) {
        return groupDAO.getParentIdByParentCode(parentcodestr);
    }

}
