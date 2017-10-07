<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中望建筑工程识图能力实训评价软件</title>
<link rel="stylesheet" href="${ctx}/themes/zw20/css/public.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/themes/zw20/css/index.css" type="text/css" />
<script type="text/javascript" src="${ctx}/themes/zw20/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/themes/zw20/js/layer191/layer/layer.js"></script>
<script type="text/javascript">
function loginTips(evt){
    var e = window.event || evt;
    //阻止事件冒泡
    if(e.stopPropagation){
        e.stopPropagation();
    }
    else{
        window.event.cancelBubble = true;
    }
    if($("#username").val()==""){
        $("#login_name > input").focus();
        $(".login_tips > p").html("用户名不能为空！");
        addBodyClickClearLoginTips();
        return false;
    }else if($("#password").val()==""){
        $("#login_pwd > input").focus();
        $(".login_tips > p ").html("密码不能为空！");
        addBodyClickClearLoginTips();
        return false;
    }else{
        $(".login_tips > p ").html("");
        document.loginForm.submit();
    }
	var oMark = false;
    function addBodyClickClearLoginTips(){
        $("body").click(function(){
            if(oMark){
                $(".login_tips > p ").html("");
                $("body").unbind("click");
            }
            oMark = true;
        })
    }

//	if(oMark){
//		$('.login_tips').animate({top:'50px',opacity:'1'},500,'linear').css({flter:"alpha(Opacity=100)"});
//		oMark = false;
//		var oMark1 = true;
//		addListener(document.body,"click", function(){
//			if(oMark1){
//				$('.login_tips').animate({top:'10px',opacity:'0'},500,'linear',function(){
//				$('.login_tips').css('top','96px')
//				}).css({flter:"alpha(Opacity=0)"});
//				console.log('111');
//			}
//			oMark1 = false;
//			oMark = true;
//			})
//	}
}

function addListener(element,e,fn){
	element.addEventListener?element.addEventListener(e,fn,false):element.attachEvent("on" + e,fn);
}
</script>
</head>
<body>
<!--header begin -->
 <div id="logo">
       <h1><a >中望建筑工程识图能力实训评价软件</a></h1>
 </div>
<!--header end -->
<!--main begin -->
<div class="main" style="position:absolute; left:50%; top:50%; margin-left:-590px; margin-top:-139px;">
	<div class="nav_wrap">
		<div class="nav_l">
			<div class="nav nav_01">
				<a ><h3>首页</h3><span>Home</span></a>
			</div>
			<div class="nav nav_02">
				<a  ><h3>智能组卷</h3><span> Smart Individulized Test</span></a>
			</div>
		</div>
		<div class="nav_c">
			<div class="nav nav_03">
				<a  ><h3>单项识图</h3><span>Single Exercises</span></a>
			</div>
			<div class="nav nav_04">
				<a  ><h3>能力自测</h3><span>Self Testing</span></a>
			</div>
		</div>
		<div class="nav_r">
			<div class="nav nav_05">
				<a><h3>能力评价</h3><span>Ability Evaluation</span></a>
			</div>
			<div class="nav nav_06">
				<a><h3>综合识图</h3><span>Combined Exercises</span></a>
			</div>
		</div>
	</div>
	<div class="logo_wrap">
		<h2>用户登录</h2>
		<form id="loginForm" name="loginForm" method="post" action="${ctx}/admin/doLogin" class="login">
			<div class="form_item" id="login_name">
				<label></label>
				<input type="text" name="username" id='username' placeholder="请输入用户名"/>
			</div>
			<div class="form_item" id="login_pwd">
				<label></label>
				<input type="password" name="password" id="password" placeholder="请输入密码" maxlength="20"/>
			</div>
			<div class="login_tips">
				<p></p>
			</div>
			<div class="login_btns">
				<input  name="submitbtn" id="submitbtn" type="button"  title="登录" value="登录"  onclick="loginTips(event);"/>
			</div>
		</form>
	</div>
	
	<div class="clear"></div>
</div>
<!--main end -->
<!--footer begin -->
<div class="footer_white"></div>
<div class="footer">
	<p>浙江建设职业技术学院 & 广州中望龙腾软件股份有限公司 版权所有</p>
</div>
<!--footer end -->
</body>
<script type="text/javascript">
	window.document.getElementById("username").focus();

	//响应回车事件,兼容firefox
	/*document.onkeypress = function(e) {
		var code;
		if (!e) {
			var e = window.event;
		}
		if (e.keyCode) {
			code = e.keyCode;
		} else if (e.which) {
			code = e.which;
		}
		if (code == 13) {
			document.getElementById("submitbtn").click();
			e.cancelBubble = true;
			e.returnValue = false;
		}
	}*/
	

	document.onkeydown = function(e) {
		var currKey = 0, e = e || event;
		currKey = e.keyCode || e.which || e.charCode;//支持IE、FF

		if (currKey == 13) {
			document.getElementById("submitbtn").click();
			e.cancelBubble = true;
			e.returnValue = false;
		}
	}
</script>

<div class="message">
	<script type="text/javascript">
		var s = ('${message}' != null && '${message}' != '')?'${message}':(('${param.message}' != null && '${param.message}' != '')?decodeURI(decodeURI('${param.message}')):'');
		if (s != null && s != '') {
			layer.msg(s, {
				icon: 0,
				time: 3000, //2秒关闭（如果不配置，默认是3秒）
				offset: ['280px', '980px']
			}, function () {
				//do something
			});
		}
		//$("#username").focus();
	</script>
</div>

</html>
