package it.webred.cs.json.isee;

import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.jsf.manbean.ProtocolloDsuMan;
import it.webred.cs.json.ISchedaValutazione;

public interface IIseeJson extends ISchedaValutazione {

	public String fillReportIsee();

	public void init(CsDIsee jpa); //Valido solo per VER.1

	public ProtocolloDsuMan getProtDsuMan();

	public void onChangeAnnoRiferimento();

	public void onChangeTipologia();

	
}
