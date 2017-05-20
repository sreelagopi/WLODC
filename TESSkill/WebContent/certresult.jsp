<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css" />
<title>Intermediate Page</title>
<SCRIPT type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
</SCRIPT>
<jsp:include page="header.jsp" />
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();"
	onunload="">
<center>
<h1><s:text name="label.certification.objectivefinished" /></h1>
<table>
	<tr>
		<td align="left" style="font: bold; color: red"><s:fielderror />
		<s:actionerror /> <s:actionmessage /></td>
	</tr>
</table>
<s:form>
	<table align="center" class="resultDisplay">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<!--  <tr>
			<td class="tdLabel"><s:text name="Final Result" /></td>
			<td class="tdLabel" colspan="4"><s:property
				value="displayResult.isPass" />
		</tr>
		<tr>
			<td class="tdLabel"><s:text name="Percentage" /></td>
			<td class="tdLabel" colspan="4"><s:property
				value="displayResult.percentile" />%
		</tr> -->
		<tr>
			<td>&nbsp;</td>
		</tr>
		<!-- <tr>
			<td align="left"><s:text name="Total Question" /></td>
			<td align="left" colspan="4"><s:property value="totalQuestions" />
		</tr>
		<tr>
			<td align="left"><s:text name="Attempt Question" /></td>
			<td align="left" colspan="4"><s:property value="totalAttempt" />
		</tr>
		<tr>
			<td align="left"><s:text name="Correct Answers " /></td>
			<td align="left" colspan="4"><s:property value="correctAnswers" />
		</tr>
		<tr>
			<td align="left"><s:text name="Wrong Answers" /></td>
			<td align="left" colspan="4"><s:property value="wrongAnswers" />
		</tr> -->
		<tr>
			<td class="tdLabel">You have completed objective section. </BR>Please click on Next Section button to continue with Subjective questions.</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	
	</table>
	<br />
	<table>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><s:submit action="NextSection" key="Next Section"
				cssClass="butStnd" /></td>		
		</tr>
	</table>
</s:form></center>
</body>
</html>