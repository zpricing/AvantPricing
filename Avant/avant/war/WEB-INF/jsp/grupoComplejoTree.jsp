<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
	$(function() {
		$( "#grupos_accordion_${grupo.id}" ).accordion({
			collapsible: true,
			autoHeight: false,
			navigation: true
		});
		
		$( "#grupos_accordion_${grupo.id}" ).accordion("activate", false);
		
		$( ".grupos_accordion_container_${grupo.id} button:first" ).button({
		    icons: {
		        primary: "ui-icon-pencil"
		    }
		}).click(function() {
				$( "#dialog-edit_${grupo.id}" ).dialog( "open" );
				return false;
			}
		).next().button({
		    icons: {
		        primary: "ui-icon-trash"
		    }
		}).click(function() {
				$( "#delete-confirm_${grupo.id}" ).dialog( "open" );
				return false;
			}
		).next().button({
		    icons: {
		        primary: "ui-icon-plus"
		    }
		}).click(function() {
				$( "#dialog-subgrupo_${grupo.id}" ).dialog( "open" );
				return false;
			}
		).next().button({
		    icons: {
		        primary: "ui-icon-plus"
		    }
		}).click(function() {
				$( "#dialog-complejos_${grupo.id}" ).dialog( "open" );
				return false;
			}
		);
		
		$( "#dialog-edit_${grupo.id}" ).dialog({
			autoOpen: false,
			modal: true,
			buttons: {
				"Editar Grupo": function() {
					$( this ).dialog( "close" );
					$( "#managegruposcomplejos_edit_${grupo.id}" ).submit();
				},
				Cancelar: function() {
					$( this ).dialog( "close" );
				}
			},
		});
		
		$( "#delete-confirm_${grupo.id}" ).dialog({
			resizable: false,
			modal: true,
			autoOpen: false,
			buttons: {
				"Eliminar": function() {
					$( this ).dialog( "close" );
					window.location.href = "admin_eliminargrupocomplejo.htm?id_grupocomplejo=${grupo.id}";
				},
				Cancelar: function() {
					$( this ).dialog( "close" );
				}
			}
		});
		
		$( "#dialog-subgrupo_${grupo.id}" ).dialog({
			autoOpen: false,
			modal: true,
			buttons: {
				"Nuevo Sub-Grupo": function() {
					$( this ).dialog( "close" );
					$( "#managegruposcomplejos_new_${grupo.id}" ).submit();
				},
				Cancelar: function() {
					$( this ).dialog( "close" );
				}
			},
		});
		
		$( "#dialog-complejos_${grupo.id}" ).dialog({
			autoOpen: false,
			modal: true,
			buttons: {
				"Agregar Complejos": function() {
					$( this ).dialog( "close" );
					$( "#managegruposcomplejos_complejos_${grupo.id}" ).submit();
				},
				Cancelar: function() {
					$( this ).dialog( "close" );
				}
			},
		});
		
		$(document).ready(function(){
   			$( "#complejos_${grupo.id}" ).multiselect({
   				noneSelectedText: 'Selecciones Complejos',
      			selectedList: 2
   			});
		});
	});
</script>
<div id="delete-confirm_${grupo.id}" title="Eliminar Grupo?">
	<p style="font-size: 12px;"><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Este grupo se eliminara definitivamente y no podra ser recuperado, desea continuar?</p>
</div>
<div id="dialog-edit_${grupo.id}" title="Editar Grupo">
	<form id="managegruposcomplejos_edit_${grupo.id}" action="admin_managegruposcomplejos.htm" method="post">
	<fieldset>
		<label for="name">Nombre</label>
		<input type="text" name="descripcion" id="descripcion" class="text ui-widget-content ui-corner-all" value="${grupo.descripcion}">
		<input type="hidden" name="id" id="id" value="${grupo.id}">
		<input type="hidden" name="padre" id="padre" value="">
		<input type="hidden" name="empresa" id="empresa" value="">
		<input type="hidden" name="orden" id="orden" value="">
		<input type="hidden" name="action" id="action" value="edit">
	</fieldset>
	</form>
</div>
<div id="dialog-subgrupo_${grupo.id}" title="Nuevo Sub-Grupo">
	<form id="managegruposcomplejos_new_${grupo.id}" action="admin_managegruposcomplejos.htm" method="post">
	<fieldset>
		<label for="name">Nombre</label>
		<input type="text" name="descripcion" id="descripcion" class="text ui-widget-content ui-corner-all" value="">
		<input type="hidden" name="padre" id="padre" value="${grupo.id}">
		<input type="hidden" name="empresa" id="empresa" value="">
		<input type="hidden" name="orden" id="orden" value="">
		<input type="hidden" name="action" id="action" value="new">
	</fieldset>
	</form>
</div>
<div id="dialog-complejos_${grupo.id}" title="Agregar Complejos">
	<form id="managegruposcomplejos_complejos_${grupo.id}" action="admin_managegruposcomplejos.htm" method="post">
		<input type="hidden" name="id" id="id" value="${grupo.id}">
		<select id="complejos_${grupo.id}" name="complejos" multiple="multiple">
			<c:forEach var="complejo" items="${complejos}">
				<option value="${complejo.id}">${complejo.nombre}</option>
			</c:forEach>
		</select>
		<input type="hidden" name="action" id="action" value="complex">
	</form>
</div>
<div id="grupos_accordion_${grupo.id}">
	<h3><a href="#"><c:out value="${grupo.descripcion}"/></a></h3>
	<div class="grupos_accordion_container_${grupo.id}">
		<button>Editar</button> 
		<button>Eliminar</button> 
		<button>Nuevo Sub-Grupo</button> 
		<button>Agregar Complejos</button> 
		<c:if test="${fn:length(grupo.complejos) > 0}">
			<h4>Complejos del Grupo</h4>
			<ul>
				<c:forEach var="complejo" items="${grupo.complejos}">
					<li><a href="#"><c:out value="${complejo.nombre}"/></a></li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${fn:length(grupo.hijos) > 0}">
			<h4>Sub-Grupos</h4>
			<c:forEach var="hijo" items="${grupo.hijos}">
		    	<c:set var="grupo" value="${hijo}" scope="request"/>
				<jsp:include page="grupoComplejoTree.jsp"/>
		    </c:forEach>
		</c:if>
		<br/>
	</div>
</div>