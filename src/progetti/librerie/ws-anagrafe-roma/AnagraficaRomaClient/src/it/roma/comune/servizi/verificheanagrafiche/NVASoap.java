/**
 * NVASoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public interface NVASoap extends java.rmi.Remote {

    /**
     * Effettua una verifica anagrafica completa
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponse verificaAnagrafica(it.roma.comune.servizi.verificheanagrafiche.VerificaAnagrafica parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Interrogazione per Legge Rutelli
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponse verificaLeggeRutelli(it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelli parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Ricerca x arco temporale
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponse verificaAnagraficaEstesa(it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesa parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Interrogazione documenti
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponse verificaDocumenti(it.roma.comune.servizi.verificheanagrafiche.VerificaDocumenti parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Verifica Individuale Carta di Identità
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponse verificaCartaIdent(it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdent parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Verifica Posizione Elettorale
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaElettoraleResponse verificaElettorale(it.roma.comune.servizi.verificheanagrafiche.VerificaElettorale parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Verifica Posizione Servizio Militare
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponse verificaLevaMilitare(it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitare parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Verifica Situazione Famiglia e Convivenza
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponse verificaSitFamigliaConv(it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConv parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Verifica Stato di Famiglia e Convivenza
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponse verificaStatoFamigliaConv(it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConv parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Verifica Storico Documenti
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponse verificaStoricaDocumenti(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumenti parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Verifica Storico Individuo
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponse verificaStoricaIndividuo(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuo parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Verifica Vaccinazioni
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponse verificaVaccinazioni(it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioni parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Ricerca Domiciliati per indirizzo
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponse verificaDomiciliatiperIndirizzo(it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzo parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Ricerca Domiciliati per intervallo di civici, lotti, palazzi
     * o kilometri
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponse verificaDomXRangeCivici(it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCivici parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Verifica Carta di Identità (ricerca carta)
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponse verificaCartaId(it.roma.comune.servizi.verificheanagrafiche.VerificaCartaId parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;

    /**
     * Verifica Storico di Damiglia con ricerca per data
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponse verificaStoricoFamigliaXData(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXData parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException;
}
