package it.webred.rulengine.brick.curit.bean;

import java.io.Serializable;

public class CuritBean implements Serializable {

	private static final long serialVersionUID = -3066229315389341790L;
	
	private String identificativo_impianto;
	private String generatori_numero;
	private String potenza_impianto_risc;
	private String potenza_impianto_acs;
	private String potenza_impianto_raff;
	private String ubicazione_toponimo;
	private String ubicazione_indirizzo;
	private String ubicazione_civico;
	private String ubicazione_comune;
	private String ubicazione_provincia;
	private String ubicazione_cap;
	private String ubicazione_codice_istat;
	private String catasto_sezione;
	private String catasto_foglio;
	private String catasto_particella;
	private String catasto_subalterno;
	private String edificio_categoria;
	private String volumetria_risc;
	private String volumetria_raff;
	private String regolazione;
	private String contabilizzazione;
	private String emissione;
	private String ape_presenza;
	private String ape_codice;
	private String generatore_categoria;
	private String generatore_progressivo;
	private String generatore_potenza;
	private String generatore_servizi;
	private String generatore_fabbricante;
	private String generatore_tipologia;
	private String generatore_combustibile;
	private String generatore_data_inst;
	private String generatore_anno_inst;
	private String generatore_tecnologia;
	private String generatore_rend_nom;
	private String rapporto_di_controllo_data;
	private String rapporto_di_controllo_anno;
	private String rap_di_controllo_esito;
	private String rap_di_controllo_rendimento;
	private String rap_di_controllo_bacharach;
	private String ispezione_aut_comp;
	private String ispezione_data;
	private String ispezione_esito;
	
	public CuritBean(){}
	
