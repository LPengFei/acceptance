//$(function() {
//
//	$(".ul-style-two:last li:first")
//			.append(
//					"<button class='remove'><span class='glyphicon glyphicon-remove'></span></button>");
//	$(".remove")
//			.click(
//					function() {
//						// 删除remove按钮所在的整个列表
//						$(".remove").parent().parent().remove();
//						// 获取ul的第一个li的文本，看里面是否含有"进出线"字符串，若含有，则添加remove按钮
//						var html = $(".ul-style-two:last").text();
//						if (html != null && html.indexOf("进出线") > 0) {
//							$(".ul-style-two:last li:first")
//									.append(
//											"<button class='remove'><span class='glyphicon glyphicon-remove'></span></button>");
//						}
//						return false;
//					});
//
//})

$(".panel-heading").css("background-color", "#F1F9F8 !important");

function remove(){
	// 删除remove按钮所在的整个列表
    //$(".remove").parent().parent().remove();
	$(".remove").parents("ul").remove();
	// 获取ul的第一个li的文本，看里面是否含有"进出线"字符串，若含有，则添加remove按钮
	var html = $(".ul-style-two:last").text();
	if (html != null && html.indexOf("进出线") > 0) {
		$(".ul-style-two:last li:first")
				.append(
						"<button class='remove' onclick='javascript:remove();'><span class='glyphicon glyphicon-remove'></span></button>");
	}
	return false;
}

function add() {
	$(".ul-style-add").remove();
	$(".panel-body:last")
			.append(
					"<ul class='ul-style-two list-unstyled'><li><input type='text' class='form-control' id='name' placeholder='进出线名称'> <li><input type='text' class='form-control' id='name' placeholder='本期规模'></li> <li><input type='text' class='form-control' id='name' placeholder='最终规模'></li></ul>");
	var hasButton = $(".add").length;
	if (hasButton == 0) {
		$(".panel-body:last")
				.append(
						"<ul class='ul-style-add list-unstyled'><li></li><li></li><li><button type='submit' class='add' onclick='javascript:add();''>添加进出线</button></li></ul>");

	}

	return false;
}
