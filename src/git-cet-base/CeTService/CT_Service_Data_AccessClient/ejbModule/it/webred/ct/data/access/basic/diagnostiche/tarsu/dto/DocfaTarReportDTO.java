package it.webred.ct.data.access.basic.diagnostiche.tarsu.dto;

import java.io.Serializable;
import java.util.List;

import it.webred.ct.data.access.basic.diagnostiche.ici.dto.QuadroTarsuDTO;
import it.webred.ct.data.model.diagnostiche.*;
import it.webred.ct.data.model.docfa.DocfaDichiaranti;
import it.webred.ct.data.model.docfa.DocfaIntestati;

public class DocfaTarReportDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private DocfaTarReport docfaTarReport;
	
	private String civico;
	
	private String causaleDocfaExt;
	private String categoriaDocfaExt;
	private String categoriaCatastoExt;
	private List<DocfaTarReportSogg> titolariAnno;
	private List<DocfaDichiaranti> listaDichiaranti;
	private List<DocfaIntestati> listaIntestati;
	private List<QuadroTarsuDTO> quadroPre;
	private List<QuadroTarsuDTO> quadroPost;

	public DocfaTarReport getDocfaTarReport() {
		return docfaTarReport;
	}

	public void setDocfaTarReport(DocfaTarReport docfaTarReport) {
		this.docfaTarReport = docfaTarReport;
	}

	public List<DocfaTarReportSogg> getTitolariAnno() {
		return titolariAnno;
	}

	public void setTitolariAnno(List<DocfaTarReportSogg> titolariAnno) {
		this.titolariAnno = titolariAnno;
	}

	public List<DocfaDichiaranti> getListaDichiaranti() {
		return listaDichiaranti;
	}

	public void setListaDichiaranti(List<DocfaDichiaranti> listaDichiaranti) {
		this.listaDichiaranti = listaDichiaranti;
	}

	public List<DocfaIntestati> getListaIntestati() {
		return listaIntestati;
	}

	public void setListaIntestati(List<DocfaIntestati> listaIntestati) {
		this.listaIntestati = listaIntestati;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCategoriaDocfaExt() {
		return categoriaDocfaExt;
	}

	public void setCategoriaDocfaExt(String categoriaDocfaExt) {
		this.categoriaDocfaExt = categoriaDocfaExt;
	}

	public String getCategoriaCatastoExt() {
		return categoriaCatastoExt;
	}

	public void setCategoriaCatastoExt(String categoriaCatastoExt) {
		this.categoriaCatastoExt = categoriaCatastoExt;
	}

	public String getCausaleDocfaExt() {
		return causaleDocfaExt;
	}

	public void setCausaleDocfaExt(String causaleDocfaExt) {
		this.causaleDocfaExt = causaleDocfaExt;
	}

	public List<QuadroTarsuDTO> getQuadroPre() {
		return quadroPre;
	}

	public void setQuadroPre(List<QuadroTarsuDTO> quadroPre) {
		this.quadroPre = quadroPre;
	}

	public List<QuadroTarsuDTO> getQuadroPost() {
		return quadroPost;
	}

	public void setQuadroPost(List<QuadroTarsuDTO> quadroPost) {
		this.quadroPost = quadroPost;
	}

	
	
}
