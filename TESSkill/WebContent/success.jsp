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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body>
	<table border="1" width="50%">
		<tr>
			<td width="100%">
			
			<s:tabbedPanel id="adminPane">
					<s:div id="TechnologyTab" label="Technology" theme="ajax" labelposition="top">
        Technologies<br />
						<s:form action="Login">
							<s:textfield name="userName" label="User Name" />
							<s:password name="password" label="Password" />
							<s:submit value="Login" />
						</s:form>
					</s:div>
					<s:div id="two" label="two" theme="ajax" labelposition="top">
        This is the second pane<br />
						<s:form action="Login">
							<s:textfield name="userName" label="User Name" />
							<s:password name="password" label="Password" />
							<s:submit value="Login" />
						</s:form>
					</s:div>
					  <s:div id="three" label="Tab 3" theme="ajax">
  This is the third panel.<br>
  Java Tutorial<br>
  PHP Tutorial<br>
  Linux Tutorial
  </s:div>

  <s:div id="four" label="Tab 4" theme="ajax">
  This is the forth panel.
  </s:div>

 </s:tabbedPanel>

 </td>
  </tr>
  </table>
  </body>
</html>
