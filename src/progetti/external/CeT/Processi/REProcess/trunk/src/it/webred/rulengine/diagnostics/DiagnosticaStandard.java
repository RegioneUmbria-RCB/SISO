package it.webred.rulengine.diagnostics;

import java.util.Properties;
import org.apache.log4j.Logger;
import it.webred.rulengine.Rule;
import it.webred.rulengine.diagnostics.superclass.AbstractDiagnostics;
import it.webred.rulengine.impl.bean.BeanCommand;

public class DiagnosticaStandard extends AbstractDiagnostics implements Rule {

	{		
		log = Logger.getLogger(DiagnosticaStandard.class.getName());
	}
	
	public DiagnosticaStandard(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

}
