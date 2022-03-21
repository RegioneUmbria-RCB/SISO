package it.webred.rulengine.diagnostics;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

public class DocfaNoResidenziale extends ElaboraDiagnosticsNonStandard {

	public DocfaNoResidenziale(Connection connPar, Context ctxPar,
			List<RRuleParamIn> paramsPar) {
		super(connPar, ctxPar, paramsPar);
		log = Logger.getLogger(DocfaNoResidenziale.class.getName());		
	}
	
	@Override
	protected void ElaborazioneNonStandard(DiagnosticConfigBean diaConfig,
			long idTestata) throws Exception {

		log.debug("[DocfaNoResidenziale] - Invoke class DocfaNoResidenziale ");
		super.ElaborazioneNonStandard(diaConfig, idTestata);
				
		String query = "{call DOCFA_REP_NORES()}";
		CallableStatement cstmt = getConn().prepareCall(query);				
		cstmt.execute();
				
	}
}
