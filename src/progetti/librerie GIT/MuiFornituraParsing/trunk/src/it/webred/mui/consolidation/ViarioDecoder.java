package it.webred.mui.consolidation;

import it.webred.mui.AppProperties;
import it.webred.mui.model.CodiceViaCivico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import net.skillbill.logging.Logger;

public class ViarioDecoder {

	private String decodeViaStatement = "select prefisso || ' ' || nome " +
										"from sitidstr where numero = ?";
	private String decodeViaStatement2 = "select viasedime || ' ' || descrizione " +
											"from sit_d_via where id_orig = ?";
	private String decodeViaTotaleStatement = "select vt.sedime ||' '||vt.indirizzo " +
												"from diogene_c349.sit_civico_unico c, diogene_c349.sit_via_totale " +
												"where id_civico = ? " +
												"and c.fk_via = vt.fk_via " +
												"and vt.fk_ente_sorgente = ? " +
												"and vt.prog_es = ?";
	

	public ViarioDecoder()  {
		super();
	}
	
	private String decodeViaTotale(it.webred.mui.model.Residenza res) throws SQLException, NamingException {
		String cu = res.getCivicoUnico();
		return decodeViaInViario(cu, 1, 1);
	}
	
	private String decodeViaTotale(CodiceViaCivico cvc) throws SQLException, NamingException {
		String cu = cvc.getCivicoUnico();
		return decodeViaInViario(cu, 4, 1);
		
		
	}
	
	private String decodeViaInViario(String civicoUnico, int es, int progEs) throws SQLException, NamingException {
		
		if(civicoUnico == null || civicoUnico.trim().length() == 0){
			return null;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			conn = AppProperties.getConnection();
			pstmt = conn.prepareStatement(decodeViaTotaleStatement);
			Logger.log().info(
					this.getClass().getName(),
					"ViarioDecoder decodeViaTotaleStatement query is \"" + decodeViaTotaleStatement.replace("\n","")
							+ "\"");
			pstmt.setString(1, civicoUnico);
			pstmt.setInt(2, es);
			pstmt.setInt(3, progEs);
			
			pstmt.setString(1, civicoUnico);
			
			Logger.log()
					.info(
							this.getClass().getName(),
							"ViarioDecoder adding parameter " + civicoUnico
									+ " to decodeViaStatement query ");
			rset = pstmt.executeQuery();
			String result = null;
			if (rset.next()) {
				result = rset.getString(1);
			}
			return result;
		} catch (NumberFormatException e) {
			Logger.log().error(this.getClass().getName(),
					"error while decoding via totale", e);
			return null;
		} catch (SQLException e) {
			Logger.log().error(this.getClass().getName(),
					"error while decoding via totale ", e);
			return null;
		} catch (NamingException e) {
			Logger.log().error(this.getClass().getName(),
					"error while decoding via totale ", e);
			return null;
		} finally {
			try {
				rset.close();
			} catch (Throwable e) {
			}
			try {
				pstmt.close();
			} catch (Throwable e) {
			}
			try {
				conn.close();
			} catch (Throwable e) {
			}
		}
	}
	
	private String decodeViaInAnagrafe(String codiceVia) throws SQLException, NamingException {
		
		if(codiceVia == null || codiceVia.trim().length() == 0){
			return null;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			conn = AppProperties.getConnection();
			pstmt = conn.prepareStatement(decodeViaStatement2);
			Logger.log().info(
					this.getClass().getName(),
					"ViarioDecoder decodeViaStatement query is \"" + decodeViaStatement2.replace("\n","")
							+ "\"");
			pstmt.setString(1, codiceVia);
			Logger.log()
					.info(
							this.getClass().getName(),
							"ViarioDecoder adding parameter " + codiceVia
									+ " to decodeViaStatement query ");
			rset = pstmt.executeQuery();
			String result = null;
			while (rset.next()) {
				result = rset.getString(1);
			}
			return result;
		} catch (NumberFormatException e) {
			Logger.log().error(this.getClass().getName(),
					"error while decoding via ", e);
			return null;
		} catch (SQLException e) {
			Logger.log().error(this.getClass().getName(),
					"error while decoding via ", e);
			return null;
		} catch (NamingException e) {
			Logger.log().error(this.getClass().getName(),
					"error while decoding via ", e);
			return null;
		} finally {
			try {
				rset.close();
			} catch (Throwable e) {
			}
			try {
				pstmt.close();
			} catch (Throwable e) {
			}
			try {
				conn.close();
			} catch (Throwable e) {
			}
		}
	}

	public String decodeViaAna(it.webred.mui.model.Residenza res) throws SQLException, NamingException {
		if (res.getCivicoUnico()!= null)
			return decodeViaTotale(res);
		else
			return decodeViaInAnagrafe(res.getCodiceVia());
		
	}
	
	public String decodeViaSIT(CodiceViaCivico cvc) throws SQLException, NamingException {
		if (cvc.getCivicoUnico()!=null)
			return decodeViaTotale(cvc);
			
		String codiceVia = cvc.getCodiceVia();
		
		if(codiceVia == null || codiceVia.trim().length() == 0){
			return null;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			conn = AppProperties.getConnection();
			pstmt = conn.prepareStatement(decodeViaStatement);
			Logger.log().info(
					this.getClass().getName(),
					"ViarioDecoder decodeViaStatement query is \"" + decodeViaStatement.replace("\n","")
							+ "\"");
			pstmt.setString(1, codiceVia);
			Logger.log()
					.info(
							this.getClass().getName(),
							"ViarioDecoder adding parameter " + codiceVia
									+ " to decodeViaStatement query ");
			rset = pstmt.executeQuery();
			String result = null;
			while (rset.next()) {
				result = rset.getString(1);
			}
			return result;
		} catch (NumberFormatException e) {
			Logger.log().error(this.getClass().getName(),
					"error while decoding via ", e);
			return null;
		} catch (SQLException e) {
			Logger.log().error(this.getClass().getName(),
					"error while decoding via ", e);
			return null;
		} catch (NamingException e) {
			Logger.log().error(this.getClass().getName(),
					"error while decoding via ", e);
			return null;
		} finally {
			try {
				rset.close();
			} catch (Throwable e) {
			}
			try {
				pstmt.close();
			} catch (Throwable e) {
			}
			try {
				conn.close();
			} catch (Throwable e) {
			}
		}
	}

}
