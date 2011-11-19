function navigation(event) {
	var keycode;
	if (event == null) {
		// IE
        keycode = window.event.keyCode;
	} else {
    	// Firefox
    	keycode = event.which;
	}
	var id;
	if (37 == keycode) {
		id = "nav-previous";
	}
	else 
	if (39 == keycode) {
		id = "nav-next";
	}
	if (id != null) {
      	var link = document.getElementById(id);
       	if (link != null) {
           	window.location = link.href;
        	return false;
        }
	}
}

// Firefox requires this :-(
document.onkeydown = navigation;
