<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css"/>
    <title><s:text name="application.title"/></title>
</head>
<body>
<div class="titleDiv"><s:text name="application.title"/></div>	
<h1><s:text name="label.technology"/></h1>		   
	<table width=600 align=center>
	    <tr> <s:url id="insert" action="setUpForInsertOrUpdate"/>
	         <td><s:a href="%{insert}">Click Here to Add New Technology</s:a></td>
	     </tr>
	</table><br/>
<table align=center class="borderAll">
    <tr>
        <th><s:text name="label.technology.name"/></th>
        <th><s:text name="label.technology.acronym"/></th>	 
        <th>&nbsp;</th>
    </tr>
    <s:iterator value="technologies" status="status">
        <tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
            <td class="nowrap"><s:property value="name"/></td>
            <td class="nowrap"><s:property value="acronym"/></td>
            <td class="nowrap">
                	<s:url id="update" action="setUpForInsertOrUpdate">
		       		   <s:param name="technology.acronym" value="acronym"/>
		       		</s:url> 
                <s:a href="%{update}">Edit</s:a>
                &nbsp;&nbsp;&nbsp;
                <s:url id="delete" action="delete">
		       		   <s:param name="technology.acronym" value="acronym"/>
		       		</s:url>
                <s:a href="%{delete}">Delete</s:a>
            </td>
        </tr>  		
	 </s:iterator>
    </table>
</body>
</html>