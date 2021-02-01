package it.webred.rulengine.brick.loadDwh.load.locazioni.accloc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.resource.cci.ResultSet;

import org.apache.commons.dbutils.DbUtils;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.exception.RulEngineException;

        

public class LocazioniFiles<T extends LocazioniEnv>  extends ImportFilesFlat<T> {
        
        private String sData;

        public LocazioniFiles(T env) {
                super(env);
                // TODO Auto-generated constructor stub
        }

        @Override
        public Timestamp getDataExport() throws RulEngineException {
                return new Timestamp(Calendar.getInstance().getTimeInMillis());
        }

        @Override
        public String getProvenienzaDefault() throws RulEngineException {
                // TODO Auto-generated method stub
                return null;
        }

        @Override
        public List<String> getValoriFromLine(String tipoRecord, String currentLine)
                        throws RulEngineException {
                
                List<String> campi = new ArrayList<String>();
                
                //nei file ACCLOC.*.A2011.* per il tipo record B il campo prog_soggetto è preceduto da due ulteriori campi 
                //(sottonumero e progressivo_negozio) di 3 caratteri ciascuno 
                //nei file di anni precedenti si aggiungono quindi 6 zeri per uniformare il tracciato alla versione più recente
                
                //quindi, nel vecchio tracciato per il tipo record B al carattere 20 si ha tipo_soggetto, che vale D oppure A
                //nel nuovo, nella stessa posizione si ha necessariamente una cifra (di prog_soggetto)
                
                //nei file ACCLOC.*.A2011.* per il tipo record A 
                //-il campo prog_soggetto è preceduto da due ulteriori campi (sottonumero e progressivo_negozio) di 3 caratteri ciascuno 
                //-il campo codice_soggetto è seguito dal campo condice_negozio di 4 caratteri (eventualmente spazi)
                //nei file di anni precedenti si aggiungono quindi 6 zeri e 4 spazi per uniformare il tracciato alla versione più recente
                
                //quindi, nel vecchio tracciato per il tipo record A al carattere 63 si ha "valuta del canone", che vale E oppure L
                //nel nuovo, nella stessa posizione si ha necessariamente una cifra (di data_inizio)
                
                if(Integer.parseInt(sData)<2011){
                        String tr = currentLine.substring(0,1);
                        if(tr.equalsIgnoreCase("B")){
                                boolean addZerosB = currentLine != null &&
                                                                        currentLine.length() >= 20 &&
                                                                        "0123456789".indexOf(currentLine.substring(19, 20)) == -1;
                                
                                if (addZerosB) {
                                        String s = currentLine.substring(0, 16) + "000000" + currentLine.substring(16);
                                        campi.add(s);
                                } else {
                                        //viene restituita la riga per intero
                                        campi.add(currentLine);
                                }
                                
                        }else if(tr.equalsIgnoreCase("A")){
                                boolean addZerosA = currentLine != null &&
                                                currentLine.length() >= 20 &&
                                                !(currentLine.substring(63, 64).equalsIgnoreCase("E") || currentLine.substring(63, 64).equalsIgnoreCase("L"));
                                
                                if(addZerosA){
                                        String s = currentLine.substring(0, 16) + "000000" + currentLine.substring(16,38)+"    "+currentLine.substring(38);
                                        campi.add(s);
                                }else
                                        campi.add(currentLine);
                                
                        }else if(tr.equalsIgnoreCase("I")){
                                
                                boolean addZerosI = currentLine != null &&
                                                currentLine.length() >= 20;
                                        
                                String s = currentLine;
                                if(addZerosI)
                                        s = currentLine.substring(0,16)+ "000000" + currentLine.substring(16);
                                
                                String stato = s.substring(26,27);
                                String flg = s.substring(27,28);
                                String codEnte = s.substring(28,32).trim();
                                String codEnteSorg=this.ctx.getBelfiore();
                                
                                boolean isComune = true;
                                if(codEnteSorg.equals(codEnte)){
                                        isComune = true;
                                }else{
                                        if(codEnte.length()==4){
                                                //Cerco il codice comune (se lo è, in siticomu)
                                                isComune = this.esisteInSiticomu(codEnte);
                                        }else 
                                                isComune = false;
                                }
                                
                                //boolean isStato = stato.equalsIgnoreCase("N")||stato.equalsIgnoreCase("U")||stato.equalsIgnoreCase("T");
                                //boolean isFlag  = flg.equalsIgnoreCase("N")||flg.equalsIgnoreCase("I")||flg.equalsIgnoreCase("P");
                                
                                //Verifico che la nuova posizione sia corretta
                                if(isComune)
                                        campi.add(s);
                                else
                                        campi.add(currentLine);
                                        
                        }else{
                                campi.add(currentLine);
                        }
                
                }else{
                        campi.add(currentLine);
                }
                
                return campi;
        }

        private boolean esisteInSiticomu(String codente){
                boolean esisteComune = false;
                Connection conn = ctx.getConnection("SITI_"+this.ctx.getBelfiore());
                PreparedStatement stmt=null;
                java.sql.ResultSet rs=null;
                try {
                        stmt = conn.prepareStatement("select * from siticomu where codi_fisc_luna= ?");
                        stmt.setString(1, codente);
                        rs = stmt.executeQuery();
                if(rs.next())
                        esisteComune = true;
                } catch (SQLException e) {
                       log.error(e);
                }finally{
                        try {
                                DbUtils.close(rs);
                                DbUtils.close(stmt);
                        } catch (SQLException e) {
                        }
                }
                return esisteComune;
        }
        
        @Override
        public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
                // TODO Auto-generated method stub
                return false;
        }

        @Override
        public void preProcesing(Connection con) throws RulEngineException {
                // TODO Auto-generated method stub
                
        }

        @Override
        protected void preProcesingFile(String file) throws RulEngineException {
                // TODO Auto-generated method stub
                
        }

        @Override
        public void sortFiles(List<String> files) throws RulEngineException {
                Collections.sort(files);
        }

        @Override
        public void tracciaFornitura(String file, String cartellaDati, String line)
                        throws RulEngineException {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                 sData = "";
                String sMeseGiornoIni = "0101";
                String sMeseGiornoFin="1231";
                if(file.startsWith("ACCLOC"))
                        sData = file.substring(13, 17);
                else if(file.startsWith("CONLOC"))
                        sData = file.substring(18, 22);
                else{
                        sData = file.substring(14, 18);
                        String trimestre = file.substring(18, 21);
                        if("090".equals(trimestre)){
                                sMeseGiornoFin = "0331";
                        }else if("181".equals(trimestre)){
                                sMeseGiornoIni = "0401";
                                sMeseGiornoFin = "0630";
                        }else if("273".equals(trimestre)){
                                sMeseGiornoIni = "0701";
                                sMeseGiornoFin = "0930";
                        }else
                                sMeseGiornoIni = "1001";
                }
                
                try {
                        env.saveFornitura(sdf.parse(sData + sMeseGiornoIni), file);
                        env.saveFornitura(sdf.parse(sData + sMeseGiornoFin), file);
                } catch (ParseException e) {    
                        log.debug("_______ ! Errore su parsing data Tracciamento fornitura: " + file);
                }
                
        }

}