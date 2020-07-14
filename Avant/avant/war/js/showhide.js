function showhide(divid)
{
	if(document.getElementById(divid).style.display == ('none' || ''))
	{
		//alert(document.getElementById(divid).nodeName);
		if(document.getElementById(divid).nodeName=="TR")
		{
			document.getElementById(divid).style.display = "";
		}
		else
		{
		document.getElementById(divid).style.display = 'block';
		}
	}
	else
	{
		document.getElementById(divid).style.display = 'none';
	}
}