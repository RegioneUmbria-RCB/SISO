package it.webred.rulengine.diagnostics.bean;

import java.util.ArrayList;
import java.util.List;

public class ListDiagnostics {

	private List<DiagnosticConfigBean> diagnostics;

	
//	public void setDiagnostics(List<DiagnosticConfigBean> diagnostics) {
//		this.diagnostics = diagnostics;
//	}

	public List<DiagnosticConfigBean> getDiagnostics() {
		return diagnostics;
	}
	
	public boolean isThereDiagnosticToExecute(){
		if ((diagnostics == null) || (diagnostics.size() == 0))	return false;
		
		for (DiagnosticConfigBean objDia : diagnostics) {
			if (objDia.isExecute()) return true;
		}
		
		return false;
	}
	
	public void addDiagnostica(DiagnosticConfigBean obj){
		if (diagnostics == null) diagnostics = new ArrayList<DiagnosticConfigBean>();
		diagnostics.add(obj);
	}
}
