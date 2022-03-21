package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ordinario implements Serializable {

	private List<ComponenteNucleo> componenteNucleo = new ArrayList<ComponenteNucleo>();
	
	private ISEEOrdinario iseeOrdinario;
	
	private List<ComponenteMinorenne> componenteMinorenne = new ArrayList<ComponenteMinorenne>();
	
	private List<StudenteUniversitario> studenteUniversitario = new ArrayList<StudenteUniversitario>();
	
	private List<ComponenteSocioSanitarioRes> componenteSocioSanitarioRes = new ArrayList<ComponenteSocioSanitarioRes>();
	
	//<!-- ISEE Minorenne -->
	private ISEEMin iseeMin;
	
	private String corrente;
	
	private String dataPresentazioneModuloSostituivo;
	
	private String protocolloDSUModuloSostitutivo;

	 

	public ISEEMin getIseeMin() {
		return iseeMin;
	}

	public void setIseeMin(ISEEMin iseeMin) {
		this.iseeMin = iseeMin;
	}

	public List<ComponenteNucleo> getComponenteNucleo() {
		return componenteNucleo;
	}

	public void setComponenteNucleo(List<ComponenteNucleo> componenteNucleo) {
		this.componenteNucleo = componenteNucleo;
	}

	public ISEEOrdinario getIseeOrdinario() {
		return iseeOrdinario;
	}

	public void setIseeOrdinario(ISEEOrdinario iseeOrdinario) {
		this.iseeOrdinario = iseeOrdinario;
	}

	public List<ComponenteMinorenne> getComponenteMinorenne() {
		return componenteMinorenne;
	}

	public void setComponenteMinorenne(
			List<ComponenteMinorenne> componenteMinorenne) {
		this.componenteMinorenne = componenteMinorenne;
	}
	public void addComponenteMinorenne(
			 ComponenteMinorenne componenteMinorenne) {
		this.componenteMinorenne.add(componenteMinorenne);
	}

	public List<StudenteUniversitario> getStudenteUniversitario() {
		return studenteUniversitario;
	}

	public void setStudenteUniversitario(
			List<StudenteUniversitario> studenteUniversitario) {
		this.studenteUniversitario = studenteUniversitario;
	}

	public void addStudenteUniversitario(
			 StudenteUniversitario  studenteUniversitario) {
		this.studenteUniversitario.add(studenteUniversitario);
	}
	
	public List<ComponenteSocioSanitarioRes> getComponenteSocioSanitarioRes() {
		return componenteSocioSanitarioRes;
	}

	public void setComponenteSocioSanitarioRes(
			List<ComponenteSocioSanitarioRes> componenteSocioSanitarioRes) {
		this.componenteSocioSanitarioRes = componenteSocioSanitarioRes;
	}
	
	public void addComponenteSocioSanitarioRes(
			 ComponenteSocioSanitarioRes componenteSocioSanitarioRes) {
		this.componenteSocioSanitarioRes.add(componenteSocioSanitarioRes);
	}

	public String getCorrente() {
		return corrente;
	}

	public void setCorrente(String corrente) {
		this.corrente = corrente;
	}

	public String getDataPresentazioneModuloSostituivo() {
		return dataPresentazioneModuloSostituivo;
	}

	public void setDataPresentazioneModuloSostituivo(
			String dataPresentazioneModuloSostituivo) {
		this.dataPresentazioneModuloSostituivo = dataPresentazioneModuloSostituivo;
	}

	public String getProtocolloDSUModuloSostitutivo() {
		return protocolloDSUModuloSostitutivo;
	}

	public void setProtocolloDSUModuloSostitutivo(
			String protocolloDSUModuloSostitutivo) {
		this.protocolloDSUModuloSostitutivo = protocolloDSUModuloSostitutivo;
	}
	
	
}
