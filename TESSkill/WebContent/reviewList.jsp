<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css" />
<title>Review List Page</title>
  <SCRIPT type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}

	var myclose = false;  
	   
	function ConfirmClose()  
	{  
	if (event.clientY < 0)  
	{  
	event.returnValue = 'Do you want to close this window';  
	setTimeout('myclose=false',100);  
	myclose=true;  
	}  
	}  
	   
	function HandleOnClose()  
	{  
	if (myclose==true)   
	{ 
	
	document.frmDomain.action ="sessionClose.action";
    document.frmDomain.submit();	
	//you can write your specific code here to call action  
	}  
	} 
</SCRIPT>
<jsp:include page="header.jsp" />
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();"
	onbeforeunload="ConfirmClose()" onunload="HandleOnClose()">

<h1><s:text name="label.reviewer.list" /></h1>
<s:form name="frmDomain" id="frmDomain">
<table width=600 align=center>
	<tr>
	</tr>
</table>
<br />
<table align=center class="borderAll">
	<tr>
		<th><s:text name="label.exam.Id" /></th>
		<th><s:text name="label.exam.submitted.date" /></th>
		<th><s:text name="label.exam.status" /></th>
		<th><s:text name="label.exam.check" /></th>
	</tr>
	<s:iterator value="testList" status="status">
		<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			<td class="nowrap"><s:property id="resultId" value="resultId" /></td>
			<td class="nowrap"><s:property value="submittedOn" /></td>
			<td class="nowrap"><s:property id="isPass" value="isPass" /></td>
			<td class="nowrap">
			<s:if test="%{isPass=='Pending'}">
			 <s:url id="check" action="setUpForCheck">
				<s:param name="resultId" value="%{resultId}"/>
			</s:url>
			<s:a href="%{check}" >Check</s:a></s:if>
			<s:else>Done</s:else></td>
		</tr>
	</s:iterator>
</table>
<table align="center" width="400">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><s:submit action="reviewBack"	key="Back" cssClass="butStnd"/>
		</td>
		<td align="left"><s:submit action="LoginHome" key="Logout" cssClass="butStnd" /></td>
	</tr>
</table>
</s:form>
</body>
</html>