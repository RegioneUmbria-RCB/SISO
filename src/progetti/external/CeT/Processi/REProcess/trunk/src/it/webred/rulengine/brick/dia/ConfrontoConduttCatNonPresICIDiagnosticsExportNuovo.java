package it.webred.rulengine.brick.dia;

import java.util.Properties;

import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.superc.dia.AbstractDiagnosticsExport;
import it.webred.rulengine.impl.bean.BeanCommand;

import org.apache.log4j.Logger;

public class ConfrontoConduttCatNonPresICIDiagnosticsExportNuovo extends AbstractDiagnosticsExport implements Rule {

	{
		ids = new int[] {1303};
		log = Logger.getLogger(ConfrontoConduttCatNonPresICIDiagnosticsExportNuovo.class.getName());
	}
	

	public ConfrontoConduttCatNonPresICIDiagnosticsExportNuovo(BeanCommand bc,
			Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}
	
	
}
