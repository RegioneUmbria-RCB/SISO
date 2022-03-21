
/**
 * Toponomastica40CallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */

    package org.tempuri;

    /**
     *  Toponomastica40CallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class Toponomastica40CallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public Toponomastica40CallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public Toponomastica40CallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for sitGetInfo method
            * override this method for handling normal response from sitGetInfo operation
            */
           public void receiveResultsitGetInfo(
                    org.tempuri.Toponomastica40Stub.SitGetInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetInfo operation
           */
            public void receiveErrorsitGetInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetVieFTopo method
            * override this method for handling normal response from sitGetVieFTopo operation
            */
           public void receiveResultsitGetVieFTopo(
                    org.tempuri.Toponomastica40Stub.SitGetVieFTopoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetVieFTopo operation
           */
            public void receiveErrorsitGetVieFTopo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetCivicoFidc method
            * override this method for handling normal response from sitGetCivicoFidc operation
            */
           public void receiveResultsitGetCivicoFidc(
                    org.tempuri.Toponomastica40Stub.SitGetCivicoFidcResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetCivicoFidc operation
           */
            public void receiveErrorsitGetCivicoFidc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetCiviciFPointExtended method
            * override this method for handling normal response from sitGetCiviciFPointExtended operation
            */
           public void receiveResultsitGetCiviciFPointExtended(
                    org.tempuri.Toponomastica40Stub.SitGetCiviciFPointExtendedResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetCiviciFPointExtended operation
           */
            public void receiveErrorsitGetCiviciFPointExtended(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetCivicoReplace method
            * override this method for handling normal response from sitGetCivicoReplace operation
            */
           public void receiveResultsitGetCivicoReplace(
                    org.tempuri.Toponomastica40Stub.SitGetCivicoReplaceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetCivicoReplace operation
           */
            public void receiveErrorsitGetCivicoReplace(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetViaFCode method
            * override this method for handling normal response from sitGetViaFCode operation
            */
           public void receiveResultsitGetViaFCode(
                    org.tempuri.Toponomastica40Stub.SitGetViaFCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetViaFCode operation
           */
            public void receiveErrorsitGetViaFCode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetCivicoChange method
            * override this method for handling normal response from sitGetCivicoChange operation
            */
           public void receiveResultsitGetCivicoChange(
                    org.tempuri.Toponomastica40Stub.SitGetCivicoChangeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetCivicoChange operation
           */
            public void receiveErrorsitGetCivicoChange(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetData method
            * override this method for handling normal response from sitGetData operation
            */
           public void receiveResultsitGetData(
                    org.tempuri.Toponomastica40Stub.SitGetDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetData operation
           */
            public void receiveErrorsitGetData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetCivicoStory method
            * override this method for handling normal response from sitGetCivicoStory operation
            */
           public void receiveResultsitGetCivicoStory(
                    org.tempuri.Toponomastica40Stub.SitGetCivicoStoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetCivicoStory operation
           */
            public void receiveErrorsitGetCivicoStory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetCiviciFPointBase method
            * override this method for handling normal response from sitGetCiviciFPointBase operation
            */
           public void receiveResultsitGetCiviciFPointBase(
                    org.tempuri.Toponomastica40Stub.SitGetCiviciFPointBaseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetCiviciFPointBase operation
           */
            public void receiveErrorsitGetCiviciFPointBase(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetValueFidc method
            * override this method for handling normal response from sitGetValueFidc operation
            */
           public void receiveResultsitGetValueFidc(
                    org.tempuri.Toponomastica40Stub.SitGetValueFidcResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetValueFidc operation
           */
            public void receiveErrorsitGetValueFidc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetTipoVia method
            * override this method for handling normal response from sitGetTipoVia operation
            */
           public void receiveResultsitGetTipoVia(
                    org.tempuri.Toponomastica40Stub.SitGetTipoViaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetTipoVia operation
           */
            public void receiveErrorsitGetTipoVia(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sitGetValueFPoint method
            * override this method for handling normal response from sitGetValueFPoint operation
            */
           public void receiveResultsitGetValueFPoint(
                    org.tempuri.Toponomastica40Stub.SitGetValueFPointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sitGetValueFPoint operation
           */
            public void receiveErrorsitGetValueFPoint(java.lang.Exception e) {
            }
                


    }
    