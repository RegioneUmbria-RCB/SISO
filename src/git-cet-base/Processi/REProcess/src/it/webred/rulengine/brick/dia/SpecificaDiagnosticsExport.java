package it.webred.rulengine.brick.dia;

import java.util.Properties;

import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.superc.dia.AbstractDiagnosticsExport;
import it.webred.rulengine.impl.bean.BeanCommand;

import org.apache.log4j.Logger;

public class SpecificaDiagnosticsExport extends AbstractDiagnosticsExport implements Rule {

	{
		//l'id viene passato come parametro alla regola
		log = Logger.getLogger(SpecificaDiagnosticsExport.class.getName());
	}
	
	/*
	public SpecificaDiagnosticsExport(BeanCommand bc) {		
		super(bc);
	}
	*/
	
	public SpecificaDiagnosticsExport(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}
	
	
}
