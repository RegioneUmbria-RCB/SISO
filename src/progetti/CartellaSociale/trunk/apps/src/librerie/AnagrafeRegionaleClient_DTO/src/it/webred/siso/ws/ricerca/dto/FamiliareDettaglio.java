package it.webred.siso.ws.ricerca.dto;

import it.webred.cs.data.model.CsTbTipoRapportoCon;

public class FamiliareDettaglio extends PersonaDettaglio{
	
	public CsTbTipoRapportoCon parentela;
	public boolean parentelaValida;

	public CsTbTipoRapportoCon getParentela() {
		return parentela;
	}

	public void setParentela(CsTbTipoRapportoCon parentela) {
		this.parentela = parentela;
	}

	public boolean isParentelaValida() {
		return parentelaValida;
	}

	public void setParentelaValida(boolean parentelaValida) {
		this.parentelaValida = parentelaValida;
	}
}
