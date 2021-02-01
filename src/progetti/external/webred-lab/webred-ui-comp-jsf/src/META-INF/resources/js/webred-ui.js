function isEnter(evtKeyPress) {
  var nTecla = evtKeyPress.keyCode;
  if (nTecla == 13) {
	  return true;
  } else {
	  return false;
  }
}

function resetValue() {
	alert('resetComune');
}

function isControl(e) {
	// ctrl
    if (e.keyCode == 17) {
        	return true;
    } else {
    	return false;
    }
}

function isEnter2(e) {
	// ctrl-enter
    if (e.keyCode == 10) {
        	return true;
    } else {
    	return false;
    }
}

