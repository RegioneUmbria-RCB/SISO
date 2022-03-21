package it.webred.ct.data.access.basic.diagnostiche.ici.dto;

import java.io.Serializable;
import java.util.List;

import it.webred.ct.data.model.diagnostiche.*;
import it.webred.ct.data.model.docfa.DocfaDichiaranti;
import it.webred.ct.data.model.docfa.DocfaIntestati;

public class DocfaIciReportDTO implements Serializable{
	
	private DocfaIciReport docfaIciReport;
	private String causaleDocfaExt;
	private String categoriaDocfaExt;
	private String categoriaCatastoExt;
	private List<DocfaIciReportSogg> titolariAnno;
	private List<DocfaDichiaranti> listaDichiaranti;
	private List<DocfaIntestati> listaIntestati;

	private List<DocfaIciReport> quadroPre;
	private List<DocfaIciReport> quadroPost;
	
	public DocfaIciReport getDocfaIciReport() {
		return docfaIciReport;
	}

	public void setDocfaIciReport(DocfaIciReport docfaIciReport) {
		this.docfaIciReport = docfaIciReport;
	}

	public List<DocfaIciReportSogg> getTitolariAnno() {
		return titolariAnno;
	}

	public void setTitolariAnno(List<DocfaIciReportSogg> titolariAnno) {
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
	
	public List<DocfaIciReport> getQuadroPre() {
		return quadroPre;
	}

	public void setQuadroPre(List<DocfaIciReport> quadroPre) {
		this.quadroPre = quadroPre;
	}

	public List<DocfaIciReport> getQuadroPost() {
		return quadroPost;
	}

	public void setQuadroPost(List<DocfaIciReport> quadroPost) {
		this.quadroPost = quadroPost;
	}
	
}
