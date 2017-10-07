package com.app.controller;

import com.app.common.ContainsKeys;
import com.app.common.Identities;
import com.app.common.RetResult;
import com.app.dto.GroupDTO;
import com.app.dto.GroupImportDTO;
import com.app.dto.UserDTO;
import com.app.entity.Tbgroup;
import com.app.service.GroupManageService;
import com.app.service.RedisCacheService;
import com.app.service.UserLogService;
import com.app.service.UserManageService;
import com.app.utils.JsonMapper;
import com.app.utils.UtilDataTime;
import com.chinasage.common.appweb.taglib.tree.InitTree;
import com.qq.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/group")
public class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private RedisCacheService<Object> redisCacheService;

    @Autowired
    private UserManageService userManageService;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private GroupManageService groupManageService;

    @RequestMapping(path = "/queryAllCategory2TreeBeanList")
    @ResponseBody
    public RetResult queryAllCategory2TreeBeanList(HttpServletRequest request, HttpServletResponse response) {
        List list = groupManageService.queryAllCategory2TreeBeanList();

        RetResult<UserDTO> ret = new RetResult(ContainsKeys.RET_OK, list);

        return ret;
    }

    @RequestMapping(path = "/getGroupInfoByIdForDisplay")
    @ResponseBody
    public RetResult getGroupInfoByIdForDisplay(HttpServletRequest request, HttpServletResponse response, @RequestParam("groupid") String groupid) {
        Tbgroup tbgroup = new Tbgroup();
        tbgroup.setGroupid(groupid);
        GroupDTO groupDTO = groupManageService.getGroupInfoByIdForDisplay(tbgroup);

        RetResult<UserDTO> ret = new RetResult(ContainsKeys.RET_OK, groupDTO);

        return ret;
    }

    @RequestMapping(path = "/getShowAddData")
    @ResponseBody
    public RetResult getShowAddData(HttpServletRequest request, HttpServletResponse response, @RequestParam("groupid") String groupid) {
        Tbgroup tbgroup = new Tbgroup();
        tbgroup.setGroupid(groupid);
        int nextlocationint = groupManageService.getNextLocationByParentId(groupid);

        tbgroup.setLocation(Long.valueOf(nextlocationint));

        Tbgroup tbgroup1 = groupManageService.getGroupInfoByID(tbgroup);

        List funclist = groupManageService.queryZTreeList();

        Map map = new HashMap();
        map.put("tbgroup", tbgroup);
        map.put("tbgroup1", tbgroup1);
        map.put("funclist", funclist);

        RetResult<UserDTO> ret = new RetResult(ContainsKeys.RET_OK, map);
        return ret;
    }

    @RequestMapping(path = "/addGroup")
    @ResponseBody
    public RetResult addGroup(HttpServletRequest request, HttpServletResponse response, Tbgroup tbgroup) {
        String sm = request.getParameter("savemethod");
        String usercode = request.getParameter("usercode");

        UserDTO user = (UserDTO) redisCacheService.getRawCacheMapValue("user", usercode);

        String groupid = "";
        String ret_mes = ContainsKeys.RET_OK;
        try {
            if (sm.equals("1")) {
                // 执行机构新增操作（需要判断机构编号的唯一性）
                // 获得记录状态、操作用户、操作时间
                tbgroup.setDeletestatus(Long.valueOf(1));
                tbgroup.setCreatorid(user.getUserid());
                tbgroup.setCreatetime(UtilDataTime.getCurrentTime());
                groupid = groupManageService.saveGroup(tbgroup);
            } else {
                groupManageService.updateGroup(tbgroup);
            }
        } catch (BusinessException e1) {
            logger.info(e1.getMessage(), e1);
            ret_mes = e1.getMessage();
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            ret_mes = ContainsKeys.RET_ERR;
        }

        RetResult ret = new RetResult(ret_mes);
        return ret;
    }

    @RequestMapping(path = "/getShowUpdateData")
    @ResponseBody
    public RetResult getShowUpdateData(HttpServletRequest request, HttpServletResponse response) {
        String groupid = request.getParameter("groupid");
        Tbgroup tbgroup = new Tbgroup();

        tbgroup.setGroupid(groupid);

        tbgroup = groupManageService.getGroupInfoByID(tbgroup);

        Tbgroup tbgroup2 = new Tbgroup();

        tbgroup2.setGroupid(tbgroup.getParentid());
        Tbgroup pojo2 = groupManageService.getGroupInfoByID(tbgroup2);

        List funclist = groupManageService.queryZTreeList();

        Map map = new HashMap();
        map.put("tbgroup", tbgroup);
        map.put("pojo2", pojo2);
        map.put("funclist", funclist);

        RetResult ret = new RetResult(ContainsKeys.RET_OK, map);
        return ret;
    }

    @RequestMapping(path = "/updateGroup")
    @ResponseBody
    public RetResult updateGroup(HttpServletRequest request, HttpServletResponse response, Tbgroup tbgroup) {
        String boolflagstr;
        try {
            groupManageService.updateGroup(tbgroup);
            boolflagstr = "edit";
        } catch (BusinessException e) {
            boolflagstr = "editfail";
        } catch (Exception e1) {
            boolflagstr = "editfail";
        }

        RetResult ret = new RetResult(boolflagstr);

        return ret;
    }

    @RequestMapping(path = "/deleteGroup")
    @ResponseBody
    public RetResult deleteGroup(HttpServletRequest request, HttpServletResponse response, Tbgroup tbgroup) {
        String boolflagstr = "";
        try {
            // 单条机构记录逻辑删除
            groupManageService.removeGroup(tbgroup);
            boolflagstr = ContainsKeys.RET_OK;
        } catch (BusinessException e1) {
            logger.info(e1.getMessage(), e1);
            boolflagstr = e1.getMessage();
        } catch (Exception e) {
            logger.info("删除失败，机构正被使用", e);
            boolflagstr = "删除失败，机构正被使用";
        }

        RetResult ret = new RetResult(boolflagstr);

        return ret;
    }

    @RequestMapping(path = "/loadGroupTree")
    @ResponseBody
    public RetResult loadGroupTree(HttpServletRequest request, HttpServletResponse response) {
        String treeWay = request.getParameter("treeWay");// 树位置，由是否传回显域进行自动判断
        String pid = request.getParameter("pid");// 获取标识当前选中的节点的的字符串
        InitTree tree = new InitTree(pid, treeWay);

        List list = null;
        if (tree.getPid().equals("-1")) {// 首先生成一级根节点
            list = groupManageService.getGroupChildrenNode("-5");
        } else {// 根据点击的节点动态生成下一级节点
            list = groupManageService.getGroupChildrenNode(tree.getPid());
        }

        RetResult ret = new RetResult(ContainsKeys.RET_OK, list);

        return ret;
    }

    @RequestMapping(path = "/importGroup")
    @ResponseBody
    public RetResult importGroup(HttpServletRequest request, HttpServletResponse response) {
        String excelBeanstr = request.getParameter("excelBean");
        String userid = request.getParameter("userid");

        JsonMapper json = JsonMapper.nonEmptyMapper();
        List<GroupImportDTO> excelBean = json.fromJson(excelBeanstr, json.createCollectionType(List.class, GroupImportDTO.class));

        List errlist = new ArrayList();
        // 判断读取EXCEL组装的BEAN是否为空
        if (!excelBean.isEmpty()) {
            GroupImportDTO[] groupExcelBean = (GroupImportDTO[]) excelBean.toArray(new GroupImportDTO[]{});
            Tbgroup[] group = new Tbgroup[groupExcelBean.length];
            Map map = new HashMap();

            // 开始业务逻辑合理性判断
            for (int i = 0; i < groupExcelBean.length; i++) {

                // 初始化记录业务错误信息缓冲串
                StringBuffer sb = new StringBuffer();
                group[i] = new Tbgroup(Identities.uuid2());
                group[i].setGroupcode(groupExcelBean[i].getGroupcode());

                Tbgroup tbgrouptemp = groupManageService.getGroupByGroupCode(groupExcelBean[i].getGroupcode());

                if (tbgrouptemp != null) {
                    sb.append("机构编号：" + groupExcelBean[i].getGroupcode() + " 已经被注册！");
                }

                boolean bool = groupExcelBean[i].getGroupcode().matches("[0-9a-zA-Z_-]+");
                if (!bool) {
                    sb.append("机构编号只能由数字、字母、下划线组合而成！");
                }

                if (map.containsKey(groupExcelBean[i].getGroupcode())) {
                    sb.append("文件中机构编号：" + groupExcelBean[i].getGroupcode() + " 重复！");
                }

                // 根据父机构编号获得对应的父机构ID
                String parentidstr = groupManageService.getParentIdByParentCode(groupExcelBean[i].getParentcode());

                if (parentidstr.trim().equals("") && !map.containsKey(groupExcelBean[i].getParentcode())) {
                    sb.append("机构父节点编号：" + groupExcelBean[i].getParentcode() + "不正确，无与其匹配的机构！");
                } else {
                    // 如果从库中找到其父节点ID，则直接赋值
                    if (!parentidstr.trim().equals("")) {
                        group[i].setParentid(parentidstr);
                    } else {
                        // 从MAP中获得
                        group[i].setParentid((map.get(groupExcelBean[i].getParentcode())).toString());
                    }
                }

                // 机构名称
                group[i].setGroupname(groupExcelBean[i].getGroupame());
                map.put(groupExcelBean[i].getGroupcode(), group[i].getGroupid());

                // 判断错误信息是否为空
                if (sb.length() > 0) {
                    errlist.add(i);
                    errlist.add(sb.toString());
                    sb.delete(0, sb.length());
                }
            }

            if (!errlist.isEmpty()) {
                return new RetResult(ContainsKeys.RET_ERR, errlist);
            }

            groupManageService.importGroup(groupExcelBean, group, userid);
        }

        return new RetResult(ContainsKeys.RET_OK);
    }

    @RequestMapping(path = "/getGroupInfoByID")
    @ResponseBody
    public RetResult getGroupInfoByID(HttpServletRequest request, HttpServletResponse response, Tbgroup tbgroup) {
        String treeWay = request.getParameter("treeWay");// 树位置，由是否传回显域进行自动判断
        String pid = request.getParameter("pid");// 获取标识当前选中的节点的的字符串

        Tbgroup list = groupManageService.getGroupInfoByID(tbgroup);

        RetResult ret = new RetResult(ContainsKeys.RET_OK, list);

        return ret;
    }

    @RequestMapping(path = "/updateGroupLocation")
    @ResponseBody
    public RetResult updateGroupLocation(HttpServletRequest request, HttpServletResponse response, GroupDTO dto) {
        try {
            groupManageService.updateGroupLocation(dto);
        } catch (BusinessException e) {
            RetResult ret = new RetResult(e.getMessage());
            return ret;
        }

        RetResult ret = new RetResult(ContainsKeys.RET_OK);
        return ret;
    }

    @RequestMapping(path = "/queryZTreeList")
    @ResponseBody
    public RetResult queryZTreeList(HttpServletRequest request, HttpServletResponse response) {
        List list = groupManageService.queryZTreeList();

        RetResult ret = new RetResult(ContainsKeys.RET_OK, list);
        return ret;
    }

    @RequestMapping(path = "/queryZTreeList2LastNodeIsHidden")
    @ResponseBody
    public RetResult queryZTreeList2LastNodeIsHidden(HttpServletRequest request, HttpServletResponse response) {
        List list = groupManageService.queryZTreeList2LastNodeIsHidden();

        RetResult ret = new RetResult(ContainsKeys.RET_OK, list);
        return ret;
    }

    public static void main(String[] args) throws Exception {

    }
}
