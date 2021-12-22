package it.webred.cs.json.barthel.ver1;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.barthel.ManSchedaBarthelBaseBean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

public class SchedaBarthelManBean extends ManSchedaBarthelBaseBean {

	private static final long serialVersionUID = 1L;

	private SchedaBarthelController controller;
	
	public SchedaBarthelManBean() {
		controller = new SchedaBarthelController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
	}
	
	@Override
	public  boolean validaData ( ) {

		boolean validazioneOk = true;
		
		List<String> messagges;
		
		messagges = controller.validaBarthelData(); 
		if( messagges.size() > 0 ) {
			addWarning("Attenzione! Campi mancanti nella compilazione della Dati BARTHEL: ", messagges);
			validazioneOk &= false;
		}

		messagges = controller.validaIADLData();
		if( messagges.size() > 0 ) {
			addWarning("Attenzione! Campi mancanti nella compilazione dei dati IADL: ", messagges);
			validazioneOk &= false;
		}

		messagges = controller.validaPatologiePsichiatricheData();
		if( messagges.size() > 0 ) {
			addWarning("Attenzione! Campi mancanti nella compilazione dei dati Patologie Psichiche: ", messagges);
			validazioneOk &= false;
		}
		
		return validazioneOk;
	}
	
	@Override
	public boolean save(Long visSecondoLivello){
		this.controller.setVisSecondoLivello(visSecondoLivello);
		return this.save();
	}
	
	@Override
	public boolean save() {
		boolean ok = false;
		try {
			if( validaData() ) {
				controller.save(this.getClass().getName());
				ok = true;
				//ora salva
				RequestContext.getCurrentInstance().addCallbackParam("canClose", true);
			}
		} 
		catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
		return ok;
	}

	@Override
	public void restore() {
		controller.restore();
	}

	public void onChangeBarthelTab() {
		controller.calcolaPunteggioTotaleBarthel();
	}

	public void onChangeIADLTab() {
		controller.calcolaPunteggioTotaleIADL();
	}

	public JsonBarthelBean getJsonCurrent() {
		return controller.getJsonCurrent();
	}
	
	private void valorizzaDatiBase(Long idCaso, Long idSchedaUdc){
		setIdCaso(idCaso);
		setIdSchedaUdc(idSchedaUdc);
	}
	
