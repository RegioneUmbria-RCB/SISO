package it.webred.cs.jsf.interfaces;

import it.webred.siso.ws.client.atlante.client.dto.GetServiziOspiteDTO;

import java.util.List;

public interface IServiziSanitari {
	
	public List<GetServiziOspiteDTO> getServiziSanitari();
	public boolean isRendered(String cf);

}
