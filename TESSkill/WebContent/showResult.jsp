<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css" />
<title>Show Result Page</title>
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
				value="showResultVo.isPass" />
		</tr>
		<tr>
			<td align="left" class="tdLabel"><s:text name="Percentage" /></td>
			<td align="left" class="tdLabel" colspan="4"><s:property
				value="showResultVo.percentile" />%
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
				value="showResultVo.totalObjQuestions" />
		</tr>
		<tr>
			<td align="left"><s:text name="Attempt Objective Question" /></td>
			<td align="left" colspan="4"><s:property
				value="showResultVo.attemptObjquestions" />
		</tr>
		<tr>
			<td align="left"><s:text name="Correct Answers " /></td>
			<td align="left" colspan="4"><s:property value="showResultVo.correctAnswers" />
		</tr>
		<tr>
			<td align="left"><s:text name="Wrong Answers" /></td>
			<td align="left" colspan="4"><s:property value="showResultVo.wrongAnswers" />
		</tr>
		<tr>
			<td align="center" colspan="2" class="tdBold"><s:text
				name="Descriptive Section" /></td>
		</tr>
		<tr>
			<td align="left"><s:text name="Total Descriptive Qeustions" /></td>
			<td align="left" colspan="4"><s:property value="showResultVo.totalDescQuestions" />
		</tr>
		
		<tr>
			<td align="left"><s:text name="Attempt Descriptive Question" /></td>
			<td align="left" colspan="4"><s:property
				value="showResultVo.attemptDescQuestions" />
		</tr>
		
		<tr>
			<td align="left"><s:text name="Questions Pending for Review" /></td>
			<td align="left" colspan="4"><s:property
				value="showResultVo.reviewForPending" />
		</tr>
		
		<tr>
			<td align="left"><s:text name="Correct Answers " /></td>
			<td align="left" colspan="4"><s:property value="showResultVo.correctDescAnswers" />
		</tr>
		<tr>
			<td align="left"><s:text name="Wrong Answers" /></td>
			<td align="left" colspan="4"><s:property value="showResultVo.wrongDescAnswers" />
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
			<s:submit action = "resultBack" value="Back" cssClass="butStnd"></s:submit>
		</tr>
	</table>
</s:form></center>
<s:if test="%{wrongAnswersList.size > 0}">
<h1><s:text name="Answer to below questions were wrong" /></h1>
<table align="center" class="questionTab" >	
		<s:iterator value="wrongAnswersList" status="status">
		    <tr>
				<td class="tdLabel" align="left"><s:property value="%{#status.index + 1}" />). <s:property
					value="questionText" escape="false"/> 
				</td>
			</tr>
			<tr>
				<td align="left"><s:text name="Option.labelA" /><s:radio
					name="selectedAnswer" disabled="true"
					list="#{'A':options[0]}" ></s:radio> 
			</td> </tr>
			<tr><td align="left"><s:text
					name="Option.labelB" /><s:radio
					name="selectedAnswer" disabled="true"
					list="#{'B':options[1]}" ></s:radio>
			</td> </tr> 
			<tr><td align="left"> <s:text
					name="Option.labelC" /><s:radio
					name="selectedAnswer" disabled="true"
					list="#{'C':options[2]}" ></s:radio> 
			</td> </tr> 
			<tr><td align="left"> <s:text
					name="Option.labelD" /><s:radio
					name="selectedAnswer" disabled="true"
					list="#{'D':options[3]}" ></s:radio></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</s:iterator>
</table>
</s:if>	
</body>
<script type="text/javascript">
/* document.body.style.MozUserSelect = "none";
document.body.style.cursor = "default"; */
</script>
</html>