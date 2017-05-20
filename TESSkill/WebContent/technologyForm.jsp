<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<center>
<div class="titleDiv"><s:text name="application.title"/></div>
<h1>
   <s:if test="technology==null || technology.acronym == null">
	 <s:text name="label.technology.add"/>
   </s:if>
   <s:else>
	 <s:text name="label.technology.edit"/>
   </s:else>
</h1>
	<table width=600 align=center>
	    <tr><td><a href="getAllTechnologies.action">Click Here to View Technologies</a></td>
	     </tr>
	</table>	 
	 <table>
		<tr><td align="left" style="font:bold;color:red"> 
	            <s:fielderror/> 	 	
                <s:actionerror/>
                <s:actionmessage/></td></tr>
     </table>		 	
    <s:form>
     <table align="center" class="borderAll">
     	 
         <tr><td class="tdLabel"><s:text name="label.technology.name"/></td>
         	        <td><s:textfield name="technology.name" size="30"/></td>
         </tr>
        <tr>
            <td class="tdLabel"><s:text name="label.technology.acronym"/></td>
                            <td><s:textfield name="technology.acronym" size="30"/></td>
        </tr>
       
        
    </table>
    		 <br/>
    <table> 
    	     <tr>
    		    <td><s:submit action="insertOrUpdate" key="button.label.submit" cssClass="butStnd"/></td>
    	        <td><s:reset key="button.label.cancel" cssClass="butStnd"/></td>
    		 <tr>
    </table> 		  		 
    	</s:form>
</center>		
</body>
</html>