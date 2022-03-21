package it.webred.ct.data.access.basic.diagnostiche.ici.dto;

import java.io.Serializable;
import java.util.List;

import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.diagnostiche.dto.IndirizzoDTO;
import it.webred.ct.data.model.diagnostiche.*;

public class UnitaImmobiliareIciDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private DocfaIciReportDTO docfaIciReportDTO;
	private List<DocfaIciReportDTO> listaDocfaPerImmobileDTO;
	private List<DocfaIciReportSogg> listaSoggetti;
	private List<IndirizzoDTO> listaIndirizziCatasto;
	private List<IndirizzoDTO> listaIndirizziIci;
	private List<SitTIciOggettoDTO> listaOggettiIciAnte;
	private List<SitTIciOggettoDTO> listaOggettiIciPost;

	public List<DocfaIciReportDTO> getListaDocfaPerImmobileDTO() {
		return listaDocfaPerImmobileDTO;
	}

	public void setListaDocfaPerImmobileDTO(
			List<DocfaIciReportDTO> listaDocfaPerImmobileDTO) {
		this.listaDocfaPerImmobileDTO = listaDocfaPerImmobileDTO;
	}

	public List<DocfaIciReportSogg> getListaSoggetti() {
		return listaSoggetti;
	}

	public void setListaSoggetti(List<DocfaIciReportSogg> listaSoggetti) {
		this.listaSoggetti = listaSoggetti;
	}

	public List<IndirizzoDTO> getListaIndirizziCatasto() {
		return listaIndirizziCatasto;
	}

	public void setListaIndirizziCatasto(List<IndirizzoDTO> listaIndirizziCatasto) {
		this.listaIndirizziCatasto = listaIndirizziCatasto;
	}

	public List<IndirizzoDTO> getListaIndirizziIci() {
		return listaIndirizziIci;
	}

	public void setListaIndirizziIci(List<IndirizzoDTO> listaIndirizziIci) {
		for(int i=0; i<listaIndirizziIci.size(); i++){
			String civico = listaIndirizziIci.get(i).getCivico();
			if(civico!=null)
				listaIndirizziIci.get(i).setCivico(StringUtils.cleanLeftPad(civico,'0'));
		}
		this.listaIndirizziIci = listaIndirizziIci;
	}

	public List<SitTIciOggettoDTO> getListaOggettiIciAnte() {
		return listaOggettiIciAnte;
	}

	public void setListaOggettiIciAnte(List<SitTIciOggettoDTO> listaOggettiIciAnte) {
		for(int i=0; i<listaOggettiIciAnte.size(); i++){
			String civico = listaOggettiIciAnte.get(i).getSitTIciOggetto().getNumCiv();
			if(civico!=null)
				listaOggettiIciAnte.get(i).getSitTIciOggetto().setNumCiv(StringUtils.cleanLeftPad(civico,'0'));
		}
		this.listaOggettiIciAnte = listaOggettiIciAnte;
	}

	public List<SitTIciOggettoDTO> getListaOggettiIciPost() {
		return listaOggettiIciPost;
	}

	public void setListaOggettiIciPost(List<SitTIciOggettoDTO> listaOggettiIciPost) {
		for(int i=0; i<listaOggettiIciPost.size(); i++){
			String civico = listaOggettiIciPost.get(i).getSitTIciOggetto().getNumCiv();
			if(civico!=null)
				listaOggettiIciPost.get(i).getSitTIciOggetto().setNumCiv(StringUtils.cleanLeftPad(civico,'0'));
		}
		this.listaOggettiIciPost = listaOggettiIciPost;
	}

	public DocfaIciReportDTO getDocfaIciReportDTO() {
		return docfaIciReportDTO;
	}

	public void setDocfaIciReportDTO(DocfaIciReportDTO docfaIciReportDTO) {
		this.docfaIciReportDTO = docfaIciReportDTO;
	}
	
	
	
}
