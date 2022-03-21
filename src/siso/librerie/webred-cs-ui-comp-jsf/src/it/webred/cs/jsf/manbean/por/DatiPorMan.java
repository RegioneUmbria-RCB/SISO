package it.webred.cs.jsf.manbean.por;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsExtraFseDatiLavoro;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class DatiPorMan extends DatiPorBaseMan {

	private static final long serialVersionUID = 1L;

	public DatiPorMan(){}

	public DatiPorMan(String codEnte, Long casoId, CsExtraFseDatiLavoro csCDatiLavoro, Long datiSocialiId, BigDecimal idCondLavoro, CsTbGVulnerabile gVulnerabile) {
		logger.info("Costruttore DatiPorMan IDCD:"+ datiSocialiId);
		this.belfiore = codEnte;
		if(datiSocialiId!=null) {
			setIdXStampa(datiSocialiId);
		}
		
		CsOOperatoreSettore opSettore = CsUiCompBaseBean.getCurrentOpSettore();

		this.csCDatiLavoro = csCDatiLavoro != null ? csCDatiLavoro : new CsExtraFseDatiLavoro();
		
		changeGruppoVulnerabile(gVulnerabile);
		
		this.csCDatiLavoro.getMaster().setCasoId(casoId);
		this.csCDatiLavoro.getMaster().setTipo(DataModelCostanti.TipoPOR.PRESA_IN_CARICO);

		this.csCDatiLavoro.getMaster().setOperatoreId(opSettore.getCsOOperatore().getId());
		this.csCDatiLavoro.getMaster().setOrganizzazioneId(opSettore.getCsOSettore().getCsOOrganizzazione().getId());
		
		this.initDatiProgetto(isVisualizzaModuloPorCs());
		
		if(idCondLavoro!=null) this.impostaCondizioneLavorativa(idCondLavoro);
	}

}
