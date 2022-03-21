package it.webred.ss.web.bean.report;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.siru.StampaFseDTO;
import it.webred.cs.csa.utils.JasperUtils;
import it.webred.cs.data.DataModelCostanti.GrVulnerabile;
import it.webred.cs.data.model.CsTbGVulnerabile;

import java.util.Arrays;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean
@ViewScoped
public class ReportPorBean extends ReportBaseBean {

	public void esportaModelloPor(StampaFseDTO datiprogetto){
		try{
		String reportPath =	getSession().getServletContext().getRealPath("/reports");
		String logo =	getSession().getServletContext().getRealPath("/images");
		String nomeModulo = this.getModuloPorRegionale();
		
	    boolean marche = this.isModuloPorMarche();
		
		ModelloPorPdfDTO pdf = this.fillModelloPOR(datiprogetto,marche);
		pdf.setImagePath(logo+"/"); //+"logo_FSE_2014_2020_completo.jpg"); nome immagine presente nel modulo
		JasperUtils jUtils = new JasperUtils();
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("Nome modulo jasper "+nomeModulo);
		jUtils.esportaReport("Modulo POR", reportPath +"/"+ nomeModulo + ".jrxml", null, map, new JRBeanCollectionDataSource(Arrays.asList(pdf)));
		
		}catch(Exception e){
			addError("report.error");
			logger.error(e.getMessage(),e);
		}		
	
		
	}
	
	public ModelloPorPdfDTO fillModelloPOR(StampaFseDTO datiPor, boolean marche){
		ModelloPorPdfDTO pdf = new ModelloPorPdfDTO();
		try{
			//Dati beneficiario
			pdf.setCognome(datiPor.getCognome());
			pdf.setNome(datiPor.getNome());
			pdf.setCodFiscale(datiPor.getCodiceFiscale());
			pdf.setCittadinanza(datiPor.getCittadinanza());
			pdf.setDataNascita(datiPor.getDataNascita());
			pdf.setSesso(datiPor.getSesso());
			
			pdf.setTelefono(datiPor.getTelefono() != null ? datiPor.getTelefono() : "");
			pdf.setCellulare(datiPor.getCellulare() != null ? datiPor.getCellulare() : "");
			pdf.setEmail(datiPor.getEmail() != null ? datiPor.getEmail() : "");
			pdf.setLuogoNascita(datiPor.getLuogoNascita());
			
			pdf.setResid_cap(datiPor.getCapResidenza());
			pdf.setResid_sigla_prov(datiPor.getSiglaProvResidenza());
			pdf.setResidenza(datiPor.getComuneResidenza());
			pdf.setResid_via(datiPor.getViaResidenza());
	
			pdf.setDom_cap(datiPor.getDomicilioCap());
			pdf.setDom_sigla_prov(datiPor.getDomicilioSiglaProv());
			pdf.setDomicilio(datiPor.getDomicilioComune());
			pdf.setDom_via(datiPor.getViaDomicilio());
		
			//Dati Dichiarati
			String vulnerabilita = "-";
			if(datiPor.getComunicaVul()){
				if(marche){
					String scelto = datiPor.getIdVulnerabile();
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					CsTbGVulnerabile gr = null;
					if(!StringUtils.isBlank(scelto) && Arrays.asList(GrVulnerabile.stampaAltro).contains(scelto)){
						dto.setObj(GrVulnerabile.ALTRO); //13 Altro tipo di vulnerabilità
						gr = configurationCsBean.getGrVulnerabileById(dto);
					}else if(!StringUtils.isBlank(scelto) && Arrays.asList(GrVulnerabile.stampaVittimaViolenza).contains(scelto)){
						dto.setObj(GrVulnerabile.VITTIMA_VIOLENZA); //11 Vittima di violenza, di tratta e grave sfruttamento
						gr = configurationCsBean.getGrVulnerabileById(dto);
					}
					vulnerabilita = gr!=null ? gr.getTooltip() : datiPor.getDescrizioneVulnerabile();
				}else
					vulnerabilita = datiPor.getDescrizioneVulnerabile();
			}else{
				vulnerabilita =  "L'utente non intende fornire informazioni sulla condizione di vulnerabilità";
			}
			pdf.setCond_vulnerabilita(vulnerabilita);
			pdf.setDurata_ric_lavoro(datiPor.getLavoroDurataRicerca());
			pdf.setCondizione_lavoro(datiPor.getCondLavoro());
			pdf.setTitolo_studio(datiPor.getTitoloStudio());
			pdf.setTitolo_studio_tooltip(datiPor.getTitoloStudioTooltip());
			
			//Dati progetto
			pdf.setNome_prog(datiPor.getFfProgettoDescrizione());
			pdf.setAttuatore_prog(datiPor.getSoggettoAttuatore());
			
			String codProgetto = datiPor.getCodProgetto();
			String attivita = datiPor.getCodAttivita();
			
			if(marche)
				pdf.setCod_prog(attivita);
			else {
				pdf.setCod_prog(codProgetto);
				pdf.setAttivita(attivita);
			}
			
			pdf.setDataSottoscrizione(datiPor.getDtSottoscrizione());
		
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return pdf;
	}
	

}
