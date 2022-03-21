package it.webred.cs.csa.ejb.dto.pai;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;

@JsonIgnoreProperties(value = { "csASoggetto", "caso", "casoId", "datiValidi", "allineatoResidenza",
		"nazioneResidenzaNonDefinita" })
public class CsPaiMastSoggDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private CsASoggettoLAZY csASoggetto;

	private String cf;

	private Long diarioId;

	private String cognome;

	private String nome;

	private String cittadinanza;

	private Integer annoNascita;

	private Boolean intestatario;

	private Date dtIns;

	private String userIns;

	private Date dtMod;

	private String userMod;

	private String comuneResidenza;

	private String nazioneResidenza;

	private String viaResidenza;

	private String secondaCittadinanza;

	private String sesso;

	private Long casoId;

	// Allineamento dati da cartella
	private CsACaso caso;
	private boolean datiValidi = false;
	private boolean allineatoResidenza;
	private boolean nazioneResidenzaNonDefinita = false;

	public CsPaiMastSoggDTO() {
		super();
	}

	public CsPaiMastSoggDTO(CsASoggettoLAZY soggetto, String viaResidenza, String jsonLuogoResidenza, String statoEsteroResidenza, boolean intestatario) {
		csASoggetto = soggetto;

		this.sincronizzaDatiAnagrafici();
		this.sincronizzaResidenza(viaResidenza, jsonLuogoResidenza, statoEsteroResidenza);

		this.verificaDatiValidi(viaResidenza, jsonLuogoResidenza);

		this.intestatario = intestatario;

	}

	public CsPaiMastSoggDTO(String cognome, String nome, String cf, String cittadinanza, Date dataNascita, String viaResidenza, String comuneResidenza, String sesso, boolean intestatario) {
		this.cognome = cognome;
		this.nome = nome;
		this.cf = cf;
		this.cittadinanza = cittadinanza;
		this.annoNascita = this.getAnno(dataNascita);
		this.intestatario = intestatario;

		this.viaResidenza = viaResidenza;
		this.comuneResidenza = comuneResidenza;
		this.sesso = sesso;
		this.datiValidi = isValorizzato();
	}

	public CsPaiMastSoggDTO(boolean intestatario){
		this.cognome="";
		this.nome="";
		this.cf="";
		this.intestatario=intestatario;
		this.cittadinanza=null;
		this.secondaCittadinanza=null;
		this.comuneResidenza=null;
		this.viaResidenza="";
	}
	
	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public Integer getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(Integer annoNascita) {
		this.annoNascita = annoNascita;
	}

	public Boolean getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(Boolean intestatario) {
		this.intestatario = intestatario;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getUserMod() {
		return userMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getNazioneResidenza() {
		return nazioneResidenza;
	}

	public void setNazioneResidenza(String nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	}

	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}

	public String getSecondaCittadinanza() {
		return secondaCittadinanza;
	}

	public void setSecondaCittadinanza(String secondaCittadinanza) {
		this.secondaCittadinanza = secondaCittadinanza;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public CsACaso getCaso() {
		return caso;
	}

	public void setCaso(CsACaso caso) {
		this.caso = caso;
	}

	// Allineamento dati da cartella
	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public boolean isDatiValidi() {
		return datiValidi;
	}

	public void setDatiValidi(boolean datiValidi) {
		this.datiValidi = datiValidi;
	}

	public boolean isAllineatoResidenza() {
		// TODO da modificare
		return true;
//		return allineatoResidenza;
	}

	public void setAllineatoResidenza(boolean allineatoResidenza) {
		this.allineatoResidenza = allineatoResidenza;
	}

	public boolean isNazioneResidenzaNonDefinita() {
		return nazioneResidenzaNonDefinita;
	}

	public void setNazioneResidenzaNonDefinita(boolean nazioneResidenzaNonDefinita) {
		this.nazioneResidenzaNonDefinita = nazioneResidenzaNonDefinita;
	}

	private Integer getAnno(Date data) {
		if (data != null) {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(data);
			return calendar1.get(Calendar.YEAR);
		} else
			return null;
	}

	// LD
	public boolean isAllineatoAnagrafica() {
		this.csASoggetto = this.getCaso() != null ? this.getCaso().getCsASoggetto() : null;
		if (csASoggetto != null) {
			CsAAnagrafica ana = csASoggetto.getCsAAnagrafica();
			return StringUtils.equals(this.cf, ana.getCf()) && StringUtils.equals(this.nome, ana.getNome())
					&& StringUtils.equals(this.cognome, ana.getCognome())
					&& getAnno(ana.getDataNascita()).equals(this.annoNascita)
					&& StringUtils.equals(this.cittadinanza, ana.getCittadinanza())
					&& StringUtils.equals(this.getSecondaCittadinanza(), ana.getCittadinanza2())
					&& StringUtils.equals(this.sesso, ana.getSesso());
		} else
			return true;
	}

	public void sincronizzaDatiAnagrafici() {
		if (csASoggetto != null) {
			nome = csASoggetto.getCsAAnagrafica().getNome();
			cognome = csASoggetto.getCsAAnagrafica().getCognome();
			cf = csASoggetto.getCsAAnagrafica().getCf();
			annoNascita = getAnno(csASoggetto.getCsAAnagrafica().getDataNascita());
			cittadinanza = csASoggetto.getCsAAnagrafica().getCittadinanza();
			secondaCittadinanza = csASoggetto.getCsAAnagrafica().getCittadinanza2();
			sesso = csASoggetto.getCsAAnagrafica().getSesso();
		}
		verificaDatiValidi(null, null);
	}

	public void sincronizzaResidenza(String viaResidenza, String jsonLuogoResidenza, String statoEsteroResidenza) {
		this.comuneResidenza = jsonLuogoResidenza;
		this.viaResidenza = viaResidenza;
		this.nazioneResidenza = !"1".equals(statoEsteroResidenza) ? statoEsteroResidenza : null;
		this.verificaDatiValidi(viaResidenza, jsonLuogoResidenza);
	}

	private void verificaDatiValidi(String viaResidenza, String jsonLuogoResidenza) {
		if ((viaResidenza != null && jsonLuogoResidenza != null) || StringUtils.equalsIgnoreCase(viaResidenza, DataModelCostanti.SENZA_FISSA_DIMORA))
			this.isAllineatoResidenza(viaResidenza, jsonLuogoResidenza);

		if (this.csASoggetto != null && (this.isAllineatoAnagrafica() & this.allineatoResidenza))
			this.datiValidi = true;
	}

	public boolean isAllineatoResidenza(String viaResidenza, String jsonLuogoResidenza) {
		if (csASoggetto != null) {
			CsAAnagrafica ana = csASoggetto.getCsAAnagrafica();

			String viaReplaced = viaResidenza != null ? viaResidenza.replaceAll(",", "") : viaResidenza;

			this.allineatoResidenza = StringUtils.equalsIgnoreCase(this.cf, ana.getCf())
					&& StringUtils.equals(this.comuneResidenza, jsonLuogoResidenza)
					&& (StringUtils.equalsIgnoreCase(this.viaResidenza, viaResidenza)
							|| StringUtils.equalsIgnoreCase(this.viaResidenza, viaReplaced)); // Alcune vie nel mast
																								// sono scritte senza
																								// virgola
		} else
			allineatoResidenza = true;
		return allineatoResidenza;
	}

	public boolean isValorizzato() {
		boolean validato = !StringUtils.isBlank(nome) && !StringUtils.isBlank(cognome) && !StringUtils.isBlank(cf)
				&& !"0000000000000000".equals(cf) && !StringUtils.isBlank(cittadinanza)
				&& (annoNascita != null && annoNascita > 0);

		if (this.intestatario) {

			validato = validato && !StringUtils.isBlank(sesso);

			boolean statoValido = !StringUtils.isBlank(nazioneResidenza) || nazioneResidenzaNonDefinita;
			boolean comuneValido = !StringUtils.isBlank(comuneResidenza);
			boolean viaValida = !StringUtils.isBlank(viaResidenza);

			// SISO-962
			validato = validato && (statoValido || (comuneValido && viaValida)
					|| StringUtils.equalsIgnoreCase(DataModelCostanti.SENZA_FISSA_DIMORA, viaResidenza));
			// SISO-962 Fine
		}
		return validato;
	}

	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void integraDatiMancanti(CsASoggettoLAZY soggetto, String viaResidenza, String luogoResidenza){
		csASoggetto = soggetto;
		if(csASoggetto!=null){
			CsAAnagrafica ana = csASoggetto.getCsAAnagrafica();
			cognome = this.integraDatoMancante(cognome, ana.getCognome());
			nome = this.integraDatoMancante(nome, ana.getNome());
			cf = this.integraDatoMancante(cf, ana.getCf());
			Integer anno = getAnno(csASoggetto.getCsAAnagrafica().getDataNascita());
			if(annoNascita==null && anno!=null && anno>0)
				this.annoNascita = anno;
			cittadinanza = this.integraDatoMancante(cittadinanza, csASoggetto.getCsAAnagrafica().getCittadinanza());
			this.secondaCittadinanza = this.integraDatoMancante(secondaCittadinanza, csASoggetto.getCsAAnagrafica().getCittadinanza2());
			this.sesso = this.integraDatoMancante(sesso, csASoggetto.getCsAAnagrafica().getSesso());		
			this.comuneResidenza = this.integraDatoMancante(comuneResidenza, luogoResidenza);
			viaResidenza = this.integraDatoMancante(this.viaResidenza, viaResidenza);
			
			this.verificaDatiValidi(viaResidenza, luogoResidenza);
			
			this.datiValidi = this.isValorizzato();
		}
	}

	private String integraDatoMancante(String dest, String orig){
		if(StringUtils.isBlank(dest) && !StringUtils.isBlank(orig))
			dest = orig;
		return dest;
	}
}
