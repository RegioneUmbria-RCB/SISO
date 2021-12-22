package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.ExportValutazioneSinbaDAO;
import it.webred.cs.csa.ejb.dao.SinbaDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDSinba;
import it.webred.cs.data.model.CsIIntervento;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableSinbaSessionBean extends CarSocialeBaseSessionBean implements AccessTableSinbaSessionBeanRemote {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SinbaDAO sinbaDao;
	
	@Autowired
	private ExportValutazioneSinbaDAO csipsDao;
	
	@EJB
	public AccessTableOperatoreSessionBeanRemote operatoreSessionBean;
	@EJB
	public AccessTableDiarioSessionBeanRemote diarioSessionBean;
	@EJB
	public AccessTableCasoSessionBeanRemote casoSessionBean;
	@EJB
	public AccessTableSoggettoSessionBeanRemote soggettoSessionBean;
	@EJB
	private AccessTableAlertSessionBeanRemote alertService;

	protected SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

	private void valorizzaDettaglioInterventoRif(CsIIntervento inter) {
		if (inter.getCsIInterventoCustom() != null) {
			switch (inter.getCsIInterventoCustom().getId().intValue()) {
			case 1:
				if (inter.getCsIVouchersad() != null
						&& !inter.getCsIVouchersad().isEmpty())
					inter.getCsIVouchersad().iterator().next()
							.setCsIIntervento(inter);
				break;
			case 2:
				if (inter.getCsIContrEco() != null
						&& !inter.getCsIContrEco().isEmpty())
					inter.getCsIContrEco().iterator().next()
							.setCsIIntervento(inter);
				break;
			case 3:
				if (inter.getCsICentrod() != null
						&& !inter.getCsICentrod().isEmpty())
					inter.getCsICentrod().iterator().next()
							.setCsIIntervento(inter);
				break;
			case 4:
				if (inter.getCsIPasti() != null
						&& !inter.getCsIPasti().isEmpty())
					inter.getCsIPasti().iterator().next()
							.setCsIIntervento(inter);
				break;
			case 5:
				if (inter.getCsIBuonoSoc() != null
						&& !inter.getCsIBuonoSoc().isEmpty())
					inter.getCsIBuonoSoc().iterator().next()
							.setCsIIntervento(inter);
				break;
			case 6:
				if (inter.getCsIResiAdulti() != null
						&& !inter.getCsIResiAdulti().isEmpty())
					inter.getCsIResiAdulti().iterator().next()
							.setCsIIntervento(inter);
				break;
			case 7:
				if (inter.getCsIResiMinore() != null
						&& !inter.getCsIResiMinore().isEmpty())
					inter.getCsIResiMinore().iterator().next()
							.setCsIIntervento(inter);
				break;
			case 8:
				if (inter.getCsIAffidoFam() != null
						&& !inter.getCsIAffidoFam().isEmpty())
					inter.getCsIAffidoFam().iterator().next()
							.setCsIIntervento(inter);
				break;
			case 9:
				if (inter.getCsIAdmAdh() != null
						&& !inter.getCsIAdmAdh().isEmpty())
					inter.getCsIAdmAdh().iterator().next()
							.setCsIIntervento(inter);
				break;
			case 10:
				if (inter.getCsISemiResiMin() != null
						&& !inter.getCsISemiResiMin().isEmpty())
					inter.getCsISemiResiMin().iterator().next()
							.setCsIIntervento(inter);
				break;
			case 11:
				if (inter.getCsISchedaLavoro() != null
						&& !inter.getCsISchedaLavoro().isEmpty())
					inter.getCsISchedaLavoro().iterator().next()
							.setCsIIntervento(inter);
				break;
			}
		}
	}

	@Override
	public CsDSinba salvaSchedaSinba(BaseDTO dto) throws Exception {

		CsDSinba schedaSinba = null;

		try {
			CsDSinba sinba = (CsDSinba) dto.getObj();
			boolean nuovaScheda = sinba.getDiarioId()==null || sinba.getDiarioId()==0;
			
			schedaSinba = sinbaDao.saveSchedaSinba((CsDSinba) dto.getObj());
			
			if(nuovaScheda){
				dto.setObj(sinba.getCsDDiario().getCsACaso().getCsASoggetto());
				dto.setObj2(sinba.getCsDDiario().getCsOOperatoreSettore());
				dto.setObj3(DataModelCostanti.TipiAlertCod.SINBA);
				dto.setObj4("una nuova scheda di valutazione Sin.Ba.");
				
				alertService.addAlertNuovoInserimentoToResponsabileCaso(dto);
			}
			
		} catch (Exception t) {
			logger.error(t);
			throw t;
		}

		return schedaSinba;
	}

	@Override
	public CsDSinba getSchedaSinbaById(BaseDTO dto) throws Exception {
		return sinbaDao.getSchedaSinbaById((Long) dto.getObj());
	}

	@Override
	public List<CsDSinba> getListaSchedaSinbaByCaso(BaseDTO dto)throws Exception {
		return sinbaDao.getListaSchedaSinbaByIdCaso((Long) dto.getObj());
	}

	@Override
	public void deleteSchedaSinba(BaseDTO b) throws Exception {
		sinbaDao.deleteSchedaSinba((Long) b.getObj());
	}

	@Override
	public List<String> findCodiciPrestazione(BaseDTO b) {
		return csipsDao.findCodiciPrestazione((String)b.getObj(), (String)b.getObj2(),(String)b.getObj3(), (String)b.getObj4());
	}

}
