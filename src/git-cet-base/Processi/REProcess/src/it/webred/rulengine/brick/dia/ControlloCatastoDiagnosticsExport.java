package it.webred.rulengine.brick.dia;

import java.util.Properties;

import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.superc.dia.AbstractDiagnosticsExport;
import it.webred.rulengine.impl.bean.BeanCommand;

import org.apache.log4j.Logger;

public class ControlloCatastoDiagnosticsExport extends AbstractDiagnosticsExport implements Rule {

	{
		nomi = new String[] {"D_CTR_CAT"};
		log = Logger.getLogger(ControlloCatastoDiagnosticsExport.class.getName());
	}

	public ControlloCatastoDiagnosticsExport(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}
	
	
}
