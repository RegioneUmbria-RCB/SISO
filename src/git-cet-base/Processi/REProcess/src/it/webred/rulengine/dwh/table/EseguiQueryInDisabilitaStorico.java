package it.webred.rulengine.dwh.table;

/**
 * @author Utente
 * Questa interfaccia tipizza una TabellaDwh come tabella i cui dati provengono da un record di traciato denormalizzato.
 * Serve a prevenire che tabelle di base o comunque di decodifica i cui dati sono contenuti all'interno di un tracciato in modo
 * ripetuto , siano soggette a inserimenti non necessari
 * 
 * Questa interfaccia viene solo verificata nel caso che fornitura in replace
 */
public interface EseguiQueryInDisabilitaStorico {

}