	public String getIdentificativo_impianto() {
		return identificativo_impianto;
	}
	public void setIdentificativo_impianto(String identificativo_impianto) {
		this.identificativo_impianto = identificativo_impianto;
	}
	public String getGeneratori_numero() {
		return generatori_numero;
	}
	public void setGeneratori_numero(String generatori_numero) {
		this.generatori_numero = generatori_numero;
	}
	public String getPotenza_impianto_risc() {
		return potenza_impianto_risc;
	}
	public void setPotenza_impianto_risc(String potenza_impianto_risc) {
		this.potenza_impianto_risc = potenza_impianto_risc;
	}
	public String getPotenza_impianto_acs() {
		return potenza_impianto_acs;
	}
	public void setPotenza_impianto_acs(String potenza_impianto_acs) {
		this.potenza_impianto_acs = potenza_impianto_acs;
	}
	public String getPotenza_impianto_raff() {
		return potenza_impianto_raff;
	}
	public void setPotenza_impianto_raff(String potenza_impianto_raff) {
		this.potenza_impianto_raff = potenza_impianto_raff;
	}
	public String getUbicazione_toponimo() {
		return ubicazione_toponimo;
	}
	public void setUbicazione_toponimo(String ubicazione_toponimo) {
		this.ubicazione_toponimo = ubicazione_toponimo;
	}
	public String getUbicazione_indirizzo() {
		return ubicazione_indirizzo;
	}
	public void setUbicazione_indirizzo(String ubicazione_indirizzo) {
		this.ubicazione_indirizzo = ubicazione_indirizzo;
	}
	public String getUbicazione_civico() {
		return ubicazione_civico;
	}
	public void setUbicazione_civico(String ubicazione_civico) {
		this.ubicazione_civico = ubicazione_civico;
	}
	public String getUbicazione_comune() {
		return ubicazione_comune;
	}
	public void setUbicazione_comune(String ubicazione_comune) {
		this.ubicazione_comune = ubicazione_comune;
	}
	public String getUbicazione_provincia() {
		return ubicazione_provincia;
	}
	public void setUbicazione_provincia(String ubicazione_provincia) {
		this.ubicazione_provincia = ubicazione_provincia;
	}
	public String getUbicazione_cap() {
		return ubicazione_cap;
	}
	public void setUbicazione_cap(String ubicazione_cap) {
		this.ubicazione_cap = ubicazione_cap;
	}
	public String getUbicazione_codice_istat() {
		return ubicazione_codice_istat;
	}
	public void setUbicazione_codice_istat(String ubicazione_codice_istat) {
		this.ubicazione_codice_istat = ubicazione_codice_istat;
	}
	public String getCatasto_sezione() {
		return catasto_sezione;
	}
	public void setCatasto_sezione(String catasto_sezione) {
		this.catasto_sezione = catasto_sezione;
	}
	public String getCatasto_foglio() {
		return catasto_foglio;
	}
	public void setCatasto_foglio(String catasto_foglio) {
		this.catasto_foglio = catasto_foglio;
	}
	public String getCatasto_particella() {
		return catasto_particella;
	}
	public void setCatasto_particella(String catasto_particella) {
		this.catasto_particella = catasto_particella;
	}
	public String getCatasto_subalterno() {
		return catasto_subalterno;
	}
	public void setCatasto_subalterno(String catasto_subalterno) {
		this.catasto_subalterno = catasto_subalterno;
	}
	public String getEdificio_categoria() {
		return edificio_categoria;
	}
	public void setEdificio_categoria(String edificio_categoria) {
		this.edificio_categoria = edificio_categoria;
	}
	public String getVolumetria_risc() {
		return volumetria_risc;
	}
	public void setVolumetria_risc(String volumetria_risc) {
		this.volumetria_risc = volumetria_risc;
	}
	public String getVolumetria_raff() {
		return volumetria_raff;
	}
	public void setVolumetria_raff(String volumetria_raff) {
		this.volumetria_raff = volumetria_raff;
	}
	public String getRegolazione() {
		return regolazione;
	}
	public void setRegolazione(String regolazione) {
		this.regolazione = regolazione;
	}
	public String getContabilizzazione() {
		return contabilizzazione;
	}
	public void setContabilizzazione(String contabilizzazione) {
		this.contabilizzazione = contabilizzazione;
	}
	public String getEmissione() {
		return emissione;
	}
	public void setEmissione(String emissione) {
		this.emissione = emissione;
	}
	public String getApe_presenza() {
		return ape_presenza;
	}
	public void setApe_presenza(String ape_presenza) {
		this.ape_presenza = ape_presenza;
	}
	public String getApe_codice() {
		return ape_codice;
	}
	public void setApe_codice(String ape_codice) {
		this.ape_codice = ape_codice;
	}
	public String getGeneratore_categoria() {
		return generatore_categoria;
	}
	public void setGeneratore_categoria(String generatore_categoria) {
		this.generatore_categoria = generatore_categoria;
	}
	public String getGeneratore_progressivo() {
		return generatore_progressivo;
	}
	public void setGeneratore_progressivo(String generatore_progressivo) {
		this.generatore_progressivo = generatore_progressivo;
	}
	public String getGeneratore_potenza() {
		return generatore_potenza;
	}
	public void setGeneratore_potenza(String generatore_potenza) {
		this.generatore_potenza = generatore_potenza;
	}
	public String getGeneratore_servizi() {
		return generatore_servizi;
	}
	public void setGeneratore_servizi(String generatore_servizi) {
		this.generatore_servizi = generatore_servizi;
	}
	public String getGeneratore_fabbricante() {
		return generatore_fabbricante;
	}
	public void setGeneratore_fabbricante(String generatore_fabbricante) {
		this.generatore_fabbricante = generatore_fabbricante;
	}
	public String getGeneratore_tipologia() {
		return generatore_tipologia;
	}
	public void setGeneratore_tipologia(String generatore_tipologia) {
		this.generatore_tipologia = generatore_tipologia;
	}
	public String getGeneratore_combustibile() {
		return generatore_combustibile;
	}
	public void setGeneratore_combustibile(String generatore_combustibile) {
		this.generatore_combustibile = generatore_combustibile;
	}
	public String getGeneratore_data_inst() {
		return generatore_data_inst;
	}
	public void setGeneratore_data_inst(String generatore_data_inst) {
		this.generatore_data_inst = generatore_data_inst;
	}
	public String getGeneratore_anno_inst() {
		return generatore_anno_inst;
	}
	public void setGeneratore_anno_inst(String generatore_anno_inst) {
		this.generatore_anno_inst = generatore_anno_inst;
	}
	public String getGeneratore_tecnologia() {
		return generatore_tecnologia;
	}
	public void setGeneratore_tecnologia(String generatore_tecnologia) {
		this.generatore_tecnologia = generatore_tecnologia;
	}
	public String getGeneratore_rend_nom() {
		return generatore_rend_nom;
	}
	public void setGeneratore_rend_nom(String generatore_rend_nom) {
		this.generatore_rend_nom = generatore_rend_nom;
	}
	public String getRapporto_di_controllo_data() {
		return rapporto_di_controllo_data;
	}
	public void setRapporto_di_controllo_data(String rapporto_di_controllo_data) {
		this.rapporto_di_controllo_data = rapporto_di_controllo_data;
	}
	public String getRapporto_di_controllo_anno() {
		return rapporto_di_controllo_anno;
	}
	public void setRapporto_di_controllo_anno(String rapporto_di_controllo_anno) {
		this.rapporto_di_controllo_anno = rapporto_di_controllo_anno;
	}
	public String getRap_di_controllo_esito() {
		return rap_di_controllo_esito;
	}
	public void setRap_di_controllo_esito(String rap_di_controllo_esito) {
		this.rap_di_controllo_esito = rap_di_controllo_esito;
	}
	public String getRap_di_controllo_rendimento() {
		return rap_di_controllo_rendimento;
	}
	public void setRap_di_controllo_rendimento(String rap_di_controllo_rendimento) {
		this.rap_di_controllo_rendimento = rap_di_controllo_rendimento;
	}
	public String getRap_di_controllo_bacharach() {
		return rap_di_controllo_bacharach;
	}
	public void setRap_di_controllo_bacharach(String rap_di_controllo_bacharach) {
		this.rap_di_controllo_bacharach = rap_di_controllo_bacharach;
	}
	public String getIspezione_aut_comp() {
		return ispezione_aut_comp;
	}
	public void setIspezione_aut_comp(String ispezione_aut_comp) {
		this.ispezione_aut_comp = ispezione_aut_comp;
	}
	public String getIspezione_data() {
		return ispezione_data;
	}
	public void setIspezione_data(String ispezione_data) {
		this.ispezione_data = ispezione_data;
	}
	public String getIspezione_esito() {
		return ispezione_esito;
	}
	public void setIspezione_esito(String ispezione_esito) {
		this.ispezione_esito = ispezione_esito;
	}
	
	
}
