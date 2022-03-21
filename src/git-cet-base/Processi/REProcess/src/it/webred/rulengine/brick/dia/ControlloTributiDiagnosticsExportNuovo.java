package it.webred.rulengine.brick.dia;

import java.util.Properties;

import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.superc.dia.AbstractDiagnosticsExport;
import it.webred.rulengine.impl.bean.BeanCommand;

import org.apache.log4j.Logger;

public class ControlloTributiDiagnosticsExportNuovo extends AbstractDiagnosticsExport implements Rule {

	{
		nomi = new String[] {"D_CTR_TRI2"};
		log = Logger.getLogger(ControlloTributiDiagnosticsExportNuovo.class.getName());
	}

	public ControlloTributiDiagnosticsExportNuovo(BeanCommand bc,
			Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}
	
	
	
}
