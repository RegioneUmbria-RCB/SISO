package it.webred.cs.csa.web.manbean.fascicolo.schedeSegr;

import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.csa.web.manbean.schedaSegr.LazyListaSchedeSegrBaseModel;
import it.webred.cs.csa.web.manbean.schedaSegr.SchedaSegr;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

public class LazyListaSchedeSegrFascicoloModel extends LazyListaSchedeSegrBaseModel {
    
	private Long idSoggetto;
	
	
    @Override
    public List<SchedaSegr> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
        List<SchedaSegr> data = new ArrayList<SchedaSegr>();
       
		SchedaSegrDTO dto = new SchedaSegrDTO();
	
		CsUiCompBaseBean.fillEnte(dto);
		dto.setFirst(first);
		dto.setPageSize(pageSize);
			
		try {
			
			AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote)CsUiCompBaseBean.getCarSocialeEjb("AccessTableSoggettoSessionBean");
			
			dto = this.getFilterCondition(filters, dto);
			
			 
			 it.webred.cs.csa.ejb.dto.BaseDTO csb = new it.webred.cs.csa.ejb.dto.BaseDTO();
			 CsUiCompBaseBean.fillEnte(csb);
			 csb.setObj(idSoggetto);
			
			 CsAAnagrafica anag = soggettoService.getAnagraficaById(csb);
			
			 dto.setSearchBySoggetto(Boolean.TRUE);
			 dto.setLoadListaUDCFascicolo(Boolean.TRUE);
			 dto.setIdAnagrafica(idSoggetto);
			 dto.setCf(anag!=null ? anag.getCf() : null);
			 
			 List<SchedaSegr> lst = this.loadListaSchedeUDC(dto, false);
			 for(SchedaSegr s : lst){
				 if (s.isCreata())
						data.add(0, s);
					else
						data.add(s);
			 }	 
			 
			 
	/*		Metodo di caricamento precedente, senza filtro 
			 
			 if(loadUdc){
	
				if(anag!=null && anag.getCf()!=null){
					String cf = anag!=null ? anag.getCf() : null;
					
					it.webred.ss.ejb.dto.BaseDTO ssb = new it.webred.ss.ejb.dto.BaseDTO();
					CsUiCompBaseBean.fillEnte(ssb);
					
					ssb.setObj(cf);
					List<SsScheda> list = ssSchedaSegrService.readSchedeByCF(ssb);
					for (SsScheda ssScheda: list){
						if (ssScheda.getCompleta() && !ssScheda.getEliminata()){
							ssb.setObj(ssScheda.getSegnalato());
							SsSchedaSegnalato segnalato = ssSchedaSegrService.readSegnalatoById(ssb);
							ssb.setObj(ssScheda.getTipo());
							SsTipoScheda tipoScheda = ssSchedaSegrService.readTipoSchedaById(ssb);
							
							// Allora la ritrovo anche in CS_SS_SCHEDA_SEGR
							CsSsSchedaSegr csScheda = null;
							IterInfoStatoMan casoInfo = null;
							if (tipoScheda != null && tipoScheda.getPresa_in_carico()) {
								// SISO-938 cambiata la chiave di ricerca su CS_SS_SCHEDA_SEGR
								csb.setObj(ssScheda.getId());
								csb.setObj2(DataModelCostanti.SchedaSegr.PROVENIENZA_SS);

								csScheda = schedaSegrService.findSchedaSegrBySchedaIdProvenienza(csb);
								
								// info stato caso
								// Dal momento che il valore in questa fase è impostato a FALSE,
								// non vale la pena caricare il dato, che non viene comunque mostrato
								if (showStatoCartella) {
									this.verificaStatoCaso(iterSessionBean, csScheda);
								}

							}
							
							SchedaSegr schedaSegr = new SchedaSegr(ssScheda, segnalato, tipoScheda, csScheda);
							
							schedaSegr.setShowStatoCartella(showStatoCartella);
							SsOperatoreAnagrafica amAna = ssScheda.getAccesso().getAnagraficaOperatore();
							schedaSegr.setOperatore(amAna != null ? amAna.getStampaDesc() : null);
							schedaSegr.setLastIterStepInfo(casoInfo);
							
							if (schedaSegr.isCreata())
								data.add(0, schedaSegr);
							else
								data.add(schedaSegr);
						}
					}
				}
				if(loadSerena){
					// SISO-938
					// aggiungo gli eventuali record su CS_SS_SCHEDA_SEGR con PROVENIENZA != 'SS'
					// (quelli == 'SS' li ho già presi prima)
					csb.setObj(anag.getId());
					List<CsSsSchedaSegr> schedeSegr = csSchedaSegrService.findSchedaSegrByIdAnagraficaNotSS(csb);

					for (CsSsSchedaSegr csSchedaSegr : schedeSegr) {
						csb.setObj(csSchedaSegr.getSchedaId());
						csb.setObj2(csSchedaSegr.getProvenienza());

						CsSchedeAltraProvenienza vistaCasiAltri = csSchedaSegrService.findVistaCasiAltriBySchedaIdProvenienza(csb);

						SchedaSegr schedaSegr = new SchedaSegr(vistaCasiAltri, csSchedaSegr);
						schedaSegr.setShowStatoCartella(false);

						if (schedaSegr.isCreata()) {
							data.add(0, schedaSegr);
						} else {
							data.add(schedaSegr);
						}
					}
				}
			}*/
			 
	     /*   //rowCount
			this.setRowCount(data.size());
			*/
		
		} catch (Exception e) {
			CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		
		return data;
    }

	public Long getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

    
}
