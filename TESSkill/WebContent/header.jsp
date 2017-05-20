<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>

<head>
    <title>Error Page</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <LINK REL="SHORTCUT ICON" HREF="/TESSkill/images/favicon.ico" type="image/x-icon">
    <SCRIPT TYPE="text/javascript">

//Disable select-text script (IE4+, NS6+)
//visit http://www.rainbow.arch.scriptmania.com/scripts/
///////////////////////////////////
function disableselect(e){
return false;
}
function reEnable(){
return true;
}
//if IE4+
document.onselectstart=new Function ("return false");
//if NS6
document.body.style.MozUserSelect = "none";
document.body.style.cursor = "default";

//Disable right click script
//visit http://www.rainbow.arch.scriptmania.com/scripts/
var message="Sorry, right-click has been disabled";
///////////////////////////////////
function clickIE() {if (document.all) {(message);return false;}}
function clickNS(e) {if
(document.layers||(document.getElementById&&!document.all)) {
if (e.which==2||e.which==3) {(message);return false;}}}
if (document.layers)
{document.captureEvents(Event.MOUSEDOWN);document.onmousedown=clickNS;}
else{document.onmouseup=clickNS;document.oncontextmenu=clickIE;}
document.oncontextmenu=new Function("return false");
</SCRIPT>

</head>
<body>
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
</body>