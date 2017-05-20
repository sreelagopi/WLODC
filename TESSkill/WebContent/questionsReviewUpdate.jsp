<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css" />
<title>Question Review Page</title>
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
<center>
<h1><s:text name="label.reviewer.question.list" /></h1>
<table>
	<tr>
		<td align="left" style="font: bold; color: red"><s:fielderror />
		<s:actionerror /> <s:actionmessage /></td>
	</tr>
</table>
<s:form>
	<table align="center" class="questionTab">
	<s:hidden name="resultId" value="%{resultId}" />
		<s:iterator value="questions" status="status">
			<tr>
				<td></td>
			</tr>
			<tr>
				<td class="tdLabel" align="left" colspan="2"><ol><li>  <s:property
					value="questionText" escape="false"/> </li></ol><s:hidden
					name="disQuestionId[%{#status.index}]" value="%{questionId}" /></td>
			</tr>
			<tr>
				<td align="left"><s:text name="label.reviewer.question.answer" /></td>
				<td align="right"><s:text name="label.reviewer.question.review" /></td>
			</tr>
			<tr>
				<td align="left"><s:textarea label="Answer" name="Answer" cols="60"
					rows="5" readonly="true" value="%{selectedAnswer}" /></td>
				<td align="right" valign="top"><s:select name="review[%{#status.index}]" list="#{'Correct':'Correct','Wronge':'Wronge'}" headerKey="-1" headerValue="Review"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</s:iterator>
	</table>
	<br />
	<table>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><s:submit action="reviewQuestions" key="Submit"
							cssClass="butStnd"/>
			</td>
			<td align="right"><s:submit action="reviewListBack"	key="Back" cssClass="butStnd"/>
			</td>
		</tr>
	</table>
</s:form></center>
</body>
<script type="text/javascript">
/* document.body.style.MozUserSelect = "none";
document.body.style.cursor = "default"; */
</script>
</html>