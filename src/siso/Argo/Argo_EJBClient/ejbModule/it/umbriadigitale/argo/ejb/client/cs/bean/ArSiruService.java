package it.umbriadigitale.argo.ejb.client.cs.bean;

import java.util.List;

import javax.ejb.Remote;

import it.umbriadigitale.argo.ejb.client.cs.dto.siru.SiruJsonProgettiDTO;

@Remote
public interface ArSiruService {
	
	public int save(List<SiruJsonProgettiDTO> progetto);

	public void aggiornaTabelleFinali();
}
