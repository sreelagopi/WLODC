<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
    <link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css"/>
 <table width="100%">
<tr>
<td width="10%"> <img src="/TESSkill/images/TES Skill.jpg" > </td> 
<td width="90%">
<%-- <table class="titleDiv" width="90%"><tr><td ><s:text name="application.title"/></td><td class="logoutDiv">
<s:url id="logout" action="LoginHome"></s:url><s:a href="%{logout}">Logout</s:a></td></tr></table>
 --%><div class="titleDiv"><s:text name="application.title"/>
 <s:if test="%{userName != null }">
<font class="logoutDiv" ><s:url id="logout" action="LoginHome"></s:url><s:a href="%{logout}">Logout</s:a> </font>
</s:if>
</div>
</td>
</tr>
 <s:if test="%{userName != null }">
<tr>
<td style="text-align:right" colspan="2"><s:text name="You are logged in with " /><b><s:property value="userName"/></b></td>
</tr>
</s:if>
</table>
    
    <title>Manager Enrol Page</title>
        <SCRIPT type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
		
</SCRIPT>
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();"
	onunload="">
<h1>
	 <s:text name="label.manager.report"/>
</h1>
	 <table align="center">
		<tr><td align="left" style="font:bold;color:red"> 
	            <s:fielderror/> 	 	
                <s:actionerror/>
                <s:actionmessage/></td></tr>
     </table>		
     <script language="JavaScript">     
  		  function func(a) {      
        var b = a.options[a.selectedIndex].value;
        document.frmDomain.action ="selectManageLevels.action";
        document.frmDomain.submit();                 
    }   
	</script> 	
    <s:form name="frmDomain" id="frmDomain">
     <table width="100%">
     <tr><td align="left" width="30%">
   		
    	 </td>
    	 <td align="left" width="70%">
     	<table align="left" class="enrolTab">
     	<tr><td>&nbsp;</td></tr>
         <tr><td class="tdEnrolLabel" ><s:text name="label.enrol.technology" /></td>
         	   <td><s:select name="technologies" list="technologyList" listKey="name" listValue="name" 
				label="Select a Technolog" headerKey="-1" headerValue="-Technology-" onchange="func(this);"/></td>
         </tr>
        <tr>
            <td class="tdEnrolLabel"><s:text name="label.enrol.level"/></td>
            <td><s:select name="levels" list="levelList" headerKey="-1" headerValue="---Levels---" 
				label="Select a Level"/></td>
        </tr>
        <tr>
        	<td class="tdEnrolLabel"><s:text name="label.enrol.dasid"/></td>
        	<td><s:textfield key="das_id"/></td>
        </tr>
        <tr><td>&nbsp;</td></tr>
    	</table>
    	</td>
    	</tr>
       <tr> <td width="30%"></td>
        <td width="70%">
    		<table align="left" width="400"> 
    		<tr><td>&nbsp;</td></tr>
    	    <tr>
    		    <td align="right"><s:submit action="manageReport" key="button.label.viewReport" cssClass="butStnd">
    		    <s:param name="technologies" value="technologies"></s:param>
    		    <s:param name="levels" value="levels"></s:param>
    		    <s:param name="das_id" value="das_id"></s:param>
    		    </s:submit></td>
    	        <td align="left"><s:submit action="resetManager" key="button.label.reset" type="reset" cssClass="butStnd" /></td>
    		</tr>
    		</table> 
    	</td>
       </tr>
       <s:if test="%{!getManagerReport().isEmpty()}">
        <tr><td width="30%"></td>
        <td width="70%">
        <table align="left" class="enrolTab">
        	<tr>
        		<td>Das ID</td>
        		<td>Name</td>
        		<s:if test="showLevel">
        		<td>Last Level</td>
        		</s:if>
        		<td>Submit Date</td>
        	</tr>
        	<s:iterator value="managerReport" id="report">
        	<tr>
        		<td><s:property value="#report.das_id"/></td>
        		<td><s:property value="#report.name"/></td>
        		<s:if test="showLevel">
        		<td><s:property value="#report.last_level"/></td>
        		</s:if>
        		<td><s:property value="#report.submitted_date"/></td>
        	</tr>	
        	</s:iterator>
        </table>
        </td>
       </tr>
       </s:if>		
    </table>		  		 
    </s:form>
</body>
</html>