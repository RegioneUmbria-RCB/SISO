function setTxtUnicoDenom(suggestionBox){
	var items = suggestionBox.getSelectedItems();
	if (items[0] != null && items[0].denominazione != null) {
		 document.getElementById("frmRicerca:txtUnicoDenom").value = items[0].denominazioneExt;
		 document.getElementById("frmRicerca:txtUnicoCodFis").value = items[0].codfisc;
		 document.getElementById("frmRicerca:txtUnicoPIva").value = items[0].pi;
	} else {
		 document.getElementById("frmRicerca:txtUnicoCodFis").value = '';
		 document.getElementById("frmRicerca:txtUnicoPIva").value = '';
	}
}

function setTxtUnicoCF(suggestionBox){
	var items = suggestionBox.getSelectedItems();
	if (items[0] != null && items[0].codfisc != null) {
		 document.getElementById("frmRicerca:txtUnicoDenom").value = items[0].denominazioneExt;
		 document.getElementById("frmRicerca:txtUnicoCodFis").value = items[0].codfisc;
		 document.getElementById("frmRicerca:txtUnicoPIva").value = items[0].pi;
	} else {
		 document.getElementById("frmRicerca:txtUnicoDenom").value = '';
		 document.getElementById("frmRicerca:txtUnicoPIva").value = '';
	}
}

function setTxtUnicoPI(suggestionBox){
	var items = suggestionBox.getSelectedItems();
	if (items[0] != null && items[0].pi != null) {
		 document.getElementById("frmRicerca:txtUnicoDenom").value = items[0].denominazioneExt;
		 document.getElementById("frmRicerca:txtUnicoCodFis").value = items[0].codfisc;
		 document.getElementById("frmRicerca:txtUnicoPIva").value = items[0].pi;
	} else {
		 document.getElementById("frmRicerca:txtUnicoDenom").value = '';
		 document.getElementById("frmRicerca:txtUnicoCodFis").value = '';
	}
}

/*function setTxtModalDenom(suggestionBox){
	var items = suggestionBox.getSelectedItems();
	if (items[0] != null && items[0].denominazione != null) {
		 document.getElementById("txtModalDenom").value = items[0].denominazione;
		 document.getElementById("txtModalCodFis").value = items[0].codfisc;
	} else {
		 document.getElementById("txtModalCodFis").value = '';
	}
}

function setTxtModalCF(suggestionBox){
	var items = suggestionBox.getSelectedItems();
	if (items[0] != null && items[0].codfisc != null) {
		 document.getElementById("frmModal:txtModalDenom").value = items[0].denominazione;
		 document.getElementById("frmModal:txtModalCodFis").value = items[0].codfisc;
	} else {
		 document.getElementById("frmModal:txtModalDenom").value = '';
	}
}*/

function setTxtTotaleDenom(suggestionBox){
	var items = suggestionBox.getSelectedItems();
	if (items[0] != null && items[0].denominazione != null) {
		 document.getElementById("frmRicerca:txtTotaleDenom").value = items[0].denominazione;
		 document.getElementById("frmRicerca:txtTotaleCodFis").value = items[0].codFis;
		 document.getElementById("frmRicerca:txtTotalePIva").value = items[0].pi;
	} else {
		 document.getElementById("frmRicerca:txtTotaleCodFis").value = '';
	}
}

function setTxtTotaleCF(suggestionBox){
	var items = suggestionBox.getSelectedItems();
	if (items[0] != null && items[0].codfisc != null) {
		 document.getElementById("frmRicerca:txtTotaleDenom").value = items[0].denominazione;
		 document.getElementById("frmRicerca:txtTotaleCodFis").value = items[0].codFis;
		 document.getElementById("frmRicerca:txtTotalePIva").value = items[0].pi;
	} else {
		 document.getElementById("frmRicerca:txtTotaleDenom").value = '';
	}
}

function setTxtTotalePI(suggestionBox){
	var items = suggestionBox.getSelectedItems();
	if (items[0] != null && items[0].pi != null) {
		 document.getElementById("frmRicerca:txtTotaleDenom").value = items[0].denominazione;
		 document.getElementById("frmRicerca:txtTotaleCodFis").value = items[0].codfisc;
		 document.getElementById("frmRicerca:txtTotalePIva").value = items[0].pi;
	} else {
		 document.getElementById("frmRicerca:txtTotaleDenom").value = '';
		 document.getElementById("frmRicerca:txtTotaleCodFis").value = '';
	}
}

/*function setTxtFoglio(suggestionBox){
	var items = suggestionBox.getSelectedItems();
	if (items[0] != null && items[0].foglio != null) {
		 document.getElementById("frmRicerca:txtFoglio").value = items[0].foglio;
		 document.getElementById("frmRicerca:txtParticella").value = items[0].particella;
	} else {
		 document.getElementById("frmRicerca:txtParticella").value = '';
	}
}

function setTxtParticella(suggestionBox){
	var items = suggestionBox.getSelectedItems();
	if (items[0] != null && items[0].particella != null) {
		 document.getElementById("frmRicerca:txtFoglio").value = items[0].foglio;
		 document.getElementById("frmRicerca:txtParticella").value = items[0].particella;
	} else {
		 document.getElementById("frmRicerca:txtFoglio").value = '';
	}
}*/