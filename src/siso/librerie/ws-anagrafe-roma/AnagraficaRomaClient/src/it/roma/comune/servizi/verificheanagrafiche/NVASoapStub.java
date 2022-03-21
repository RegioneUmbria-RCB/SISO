/**
 * NVASoapStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class NVASoapStub extends org.apache.axis.client.Stub implements it.roma.comune.servizi.verificheanagrafiche.NVASoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[16];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaAnagrafica");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaAnagrafica"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagrafica"), it.roma.comune.servizi.verificheanagrafiche.VerificaAnagrafica.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagraficaResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaAnagraficaResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaLeggeRutelli");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaLeggeRutelli"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaLeggeRutelli"), it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelli.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaLeggeRutelliResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaLeggeRutelliResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaAnagraficaEstesa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaAnagraficaEstesa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagraficaEstesa"), it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesa.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagraficaEstesaResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaAnagraficaEstesaResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaDocumenti");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaDocumenti"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDocumenti"), it.roma.comune.servizi.verificheanagrafiche.VerificaDocumenti.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDocumentiResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaDocumentiResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaCartaIdent");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaCartaIdent"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaIdent"), it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdent.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaIdentResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaCartaIdentResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaElettorale");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaElettorale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaElettorale"), it.roma.comune.servizi.verificheanagrafiche.VerificaElettorale.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaElettoraleResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaElettoraleResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaElettoraleResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaLevaMilitare");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaLevaMilitare"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaLevaMilitare"), it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitare.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaLevaMilitareResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaLevaMilitareResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaSitFamigliaConv");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaSitFamigliaConv"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaSitFamigliaConv"), it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConv.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaSitFamigliaConvResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaSitFamigliaConvResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaStatoFamigliaConv");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStatoFamigliaConv"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStatoFamigliaConv"), it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConv.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStatoFamigliaConvResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStatoFamigliaConvResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaStoricaDocumenti");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStoricaDocumenti"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricaDocumenti"), it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumenti.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricaDocumentiResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStoricaDocumentiResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaStoricaIndividuo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStoricaIndividuo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricaIndividuo"), it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricaIndividuoResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStoricaIndividuoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaVaccinazioni");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaVaccinazioni"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaVaccinazioni"), it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioni.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaVaccinazioniResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaVaccinazioniResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaDomiciliatiperIndirizzo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaDomiciliatiperIndirizzo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomiciliatiperIndirizzo"), it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzo.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomiciliatiperIndirizzoResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaDomiciliatiperIndirizzoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaDomXRangeCivici");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaDomXRangeCivici"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomXRangeCivici"), it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCivici.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomXRangeCiviciResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaDomXRangeCiviciResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaCartaId");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaCartaId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaId"), it.roma.comune.servizi.verificheanagrafiche.VerificaCartaId.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaIdResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaCartaIdResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("VerificaStoricoFamigliaXData");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStoricoFamigliaXData"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricoFamigliaXData"), it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXData.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader"), it.roma.comune.servizi.verificheanagrafiche.LogHeader.class, true, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricoFamigliaXDataResponse"));
        oper.setReturnClass(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStoricoFamigliaXDataResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

    }

    public NVASoapStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public NVASoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public NVASoapStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaAnagraficaEstesaResponse>VerificaAnagraficaEstesaResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponseVerificaAnagraficaEstesaResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaAnagraficaResponse>VerificaAnagraficaResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponseVerificaAnagraficaResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaCartaIdentResponse>VerificaCartaIdentResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponseVerificaCartaIdentResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaCartaIdResponse>VerificaCartaIdResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponseVerificaCartaIdResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaDocumentiResponse>VerificaDocumentiResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponseVerificaDocumentiResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaDomiciliatiperIndirizzoResponse>VerificaDomiciliatiperIndirizzoResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponseVerificaDomiciliatiperIndirizzoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaDomXRangeCiviciResponse>VerificaDomXRangeCiviciResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponseVerificaDomXRangeCiviciResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaElettoraleResponse>VerificaElettoraleResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaElettoraleResponseVerificaElettoraleResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaLeggeRutelliResponse>VerificaLeggeRutelliResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponseVerificaLeggeRutelliResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaLevaMilitareResponse>VerificaLevaMilitareResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponseVerificaLevaMilitareResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaSitFamigliaConvResponse>VerificaSitFamigliaConvResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponseVerificaSitFamigliaConvResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaStatoFamigliaConvResponse>VerificaStatoFamigliaConvResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponseVerificaStatoFamigliaConvResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaStoricaDocumentiResponse>VerificaStoricaDocumentiResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponseVerificaStoricaDocumentiResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaStoricaIndividuoResponse>VerificaStoricaIndividuoResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponseVerificaStoricaIndividuoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaStoricoFamigliaXDataResponse>VerificaStoricoFamigliaXDataResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponseVerificaStoricoFamigliaXDataResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaVaccinazioniResponse>VerificaVaccinazioniResult");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponseVerificaVaccinazioniResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagrafica");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaAnagrafica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagraficaEstesa");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagraficaEstesaResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagraficaResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaId");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaCartaId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaIdent");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdent.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaIdentResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaIdResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDocumenti");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaDocumenti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDocumentiResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomiciliatiperIndirizzo");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomiciliatiperIndirizzoResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomXRangeCivici");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCivici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomXRangeCiviciResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaElettorale");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaElettorale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaElettoraleResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaElettoraleResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaLeggeRutelli");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelli.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaLeggeRutelliResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaLevaMilitare");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitare.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaLevaMilitareResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaSitFamigliaConv");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaSitFamigliaConvResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStatoFamigliaConv");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStatoFamigliaConvResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricaDocumenti");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumenti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricaDocumentiResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricaIndividuo");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricaIndividuoResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricoFamigliaXData");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricoFamigliaXDataResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaVaccinazioni");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioni.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaVaccinazioniResponse");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "LogHeader");
            cachedSerQNames.add(qName);
            cls = it.roma.comune.servizi.verificheanagrafiche.LogHeader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponse verificaAnagrafica(it.roma.comune.servizi.verificheanagrafiche.VerificaAnagrafica parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaAnagrafica");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaAnagrafica"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponse verificaLeggeRutelli(it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelli parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaLeggeRutelli");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaLeggeRutelli"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponse verificaAnagraficaEstesa(it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesa parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaAnagraficaEstesa");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaAnagraficaEstesa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponse verificaDocumenti(it.roma.comune.servizi.verificheanagrafiche.VerificaDocumenti parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaDocumenti");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaDocumenti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponse verificaCartaIdent(it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdent parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaCartaIdent");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaCartaIdent"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaElettoraleResponse verificaElettorale(it.roma.comune.servizi.verificheanagrafiche.VerificaElettorale parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaElettorale");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaElettorale"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaElettoraleResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaElettoraleResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaElettoraleResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponse verificaLevaMilitare(it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitare parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaLevaMilitare");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaLevaMilitare"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponse verificaSitFamigliaConv(it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConv parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaSitFamigliaConv");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaSitFamigliaConv"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponse verificaStatoFamigliaConv(it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConv parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaStatoFamigliaConv");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaStatoFamigliaConv"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponse verificaStoricaDocumenti(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumenti parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaStoricaDocumenti");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaStoricaDocumenti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponse verificaStoricaIndividuo(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuo parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaStoricaIndividuo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaStoricaIndividuo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponse verificaVaccinazioni(it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioni parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaVaccinazioni");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaVaccinazioni"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponse verificaDomiciliatiperIndirizzo(it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzo parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaDomiciliatiperIndirizzo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaDomiciliatiperIndirizzo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponse verificaDomXRangeCivici(it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCivici parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaDomXRangeCivici");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaDomXRangeCivici"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponse verificaCartaId(it.roma.comune.servizi.verificheanagrafiche.VerificaCartaId parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaCartaId");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaCartaId"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponse verificaStoricoFamigliaXData(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXData parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://servizi.comune.roma.it/verificheanagrafiche/VerificaStoricoFamigliaXData");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "VerificaStoricoFamigliaXData"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, logHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
