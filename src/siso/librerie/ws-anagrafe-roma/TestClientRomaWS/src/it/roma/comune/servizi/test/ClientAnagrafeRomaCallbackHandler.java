
/**
 * ClientAnagrafeRomaCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.2  Built on : Sep 06, 2010 (09:42:01 CEST)
 */

    package it.roma.comune.servizi.test;

    /**
     *  ClientAnagrafeRomaCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ClientAnagrafeRomaCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ClientAnagrafeRomaCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ClientAnagrafeRomaCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for verificaDatiAnagrafici method
            * override this method for handling normal response from verificaDatiAnagrafici operation
            */
           public void receiveResultverificaDatiAnagrafici(
                    it.roma.comune.servizi.test.ClientAnagrafeRomaStub.VerificaDatiAnagraficiResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verificaDatiAnagrafici operation
           */
            public void receiveErrorverificaDatiAnagrafici(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ricercaPerCodiceIndividuale method
            * override this method for handling normal response from ricercaPerCodiceIndividuale operation
            */
           public void receiveResultricercaPerCodiceIndividuale(
                    it.roma.comune.servizi.test.ClientAnagrafeRomaStub.RicercaPerCodiceIndividualeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ricercaPerCodiceIndividuale operation
           */
            public void receiveErrorricercaPerCodiceIndividuale(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for eseguiRicercaAnagraficaEstesa method
            * override this method for handling normal response from eseguiRicercaAnagraficaEstesa operation
            */
           public void receiveResulteseguiRicercaAnagraficaEstesa(
                    it.roma.comune.servizi.test.ClientAnagrafeRomaStub.EseguiRicercaAnagraficaEstesaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from eseguiRicercaAnagraficaEstesa operation
           */
            public void receiveErroreseguiRicercaAnagraficaEstesa(java.lang.Exception e) {
            }
                


    }
    