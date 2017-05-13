$(function() {

	//全选/反选功能
	$(".checkAll").click(function() {
		var $checks = $(".checkOne");
		for(var i = 0; i < $checks.length; i++) {
			if(this.checked) {
				$checks[i].checked = true;
			} else {
				$checks[i].checked = false;
			}
		}
	});

	//页面加载时获取所有
	getAll();

	//删除操作
	$("#deletBtn").click(function() {
		var $checks = $(".checkOne");
		var ids = "";
		//获取哪些被选中
		for(var i = 0; i < $checks.length; i++) {
			if($checks[i].checked) {
				ids += $($checks[i]).attr("idd") + ",";
			}
		}
		//去掉最后一个,
		var new_ids = ids.substring(0, ids.length - 1)
		//请求删除
        deleteOneOrMany(new_ids);
	});
})

function getAll() {
	//先清空
	$("#tbody").children().remove();

	$.ajax({
		type: "get",
		url: "userAnswers/getAll",
		data: {},
		dataType: "json",
		success: function(data) {
			console.log(data);
			var result = data.data;
			for(var i = 1; i <= result.length; i++) {
				$('<tr><td><input type="checkbox" class="checkOne" idd="' + result[i - 1].userId + '" /></td><td>' + i + '</td><td>' + result[i - 1].userName + '</td><td>' +
					result[i - 1].answers + '</td><td>' + result[i - 1].userType + '</td></tr>').appendTo($("#tbody"));
			}
		},
		error: function(XHR) {
			alert(XHR.status);
		}
	});
}

function deleteOneOrMany(ids) {
	$.ajax({
		type: "post",
		url: "userAnswers/delete",
		data: {
			"userAnswers_ids": ids
		},
		dataType: "json",
		success: function(data) {
			//console.log(data);
			if(data.status) {
				alert("删除成功！");
				getAll();
			} else {
				alert(data.message);
			}
		},
		error: function(XHR) {
			alert(XHR.status);
		}
	});
}