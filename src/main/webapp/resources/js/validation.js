$(function(){
	if( $("form.validate").size() > 0 ){
		// 需要验证的要在验证成功后block
		$.validator.setDefaults({
			submitHandler: function(form) {
				$.blockUI({ message: '<p style="font-size: 16px;font-weight: bold;">正在处理，请稍候...</p>' }); 
				form.submit();
			}
		});
		// class为validate的表单都要验证
		$("form.validate").validate();
	}
		
	// 不需要验证的提交后就block
	$("form:not(.validate)").submit(function(){
		$.blockUI({
			message: '<p style="font-size: 16px;font-weight: bold;">正在处理，请稍候...</p>' 
		});
	});
		
	// 超链接执行的功能也block（在另外窗口中打开的除外）
	// :not([target]) 指定的target
	// :not([href=#]) 刷新本页，一般是还未最终完成的超链接
	// :not([href^=javascript]) 调用JavaScript函数
	// :not([onclick]) 在点击时调用JS函数。如删除时，选取消也会block。这个效果由confirmDel()完成
	// :not(a:has(img)) 超链接在一个图片上，没有指定target，在点击时也会在新窗口中打开。使用FCKeditor发表内容时可能会出现这样的情况：。
	// :not(div.content a) 在显示文章内容时出现的a，不block
	$("a:not([target]):not([href=#]):not([href^=javascript]):not([onclick]):not(a:has(img)):not(div.content a)").click(function(){
		$.blockUI({
			message: '<p style="font-size: 16px;font-weight: bold;">正在执行，请稍候...</p>' 
		});
	});
});

function formValidation(name,message){
    var objById=jQuery("#"+name);
    var obj=jQuery("input[name="+name+"]");
    var label=jQuery('<label class="error" for="'+name+'" generated="true">'+message+'</label>');
    if(objById[0]!=undefined){
        objById.after(label);
    }else{
        obj.after(label);
    }
}