<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Predicciones</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="predc"/>" />

<script type="text/javascript" src="<c:url value="/js/showhide.js"/>"></script>
<script language="JavaScript"
	src="<c:url value="/FusionCharts/FusionCharts.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/popupDiv.js"/>"></script>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
<script type="text/javascript">
var com = -1; 
var pred = -1;
var mostrando = -1;
var mostrandocoef = -1;

function cierra(){
	showhide("boton_grafico");
	var a1 = mostrando + "Div";
	var a2= mostrandocoef + "CoefDiv";
	showhide(a1);
	showhide(a2);
	mostrando = -1;
	mostrandocoef = -1;
}

function muestra(id, coef) {
	try {
		rePos();
		showhide("boton_grafico");
		if(mostrando==id){
			var a1 = id + "Div";
			var a2= coef + "CoefDiv";
			showhide(a1);
			showhide(a2);
			mostrando = -1;
			mostrandocoef = -1;
		}
		
		else if(mostrando==-1){
			var a1 = id + "Div";
			var a2= coef + "CoefDiv";
			showhide(a1);
			showhide(a2);
			mostrando = id;
			mostrandocoef = coef;
		}
		else{			
			var g1 = mostrando + "Div";
			var g2 = mostrandocoef + "CoefDiv";
			showhide(g1);
			showhide(g2);
			var a1 = id + "Div";
			var a2= coef + "CoefDiv";
			showhide(a1);
			showhide(a2);
			mostrando = id;
			mostrandocoef = coef;
			
		}
		
	} catch (e) {
		alert("En muestra " + e);
	}
}

function muestraComplejo(c) {
	try {
		if (c != com) {
			if (mostrando != -1) {
				var g1 = mostrando + "Div";
				var g2 = mostrandocoef + "CoefDiv";
				showhide(g1);
				showhide(g2);
				mostrando = -1;
				mostrandocoef = -1;
			}

			if (pred != -1) {
				var p1 = "Pred" + pred;
				showhide(p1);
				pred = -1;
			}

			if (com != -1) {
				var c1 = "Com" + com;
				showhide(c1);
				com = -1;
			}
			
			var c2 = "Com" + c;
			showhide(c2);
			com = c;

		} else {
			if (mostrando != -1) {
				var g1 = mostrando + "Div";
				var g2 = mostrandocoef + "CoefDiv";
				showhide(g1);
				showhide(g2);
				mostrando = -1;
				mostrandocoef = -1;
			}
			if (pred != -1) {
				var p1 = "Pred" + pred;
				showhide(p1);
				pred = -1;
			}
			
			var c1 = "Com" + c;
			showhide(c1);
			com = -1;

		}
	} catch (e) {
		alert("En muestraComplejo " + e);
	}
}


function muestraPrediccion(p) {
	try {
		if (pred != p) {
			if (mostrando != -1) {
				var g1 = mostrando + "Div";
				var g2 = mostrandocoef + "CoefDiv";
				showhide(g1);
				showhide(g2);
				mostrando = -1;
				mostrandocoef = -1;
			}

			if (pred != -1) {
				var p1 = "Pred" + pred;
				showhide(p1);
				pred = -1;
			}
			var p2 = "Pred" + p;
			showhide(p2);
			pred = p;
		}

		else {

			if (mostrando != -1) {
				var g1 = mostrando + "Div";
				var g2 = mostrandocoef + "CoefDiv";
				showhide(g1);
				showhide(g2);
				mostrando = -1;
				mostrandocoef = -1;
			}

			var p1 = "Pred" + p;
			showhide(p1);
			pred = -1;

		}
	} catch (e) {
		alert("En muestraPrediccion " + e);
	}

}
	
	function esconder() {
		var len = document.getElementById("graficos").childNodes.length;
		try{
			
				
					
					
					if(''!=''){
					var g1= +"Div";
					var g2=+"CoefDiv";
					showhide(g1);
					showhide(g2);
					}
					
					
					
					if(''!=''){
					var g1= +"Div";
					var g2=+"CoefDiv";
					showhide(g1);
					showhide(g2);
					}
					
					
					
					if(''!=''){
					var g1= +"Div";
					var g2=+"CoefDiv";
					showhide(g1);
					showhide(g2);
					}
					
					
					
					if(''!=''){
					var g1= +"Div";
					var g2=+"CoefDiv";
					showhide(g1);
					showhide(g2);
					}
					
					
					
					if(''!=''){
					var g1= +"Div";
					var g2=+"CoefDiv";
					showhide(g1);
					showhide(g2);
					}
					
					
					
					if('0'!=''){
					var g1= 0+"Div";
					var g2=1+"CoefDiv";
					showhide(g1);
					showhide(g2);
					}
					
					
			
					
		
		
					
										
				
			
					
							
				
					
					
			
					
					
					
					
					
								
				
					
					
					
										
				
				showhide("Pred198");
				
			
				
										
				
										
				
										
				
										
				
										
				
										
				
										
				
										
				
										
				
										
				
				showhide("Pred199");
				
			

			
				showhide("Com1");
			
				showhide("Com2");
				initialize();
				showhide("boton_grafico");
				init();
		document.getElementById("cuerpo").style.visibility="visible";//IE
		}catch(e){
			alert("Error en esconder "+e);
		document.getElementById("cuerpo").style.visibility="visible";//Firefox
		}
		
	}

		
</script>

<script language="JavaScript">
IE5=NN4=NN6=false
if(document.all)IE5=true
else if(document.layers)NN4=true
else if(document.getElementById)NN6=true

var cursorY=0;
var cursorX=0;
function initialize() {
	if(IE5||NN6){
		myObj=document.getElementById("graficos").style;
		myBut=document.getElementById("boton_grafico").style;
	}
	else if(NN4){
		myObj=document.graficos;
		myBut=document.boton_grafico;
	}

	
	rePos();
}

function init() {
	  if (window.Event) {
	    document.captureEvents(Event.MOUSEMOVE);
	  }
	  document.onmousemove = getXY;
	}

	function getXY(e) {
	  x = (window.Event) ? e.pageX : event.clientX;
	  y = (window.Event) ? e.pageY : event.clientY;

	  cursorX=x;
	  cursorY=y;
	}

function rePos() {
	// compute center coordinate
	
	// reposition div
	
	if(IE5||NN6){
		myObj.top = cursorY+ "px";
		myBut.top = cursorY+ "px";
	}
	else if(this.NN4){
		myObj.moveTo(80,cursorY);
		myBut.top = cursorY+ "px";
	}
}

</script>


</head>
<body onload="esconder()">
<div id="contenedor">
<div id="header">
<div id="logo"><img title="" src="/zhetapricing/images/LogoZP.PNG"
	width="200" /></div>
<div>

<h1>Predicciones</h1>
</div>

</div>

<div id="cuerpo">


<div id="menu">
<div id = "complejos">
<ul>

<li><a href="javascript:muestraComplejo(1)";>
		San Agustin</a></li>

<li><a href="javascript:muestraComplejo(2)";>
		Huerfanos</a></li>

</ul>
</div>

<div id="Com1">
<ul>
	
	
		<li><a
			href="javascript:muestraPrediccion(198)";>
		MADAGASCAR 2</a></li>
	
	
	
	

</ul>
</div>

<div id="Com2">
<ul>
	
	
	
	
		<li><a
			href="javascript:muestraPrediccion(199)";>
		MADAGASCAR 2</a></li>
	
	
</ul>
</div>

</div>

<div id = "predicciones">

<form id="predicciones" action="/zhetapricing/predicciones.htm" method="post"> 

