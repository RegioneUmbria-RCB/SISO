package it.webred.mui.model;

import it.webred.mui.consolidation.DapManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MiConsOggetto extends MiConsOggettoBase {
	private List<CodiceViaCivico> codiciViaCivici = new ArrayList<CodiceViaCivico>();

	public Iterator<CodiceViaCivico> getCodiceViaCivicos() {
		return codiciViaCivici.iterator();
	}

	public void addCodiceViaCivico(CodiceViaCivico codiceViaCivico) {
		codiciViaCivici.add(codiceViaCivico);
	}
	public void addCodiceViaCivico(String codiceVia,String numeroCivico) {
		CodiceViaCivico cvc= new CodiceViaCivico();
		cvc.setCivico(numeroCivico);
		cvc.setCodiceVia(codiceVia);
		addCodiceViaCivico(cvc);
	}
	public boolean isAbitativo(){
		return DapManager.isTipologiaImmobileAbitativo(getCategoria());
	}

//	public boolean equals(Object ogg){
//		return equals((MiConsOggetto) ogg);
//	}
//	public boolean equals(MiConsOggetto ogg){
//		return (getNumeroCivico().equals(ogg.getCodiceVia())&&getCodiceVia().equals(ogg.getCodiceVia()));
//	}
}
