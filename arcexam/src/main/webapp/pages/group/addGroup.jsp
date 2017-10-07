<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<script type="text/javascript">

    var setting = {
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeExpand: beforeExpand,
            onExpand: onExpand,
            onClick: onClick17
        }
    };


    <c:if test="${savemethod==2}">
    var zNodes = ${funclist};
    </c:if>
    //////////////////////////////////////

    var curExpandNode = null;
    function beforeExpand(treeId, treeNode) {
        var pNode = curExpandNode ? curExpandNode.getParentNode() : null;
        var treeNodeP = treeNode.parentTId ? treeNode.getParentNode() : null;
        var zTree = $.fn.zTree.getZTreeObj("treeDemo17");
        for (var i = 0, l = !treeNodeP ? 0 : treeNodeP.children.length; i < l; i++) {
            if (treeNode !== treeNodeP.children[i]) {
                zTree.expandNode(treeNodeP.children[i], false);
            }
        }
        while (pNode) {
            if (pNode === treeNode) {
                break;
            }
            pNode = pNode.getParentNode();
        }
        if (!pNode) {
            singlePath(treeNode);
        }

    }
    function singlePath(newNode) {
        if (newNode === curExpandNode) return;
        if (curExpandNode && curExpandNode.open == true) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo17");
            if (newNode.parentTId === curExpandNode.parentTId) {
                zTree.expandNode(curExpandNode, false);
            } else {
                var newParents = [];
                while (newNode) {
                    newNode = newNode.getParentNode();
                    if (newNode === curExpandNode) {
                        newParents = null;
                        break;
                    } else if (newNode) {
                        newParents.push(newNode);
                    }
                }
                if (newParents != null) {
                    var oldNode = curExpandNode;
                    var oldParents = [];
                    while (oldNode) {
                        oldNode = oldNode.getParentNode();
                        if (oldNode) {
                            oldParents.push(oldNode);
                        }
                    }
                    if (newParents.length > 0) {
                        zTree.expandNode(oldParents[Math.abs(oldParents.length - newParents.length) - 1], false);
                    } else {
                        zTree.expandNode(oldParents[oldParents.length - 1], false);
                    }
                }
            }
        }
        curExpandNode = newNode;
    }

    function onExpand(event, treeId, treeNode) {
        curExpandNode = treeNode;
    }


    ////////////////////////////////////////////////

    function onClick17(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo17");


        zTree.expandNode(treeNode, null, null, null, true);
        var nodes = zTree.getSelectedNodes();
        var v = "";
        var t = "";

        nodes.sort(function compare(a, b) {
            return a.id - b.id;
        });
        for (var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i].name + ",";
            t += nodes[i].id + ",";
        }

        if (v.length > 0) {
            v = v.substring(0, v.length - 1);
            t = t.substring(0, t.length - 1);

        }

        var cityObj = $("#citySel17");
        var parentid = $('#parentid');
        cityObj.attr("value", v);
        parentid.attr("value", t);

    }

    function showMenu17() {
        var cityObj = $("#citySel17");
        var cityOffset = $("#citySel17").offset();
        $("#menuContent17").css({
            left: cityOffset.left - 110 + "px",
            top: cityOffset.top - 210 + cityObj.outerHeight() + "px"
        }).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    }
    function hideMenu() {
        $("#menuContent17").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn17" || event.target.id == "menuContent17" || $(
                        event.target).parents("#menuContent17").length > 0)) {
            hideMenu();
        }
    }

    <c:if test="${savemethod==2}">
    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo17"), setting, zNodes);
    });
    </c:if>

</script>

<div class="pageContent">
    <form id="groupManageForm" action="${ctx}/group/addGroup" styleClass="pageForm required-validate"
          method="post" onsubmit="return validateCallback(this, dialogAjaxDone)">
        <input type="hidden" name="groupid" value="${pojo.groupid}"/>
        <input type="hidden" name="location" value="${pojo.location}"/>
        <input type="hidden" name="savemethod" value="${savemethod}"/>

        <div class="pageFormContent" layoutH="58">
            <c:if test="${savemethod==1}">
                <div class="unit">
                    <label>父节点：</label>${tbgroup1.groupname}
                    <input size="30" name="parentid" id="parentid" type="hidden" value="${tbgroup1.groupid}"/>
                        <%-- <input readonly="readonly" id="citySel17" size="30" type="text" class="required" value="${tbgroup1.groupname}"/> --%>
                </div>
            </c:if>

            <c:if test="${savemethod==2}">
                <div class="unit">
                    <label>父节点：</label>${parentname}
                    <input size="30" name="parentid" id="parentid" type="hidden" value="${pojo.parentid}"/>
                        <%--
                        <input readonly="readonly" id="citySel17" size="30" type="text" class="required" value="${groupManageForm.map.parentname}"/>
                        <a id="menuBtn17" class="menuBtn" href="#" onclick="showMenu17(); return false;">选择</a>--%>
                </div>
            </c:if>

            <div class="unit">
                <c:if test="${savemethod==2}">
                    <label>编号：</label>${pojo.groupcode}
                    <input size="30" name="groupcode" id="groupcode" type="hidden" value="${pojo.groupcode}"/>
                </c:if>
                <c:if test="${savemethod==1}">
                    <label>编号：</label>
                    <input type="text" name="groupcode" id="groupcode" size="30" class="required alphanumeric"
                           maxlength="10" value="${pojo.groupcode}">
                </c:if>
            </div>
            <div class="unit">
                <label>名称：</label>
                <input type="text" name="groupname" id="groupname" size="30" class="required" maxlength="50" value="${pojo.groupname}"/>
            </div>
            <c:if test="${savemethod==1}">
                <c:if test="${(tbgroup1.parentid=='9001')||(tbgroup1.parentid=='-5')}"><input type="hidden" name="type"
                                                                                              value="1"/></c:if>
                <c:if test="${(tbgroup1.parentid!='9001')&&(tbgroup1.parentid!='-5')}">
                    <c:if test="${tbgroup1.type=='1'}"><input type="hidden" name="type" value="2"/></c:if>
                </c:if>

            </c:if>
            <c:if test="${savemethod==2}">
                <input type="hidden" name="type" value="${pojo.type}"/>
            </c:if>
            <%--
            <div class="unit">
                <label>组织类型：</label>
                <html:radio property="type" styleId="type" value="1">年级</html:radio>
                <html:radio property="type" styleId="type" value="2">班级</html:radio>
            </div>
             --%>
            <div class="unit">
                <label>备注：</label>
                <textarea name="remark" id="remark" cols="80" rows="4">${pojo.remark}</textarea>
            </div>

        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">提交</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>
<div id="menuContent17" class="menuContent"
     style="display: none; position: absolute; background-color:#EEF3F4; border-radius:6px;">
    <ul id="treeDemo17" class="ztree" style="margin-top: 0; width: 175px;"></ul>
</div>
