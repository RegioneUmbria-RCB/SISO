package it.webred.rulengine.brick.dia;

import java.util.Properties;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.superc.dia.AbstractDiagnosticsExport;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import org.apache.log4j.Logger;

public class ControlloDemografiaDiagnosticsExport extends AbstractDiagnosticsExport implements Rule {

	{
		nomi = new String[] {"D_CTR_DEM"};
		log = Logger.getLogger(ControlloDemografiaDiagnosticsExport.class.getName());
	}

	public ControlloDemografiaDiagnosticsExport(BeanCommand bc,
			Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	
	
}
