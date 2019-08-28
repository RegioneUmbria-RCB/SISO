package it.webred.cs.csa.web.manbean.report.filler;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.report.ReportBaseBean;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.AnagraficaSinteticaPdfDTO;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAIndirizzo;

import java.util.List;

public class ReportFascicoloFiller extends ReportBaseBean {

	
	public AnagraficaSinteticaPdfDTO fillDatiAnagrafica(){
		//dati soggetto 
		AnagraficaSinteticaPdfDTO pdfDto = new AnagraficaSinteticaPdfDTO();
		
		CsAAnagrafica ana = soggetto.getCsAAnagrafica();
		String denominazione = ana.getCognome()+" "+ana.getNome();
		pdfDto.setDenominazione(denominazione);
		pdfDto.setCf(ana.getCf());
		pdfDto.setComuneNascita(ana.getComuneNascitaDes()+" ("+ana.getProvNascitaCod()+")");
		pdfDto.setDataNascita(ddMMyyyy.format(ana.getDataNascita()));
		pdfDto.setCittadinanza(ana.getCittadinanza());
		String telefono = ana.getTel()!=null ? "fisso: "+ana.getTel() : "";
		telefono += ana.getCell()!=null? " cell.: "+ana.getCell() : "";
		pdfDto.setTelefono(telefono);
		
				
		BaseDTO dto1 = new BaseDTO();
		fillEnte(dto1);
		dto1.setObj(soggetto.getAnagraficaId());	
		List <CsAIndirizzo> indirizzo = indirizzoService.getIndirizziBySoggetto(dto1);
		for(CsAIndirizzo i: indirizzo){
			if(i.getDataFineApp()==null && i.getCsTbTipoIndirizzo().getId()==1){
				CsAAnaIndirizzo ind = i.getCsAAnaIndirizzo();
				String via = ind.getIndirizzo();
				String civico = ind.getCivicoNumero();
				String citta = ind.getComDes();
				String pv = ind.getProv();
				String d = "";
				d+= (via!=null && !via.isEmpty()) ? via : "";
				d+= (civico!=null && !civico.isEmpty()) ? " "+civico : "";
				d+= (citta!=null && !citta.isEmpty()) ? ", "+citta : "";
				d+= (pv!=null && !pv.isEmpty()) ? " ("+pv+")" : "";
				if(!d.isEmpty()) pdfDto.setIndirizzo(d.trim());
			}		
		}
		
		return pdfDto;
	}

}
