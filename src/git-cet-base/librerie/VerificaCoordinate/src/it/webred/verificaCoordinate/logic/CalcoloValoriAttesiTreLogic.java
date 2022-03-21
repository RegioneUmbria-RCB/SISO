package it.webred.verificaCoordinate.logic;

import it.webred.verificaCoordinate.dto.request.VerificaCoordinateRequestDTO;
import it.webred.verificaCoordinate.dto.response.VerificaCoordinateResponseDTO;

import java.sql.Connection;

public class CalcoloValoriAttesiTreLogic extends CalcoloValoriAttesiLogic {
	
	public CalcoloValoriAttesiTreLogic(String codEnte, Connection extConn) {
		super(codEnte, extConn);
	}
	
	public void calcola(VerificaCoordinateRequestDTO params, VerificaCoordinateResponseDTO resp) throws Exception {
		//caso non implementato
	}

}