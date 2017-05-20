<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
    <link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css"/>
    <title>Review Enrol Page</title>
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
<h1>
	 <s:text name="label.review.enrol"/>
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
        document.frmDomain.action ="selectReviewLevels.action";
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
        <tr><td>&nbsp;</td></tr>
    	</table>
    	</td>
    	</tr>
       <tr> <td width="30%"></td>
        <td width="70%">
    		<table align="left" width="400"> 
    		<tr><td>&nbsp;</td></tr>
    	    <tr>
    		    <td align="right"><s:submit action="reviewStart" key="button.label.review" cssClass="butStnd">
    		    <s:param name="technologies" value="technologies"></s:param>
    		    <s:param name="levels" value="levels"></s:param>
    		    </s:submit></td>
    	        <td align="left"><s:submit action="resetReview" key="button.label.reset" type="reset" cssClass="butStnd" /></td>
    		</tr>
    		</table> 
    	</td>
       </tr>		
    </table>		  		 
    </s:form>
</body>
</html>