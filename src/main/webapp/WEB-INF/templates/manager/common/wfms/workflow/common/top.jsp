<%@ page contentType="text/html;charset=UTF-8"%>
<form id="inputForm" action="${ctx}/center/common/wfms/workflow/endTask" method="post" class="validate">
<input type="hidden" name="tiid" value="${tiid}"/>
<input type="hidden" name="piid" value="${piid}"/>
<quick:backPageUrl/>