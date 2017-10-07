package com.app.controller;

import com.app.common.BaseController;
import com.app.common.ContainsKeys;
import com.app.common.Identities;
import com.app.common.RetResult;
import com.app.dto.GroupDTO;
import com.app.dto.UserDTO;
import com.app.entity.Tbgroup;
import com.app.entity.Tbuser;
import com.app.service.GroupManageService;
import com.app.utils.JsonMapper;
import com.app.utils.UtilDataTime;
import com.qq.base.common.Page;
import com.qq.base.excel.base.AbstractExcelToBean;
import com.qq.base.excel.jxl.JxlExcelToBeanImp;
import com.qq.base.exception.BusinessException;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserManageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserManageController.class);

    @Autowired
    private GroupManageService groupManageService;

    @RequestMapping(path = "/updateUserPwd")
    public String updateAdminPwd(HttpServletRequest request, HttpServletResponse response, Model model) {
        String flag = request.getParameter("flag");
        //String userid = request.getParameter("userid");
        String usercode = request.getAttribute("usercode").toString();
        if (StringUtils.isNotEmpty(flag)) {
            if (flag.equals("1")) {
                return "admin/changepwd";
            }
        }

        Map tbuser = new HashMap();
        //tbuser.put("userid", userid);
        tbuser.put("usercode", usercode);
        tbuser.put("password", Identities.getMD5(request.getParameter("rnewPassword")));

        RetResult ret = userManageService.updatePassword(tbuser);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            dwzCallback(model, ERROR, null, "密码修改失败！", null);
        } else {
            dwzCallback(model, OK, "closeCurrent", "密码修改成功！您的新密码是：" + request.getParameter("rnewPassword"), "", null, null);
        }
        return DWZ_COMMON_PAGE;
    }

    @RequestMapping(path = "/listUser")
    public String getUserList(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "user/listUser";
    }

    @RequestMapping(path = "/lookUpUser")
    public String lookUpUser(Model model, HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        String mode = request.getParameter("mode");
        if (StringUtils.isNotEmpty(mode)) {
            request.setAttribute("mode", mode);
        } else {
            request.setAttribute("mode", "true");
        }

        RetResult ret = userManageService.queryUserList(dto);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }
        String retobj = ret.getRetObj().toString();
        JsonMapper json = JsonMapper.nonEmptyMapper();
        Page page = json.fromJson(retobj, Page.class);

        bindPageQuery(model, page, "entityList");
        String path = "/pages/systemservice/usermanage/lookUpUser.jsp";
        return new String(path);
        // mapping.findForward("lookUpUser");
    }

    /**
     * 用户信息条件查询（用户编号、用户姓名、所属机构、有效状态）
     */
    @RequestMapping(path = "/listUserInfo")
    public String listUserInfo(Model model, HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        // 所属机构查询条件第一次默认用户所属机构
        // if (dto.getSearchgroupid() == null || dto.getSearchgroupid().equals("")) {
        // dto.setSearchgroupid(user.getGroup().getGroupid());
        // dto.setGroupname(user.getGroup().getGroupname());
        // }

        // 所属机构查询条件第一次默认用户所属机构
        if (dto.getSearchchildflag() == null || dto.getSearchchildflag().equals("")) {
            dto.setSearchchildflag("0");
        }

        RetResult ret = userManageService.queryUserListDwz(dto);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }
        String retobj = ret.getRetObj().toString();
        JsonMapper json = JsonMapper.nonEmptyMapper();
        Page page = json.fromJson(retobj, Page.class);

        bindPageQuery(model, page, "entityList");

        // 将实例化对象UserDTO的属性值拷贝到from中
        //this.bindForm(lazyForm, dto);
        model.addAttribute("dto", dto);

        ret = groupManageService.queryZTreeList();
        List funclist = (List) ret.getRetObj();
        request.setAttribute("funclist", json.toJson(funclist).toString());
        return "user/contentUser";
    }

    /**
     * 跳转到用户新增页面
     */
    @RequestMapping(path = "/showAdd")
    public String showAdd(Model model, HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        dto.setSex(Long.valueOf(1));
        dto.setUsertype(Long.valueOf(0));
        // dto.setGroupname(user.getGroup().getGroupname());
        // dto.setGroupid(user.getGroup().getGroupid());

        // 将实例化对象dto的属性值拷贝到from中
        //this.bindForm(viewform, dto);
        model.addAttribute("dto", dto);
        model.addAttribute("savemethod", "1");

        RetResult ret = groupManageService.queryZTreeList();
        List funclist = (List) ret.getRetObj();
        JsonMapper json = JsonMapper.nonEmptyMapper();
        request.setAttribute("funclist", json.toJson(funclist).toString());
        return "user/addUser";
    }

    /**
     * 新增用户信息
     */
    @RequestMapping(path = "/addUser")
    public String addUser(Model model, HttpServletRequest request, HttpServletResponse response, UserDTO userdto, @RequestParam("photofile") MultipartFile file) {
        //FormFile formfile = (FormFile) viewform.get("photofile");

        // 判断是否为表单重复提交
        // if (!isTokenValid(request)) {
        // this.saveErrorSession(request, "error.invalid.token");
        //
        // logger.info("新增用户数据被重复提交！");
        // return new String("/UserManageAction.do?method=listUserInfo&searchgroupid=" + userdto.getGroupid(),
        // true);
        // }
        // 重置令牌
        //resetToken(request);

        // 获得用户实体

        // 获得记录状态、操作用户、操作时间
        String iiid = request.getParameter("usercompanyid");
        //userdto.setCompanyid(iiid);

        String originalFilename = file.getOriginalFilename().trim();
        String newfilename = null;
        if (StringUtils.isNotEmpty(originalFilename)) {
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            newfilename = Identities.uuid2() + suffix;
            userdto.setPhoto(newfilename);
        }
        try {
            String sm = request.getParameter("savemethod");
            if (sm.equals("1")) {
                // 执行人员新增操作（需要判断人员编号的唯一性）
                userdto.setDeletestatus(Long.valueOf(1));
                userdto.setCreatorid(request.getAttribute("userid").toString());
                userdto.setCreatetime(UtilDataTime.getCurrentTime());

                RetResult ret = userManageService.saveUser(userdto);
                String retMessage = ret.getRetMessage();
                if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
                    dwzCallback(model, OK, null, "保存失败！", null, null, null);
                    return DWZ_COMMON_PAGE;
                }
            } else {
                // 更新修改时间
                userdto.setModifytime(UtilDataTime.getCurrentTime());
                RetResult ret = userManageService.updateUser(userdto);
                String retMessage = ret.getRetMessage();
                if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
                    dwzCallback(model, OK, null, "保存失败！", null, null, null);
                    return DWZ_COMMON_PAGE;
                }
            }

            if (StringUtils.isNotEmpty(newfilename)) {
                String newfilename_ = request.getSession().getServletContext().getRealPath("/") + ContainsKeys.USER_PHOTO_PATH + newfilename;
                file.transferTo(new File(newfilename_));
            }

            // viewform.set("searchgroupid", userdto.getGroupid());
            model.addAttribute("searchgroupid", userdto.getGroupid());
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            dwzCallback(model, OK, null, "保存失败！" + e.getMessage(), null, null, null);
            return DWZ_COMMON_PAGE;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            dwzCallback(model, OK, null, "保存失败！" + e.getMessage(), null, null, null);
            return DWZ_COMMON_PAGE;
        }


        dwzCallback(model, OK, CLOSE_CURRENT, "保存成功！", "cslms031", null, null);
        return DWZ_COMMON_PAGE;
    }

    /**
     * 跳转到用户修改页面
     */
    @RequestMapping(path = "/showUpdate")
    public String showUpdate(Model model, HttpServletRequest request, HttpServletResponse response, UserDTO userdto) {
        RetResult ret = userManageService.getUserInfoByID(userdto);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }

        UserDTO updateuserdto = new UserDTO();
        try {
            BeanUtils.populate(updateuserdto, (Map) ret.getRetObj());
        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }

        // updateuserdto.setGroupid(userdto.getSearchgroupid());
        updateuserdto.setSearchgroupid(userdto.getSearchgroupid());

        // 查询用户所属岗位的名称
        /*IPositionManage pmservice = (IPositionManage) getServiceBean("PositionManageImpl", "orcl");
        Tbposition tbposition = pmservice.getPositionInfoById(PubStringUtil.isNull(updateuserdto.getPosition()) ? "0" : updateuserdto.getPosition());
        updateuserdto.setPositionname(tbposition == null ? "-" : tbposition.getPositionname());*/

        // 查询用户所属机构的名称
        Tbgroup tbgroup = new Tbgroup();
        tbgroup.setGroupid(updateuserdto.getGroupid());

        Map map = new BeanMap(tbgroup);
        ret = groupManageService.getGroupInfoByID(map);
        retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }
        map = (Map) ret.getRetObj();
        updateuserdto.setGroupname(map.get("groupname").toString());

        // 将实例化对象UserDTO的属性值拷贝到from中
        //this.bindForm(viewform, updateuserdto);
        model.addAttribute("dto", updateuserdto);
        model.addAttribute("savemethod", "2");

        ret = groupManageService.queryZTreeList();
        List funclist = (List) ret.getRetObj();
        JsonMapper json = JsonMapper.nonEmptyMapper();
        request.setAttribute("funclist", json.toJson(funclist).toString());
        return "user/editUser";
    }

    /**
     * 跳转到用户修改页面
     */
    @RequestMapping(path = "/showTeacherUpdate")
    public String showTeacherUpdate(Model model, HttpServletRequest request, HttpServletResponse response, UserDTO userdto) {
        RetResult ret = userManageService.getUserInfoByID(userdto);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }

        UserDTO updateuserdto = new UserDTO();
        try {
            BeanUtils.populate(updateuserdto, (Map) ret.getRetObj());
        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }

        // updateuserdto.setGroupid(userdto.getSearchgroupid());
        updateuserdto.setSearchgroupid(userdto.getSearchgroupid());

        // 查询用户所属岗位的名称
        /*IPositionManage pmservice = (IPositionManage) getServiceBean("PositionManageImpl", "orcl");
        Tbposition tbposition = pmservice.getPositionInfoById(PubStringUtil.isNull(updateuserdto.getPosition()) ? "0" : updateuserdto.getPosition());
        updateuserdto.setPositionname(tbposition == null ? "-" : tbposition.getPositionname());*/

        // 查询用户所属机构的名称
        Tbgroup tbgroup = new Tbgroup();
        tbgroup.setGroupid(updateuserdto.getGroupid());

        Map map = new BeanMap(tbgroup);
        ret = groupManageService.getGroupInfoByID(map);
        retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }
        map = (Map) ret.getRetObj();
        updateuserdto.setGroupname(map.get("groupname").toString());

        // 将实例化对象UserDTO的属性值拷贝到from中
        //this.bindForm(viewform, updateuserdto);
        model.addAttribute("dto", updateuserdto);
        model.addAttribute("savemethod", "2");

        ret = groupManageService.queryZTreeList();
        List funclist = (List) ret.getRetObj();
        JsonMapper json = JsonMapper.nonEmptyMapper();
        request.setAttribute("funclist", json.toJson(funclist).toString());
        request.setAttribute("teacherupdate", "teacherupdate");
        return "user/addUser";
    }

    /**
     * 修改用户信息
     */
    @RequestMapping(path = "/updateUser")
    public String updateUser(Model model, HttpServletRequest request, HttpServletResponse response, UserDTO userdto, @RequestParam("photofile") MultipartFile file) {
        //FormFile formfile = (FormFile) viewform.get("photofile");

        String originalFilename = file.getOriginalFilename().trim();
        String newfilename = null;
        if (StringUtils.isNotEmpty(originalFilename)) {
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            newfilename = Identities.uuid2() + suffix;
            userdto.setPhoto(newfilename);
        }
        try {
            // 更新修改时间
            userdto.setModifytime(UtilDataTime.getCurrentTime());
            RetResult ret = userManageService.updateUser(userdto);
            String retMessage = ret.getRetMessage();
            if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
                dwzCallback(model, OK, null, "保存失败！", null, null, null);
                return DWZ_COMMON_PAGE;
            }

            if (StringUtils.isNotEmpty(newfilename)) {
                String newfilename_ = request.getSession().getServletContext().getRealPath("/") + ContainsKeys.USER_PHOTO_PATH + newfilename;
                file.transferTo(new File(newfilename_));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            dwzCallback(model, OK, null, "保存失败！" + e.getMessage(), null, null, null);
            return DWZ_COMMON_PAGE;
        }

        return new String("/user/listUserInfo?searchgroupid=" + userdto.getGroupid());
    }

    /**
     * 删除用户记录（单条记录逻辑删除操作）
     */
    @RequestMapping(path = "/deleteUser")
    public String deleteUser(Model model, HttpServletRequest request, HttpServletResponse response, Tbuser tbuser) {
        String searchgroupidstr = request.getParameter("searchgroupid");

        // 单条用户记录逻辑删除
        userManageService.removeUser(tbuser);

        Map map = new BeanMap(tbuser);
        RetResult ret = groupManageService.getGroupInfoByID(map);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }
        int retint = (Integer) ret.getRetObj();

        return new String("/UserManageAction.do?method=listUserInfo&searchgroupid=" + searchgroupidstr);
    }

    /**
     * 重置用户密码 add by bradnd
     */
    @RequestMapping(path = "/resetpassword")
    public String resetpassword(Model model, HttpServletRequest request, HttpServletResponse response, Tbuser tbuser) {
        // 执行方法
        RetResult ret = userManageService.resetpassword(tbuser);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }

        dwzCallback(model, OK, null, "密码重置成功！您的新密码是：" + tbuser.getUsercode(), "cslms031", null, null);
        return DWZ_COMMON_PAGE;
    }

    /**
     * 更新用户密码 add by zhaoyuyang
     */
    /*
    @RequestMapping(path = "/updateUserPwd")
    public String updateUserPwd(Model model, HttpServletRequest request, HttpServletResponse response) {
        String flag = request.getParameter("flag");

        if (StringUtils.isNotEmpty(flag)) {

            if (flag.equals("1")) {
                //request.setAttribute("userid", user.getUser().getUserid());
                return new String("/pages/systemservice/usermanage/changepwd.jsp");
            }

        }

        Tbuser tbuser = new Tbuser();
        tbuser.setUserid(request.getParameter("userid"));
        tbuser.setUsercode(request.getParameter("rnewPassword"));

        // 执行方法
        RetResult ret = userManageService.resetpassword(tbuser);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            dwzCallback(model, OK, null, "密码修改失败！", null, null, null);
            return DWZ_COMMON_PAGE;
        }

        dwzCallback(model, OK, "closeCurrent", "密码修改成功！您的新密码是：" + tbuser.getUsercode(), "", null, null);
        return DWZ_COMMON_PAGE;
    }*/

    /**
     * 更新用户积分 add by zhaoyuyang
     */
    @RequestMapping(path = "/updateUserIntegration")
    public String updateUserIntegration(Model model, HttpServletRequest request, HttpServletResponse response) {
        String flag = request.getParameter("flag");
        String integration = request.getParameter("integration");
        String userid = request.getParameter("userid");
        if (StringUtils.isNotEmpty(flag)) {
            if (flag.equals("1")) {
                //request.setAttribute("userid", userid);
                request.setAttribute("integration", integration);
                return "user/changeJiFen";
            }

        }

        // 执行方法
        if (StringUtils.isNotBlank(integration)) {
            Tbuser user = new Tbuser(userid);
            user.setIntegration(Long.parseLong(integration));

            RetResult ret = userManageService.updateUserIntegration(user);
            String retMessage = ret.getRetMessage();
            if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
                dwzCallback(model, OK, null, "积分更新失败！", null, null, null);
                return DWZ_COMMON_PAGE;
            }
        }

        dwzCallback(model, OK, "closeCurrent", "积分更新成功！", "cslms031", null, null);
        return DWZ_COMMON_PAGE;
    }

    /**
     * 批量删除用户记录（多条记录逻辑删除操作）
     */
    @RequestMapping(path = "/deleteBatchUser")
    public String deleteBatchUser(Model model, HttpServletRequest request, HttpServletResponse response, String ids) {
        // 单条用户记录逻辑删除
        RetResult ret = userManageService.removeAllBatchUser(ids);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            dwzCallback(model, OK, null, "删除失败！", null, null, null);
            return DWZ_COMMON_PAGE;
        }

        dwzCallback(model, OK, null, "删除成功！", "cslms031", null, null);

        return DWZ_COMMON_PAGE;
    }

    /**
     * 更新人员的有效状态（激活状态）
     */
    @RequestMapping(path = "/updateStatus")
    public String updateStatus(Model model, HttpServletRequest request, HttpServletResponse response, Tbuser tbuser) {
        String searchgroupidstr = request.getParameter("searchgroupid");

        RetResult ret = userManageService.updateUserStatus(tbuser);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            dwzCallback(model, OK, null, "状态更新失败！", null, null, null);
            return DWZ_COMMON_PAGE;
        }

        // 将实例化对象Tbuser的属性值拷贝到from中
        //this.bindForm(viewform, tbuser);
        model.addAttribute("tbuser", tbuser);

        dwzCallback(model, OK, null, "状态更新成功！", "cslms031", null, null);
        return DWZ_COMMON_PAGE;
    }

    /**
     * 根据用户编号获得单条展示的用户信息
     */
    @RequestMapping(path = "/showOneUserInfo")
    public String showOneUserInfo(Model model, HttpServletRequest request, HttpServletResponse response, Tbuser tbuser) {
        // 获得单条信息
        RetResult ret = userManageService.getUserByIDForDisplay(tbuser);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            dwzCallback(model, OK, null, "状态更新失败！", null, null, null);
            return DWZ_COMMON_PAGE;
        }
        UserDTO dto = new UserDTO();
        try {
            BeanUtils.populate(dto, (Map) ret.getRetObj());
        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }

        // 判断用户头像图片是否为空，如果为空，则使用默认的图片进行形式
        if (null == dto.getPhoto() || dto.getPhoto().indexOf(".") == -1) {
            dto.setPhoto("themes/default/images/defaultuserpic.jpg");
        } else {
            dto.setPhoto("resource/userphoto/" + dto.getPhoto());
        }

        // 将实例化对象Tbuser的属性值拷贝到from中
        //this.bindForm(viewform, dto);
        model.addAttribute("dto", dto);

        return "user/showUser";
    }

    /**
     * 跳转到人员导入页面 modify by lianyg 增加页面跳转
     */
    @RequestMapping(path = "/gotoImportUsers")
    public String gotoImportUsers(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "user/importUser";
    }

    /**
     * add by Zhao Yuyang 2010.10.11 人员EXCEL文件导入方法
     * <p/>
     * modify bu lianyg 2010-10-28 增加对人员副表数据的插入操作，调整spring配置文件等
     */
    @RequestMapping(path = "/importUser")
    public String importUser(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("excelFile") MultipartFile file) {
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
            dwzCallback(model, ERROR, null, "文件上传失败！", null);
            return DWZ_COMMON_PAGE;
        }

        //导入文件处理
        String ctx = request.getSession().getServletContext().getRealPath("/");
        try {
            // 初始化EXCEL解析组件
            AbstractExcelToBean aetb = new JxlExcelToBeanImp("user", null);

            // 首先判断公共的校验是否为空，不为空直接返回错误信息
            if (!aetb.getErrorData().isEmpty()) {
                request.getSession().setAttribute("errorData", aetb.getErrorData());
                String returnPath = request.getContextPath()+"/user/importUserError";

                dwzCallback(model, "", "forward", "导入失败！", null, null, returnPath);
                return DWZ_COMMON_PAGE;
            }

            // 读取上传到服务器的EXCEL人员信息，本方法返回的List中实在配置文件中绑定的业务BEAN
            List excelBean = aetb.getBeanList(newfilename);

            RetResult ret = userManageService.importUser(excelBean, userid);
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
            dwzCallback(model, ERROR, null, "导入失败！" + e.getMessage(), null);
            return DWZ_COMMON_PAGE;
        }

        if (!errorlist.isEmpty()) {
            request.getSession().setAttribute("errorData", errorlist);

            String returnPath = request.getContextPath()+"/user/importUserError";

            dwzCallback(model, "", "forward", "导入失败！", null, null, returnPath);
            return DWZ_COMMON_PAGE;
        }

        dwzCallback(model, OK, CLOSE_CURRENT, "导入成功！", "cslms031", null, null);
        return DWZ_COMMON_PAGE;

    }

    //导入学生出错页面
    @RequestMapping(path = "/importUserError")
    public String importUserError(HttpServletRequest request, Model model) {
        return "user/importUserError";
    }

    @RequestMapping(path = "/getUserListPaiMing")
    public String getUserListPaiMing(Model model, HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        String groupid = dto.getGroupid();
        Map map = new HashMap();
        map.put("groupid", groupid);
        RetResult ret = groupManageService.getGroupInfoByID(map);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }
        GroupDTO group = new GroupDTO();
        try {
            BeanUtils.populate(group, (Map) ret.getRetObj());
        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }

        // 所属机构查询条件第一次默认用户所属机构
        // if (dto.getSearchgroupid() == null || dto.getSearchgroupid().equals("")) {
        // dto.setSearchgroupid(user.getGroup().getGroupid());
        // dto.setGroupname(user.getGroup().getGroupname());
        // }
        String all = request.getParameter("searchgroupid");
        if (StringUtils.isEmpty(all)) {
            dto.setSearchgroupid(group.getGroupid());
            dto.setGroupname(group.getGroupname());
        }

        // 所属机构查询条件第一次默认用户所属机构
        if (dto.getSearchchildflag() == null || dto.getSearchchildflag().equals("")) {
            dto.setSearchchildflag("0");
        }

        ret = userManageService.getUserListPaiMing(dto);
        retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }
        String retobj = ret.getRetObj().toString();
        JsonMapper json = JsonMapper.nonEmptyMapper();
        Page page = json.fromJson(retobj, Page.class);

        bindPageQuery(model, page, "entityList");


        // 将实例化对象UserDTO的属性值拷贝到from中
        //this.bindForm(lazyForm, dto);
        model.addAttribute("dto", dto);

        ret = groupManageService.queryZTreeList();
        List funclist = (List) ret.getRetObj();
        request.setAttribute("funclist", json.toJson(funclist).toString());
        return "user/userListPaiMing";
    }

    @RequestMapping(path = "/getUserListPaiMingByBanJi")
    public String getUserListPaiMingByBanJi(Model model, HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        String perPage = request.getParameter("perPage");
        String Searchgroupid = request.getParameter("searchgroupid");

        UserDTO dto_1 = (UserDTO) redisCacheService.getRawCacheMapValue("user", dto.getUsercode());
        if (StringUtils.isEmpty(Searchgroupid)) {
            Searchgroupid = dto_1.getGroupid();
        }
        dto.setSearchgroupid(Searchgroupid);

        RetResult ret = userManageService.getUserListPaiMingByBanJi(dto);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }
        String retobj = ret.getRetObj().toString();
        JsonMapper json = JsonMapper.nonEmptyMapper();
        Page page = json.fromJson(retobj, Page.class);

        bindPageQuery(model, page, "entityList");

        //this.bindForm(lazyForm, dto);
        model.addAttribute("dto", dto);

        ret = groupManageService.queryZTreeList();
        List funclist = (List) ret.getRetObj();
        request.setAttribute("funclist", json.toJson(funclist).toString());

        return "user/getUserListPaiMingByBanJi";
    }

    @RequestMapping(path = "/getUserListPaiMingByNianJi")
    public String getUserListPaiMingByNianJi(Model model, HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        String perPage = request.getParameter("perPage");
        String companyid = request.getParameter("companyid");

        UserDTO dto_1 = (UserDTO) redisCacheService.getRawCacheMapValue("user", dto.getUsercode());
        if (StringUtils.isEmpty(companyid)) {
            //companyid = dto_1.getCompanyid();
        }

        //dto.setCompanyid(companyid);

        RetResult ret = userManageService.getUserListPaiMingByNianJi(dto);
        String retMessage = ret.getRetMessage();
        if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
            return "error/500";
        }
        String retobj = ret.getRetObj().toString();
        JsonMapper json = JsonMapper.nonEmptyMapper();
        Page page = json.fromJson(retobj, Page.class);

        bindPageQuery(model, page, "entityList");


        // 将实例化对象UserDTO的属性值拷贝到from中
        //this.bindForm(lazyForm, dto);
        model.addAttribute("dto", dto);

        ret = groupManageService.queryZTreeList2LastNodeIsHidden();
        List funclist = (List) ret.getRetObj();
        json = JsonMapper.nonEmptyMapper();
        request.setAttribute("funclist", json.toJson(funclist).toString());
        System.out.println(json.toJson(funclist).toString());
        return "user/getUserListPaiMingByNianJi";
    }

    public static void main(String[] args) throws Exception {

    }
}
