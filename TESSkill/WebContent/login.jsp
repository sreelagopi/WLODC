<%-- 
    Document   : index
    Created on : Feb 28, 2009, 9:45:01 AM
    Author     : Meyyappan Muthuraman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<s:head theme="ajax" debug="true" />
<link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
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
<h2>
	 <s:text name="label.Login"/>
</h2>
	<table>
		<tr><td align="left" style="font:bold;color:red"> 
	            <s:fielderror/> 	 	
                <s:actionerror/>
                <s:actionmessage/></td></tr>
     </table>	
    <s:form action="Login">
	 <table align="center" class="borderAll">
	 <tr><td>&nbsp;</td></tr>
		<tr>
			<td><s:text name="label.login.UserLabel"/></td>
			<td align="left">
					<s:textfield name="userName" label="User Name" size="23"/></td>
		</tr>
		<tr>
			<td><s:text name="label.login.PasswordLabel"/></td>			
			<td align="left">
					<s:password name="password" label="Password" size="25"/></td>
		</tr>
		
		 <tr><td ><s:text name="label.login.AccessLabel"/></td>
         	   <td align="left"><s:select name="accessLevel" list="#{'Reviewer':'Reviewer','Manager':'Manager'}" headerKey="User" headerValue="User"/></td>
         </tr>
		 <tr><td>&nbsp;</td></tr>
	 </table>
	 <table>
	 <tr><td>&nbsp;</td></tr>
	    <tr><td>
	 	  <s:submit value="Login" cssClass="butStnd"></s:submit>
	 	</td> <td><s:reset key="button.label.reset" cssClass="butStnd" onclick="this.form.reset();"/></td>
	 	</tr>
	 </table>
   </s:form>
</center>	
</body>
</html>
