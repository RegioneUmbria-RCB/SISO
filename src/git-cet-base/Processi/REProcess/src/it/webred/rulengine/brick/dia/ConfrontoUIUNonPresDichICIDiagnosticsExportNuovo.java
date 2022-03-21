package it.webred.rulengine.brick.dia;

import java.util.Properties;

import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.superc.dia.AbstractDiagnosticsExport;
import it.webred.rulengine.impl.bean.BeanCommand;

import org.apache.log4j.Logger;

public class ConfrontoUIUNonPresDichICIDiagnosticsExportNuovo extends AbstractDiagnosticsExport implements Rule {

	{
		ids = new int[] {1302};
		log = Logger.getLogger(ConfrontoUIUNonPresDichICIDiagnosticsExportNuovo.class.getName());
	}

	public ConfrontoUIUNonPresDichICIDiagnosticsExportNuovo(BeanCommand bc,
			Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}
	
	
}
