package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CS_D_SINA")
@Cacheable(false)
public class CsDSina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CS_D_SINA_GENERATOR", sequenceName = "SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_D_SINA_GENERATOR")
	private Long id;

	// bi-directional many-to-one association to CsACaso
	
	@ManyToOne
    @JoinColumn(name = "DIARIO_ID", insertable = false, updatable = false)
    private CsDDiario csDDiario;
	 
	@Column(name = "INTERVENTO_ESEG_MAST_ID")
	private Long intEsegMastId;

	@Column(name = "DIARIO_ID")
	private Long diarioId;
	
	 @Column(name="DATA")
	 private Date data;
		
	 //** Mod. SISO - 886 **//
	 @Column(name="FLAG_VALUTA_DOPO")
	 private Boolean flagValutaDopo;
	 
	 @Column(name="DT_INS")
	 private Date dtIns;
	 
	 @Column(name="USER_INS")
	 private String userIns;
	 
	 @Column(name="DT_MOD")
	 private Date dtMod;
	 
	 @Column(name="USER_MOD")
	 private String userMod;
	 
	/*
	  // bi-directional many-to-many association to ArTbPrestazioniInps
	  
	  @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER )
	  
	  @Fetch(FetchMode.SELECT)
	  
	  @JoinTable(name="CS_D_SINA_PRES_INPS",
	  joinColumns={@JoinColumn(name="SINA_ID") },
	  inverseJoinColumns={@JoinColumn(name="PRESTAZIONE_INPS_ID")} ) private
	  List<ArTbPrestazioniInps> arTbPrestazioniInps;
	 */
	 
	
	public CsDSina() {
		//arTbPrestazioniInps = new ArrayList<ArTbPrestazioniInps>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	/*
	 * public void setArTbPrestazioniInps(List<ArTbPrestazioniInps>
	 * arTbPrestazioniInps) { this.arTbPrestazioniInps = arTbPrestazioniInps; }
	 */
	/*
	 * public List<ArTbPrestazioniInps> getArTbPrestazioniInps() { return
	 * this.arTbPrestazioniInps; }
	 * 
	 * public void addArTbPrestazioniInps(ArTbPrestazioniInps arTbPrestazioniInps) {
	 * if (this.arTbPrestazioniInps == null) this.arTbPrestazioniInps = new
	 * ArrayList<ArTbPrestazioniInps>();
	 * 
	 * this.arTbPrestazioniInps.add(arTbPrestazioniInps); }
	 */

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Long getIntEsegMastId() {
		return intEsegMastId;
	}

	public void setIntEsegMastId(Long intEsegMastId) {
		this.intEsegMastId = intEsegMastId;
	}

	//** Mod. SISO - 886 **//
	public boolean getFlagValutaDopo() {
		return flagValutaDopo == null ? false : flagValutaDopo;
	}

	public void setFlagValutaDopo(Boolean flagValutaDopo) {
		this.flagValutaDopo = flagValutaDopo;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario;
	}

	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
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
	
}
