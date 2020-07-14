function color(td){
	td.className += 'Hilite';
}
function uncolor(td){
	li = td.className.lastIndexOf('Hilite');
	td.className = td.className.substr(0,li);
}