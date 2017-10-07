$(document).ready(function(){
	var selecteNum = 0;
	part_item()
	/*左侧菜单*/
	$('.mainmenu').hover(function(){
		$(this).find('.menu_sub').show()
	},function(){
		$(this).find('.menu_sub').hide()
	});
	/*
	//试题加减
	$('.num_minus').bind('click',function(){
		var valOld = $(this).parent().find('.num_input').val();
			if((parseInt(valOld)-1)<0){
				//alert('不能小于0。'); //小于时候也不提示，防止出现duang的一声
			}else{
				valOld--;
				$(this).parent().find('.num_input').val(valOld);
		}
			getCount();
	})
	
	$('.num_add').bind('click',function(){
		var valOld = $(this).parent().find('.num_input').val();
		var max = $(this).parent().find('.num_max').val();
		if((parseInt(valOld)+1)>parseInt(max)){
//			alert('超过最高题数限制。'+valOld+'---'+max); //大于时候不提示，防止出现duang的一声
		}else{
			valOld++;
			$(this).parent().find('.num_input').val(valOld);
		}
		getCount();
	})
	*/
	$('.select_btn').bind('click',function(){
		var index = $(this).parents('tr').attr('id').substring(13);
		var selectVal = $(this).parents().siblings('.part_itme_t').html();
		var str = '<li id="item_'+index+'"><a href="#">'+selectVal+'</a><span>'+selectVal+'</span><em><i>删除</i></em></li>';
		if($(this).is(":checked")){
			$(this).parents('.part_student').find('.part_selected').append(str);
		}
		else{
			$(this).parents('.part_student').find('.part_selected li').remove('li[id=item_'+index+']');
		}
		part_item();
	})
	$('.selectall').bind('click',function(){
		if($(this).is(":checked")){
			$(this).parents('.part_student').find('.select_btn').attr("checked", true);
			$(this).parents('.part_student').find('.select_btn').each(function(index){
				var selectVal = $(this).parents().siblings('.part_itme_t').html();
				var str = '<li id="item_'+index+'"><a href="#">'+selectVal+'</a><span>'+selectVal+'</span><em><i>删除</i></em></li>';
				$('.part_selected').append(str);
			})
		}
		else{
			$(this).parents('.part_student').find('.select_btn').attr("checked", false);
			$(this).parents('.part_student').find('.part_selected li').remove();
		}
		part_item();
	})
})
/*试题选项卡*/
function tab(obj,id,classname){
	$(obj).parent().parent().find('a').removeClass('current');
	$(obj).addClass('current');
	$('.'+classname).css('display','none');
	$('#'+classname+id).css('display','block');
}

/*已选试题分类*/
function part_item(){
	$('.part_selected li a').hover(function(){
		$(this).siblings('span').show();
		$(this).siblings('em').show();
	},function(){
		$(this).siblings('span').hide();
		$(this).siblings('em').hide();
	})
	$('.part_selected li em').hover(function(){
		$(this).siblings('span').show();
		$(this).show();
	},function(){
		$(this).siblings('span').hide();
		$(this).hide();
	})
	$('.part_selected li').bind('click',function(){
		var index = $(this).attr('id').substring(5);
		$(this).parents('.part_student').find('#part_student_'+index).find('.select_btn').attr("checked", false);
		$(this).remove();
	})
}

	