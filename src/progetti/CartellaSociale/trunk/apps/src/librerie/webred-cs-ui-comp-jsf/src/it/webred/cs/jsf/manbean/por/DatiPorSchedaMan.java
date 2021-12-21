package it.webred.cs.jsf.manbean.por;

import it.webred.cs.data.model.CsExtraFseDatiLavoro;
import it.webred.cs.data.model.CsExtraFseMast;

import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class DatiPorSchedaMan extends DatiPorBaseMan {

	private static final long serialVersionUID = 3932330281946426754L;
	
	public DatiPorSchedaMan(){
		super();
	}

	public DatiPorSchedaMan(CsExtraFseDatiLavoro datiLavoro, String belfiore, BigDecimal idCodLav) {
		this.belfiore = belfiore;
		this.init(datiLavoro);
		if(idCodLav!=null)this.impostaCondizioneLavorativa(idCodLav);
	}
	public DatiPorSchedaMan(String belfiore, BigDecimal idCodLav) {
		this.belfiore = belfiore;
		this.init(null);
		if(idCodLav!=null)this.impostaCondizioneLavorativa(idCodLav);
	}
	public void init(CsExtraFseDatiLavoro datiLavoro) {
		this.csCDatiLavoro = datiLavoro != null ? datiLavoro : new CsExtraFseDatiLavoro();

		if(csCDatiLavoro.getMaster()==null){
			CsExtraFseMast master = new CsExtraFseMast();
			csCDatiLavoro.setMaster(master);
		}
		
		this.initDatiProgetto();
	}

}
