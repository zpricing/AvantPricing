<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- MENU -->	
	<script type="text/javascript" src="<c:url value="javascript.v2.0/script.js"></c:url>"></script>
	
	<div id="contenido">
		<div id="tablaheader">
			<p>&nbsp;</p>		
			<div id="sidebar">
				<div align="center"><a href="#"><img src="<c:url value="images.v2.0/bot-paneladmin.png"/>" alt="Panel de Configuración" width="166" height="42" border="0" /></a>
				</div>
				
				<c:forEach items="${map}" var="map_parent">
					<div id="menuwrap">
						<ul class="menu">
							<li style="list-style:none;"><a class="menucollapser" href="#">
   								<img src="<c:url value="images.v2.0/bot-collapse.png"/>" alt="collapse" width="12" height="13" border="0" /> <fmt:message key="${map_parent.header}"/></a>
  								<ul>
  									<c:forEach items="${map_parent.lista_elementos}" var="menuElement">
  										<li class="itemmenu"><a href="<c:url value="${menuElement.link}"/>"><fmt:message key="${menuElement.title}"/></a></li>
  									</c:forEach>  									
								</ul>
							</li>
						</ul>
						<img style="margin-left:5px;" src="<c:url value="images.v2.0/menuwrapbottom.png"/>" width="160" height="13" />
					</div>
				</c:forEach>				
			</div>
		</div>
	</div>
<!-- FIN MENU -->	


