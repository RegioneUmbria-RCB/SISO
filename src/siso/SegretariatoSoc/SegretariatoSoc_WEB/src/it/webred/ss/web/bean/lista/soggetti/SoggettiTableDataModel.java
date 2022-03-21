package it.webred.ss.web.bean.lista.soggetti;

import it.webred.ss.web.bean.util.Soggetto;
import it.webred.ss.web.bean.wizard.Anagrafica;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SelectableDataModel;

public class SoggettiTableDataModel extends ListDataModel<Soggetto> implements SelectableDataModel<Soggetto> {

	public SoggettiTableDataModel(List<Soggetto> soggetti){
		super(soggetti);
	}
	
	@Override
	public Soggetto getRowData(String arg) {
		List<Soggetto> soggetti = (List<Soggetto>) getWrappedData();
		for(Soggetto s: soggetti)
			if(arg.equals(getRowKey(s)))
				return s;
		
		return null;
	}

//	@Override
//	public Object getRowKey(Soggetto arg) {
//		return arg.getCf();
//	}
	
	@Override
	public Object getRowKey(Soggetto arg){
		return arg.getSoggettoKey();
		/*System.out.println("getRowKey["+arg.getCf()+"]["+arg.getId()+"][]");
		if (arg.isAnonimo())
			return arg.getAlias();
		else if(StringUtils.isBlank(arg.getCf()))
			return arg.getCf();
		else return arg.getId();*/
	}
	
}
