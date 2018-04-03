function changeUnit(obj) {
	var text = obj.options[obj.selectedIndex].text;
	jQuery(".goodsUnit").text(text);
}

/**
 * 新增一行图片上传
 */
function addRealFigure() {
	var id = parseInt(jQuery("#TheRealFigureIdStart").val()) + 1;
	var max = parseInt(jQuery("#TheRealFigureIdMax").val());
	var resmax = parseInt(jQuery("#TheRealFigureIdRESMax").val());
	if (id >= (max - 1)) {
		alert("最多能上传" + (resmax) + "张实物图！");
		return;
	}
	var content = jQuery("#realFigure");
	var rfTr = jQuery("<tr></tr>");
	content.append(rfTr);
	rfTr.attr("id", "TheRealFigureTr_" + id);
	rfTr.css("border-top", "1px solid #83accf");
	var rfTd1 = jQuery("<td></td>");
	var rfTd2 = jQuery("<td></td>");
	var fileInput = jQuery('<input type="file" accept="gif|jpg|jpeg|png" class="SmallInput"  style="width:230px"/>');

	var removeInput = jQuery("<input type='button' value='删除' />");

	var img = jQuery("<img/>");
	img.attr("src", ctx+"/resources/images/zwtp.png");
	img.attr("id", "TheRealFigureImg_" + id);
	img.attr("width", "100");
	img.attr("height", "100");
	img.css("border", "0");

	fileInput.attr("name", "realFigure_" + id);
	fileInput.attr("id", "realFigure_" + id);
	rfTd1.addClass("TableData");
	rfTd1.append(img);
	rfTd1.css("background-color", "white");
	rfTd2.addClass("TableData");
	rfTd2.append(fileInput);
	rfTd2.css("background-color", "white");
	rfTd2.append(removeInput);
	removeInput.click(function() {
		removeRealFigure(id);
	});
	rfTr.append(rfTd1);
	rfTr.append(rfTd2);
	jQuery("#TheRealFigureIdStart").val(id);

	tableTrStyleChange();

	fileInput.change(function() {
		setImagePreview(this);
	});
}

/**
 * 删除一行图片上传
 */
function removeRealFigure(rid) {
	if (!confirm("您确定要删除该图片？")) {
		return;
	}
	jQuery("#TheRealFigureTr_" + rid).remove();
	var max = parseInt(jQuery("#TheRealFigureIdMax").val());
	jQuery("#TheRealFigureIdMax").val(max + 1);
	var TheRealFigureRemoveId = jQuery("#TheRealFigureRemoveId").val();
	if (TheRealFigureRemoveId) {
		TheRealFigureRemoveId = TheRealFigureRemoveId + ",";
	}
	jQuery("#TheRealFigureRemoveId").val(TheRealFigureRemoveId + rid);
}

/**
 * 图片预览
 */
function setImagePreview(docObj) {
	var id = docObj.id;
	var tid = id.split("_")[1]
	var imgObjPreview = jQuery("#TheRealFigureImg_" + tid)[0];
	if (docObj.files && docObj.files[0]) {
		// 火狐下，直接设img属性
		imgObjPreview.style.display = 'block';
		imgObjPreview.style.width = '100px';
		imgObjPreview.style.height = '100px';
		// imgObjPreview.src = docObj.files[0].getAsDataURL();

		// 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
	} else {
		// IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		var localImagId = document.getElementById("localImag");
		// 必须设置初始大小
		localImagId.style.width = "100px";
		localImagId.style.height = "100px";
		// 图片异常的捕捉，防止用户修改后缀来伪造图片
		try {
			localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters
					.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		} catch (e) {
			alert("您上传的图片格式不正确，请重新选择!");
			return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	return true;
}


function transModeControl(value) {
	if (value == "2") {
		jQuery("#amount").attr("readonly", "readonly");
		jQuery("#warehouseReceiptTR").show();
		jQuery("#warehouseReceipt").addClass("required");
		jQuery("#warehouseReceipt").removeAttr("disabled");
		
		jQuery("#deliveryPlaceTR").hide();
		jQuery("#deliveryPlace").removeClass("required");
		jQuery("#deliveryPlace").attr("disabled","disabled");
	} else if(value == "1"){
		jQuery("#amount").removeAttr("readonly");
		jQuery("#warehouseReceiptTR").hide();
		jQuery("#warehouseReceipt").removeClass("required");
		jQuery("#warehouseReceipt").attr("disabled","disabled");
		
		jQuery("#deliveryPlaceTR").show();
		jQuery("#deliveryPlace").addClass("required");
		jQuery("#deliveryPlace").removeAttr("disabled");
	}else{
		jQuery("#warehouseReceiptTR").hide();
		jQuery("#warehouseReceipt").removeClass("required");
		jQuery("#warehouseReceipt").attr("disabled","disabled");
		
		jQuery("#deliveryPlaceTR").hide();
		jQuery("#deliveryPlace").removeClass("required");
		jQuery("#deliveryPlace").attr("disabled","disabled");
	}
}





