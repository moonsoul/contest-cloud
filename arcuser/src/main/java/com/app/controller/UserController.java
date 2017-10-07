package com.app.controller;

import com.app.common.ContainsKeys;
import com.app.common.Identities;
import com.app.common.RetResult;
import com.app.dto.UserDTO;
import com.app.dto.UserImportDTO;
import com.app.entity.Tbgroup;
import com.app.entity.Tbuser;
import com.app.entity.Tbusergroup;
import com.app.service.*;
import com.app.utils.JsonMapper;
import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.qq.base.common.Page;
import com.qq.base.exception.BusinessException;
import com.qq.base.extremetable.ExtremeTablePageInfo;
import com.qq.base.web.DwzPage;
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
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RedisCacheService<Object> redisCacheService;

    @Autowired
    private UserManageService userManageService;

    @Autowired
    private GroupManageService groupManageService;

    @Autowired
    private UserGroupManageService userGroupManageService;

    @Autowired
    private UserLogService userLogService;

    @RequestMapping(path = "/stuLogin")
    @ResponseBody
    public RetResult stuLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String usercode, @RequestParam("password") String password) {
        UserDTO user = userManageService.getUserByUsercode(usercode);
        if (null == user) {
            RetResult ret = new RetResult("用户" + usercode + "未注册！");
            return ret;
        }

        if (0l != user.getUsertype()) {
            RetResult ret = new RetResult("用户" + usercode + " 类型错误！");
            return ret;
        }

        if (!Identities.getMD5(password).equals(user.getPassword())) {
            RetResult ret = new RetResult("用户" + usercode + " 密码填写错误！");
            return ret;
        }

        if (!"1".equals(user.getDeletestatus().toString())) {
            RetResult ret = new RetResult("用户：" + usercode + " 已被删除，请联系管理员！");
            return ret;
        }

        if (!"1".equals(user.getStatus().toString())) {
            RetResult ret = new RetResult("用户：" + usercode + " 已被锁定，请联系管理员！");
            return ret;
        }

        String authkey = Identities.uuid2();
        String ret_ = userManageService.updateUserAuthkey(usercode, authkey);
        if (!StringUtils.equals(ret_, ContainsKeys.RET_OK)) {
            RetResult ret = new RetResult("用户信息更新失败！");
            return ret;
        }

        user.setIp(request.getParameter("ip"));
        user.setAuthkey(authkey);
        userLogService.saveLog(user);

        RetResult<UserDTO> ret = new RetResult(ContainsKeys.RET_OK, user);

        logger.info(usercode + " login!--");
        return ret;
    }

    @RequestMapping(path = "/adminLogin")
    @ResponseBody
    public RetResult adminLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String usercode, @RequestParam("password") String password) {
        UserDTO user = userManageService.getUserByUsercode(usercode);
        if (null == user) {
            RetResult ret = new RetResult("用户" + usercode + "未注册！");
            return ret;
        }

        if (2l != user.getUsertype()) {
            RetResult ret = new RetResult("用户" + usercode + " 类型错误！");
            return ret;
        }

        if (!Identities.getMD5(password).equals(user.getPassword())) {
            RetResult ret = new RetResult("用户" + usercode + " 密码填写错误！");
            return ret;
        }

        if (!"1".equals(user.getDeletestatus().toString())) {
            RetResult ret = new RetResult("用户：" + usercode + " 已被删除，请联系管理员！");
            return ret;
        }

        if (!"1".equals(user.getStatus().toString())) {
            RetResult ret = new RetResult("用户：" + usercode + " 已被锁定，请联系管理员！");
            return ret;
        }

        String authkey = Identities.uuid2();
        String ret_ = userManageService.updateUserAuthkey(usercode, authkey);
        if (!StringUtils.equals(ret_, ContainsKeys.RET_OK)) {
            RetResult ret = new RetResult("用户信息更新失败！");
            return ret;
        }

        user.setIp(request.getParameter("ip"));
        user.setAuthkey(authkey);
        userLogService.saveLog(user);

        RetResult<UserDTO> ret = new RetResult(ContainsKeys.RET_OK, user);

        logger.info(usercode + " login!--");
        return ret;
    }

    @RequestMapping(path = "/userByUsercode")
    @ResponseBody
    public RetResult userByUsercode(HttpServletRequest request, HttpServletResponse response, @RequestParam("usercode") String usercode) {
        UserDTO user = userManageService.getUserByUsercode(usercode);
        if (null == user) {
            RetResult ret = new RetResult("用户" + usercode + "未注册！");
            return ret;
        }

        RetResult<UserDTO> ret = new RetResult(ContainsKeys.RET_OK, user);
        return ret;
    }

    @RequestMapping(path = "/updatePassword")
    @ResponseBody
    public RetResult updatePassword(HttpServletRequest request, HttpServletResponse response, UserDTO user) {
        userManageService.updatePassword(user);
        RetResult<UserDTO> ret = new RetResult(ContainsKeys.RET_OK);
        return ret;
    }

    @RequestMapping(path = "/queryUserList")
    @ResponseBody
    public RetResult queryUserList(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        Page page = userManageService.queryUserList(new ExtremeTablePageInfo(request, "Oracle", dto));
        JsonMapper json = JsonMapper.nonEmptyMapper();
        RetResult<String> ret = new RetResult(ContainsKeys.RET_OK, json.toJson(page));
        return ret;
    }

    @RequestMapping(path = "/queryUserListDwz")
    @ResponseBody
    public RetResult queryUserListDwz(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        Page page = userManageService.queryUserList(new DwzPage(request, dto));
        JsonMapper json = JsonMapper.nonEmptyMapper();
        RetResult<String> ret = new RetResult(ContainsKeys.RET_OK, json.toJson(page));
        return ret;
    }

    @RequestMapping(path = "/saveUser")
    @ResponseBody
    public RetResult saveUser(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        String retstr = null;
        try {
            retstr = userManageService.saveUser(dto);
        } catch (BusinessException e) {
            e.printStackTrace();
            RetResult ret = new RetResult(e.getMessage());
            return ret;
        }

        RetResult<String> ret = new RetResult(ContainsKeys.RET_OK, retstr);
        return ret;
    }

    @RequestMapping(path = "/isDisplayByUserCode")
    @ResponseBody
    public RetResult isDisplayByUserCode(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        int retint = userManageService.isDisplayByUserCode(dto.getUsercode());

        RetResult<Integer> ret = new RetResult(ContainsKeys.RET_OK, retint);
        return ret;
    }

    @RequestMapping(path = "/updateUser")
    @ResponseBody
    public RetResult updateUser(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        userManageService.updateUser(dto);

        RetResult ret = new RetResult(ContainsKeys.RET_OK);
        return ret;
    }

    @RequestMapping(path = "/getUserInfoById")
    @ResponseBody
    public RetResult getUserInfoById(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        UserDTO userdto = userManageService.getUserInfoById(dto);

        RetResult<UserDTO> ret = new RetResult(ContainsKeys.RET_OK, userdto);
        return ret;
    }

    @RequestMapping(path = "/deleteUser")
    @ResponseBody
    public RetResult deleteUser(HttpServletRequest request, HttpServletResponse response, Tbuser tbuser) {
        int retint = userManageService.deleteUser(tbuser);

        RetResult<Integer> ret = new RetResult(ContainsKeys.RET_OK, retint);
        return ret;
    }


    @RequestMapping(path = "/updateUserJiFen")
    @ResponseBody
    public RetResult updateUserJiFen(HttpServletRequest request, HttpServletResponse response, Tbuser tbuser) {
        userManageService.updateUserJiFen(tbuser);

        RetResult ret = new RetResult(ContainsKeys.RET_OK);
        return ret;
    }

    @RequestMapping(path = "/removeAllBatchUser")
    @ResponseBody
    public RetResult removeAllBatchUser(HttpServletRequest request, HttpServletResponse response, String ids) {
        String useridarr[] = ids.split(",");
        userManageService.removeAllBatchUser(useridarr);

        RetResult ret = new RetResult(ContainsKeys.RET_OK);
        return ret;
    }

    @RequestMapping(path = "/changeUserStatus")
    @ResponseBody
    public RetResult changeUserStatus(HttpServletRequest request, HttpServletResponse response, Tbuser tbuser) {
        Integer retint = userManageService.changeUserStatus(tbuser);

        RetResult ret = new RetResult(ContainsKeys.RET_OK, retint);
        return ret;
    }

    @RequestMapping(path = "/getUserInfoByIdForDisplay")
    @ResponseBody
    public RetResult getUserInfoByIdForDisplay(HttpServletRequest request, HttpServletResponse response, Tbuser tbuser) {
        UserDTO userdto = userManageService.getUserInfoByIdForDisplay(tbuser);

        RetResult<UserDTO> ret = new RetResult(ContainsKeys.RET_OK, userdto);
        return ret;
    }

    @RequestMapping(path = "/importUser")
    @ResponseBody
    public RetResult importUser(HttpServletRequest request, HttpServletResponse response) {
        String excelBeanstr = request.getParameter("excelBean");
        String userid = request.getParameter("userid");

        JsonMapper json = JsonMapper.nonEmptyMapper();
        List<UserImportDTO> excelBean = json.fromJson(excelBeanstr, json.createCollectionType(List.class, UserImportDTO.class));

        List errlist = new ArrayList();
        // 判断读取EXCEL组装的BEAN是否为空
        if (!excelBean.isEmpty()) {
            UserImportDTO[] userExcelBean = (UserImportDTO[]) excelBean.toArray(new UserImportDTO[]{});
            UserDTO[] userdto_ = new UserDTO[userExcelBean.length];
            Map map = new HashMap();

            // 开始业务逻辑合理性判断
            for (int i = 0; i < userExcelBean.length; i++) {
                // 初始化记录业务错误信息缓冲串
                StringBuffer sb = new StringBuffer();
                userdto_[i] = new UserDTO(Identities.uuid2());

                // Tbuser userTemp = getUserInfoByUserCode(userExcelBean[i].getUsercode());

                int ut = Integer.parseInt(userExcelBean[i].getUsertype());

                if (ut < 0 || ut > 3) {
                    sb.append("所属角色：必须填写大于等于0,小于等于3的数字，0代表学生，1代表老师，2代表管理员！");
                }

                if (map.containsKey(userExcelBean[i].getUsercode())) {
                    sb.append("Excel导入文件中学号：" + userExcelBean[i].getUsercode() + " 重复！");
                } else {
                    map.put(userExcelBean[i].getUsercode(), userExcelBean[i].getUsercode());
                }

                //int num = userManageService.isDisplayByUserCode(userExcelBean[i].getUsercode());
                UserDTO userdto = userManageService.getUserInfoByUsercode(userExcelBean[i].getUsercode());

                Tbgroup tempGroup = groupManageService.getGroupByGroupCode(userExcelBean[i].getGroupcode());
                if (tempGroup == null) {
                    sb.append("人员所在的班级编号为：" + userExcelBean[i].getGroupcode() + "不存在!");
                }

                // 判断用户编号唯一性
                if (userdto != null && tempGroup != null) {
                    //查找tbusergroup记录
                    Tbusergroup tbusergroup = new Tbusergroup();
                    tbusergroup.setUserid(userdto.getUserid());
                    tbusergroup.setGroupid(tempGroup.getGroupid());
                    if(userGroupManageService.checkUserGroupExisted(tbusergroup)) {
                        sb.append("用户名：" + userExcelBean[i].getUsercode() + " 及组织关系已经存在！");
                    }

                    userdto_[i].setUserid(userdto.getUserid());
                    userdto_[i].setGroupid(tempGroup.getGroupid());
                }

                boolean bool = userExcelBean[i].getUsercode().matches("[0-9a-zA-Z_-]+");
                if (!bool) {
                    sb.append("学号只能由数字、字母、下划线组合而成！");
                }

                // 判断性别并赋值
                if (userExcelBean[i].getSex().trim().equals("男")) {

                    userdto_[i].setSex(new Long(1));

                } else if (userExcelBean[i].getSex().trim().equals("女")) {

                    userdto_[i].setSex(new Long(2));

                } else {
                    sb.append("Excel导入文件性别列输入错误，请使用“男”或“女”汉字代替！");
                }

                userdto_[i].setPosition(userExcelBean[i].getPositioncode());
                userdto_[i].setEmail(userExcelBean[i].getEmail());
                // Tbposition tempPosition =
                // positionManage.getPositionByPositionCode(userExcelBean[i].getPositioncode());
                //
                // if (tempPosition == null) {
                //
                // sb.append("人员所在的岗位编号为：" + userExcelBean[i].getPositioncode() + "不存在!");
                // } else {
                // user[i].setPosition(tempPosition.getPositionid());
                // }
                //
                // //判断电子邮件格式
                // boolean emailbool =
                // userExcelBean[i].getEmail().matches("[a-zA-Z0-9_.-]+@([a-zA-Z0-9-]+.)+[a-zA-Z0-9]{2,4}");
                // if(!emailbool){
                // sb.append("电子邮件格式错误！");
                // }
                // else{
                // user[i].setEmail(userExcelBean[i].getEmail());
                // }
                //
                // 判断错误信息是否为空
                if (sb.length() > 0) {
                    errlist.add(i);
                    errlist.add(sb.toString());
                    sb.delete(0, sb.length());
                }
            }
            map = null;
            if (!errlist.isEmpty()) {
                return new RetResult(ContainsKeys.RET_ERR, errlist);
            }

            userManageService.importUser(userExcelBean, userdto_, userid);
        }

        return new RetResult(ContainsKeys.RET_OK);
    }


    @RequestMapping(path = "/getUserListPaiMing")
    @ResponseBody
    public RetResult getUserListPaiMing(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        Page page = userManageService.getUserListPaiMing(new DwzPage(request, dto));
        JsonMapper json = JsonMapper.nonEmptyMapper();
        RetResult<String> ret = new RetResult(ContainsKeys.RET_OK, json.toJson(page));
        return ret;
    }

    @RequestMapping(path = "/getUserListPaiMingByBanJi")
    @ResponseBody
    public RetResult getUserListPaiMingByBanJi(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        Page page = userManageService.getUserListPaiMingByBanJi(new DwzPage(request, dto));
        JsonMapper json = JsonMapper.nonEmptyMapper();
        RetResult<String> ret = new RetResult(ContainsKeys.RET_OK, json.toJson(page));
        return ret;
    }

    @RequestMapping(path = "/getUserListPaiMingByNianJi")
    @ResponseBody
    public RetResult getUserListPaiMingByNianJi(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        Page page = userManageService.getUserListPaiMingByNianJi(new DwzPage(request, dto));
        JsonMapper json = JsonMapper.nonEmptyMapper();
        RetResult<String> ret = new RetResult(ContainsKeys.RET_OK, json.toJson(page));
        return ret;
    }

    @RequestMapping(path = "/getUserPaiMingByID")
    @ResponseBody
    public RetResult getUserPaiMingByID(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        Long index = userManageService.getUserPaiMingByID(dto.getUserid());
        JsonMapper json = JsonMapper.nonEmptyMapper();
        RetResult<Long> ret = new RetResult(ContainsKeys.RET_OK, index);
        return ret;
    }

    public static void main(String[] args) throws Exception {

    }
}
