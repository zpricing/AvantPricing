<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title><fmt:message key="pageTitle" /></title>
<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />
<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="datepicker"/>" />

<script type='text/javascript' src='<c:url value="/dwr/interface/Util.js"/>'> </script>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
<script type="text/javascript" src="<c:url value="/js/popcalendar.js" />"></script>
<script type='text/javascript'>

function addSrcToDestList() {
	destList = dwr.util.byId("peliculas_seleccionadas"); 
	srcList = dwr.util.byId("peliculas");
	pelsel = dwr.util.byId("pelsel");  
	var len = destList.length;
	var selected = pelsel.options[pelsel.selectedIndex].value;
for(var i = 0; i < srcList.length; i++) {
	if ((srcList.options[i] != null) && (srcList.options[i].selected)) {
		var found = false;
		for(var count = 0; count < len; count++) {
			if (destList.options[count] != null) {
				if (srcList.options[i].text == destList.options[count].text) {
					found = true;
					break;
	     			}
	   		}
		}
		if (found != true) {
			if(srcList.options[i].value!=selected){
			destList.options[len] = new Option(srcList.options[i].text);
			destList.options[len].value =  srcList.options[i].value;
			len++;
			}
			else{
				alert('<fmt:message key="error.movierepeated" />');
			}
		}
	}
}
}

function deleteFromDestList() {
	var destList  = dwr.util.byId("peliculas_seleccionadas"); 
	var len = destList.options.length;
	for(var i = (len-1); i >= 0; i--) {
	if ((destList.options[i] != null) && (destList.options[i].selected == true)) {
	destList.options[i] = null;
	      }
	   }
}


function selecciona(){
	var destList  = dwr.util.byId("peliculas_seleccionadas"); 
	var len = destList.options.length;
	if(len!=0){
	for(var i = (len-1); i >= 0; i--) {
	if ((destList.options[i] != null) && (destList.options[i].selected == false)) {
		destList.options[i].selected = true;
	      }
	 }
	 document.formsel.action = "seleccionarpeliculas.htm?seleccionar=si";
	 document.formsel.submit();
	}
	else{
		alert('<fmt:message key="error.movienull" />');
		return 0;
	}
	 
}

function verifica(){
	addSrcToDestList();	
	
}

function borrarDeDest(){
	pelsel = dwr.util.byId("pelsel");  
	var selected = pelsel.options[pelsel.selectedIndex].value;
	var destList  = dwr.util.byId("peliculas_seleccionadas"); 
	var len = destList.options.length;
	for(var i = (len-1); i >= 0; i--) {
	if ((destList.options[i] != null) && (destList.options[i].value == selected)) {
	destList.options[i] = null;
	      }
	  }
}


</script>
</head>
<body>

<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor"><span id="titulo"><fmt:message
	key="predict" /></span> <br>

