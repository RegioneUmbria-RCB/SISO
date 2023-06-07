package it.webred.cs.json.provvedimentiMinori;

import java.util.Date;

import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.provvedimentiMinori.tabs.IDatiProvvedimento;
import it.webred.cs.json.provvedimentiMinori.tabs.IIntSpecialistiTempiChiusure;
import it.webred.cs.json.provvedimentiMinori.tabs.IInterventiTutela;
import it.webred.cs.json.provvedimentiMinori.tabs.IPrescrizioniSocioEducative;
import it.webred.cs.json.provvedimentiMinori.tabs.IPrescrizioniSpecialistiche;

public interface IProvvedimentiMinori extends ISchedaValutazione {

	public IDatiProvvedimento getDatiProv();

	public IPrescrizioniSpecialistiche getPrescSpec();

	public IPrescrizioniSocioEducative getPrescSocEd();

	public IInterventiTutela getInterTutela();

	public IIntSpecialistiTempiChiusure getIntSpecTemp();
  
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto);
	
	public void valorizzaRowList(ProvvedimentiMinoriRowBean row);

	public void initRowList(CsDValutazione parent, CsDValutazione scheda);
	
	/*Servono per il TaskGiornaliero*/
	
	public void initRowListTask(CsDValutazione parent, CsDValutazione scheda);
	
	public Date getScadenzaAdempimento();
	
	public Date getScadenzaAggiornamento();

}
