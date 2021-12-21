package it.webred.cs.csa.web.manbean.report;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.PresaInCaricoDTO;
import it.webred.cs.csa.utils.bean.report.dto.HeaderPdfDTO;
import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean
@SessionScoped
public class ReportBaseBean extends CsUiCompBaseBean {
	
	protected CsASoggettoLAZY soggetto;
	
	public void fillReportCommonData(String reportPath, List<String> listaSubreport, HashMap<String, Object> map, ReportPdfDTO pdf ) throws Exception{
		this.fillReportHeader(reportPath, listaSubreport, map);
		this.fillReportPieDiPagina(pdf);
		pdf.setDataOdierna(ddMMyyyy.format(new Date()));
		pdf.setComune(getNomeEnte());
		
		if(soggetto!=null){
			//campi PIC
	        BaseDTO dto = new BaseDTO();
	        fillEnte(dto);
	        dto.setObj(soggetto.getCsACaso().getId());
			PresaInCaricoDTO pic = iterService.getLastPICByCaso(dto);
			if(pic!=null){
				pdf.setAssSociale(pic.getResponsabile().getDenominazione());
				pdf.setDataPIC(ddMMyyyy.format(pic.getDataAmministrativa()));
			}
		}
	}
		
	public void fillReportHeader(String reportPath, List<String> listaSubreport, HashMap<String, Object> map) {
		
		BaseDTO baseDto = new BaseDTO();
		fillEnte(baseDto);
		
		HeaderPdfDTO hPdf = new HeaderPdfDTO();
		CsOOperatoreSettore opSett = getCurrentOpSettore();
		
		//carico path dati
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("dir.files.datiDiogene");
		criteria.setComune(null);
		criteria.setSection(null);
		
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		if(amKey != null) {
			String dir =  amKey.getValueConf();
			String path = dir + baseDto.getEnteId() + this.dirLoghi;
			File logo = new File(path + "logo_bw.png");
			if(logo.exists())
				hPdf.setImagePath(path);
			else {
				logger.error("Attenzione: Il logo del report non è presente");
				hPdf.setImagePath(reportPath + "/logo/");
			}
		} else {
			logger.error("Attenzione: Il path per il recupero logo del report non è impostato");
			hPdf.setImagePath(reportPath + "/logo/");
		}
		
		hPdf.setNomeSettore(opSett.getCsOSettore().getNome());
		hPdf.setNomeUfficio(opSett.getCsOSettore().getNome2());
		hPdf.setNomeEnte(getNomeEnte());

		listaSubreport.add(reportPath + "/subreport/header.jrxml");
		map.put("header", new JRBeanCollectionDataSource(Arrays.asList(hPdf)));
		
	}

	public void fillReportPieDiPagina(ReportPdfDTO dto) {
		String pie = "";
		
		CsOSettore sett = getCurrentOpSettore().getCsOSettore();
		pie += sett.getNome();
		if(sett.getNome2() != null)
			pie += (" - " + sett.getNome2());
		pie += "<br>";
		if(sett.getCsAAnaIndirizzo() != null) {
			pie += (sett.getCsAAnaIndirizzo().getIndirizzo() + " " + sett.getCsAAnaIndirizzo().getCivicoNumero());
			pie += (" - " + sett.getCsAAnaIndirizzo().getComDes());
			pie += "<br>";
		}
		if(sett.getOrario() != null) {
			pie += ("Orari: " + sett.getOrario());
			pie += "<br>";
		}
		if(sett.getEmail() != null)
			pie += "Email: " + sett.getEmail()+" ";
		if(sett.getTel() != null)
			pie += "Tel: " + sett.getTel()+" ";
		if(sett.getCell() != null)
			pie += "Cell: " + sett.getCell()+" ";

		dto.setPieDiPagina(pie);
	}

	public CsASoggettoLAZY getSoggetto() {
		return soggetto;
	}

	public String getDenominazione() {
		if(this.soggetto!=null)
			return soggetto.getCsAAnagrafica().getCognome() + "_" + soggetto.getCsAAnagrafica().getNome();
		else
			return "anonimo";
	}

	public void setSoggetto(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
	}

}
