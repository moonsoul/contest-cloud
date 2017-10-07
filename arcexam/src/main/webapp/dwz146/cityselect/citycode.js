<!--
		var settingcode = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: oncodeClick 
			}
		};
		function oncodeClick(e, treeId, treeNode) {
			var cityObj = $("#title",navTab.getCurrentPanel());
			cityObj.attr("value",treeNode.id);
			var cityOb = $("#parentids",navTab.getCurrentPanel());
			cityOb.attr("value",treeNode.name);
			hidecodeMenu();
			cityOb.focus();
			cityOb.keydown();
		}
		
        var content1;
		function showMenu(cobj,content) {
			var cityObj=$("#"+cobj,navTab.getCurrentPanel());
			var cityOffset = cityObj.offset();
			$("#"+content,navTab.getCurrentPanel()).css({left:cityOffset.left-212 + "px", top:cityOffset.top + cityObj.outerHeight()-83 + "px"}).slideDown("fast");
			content1=content;
			$("body").bind("mousedown", onBodyDown);
		}
		
		
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0||event.target.id == content1 || $(event.target).parents("#"+content1).length>0)) {
				hidecodeMenu();
			}
		}
		function hidecodeMenu() {
			$(".menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		

		//-->
		var jQuery=$;
		var curprovince='';
		var curcounty='';
		var curcity='';
		var url='';

		//取所有省份
		function getProvince(){
		    //&callback=?"注意这个是为了解决跨域访问 的问题   
		  var url1 =url+"0&callback=?";
		jQuery.getJSON(url1,null,function call(result){   
		setProvince(result); 
		});



		}




		//设置省份
		function setProvince(result){

		var province = document.getElementById("toProvince");

		jQuery.each(result.data, function(i, area){
		var value = area.id;
		var text = area.name;
		var option = new Option(text,value);
		if (value==curprovince) {
			option.selected = true;
			}

		province.options.add(option);

		}); 
		if(!province.options[province.selectedIndex].value == ""){
		getArea('city');
		}
		}


		//按上级ID取子地区
		function getArea(name){

		if( name=='city' ){
		clearSel(document.getElementById("toCity")); //清空城市
		var province = document.getElementById("toProvince");
		if(province.options[province.selectedIndex].value == ""){ 
			var city = document.getElementById("toCity");
			var value = '';
			var text = '请选择城市...';
			var option = new Option(text,value);
			city.options.add(option);
			getArea('county');
			return;}
		var areaId = province.options[province.selectedIndex].value;

		 var url1 =url+areaId+"&callback=?";
		jQuery.getJSON(url1,null,function call(result){   
		setCity(result); 
		});

		}else if( name=='county'){
		clearSel(document.getElementById("toCounty")); //清空城区
		var city = document.getElementById("toCity");
		if(city.options[city.selectedIndex].value == ""){
			var county = document.getElementById("toCounty");
			var value = '';
			var text = '请选择城区...';
			var option = new Option(text,value);
			county.options.add(option);
			return;}
		var areaId = city.options[city.selectedIndex].value;

		var url1 =url+areaId+"&callback=?";
		jQuery.getJSON(url1,null,function call(result){   
		setCounty(result); 
		});

		}
		}

		//当改变省份时设置城市
		function setCity(result){

		var city = document.getElementById("toCity");

		jQuery.each(result.data, function(i, area){
		var value = area.id;
		var text = area.name;
		var option = new Option(text,value);

		if (value==curcity) {
			option.selected = true;
			}

		city.options.add(option);

		}); 
		if(city.options[city.selectedIndex].value != ""){
		getArea('county');
		}

		}


		//当改变省份时设置城市
		function setCounty(result){

		var county = document.getElementById("toCounty");

		jQuery.each(result.data, function(i, area){
		var value = area.id;
		var text = area.name;
		var option = new Option(text,value);
		if (value==curcounty) {
			option.selected = true;
			}
		county.options.add(option);
		}); 

		}

		// 清空下拉列表
		function clearSel(oSelect){

		while(oSelect.childNodes.length>0){
		oSelect.removeChild(oSelect.childNodes[0]);
		}

		}