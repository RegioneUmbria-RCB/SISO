<CENTER	>
<div class="divTableContainer">
	<c:if test="${!empty lstFeedMsg}">
	<table style="background-color:transparent;border-collapse:collapse;border:0; width: 100%; ">
		<tr style="border-collapse:collapse;border:0; ">
			<td colspan="4" style="font-size:12pt;border-collapse: collapse;border: 0;" align="middle"><b>Funzionalità rilasciate</b></td>
		</tr>
		<tr>
			<td width="94px"  style="font-size:10pt;"><b>Versione</b></td>
			<td width="201px" style="font-size:10pt;"><b>Titolo</b></td>
			<td width="221px" style="font-size:10pt;"><b>Descrizione</b></td>
			<td width="97px"  style="font-size:10pt;"><b>Allegato</b></td>
		</tr>
	</table>
	
	<marquee width="100%"  behavior="scroll" height="40" direction="up" scrolldelay="500" onmouseover="this.stop();" onmouseout="this.start();" >
		<table style="background-color:transparent;border-collapse:collapse;border:0; width: 100%; ">
			<c:forEach items="${lstFeedMsg}" var="ril" varStatus="status">
				<tr style="border-collapse: collapse;border: 0;">
					<td width="100px" style="padding:1px;font-size:10pt;font-style:normal;padding-bottom:1px; border-collapse: collapse;border: 0;">
 						<a href="<%=request.getContextPath()%>/jsp/feedMessageViewDetail.jsp" target="_blank" style="text-decoration: none;"> 
							<c:out value="${ril.version}" />
 						</a> 
					</td>
					<td width="200px" style="padding:1px;font-size:10pt;font-style:normal;padding-bottom:1px;border-collapse: collapse;border: 0;"><c:out value="${ril.title}" /></td>
					<td width="220px" style="padding:1px;font-size:10pt;font-style:normal;padding-bottom:1px;border-collapse: collapse;border: 0;">
						<a href="<%=request.getContextPath()%>/jsp/feedMessageViewDetail.jsp" target="_blank" style="text-decoration: none;">
							<c:out value="${ril.shortDescription}" />
						</a>
					</td>
					<td width="100px" style="padding:1px;font-size:10pt;font-style:normal;padding-bottom:1px; border-collapse: collapse;border: 0;">
					<c:if test="${(!empty ril.link) && (ril.link ne '') && (ril.link ne 'link') }">
						<a href="<c:out value="${ril.link}" />" target="_blank">Allegato</a>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</marquee>
	</c:if>
	<c:if test="${!empty ErrMsg}">
		<h3><c:out value="${ErrMsg}" /></h3>
	</c:if>
</div>
</CENTER>
