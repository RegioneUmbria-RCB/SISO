package it.webred.rulengine.diagnostics;

import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Rule;
import it.webred.rulengine.diagnostics.superclass.AbstractDiagnostics;
import it.webred.rulengine.impl.bean.BeanCommand;

public class DiagnosticaNonStandard extends AbstractDiagnostics implements Rule {

	{			
		isDiaStandard = false;
		log = Logger.getLogger(DiagnosticaNonStandard.class.getName());
	}
	
	public DiagnosticaNonStandard(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub			
	}
	

}
