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

	//页面加载时获取所有题目
	getQuestion();

	//增加操作
	$("#addBtn").click(function() {
		var question = $("#question").val().trim(),
			answer1 = $("#answer1").val().trim(),
			answer2 = $("#answer2").val().trim();
		if(question == "" && answer1 == "" && answer2 == "") {
			alert("请填写完！");
			return;
		}
		//封装参数
		var question = {
			"question": question,
			"answer1": answer1,
			"answer2": answer2
		}
		//提交数据
		addQuestion(question);
	});

	//删除操作
	$("#deletBtn").click(function() {
		var $checks = $(".checkOne");
		var question_ids = "";
		//获取哪些被选中
		for(var i = 0; i < $checks.length; i++) {
			if($checks[i].checked) {
				question_ids += $($checks[i]).attr("question_id") + ",";
			}
		}
		//去掉最后一个,
		var new_question_ids = question_ids.substring(0, question_ids.length - 1)
		//请求删除
		deletQuestion(new_question_ids);
	});
})

function getQuestion() {
	//先清空
	$("#tbody").children().remove();

	$.ajax({
		type: "get",
		url: "question/getAll",
		data: {},
		dataType: "json",
		success: function(data) {
			//console.log(data);
			var result = data.data;
			for(var i = 1; i <= result.length; i++) {
				$('<tr><td><input type="checkbox" class="checkOne" question_id="' + result[i - 1].id + '" /></td><td>' + i + '</td><td>' + result[i - 1].content + '</td><td>' +
					result[i - 1].answers.answer1 + '</td><td>' + result[i - 1].answers.answer2 + '</td></tr>').appendTo($("#tbody"));
			}
		},
		error: function(XHR) {
			alert(XHR.status);
		}
	});
}

function addQuestion(question) {
	$.ajax({
		type: "post",
		url: "question/addOne",
		data: question,
		dataType: "json",
		success: function(data) {
			//console.log(data);
			if(data.status) {
				alert("增加成功！");
				//关闭模态框
				$("#addModal").modal('hide');
				getQuestion();
			} else {
				alert(data.message);
			}
		},
		error: function(XHR) {
			alert(XHR.status);
		}
	});
}

function deletQuestion(question_ids) {
	$.ajax({
		type: "post",
		url: "question/delete",
		data: {
			"question_ids": question_ids
		},
		dataType: "json",
		success: function(data) {
			//console.log(data);
			if(data.status) {
				alert("删除成功！");
				getQuestion();
			} else {
				alert(data.message);
			}
		},
		error: function(XHR) {
			alert(XHR.status);
		}
	});
}