<div id="form-area">
	<form:form method="post" commandName="seleccionarPeliculas" cssClass="formStyle" name="formsel">
	<table>
		<tr>
			<td>
			<fieldset style="width: 440px; height: 170px;"><legend>
				<fmt:message key="initialData"/>
			</legend>
			<table id="tablaForm">
				<tr>
					<td><fmt:message key="movie" /> <fmt:message key="to" /> <fmt:message
						key="predict" /></td>
					<td><form:select path="id_pelicula" cssClass="select"
						id="pelsel" onchange="borrarDeDest()">
						<c:forEach items="${peliculas}" var="p">
							<form:option value="${p.id}">
								<c:out value="${p.nombre}"></c:out>
							</form:option>
						</c:forEach>
					</form:select></td>
				</tr>
				<tr>
					<td><fmt:message key="complexs" /></td>
					<td><form:select path="id_complejos" multiple="true" id="sel"
						cssStyle="width:145px;font-family: 'Lucida Sans Unicode', 'Lucida Grande', Sans-Serif;
  	font-size: 12px;">
						<c:forEach items="${complejos}" var="c">
							<form:option value="${c.id}">
								<c:out value="${c.nombre}"></c:out>
							</form:option>
						</c:forEach>
					</form:select> <form:errors path="id_complejos" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="predict" /> <fmt:message key="since" /></td>
					<td><form:input path="fecha" cssClass="input" /> <img
						name="calendar1" title="calendar" alt="calendar"
						src="<c:url value="/images/calendar/cal.jpg"/>" width="20"
						height="20"
						onclick="javascript:popUpCalendar(this,document.getElementById('fecha'), 'dd-mm-yyyy');" />
					<form:errors path="fecha" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="days" /> <fmt:message key="to" /> <fmt:message
						key="predict" /><form:errors path="dias_a_predecir"
						cssClass="error" /></td>
					<td><form:input path="dias_a_predecir" cssClass="input" /></td>
				</tr>

			</table>
			</fieldset>
			</td>
			<td>
			<fieldset style="width: 440px; height: 170px;"><legend>
				<fmt:message key="filters"/>
			</legend>
			<table id="tablaForm">
				<tr>
					<td>
					<div><fmt:message key="categories" /></div>
					<div><form:select path="categorias" multiple="true" size="6"
						id="sel" cssStyle="width:130px;">
						<c:forEach items="${categorias}" var="c">
							<form:option value="${c.id}">
								<c:out value="${c.descripcion}"></c:out>
							</form:option>
						</c:forEach>
					</form:select></div>
					<div><form:errors path="categorias" cssClass="error" /></div>
					</td>
					<td>
					<div><fmt:message key="epochs" /></div>
					<div><form:select path="epocas" multiple="true" id="sel"
						size="6" cssStyle="width:130px;">
						<c:forEach items="${epocas}" var="e">
							<form:option value="${e.id}">
								<c:out value="${e.descripcion}"></c:out>
							</form:option>
						</c:forEach>
					</form:select></div>
					<div><form:errors path="epocas" cssClass="error" /></div>
					</td>
					<td>
					<div><fmt:message key="publics" /></div>
					<div><form:select path="publicos" multiple="true" size="6"
						id="sel" cssStyle="width:130px;">
						<c:forEach items="${publicos}" var="pub">
							<form:option value="${pub.id}">
								<c:out value="${pub.descripcion}"></c:out>
							</form:option>
						</c:forEach>
					</form:select></div>
					<div><form:errors path="publicos" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<td colspan="3" align="right"><input id="boton" type="submit"
						name="filtrar" class="submit"
						value="<fmt:message key="filtrate"/>"></td>
				</tr>
			</table>

			</fieldset>
			</td>
		</tr>
	</table>

	<fieldset style="width: 440px;"><legend>
		<fmt:message key="selectSimilarMovies"/>
	</legend>
	<table id="tablaForm" style="width: 150px;">
		<tr>
			<td><br>
			<fmt:message key="availableMovies"/><br>
			<form:select path="id_peliculas" multiple="true" size="11"
				id="peliculas"
				cssStyle="font-family: 'Lucida Sans Unicode', 'Lucida Grande', Sans-Serif;
  	font-size: 12px; width:420px;">
				<c:forEach items="${peliculas_filtradas}" var="p">
					<form:option value="${p.id}" id="${p.id}">
						<c:out value="${p.nombre}"></c:out>
					</form:option>
				</c:forEach>
			</form:select>
			<div align="center" style="margin-top: 3px;"><input id="boton"
				type="submit" class="submit"
				onclick="addSrcToDestList(); return false;"
				value="<fmt:message key="add"/> <fmt:message key="movies"/>"></div>
			<br>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="selectedMovies"/><br>
			<form:select multiple="true" path="id_peliculas_seleccionada"
				id="peliculas_seleccionadas"
				cssStyle="font-family: 'Lucida Sans Unicode', 'Lucida Grande', Sans-Serif;
  	font-size: 12px; width:420px;">
				<c:forEach items="${peliculas_seleccionadas}" var="p">
					<form:option value="${p.id}" id="${p.id}">
						<c:out value="${p.nombre}"></c:out>
					</form:option>
				</c:forEach>
			</form:select>
			<div align="center" style="margin-top: 3px;"><input id="boton"
				type="submit" class="submit"
				onclick="deleteFromDestList(); return false;"
				value="<fmt:message key="eliminate"/> <fmt:message key="movies"/>"></div>
			<div><form:errors path="id_peliculas_seleccionada"
				cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<td align="center"><br>
			<input type="submit" id="botonImportante"
				onclick="selecciona(); return false;" style="font-weight: bold"
				name="seleccionar"
				value="<fmt:message key="continue"/> <fmt:message key="prediction"/>" />
			</td>
		</tr>
	</table>
	<br>

	</fieldset>
</form:form></div>
</div>

</body>
</html>