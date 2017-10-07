package com.app.controller;

import com.app.common.BaseController;
import com.app.common.ContainsKeys;
import com.app.common.Identities;
import com.app.common.RetResult;
import com.app.dto.GroupDTO;
import com.app.entity.Tbgroup;
import com.app.service.GroupManageService;
import com.app.utils.EncodingTools;
import com.app.utils.JsonMapper;
import com.chinasage.common.appweb.taglib.tree.InitTree;
import com.qq.base.excel.base.AbstractExcelToBean;
import com.qq.base.excel.jxl.JxlExcelToBeanImp;
import com.qq.base.exception.BusinessException;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/group")
public class GroupManageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(GroupManageController.class);

    @Autowired
    private GroupManageService groupManageService;

    @RequestMapping(path = "/getGroupList")
    public String getGroupList(HttpServletRequest request, HttpServletResponse response, Model model) {
        RetResult ret = groupManageService.queryAllCategory2TreeBeanList();
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }
        List list = (List) ret.getRetObj();
        model.addAttribute("funcAndChidList", list);
        return "group/listGroup";
    }

    /**
     * 根据机构编号获得机构信息（展示机构详细信息） 调整其在增加、修改、删除操作时的机构树自动刷新的功能 modify by lianyg 2010-11-29
     */
    @RequestMapping(path = "/showGroupInfoByID")
    public String showGroupInfoByID(Model model, HttpServletRequest request, HttpServletResponse response, Tbgroup tbgroup) {
        Map map = new HashMap();
        map.putAll(new BeanMap(tbgroup));

        RetResult ret = groupManageService.getGroupInfoByIdForDisplay(map);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }

        Map dto = (Map) ret.getRetObj();

        // 将实例化对象GroupDTO的属性值拷贝到from中
        // this.bindForm(viewform, dto);
        dto.put("level", Integer.valueOf(request.getParameter("level")));
        request.setAttribute("dto", dto);
        return "group/contentGroup";
    }

    /**
     * 跳转到机构新增页面
     */
    @RequestMapping(path = "/showAdd")
    public String showAdd(Model model, HttpServletRequest request, HttpServletResponse response) {

        // 防止重复提交的token
        //saveToken(request);

        String groupid = request.getParameter("groupid");
        Map tbgroup_ = new HashMap();
        tbgroup_.put("groupid", groupid);
        ;

        RetResult ret = groupManageService.getShowAddData(tbgroup_);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }

        Map map = (Map) ret.getRetObj();

        Map tbgroup = (Map) map.get("tbgroup");
        Map tbgroup1 = (Map) map.get("tbgroup1");
        List funclist = (List) map.get("funclist");

        JsonMapper json = JsonMapper.nonEmptyMapper();
        request.setAttribute("funclist", json.toJson(funclist).toString());
        request.setAttribute("savemethod", "1");
        request.setAttribute("pojo", tbgroup);
        request.setAttribute("tbgroup1", tbgroup1);

        return "group/addGroup";
    }

    /**
     * 新增机构信息（判断机构编号唯一性）
     */
    @RequestMapping(path = "/addGroup")
    public String addGroup(Model model, HttpServletRequest request, HttpServletResponse response, Tbgroup tbgroup) {
        String usercode = request.getAttribute("usercode").toString();
        String sm = request.getParameter("savemethod");
        Map map = new HashMap();
        map.putAll(new BeanMap(tbgroup));
        map.put("usercode", usercode);
        map.put("savemethod", sm);

        // 重置令牌
        //resetToken(request);

        RetResult ret = groupManageService.addGroup(map);
        String retMessage = ret.getRetMessage();
        if (StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            dwzCallback(model, OK, CLOSE_CURRENT, "保存成功!", "cslms030", "jbsxBox6", null);
        } else if (StringUtils.equals(retMessage, ContainsKeys.RET_ERR)) {
            dwzCallback(model, ERROR, null, "保存失败", null, "jbsxBox6", null);
        } else {
            dwzCallback(model, ERROR, null, retMessage, null, "jbsxBox6", null);
        }

        return DWZ_COMMON_PAGE;
    }

    /**
     * 跳转到机构修改页面
     */
    @RequestMapping(path = "/showUpdate")
    public String showUpdate(Model model, HttpServletRequest request, HttpServletResponse response) {
        String groupid = request.getParameter("groupid");
        Map map_ = new HashMap();
        map_.put("groupid", groupid);

        RetResult ret = groupManageService.getShowUpdateData(map_);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }

        Map map = (Map) ret.getRetObj();

        Map tbgroup = (Map) map.get("tbgroup");
        Map pojo2 = (Map) map.get("pojo2");
        List funclist = (List) map.get("funclist");

        request.setAttribute("parentname", pojo2.get("groupname"));

        JsonMapper json = JsonMapper.nonEmptyMapper();
        request.setAttribute("funclist", json.toJson(funclist).toString());
        request.setAttribute("pojo", tbgroup);
        request.setAttribute("savemethod", "2");

        return "group/addGroup";
    }

    /**deprecated
     * 修改机构信息
     */
    @RequestMapping(path = "/updateGroup")
    public ModelAndView updateGroup(Model model, HttpServletRequest request, HttpServletResponse response, Tbgroup tbgroup) {
        Map map = new BeanMap(tbgroup);
        RetResult ret = groupManageService.updateGroup(map);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            retMessage = "editfail";
        }

        String urlSuffix = "&flag=" + retMessage + "&nname=" + EncodingTools.UTFtoISO(tbgroup.getGroupname())
                + "&pid=" + tbgroup.getParentid() + "&nid=" + tbgroup.getGroupid();
        String url = "/GroupManageAction.do?method=showGroupInfoByID&groupid=" + tbgroup.getGroupid()
                + urlSuffix;

        return new ModelAndView(url);
    }

    /**
     * 删除机构信息记录（逻辑删除）
     */
    @RequestMapping(path = "/deleteGroup")
    public String deleteGroup(Model model, HttpServletRequest request, HttpServletResponse response) {
        String groupid = request.getParameter("groupid");
        Map map = new HashMap();
        map.put("groupid", groupid);

        RetResult ret = groupManageService.removeGroup(map);
        String retMessage = ret.getRetMessage();
        if (StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            dwzCallback(model, OK, null, "删除成功！", "cslms030", "jbsxBox6", null);
        } else if (StringUtils.equals(retMessage, ContainsKeys.RET_ERR)) {
            dwzCallback(model, ERROR, null, "删除失败！", null, "jbsxBox6", null);
        } else {
            dwzCallback(model, ERROR, null, retMessage, null, "jbsxBox6", null);
        }

        return DWZ_COMMON_PAGE;
    }

    /**
     * 加载机构组织树（使用递归及AJAX进行动态刷行动方式）
     */
    @RequestMapping(path = "/loadGroupTree")
    public String loadGroupTree(Model model, HttpServletRequest request, HttpServletResponse response) {

        String treeWay = request.getParameter("treeWay");// 树位置，由是否传回显域进行自动判断
        String pid = request.getParameter("pid");// 获取标识当前选中的节点的的字符串
        InitTree tree = new InitTree(pid, treeWay);
        Map map = new HashMap();
        map.put("treeWay", treeWay);
        map.put("pid", pid);

        RetResult ret = groupManageService.loadGroupTree(map);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }

        List list = (List) ret.getRetObj();
        tree.setNodeList(list);

        String scriptContent = tree.getTreeNodeJsScript();
        super.renderHtml(response, scriptContent);
        return null;
    }

    /**
     * 跳转到机构批量导入页面
     */
    @RequestMapping(path = "/gotoImportGroups")
    public String gotoImportGroups(Model model, HttpServletRequest request, HttpServletResponse response) {
        Tbgroup tbgroup = new Tbgroup();
        String groupid = request.getParameter("groupid");
        tbgroup.setGroupid(groupid);

        // 将实例化对象Tbgroup的属性值拷贝到from中
        //this.bindForm(form, tbgroup);

        return "group/importGroup";
    }

    /**
     * 机构Excel文件导入
     */
    @RequestMapping(path = "/importGroup")
    public String importGroup(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("excelFile") MultipartFile file) {
        String usercode = request.getAttribute("usercode").toString();
        String userid = request.getAttribute("userid").toString();

        String newfilename = "";
        List errorlist = new ArrayList();
        if (file.isEmpty()) {
            dwzCallback(model, ERROR, null, "导入的excel文件为空！", null);
            return DWZ_COMMON_PAGE;
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            if (!suffix.equalsIgnoreCase(".xls")) {
                dwzCallback(model, ERROR, null, "导入文件不是excel文件！", null);
                return DWZ_COMMON_PAGE;
            }

            newfilename = Identities.uuid2() + suffix;
            newfilename = request.getSession().getServletContext().getRealPath("/") + ContainsKeys.UPLOAD_EXAM_FILE_PATH + newfilename;
            file.transferTo(new File(newfilename));
        } catch (Exception e) {
            dwzCallback(model, ERROR, null, "导入失败！", null);
            return DWZ_COMMON_PAGE;
        }

        //导入文件处理
        String ctx = request.getSession().getServletContext().getRealPath("/");
        try {
            // 初始化EXCEL解析组件
            AbstractExcelToBean aetb = new JxlExcelToBeanImp("group", null);

            // 首先判断公共的校验是否为空，不为空直接返回错误信息
            if (!aetb.getErrorData().isEmpty()) {
                request.getSession().setAttribute("errorData", aetb.getErrorData());
                String returnPath = "group/importGroupError";
                dwzCallback(model, OK, "forward", "导入失败！", null, null, returnPath);
                return DWZ_COMMON_PAGE;
            }

            // 读取上传到服务器的EXCEL人员信息，本方法返回的List中实在配置文件中绑定的业务BEAN
            List excelBean = aetb.getBeanList(newfilename);

            RetResult ret = groupManageService.importGroup(excelBean, userid);
            if (StringUtils.equals(ret.getRetMessage(), ContainsKeys.RET_ERR)) {
                List list = (List) ret.getRetObj();
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        int index = Integer.parseInt(list.get(i++).toString());
                        aetb.superaddErrorData(excelBean.get(index), list.get(i).toString());
                    }
                }
            }
            if (!aetb.getErrorData().isEmpty()) {
                errorlist = aetb.getErrorData();
            }
        } catch (BusinessException e) {
            dwzCallback(model, ERROR, null, "导入失败！", null);
            return DWZ_COMMON_PAGE;
        }

        if (!errorlist.isEmpty()) {
            request.getSession().setAttribute("errorData", errorlist);

            String returnPath = "exam/importGroupError";
            dwzCallback(model, OK, "forward", "导入失败！", null, null, returnPath);
            return DWZ_COMMON_PAGE;
        }

        dwzCallback(model, OK, CLOSE_CURRENT, "导入成功！", "listexamstudent", null, null);
        return DWZ_COMMON_PAGE;
    }

    /**
     * 跳转到移动机构排序位置的页面
     *
     */
    @RequestMapping(path = "/showGroupLocation")
    public String showGroupLocation(Model model, HttpServletRequest request, HttpServletResponse response) {
        String groupid = request.getParameter("groupid");
        Map tbgroup = new HashMap();
        tbgroup.put("groupid", groupid);

        if (!groupid.trim().equals("9001")) {
            RetResult ret = groupManageService.getGroupInfoByID(tbgroup);
            String retMessage = ret.getRetMessage();
            if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
                return "error/500";
            }

            tbgroup = (Map)ret.getRetObj();
            // 将实例化对象Tbgroup的属性值拷贝到from中
            model.addAttribute("tbgroup", tbgroup);
        } else {
            //this.saveErrorSession(request, "error：不能对初始机构进行排序！");
            return new String("/GroupManageAction.do?method=showGroupInfoByID&groupid=" + groupid);
        }

        return "group/changeGroupLocation";
    }

    /**
     * 移动机构排列顺序（Location）
     */
    @RequestMapping(path = "/changeGroupLocation")
    public String changeGroupLocation(Model model, HttpServletRequest request, HttpServletResponse response, GroupDTO dto) {
        Map map = new BeanMap(dto);

            // 移动位置，其所有该父节点下的同等级机构的位置都相应的变化
        RetResult ret = groupManageService.updateGroupLocation(map);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }

        return new String("/GroupManageAction.do?method=showGroupInfoByID&groupid=" + dto.getGroupid());
    }

    public static void main(String[] args) throws Exception {

    }
}
