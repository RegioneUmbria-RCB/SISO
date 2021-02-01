package it.webred.ct.data.model.diagnostiche;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DocfaReportSoggPK implements Serializable {
	
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FK_ID_REP")
	private String fkIdRep;
	
	@Column(name="PK_CUAA")
	private BigDecimal pkCuaa;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_POSSESSO")
	private Date dataFinePossesso;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_POSSESSO")
	private Date dataInizioPossesso;

	public void setFkIdRep(String fkIdRep) {
		this.fkIdRep = fkIdRep;
	}

	public String getFkIdRep() {
		return fkIdRep;
	}

	public void setPkCuaa(BigDecimal pkCuaa) {
		this.pkCuaa = pkCuaa;
	}

	public BigDecimal getPkCuaa() {
		return pkCuaa;
	}
	
	public Date getDataFinePossesso() {
		return this.dataFinePossesso;
	}

	public void setDataFinePossesso(Date dataFinePossesso) {
		this.dataFinePossesso = dataFinePossesso;
	}

	public Date getDataInizioPossesso() {
		return this.dataInizioPossesso;
	}

	public void setDataInizioPossesso(Date dataInizioPossesso) {
		this.dataInizioPossesso = dataInizioPossesso;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if(obj instanceof DocfaReportSoggPK){
			
		DocfaReportSoggPK pk = (DocfaReportSoggPK) obj;
		return fkIdRep.equals(pk.getFkIdRep())
				&& pkCuaa.equals(pk.getPkCuaa())
				&& dataFinePossesso.compareTo(pk.getDataFinePossesso()) ==0
				&& dataInizioPossesso.compareTo(pk.getDataInizioPossesso()) ==0;
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fkIdRep.hashCode();
		hash = hash * prime + ((int) (this.pkCuaa.hashCode() ^ (this.pkCuaa.hashCode() >>> 32)));
		hash = hash * prime + this.dataFinePossesso.hashCode();
		hash = hash * prime + this.dataInizioPossesso.hashCode();
		
		return hash;
    }
}
