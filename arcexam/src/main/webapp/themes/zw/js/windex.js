$(function(){
	$(".pagebox a:last").siblings().css("border-right","none");
	$(".ridobox li").click(function(){
		$(this).addClass("on").siblings().removeClass();
	});
	$(".butns5").hover(function(){$(this).find("dl").fadeIn();},function(){$(this).find("dl").fadeOut();})
	//flash_resize(); //设置答题相关页面flash大小

	$(".changesize").click(function(){
		if($(this).attr("src") == "images/ico_big.jpg"){
			$(this).parent("dd").find("embed").attr("width","800px");
			$(this).parent("dd").find("embed").attr("height","633px");
			$(this).attr("src","images/ico_small.jpg");
		}else{
			$(this).parent("dd").find("embed").attr("width","185px");
			$(this).parent("dd").find("embed").attr("height","146px");
			$(this).attr("src","images/ico_big.jpg");
		}
	})

	$(".ico_close").hover(  
		function() {  
			$(".ico_close").stop().attr('src',"images/ico_close_on.png");
		},   
		function() {  
			$(".ico_close").stop().attr('src',"images/ico_close.png");
	}); 
});

function selectdiv(id){
	var sid=$(id).find("p");
	sid.next("ol").slideToggle(200).find("li").click(function(){
		sid.html($(this).html());
		$(this).parent().slideUp(200);
	}).parent().mouseleave(function(){
		$(this).slideUp(200);
	});
};

function changequestion(k){

	/*******************************
	 * 此处仅作模拟提交使用，正规嵌入程序时，应该使用ajax的 $.post() 来改变题干、题号与答案的对应；与以下模拟程序基本无关。
	 *******************************/
	var snum = 10; //总题目数
	var p = $("#question").val();
	if(k == 'prev'){
		w = parseInt(p) - 1;
	}else if(k == 'next'){
		w = parseInt(p) + 1;
	}else{
		w = k;
	}
	akey = w - 1;

	if(akey < 0){
		akey = 0;
		w = 1;
	}
	if(akey >= snum){
		akey = snum - 1;
		w = snum;
	}
	
	var ques = new Array();
	ques[0] = new Array();
	ques[0]['question'] = "这是第一小题，我还有一个特殊符号 轴2<img src='images/zhou2.png'>轴2轴2轴2";
	ques[0]['flash'] = '<embed  width="100%" height="100%" type="application/x-shockwave-flash" src="swf/001.swf" pluginspage="http://www.adobe.com/go/getflashplayer"/>';

	ques[1] = new Array();
	ques[1]['question'] = "按图中所示，请选择正确的国家宏观共和国海关和国家宏观环境规划结构和价格监管机构及监管机构及国家宏观角钢精村注。构及监管机构及国家宏观。（2.5）";
	ques[1]['flash'] = '<embed  width="100%" height="100%" type="application/x-shockwave-flash" src="swf/002.swf" pluginspage="http://www.adobe.com/go/getflashplayer"/>';

	ques[2] = new Array();
	ques[2]['question'] = "这是第三小题，我也有一个特殊符号 轴C<img src='images/zhouc.png'>轴C轴C轴C";
	ques[2]['flash'] = '<embed  width="100%" height="100%" type="application/x-shockwave-flash" src="swf/003.swf" pluginspage="http://www.adobe.com/go/getflashplayer"/>';
	
	ques[3] = new Array();
	ques[3]['question'] = "这是第四小题，我也有一个特殊符号 轴D<img src='images/zhoud.png'>轴D轴D轴D";
	ques[3]['flash'] = '<embed  width="100%" height="100%" type="application/x-shockwave-flash" src="swf/003.swf" pluginspage="http://www.adobe.com/go/getflashplayer"/>';
	
	ques[4] = new Array();
	ques[4]['question'] = "这是第五小题";
	ques[4]['flash'] = '<embed  width="100%" height="100%" type="application/x-shockwave-flash" src="swf/003.swf" pluginspage="http://www.adobe.com/go/getflashplayer"/>';
	
	ques[5] = new Array();
	ques[5]['question'] = "这是第六小题";
	ques[5]['flash'] = '<embed  width="100%" height="100%" type="application/x-shockwave-flash" src="swf/003.swf" pluginspage="http://www.adobe.com/go/getflashplayer"/>';
	
	ques[6] = new Array();
	ques[6]['question'] = "这是第七小题";
	ques[6]['flash'] = '<embed  width="100%" height="100%" type="application/x-shockwave-flash" src="swf/003.swf" pluginspage="http://www.adobe.com/go/getflashplayer"/>';
	
	ques[7] = new Array();
	ques[7]['question'] = "这是第八小题";
	ques[7]['flash'] = '<embed  width="100%" height="100%" type="application/x-shockwave-flash" src="swf/003.swf" pluginspage="http://www.adobe.com/go/getflashplayer"/>';
	
	ques[8] = new Array();
	ques[8]['question'] = "这是第九小题";
	ques[8]['flash'] = '<embed  width="100%" height="100%" type="application/x-shockwave-flash" src="swf/003.swf" pluginspage="http://www.adobe.com/go/getflashplayer"/>';
	
	ques[9] = new Array();
	ques[9]['question'] = "这是第十小题";
	ques[9]['flash'] = '<embed  width="100%" height="100%" type="application/x-shockwave-flash" src="swf/003.swf" pluginspage="http://www.adobe.com/go/getflashplayer"/>';
	
	var flash = ques[akey]['flash'];
	var question = ques[akey]['question'];

	/***************模拟程序结束**********************/
	$('input:radio[name=answer]').attr('checked',false);
	$('.ridobox').find("label").css('color',"#FFF");

	//判断答案
	if(questions[akey] == "A"){
		$('input:radio[name=answer]:eq(0)').attr("checked","true");
		$('input:radio[name=answer]:eq(0)').parent('label').css("color","#fff82e");
	}else if(questions[akey] == "B"){
		$('input:radio[name=answer]:eq(1)').attr("checked","true");
		$('input:radio[name=answer]:eq(1)').parent('label').css("color","#fff82e");
	}else if(questions[akey] == "C"){
		$('input:radio[name=answer]:eq(2)').attr("checked","true");
		$('input:radio[name=answer]:eq(2)').parent('label').css("color","#fff82e");
	}else if(questions[akey] == "D"){
		$('input:radio[name=answer]:eq(3)').attr("checked","true");
		$('input:radio[name=answer]:eq(3)').parent('label').css("color","#fff82e");
	}


	$('#showflash').html(flash);
	$('#showquestion').html(question);
	$("#question").val(w);
	
}

