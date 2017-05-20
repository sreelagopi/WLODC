<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css" />
<title>Enrol Page</title>
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
<h1><s:text name="label.enrol.certification" /></h1>
<table align="center">
	<tr>
		<td align="left" style="font: bold; color: red"><s:fielderror />
		<s:actionerror /> <s:actionmessage /></td>
	</tr>
</table>
<script language="JavaScript">
	function func(a) {
		var b = a.options[a.selectedIndex].value;
		document.frmDomain.action = "selectLevels.action";
		document.frmDomain.submit();
	}
</script>
<s:form name="frmDomain" id="frmDomain">
	<table width="100%">
		<tr>
			<td align="left" width="40%">
			<table align="left" class="subBorderAll">
				<tr>
					<td align="center" class="tdBold"><s:text
						name="label.enrol.technology" /></td>
					<td align="center" class="tdBold">Level</td>
					<td align="center" class="tdBold">Result</td>
				</tr>
				<!-- td align="left"><s:iterator value="technologyList" status="status">
        	 	<s:property value="name" /><br></s:iterator></td-->
				<s:iterator value="compLevelList">
					<tr>
						<td align="left"><s:property value="techName" /></td>
						<td align="left"><s:property value="techLevel" /></td>
						<td align="left"><s:url id="check" action="showResult">
							<s:param name="techName" value="%{techName}" />
							<s:param name="levelId" value="%{techLevel}" />
							<s:param name="reviewStatus" value="%{reviewStatus}" />							
						  </s:url> <s:a href="%{check}"><s:property
							value="reviewStatus" /></s:a> 
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			</td>
			<td align="left" valign="top" width="60%">
			<table align="left" class="enrolTab" style="margin-right:200px">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="tdEnrolLabel"><s:text name="label.enrol.technology" /></td>
					<td><s:select name="technologies" list="technologyList"
						listKey="name" listValue="name" label="Select a Technolog"
						headerKey="-1" headerValue="-Technology-" onchange="func(this);" /></td>
				</tr>
				<tr>
					<td class="tdEnrolLabel"><s:text name="label.enrol.level" /></td>
					<td><s:select name="levels" list="levelList" headerKey="-1"
						headerValue="---Levels---" label="Select a Level" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>				
			</table>
			<table align="left" style="margin-left:30px">
			<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right"><s:submit action="enrolCert"
						key="button.label.start" cssClass="butStnd">
						<s:param name="technologies" value="technologies"></s:param>
						<s:param name="levels" value="levels"></s:param>
					</s:submit></td>
					<td align="left"><s:submit action="reset"
						key="button.label.reset" type="reset" cssClass="butStnd" /></td>
				</tr>
			</table>			
			</td>			
		</tr>
		<!-- <tr>
			<td width="30%"></td>
			<td width="70%">
			<table align="left" width="400">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right"><s:submit action="enrolCert"
						key="button.label.start" cssClass="butStnd">
						<s:param name="technologies" value="technologies"></s:param>
						<s:param name="levels" value="levels"></s:param>
					</s:submit></td>
					<td align="left"><s:submit action="reset"
						key="button.label.reset" type="reset" cssClass="butStnd" /></td>
				</tr>
			</table>
			</td>
		</tr>  -->
	</table>
</s:form>
</body>
</html>