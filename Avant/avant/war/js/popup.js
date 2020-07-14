function Popup(){
	this.enablePopup = true;
	this.show = show;
	this.enable = enable;
	this.disable = disable;
}
function show(llamada){
	if(this.enablePopup == true)
		window.open(llamada , 'window' , 'fullscreen=no , toolbar=no , location=no , status=no , menubar=no , scrollbars=yes , resizable=yes , width=800 , height=600');
}
function enable(){
	this.enablePopup = true;
}
function disable(){
	this.enablePopup = false;
}