<div id="Pred198">
<table class="stats" summary="Predicciones Por Funcion y Clase">
			
	<thead>
		<tr>
			<th scope="col" class="hed">Prediccion			
			<a href="eliminarprediccion.htm?idprediccion=198">
				<img border="0" width="12px" alt="" src="/zhetapricing/images/delete_icon.gif" />
			</a>			
			</th>
			
		</tr>

	</thead>
	<tfoot>
		<tr>
			<th scope="col" class="hed">
			Fecha y hora de creacion 03/02/09 10:27 por Daniel Estevez
			</th>
			
		</tr>
	</tfoot>
	
	
	<tr>

		<td>
		<table>
		<tr><td >
		<span class="dia">
		Dia 02/01/09
		</span>
				</td></tr>
		<tr><td>
		284
		</td></tr>
		
		</table>
		</td>

		
			
			<td>
			<table>
			<tr><td>			
			Hora 10:00</td></tr>
			<tr><td>
			Estimado 18
			</td></tr>
			<tr><td>
			Varianza 247,92 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,11 
			</td></tr>
			<tr><td>
			<select id="mascaras0" name="mascaras[0]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 12:00</td></tr>
			<tr><td>
			Estimado 18
			</td></tr>

			<tr><td>
			Varianza 111,45 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,11 
			</td></tr>
			<tr><td>
			<select id="mascaras1" name="mascaras[1]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 14:00</td></tr>

			<tr><td>
			Estimado 21
			</td></tr>
			<tr><td>
			Varianza 191,32 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,13 
			</td></tr>
			<tr><td>

			<select id="mascaras2" name="mascaras[2]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 16:00</td></tr>
			<tr><td>
			Estimado 40
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,25 
			</td></tr>
			<tr><td>
			<select id="mascaras3" name="mascaras[3]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 18:00</td></tr>
			<tr><td>
			Estimado 45
			</td></tr>

			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,28 
			</td></tr>
			<tr><td>
			<select id="mascaras4" name="mascaras[4]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 20:00</td></tr>

			<tr><td>
			Estimado 98
			</td></tr>
			<tr><td>
			Varianza 2.418,67 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,60 
			</td></tr>
			<tr><td>

			<select id="mascaras5" name="mascaras[5]">
				
				<option value="0" selected="selected">
				Optimizar
				</option>
				<option value="1">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
			
			<tr><td>

			<table>
			
					<tr>
						<td>3000.0</td>
						<td>37</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>2000.0</td>

						<td>16</td>
						<td>0,01</td>
					</tr>					
			
					<tr>
						<td>1800.0</td>
						<td>5</td>
						<td>0,01</td>

					</tr>					
			
					<tr>
						<td>1000.0</td>
						<td>39</td>
						<td>0,03</td>
					</tr>					
			
			</table>			
			</td></tr>
			
			<tr><td>

			<a href="javascript:muestra(0,1)";>
					Mostrar graficos</a>					
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 22:00</td></tr>

			<tr><td>
			Estimado 44
			</td></tr>
			<tr><td>
			Varianza 177,41 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,27 
			</td></tr>
			<tr><td>

			<select id="mascaras6" name="mascaras[6]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 03/01/09
				</td></tr>
		<tr><td>
		331
		</td></tr>

		
		</table>
		</td>
		
			
			<td>
			<table>
			<tr><td>			
			Hora 10:00</td></tr>
			<tr><td>
			Estimado 41
			</td></tr>

			<tr><td>
			Varianza 1.900,78 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,25 
			</td></tr>
			<tr><td>
			<select id="mascaras7" name="mascaras[7]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 12:00</td></tr>

			<tr><td>
			Estimado 22
			</td></tr>
			<tr><td>
			Varianza 273,89 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,13 
			</td></tr>
			<tr><td>

			<select id="mascaras8" name="mascaras[8]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 14:00</td></tr>
			<tr><td>
			Estimado 24
			</td></tr>
			<tr><td>
			Varianza 98,87 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,15 
			</td></tr>
			<tr><td>
			<select id="mascaras9" name="mascaras[9]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 16:00</td></tr>
			<tr><td>
			Estimado 30
			</td></tr>

			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,18 
			</td></tr>
			<tr><td>
			<select id="mascaras10" name="mascaras[10]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 18:00</td></tr>

			<tr><td>
			Estimado 38
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,23 
			</td></tr>
			<tr><td>

			<select id="mascaras11" name="mascaras[11]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 20:00</td></tr>
			<tr><td>
			Estimado 79
			</td></tr>
			<tr><td>
			Varianza 1.769,74 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,48 
			</td></tr>
			<tr><td>
			<select id="mascaras12" name="mascaras[12]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 22:00</td></tr>
			<tr><td>
			Estimado 87
			</td></tr>

			<tr><td>
			Varianza 3.513,88 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,53 
			</td></tr>
			<tr><td>
			<select id="mascaras13" name="mascaras[13]">
				
				<option value="0" selected="selected">
				Optimizar
				</option>

				<option value="1">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
			
			<tr><td>
			<table>
			
					<tr>
						<td>3000.0</td>

						<td>34</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>2000.0</td>
						<td>14</td>
						<td>0,00</td>

					</tr>					
			
					<tr>
						<td>1800.0</td>
						<td>5</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>1000.0</td>

						<td>35</td>
						<td>0,00</td>
					</tr>					
			
			</table>			
			</td></tr>
			
			<tr><td>
			<a href="javascript:muestra(2,3)";>
					Mostrar graficos</a>					
			</td></tr>

						
			</table>					
			</td>
			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 04/01/09
				</td></tr>
		<tr><td>

		364
		</td></tr>
		
		</table>
		</td>
		
			
			<td>
			<table>
			<tr><td>			
			Hora 10:00</td></tr>
			<tr><td>

			Estimado 26
			</td></tr>
			<tr><td>
			Varianza 1.077,05 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,16 
			</td></tr>
			<tr><td>
			<select id="mascaras14" name="mascaras[14]">

				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>

			<table>
			<tr><td>			
			Hora 12:00</td></tr>
			<tr><td>
			Estimado 15
			</td></tr>
			<tr><td>
			Varianza 54,00 
			
			</td></tr>
			<tr><td>

			% ocupacion 0,09 
			</td></tr>
			<tr><td>
			<select id="mascaras15" name="mascaras[15]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 14:00</td></tr>
			<tr><td>
			Estimado 42
			</td></tr>

			<tr><td>
			Varianza 573,92 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,26 
			</td></tr>
			<tr><td>
			<select id="mascaras16" name="mascaras[16]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 16:00</td></tr>

			<tr><td>
			Estimado 65
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,40 
			</td></tr>
			<tr><td>

			<select id="mascaras17" name="mascaras[17]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 18:00</td></tr>
			<tr><td>
			Estimado 76
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,47 
			</td></tr>
			<tr><td>
			<select id="mascaras18" name="mascaras[18]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 20:00</td></tr>
			<tr><td>
			Estimado 80
			</td></tr>

			<tr><td>
			Varianza 188,55 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,49 
			</td></tr>
			<tr><td>
			<select id="mascaras19" name="mascaras[19]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 22:00</td></tr>

			<tr><td>
			Estimado 43
			</td></tr>
			<tr><td>
			Varianza 66,92 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,26 
			</td></tr>
			<tr><td>

			<select id="mascaras20" name="mascaras[20]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 05/01/09
				</td></tr>
		<tr><td>
		286
		</td></tr>

		
		</table>
		</td>
		
			
			<td>
			<table>
			<tr><td>			
			Hora 10:00</td></tr>
			<tr><td>
			Estimado 9
			</td></tr>

			<tr><td>
			Varianza 107,15 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,06 
			</td></tr>
			<tr><td>
			<select id="mascaras21" name="mascaras[21]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 12:00</td></tr>

			<tr><td>
			Estimado 13
			</td></tr>
			<tr><td>
			Varianza 18,96 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,08 
			</td></tr>
			<tr><td>

			<select id="mascaras22" name="mascaras[22]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 14:00</td></tr>
			<tr><td>
			Estimado 28
			</td></tr>
			<tr><td>
			Varianza 184,22 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,17 
			</td></tr>
			<tr><td>
			<select id="mascaras23" name="mascaras[23]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 16:00</td></tr>
			<tr><td>
			Estimado 49
			</td></tr>

			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,30 
			</td></tr>
			<tr><td>
			<select id="mascaras24" name="mascaras[24]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 18:00</td></tr>

			<tr><td>
			Estimado 110
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,67 
			</td></tr>
			<tr><td>

			<select id="mascaras25" name="mascaras[25]">
				
				<option value="0" selected="selected">
				Optimizar
				</option>
				<option value="1">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
			
			<tr><td>

			<table>
			
					<tr>
						<td>3000.0</td>
						<td>12</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>2000.0</td>

						<td>15</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>1800.0</td>
						<td>6</td>
						<td>0,00</td>

					</tr>					
			
					<tr>
						<td>1000.0</td>
						<td>76</td>
						<td>0,01</td>
					</tr>					
			
			</table>			
			</td></tr>
			
			<tr><td>

			<a href="javascript:muestra(4,5)";>
					Mostrar graficos</a>					
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 20:00</td></tr>

			<tr><td>
			Estimado 55
			</td></tr>
			<tr><td>
			Varianza 233,15 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,34 
			</td></tr>
			<tr><td>

			<select id="mascaras26" name="mascaras[26]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 22:00</td></tr>
			<tr><td>
			Estimado 22
			</td></tr>
			<tr><td>
			Varianza 103,62 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,13 
			</td></tr>
			<tr><td>
			<select id="mascaras27" name="mascaras[27]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 06/01/09
				</td></tr>

		<tr><td>
		323
		</td></tr>
		
		</table>
		</td>
		
			
			<td>
			<table>
			<tr><td>			
			Hora 10:00</td></tr>

			<tr><td>
			Estimado 78
			</td></tr>
			<tr><td>
			Varianza 10.118,59 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,48 
			</td></tr>
			<tr><td>

			<select id="mascaras28" name="mascaras[28]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 12:00</td></tr>
			<tr><td>
			Estimado 31
			</td></tr>
			<tr><td>
			Varianza 828,56 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,19 
			</td></tr>
			<tr><td>
			<select id="mascaras29" name="mascaras[29]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 14:00</td></tr>
			<tr><td>
			Estimado 31
			</td></tr>

			<tr><td>
			Varianza 136,02 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,19 
			</td></tr>
			<tr><td>
			<select id="mascaras30" name="mascaras[30]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 16:00</td></tr>

			<tr><td>
			Estimado 37
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,23 
			</td></tr>
			<tr><td>

			<select id="mascaras31" name="mascaras[31]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 18:00</td></tr>
			<tr><td>
			Estimado 53
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,33 
			</td></tr>
			<tr><td>
			<select id="mascaras32" name="mascaras[32]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 20:00</td></tr>
			<tr><td>
			Estimado 68
			</td></tr>

			<tr><td>
			Varianza 1.028,65 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,42 
			</td></tr>
			<tr><td>
			<select id="mascaras33" name="mascaras[33]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 22:00</td></tr>

			<tr><td>
			Estimado 25
			</td></tr>
			<tr><td>
			Varianza 185,14 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,15 
			</td></tr>
			<tr><td>

			<select id="mascaras34" name="mascaras[34]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 07/01/09
				</td></tr>
		<tr><td>
		274
		</td></tr>

		
		</table>
		</td>
		
			
			<td>
			<table>
			<tr><td>			
			Hora 10:00</td></tr>
			<tr><td>
			Estimado 27
			</td></tr>

			<tr><td>
			Varianza 1.070,69 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,17 
			</td></tr>
			<tr><td>
			<select id="mascaras35" name="mascaras[35]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 12:00</td></tr>

			<tr><td>
			Estimado 32
			</td></tr>
			<tr><td>
			Varianza 658,84 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,20 
			</td></tr>
			<tr><td>

			<select id="mascaras36" name="mascaras[36]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 14:00</td></tr>
			<tr><td>
			Estimado 12
			</td></tr>
			<tr><td>
			Varianza 110,70 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,07 
			</td></tr>
			<tr><td>
			<select id="mascaras37" name="mascaras[37]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 16:00</td></tr>
			<tr><td>
			Estimado 28
			</td></tr>

			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,17 
			</td></tr>
			<tr><td>
			<select id="mascaras38" name="mascaras[38]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 18:00</td></tr>

			<tr><td>
			Estimado 42
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,26 
			</td></tr>
			<tr><td>

			<select id="mascaras39" name="mascaras[39]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 20:00</td></tr>
			<tr><td>
			Estimado 84
			</td></tr>
			<tr><td>
			Varianza 5.730,88 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,52 
			</td></tr>
			<tr><td>
			<select id="mascaras40" name="mascaras[40]">
				
				<option value="0" selected="selected">
				Optimizar
				</option>
				<option value="1">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
			
			<tr><td>
			<table>
			
					<tr>
						<td>3000.0</td>
						<td>2</td>
						<td>0,00</td>

					</tr>					
			
					<tr>
						<td>2000.0</td>
						<td>5</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>1800.0</td>

						<td>3</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>1000.0</td>
						<td>73</td>
						<td>0,00</td>

					</tr>					
			
			</table>			
			</td></tr>
			
			<tr><td>
			<a href="javascript:muestra(6,7)";>
					Mostrar graficos</a>					
			</td></tr>
						
			</table>					
			</td>
			
			<td>

			<table>
			<tr><td>			
			Hora 22:00</td></tr>
			<tr><td>
			Estimado 49
			</td></tr>
			<tr><td>
			Varianza 1.582,12 
			
			</td></tr>
			<tr><td>

			% ocupacion 0,30 
			</td></tr>
			<tr><td>
			<select id="mascaras41" name="mascaras[41]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 08/01/09
				</td></tr>

		<tr><td>
		346
		</td></tr>
		
		</table>
		</td>
		
			
			<td>
			<table>
			<tr><td>			
			Hora 10:00</td></tr>

			<tr><td>
			Estimado 6
			</td></tr>
			<tr><td>
			Varianza 11,24 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,06 
			</td></tr>
			<tr><td>

			<select id="mascaras42" name="mascaras[42]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 12:00</td></tr>
			<tr><td>
			Estimado 24
			</td></tr>
			<tr><td>
			Varianza 162,81 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,24 
			</td></tr>
			<tr><td>
			<select id="mascaras43" name="mascaras[43]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 14:00</td></tr>
			<tr><td>
			Estimado 37
			</td></tr>

			<tr><td>
			Varianza 23,10 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,37 
			</td></tr>
			<tr><td>
			<select id="mascaras44" name="mascaras[44]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 16:00</td></tr>

			<tr><td>
			Estimado 56
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,57 
			</td></tr>
			<tr><td>

			<select id="mascaras45" name="mascaras[45]">
				
				<option value="0" selected="selected">
				Optimizar
				</option>
				<option value="1">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
			
			<tr><td>

			<table>
			
					<tr>
						<td>3000.0</td>
						<td>18</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>2000.0</td>

						<td>9</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>1800.0</td>
						<td>3</td>
						<td>0,00</td>

					</tr>					
			
					<tr>
						<td>1000.0</td>
						<td>25</td>
						<td>0,01</td>
					</tr>					
			
			</table>			
			</td></tr>
			
			<tr><td>

			<a href="javascript:muestra(8,9)";>
					Mostrar graficos</a>					
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 18:00</td></tr>

			<tr><td>
			Estimado 67
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,68 
			</td></tr>
			<tr><td>

			<select id="mascaras46" name="mascaras[46]">
				
				<option value="0" selected="selected">
				Optimizar
				</option>
				<option value="1">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
			
			<tr><td>

			<table>
			
					<tr>
						<td>3000.0</td>
						<td>27</td>
						<td>0,02</td>
					</tr>					
			
					<tr>
						<td>2000.0</td>

						<td>11</td>
						<td>0,04</td>
					</tr>					
			
					<tr>
						<td>1800.0</td>
						<td>3</td>
						<td>0,05</td>

					</tr>					
			
					<tr>
						<td>1000.0</td>
						<td>26</td>
						<td>0,07</td>
					</tr>					
			
			</table>			
			</td></tr>
			
			<tr><td>

			<a href="javascript:muestra(10,11)";>
					Mostrar graficos</a>					
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 20:00</td></tr>

			<tr><td>
			Estimado 111
			</td></tr>
			<tr><td>
			Varianza 917,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 1,12 
			</td></tr>
			<tr><td>

			<select id="mascaras47" name="mascaras[47]">
				
				<option value="0" selected="selected">
				Optimizar
				</option>
				<option value="1">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
			
			<tr><td>

			<table>
			
					<tr>
						<td>3000.0</td>
						<td>42</td>
						<td>0,01</td>
					</tr>					
			
					<tr>
						<td>2000.0</td>

						<td>18</td>
						<td>0,03</td>
					</tr>					
			
					<tr>
						<td>1800.0</td>
						<td>6</td>
						<td>0,03</td>

					</tr>					
			
					<tr>
						<td>1000.0</td>
						<td>45</td>
						<td>0,06</td>
					</tr>					
			
			</table>			
			</td></tr>
			
			<tr><td>

			<a href="javascript:muestra(12,13)";>
					Mostrar graficos</a>					
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 22:00</td></tr>

			<tr><td>
			Estimado 45
			</td></tr>
			<tr><td>
			Varianza 0,92 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,45 
			</td></tr>
			<tr><td>

			<select id="mascaras48" name="mascaras[48]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 09/01/09
				</td></tr>
		<tr><td>
		133
		</td></tr>

		
		</table>
		</td>
		
			
			<td>
			<table>
			<tr><td>			
			Hora 10:00</td></tr>
			<tr><td>
			Estimado 7
			</td></tr>

			<tr><td>
			Varianza 17,81 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,07 
			</td></tr>
			<tr><td>
			<select id="mascaras49" name="mascaras[49]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 12:00</td></tr>

			<tr><td>
			Estimado 36
			</td></tr>
			<tr><td>
			Varianza 55,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,36 
			</td></tr>
			<tr><td>

			<select id="mascaras50" name="mascaras[50]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 14:00</td></tr>
			<tr><td>
			Estimado 35
			</td></tr>
			<tr><td>
			Varianza 103,91 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,35 
			</td></tr>
			<tr><td>
			<select id="mascaras51" name="mascaras[51]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 16:00</td></tr>
			<tr><td>
			Estimado 16
			</td></tr>

			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,16 
			</td></tr>
			<tr><td>
			<select id="mascaras52" name="mascaras[52]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 18:00</td></tr>

			<tr><td>
			Estimado 14
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,14 
			</td></tr>
			<tr><td>

			<select id="mascaras53" name="mascaras[53]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 20:00</td></tr>
			<tr><td>
			Estimado 13
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,13 
			</td></tr>
			<tr><td>
			<select id="mascaras54" name="mascaras[54]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 22:00</td></tr>
			<tr><td>
			Estimado 5
			</td></tr>

			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,05 
			</td></tr>
			<tr><td>
			<select id="mascaras55" name="mascaras[55]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			
	</tr>	
	
	<tr>
		<td>
		<table>

		<tr><td>
		Dia 10/01/09
				</td></tr>
		<tr><td>
		222
		</td></tr>
		
		</table>
		</td>
		
			
			<td>
			<table>

			<tr><td>			
			Hora 10:00</td></tr>
			<tr><td>
			Estimado 10
			</td></tr>
			<tr><td>
			Varianza 41,93 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,10 
			</td></tr>

			<tr><td>
			<select id="mascaras56" name="mascaras[56]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>

						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 12:00</td></tr>
			<tr><td>
			Estimado 26
			</td></tr>
			<tr><td>

			Varianza 81,71 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,26 
			</td></tr>
			<tr><td>
			<select id="mascaras57" name="mascaras[57]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 14:00</td></tr>

			<tr><td>
			Estimado 45
			</td></tr>
			<tr><td>
			Varianza 235,69 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,45 
			</td></tr>
			<tr><td>

			<select id="mascaras58" name="mascaras[58]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 16:00</td></tr>
			<tr><td>
			Estimado 18
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,18 
			</td></tr>
			<tr><td>
			<select id="mascaras59" name="mascaras[59]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 18:00</td></tr>
			<tr><td>
			Estimado 31
			</td></tr>

			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,31 
			</td></tr>
			<tr><td>
			<select id="mascaras60" name="mascaras[60]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 20:00</td></tr>

			<tr><td>
			Estimado 44
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,44 
			</td></tr>
			<tr><td>

			<select id="mascaras61" name="mascaras[61]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 22:00</td></tr>
			<tr><td>
			Estimado 41
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,41 
			</td></tr>
			<tr><td>
			<select id="mascaras62" name="mascaras[62]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 11/01/09
				</td></tr>

		<tr><td>
		222
		</td></tr>
		
		</table>
		</td>
		
			
			<td>
			<table>
			<tr><td>			
			Hora 10:00</td></tr>

			<tr><td>
			Estimado 8
			</td></tr>
			<tr><td>
			Varianza 69,74 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,08 
			</td></tr>
			<tr><td>

			<select id="mascaras63" name="mascaras[63]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 12:00</td></tr>
			<tr><td>
			Estimado 11
			</td></tr>
			<tr><td>
			Varianza 5,64 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,11 
			</td></tr>
			<tr><td>
			<select id="mascaras64" name="mascaras[64]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 14:00</td></tr>
			<tr><td>
			Estimado 20
			</td></tr>

			<tr><td>
			Varianza 75,20 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,20 
			</td></tr>
			<tr><td>
			<select id="mascaras65" name="mascaras[65]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 16:00</td></tr>

			<tr><td>
			Estimado 23
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,23 
			</td></tr>
			<tr><td>

			<select id="mascaras66" name="mascaras[66]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>

			
			<td>
			<table>
			<tr><td>			
			Hora 18:00</td></tr>
			<tr><td>
			Estimado 40
			</td></tr>
			<tr><td>
			Varianza 0,00 
			
			</td></tr>

			<tr><td>
			% ocupacion 0,40 
			</td></tr>
			<tr><td>
			<select id="mascaras67" name="mascaras[67]">
				
				<option value="0">
				Optimizar
				</option>
				<option value="1" selected="selected">
				No Optimizar
				</option>

							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 20:00</td></tr>
			<tr><td>
			Estimado 75
			</td></tr>

			<tr><td>
			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,76 
			</td></tr>
			<tr><td>
			<select id="mascaras68" name="mascaras[68]">
				
				<option value="0" selected="selected">
				Optimizar
				</option>

				<option value="1">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
			
			<tr><td>
			<table>
			
					<tr>
						<td>3000.0</td>

						<td>30</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>2000.0</td>
						<td>12</td>
						<td>0,00</td>

					</tr>					
			
					<tr>
						<td>1800.0</td>
						<td>4</td>
						<td>0,00</td>
					</tr>					
			
					<tr>
						<td>1000.0</td>

						<td>29</td>
						<td>0,01</td>
					</tr>					
			
			</table>			
			</td></tr>
			
			<tr><td>
			<a href="javascript:muestra(14,15)";>
					Mostrar graficos</a>					
			</td></tr>

						
			</table>					
			</td>
			
			<td>
			<table>
			<tr><td>			
			Hora 22:00</td></tr>
			<tr><td>
			Estimado 40
			</td></tr>
			<tr><td>

			Varianza 0,00 
			
			</td></tr>
			<tr><td>
			% ocupacion 0,40 
			</td></tr>
			<tr><td>
			<select id="mascaras69" name="mascaras[69]">
				
				<option value="0">
				Optimizar
				</option>

				<option value="1" selected="selected">
				No Optimizar
				</option>
							
			</select>						
			</td></tr>
						
			</table>					
			</td>
			
			
	</tr>	
	