	@Override
	public void init(ISchedaValutazione bean) {
		try{
			controller.load((JsonBarthelBean)bean.getSelected());
			valorizzaDatiBase(bean.getIdCaso(), bean.getIdSchedaUdc());
			//this.getJsonCurrent().setDataValutazione(new Date());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void init(CsDValutazione parent, CsDValutazione scheda) {
		
		try {
			
			controller.loadData(parent, scheda);
			
			if(scheda!=null){
				CsDDiario diario = scheda!=null ? scheda.getCsDDiario() : new CsDDiario();
				Long idCaso = diario.getCsACaso() != null ? diario.getCsACaso().getId() : null;
				valorizzaDatiBase(idCaso, diario.getSchedaId());
			}
			
			valUltimModifica(parent.getCsDDiario());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public boolean elimina(){
	     boolean ok = false;
	       try{ 
	    	   controller.elimina();
	    	   addInfoFromProperties( "elimina.ok" );
	    	   ok= true;
	       }catch(CarSocialeServiceException cse){
	    	   addMessage("Errore di eliminazione",cse.getMessage(),FacesMessage.SEVERITY_ERROR);
	    	   logger.error(cse.getMessage(),cse);
	       }catch(Exception e){
	    	   addErrorFromProperties("elimina.error");
	    	   logger.error(e.getMessage(),e);
	       }
		 return ok;
	}

	@Override
	public void setIdCasoController(Long idCaso) {
		controller.setCasoId(idCaso);
	}

	@Override
	public void setIdSchedaUdc(Long idUdc) {
		controller.setUdcId(idUdc);	
	}

	@Override
	public CsDValutazione getCurrentModel() {
		return controller.getDataModel();
	}

	@Override
	public boolean isNew() {
		return !(controller.getDiarioId()!=null && controller.getDiarioId().longValue()>0);
	}
	
	@Override
	public Long getIdSchedaUdc() {
		return controller.getUdcId();
	}

	@Override
	public void fillReport(String reportPath, List<String> listaSubreport, HashMap<String, Object> map){
		JsonBarthelBean bean = getJsonCurrent();
		BarthelPdfDTO pdf = new BarthelPdfDTO();
		
/*		String un = this.getCurrentModel().getCsDDiario().getCsOOperatoreSettore().getCsOOperatore().getUsername();
		String oper = this.getCognomeNomeUtente(un);
		pdf.setOperatore(oper);*/
		
		if(bean!=null && bean.getMainData()!=null){
			JsonBarthelMainDataBean main = bean.getMainData();
		    if(main!=null){
				pdf.setAlimentazione(main.getDescrizione(main.getAlimentazione()));
				pdf.setBagnoDoccia(main.getDescrizione(main.getBagnoDoccia()));
				pdf.setIgienePersonale(main.getDescrizione(main.getIgienePersonale()));
				pdf.setAbbigliamento(main.getDescrizione(main.getAbbigliamento()));
				pdf.setContinenzaIntestinale(main.getDescrizione(main.getContinenzaIntestinale()));
				pdf.setContinenzaUrinaria(main.getDescrizione(main.getContinenzaUrinaria()));
				pdf.setUsoGabinetto(main.getDescrizione(main.getUsoGabinetto()));
				pdf.setLettoCarrozzina(main.getDescrizione(main.getLettoCarrozzina()));
				pdf.setDeambulazione(main.getDescrizione(main.getDeambulazione()));
				pdf.setScale(main.getDescrizione(main.getScale()));
				pdf.setUsoCarrozzina(main.getDescrizione(main.getUsoCarrozzina()));
				pdf.setPunteggioBarthel(main.getPunteggioTotale().toString()+" "+main.getPunteggioTotaleLegenda());
				pdf.setIntervistaABarthel(main.getIntervistaA());
				
				pdf.setProtesiAusili(main.fillReportAusili());
		    }
		    
			JsonBarthelIADLDataBean iadl = bean.getIadlData();
			if(iadl!=null){
				pdf.setUsoTelefono(iadl.getDescrizione(iadl.getCapacitaUsareTelefono()));
				pdf.setFareAcquisti(iadl.getDescrizione(iadl.getFareAcquisti()));
				pdf.setPreparazioneCibo(iadl.getDescrizione(iadl.getPreparazioneCibo()));
				pdf.setGovernoCasa(iadl.getDescrizione(iadl.getGovernoDellaCasa()));
				pdf.setBiancheria(iadl.getDescrizione(iadl.getBiancheria()));
				pdf.setMezziTrasporto(iadl.getDescrizione(iadl.getMezziDiTrasporto()));
				pdf.setUsoFarmaci(iadl.getDescrizione(iadl.getResponsabilitaUsoFarmaci()));
				pdf.setGestioneDenaro(iadl.getDescrizione(iadl.getCapacitaGestioneDenaro()));
				pdf.setIntervistaAIADL(iadl.getIntervistaA());
				pdf.setPunteggioIADL(iadl.getPunteggioTotale().toString()+" "+iadl.getPunteggioTotaleLegenda());
			}
			
			JsonBarthelPatologiePsichiatricheDataBean psi = bean.getPatologiePsichiatricheData();
			String s = psi.fillReport();
			pdf.setDataValutazione(ddMMyyyy.format(psi.getDataDiValutazione()));
			if(!s.isEmpty())
				pdf.setPatologiePsichiche(s);
			
			listaSubreport.add(reportPath + "/subreport/schedaMultidimensionale/barthel.jrxml");
			map.put("barthel", new JRBeanCollectionDataSource(Arrays.asList(pdf)));
		}
	}

	protected void loadCommonList() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public JsonBarthelBean getSelected() {
		return this.getJsonCurrent();
	}


}
