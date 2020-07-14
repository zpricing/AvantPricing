function agregarProceso(nombreProceso, nombreAbreviado, type){
	var $filaProceso =$( '<tr>'+
						'<td>'+nombreProceso+'</td>'+
						'<td id="'+nombreAbreviado+'Estado"></td>'+
						'<td id="'+nombreAbreviado+'Etapa"></td>'+
						'<td id="'+nombreAbreviado+'UltimaEjecucion"></td>'+
						'<td id="'+nombreAbreviado+'UltimaEjecucionExitosa"></td>'+
						'<td id="'+nombreAbreviado+'TiempoE"></td>'+
						'<td>'+
							' <button type="button" class="black icon_only small img_icon" id="button_' + nombreAbreviado + 'Estado" class="ejecutar_proceso" onClick="javascript:ejecutarProceso(\''+nombreAbreviado+'\')">'+
							' <img src="assets/adminica/images/icons/small/white/bended_arrow_down.png">'+
							' </button>'+
						'</td>'+
						'<td>'+
							' <button type="button" class="yellow icon_only small img_icon" id="button_'+nombreAbreviado+'ForzarInicio" >'+
							' <img src="assets/adminica/images/icons/small/white/pacman.png">'+
							' </button>'+
						'</td>'+
						'<td>'+
							' <a id="'+nombreAbreviado+'Error" href="#containerError" class="toggle" data-error></a>'+
						'</td>'+
						'</tr>'
	);

	$filaProceso.find('#button_'+nombreAbreviado+'ForzarInicio').click({param: nombreAbreviado},forzarTermino);

	$filaProceso.appendTo('#listaDeProcesos_' + type).find('a').click({param: nombreAbreviado},mostrarErrorProceso);
}
var mapa;

function handlerRefresh(data) {
	mapa = eval("(" + data + ")");
	var info_tablas= eval("(" + data + ")");
	for(var datos in info_tablas ){
		var proceso = info_tablas[datos];
		if(proceso.Estado=="En ejecucion"){
			disableButton($("#button_"+datos+"Estado"));
		}
		else{
			enableButton($("#button_"+datos+"Estado"));
		}
		for(var key in proceso){
			if(key != "Error"){
				$("#"+datos+key).html(proceso[key]);
			}
			else{
				if(proceso.TieneError == "true"){
					$("#"+datos+key).css({"background": 'rgba(142, 214, 14, 1) url("assets/adminica/images/interface/toggle.png") 2px -15px no-repeat' });
					$("#"+datos+key).attr('data-error', proceso.Errores);
				}
				else{
					$("#"+datos+key).css({"background": 'rgba(0, 0, 0, 0.1)  url("assets/adminica/images/interface/toggle.png") 2px -15px no-repeat' });
				}
			}			
		}
	}

	rellenarTablaCola();
	erroresRefresh();

	pagina_refresh();
}

function erroresRefresh() {
	var tbo = document.getElementById("listaErrores");
 	while ( tbo.rows.length > 0 ){
    	tbo.deleteRow(0);
 	}
	for(var datos in mapa) {
		var proceso = mapa[datos];
		if(proceso.Errores && proceso.Errores.length > 2 ) {
			var $fila = $( '<tr>'+'</tr>');
			$fila.append('<td>'+datos+'</td>');
			$fila.append('<td>'+proceso.UltimaEjecucion+'</td>');
			var $td = $('<td data-error="">'+proceso.Errores.substring(0, Math.min(proceso.Errores.length, 50))+'</td>');
			$td.attr('data-error', proceso.Errores);
			var parametro = proceso.Errores;
			$fila.mouseover(function() {
								$(this).addClass('selectedRow');
							}).mouseout(function() {
								$(this).removeClass('selectedRow');
													
							}).click({param1: parametro},mostrarError).append($td);

			$fila.appendTo($('#dt1').find('#listaErrores'));
		}
	}
}


function rellenarTablaCola(){
	var tbo = document.getElementById("cuerpoTabla")
 	while ( tbo.rows.length > 0 ){
    	tbo.deleteRow(0);
 	}
	$.each(mapa.Cola,function(i,propt){
		//Columna de tipo de proceso 
		var row=document.createElement('tr');
		row.setAttribute('id',''+propt.getId);
		var cell1=document.createElement('td');
		cell1.appendChild(document.createTextNode(propt.tipoProceso))
		row.appendChild(cell1);
		
		//Comlumna estado del proceso
		cell1=document.createElement('td');
		cell1.setAttribute('class','estado');
		cell1.appendChild(document.createTextNode(propt.estado))
		row.appendChild(cell1);

		//Proceso ID
		cell1=document.createElement('td');
		cell1.appendChild(document.createTextNode(propt.getId))
		row.appendChild(cell1);
		
		
		tbo.appendChild(row);
		
		//Boton para quitar de la cola
		agregarNuevoBoton(propt.getId, "quitarCola");

	})
	
	$.each($('#cuerpoTabla').children(),
	function(i,$row){
		$estado = $(this).children('.estado').text();
		if(!($estado=="En ejecucion" || $estado=="En Cola")){
            disableButton($(this).find('.quitarCola'));
		}
		else{
			enableButton($(this).find('.quitarCola'));
		}
	});
}


function agregarNuevoBoton(id ,nombre){
	$cell = $('<td></td>').appendTo('#'+id);
	var $boton = $( '<button type="button" class="'+nombre+'" id="'+nombre+'-'+id+'"'+'>'+
			   			'<img src="assets/adminica/images/icons/small/white/acces_denied_sign.png">'+
			    	'</button>'
		  	  ).click(function(event){
						actualizarCola($boton.attr('id'))
					}).appendTo($cell);
}

function disableButton(button) {
	button.prop("disabled", true);
	button.removeClass('black');
	button.addClass('light');
}
function enableButton(button) {
	button.prop("disabled", false);
	button.removeClass('light');
	button.addClass('black');
}

var habilitar = true;
function actualizarCola(id){
	var accion 		= id.split('-')[0];
	var objetivo	= id.split('-')[1];	
	if(accion == "quitarCola"){
		quitarCola(objetivo);
	}
	else{
		console.debug("accion desconocida");
	}
}

function mostrarError(event) {
	console.log(event)
	$('#containerError').text(event.data.param1);
}

function mostrarErrorProceso(event) {
	console.log(event)
	$('#containerError').text($("#" + event.data.param + "Error").attr('data-error'));
}

//Dwr's
function limpiarCola(){
	dwrProcesos.limpiarCola({ callback:handlerCola, 
		errorHandler:errorHandler});
}

function pagina_refresh() {
	dwrProcesos.refreshTabla({ callback:handlerRefresh, errorHandler:errorHandler});
}

function quitarCola(id){
	dwrProcesos.quitarCola(id,{ callback:handlerCola, errorHandler:errorHandler});
}

function ejecutarProceso(codigo) {
	dwrProcesos.ejecutarProceso(codigo, { callback:handlerEjecutarProceso, errorHandler:errorHandler});
	disableButton($('#button_' + codigo));
}

function handlerEjecutarProceso() {
	var n = noty({	text: 'Ejecucion de proceso iniciado.',
					layout: 'topRight',
					type: "success",
					timeout: 900
				});
}

function forzarTermino(proceso){
	dwrProcesos.forzarTermino(proceso.data.param,{ callback:handlerForzarTermino, errorHandler:errorHandler});	
}

function errorHandler(message) {
	var n = noty({	text: 'Error en ejecucion de proceso: ' + message,
					layout: 'topRight',
					type: "error"
				});	
}

function handlerCola(data){
	console.debug(data);
}

function handlerForzarTermino(data)
{

}