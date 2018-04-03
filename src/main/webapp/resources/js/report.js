//运行报表
function runReport(outType) {
	$("input[name=format]").val(outType);
	$("#ListForm").submit();
	$.unblockUI();
}
//查询报表(默认输出html)
function searchReport() {
	runReport("html");
}

//查询报表(导出报表)
function expReport() {
	var outformat = $("#selectformatid").val();
	runReport(outformat);
}
