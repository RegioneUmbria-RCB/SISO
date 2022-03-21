package it.webred.cs.data.base;

import it.webred.cs.data.model.CsDDiario;

import java.io.Serializable;

public interface ICsDDiarioChild extends Serializable {

	public Long getDiarioId();

	public void setDiarioId(Long diarioId);

	public CsDDiario getCsDDiario();

	public void setCsDDiario(CsDDiario csDDiario);

}