<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css" />
<title>Result Page</title>
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
<h1><s:text name="label.certification.final.result" /></h1>
<table>
	<tr>
		<td align="left" style="font: bold; color: red"><s:fielderror />
		<s:actionerror /> <s:actionmessage /></td>
	</tr>
</table>
<s:form>
	<table align="center" class="resultDisplay">		
		<tr>
			<td align="left" class="tdLabel"><s:text name="Final Result" /></td>
			<td align="left" class="tdLabel" colspan="4"><s:property
				value="status" />
		</tr>
		<tr>
			<td align="left" class="tdLabel"><s:text name="Percentage" /></td>
			<td align="left" class="tdLabel" colspan="4"><s:property
				value="percentile" />%
		</tr>
		
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center" colspan="2" class="tdBold"><s:text
				name="Objective Section" /></td>
		</tr>
		<tr>
			<td align="left"><s:text name="Total Objective Questions" /></td>
			<td align="left" colspan="4"><s:property
				value="totalObjQuestions" />
		</tr>
		<tr>
			<td align="left"><s:text name="Attempt Objective Question" /></td>
			<td align="left" colspan="4"><s:property
				value="attemptObjquestions" />
		</tr>
		<tr>
			<td align="left"><s:text name="Correct Answers " /></td>
			<td align="left" colspan="4"><s:property value="correctAnswers" />
		</tr>
		<tr>
			<td align="left"><s:text name="Wrong Answers" /></td>
			<td align="left" colspan="4"><s:property value="wrongAnswers" />
		</tr>
		<tr>
			<td align="center" colspan="2" class="tdBold"><s:text
				name="Descriptive Section" /></td>
		</tr>
		<tr>
			<td align="left"><s:text name="Total Descriptive Qeustions" /></td>
			<td align="left" colspan="4"><s:property value="totalQuestions" />
		</tr>
		<tr>
			<td align="left"><s:text name="Attempt Descriptive Question" /></td>
			<td align="left" colspan="4"><s:property
				value="attemptDescQuestions" />
		</tr>
		<tr>
			<td align="left"><s:text name="Questions Pending for Review" /></td>
			<td align="left" colspan="4"><s:property
				value="%{pendingForreview}" />
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
		<!--  	<td><s:submit action = "resultBack" value="Back to Home" cssClass="butStnd"></s:submit> </td> -->
			<td><s:submit action="Home" key="Home" cssClass="butStnd" /></td>			
		</tr>
	</table>
</s:form></center>
</body>
</html>