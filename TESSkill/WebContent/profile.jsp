<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <link href="<s:url value="/main.css"/>" rel="stylesheet" type="text/css"/>
    <title><s:text name="application.title"/></title>
</head>
<body>
<div class="titleDiv"><s:text name="application.title"/></div>	
<h1><s:text name="label.profile"/></h1>		   
	<table width=600 align=center>
	    <tr> <s:url id="enrol" action="enrol"/>
	         <td><s:a href="%{enrol}">Click Here to Enrol for New Certification</s:a></td>
	     </tr>
	</table><br/>
<table align=center class="borderAll">
    <tr>
        <th><s:text name="label.result.exam.id"/></th>
        <th><s:text name="label.result.pass"/></th>
        <th><s:text name="label.result.percentile"/></th>
        <th><s:text name="label.result.enrolled.on"/></th>	 
        <th><s:text name="label.result.submitted.on"/></th>	 
        <th>&nbsp;</th>
    </tr>
    <s:iterator value="results" status="status">
        <tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
            <td class="nowrap"><s:property value="examId"/></td>
            <td class="nowrap"><s:property value="isPass"/></td>
            <td class="nowrap"><s:property value="percentile"/></td>
            <td class="nowrap"><s:property value="enrolledOn"/></td>
            <td class="nowrap"><s:property value="submittedOn" default="-"/></td>
            <td class="nowrap">
                	<s:url id="retake" action="retake">
		       		   <s:param name="result.resultId" value="resultId"/>
		       		</s:url> 
                <s:a href="%{retake}">Re-take</s:a>
                &nbsp;&nbsp;&nbsp;
                <s:url id="nextlevel" action="nextlevel">
		       		   <s:param name="result.resultId" value="resuId"/>
		       		</s:url>
                <s:a href="%{nextlevel}">Next Level</s:a>
            </td>
        </tr>  		
	 </s:iterator>
    </table>
</body>
</html>