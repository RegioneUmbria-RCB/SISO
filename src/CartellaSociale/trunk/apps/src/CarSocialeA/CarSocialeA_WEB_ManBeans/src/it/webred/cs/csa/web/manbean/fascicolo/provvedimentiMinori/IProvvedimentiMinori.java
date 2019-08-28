package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori;

import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.DatiProvvedimentoMan;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.IntSpecialistiTempiChiusureMan;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.InterventiTutelaMan;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.PrescrizioniSocioEducativeMan;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.PrescrizioniSpecialisticheMan;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.ISchedaValutazione;

public interface IProvvedimentiMinori extends ISchedaValutazione {

	public DatiProvvedimentoMan getDatiProv();

	public PrescrizioniSpecialisticheMan getPrescSpec();

	public PrescrizioniSocioEducativeMan getPrescSocEd();

	public InterventiTutelaMan getInterTutela();

	public IntSpecialistiTempiChiusureMan getIntSpecTemp();
  
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto);
	
	public void valorizzaRowList(ProvvedimentiMinoriRowBean row);

	public void initRowList(CsDValutazione parent, CsDValutazione scheda);
	

}