</table>
</div>

<div id="Pred199">

<table class="stats" summary="Predicciones Por Funcion y Clase">
			
	<thead>
		<tr>
			<th scope="col" class="hed">Prediccion			
			<a href="eliminarprediccion.htm?idprediccion=199">
				<img border="0" width="12px" alt="" src="/zhetapricing/images/delete_icon.gif" />
			</a>			
			</th>
			
		</tr>
	</thead>

	<tfoot>
		<tr>
			<th scope="col" class="hed">
			Fecha y hora de creacion 03/02/09 10:27 por Daniel Estevez
			</th>
			
		</tr>
	</tfoot>
	
	
	<tr>
		<td>

		<table>
		<tr><td>
		Dia 02/01/09
				</td></tr>
		<tr><td>
		275
		</td></tr>
		
		</table>
		</td>
		
		<td>		
		Dia sin funciones
		</td>

		
			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 03/01/09
				</td></tr>
		<tr><td>
		230
		</td></tr>

		
		</table>
		</td>
		
		<td>		
		Dia sin funciones
		</td>
		
			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>

		Dia 04/01/09
				</td></tr>
		<tr><td>
		371
		</td></tr>
		
		</table>
		</td>
		
		<td>		
		Dia sin funciones
		</td>
		
			
			
	</tr>	
	
	<tr>

		<td>
		<table>
		<tr><td>
		Dia 05/01/09
				</td></tr>
		<tr><td>
		288
		</td></tr>
		
		</table>
		</td>

		
		<td>		
		Dia sin funciones
		</td>
		
			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 06/01/09
				</td></tr>
		<tr><td>

		162
		</td></tr>
		
		</table>
		</td>
		
		<td>		
		Dia sin funciones
		</td>
		
			
			
	</tr>	
	
	<tr>
		<td>
		<table>

		<tr><td>
		Dia 07/01/09
				</td></tr>
		<tr><td>
		163
		</td></tr>
		
		</table>
		</td>
		
		<td>		
		Dia sin funciones
		</td>

		
			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 08/01/09
				</td></tr>
		<tr><td>
		273
		</td></tr>

		
		</table>
		</td>
		
		<td>		
		Dia sin funciones
		</td>
		
			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>

		Dia 09/01/09
				</td></tr>
		<tr><td>
		113
		</td></tr>
		
		</table>
		</td>
		
		<td>		
		Dia sin funciones
		</td>
		
			
			
	</tr>	
	
	<tr>

		<td>
		<table>
		<tr><td>
		Dia 10/01/09
				</td></tr>
		<tr><td>
		141
		</td></tr>
		
		</table>
		</td>

		
		<td>		
		Dia sin funciones
		</td>
		
			
			
	</tr>	
	
	<tr>
		<td>
		<table>
		<tr><td>
		Dia 11/01/09
				</td></tr>
		<tr><td>

		261
		</td></tr>
		
		</table>
		</td>
		
		<td>		
		Dia sin funciones
		</td>
		
			
			
	</tr>	
	
</table>
</div>

<table align="center">

			<tr>
				<td><input type="submit" class="submit" name="predecir"
		value="Predecir x Clase" /></td>
				<td><input type="submit" name="volver" value="Volver" /></td>
			</tr>
		</table>


</form>
</div>


