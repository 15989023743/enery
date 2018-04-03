<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" language="javascript"> 
<!-- 
function TuneHeight(fm_name,fm_id){
    var frm=document.getElementById(fm_id); 
    var subWeb=document.frames?document.frames[fm_name].document:frm.contentDocument; 
    if(frm != null && subWeb != null){ 
        //frm.style.height = subWeb.documentElement.scrollHeight+"px"; 
        //frm.style.width = subWeb.documentElement.scrollWidth+"px"; 
    }
}
//--> 
</script>
<div>
<iframe load="TuneHeight('result','result')" id="result"  name="result"  style="width: 100%;height:300px;" marginwidth="0" marginheight="0" frameborder="0"  scrolling="no" src="${ctx}/center/common/wfms/workflow/traceProcess?piid=${piid}"></iframe>
<div>
</form>