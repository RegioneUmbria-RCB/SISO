package it.umbriadigitale.argo.ejb.client.cs.bean;

import java.util.List;

import it.umbriadigitale.argo.ejb.client.cs.dto.siru.SiruJsonProgettiDTO;

public interface ArSiruService {
	
	public int save(List<SiruJsonProgettiDTO> progetto);

	public void aggiornaTabelleFinali();
}
