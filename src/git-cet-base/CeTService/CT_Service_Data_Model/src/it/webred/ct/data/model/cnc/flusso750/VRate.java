package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the RE_CNC_R7G_RATE database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R7G_RATE")
public class VRate implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;


	@Embedded
	private ChiaveULPartita chiavePartita;
	
	@Column(name="COD_ENTRATA")
	private String codEntrata;

	@Column(name="COD_TIPO_ENTRATA")
	private String codTipoEntrata;



    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Column(name="IMPORTO_RATA_1")
	private String importoRata1;

	@Column(name="IMPORTO_RATA_10")
	private String importoRata10;

	@Column(name="IMPORTO_RATA_11")
	private String importoRata11;

	@Column(name="IMPORTO_RATA_12")
	private String importoRata12;

	@Column(name="IMPORTO_RATA_13")
	private String importoRata13;

	@Column(name="IMPORTO_RATA_14")
	private String importoRata14;

	@Column(name="IMPORTO_RATA_15")
	private String importoRata15;

	@Column(name="IMPORTO_RATA_16")
	private String importoRata16;

	@Column(name="IMPORTO_RATA_17")
	private String importoRata17;

	@Column(name="IMPORTO_RATA_18")
	private String importoRata18;

	@Column(name="IMPORTO_RATA_19")
	private String importoRata19;

	@Column(name="IMPORTO_RATA_2")
	private String importoRata2;

	@Column(name="IMPORTO_RATA_20")
	private String importoRata20;

	@Column(name="IMPORTO_RATA_3")
	private String importoRata3;

	@Column(name="IMPORTO_RATA_4")
	private String importoRata4;

	@Column(name="IMPORTO_RATA_5")
	private String importoRata5;

	@Column(name="IMPORTO_RATA_6")
	private String importoRata6;

	@Column(name="IMPORTO_RATA_7")
	private String importoRata7;

	@Column(name="IMPORTO_RATA_8")
	private String importoRata8;

	@Column(name="IMPORTO_RATA_9")
	private String importoRata9;

	private String processid;

	@Column(name="PROGRESSIVO_ARTICOLO_RUOLO")
	private String progressivoArticoloRuolo;

	@Column(name="PROGRESSIVO_RATA_1")
	private String progressivoRata1;

	@Column(name="PROGRESSIVO_RATA_10")
	private String progressivoRata10;

	@Column(name="PROGRESSIVO_RATA_11")
	private String progressivoRata11;

	@Column(name="PROGRESSIVO_RATA_12")
	private String progressivoRata12;

	@Column(name="PROGRESSIVO_RATA_13")
	private String progressivoRata13;

	@Column(name="PROGRESSIVO_RATA_14")
	private String progressivoRata14;

	@Column(name="PROGRESSIVO_RATA_15")
	private String progressivoRata15;

	@Column(name="PROGRESSIVO_RATA_16")
	private String progressivoRata16;

	@Column(name="PROGRESSIVO_RATA_17")
	private String progressivoRata17;

	@Column(name="PROGRESSIVO_RATA_18")
	private String progressivoRata18;

	@Column(name="PROGRESSIVO_RATA_19")
	private String progressivoRata19;

	@Column(name="PROGRESSIVO_RATA_2")
	private String progressivoRata2;

	@Column(name="PROGRESSIVO_RATA_20")
	private String progressivoRata20;

	@Column(name="PROGRESSIVO_RATA_3")
	private String progressivoRata3;

	@Column(name="PROGRESSIVO_RATA_4")
	private String progressivoRata4;

	@Column(name="PROGRESSIVO_RATA_5")
	private String progressivoRata5;

	@Column(name="PROGRESSIVO_RATA_6")
	private String progressivoRata6;

	@Column(name="PROGRESSIVO_RATA_7")
	private String progressivoRata7;

	@Column(name="PROGRESSIVO_RATA_8")
	private String progressivoRata8;

	@Column(name="PROGRESSIVO_RATA_9")
	private String progressivoRata9;

	@Column(name="PROGRESSIVO_RECORD")
	private String progressivoRecord;


	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

    public VRate() {
    }



	public String getCodEntrata() {
		return this.codEntrata;
	}

	public void setCodEntrata(String codEntrata) {
		this.codEntrata = codEntrata;
	}

	public String getCodTipoEntrata() {
		return this.codTipoEntrata;
	}

	public void setCodTipoEntrata(String codTipoEntrata) {
		this.codTipoEntrata = codTipoEntrata;
	}



	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getImportoRata1() {
		return this.importoRata1;
	}

	public void setImportoRata1(String importoRata1) {
		this.importoRata1 = importoRata1;
	}

	public String getImportoRata10() {
		return this.importoRata10;
	}

	public void setImportoRata10(String importoRata10) {
		this.importoRata10 = importoRata10;
	}

	public String getImportoRata11() {
		return this.importoRata11;
	}

	public void setImportoRata11(String importoRata11) {
		this.importoRata11 = importoRata11;
	}

	public String getImportoRata12() {
		return this.importoRata12;
	}

	public void setImportoRata12(String importoRata12) {
		this.importoRata12 = importoRata12;
	}

	public String getImportoRata13() {
		return this.importoRata13;
	}

	public void setImportoRata13(String importoRata13) {
		this.importoRata13 = importoRata13;
	}

	public String getImportoRata14() {
		return this.importoRata14;
	}

	public void setImportoRata14(String importoRata14) {
		this.importoRata14 = importoRata14;
	}

	public String getImportoRata15() {
		return this.importoRata15;
	}

	public void setImportoRata15(String importoRata15) {
		this.importoRata15 = importoRata15;
	}

	public String getImportoRata16() {
		return this.importoRata16;
	}

	public void setImportoRata16(String importoRata16) {
		this.importoRata16 = importoRata16;
	}

	public String getImportoRata17() {
		return this.importoRata17;
	}

	public void setImportoRata17(String importoRata17) {
		this.importoRata17 = importoRata17;
	}

	public String getImportoRata18() {
		return this.importoRata18;
	}

	public void setImportoRata18(String importoRata18) {
		this.importoRata18 = importoRata18;
	}

	public String getImportoRata19() {
		return this.importoRata19;
	}

	public void setImportoRata19(String importoRata19) {
		this.importoRata19 = importoRata19;
	}

	public String getImportoRata2() {
		return this.importoRata2;
	}

	public void setImportoRata2(String importoRata2) {
		this.importoRata2 = importoRata2;
	}

	public String getImportoRata20() {
		return this.importoRata20;
	}

	public void setImportoRata20(String importoRata20) {
		this.importoRata20 = importoRata20;
	}

	public String getImportoRata3() {
		return this.importoRata3;
	}

	public void setImportoRata3(String importoRata3) {
		this.importoRata3 = importoRata3;
	}

	public String getImportoRata4() {
		return this.importoRata4;
	}

	public void setImportoRata4(String importoRata4) {
		this.importoRata4 = importoRata4;
	}

	public String getImportoRata5() {
		return this.importoRata5;
	}

	public void setImportoRata5(String importoRata5) {
		this.importoRata5 = importoRata5;
	}

	public String getImportoRata6() {
		return this.importoRata6;
	}

	public void setImportoRata6(String importoRata6) {
		this.importoRata6 = importoRata6;
	}

	public String getImportoRata7() {
		return this.importoRata7;
	}

	public void setImportoRata7(String importoRata7) {
		this.importoRata7 = importoRata7;
	}

	public String getImportoRata8() {
		return this.importoRata8;
	}

	public void setImportoRata8(String importoRata8) {
		this.importoRata8 = importoRata8;
	}

	public String getImportoRata9() {
		return this.importoRata9;
	}

	public void setImportoRata9(String importoRata9) {
		this.importoRata9 = importoRata9;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProgressivoArticoloRuolo() {
		return this.progressivoArticoloRuolo;
	}

	public void setProgressivoArticoloRuolo(String progressivoArticoloRuolo) {
		this.progressivoArticoloRuolo = progressivoArticoloRuolo;
	}

	public String getProgressivoRata1() {
		return this.progressivoRata1;
	}

	public void setProgressivoRata1(String progressivoRata1) {
		this.progressivoRata1 = progressivoRata1;
	}

	public String getProgressivoRata10() {
		return this.progressivoRata10;
	}

	public void setProgressivoRata10(String progressivoRata10) {
		this.progressivoRata10 = progressivoRata10;
	}

	public String getProgressivoRata11() {
		return this.progressivoRata11;
	}

	public void setProgressivoRata11(String progressivoRata11) {
		this.progressivoRata11 = progressivoRata11;
	}

	public String getProgressivoRata12() {
		return this.progressivoRata12;
	}

	public void setProgressivoRata12(String progressivoRata12) {
		this.progressivoRata12 = progressivoRata12;
	}

	public String getProgressivoRata13() {
		return this.progressivoRata13;
	}

	public void setProgressivoRata13(String progressivoRata13) {
		this.progressivoRata13 = progressivoRata13;
	}

	public String getProgressivoRata14() {
		return this.progressivoRata14;
	}

	public void setProgressivoRata14(String progressivoRata14) {
		this.progressivoRata14 = progressivoRata14;
	}

	public String getProgressivoRata15() {
		return this.progressivoRata15;
	}

	public void setProgressivoRata15(String progressivoRata15) {
		this.progressivoRata15 = progressivoRata15;
	}

	public String getProgressivoRata16() {
		return this.progressivoRata16;
	}

	public void setProgressivoRata16(String progressivoRata16) {
		this.progressivoRata16 = progressivoRata16;
	}

	public String getProgressivoRata17() {
		return this.progressivoRata17;
	}

	public void setProgressivoRata17(String progressivoRata17) {
		this.progressivoRata17 = progressivoRata17;
	}

	public String getProgressivoRata18() {
		return this.progressivoRata18;
	}

	public void setProgressivoRata18(String progressivoRata18) {
		this.progressivoRata18 = progressivoRata18;
	}

	public String getProgressivoRata19() {
		return this.progressivoRata19;
	}

	public void setProgressivoRata19(String progressivoRata19) {
		this.progressivoRata19 = progressivoRata19;
	}

	public String getProgressivoRata2() {
		return this.progressivoRata2;
	}

	public void setProgressivoRata2(String progressivoRata2) {
		this.progressivoRata2 = progressivoRata2;
	}

	public String getProgressivoRata20() {
		return this.progressivoRata20;
	}

	public void setProgressivoRata20(String progressivoRata20) {
		this.progressivoRata20 = progressivoRata20;
	}

	public String getProgressivoRata3() {
		return this.progressivoRata3;
	}

	public void setProgressivoRata3(String progressivoRata3) {
		this.progressivoRata3 = progressivoRata3;
	}

	public String getProgressivoRata4() {
		return this.progressivoRata4;
	}

	public void setProgressivoRata4(String progressivoRata4) {
		this.progressivoRata4 = progressivoRata4;
	}

	public String getProgressivoRata5() {
		return this.progressivoRata5;
	}

	public void setProgressivoRata5(String progressivoRata5) {
		this.progressivoRata5 = progressivoRata5;
	}

	public String getProgressivoRata6() {
		return this.progressivoRata6;
	}

	public void setProgressivoRata6(String progressivoRata6) {
		this.progressivoRata6 = progressivoRata6;
	}

	public String getProgressivoRata7() {
		return this.progressivoRata7;
	}

	public void setProgressivoRata7(String progressivoRata7) {
		this.progressivoRata7 = progressivoRata7;
	}

	public String getProgressivoRata8() {
		return this.progressivoRata8;
	}

	public void setProgressivoRata8(String progressivoRata8) {
		this.progressivoRata8 = progressivoRata8;
	}

	public String getProgressivoRata9() {
		return this.progressivoRata9;
	}

	public void setProgressivoRata9(String progressivoRata9) {
		this.progressivoRata9 = progressivoRata9;
	}

	public String getProgressivoRecord() {
		return this.progressivoRecord;
	}

	public void setProgressivoRecord(String progressivoRecord) {
		this.progressivoRecord = progressivoRecord;
	}


	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}