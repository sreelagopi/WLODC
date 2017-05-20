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
<title>Question Page</title>
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
<h1><s:text name="label.question.header" /></h1>
<table>
	<tr> <td align="left" style="font: bold; color: green"><s:if test="%{attempted > 0}">
		You have already attempted <s:property value="attempted"/> questions 
		</s:if>
	</td></tr>
	<tr>
		<td align="left" style="font: bold; color: red"><s:fielderror />
		<s:actionerror /> <s:actionmessage /></td>
	</tr>
</table>

<s:if test="%{attempted == 0 && counter <= 3}">
<h1 align="left"><s:text name="Passing question count for this assessment is "/><s:property value="passingCount"/><s:text name=" out of "/><s:property value="totalCount"/></h1>
</s:if>

<s:form name="frmDomain" id="frmDomain">
<table align="center" width="100%"><tr><td>
	<table align="center" class="questionTab" >
	 <tr> <td align="right"> Questions (<s:property value="counter"/>/<s:property value="totalQuestions"/>)</td></tr>
		<s:iterator value="questionsDisplay" status="status">
		    <tr>
				<td></td>
			</tr>			
			<tr>
				<td class="tdLabel" align="left"><s:property
					value="questionText" escape="false"/> <s:hidden
					name="disQuestionId[%{#status.index}]" value="%{questionId}" /></td>
			</tr>
			<tr>
				<td align="left"><s:text name="Option.labelA" /><s:radio
					name="answerMap[%{#status.index}].selectedAnswer"
					list="#{'A':options[0]}" ></s:radio> 
			</td> </tr>
			<tr><td align="left"><s:text
					name="Option.labelB" /><s:radio
					name="answerMap[%{#status.index}].selectedAnswer"
					list="#{'B':options[1]}" ></s:radio>
			</td> </tr> 
			<tr><td align="left"> <s:text
					name="Option.labelC" /><s:radio
					name="answerMap[%{#status.index}].selectedAnswer"
					list="#{'C':options[2]}" ></s:radio> 
			</td> </tr> 
			<tr><td align="left"> <s:text
					name="Option.labelD" /><s:radio
					name="answerMap[%{#status.index}].selectedAnswer"
					list="#{'D':options[3]}" ></s:radio></td>
			</tr>
			<tr><td align="left"> <s:text
					name="Answer.hint" />
					<s:text name="answerMap[%{#status.index}].hint" />
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</s:iterator>
	</table>
	</td></tr>
	<tr><td>
	<table align="center">
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td><s:if test="%{counter > 3}">
				<s:submit action="enrolCertPrevious" key="Previous" cssClass="butStnd">
				<s:param name="answerMap" value="answerMap"></s:param>
				<s:param name="questionsDisplay" value="questionsDisplay"></s:param>
				</s:submit>
			</s:if></td>
			<td><s:submit action="enrolCertNext" key="Next"
				cssClass="butStnd">
				<s:param name="answerMap" value="answerMap"></s:param>
				<s:param name="counter" value="counter"></s:param>
				<s:param name="remainCount" value="remainCount"></s:param>
				<s:param name="disQuestionId" value="disQuestionId"></s:param>
			</s:submit></td>
			<!--  td><s:submit action="resultCert" key="Submit"
				cssClass="butStnd">
				<s:param name="answerMap" value="answerMap">
				</s:param>
				<s:param name="questions" value="questions">
				</s:param>
				<s:param name="questionsDisplay" value="questionsDisplay"></s:param>
				<s:param name="disQuestionId" value="disQuestionId"></s:param>
			</s:submit></td-->
		</tr>
		<tr><td>&nbsp;</td></tr>
		<!--  <tr><td>Note: If you click on Next it means you have attempted these questions for this Test</td></tr> -->
	</table>
 </td></tr></table>
</s:form>
<script type="text/javascript">

</script>
</body>
</html>