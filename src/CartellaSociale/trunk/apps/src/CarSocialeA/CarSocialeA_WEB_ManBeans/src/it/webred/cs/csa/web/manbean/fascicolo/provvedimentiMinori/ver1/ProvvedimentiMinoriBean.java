package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1;

import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.*;
import it.webred.cs.json.dto.JsonBaseBean;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProvvedimentiMinoriBean extends JsonBaseBean {
	private static final String JsnonName = "ProvvedimentoMinoriBean";
	private DatiProvvedimentoBean datiProvvedimenti;
	private PrescrizioniSpecialisticheBean prescrizioniSpecialistiche;
	private PrescrizioniSocioEducativeBean prescrSocioEducative;
	private InterventiTutelaBean interventiTutela;
	private IntSpecialistiTempiChiusureBean interventiTempiChiusure;
	private String nota;

	public ProvvedimentiMinoriBean()
	{
		this.datiProvvedimenti = new DatiProvvedimentoBean();
		this.prescrizioniSpecialistiche = new PrescrizioniSpecialisticheBean();
		this.prescrSocioEducative = new PrescrizioniSocioEducativeBean();
		this.interventiTutela = new InterventiTutelaBean();
		this.interventiTempiChiusure = new IntSpecialistiTempiChiusureBean();

	}

	public ProvvedimentiMinoriBean(ProvvedimentiMinoriBean jsonOriginal) {

		try {
			if (jsonOriginal != null) {
				datiProvvedimenti = (DatiProvvedimentoBean) BeanUtils.cloneBean(jsonOriginal.getDatiProvvedimenti());
				prescrizioniSpecialistiche = (PrescrizioniSpecialisticheBean) BeanUtils.cloneBean(jsonOriginal.getPrescrizioniSpecialistiche());
				prescrSocioEducative = (PrescrizioniSocioEducativeBean) BeanUtils.cloneBean(jsonOriginal.getPrescrSocioEducative());
				interventiTutela = (InterventiTutelaBean) BeanUtils.cloneBean(jsonOriginal.getInterventiTutela());
				interventiTempiChiusure = (IntSpecialistiTempiChiusureBean) BeanUtils.cloneBean(jsonOriginal.getInterventiTempiChiusure());
			}
			else
			{
				this.datiProvvedimenti = new DatiProvvedimentoBean();
				this.prescrizioniSpecialistiche = new PrescrizioniSpecialisticheBean();
				this.prescrSocioEducative = new PrescrizioniSocioEducativeBean();
				this.interventiTutela = new InterventiTutelaBean();
				this.interventiTempiChiusure = new IntSpecialistiTempiChiusureBean();
			}

		} catch (Exception ex) {
			logger.error(ex);
			throw new Error(ex);
		}
	}

	// *******GETTER AND SETTER****//

	public DatiProvvedimentoBean getDatiProvvedimenti() {
		return datiProvvedimenti;
	}

	public void setDatiProvvedimenti(DatiProvvedimentoBean datiProvvedimenti) {
		this.datiProvvedimenti = datiProvvedimenti;
	}

	public PrescrizioniSpecialisticheBean getPrescrizioniSpecialistiche() {
		return prescrizioniSpecialistiche;
	}

	public void setPrescrizioniSpecialistiche(
			PrescrizioniSpecialisticheBean prescrizioniSpecialistiche) {
		this.prescrizioniSpecialistiche = prescrizioniSpecialistiche;
	}

	public PrescrizioniSocioEducativeBean getPrescrSocioEducative() {
		return prescrSocioEducative;
	}

	public void setPrescrSocioEducative(
			PrescrizioniSocioEducativeBean prescrSocioEducative) {
		this.prescrSocioEducative = prescrSocioEducative;
	}

	public InterventiTutelaBean getInterventiTutela() {
		return interventiTutela;
	}

	public void setInterventiTutela(InterventiTutelaBean interventiTutela) {
		this.interventiTutela = interventiTutela;
	}

	public IntSpecialistiTempiChiusureBean getInterventiTempiChiusure() {
		return interventiTempiChiusure;
	}

	public void setInterventiTempiChiusure(
			IntSpecialistiTempiChiusureBean interventiTempiChiusure) {
		this.interventiTempiChiusure = interventiTempiChiusure;
	}

	public static String getJsnonname() {
		return JsnonName;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getNota() {
		return nota;
	}

	// /**JsonBaseBean Methods*///
	@Override
	public List<String> checkObbligatorieta() {
		// TODO Auto-generated method stub
		return new LinkedList<String>();
	}
}
