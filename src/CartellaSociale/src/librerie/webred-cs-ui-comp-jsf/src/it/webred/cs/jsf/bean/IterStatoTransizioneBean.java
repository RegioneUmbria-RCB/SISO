package it.webred.cs.jsf.bean;

import it.webred.cs.data.DataModelCostanti.StatoTransizione;
import it.webred.cs.data.model.CsCfgItTransizione;

import java.io.Serializable;

public class IterStatoTransizioneBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Long idStatoA;
	private StatoTransizione.Enum eStatoOrganizzazione;
	private StatoTransizione.Enum eStatoSettore;
	private StatoTransizione.Enum eStatoOperatore;
	
	public IterStatoTransizioneBean( CsCfgItTransizione itTrans )
	{
		nome = itTrans.getNome();
		idStatoA = itTrans.getCsCfgItStatoA().getId();
		eStatoOrganizzazione = StatoTransizione.ToEnum( itTrans.getStatoOrganizzazione() );
		eStatoSettore = StatoTransizione.ToEnum( itTrans.getStatoSettore() );
		eStatoOperatore = StatoTransizione.ToEnum( itTrans.getStatoOperatore() );
	}

	public String getNome() {
		return nome;
	}

	public Long getIdStatoA() {
		return idStatoA;
	}

	public StatoTransizione.Enum getStatoOrganizzazione() {
		return eStatoOrganizzazione;
	}

	public StatoTransizione.Enum getStatoSettore() {
		return eStatoSettore;
	}

	public StatoTransizione.Enum getStatoOperatore() {
		return eStatoOperatore;
	}

}