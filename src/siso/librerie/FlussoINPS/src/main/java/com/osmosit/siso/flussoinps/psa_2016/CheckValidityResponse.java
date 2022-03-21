package com.osmosit.siso.flussoinps.psa_2016;

import java.util.ArrayList;
import java.util.List;

public class CheckValidityResponse { 
	private boolean isValid = true;
	private List<String> errorMessage = new ArrayList<String>();

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public List<String> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(List<String> errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void check(boolean b, String string) {
		isValid &= b;
		if (!b) {
			errorMessage.add("Errore di validità su campo " + string);
		}
		
	}
	
	
}
