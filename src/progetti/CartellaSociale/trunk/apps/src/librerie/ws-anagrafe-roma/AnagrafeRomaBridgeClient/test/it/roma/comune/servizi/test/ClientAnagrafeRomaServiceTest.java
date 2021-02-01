

/**
 * ClientAnagrafeRomaServiceTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.2  Built on : Sep 06, 2010 (09:42:01 CEST)
 */
    package it.roma.comune.servizi.test;

    /*
     *  ClientAnagrafeRomaServiceTest Junit test case
    */

    public class ClientAnagrafeRomaServiceTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testverificaDatiAnagrafici() throws java.lang.Exception{

        it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub stub =
                    new it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub();//the default implementation should point to the right endpoint

           it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.VerificaDatiAnagrafici verificaDatiAnagrafici8=
                                                        (it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.VerificaDatiAnagrafici)getTestObject(it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.VerificaDatiAnagrafici.class);
                    // TODO : Fill in the verificaDatiAnagrafici8 here
                
                        assertNotNull(stub.verificaDatiAnagrafici(
                        verificaDatiAnagrafici8));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testricercaPerCodiceIndividuale() throws java.lang.Exception{

        it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub stub =
                    new it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub();//the default implementation should point to the right endpoint

           it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.RicercaPerCodiceIndividuale ricercaPerCodiceIndividuale10=
                                                        (it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.RicercaPerCodiceIndividuale)getTestObject(it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.RicercaPerCodiceIndividuale.class);
                    // TODO : Fill in the ricercaPerCodiceIndividuale10 here
                
                        assertNotNull(stub.ricercaPerCodiceIndividuale(
                        ricercaPerCodiceIndividuale10));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testverificaDatiAnagraficiCompleta() throws java.lang.Exception{

        it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub stub =
                    new it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub();//the default implementation should point to the right endpoint

           it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.VerificaDatiAnagraficiCompleta verificaDatiAnagraficiCompleta12=
                                                        (it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.VerificaDatiAnagraficiCompleta)getTestObject(it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.VerificaDatiAnagraficiCompleta.class);
                    // TODO : Fill in the verificaDatiAnagraficiCompleta12 here
                
                        assertNotNull(stub.verificaDatiAnagraficiCompleta(
                        verificaDatiAnagraficiCompleta12));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testeseguiRicercaAnagraficaEstesa() throws java.lang.Exception{

        it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub stub =
                    new it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub();//the default implementation should point to the right endpoint

           it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.EseguiRicercaAnagraficaEstesa eseguiRicercaAnagraficaEstesa14=
                                                        (it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.EseguiRicercaAnagraficaEstesa)getTestObject(it.roma.comune.servizi.test.ClientAnagrafeRomaServiceStub.EseguiRicercaAnagraficaEstesa.class);
                    // TODO : Fill in the eseguiRicercaAnagraficaEstesa14 here
                
                        assertNotNull(stub.eseguiRicercaAnagraficaEstesa(
                        eseguiRicercaAnagraficaEstesa14));
                  



        }
        
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    