<div id="graficos">
		
		<div id="boton_grafico"> 
				<a href="javascript:cierra()";>
					<img border="0" alt="" src="/zhetapricing/images/delete_icon.gif" /></a>
			</div>
		
			
			

			<!-- START Script Block for Chart 0 -->
			<div id='0Div' align='center'></div>
			<script type='text/javascript'>
			var chart_0 = new FusionCharts("FusionCharts/FCF_MSLine.swf", "0", "1200", "400", "0", "1");
			
			
				    // Provide entire XML data using dataXML method
				    chart_0.setDataXML("<graph bgSWF='images/fusioncharts.jpg' showvalues='0' rotateNames='1' canvasBgColor='FFFFFF' canvasBgAlpha='20' canvasBorderColor='7B3F00' canvasBorderThickness='0' divLineColor='a82925' numdivlines='4' showgridbg='1' showhovercap='1' lineThickness='1' animation='1' caption='' xAxisName='Demanda' yAxisName='Precio' numberPrefix='' formatNumberScale='2' decimalPrecision='2'><categories><category name='237.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='283.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='299.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='325.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='342.0'/><category/><category/><category/><category/><category/><category/><category/><category name='350.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='380.0'/><category/><category/><category name='383.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='417.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='441.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='637.0'/><category/><category/><category/><category/><category/><category name='643.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='664.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='677.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='733.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='769.0'/></categories><dataset seriesName='Precio' color='AFD8F8' anchorBorderColor='F6BD0F' anchorBgColor='8BBA00'><set value='3400.0'/><set value='3391.304347826087'/><set value='3382.608695652174'/><set value='3373.913043478261'/><set value='3365.217391304348'/><set value='3356.521739130435'/><set value='3347.8260869565215'/><set value='3339.1304347826085'/><set value='3330.4347826086955'/><set value='3321.7391304347825'/><set value='3313.0434782608695'/><set value='3304.3478260869565'/><set value='3295.6521739130435'/><set value='3286.9565217391305'/><set value='3278.2608695652175'/><set value='3269.5652173913045'/><set value='3260.8695652173915'/><set value='3252.173913043478'/><set value='3243.478260869565'/><set value='3234.782608695652'/><set value='3226.086956521739'/><set value='3217.391304347826'/><set value='3208.695652173913'/><set value='3200.0'/><set value='3191.304347826087'/><set value='3182.608695652174'/><set value='3173.913043478261'/><set value='3165.217391304348'/><set value='3156.521739130435'/><set value='3147.826086956522'/><set value='3139.1304347826085'/><set value='3130.4347826086955'/><set value='3121.7391304347825'/><set value='3113.0434782608695'/><set value='3104.3478260869565'/><set value='3095.6521739130435'/><set value='3086.9565217391305'/><set value='3078.2608695652175'/><set value='3069.5652173913045'/><set value='3060.8695652173915'/><set value='3052.173913043478'/><set value='3043.478260869565'/><set value='3034.782608695652'/><set value='3026.086956521739'/><set value='3017.391304347826'/><set value='3008.695652173913'/><set value='3000.0'/><set value='2987.5'/><set value='2975.0'/><set value='2962.5'/><set value='2950.0'/><set value='2937.5'/><set value='2925.0'/><set value='2912.5'/><set value='2900.0'/><set value='2887.5'/><set value='2875.0'/><set value='2862.5'/><set value='2850.0'/><set value='2837.5'/><set value='2825.0'/><set value='2812.5'/><set value='2800.0'/><set value='2796.153846153846'/><set value='2792.3076923076924'/><set value='2788.4615384615386'/><set value='2784.6153846153848'/><set value='2780.769230769231'/><set value='2776.923076923077'/><set value='2773.076923076923'/><set value='2769.230769230769'/><set value='2765.3846153846152'/><set value='2761.5384615384614'/><set value='2757.6923076923076'/><set value='2753.846153846154'/><set value='2750.0'/><set value='2746.153846153846'/><set value='2742.3076923076924'/><set value='2738.4615384615386'/><set value='2734.6153846153848'/><set value='2730.769230769231'/><set value='2726.923076923077'/><set value='2723.076923076923'/><set value='2719.230769230769'/><set value='2715.3846153846152'/><set value='2711.5384615384614'/><set value='2707.6923076923076'/><set value='2703.846153846154'/><set value='2700.0'/><set value='2694.1176470588234'/><set value='2688.235294117647'/><set value='2682.3529411764707'/><set value='2676.470588235294'/><set value='2670.5882352941176'/><set value='2664.705882352941'/><set value='2658.823529411765'/><set value='2652.9411764705883'/><set value='2647.0588235294117'/><set value='2641.176470588235'/><set value='2635.294117647059'/><set value='2629.4117647058824'/><set value='2623.529411764706'/><set value='2617.6470588235293'/><set value='2611.764705882353'/><set value='2605.8823529411766'/><set value='2600.0'/><set value='2587.5'/><set value='2575.0'/><set value='2562.5'/><set value='2550.0'/><set value='2537.5'/><set value='2525.0'/><set value='2512.5'/><set value='2500.0'/><set value='2496.0'/><set value='2492.0'/><set value='2488.0'/><set value='2484.0'/><set value='2480.0'/><set value='2476.0'/><set value='2472.0'/><set value='2468.0'/><set value='2464.0'/><set value='2460.0'/><set value='2456.0'/><set value='2452.0'/><set value='2448.0'/><set value='2444.0'/><set value='2440.0'/><set value='2436.0'/><set value='2432.0'/><set value='2428.0'/><set value='2424.0'/><set value='2420.0'/><set value='2416.0'/><set value='2412.0'/><set value='2408.0'/><set value='2404.0'/><set value='2400.0'/><set value='2396.0'/><set value='2392.0'/><set value='2388.0'/><set value='2384.0'/><set value='2380.0'/><set value='2353.3333333333335'/><set value='2326.6666666666665'/><set value='2300.0'/><set value='2294.1176470588234'/><set value='2288.235294117647'/><set value='2282.3529411764707'/><set value='2276.470588235294'/><set value='2270.5882352941176'/><set value='2264.705882352941'/><set value='2258.823529411765'/><set value='2252.9411764705883'/><set value='2247.0588235294117'/><set value='2241.176470588235'/><set value='2235.294117647059'/><set value='2229.4117647058824'/><set value='2223.529411764706'/><set value='2217.6470588235293'/><set value='2211.764705882353'/><set value='2205.8823529411766'/><set value='2200.0'/><set value='2194.1176470588234'/><set value='2188.235294117647'/><set value='2182.3529411764707'/><set value='2176.470588235294'/><set value='2170.5882352941176'/><set value='2164.705882352941'/><set value='2158.823529411765'/><set value='2152.9411764705883'/><set value='2147.0588235294117'/><set value='2141.176470588235'/><set value='2135.294117647059'/><set value='2129.4117647058824'/><set value='2123.529411764706'/><set value='2117.6470588235293'/><set value='2111.764705882353'/><set value='2105.8823529411766'/><set value='2100.0'/><set value='2095.8333333333335'/><set value='2091.6666666666665'/><set value='2087.5'/><set value='2083.3333333333335'/><set value='2079.1666666666665'/><set value='2075.0'/><set value='2070.8333333333335'/><set value='2066.6666666666665'/><set value='2062.5'/><set value='2058.3333333333335'/><set value='2054.1666666666665'/><set value='2050.0'/><set value='2045.8333333333333'/><set value='2041.6666666666667'/><set value='2037.5'/><set value='2033.3333333333333'/><set value='2029.1666666666667'/><set value='2025.0'/><set value='2020.8333333333333'/><set value='2016.6666666666667'/><set value='2012.5'/><set value='2008.3333333333333'/><set value='2004.1666666666667'/><set value='2000.0'/><set value='1999.4897959183672'/><set value='1998.9795918367347'/><set value='1998.469387755102'/><set value='1997.9591836734694'/><set value='1997.4489795918366'/><set value='1996.938775510204'/><set value='1996.4285714285713'/><set value='1995.9183673469388'/><set value='1995.408163265306'/><set value='1994.8979591836735'/><set value='1994.3877551020407'/><set value='1993.8775510204082'/><set value='1993.3673469387754'/><set value='1992.857142857143'/><set value='1992.3469387755101'/><set value='1991.8367346938776'/><set value='1991.3265306122448'/><set value='1990.8163265306123'/><set value='1990.3061224489795'/><set value='1989.795918367347'/><set value='1989.2857142857142'/><set value='1988.7755102040817'/><set value='1988.265306122449'/><set value='1987.7551020408164'/><set value='1987.2448979591836'/><set value='1986.734693877551'/><set value='1986.2244897959183'/><set value='1985.7142857142858'/><set value='1985.204081632653'/><set value='1984.6938775510205'/><set value='1984.1836734693877'/><set value='1983.6734693877552'/><set value='1983.1632653061224'/><set value='1982.6530612244899'/><set value='1982.142857142857'/><set value='1981.6326530612246'/><set value='1981.1224489795918'/><set value='1980.6122448979593'/><set value='1980.1020408163265'/><set value='1979.591836734694'/><set value='1979.0816326530612'/><set value='1978.5714285714287'/><set value='1978.061224489796'/><set value='1977.5510204081634'/><set value='1977.0408163265306'/><set value='1976.530612244898'/><set value='1976.0204081632653'/><set value='1975.5102040816328'/><set value='1975.0'/><set value='1974.4897959183672'/><set value='1973.9795918367347'/><set value='1973.469387755102'/><set value='1972.9591836734694'/><set value='1972.4489795918366'/><set value='1971.938775510204'/><set value='1971.4285714285713'/><set value='1970.9183673469388'/><set value='1970.408163265306'/><set value='1969.8979591836735'/><set value='1969.3877551020407'/><set value='1968.8775510204082'/><set value='1968.3673469387754'/><set value='1967.857142857143'/><set value='1967.3469387755101'/><set value='1966.8367346938776'/><set value='1966.3265306122448'/><set value='1965.8163265306123'/><set value='1965.3061224489795'/><set value='1964.795918367347'/><set value='1964.2857142857142'/><set value='1963.7755102040817'/><set value='1963.265306122449'/><set value='1962.7551020408164'/><set value='1962.2448979591836'/><set value='1961.734693877551'/><set value='1961.2244897959183'/><set value='1960.7142857142858'/><set value='1960.204081632653'/><set value='1959.6938775510205'/><set value='1959.1836734693877'/><set value='1958.6734693877552'/><set value='1958.1632653061224'/><set value='1957.6530612244899'/><set value='1957.142857142857'/><set value='1956.6326530612246'/><set value='1956.1224489795918'/><set value='1955.6122448979593'/><set value='1955.1020408163265'/><set value='1954.591836734694'/><set value='1954.0816326530612'/><set value='1953.5714285714287'/><set value='1953.061224489796'/><set value='1952.5510204081634'/><set value='1952.0408163265306'/><set value='1951.530612244898'/><set value='1951.0204081632653'/><set value='1950.5102040816328'/><set value='1950.0'/><set value='1949.4897959183672'/><set value='1948.9795918367347'/><set value='1948.469387755102'/><set value='1947.9591836734694'/><set value='1947.4489795918366'/><set value='1946.938775510204'/><set value='1946.4285714285713'/><set value='1945.9183673469388'/><set value='1945.408163265306'/><set value='1944.8979591836735'/><set value='1944.3877551020407'/><set value='1943.8775510204082'/><set value='1943.3673469387754'/><set value='1942.857142857143'/><set value='1942.3469387755101'/><set value='1941.8367346938776'/><set value='1941.3265306122448'/><set value='1940.8163265306123'/><set value='1940.3061224489795'/><set value='1939.795918367347'/><set value='1939.2857142857142'/><set value='1938.7755102040817'/><set value='1938.265306122449'/><set value='1937.7551020408164'/><set value='1937.2448979591836'/><set value='1936.734693877551'/><set value='1936.2244897959183'/><set value='1935.7142857142858'/><set value='1935.204081632653'/><set value='1934.6938775510205'/><set value='1934.1836734693877'/><set value='1933.6734693877552'/><set value='1933.1632653061224'/><set value='1932.6530612244899'/><set value='1932.142857142857'/><set value='1931.6326530612246'/><set value='1931.1224489795918'/><set value='1930.6122448979593'/><set value='1930.1020408163265'/><set value='1929.591836734694'/><set value='1929.0816326530612'/><set value='1928.5714285714287'/><set value='1928.061224489796'/><set value='1927.5510204081634'/><set value='1927.0408163265306'/><set value='1926.5306122448978'/><set value='1926.0204081632653'/><set value='1925.5102040816328'/><set value='1925.0'/><set value='1924.4897959183672'/><set value='1923.9795918367347'/><set value='1923.4693877551022'/><set value='1922.9591836734694'/><set value='1922.4489795918366'/><set value='1921.938775510204'/><set value='1921.4285714285713'/><set value='1920.9183673469388'/><set value='1920.408163265306'/><set value='1919.8979591836735'/><set value='1919.3877551020407'/><set value='1918.8775510204082'/><set value='1918.3673469387754'/><set value='1917.857142857143'/><set value='1917.3469387755101'/><set value='1916.8367346938776'/><set value='1916.3265306122448'/><set value='1915.8163265306123'/><set value='1915.3061224489795'/><set value='1914.795918367347'/><set value='1914.2857142857142'/><set value='1913.7755102040817'/><set value='1913.265306122449'/><set value='1912.7551020408164'/><set value='1912.2448979591836'/><set value='1911.734693877551'/><set value='1911.2244897959183'/><set value='1910.7142857142858'/><set value='1910.204081632653'/><set value='1909.6938775510205'/><set value='1909.1836734693877'/><set value='1908.6734693877552'/><set value='1908.1632653061224'/><set value='1907.6530612244899'/><set value='1907.142857142857'/><set value='1906.6326530612246'/><set value='1906.1224489795918'/><set value='1905.6122448979593'/><set value='1905.1020408163265'/><set value='1904.591836734694'/><set value='1904.0816326530612'/><set value='1903.5714285714287'/><set value='1903.061224489796'/><set value='1902.5510204081634'/><set value='1902.0408163265306'/><set value='1901.5306122448978'/><set value='1901.0204081632653'/><set value='1900.5102040816328'/><set value='1900.0'/><set value='1875.0'/><set value='1850.0'/><set value='1825.0'/><set value='1800.0'/><set value='1775.0'/><set value='1750.0'/><set value='1745.2380952380952'/><set value='1740.4761904761904'/><set value='1735.7142857142858'/><set value='1730.952380952381'/><set value='1726.1904761904761'/><set value='1721.4285714285713'/><set value='1716.6666666666667'/><set value='1711.904761904762'/><set value='1707.142857142857'/><set value='1702.3809523809523'/><set value='1697.6190476190477'/><set value='1692.857142857143'/><set value='1688.095238095238'/><set value='1683.3333333333333'/><set value='1678.5714285714287'/><set value='1673.8095238095239'/><set value='1669.047619047619'/><set value='1664.2857142857142'/><set value='1659.5238095238096'/><set value='1654.7619047619048'/><set value='1650.0'/><set value='1646.1538461538462'/><set value='1642.3076923076924'/><set value='1638.4615384615386'/><set value='1634.6153846153845'/><set value='1630.7692307692307'/><set value='1626.923076923077'/><set value='1623.076923076923'/><set value='1619.2307692307693'/><set value='1615.3846153846155'/><set value='1611.5384615384614'/><set value='1607.6923076923076'/><set value='1603.8461538461538'/><set value='1600.0'/><set value='1594.642857142857'/><set value='1589.2857142857142'/><set value='1583.9285714285713'/><set value='1578.5714285714287'/><set value='1573.2142857142858'/><set value='1567.857142857143'/><set value='1562.5'/><set value='1557.142857142857'/><set value='1551.7857142857142'/><set value='1546.4285714285713'/><set value='1541.0714285714287'/><set value='1535.7142857142858'/><set value='1530.357142857143'/><set value='1525.0'/><set value='1519.642857142857'/><set value='1514.2857142857142'/><set value='1508.9285714285713'/><set value='1503.5714285714287'/><set value='1498.2142857142858'/><set value='1492.857142857143'/><set value='1487.5'/><set value='1482.142857142857'/><set value='1476.7857142857142'/><set value='1471.4285714285713'/><set value='1466.0714285714287'/><set value='1460.7142857142858'/><set value='1455.357142857143'/><set value='1450.0'/><set value='1444.642857142857'/><set value='1439.2857142857142'/><set value='1433.9285714285713'/><set value='1428.5714285714287'/><set value='1423.2142857142858'/><set value='1417.857142857143'/><set value='1412.5'/><set value='1407.142857142857'/><set value='1401.7857142857142'/><set value='1396.4285714285713'/><set value='1391.0714285714287'/><set value='1385.7142857142858'/><set value='1380.357142857143'/><set value='1375.0'/><set value='1369.642857142857'/><set value='1364.2857142857142'/><set value='1358.9285714285713'/><set value='1353.5714285714287'/><set value='1348.2142857142858'/><set value='1342.857142857143'/><set value='1337.5'/><set value='1332.142857142857'/><set value='1326.7857142857142'/><set value='1321.4285714285716'/><set value='1316.0714285714284'/><set value='1310.7142857142858'/><set value='1305.357142857143'/><set value='1300.0'/><set value='1200.0'/><set value='1100.0'/><set value='1073.5294117647059'/><set value='1047.0588235294117'/><set value='1020.5882352941177'/><set value='994.1176470588235'/><set value='967.6470588235294'/><set value='941.1764705882354'/><set value='914.7058823529412'/><set value='888.2352941176471'/><set value='861.7647058823529'/><set value='835.2941176470588'/><set value='808.8235294117646'/><set value='782.3529411764706'/><set value='755.8823529411765'/><set value='729.4117647058824'/><set value='702.9411764705883'/><set value='676.4705882352941'/><set value='650.0'/><set value='623.5294117647059'/><set value='597.0588235294117'/><set value='570.5882352941177'/><set value='544.1176470588235'/><set value='517.6470588235294'/><set value='491.17647058823525'/><set value='464.7058823529412'/><set value='438.2352941176471'/><set value='411.7647058823529'/><set value='385.2941176470588'/><set value='358.82352941176475'/><set value='332.3529411764706'/><set value='305.88235294117646'/><set value='279.4117647058823'/><set value='252.9411764705883'/><set value='226.47058823529414'/><set value='200.0'/></dataset><dataset seriesName='Regresion' color='A66EDD' anchorBorderColor='F984A1' anchorBgColor='CCCC00'><set value='3767.223521738901'/><set value='3749.1882056881786'/><set value='3731.31431072843'/><set value='3713.5997257228532'/><set value='3696.0423757878516'/><set value='3678.6402215232865'/><set value='3661.391258262176'/><set value='3644.29351533927'/><set value='3627.3450553779467'/><set value='3610.543973594901'/><set value='3593.888397122116'/><set value='3577.3764843456133'/><set value='3561.006424260498'/><set value='3544.7764358418517'/><set value='3528.684767430996'/><set value='3512.729696136714'/><set value='3496.9095272509962'/><set value='3481.2225936789023'/><set value='3465.667255382151'/><set value='3450.2418988360496'/><set value='3434.9449364993925'/><set value='3419.7748062969713'/><set value='3404.7299711143532'/><set value='3389.8089183045786'/><set value='3375.010159206469'/><set value='3360.332228674214'/><set value='3345.773684617938'/><set value='3331.333107554952'/><set value='3317.0091001713968'/><set value='3302.800286893999'/><set value='3288.70531347168'/><set value='3274.7228465667395'/><set value='3260.8515733553736'/><set value='3247.0902011372687'/><set value='3233.4374569540432'/><set value='3219.8920872162985'/><set value='3206.452857339053'/><set value='3193.118551385346'/><set value='3179.887971717795'/><set value='3166.759938657904'/><set value='3153.733290152919'/><set value='3140.80688145004'/><set value='3127.9795847778023'/><set value='3115.2502890344413'/><set value='3102.617899483063'/><set value='3090.0813374534546'/><set value='3077.6395400503548'/><set value='3065.29145986804'/><set value='3053.036064711046'/><set value='3040.872337320894'/><set value='3028.799275108654'/><set value='3016.8158898932124'/><set value='3004.9212076450963'/><set value='2993.114268235721'/><set value='2981.3941251919277'/><set value='2969.759845455681'/><set value='2958.210509148796'/><set value='2946.745209342587'/><set value='2935.3630518323'/><set value='2924.0631549162267'/><set value='2912.844649179387'/><set value='2901.7066772816606'/><set value='2890.6483937502735'/><set value='2879.6689647765274'/><set value='2868.767568016675'/><set value='2857.9433923968445'/><set value='2847.1956379219146'/><set value='2836.5235154882475'/><set value='2825.9262467001927'/><set value='2815.4030636902708'/><set value='2804.9532089429526'/><set value='2794.575935121952'/><set value='2784.2705049009464'/><set value='2774.0361907976508'/><set value='2763.872275011168'/><set value='2753.778049262538'/><set value='2743.752814638413'/><set value='2733.7958814377903'/><set value='2723.9065690217303'/><set value='2714.0842056659976'/><set value='2704.328128416547'/><set value='2694.637682947811'/><set value='2685.012223423703'/><set value='2675.4511123612947'/><set value='2665.953720497095'/><set value='2656.5194266558847'/><set value='2647.1476176220326'/><set value='2637.8376880132632'/><set value='2628.5890401567985'/><set value='2619.401083967838'/><set value='2610.273236830315'/><set value='2601.2049234798874'/><set value='2592.1955758891136'/><set value='2583.2446331547576'/><set value='2574.3515413871933'/><set value='2565.5157536018437'/><set value='2556.7367296126295'/><set value='2548.013935927372'/><set value='2539.3468456451133'/><set value='2530.734938355311'/><set value='2522.177700038872'/><set value='2513.674622970982'/><set value='2505.2252056256943'/><set value='2496.828952582241'/><set value='2488.4853744330326'/><set value='2480.1939876933043'/><set value='2471.9543147123886'/><set value='2463.7658835865577'/><set value='2455.6282280734304'/><set value='2447.5408875078865'/><set value='2439.503406719478'/><set value='2431.5153359512888'/><set value='2423.576230780226'/><set value='2415.6856520387105'/><set value='2407.843165737732'/><set value='2400.048342991253'/><set value='2392.3007599419193'/><set value='2384.599997688065'/><set value='2376.945642211977'/><set value='2369.3372843093966'/><set value='2361.7745195202347'/><set value='2354.256948060472'/><set value='2346.7841747552275'/><set value='2339.3558089729645'/><set value='2331.9714645608196'/><set value='2324.6307597810237'/><set value='2317.3333172484013'/><set value='2310.078763868923'/><set value='2302.8667307792916'/><set value='2295.6968532875403'/><set value='2288.568770814627'/><set value='2281.4821268369997'/><set value='2274.43656883012'/><set value='2267.431748212922'/><set value='2260.46732029319'/><set value='2253.5429442138407'/><set value='2246.658282900085'/><set value='2239.813003007457'/><set value='2233.006774870703'/><set value='2226.2392724534893'/><set value='2219.5101732989415'/><set value='2212.8191584809847'/><set value='2206.165912556465'/><set value='2199.550123518055'/><set value='2192.971482747908'/><set value='2186.4296849720636'/><set value='2179.9244282155814'/><set value='2173.455413758394'/><set value='2167.022346091861'/><set value='2160.6249328760136'/><set value='2154.2628848974828'/><set value='2147.935916028088'/><set value='2141.643743184079'/><set value='2135.3860862860242'/><set value='2129.162668219323'/><set value='2122.9732147953405'/><set value='2116.8174547131466'/><set value='2110.695119521855'/><set value='2104.6059435835414'/><set value='2098.5496640367433'/><set value='2092.5260207605193'/><set value='2086.534756339065'/><set value='2080.5756160268725'/><set value='2074.6483477144243'/><set value='2068.752701894415'/><set value='2062.888431628484'/><set value='2057.055292514458'/><set value='2051.253042654094'/><set value='2045.481442621304'/><set value='2039.7402554308665'/><set value='2034.0292465076068'/><set value='2028.3481836560434'/><set value='2022.6968370304862'/><set value='2017.0749791055857'/><set value='2011.4823846473219'/><set value='2005.918830684423'/><set value='2000.3840964802114'/><set value='1994.8779635048666'/><set value='1989.4002154080965'/><set value='1983.950637992216'/><set value='1978.5290191856163'/><set value='1973.135149016631'/><set value='1967.7688195877765'/><set value='1962.4298250503766'/><set value='1957.1179615795543'/><set value='1951.8330273495835'/><set value='1946.5748225096063'/><set value='1941.3431491596943'/><set value='1936.1378113272613'/><set value='1930.958614943811'/><set value='1925.805367822024'/><set value='1920.6778796331687'/><set value='1915.575961884839'/><set value='1910.4994278990089'/><set value='1905.4480927903978'/><set value='1900.4217734451454'/><set value='1895.4202884997878'/><set value='1890.443458320533'/><set value='1885.4911049828245'/><set value='1880.5630522511983'/><set value='1875.659125559418'/><set value='1870.7791519908899'/><set value='1865.922960259354'/><set value='1861.090380689843'/><set value='1856.2812451999027'/><set value='1851.4953872810806'/><set value='1846.7326419806652'/><set value='1841.9928458836787'/><set value='1837.2758370951212'/><set value='1832.5814552224551'/><set value='1827.9095413583332'/><set value='1823.2599380635613'/><set value='1818.6324893502965'/><set value='1814.027040665471'/><set value='1809.4434388744442'/><set value='1804.8815322448766'/><set value='1800.3411704308212'/><set value='1795.822204457031'/><set value='1791.3244867034764'/><set value='1786.8478708900748'/><set value='1782.3922120616207'/><set value='1777.957366572921'/><set value='1773.5431920741282'/><set value='1769.1495474962685'/><set value='1764.7762930369645'/><set value='1760.4232901463458'/><set value='1756.0904015131468'/><set value='1751.7774910509893'/><set value='1747.484423884845'/><set value='1743.2110663376775'/><set value='1738.957285917259'/><set value='1734.7229513031607'/><set value='1730.5079323339123'/><set value='1726.3120999943303'/><set value='1722.1353264030113'/><set value='1717.9774847999881'/><set value='1713.8384495345445'/><set value='1709.7180960531898'/><set value='1705.6163008877888'/><set value='1701.5329416438424'/><set value='1697.4678969889223'/><set value='1693.4210466412515'/><set value='1689.3922713584332'/><set value='1685.3814529263223'/><set value='1681.3884741480392'/><set value='1677.413218833126'/><set value='1673.4555717868354'/><set value='1669.5154187995608'/><set value='1665.5926466363971'/><set value='1661.6871430268334'/><set value='1657.7987966545782'/><set value='1653.9274971475086'/><set value='1650.073135067749'/><set value='1646.2356019018735'/><set value='1642.4147900512285'/><set value='1638.6105928223794'/><set value='1634.822904417674'/><set value='1631.051619925923'/><set value='1627.2966353131987'/><set value='1623.5578474137444'/><set value='1619.8351539209973'/><set value='1616.1284533787232'/><set value='1612.4376451722583'/><set value='1608.7626295198604'/><set value='1605.1033074641643'/><set value='1601.4595808637434'/><set value='1597.8313523847726'/><set value='1594.2185254927945'/><set value='1590.621004444584'/><set value='1587.0386942801128'/><set value='1583.4715008146106'/><set value='1579.919330630723'/><set value='1576.3820910707625'/><set value='1572.8596902290544'/><set value='1569.3520369443731'/><set value='1565.859040792472'/><set value='1562.380612078697'/><set value='1558.916661830696'/><set value='1555.4671017912087'/><set value='1552.0318444109457'/><set value='1548.61080284155'/><set value='1545.2038909286455'/><set value='1541.811023204962'/><set value='1538.4321148835484'/><set value='1535.067081851059'/><set value='1531.7158406611231'/><set value='1528.378308527792'/><set value='1525.0544033190604'/><set value='1521.744043550467'/><set value='1518.447148378766'/><set value='1515.1636375956764'/><set value='1511.8934316216992'/><set value='1508.6364515000116'/><set value='1505.3926188904277'/><set value='1502.1618560634315'/><set value='1498.9440858942771'/><set value='1495.7392318571583'/><set value='1492.547218019444'/><set value='1489.3679690359809'/><set value='1486.2014101434604'/><set value='1483.0474671548511'/><set value='1479.9060664538927'/><set value='1476.7771349896557'/><set value='1473.6606002711594'/><set value='1470.556390362055'/><set value='1467.4644338753649'/><set value='1464.3846599682843'/><set value='1461.3169983370396'/><set value='1458.2613792118068'/><set value='1455.217733351685'/><set value='1452.1859920397278'/><set value='1449.1660870780286'/><set value='1446.1579507828628'/><set value='1443.1615159798835'/><set value='1440.1767159993706'/><set value='1437.2034846715317'/><set value='1434.2417563218578'/><set value='1431.2914657665272'/><set value='1428.352548307864'/><set value='1425.424939729843'/><set value='1422.508576293645'/><set value='1419.6033947332653'/><set value='1416.7093322511637'/><set value='1413.8263265139653'/><set value='1410.9543156482118'/><set value='1408.093238236152'/><set value='1405.2430333115844'/><set value='1402.4036403557423'/><set value='1399.5749992932233'/><set value='1396.7570504879639'/><set value='1393.9497347392594'/><set value='1391.1529932778215'/><set value='1388.3667677618841'/><set value='1385.5910002733492'/><set value='1382.8256333139716'/><set value='1380.0706098015883'/><set value='1377.3258730663872'/><set value='1374.5913668472135'/><set value='1371.8670352879194'/><set value='1369.1528229337482'/><set value='1366.4486747277617'/><set value='1363.754536007301'/><set value='1361.0703525004892'/><set value='1358.3960703227658'/><set value='1355.731635973464'/><set value='1353.0769963324187'/><set value='1350.4320986566133'/><set value='1347.796890576861'/><set value='1345.1713200945192'/><set value='1342.555335578242'/><set value='1339.948885760764'/><set value='1337.3519197357166'/><set value='1334.7643869544816'/><set value='1332.186237223073'/><set value='1329.6174206990545'/><set value='1327.0578878884871'/><set value='1324.5075896429094'/><set value='1321.9664771563475'/><set value='1319.4345019623584'/><set value='1316.911615931101'/><set value='1314.3977712664398'/><set value='1311.8929205030759'/><set value='1309.397016503709'/><set value='1306.9100124562285'/><set value='1304.4318618709322'/><set value='1301.9625185777736'/><set value='1299.5019367236387'/><set value='1297.050070769649'/><set value='1294.6068754884914'/><set value='1292.172305961777'/><set value='1289.746317577426'/><set value='1287.3288660270762'/><set value='1284.9199073035227'/><set value='1282.5193976981798'/><set value='1280.1272937985682'/><set value='1277.743552485831'/><set value='1275.3681309322694'/><set value='1273.000986598907'/><set value='1270.6420772330775'/><set value='1268.291360866035'/><set value='1265.9487958105897'/><set value='1263.614340658767'/><set value='1261.287954279489'/><set value='1258.9695958162788'/><set value='1256.6592246849887'/><set value='1254.3568005715495'/><set value='1252.0622834297433'/><set value='1249.7756334789972'/><set value='1247.4968112021988'/><set value='1245.2257773435338'/><set value='1242.9624929063434'/><set value='1240.706919151005'/><set value='1238.4590175928297'/><set value='1236.2187499999861'/><set value='1233.9860783914378'/><set value='1231.7609650349054'/><set value='1229.5433724448467'/><set value='1227.3332633804573'/><set value='1225.130600843689'/><set value='1222.9353480772882'/><set value='1220.7474685628536'/><set value='1218.5669260189127'/><set value='1216.3936843990152'/><set value='1214.2277078898467'/><set value='1212.0689609093586'/><set value='1209.917408104918'/><set value='1207.7730143514732'/><set value='1205.635744749738'/><set value='1203.5055646243925'/><set value='1201.3824395223019'/><set value='1199.2663352107513'/><set value='1197.1572176756972'/><set value='1195.0550531200367'/><set value='1192.9598079618925'/><set value='1190.871448832912'/><set value='1188.7899425765866'/><set value='1186.7152562465826'/><set value='1184.647357105091'/><set value='1182.5862126211912'/><set value='1180.5317904692286'/><set value='1178.4840585272107'/><set value='1176.4429848752163'/><set value='1174.4085377938193'/><set value='1172.3806857625266'/><set value='1170.3593974582334'/><set value='1168.3446417536888'/><set value='1166.3363877159793'/><set value='1164.3346046050237'/><set value='1162.339261872083'/><set value='1160.3503291582842'/><set value='1158.3677762931572'/><set value='1156.391573293185'/><set value='1154.421690360369'/><set value='1152.4580978808037'/><set value='1150.5007664232685'/><set value='1148.54966673783'/><set value='1146.6047697544575'/><set value='1144.6660465816515'/><set value='1142.7334685050841'/><set value='1140.807006986253'/><set value='1138.8866336611457'/><set value='1136.9723203389167'/><set value='1135.0640390005783'/><set value='1133.1617617977001'/><set value='1131.2654610511236'/><set value='1129.3751092496846'/><set value='1127.490679048951'/><set value='1125.612143269969'/><set value='1123.7394748980219'/><set value='1121.8726470813995'/><set value='1120.0116331301788'/><set value='1118.1564065150164'/><set value='1116.306940865949'/><set value='1114.4632099712076'/><set value='1112.6251877760405'/><set value='1110.7928483815474'/><set value='1108.9661660435233'/><set value='1107.1451151713136'/><set value='1105.3296703266772'/><set value='1103.519806222663'/><set value='1101.7154977224932'/><set value='1099.9167198384575'/><set value='1098.1234477308185'/><set value='1096.335656706723'/><set value='1094.5533222191277'/><set value='1092.7764198657294'/><set value='1091.0049253879085'/><set value='1089.23881466968'/><set value='1087.4780637366528'/><set value='1085.7226487549995'/><set value='1083.972546030435'/><set value='1082.227732007203'/><set value='1080.4881832670721'/><set value='1078.753876528341'/><set value='1077.0247886448492'/><set value='1075.300896605003'/><set value='1073.582177530801'/><set value='1071.8686086768748'/><set value='1070.1601674295368'/><set value='1068.456831305832'/><set value='1066.7585779526034'/><set value='1065.065385145562'/><set value='1063.3772307883658'/><set value='1061.6940929117063'/><set value='1060.0159496724038'/><set value='1058.3427793525086'/><set value='1056.6745603584109'/><set value='1055.01127121996'/><set value='1053.352890589586'/><set value='1051.6993972414352'/><set value='1050.0507700705075'/><set value='1048.4069880918044'/><set value='1046.7680304394821'/><set value='1045.133876366014'/><set value='1043.504505241357'/><set value='1041.879896552129'/><set value='1040.2600299007881'/><set value='1038.644885004825'/><set value='1037.0344416959554'/><set value='1035.428679919324'/><set value='1033.827579732714'/><set value='1032.2311213057608'/><set value='1030.6392849191766'/><set value='1029.052050963977'/><set value='1027.469399940717'/><set value='1025.8913124587325'/><set value='1024.3177692353863'/><set value='1022.7487510953238'/><set value='1021.184238969732'/><set value='1019.6242138956055'/><set value='1018.0686570150192'/><set value='1016.5175495744049'/><set value='1014.9708729238374'/><set value='1013.4286085163219'/><set value='1011.8907379070919'/><set value='1010.3572427529085'/><set value='1008.8281048113691'/><set value='1007.3033059402196'/><set value='1005.7828280966718'/><set value='1004.2666533367286'/><set value='1002.7547638145122'/><set value='1001.2471417816'/><set value='999.7437695863633'/><set value='998.2446296733142'/><set value='996.749704582455'/><set value='995.2589769486353'/><set value='993.7724295009124'/><set value='992.290045061918'/><set value='990.8118065472293'/><set value='989.3376969647455'/><set value='987.8676994140694'/><set value='986.4017970858933'/><set value='984.9399732613906'/></dataset></graph>");
				
				<!-- Finally, render the chart.-->
				chart_0.render("0Div");
			</script>
			<!--END Script Block for Chart 0 -->


			

			<!-- START Script Block for Chart 1Coef -->
			<div id='1CoefDiv' align='center'></div>
			<script type='text/javascript'>
			var chart_1Coef = new FusionCharts("FusionCharts/FCF_MSLine.swf", "1Coef", "1200", "400", "0", "1");
			
			
				    // Provide entire XML data using dataXML method
				    chart_1Coef.setDataXML("<graph bgSWF='images/fusioncharts.jpg' showvalues='0' rotateNames='1' canvasBgColor='FFFFFF' canvasBgAlpha='20' canvasBorderColor='7B3F00' canvasBorderThickness='0' divLineColor='a82925' numdivlines='4' showgridbg='1' showhovercap='1' lineThickness='1' animation='1' caption='' xAxisName='Demanda' yAxisName='Coeficiente de Variabilidad' numberPrefix='' formatNumberScale='2' decimalPrecision='2'><categories><category name='237.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='283.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='325.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='350.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='380.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='417.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='441.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='643.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='733.0'/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category/><category name='769.0'/></categories><dataset seriesName='Coeficiente de Variabilidad' color='AFD8F8' anchorBorderColor='F6BD0F' anchorBgColor='8BBA00'><set value='0.26135372144454727'/><set value='0.2584686411461243'/><set value='0.25558356084770123'/><set value='0.25269848054927824'/><set value='0.24981340025085522'/><set value='0.24692831995243222'/><set value='0.2440432396540092'/><set value='0.24115815935558618'/><set value='0.2382730790571632'/><set value='0.23538799875874017'/><set value='0.23250291846031718'/><set value='0.22961783816189416'/><set value='0.22673275786347113'/><set value='0.22384767756504814'/><set value='0.22096259726662512'/><set value='0.2180775169682021'/><set value='0.2151924366697791'/><set value='0.2123073563713561'/><set value='0.20942227607293307'/><set value='0.20653719577451007'/><set value='0.20365211547608705'/><set value='0.20076703517766403'/><set value='0.19788195487924104'/><set value='0.19499687458081802'/><set value='0.192111794282395'/><set value='0.189226713983972'/><set value='0.18634163368554899'/><set value='0.18345655338712596'/><set value='0.18057147308870297'/><set value='0.17768639279027995'/><set value='0.17480131249185693'/><set value='0.17191623219343394'/><set value='0.16903115189501092'/><set value='0.1661460715965879'/><set value='0.1632609912981649'/><set value='0.1603759109997419'/><set value='0.15749083070131886'/><set value='0.15460575040289587'/><set value='0.15172067010447288'/><set value='0.14883558980604986'/><set value='0.14595050950762684'/><set value='0.14306542920920381'/><set value='0.1401803489107808'/><set value='0.1372952686123578'/><set value='0.13441018831393478'/><set value='0.1315251080155118'/><set value='0.12864002771708877'/><set value='0.127084500743313'/><set value='0.12552897376953723'/><set value='0.12397344679576146'/><set value='0.1224179198219857'/><set value='0.12086239284820993'/><set value='0.11930686587443416'/><set value='0.11775133890065839'/><set value='0.11619581192688262'/><set value='0.11464028495310685'/><set value='0.11308475797933108'/><set value='0.11152923100555533'/><set value='0.10997370403177956'/><set value='0.10841817705800379'/><set value='0.10686265008422802'/><set value='0.10530712311045225'/><set value='0.10375159613667648'/><set value='0.10219606916290071'/><set value='0.10064054218912494'/><set value='0.09908501521534918'/><set value='0.09752948824157341'/><set value='0.09597396126779764'/><set value='0.09441843429402189'/><set value='0.0928629073202461'/><set value='0.09130738034647035'/><set value='0.08975185337269456'/><set value='0.0881963263989188'/><set value='0.08664079942514304'/><set value='0.08508527245136727'/><set value='0.0835297454775915'/><set value='0.08197421850381573'/><set value='0.08041869153003996'/><set value='0.07886316455626419'/><set value='0.07730763758248843'/><set value='0.07575211060871265'/><set value='0.07419658363493689'/><set value='0.07264105666116112'/><set value='0.07108552968738535'/><set value='0.0695300027136096'/><set value='0.06797447573983381'/><set value='0.06641894876605806'/><set value='0.06486342179228229'/><set value='0.06330789481850652'/><set value='0.06313471719039727'/><set value='0.06296153956228802'/><set value='0.06278836193417878'/><set value='0.06261518430606954'/><set value='0.062442006677960286'/><set value='0.06226882904985104'/><set value='0.062095651421741796'/><set value='0.06192247379363255'/><set value='0.061749296165523306'/><set value='0.06157611853741406'/><set value='0.06140294090930481'/><set value='0.061229763281195566'/><set value='0.06105658565308632'/><set value='0.060883408024977076'/><set value='0.06071023039686783'/><set value='0.06053705276875858'/><set value='0.06036387514064934'/><set value='0.06019069751254009'/><set value='0.060017519884430846'/><set value='0.0598443422563216'/><set value='0.05967116462821235'/><set value='0.05949798700010311'/><set value='0.05932480937199386'/><set value='0.05915163174388462'/><set value='0.05897845411577537'/><set value='0.059201000078134225'/><set value='0.05942354604049309'/><set value='0.059646092002851946'/><set value='0.0598686379652108'/><set value='0.060091183927569666'/><set value='0.06031372988992852'/><set value='0.06053627585228739'/><set value='0.060758821814646244'/><set value='0.0609813677770051'/><set value='0.061203913739363965'/><set value='0.06142645970172282'/><set value='0.06164900566408168'/><set value='0.06187155162644054'/><set value='0.0620940975887994'/><set value='0.06231664355115826'/><set value='0.06253918951351711'/><set value='0.06276173547587598'/><set value='0.06298428143823484'/><set value='0.06320682740059369'/><set value='0.06342937336295255'/><set value='0.06365191932531142'/><set value='0.06387446528767028'/><set value='0.06409701125002913'/><set value='0.064319557212388'/><set value='0.06454210317474686'/><set value='0.06476464913710571'/><set value='0.06498719509946457'/><set value='0.06520974106182344'/><set value='0.06543228702418229'/><set value='0.06565483298654115'/><set value='0.0643716095746899'/><set value='0.06308838616283866'/><set value='0.0618051627509874'/><set value='0.06052193933913615'/><set value='0.0592387159272849'/><set value='0.057955492515433644'/><set value='0.0566722691035824'/><set value='0.05538904569173114'/><set value='0.054105822279879895'/><set value='0.05282259886802865'/><set value='0.05153937545617739'/><set value='0.050256152044326145'/><set value='0.04897292863247489'/><set value='0.04768970522062364'/><set value='0.046406481808772396'/><set value='0.04512325839692114'/><set value='0.04384003498506989'/><set value='0.04255681157321864'/><set value='0.04127358816136739'/><set value='0.039990364749516144'/><set value='0.03870714133766489'/><set value='0.037423917925813635'/><set value='0.03614069451396239'/><set value='0.03485747110211114'/><set value='0.03357424769025989'/><set value='0.03229102427840864'/><set value='0.031007800866557383'/><set value='0.029724577454706136'/><set value='0.02844135404285488'/><set value='0.027158130631003634'/><set value='0.02587490721915238'/><set value='0.024591683807301132'/><set value='0.023308460395449884'/><set value='0.02202523698359863'/><set value='0.020742013571747382'/><set value='0.019458790159896128'/><set value='0.01817556674804488'/><set value='0.017512733704215723'/><set value='0.016849900660386566'/><set value='0.016187067616557412'/><set value='0.015524234572728255'/><set value='0.014861401528899098'/><set value='0.01419856848506994'/><set value='0.013535735441240785'/><set value='0.01287290239741163'/><set value='0.012210069353582473'/><set value='0.011547236309753316'/><set value='0.010884403265924159'/><set value='0.010221570222095003'/><set value='0.009558737178265846'/><set value='0.00889590413443669'/><set value='0.008233071090607533'/><set value='0.007570238046778378'/><set value='0.006907405002949221'/><set value='0.0062445719591200655'/><set value='0.005581738915290908'/><set value='0.004918905871461751'/><set value='0.004256072827632596'/><set value='0.0035932397838034386'/><set value='0.0029304067399742815'/><set value='0.0022675736961451248'/><set value='0.0027048802323991995'/><set value='0.0031421867686532738'/><set value='0.003579493304907348'/><set value='0.004016799841161423'/><set value='0.0044541063774154975'/><set value='0.004891412913669572'/><set value='0.005328719449923646'/><set value='0.00576602598617772'/><set value='0.006203332522431795'/><set value='0.006640639058685869'/><set value='0.007077945594939945'/><set value='0.007515252131194019'/><set value='0.007952558667448094'/><set value='0.008389865203702168'/><set value='0.008827171739956242'/><set value='0.009264478276210316'/><set value='0.009701784812464392'/><set value='0.010139091348718465'/><set value='0.01057639788497254'/><set value='0.011013704421226614'/><set value='0.01145101095748069'/><set value='0.011888317493734764'/><set value='0.012325624029988836'/><set value='0.012762930566242912'/><set value='0.013200237102496987'/><set value='0.013637543638751063'/><set value='0.014074850175005135'/><set value='0.01451215671125921'/><set value='0.014949463247513286'/><set value='0.015386769783767358'/><set value='0.015824076320021432'/><set value='0.01626138285627551'/><set value='0.016698689392529584'/><set value='0.01713599592878366'/><set value='0.017573302465037733'/><set value='0.018010609001291807'/><set value='0.01844791553754588'/><set value='0.018885222073799956'/><set value='0.01932252861005403'/><set value='0.019759835146308104'/><set value='0.020197141682562182'/><set value='0.020634448218816256'/><set value='0.021071754755070327'/><set value='0.021509061291324405'/><set value='0.02194636782757848'/><set value='0.02238367436383255'/><set value='0.022820980900086628'/><set value='0.023258287436340702'/><set value='0.023695593972594776'/><set value='0.02413290050884885'/><set value='0.02457020704510293'/><set value='0.025007513581357003'/><set value='0.025444820117611074'/><set value='0.025882126653865148'/><set value='0.026319433190119222'/><set value='0.026756739726373296'/><set value='0.027194046262627374'/><set value='0.02763135279888145'/><set value='0.028068659335135523'/><set value='0.028505965871389594'/><set value='0.028943272407643668'/><set value='0.029380578943897742'/><set value='0.02981788548015182'/><set value='0.030255192016405894'/><set value='0.03069249855265997'/><set value='0.031129805088914043'/><set value='0.031567111625168114'/><set value='0.032004418161422195'/><set value='0.03244172469767626'/><set value='0.032879031233930336'/><set value='0.03331633777018441'/><set value='0.033753644306438485'/><set value='0.03419095084269256'/><set value='0.034628257378946634'/><set value='0.03506556391520071'/><set value='0.03550287045145478'/><set value='0.035940176987708856'/><set value='0.03637748352396293'/><set value='0.036814790060217005'/><set value='0.03725209659647108'/><set value='0.037689403132725154'/><set value='0.038126709668979235'/><set value='0.03856401620523331'/><set value='0.03900132274148738'/><set value='0.03943862927774145'/><set value='0.039875935813995525'/><set value='0.040313242350249606'/><set value='0.04075054888650368'/><set value='0.041187855422757755'/><set value='0.04162516195901183'/><set value='0.0420624684952659'/><set value='0.04249977503151997'/><set value='0.04293708156777405'/><set value='0.043374388104028126'/><set value='0.0438116946402822'/><set value='0.044249001176536275'/><set value='0.04468630771279034'/><set value='0.04512361424904442'/><set value='0.0455609207852985'/><set value='0.04599822732155257'/><set value='0.046435533857806646'/><set value='0.04687284039406073'/><set value='0.047310146930314795'/><set value='0.047747453466568876'/><set value='0.04818476000282294'/><set value='0.04862206653907702'/><set value='0.04905937307533109'/><set value='0.049496679611585166'/><set value='0.04993398614783925'/><set value='0.050371292684093315'/><set value='0.050808599220347396'/><set value='0.05124590575660146'/><set value='0.05168321229285554'/><set value='0.05212051882910962'/><set value='0.052557825365363686'/><set value='0.05299513190161777'/><set value='0.053432438437871835'/><set value='0.053869744974125916'/><set value='0.05430705151037998'/><set value='0.05474435804663406'/><set value='0.05518166458288814'/><set value='0.055618971119142206'/><set value='0.05605627765539629'/><set value='0.056493584191650355'/><set value='0.056930890727904436'/><set value='0.05736819726415851'/><set value='0.057805503800412585'/><set value='0.05824281033666666'/><set value='0.058680116872920726'/><set value='0.05911742340917481'/><set value='0.059554729945428875'/><set value='0.059992036481682956'/><set value='0.06042934301793703'/><set value='0.060866649554191105'/><set value='0.06130395609044518'/><set value='0.06174126262669926'/><set value='0.06217856916295333'/><set value='0.0626158756992074'/><set value='0.06305318223546148'/><set value='0.06349048877171555'/><set value='0.06392779530796963'/><set value='0.0643651018442237'/><set value='0.06480240838047778'/><set value='0.06523971491673185'/><set value='0.06567702145298591'/><set value='0.06611432798924'/><set value='0.06655163452549406'/><set value='0.06698894106174814'/><set value='0.06742624759800223'/><set value='0.0678635541342563'/><set value='0.06830086067051037'/><set value='0.06873816720676444'/><set value='0.06917547374301852'/><set value='0.06961278027927259'/><set value='0.07005008681552667'/><set value='0.07048739335178074'/><set value='0.07092469988803482'/><set value='0.07136200642428889'/><set value='0.07179931296054297'/><set value='0.07223661949679704'/><set value='0.07267392603305112'/><set value='0.07311123256930518'/><set value='0.07354853910555927'/><set value='0.07398584564181335'/><set value='0.07442315217806741'/><set value='0.0748604587143215'/><set value='0.07529776525057556'/><set value='0.07573507178682964'/><set value='0.07617237832308371'/><set value='0.07660968485933778'/><set value='0.07704699139559186'/><set value='0.07748429793184593'/><set value='0.07792160446810001'/><set value='0.07835891100435409'/><set value='0.07879621754060816'/><set value='0.07923352407686224'/><set value='0.0796708306131163'/><set value='0.08010813714937039'/><set value='0.08054544368562445'/><set value='0.08098275022187854'/><set value='0.0814200567581326'/><set value='0.08185736329438668'/><set value='0.08229466983064077'/><set value='0.08273197636689482'/><set value='0.0831692829031489'/><set value='0.08360658943940298'/><set value='0.08404389597565705'/><set value='0.08448120251191113'/><set value='0.08491850904816521'/><set value='0.08535581558441928'/><set value='0.08579312212067335'/><set value='0.08623042865692743'/><set value='0.08666773519318151'/><set value='0.08710504172943556'/><set value='0.08754234826568964'/><set value='0.08797965480194372'/><set value='0.0884169613381978'/><set value='0.08885426787445187'/><set value='0.08929157441070594'/><set value='0.08972888094696002'/><set value='0.09016618748321409'/><set value='0.09060349401946817'/><set value='0.08983324669734387'/><set value='0.08906299937521957'/><set value='0.08829275205309527'/><set value='0.08752250473097097'/><set value='0.08675225740884668'/><set value='0.08598201008672238'/><set value='0.08521176276459808'/><set value='0.08444151544247377'/><set value='0.08367126812034947'/><set value='0.08290102079822517'/><set value='0.08213077347610087'/><set value='0.08136052615397657'/><set value='0.08059027883185227'/><set value='0.07982003150972797'/><set value='0.07904978418760368'/><set value='0.07827953686547938'/><set value='0.07750928954335508'/><set value='0.07673904222123078'/><set value='0.07596879489910648'/><set value='0.07519854757698217'/><set value='0.07442830025485787'/><set value='0.07365805293273359'/><set value='0.07288780561060929'/><set value='0.07211755828848498'/><set value='0.07134731096636068'/><set value='0.07057706364423638'/><set value='0.06980681632211208'/><set value='0.06903656899998778'/><set value='0.06826632167786348'/><set value='0.06749607435573918'/><set value='0.06672582703361488'/><set value='0.06595557971149058'/><set value='0.06518533238936629'/><set value='0.06441508506724199'/><set value='0.06364483774511769'/><set value='0.06287459042299338'/><set value='0.06210434310086908'/><set value='0.06133409577874478'/><set value='0.06056384845662049'/><set value='0.05979360113449619'/><set value='0.059023353812371886'/><set value='0.058253106490247585'/><set value='0.05748285916812329'/><set value='0.05671261184599899'/><set value='0.05594236452387469'/><set value='0.05517211720175039'/><set value='0.054401869879626086'/><set value='0.05363162255750179'/><set value='0.05286137523537749'/><set value='0.05209112791325319'/><set value='0.05132088059112889'/><set value='0.050550633269004594'/><set value='0.049780385946880286'/><set value='0.04901013862475599'/><set value='0.04823989130263169'/><set value='0.04746964398050739'/><set value='0.046699396658383095'/><set value='0.045929149336258794'/><set value='0.04515890201413449'/><set value='0.0443886546920102'/><set value='0.0436184073698859'/><set value='0.04284816004776159'/><set value='0.042077912725637295'/><set value='0.041307665403512994'/><set value='0.04053741808138869'/><set value='0.0397671707592644'/><set value='0.0389969234371401'/><set value='0.0382266761150158'/><set value='0.0374564287928915'/><set value='0.036686181470767194'/><set value='0.03591593414864289'/><set value='0.0351456868265186'/><set value='0.0343754395043943'/><set value='0.03360519218227'/><set value='0.0328349448601457'/><set value='0.0320646975380214'/><set value='0.03129445021589709'/><set value='0.030524202893772806'/><set value='0.029753955571648498'/><set value='0.028983708249524204'/><set value='0.028213460927399903'/><set value='0.0274432136052756'/><set value='0.026672966283151314'/><set value='0.025902718961027'/><set value='0.025132471638902698'/><set value='0.02436222431677841'/><set value='0.02359197699465411'/><set value='0.02282172967252981'/><set value='0.022051482350405507'/><set value='0.021281235028281203'/><set value='0.02378584800716847'/><set value='0.026290460986055735'/><set value='0.028795073964942998'/><set value='0.031299686943830264'/><set value='0.03380429992271753'/><set value='0.036308912901604797'/><set value='0.03881352588049206'/><set value='0.04131813885937933'/><set value='0.043822751838266595'/><set value='0.04632736481715386'/><set value='0.04883197779604113'/><set value='0.05133659077492839'/><set value='0.05384120375381565'/><set value='0.05634581673270292'/><set value='0.058850429711590185'/><set value='0.06135504269047745'/><set value='0.06385965566936472'/><set value='0.06636426864825198'/><set value='0.06886888162713925'/><set value='0.07137349460602652'/><set value='0.07387810758491378'/><set value='0.07638272056380105'/><set value='0.07888733354268831'/><set value='0.08139194652157558'/><set value='0.08389655950046285'/><set value='0.08640117247935011'/><set value='0.08890578545823738'/><set value='0.09141039843712465'/><set value='0.09391501141601191'/><set value='0.09641962439489918'/><set value='0.09892423737378644'/><set value='0.10142885035267371'/><set value='0.10393346333156098'/><set value='0.10643807631044824'/><set value='0.10894268928933551'/><set value='0.11144730226822278'/></dataset><dataset seriesName='Regresion' color='A66EDD' anchorBorderColor='F984A1' anchorBgColor='CCCC00'><set value='0.06307698416483623'/><set value='0.06300234114861122'/><set value='0.06292778646222542'/><set value='0.06285332000115254'/><set value='0.06277894166098993'/><set value='0.06270465133745855'/><set value='0.06263044892640263'/><set value='0.06255633432378976'/><set value='0.062482307425710655'/><set value='0.06240836812837889'/><set value='0.06233451632813094'/><set value='0.062260751921425925'/><set value='0.06218707480484553'/><set value='0.06211348487509374'/><set value='0.062039982028996826'/><set value='0.06196656616350319'/><set value='0.06189323717568309'/><set value='0.06181999496272865'/><set value='0.06174683942195368'/><set value='0.061673770450793405'/><set value='0.06160078794680451'/><set value='0.0615278918076649'/><set value='0.0614550819311735'/><set value='0.06138235821525024'/><set value='0.06130972055793583'/><set value='0.06123716885739166'/><set value='0.061164703011899545'/><set value='0.06109232291986176'/><set value='0.0610200284798008'/><set value='0.06094781959035917'/><set value='0.060875696150299405'/><set value='0.06080365805850378'/><set value='0.06073170521397431'/><set value='0.06065983751583239'/><set value='0.06058805486331892'/><set value='0.060516357155793996'/><set value='0.06044474429273676'/><set value='0.06037321617374536'/><set value='0.060301772698536774'/><set value='0.060230413766946565'/><set value='0.06015913927892891'/><set value='0.060087949134556354'/><set value='0.06001684323401966'/><set value='0.05994582147762773'/><set value='0.05987488376580745'/><set value='0.05980402999910354'/><set value='0.059733260078178335'/><set value='0.059662573903811814'/><set value='0.059591971376901365'/><set value='0.05952145239846156'/><set value='0.0594510168696242'/><set value='0.059380664691638045'/><set value='0.05931039576586875'/><set value='0.059240209993798615'/><set value='0.059170107277026575'/><set value='0.05910008751726804'/><set value='0.059030150616354644'/><set value='0.05896029647623424'/><set value='0.05889052499897074'/><set value='0.05882083608674387'/><set value='0.05875122964184918'/><set value='0.05868170556669784'/><set value='0.05861226376381644'/><set value='0.05854290413584698'/><set value='0.05847362658554665'/><set value='0.058404431015787725'/><set value='0.05833531732955737'/><set value='0.05826628542995761'/><set value='0.058197335220205135'/><set value='0.05812846660363108'/><set value='0.05805967948368107'/><set value='0.05799097376391496'/><set value='0.05792234934800674'/><set value='0.05785380613974432'/><set value='0.057785344043029555'/><set value='0.05771696296187799'/><set value='0.0576486628004187'/><set value='0.05758044346289429'/><set value='0.05751230485366065'/><set value='0.057444246877186814'/><set value='0.05737626943805491'/><set value='0.05730837244096001'/><set value='0.05724055579070986'/><set value='0.05717281939222495'/><set value='0.057105163150538235'/><set value='0.057037586970795105'/><set value='0.05697009075825311'/><set value='0.05690267441828198'/><set value='0.056835337856363445'/><set value='0.05676808097809099'/><set value='0.056700903689169906'/><set value='0.05663380589541704'/><set value='0.056566787502760715'/><set value='0.05649984841724051'/><set value='0.05643298854500725'/><set value='0.056366207792322834'/><set value='0.05629950606556001'/><set value='0.056232883271202384'/><set value='0.056166339315844235'/><set value='0.05609987410619031'/><set value='0.05603348754905581'/><set value='0.05596717955136622'/><set value='0.0559009500201571'/><set value='0.05583479886257409'/><set value='0.055768725985872666'/><set value='0.0557027312974181'/><set value='0.05563681470468522'/><set value='0.055570976115258394'/><set value='0.05550521543683136'/><set value='0.055439532577207025'/><set value='0.055373927444297456'/><set value='0.055308399946123674'/><set value='0.05524294999081558'/><set value='0.055177577486611715'/><set value='0.055112282341859255'/><set value='0.055047064465013874'/><set value='0.054981923764639465'/><set value='0.054916860149408234'/><set value='0.05485187352810042'/><set value='0.054786963809604176'/><set value='0.05472213090291552'/><set value='0.05465737471713816'/><set value='0.05459269516148332'/><set value='0.054528092145269706'/><set value='0.05446356557792331'/><set value='0.054399115368977354'/><set value='0.054334741428072014'/><set value='0.05427044366495448'/><set value='0.05420622198947876'/><set value='0.05414207631160543'/><set value='0.05407800654140171'/><set value='0.05401401258904123'/><set value='0.05395009436480392'/><set value='0.05388625177907581'/><set value='0.053822484742349054'/><set value='0.05375879316522173'/><set value='0.05369517695839764'/><set value='0.05363163603268632'/><set value='0.05356817029900286'/><set value='0.05350477966836768'/><set value='0.0534414640519066'/><set value='0.05337822336085056'/><set value='0.053315057506535535'/><set value='0.053251966400402445'/><set value='0.053188949953997'/><set value='0.05312600807896962'/><set value='0.053063140687075176'/><set value='0.053000347690173075'/><set value='0.052937629000226985'/><set value='0.05287498452930471'/><set value='0.05281241418957817'/><set value='0.052749917893323194'/><set value='0.05268749555291945'/><set value='0.052625147080850206'/><set value='0.05256287238970239'/><set value='0.052500671392166354'/><set value='0.0524385440010357'/><set value='0.05237649012920731'/><set value='0.05231450968968111'/><set value='0.052252602595559944'/><set value='0.05219076876004954'/><set value='0.052129008096458346'/><set value='0.0520673205181973'/><set value='0.0520057059387799'/><set value='0.051944164271821976'/><set value='0.05188269543104158'/><set value='0.0518212993302588'/><set value='0.051759975883395794'/><set value='0.05169872500447657'/><set value='0.0516375466076268'/><set value='0.051576440607073865'/><set value='0.0515154069171466'/><set value='0.05145444545227527'/><set value='0.0513935561269913'/><set value='0.051332738855927346'/><set value='0.05127199355381709'/><set value='0.05121132013549502'/><set value='0.051150718515896504'/><set value='0.05109018861005754'/><set value='0.05102973033311464'/><set value='0.05096934360030478'/><set value='0.05090902832696524'/><set value='0.050848784428533446'/><set value='0.05078861182054693'/><set value='0.050728510418643166'/><set value='0.050668480138559485'/><set value='0.050608520896132846'/><set value='0.0505486326072999'/><set value='0.050488815188096725'/><set value='0.05042906855465879'/><set value='0.05036939262322075'/><set value='0.050309787310116455'/><set value='0.05025025253177873'/><set value='0.05019078820473925'/><set value='0.05013139424562853'/><set value='0.05007207057117574'/><set value='0.05001281709820851'/><set value='0.04995363374365298'/><set value='0.04989452042453357'/><set value='0.04983547705797285'/><set value='0.04977650356119153'/><set value='0.049717599851508254'/><set value='0.04965876584633946'/><set value='0.04960000146319937'/><set value='0.049541306619699826'/><set value='0.0494826812335501'/><set value='0.0494241252225569'/><set value='0.04936563850462418'/><set value='0.04930722099775306'/><set value='0.04924887262004165'/><set value='0.04919059328968502'/><set value='0.04913238292497503'/><set value='0.04907424144430027'/><set value='0.04901616876614581'/><set value='0.04895816480909328'/><set value='0.04890022949182063'/><set value='0.04884236273310199'/><set value='0.04878456445180768'/><set value='0.04872683456690401'/><set value='0.048669172997453145'/><set value='0.048611579662613064'/><set value='0.048554054481637424'/><set value='0.04849659737387537'/><set value='0.04843920825877156'/><set value='0.048381887055865956'/><set value='0.048324633684793686'/><set value='0.048267448065285046'/><set value='0.04821033011716529'/><set value='0.04815327976035455'/><set value='0.04809629691486771'/><set value='0.04803938150081433'/><set value='0.04798253343839848'/><set value='0.04792575264791871'/><set value='0.0478690390497678'/><set value='0.047812392564432815'/><set value='0.04775581311249489'/><set value='0.0476993006146291'/><set value='0.04764285499160444'/><set value='0.04758647616428367'/><set value='0.04753016405362314'/><set value='0.04747391858067279'/><set value='0.04741773966657598'/><set value='0.04736162723256935'/><set value='0.04730558119998278'/><set value='0.04724960149023927'/><set value='0.04719368802485472'/><set value='0.047137840725437974'/><set value='0.04708205951369064'/><set value='0.04702634431140698'/><set value='0.04697069504047374'/><set value='0.04691511162287018'/><set value='0.04685959398066786'/><set value='0.046804142036030554'/><set value='0.046748755711214125'/><set value='0.04669343492856647'/><set value='0.04663817961052739'/><set value='0.04658298967962839'/><set value='0.04652786505849271'/><set value='0.04647280566983519'/><set value='0.04641781143646203'/><set value='0.04636288228127084'/><set value='0.04630801812725048'/><set value='0.046253218897480894'/><set value='0.04619848451513309'/><set value='0.04614381490346899'/><set value='0.04608920998584129'/><set value='0.046034669685693426'/><set value='0.045980193926559415'/><set value='0.04592578263206379'/><set value='0.045871435725921374'/><set value='0.04581715313193736'/><set value='0.04576293477400707'/><set value='0.04570878057611591'/><set value='0.04565469046233917'/><set value='0.045600664356842055'/><set value='0.0455467021838795'/><set value='0.04549280386779604'/><set value='0.045438969333025755'/><set value='0.04538519850409219'/><set value='0.045331491305608124'/><set value='0.045277847662275605'/><set value='0.045224267498885785'/><set value='0.045170750740318774'/><set value='0.04511729731154362'/><set value='0.04506390713761816'/><set value='0.04501058014368886'/><set value='0.044957316254990824'/><set value='0.044904115396847605'/><set value='0.04485097749467116'/><set value='0.04479790247396162'/><set value='0.04474489026030738'/><set value='0.04469194077938484'/><set value='0.04463905395695837'/><set value='0.044586229718880155'/><set value='0.04453346799109015'/><set value='0.044480768699615966'/><set value='0.044428131770572706'/><set value='0.04437555713016293'/><set value='0.04432304470467657'/><set value='0.04427059442049069'/><set value='0.04421820620406954'/><set value='0.04416587998196441'/><set value='0.04411361568081343'/><set value='0.0440614132273416'/><set value='0.04400927254836065'/><set value='0.04395719357076884'/><set value='0.04390517622155101'/><set value='0.04385322042777837'/><set value='0.04380132611660846'/><set value='0.04374949321528496'/><set value='0.043697721651137694'/><set value='0.043646011351582484'/><set value='0.04359436224412105'/><set value='0.04354277425634084'/><set value='0.04349124731591506'/><set value='0.04343978135060251'/><set value='0.04338837628824741'/><set value='0.043337032056779426'/><set value='0.04328574858421352'/><set value='0.04323452579864975'/><set value='0.04318336362827336'/><set value='0.043132262001354546'/><set value='0.043081220846248325'/><set value='0.043030240091394584'/><set value='0.042979319665317865'/><set value='0.04292845949662724'/><set value='0.04287765951401633'/><set value='0.042826919646263104'/><set value='0.04277623982222983'/><set value='0.042725619970862924'/><set value='0.04267506002119292'/><set value='0.04262455990233432'/><set value='0.04257411954348553'/><set value='0.042523738873928685'/><set value='0.04247341782302966'/><set value='0.04242315632023791'/><set value='0.04237295429508633'/><set value='0.042322811677191256'/><set value='0.04227272839625232'/><set value='0.04222270438205227'/><set value='0.042172739564457024'/><set value='0.04212283387341548'/><set value='0.04207298723895937'/><set value='0.04202319959120331'/><set value='0.04197347086034459'/><set value='0.04192380097666305'/><set value='0.04187418987052109'/><set value='0.041824637472363514'/><set value='0.04177514371271742'/><set value='0.041725708522192095'/><set value='0.04167633183147898'/><set value='0.04162701357135152'/><set value='0.0415777536726651'/><set value='0.04152855206635686'/><set value='0.04147940868344574'/><set value='0.041430323455032295'/><set value='0.04138129631229857'/><set value='0.04133232718650809'/><set value='0.04128341600900572'/><set value='0.041234562711217546'/><set value='0.0411857672246508'/><set value='0.04113702948089382'/><set value='0.041088349411615814'/><set value='0.041039726948566904'/><set value='0.040991162023578'/><set value='0.040942654568560596'/><set value='0.04089420451550684'/><set value='0.04084581179648935'/><set value='0.040797476343661064'/><set value='0.04074919808925526'/><set value='0.04070097696558542'/><set value='0.040652812905045084'/><set value='0.040604705840107835'/><set value='0.04055665570332711'/><set value='0.04050866242733621'/><set value='0.040460725944848154'/><set value='0.04041284618865554'/><set value='0.04036502309163053'/><set value='0.04031725658672475'/><set value='0.0402695466069691'/><set value='0.04022189308547378'/><set value='0.04017429595542815'/><set value='0.04012675515010059'/><set value='0.040079270602838474'/><set value='0.040031842247068065'/><set value='0.03998447001629436'/><set value='0.039937153844101084'/><set value='0.03988989366415057'/><set value='0.039842689410183596'/><set value='0.039795541016019384'/><set value='0.03974844841555547'/><set value='0.03970141154276763'/><set value='0.03965443033170975'/><set value='0.03960750471651373'/><set value='0.039560634631389466'/><set value='0.039513820010624694'/><set value='0.03946706078858486'/><set value='0.03942035689971316'/><set value='0.03937370827853033'/><set value='0.039327114859634554'/><set value='0.03928057657770148'/><set value='0.03923409336748403'/><set value='0.039187665163812294'/><set value='0.039141291901593545'/><set value='0.03909497351581208'/><set value='0.039048709941529064'/><set value='0.03900250111388259'/><set value='0.03895634696808749'/><set value='0.03891024743943519'/><set value='0.038864202463293786'/><set value='0.03881821197510779'/><set value='0.038772275910398155'/><set value='0.038726394204762114'/><set value='0.03868056679387308'/><set value='0.03863479361348063'/><set value='0.03858907459941038'/><set value='0.03854340968756384'/><set value='0.03849779881391841'/><set value='0.038452241914527265'/><set value='0.038406738925519185'/><set value='0.03836128978309861'/><set value='0.038315894423545445'/><set value='0.038270552783214964'/><set value='0.0382252647985378'/><set value='0.03818003040601982'/><set value='0.03813484954224197'/><set value='0.038089722143860305'/><set value='0.03804464814760582'/><set value='0.037999627490284335'/><set value='0.03795466010877652'/><set value='0.0379097459400377'/><set value='0.037864884921097824'/><set value='0.037820076989061355'/><set value='0.037775322081107135'/><set value='0.03773062013448842'/><set value='0.037685971086532696'/><set value='0.03764137487464157'/><set value='0.03759683143629078'/><set value='0.03755234070903003'/><set value='0.03750790263048291'/><set value='0.037463517138346863'/><set value='0.03741918417039304'/><set value='0.0373749036644662'/><set value='0.0373306755584847'/><set value='0.03728649979044035'/><set value='0.03724237629839832'/><set value='0.037198305020497084'/><set value='0.03715428589494835'/><set value='0.037110318860036864'/><set value='0.03706640385412049'/><set value='0.03702254081562999'/><set value='0.036978729683069'/><set value='0.036934970395013936'/><set value='0.036891262890113866'/><set value='0.036847607107090494'/><set value='0.03680400298473804'/><set value='0.03676045046192311'/><set value='0.03671694947758469'/><set value='0.03667349997073405'/><set value='0.03663010188045455'/><set value='0.03658675514590169'/><set value='0.03654345970630301'/><set value='0.03650021550095787'/><set value='0.03645702246923752'/><set value='0.03641388055058498'/><set value='0.03637078968451487'/><set value='0.036327749810613416'/><set value='0.03628476086853835'/><set value='0.036241822798018776'/><set value='0.03619893553885515'/><set value='0.03615609903091915'/><set value='0.03611331321415362'/><set value='0.036070578028572484'/><set value='0.03602789341426059'/><set value='0.035985259311373766'/><set value='0.03594267566013862'/><set value='0.03590014240085248'/><set value='0.03585765947388334'/><set value='0.035815226819669796'/><set value='0.035772844378720844'/><set value='0.03573051209161596'/><set value='0.03568822989900489'/><set value='0.03564599774160763'/><set value='0.035603815560214316'/><set value='0.035561683295685174'/><set value='0.03551960088895037'/><set value='0.03547756828101001'/><set value='0.035435585412934026'/><set value='0.035393652225862035'/><set value='0.03535176866100335'/><set value='0.03530993465963684'/><set value='0.03526815016311087'/><set value='0.03522641511284322'/><set value='0.03518472945032095'/><set value='0.03514309311710041'/><set value='0.03510150605480711'/><set value='0.035059968205135586'/><set value='0.03501847950984942'/><set value='0.03497703991078111'/><set value='0.03493564934983195'/><set value='0.034894307768972004'/><set value='0.03485301511024005'/><set value='0.03481177131574336'/><set value='0.0347705763276578'/><set value='0.03472943008822765'/><set value='0.034688332539765485'/><set value='0.034647283624652185'/><set value='0.03460628328533683'/><set value='0.03456533146433656'/><set value='0.03452442810423656'/><set value='0.03448357314768996'/><set value='0.03444276653741776'/><set value='0.03440200821620874'/><set value='0.03436129812691935'/><set value='0.034320636212473686'/><set value='0.034280022415863406'/><set value='0.034239456680147586'/><set value='0.03419893894845271'/><set value='0.03415846916397258'/><set value='0.03411804726996817'/><set value='0.03407767320976765'/><set value='0.03403734692676623'/><set value='0.03399706836442608'/><set value='0.03395683746627632'/><set value='0.033916654175912886'/><set value='0.033876518436998425'/><set value='0.03383643019326229'/><set value='0.03379638938850041'/><set value='0.03375639596657521'/><set value='0.03371644987141557'/><set value='0.0336765510470167'/><set value='0.03363669943744012'/><set value='0.03359689498681351'/></dataset></graph>");
				
				<!-- Finally, render the chart.-->
				chart_1Coef.render("1CoefDiv");
			</script>
			<!--END Script Block for Chart 1Coef -->

		
		
		
	
		
	

</div>





</div>
</div>
</body>

</html>
