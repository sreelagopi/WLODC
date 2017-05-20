<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%
	String[] answer;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css" />
<title>Descriptvie Question Page</title>
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
	event.returnValue = 'Any message you want';  
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
<h1><s:text name="label.question.header" /></h1>
<table>
	<tr>
		<td align="left" style="font: bold; color: green"><s:if
			test="%{attempted > 0}">
		You have already attempted <s:property value="attempted" /> Subjective questions 
		</s:if></td>
	</tr>
	<tr>
		<td align="left" style="font: bold; color: red"><s:fielderror />
		<s:actionerror /> <s:actionmessage /></td>
	</tr>
</table>

<s:form name="frmDomain" id="frmDomain">
	<table align="left" width="75%">
<s:if test="%{counter > 0}">
		<tr>
			<td nowrap="nowrap" width="75%">
			<table align="left" class="questionTab">
				<tr>
					<td align="right">Questions (<s:property value="counter" />/<s:property
						value="totalQuestions" />)</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<s:iterator value="questionsDisplay" status="status">
					<tr>
						<td class="tdLabel" align="left"><s:property
							value="questionText" escape="false"/></td>
					</tr>
					<tr>
						<td align="left"><s:text name="Question.answer" /><s:textarea
							rows="10" cols="40" name="questions[%{counter-1}].selectedAnswer"></s:textarea>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
</s:if>
		<tr>
			<td>
			<table align="center">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><s:if test="%{counter>=2}">
						<s:submit action="desPrevious" key="Previous" cssClass="butStnd">
							<s:param name="questions" value="questions"></s:param>
						</s:submit>	
					</s:if></td>

					<td><s:if test="%{counter < totalQuestions}">
						<s:submit action="desNext" key="Next" cssClass="butStnd">
							<s:param name="questions" value="questions"></s:param>
						</s:submit>
					</s:if></td>

					<td><s:if test="%{counter == totalQuestions}">
						<s:submit action="totalResultCert" key="Submit & View Result" cssClass="butStnd">
						</s:submit>
					</s:if></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>

</body>

<script type="text/javascript">
/* document.body.style.MozUserSelect = "none";
document.body.style.cursor = "default"; */
</script>

</html>