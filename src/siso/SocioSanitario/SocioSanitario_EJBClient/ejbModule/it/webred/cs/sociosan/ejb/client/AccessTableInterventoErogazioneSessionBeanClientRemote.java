package it.webred.cs.sociosan.ejb.client;

import it.webred.cs.csa.ejb.dto.mobi.FindCasiByOperatoreBySoggettoRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreResponseDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadInterventiErogazioneResponseDTO;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.support.validation.CeTToken;

import javax.ejb.Remote;

@Remote
public interface AccessTableInterventoErogazioneSessionBeanClientRemote {
	
	public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findInterventoErogazioneByIdSettoreEroganteData(CeTToken tok, it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO input) throws SocioSanitarioException ;
	
	public FindCasiByUsernameOperatoreResponseDTO findCasiByUsernameOperatore (CeTToken tok, FindCasiByUsernameOperatoreRequestDTO input) throws SocioSanitarioException ;
	
	public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findCasiByOperatoreBySoggetto (CeTToken tok, FindCasiByOperatoreBySoggettoRequestDTO input) throws SocioSanitarioException ;
	
	public UploadInterventiErogazioneResponseDTO uploadDatiMobile (CeTToken tok, String json);
	
	public void elaboraUploadDatiMobile (CeTToken tok);

	public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findInterventoErogazioneByIdSettoreEroganteDataCasoId(
			CeTToken token,
			FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO input) throws SocioSanitarioException;

	}