function showdiv(){
	var $high=document.documentElement.clientHeight;
	$(".tipbg").height($high).show();
	$(".tipdiv").show();
}
function closediv(){
	$(".tipbg,.tipdiv").hide();
}

/*---------查看答题--------------*/
function show_see_div(){
	var $high=document.documentElement.clientHeight;
	$(".tipbg").height($high).show();
	$(".see").show();
}

function close_see_div(){
	$(".tipbg").hide();
	$(".see").hide();
}

/*---------答题页-答案容器--------------*/
var questions = new Array();
	questions[0] = "";
	questions[1] = "";
	questions[2] = "";
	questions[3] = "";
	questions[4] = "";
	questions[5] = "";
	questions[6] = "";
	questions[7] = "";
	questions[8] = "";
	questions[9] = "";


/*---------答题页按钮点击变色--------------*/

$(".ridobox input").click(function(){
	$(".ridobox label").css("color","#FFF");
	$(this).parent('label').css("color","#fff82e");

	var wei = $("#weida");
	if(wei.length > 0){
		//点击后 设置此题以作答
		var hml = $("#weida").html();
		var f = "<a>" + $("#question").val() + "</a>";
		re=new RegExp(f,"g");
		$("#weida").html(hml.replace(re,""));

		var qid = $("#question").val();
		qid -= 1;

		if(questions[qid] == ""){
			var weidanum = parseInt($("#weidanum").html());
			weidanum -= 1;
			$("#weidanum").html(weidanum);
		}
		questions[qid] = $(this).val();
	}

})


/*---------全选--------------*/
function selectall(p,frm){
	if(p.checked){
		$("input[name='"+ frm +"']").each(function(){
			$(this).attr("checked",true);
		 }); 
	}else{
		$("input[name='"+ frm +"']").each(function(){
			$(this).attr("checked",false);
		 }); 
	}
}

/*---------flash大小适应--------------
function flash_resize(){
	var km = $("#showflash");
	if(km.length > 0){
		var win_hi = $(window).height();
		if(win_hi > 768){
			$("#showflash").css({"width":"800px","height":"633px"});
		}else{
			$("#showflash").css({"width":"752px","height":"595px"});
			$(".flashbox").css({"width":"752px"});
		}
	}
}*/