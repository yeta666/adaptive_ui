$(function() {
	//页面加载时获取所有
	getAll();
})

function getAll() {
	//先清空
	$("#tbody").children().remove();

	$.ajax({
		type: "get",
		url: "trainArrayAttributes/getAll",
		data: {},
		dataType: "json",
		success: function(data) {
			console.log(data);
			var result = data.data;
            var html = '';
			for(var i = 1; i <= result.length; i++) {
                var attributes_array = result[i - 1].attributes.split(",");
                html += '<tr><td rowspan="' + attributes_array.length + '" style="vertical-align: middle">' + i + '</td><td>' + 1 + '</td><td>' + attributes_array[0] + '</td><td rowspan="' + attributes_array.length + '" style="vertical-align: middle">' + result[i - 1].modelType + '</td></tr>';
				for(var j = 2; j <= attributes_array.length; j++){
                    html += '<tr><td>' + j + '</td><td>' + attributes_array[j - 1] + '</td></tr>';
				}
			}
			$("#tbody").html(html);
		},
		error: function(XHR) {
			alert(XHR.status);
		}
	});
}