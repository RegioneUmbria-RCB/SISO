<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<jsp:useBean id="menuBuilder"
	class="it.webred.AMProfiler.servlet.MenuBulder" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link href="./css/newstyle.css" rel="stylesheet" type="text/css" />
<title>PROGETTO GIT</title>
<style type="text/css">
<!--
a:link {
	color: #0000CC;
}

a:visited {
	color: #0000CC;
}

a:hover {
	color: #0000CC;
}

a:active {
	color: #0000CC;
}
-->
</style>
<script type="text/javascript">
function TabView(id, current){
    if(typeof(TabView.cnt) == "undefined"){
        TabView.init();
    }
    current = (typeof(current) == "undefined") ? 0 : current;
    this.newTab(id, current);
}
TabView.init = function(){
    TabView.cnt = 0;
    TabView.arTabView = new Array();
}
TabView.switchTab = function(TabViewIdx, option){
	//mando la richiesta di cambiamento ente tramite ajax
	var TabIdx = option.selectedIndex;
	var value = option.options[TabIdx].value;
	var url = window.location.href;
	var nohttp = url.split('//')[1];
	var hostPort = nohttp.split('/')[0];
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'http://'+hostPort+'/AMProfiler/SceltaEnte?provenienza=menu&enteScelto=' + value, true);
	xhr.send(null);
    TabView.arTabView[TabViewIdx].TabView.switchTab(TabIdx);
}
TabView.prototype.newTab = function(id, current){
    var TabViewElem, idx = 0, el = '', elTabs = '', elPages = '';
    TabViewElem = document.getElementById(id);
    TabView.arTabView[TabView.cnt] = TabViewElem;
    this.TabElem = TabViewElem;
    this.TabElem.TabView = this;
    this.tabCnt = 0;
    this.arTab = new Array();
    // Loop throught the elements till the object with
    // classname 'Tabs' is obtained
    elTabs = TabViewElem.firstChild;
    while(elTabs.className != "Tabs" )elTabs = elTabs.nextSibling;
    el = elTabs.firstChild;
    do{
        if(el.tagName == "A"){
            el.href = "javascript:TabView.switchTab(" + TabView.cnt + "," + idx + ");";
            this.arTab[idx] = new Array(el, 0);
            this.tabCnt = idx++;
        }
    }while (el = el.nextSibling);

    // Loop throught the elements till the object with
    // classname 'Pages' is obtained
    elPages = TabViewElem.firstChild;
    while (elPages.className != "Pages")elPages = elPages.nextSibling;
    el = elPages.firstChild;
    idx = 0;
    do{
        if(el.className == "Page"){
            this.arTab[idx][1] = el;
            idx++;
        }
    }while (el = el.nextSibling);
    this.switchTab(current);
    // Update TabView Count
    TabView.cnt++;
}
TabView.prototype.switchTab = function(TabIdx){
    var Tab;
    if(this.TabIdx == TabIdx)return false;
    for(idx in this.arTab){
        Tab = this.arTab[idx];
        if(idx == TabIdx){
            Tab[0].className = "ActiveTab";
            Tab[1].style.display = "block";
            Tab[0].blur();
        }else{
            Tab[0].className = "InactiveTab";
            Tab[1].style.display = "none";
        }
    }
    this.TabIdx = TabIdx;

}
function init(){
    t1 = new TabView('TabView1');

}
</script>
<style type="text/css">
body,div,table {
	font: normal 11px Verdana, Arial, sans-serif;
}

.TabView {
	margin:auto;
	width: 80%;
}

.TabView .Tabs {
	height: 26px;
	display: block;
	background: #EAF0F8;
}

.TabView .Tabs a {
	/*display: block;
	float: left;
	height: 25px;
	line-height: 25px;
	color: #333;
	text-align: center;
	text-decoration: none;
	font-weight: bold;
	border: 1px #666 dashed;
	margin: 0px 0px;*/
}

.TabView .Tabs a.ActiveTab {
	/*background-image: url(img/pageBg_dark.jpg);
	display: block;
	float: left;
	height: 25px;
	line-height: 25px;
	color: #333;
	text-align: center;
	text-decoration: none;
	font-weight: bold;
	border: 1px #666 solid;
	margin: 0px 0px;*/
}

.TabView .Tabs a.InactiveTab {
	
}

.TabView .Pages {
	width: 100%;
}

.TabView .Pages .Page {
	background-color:#F3F2F2;
}
</style>

</head>
<body>
<div id="clearheader"></div>
<!-- parametri di configurazione -->
<c:if test="${gestPerm eq 'true'}">
<br><br>
<CENTER>
	<table class="TabView" style="text-align: center; border: 1px solid #C0C0C0; background-color: white;">
		<tr><td style="padding: 0px;">
		<a href="ParametriConf"><img style="border: 0;" src="img/parametri.jpg"/></a>
		</td></tr>
	</table>
</CENTER>
</c:if>
<br>

<c:if test="${myMenu != null}">

	<CENTER><div style="margin: auto;"><c:out value="${myMenu}"
		escapeXml="false" /></div></CENTER>
</c:if>
<c:if test="${myMenu == null}">
	<center>
	<div class="divTableContainer">
	<table class="griglia" cellpadding="0" cellspacing="0">
		<tr class="header">
			<c:forEach items="${intestazioneTab}" var="intest">
				<td height="35 px"><c:out value="${intest}" /></td>
			</c:forEach>
		</tr>
		<c:forEach items="${listaApplicazioni}" var="ente" varStatus="st">

			<c:choose>
				<c:when test="${ (st.count mod 2)==0}">
					<tr class="alternateRow">
				</c:when>
				<c:otherwise>
					<tr>
				</c:otherwise>
			</c:choose>

			<c:forEach items="${ente.value}" var="appli" varStatus="status">
				<td>
				<c:if test="${status.count == 1 }">
					<c:out value="${ente.key}" />
				</c:if> 
				<c:if test="${status.count != 1 }">
					<c:forEach items="${appli.value}" var="ap">
						<P><c:if test="${ap.accessoAutorizzato }">
							<c:if test="${ap.url != null}">
								<a href='<c:out value="${ap.url}" />'><c:out
									value="${ap.name}" /></a>
							</c:if>
							<c:if test="${ap.url == null}">
								<a href="javascript:void(0);"
									onclick="alert('URL non impostata! Contattare l\'amministratore');">
								<c:out value="${ap.name}" /></a>
							</c:if>
						</c:if> <c:if test="${!ap.accessoAutorizzato }">
							<c:out value="${ap.name}" />
						</c:if> <c:if test="${gestPerm }">
							<a
								href='CaricaPermessi?application=<c:out value="${ap.name}" />&appType=<c:out value="${ap.tipo_app}" />'>
							<img style="border: none;" src="img/keys.gif"></img> </a>
						</c:if></p>
					</c:forEach>
				</c:if></td>
			</c:forEach>
			</tr>
		</c:forEach>

	</table>


	</div>
	</center>
</c:if>
<div id="header" style="text-align: center;">
	<img src="img/git.jpg" width="165" height="43" style="padding: 5px 10px 5px 0px; vertical-align: middle;"/>
   	<font size='5' color="#4A75B5" style="font-weight: bold; vertical-align: middle;">Menù</font>
</div>
<br><br>

 <!--  <div id="clearfooter"></div>-->
<div id="footer"><%@include file="footer.jsp"%>
</div> 

<%@include file="user.jsp"%>
</body>
</html>
