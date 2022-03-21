/**
 * BasicHttpBinding_ITopoServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class BasicHttpBinding_ITopoServiceStub extends org.apache.axis.client.Stub implements org.tempuri.ITopoService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[9];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sitGetInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pToken"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetInfo"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetInfoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sitGetCiviciFPointBase");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pToken"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pCoordinateX"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"), java.lang.Double.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pCoordinateY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"), java.lang.Double.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pReference"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.EnumReference"), org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.EnumStatoCivico"), org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pNumber"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), java.lang.Integer.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pDistance"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"), java.lang.Double.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCiviciFPointBase"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointBase.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetCiviciFPointBaseResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sitGetCiviciFPointExtended");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pToken"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pCoordinateX"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"), java.lang.Double.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pCoordinateY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"), java.lang.Double.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pReference"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.EnumReference"), org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.EnumStatoCivico"), org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pNumber"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), java.lang.Integer.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pDistance"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"), java.lang.Double.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCiviciFPointExtended"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointExtended.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetCiviciFPointExtendedResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sitGetVieFTopo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pToken"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pToponomy"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetVieFTopo"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetVieFTopo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetVieFTopoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sitGetViaFCode");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pToken"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pStreetCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), java.lang.Integer.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pCivic"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pState"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.EnumStatoCivico"), org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pReference"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.EnumReference"), org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetViaFCode"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetViaFCode.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetViaFCodeResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sitGetCivicoFidc");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pToken"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pIDC"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), java.lang.Integer.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pReference"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.EnumReference"), org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoFidc"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoFidc.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetCivicoFidcResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sitGetCivicoStory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pToken"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pIDC"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), java.lang.Integer.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pData1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pData2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoStory"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoStory.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetCivicoStoryResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sitGetCivicoChange");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pToken"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pData1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pData2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoChange"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoChange.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetCivicoChangeResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("sitGetTipoVia");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pToken"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "pType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetTipoVia"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetTipoVia.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetTipoViaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

    }

    public BasicHttpBinding_ITopoServiceStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public BasicHttpBinding_ITopoServiceStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public BasicHttpBinding_ITopoServiceStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "ArrayOfTopoService.typeGetCiviciFPointBaseCivic");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointBaseCivic[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCiviciFPointBaseCivic");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCiviciFPointBaseCivic");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "ArrayOfTopoService.typeGetCiviciFPointExtendedCivic");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointExtendedCivic[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCiviciFPointExtendedCivic");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCiviciFPointExtendedCivic");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "ArrayOfTopoService.typeGetCivicoChangeCivic");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoChangeCivic[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoChangeCivic");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoChangeCivic");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "ArrayOfTopoService.typeGetCivicoStoryCivic");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoStoryCivic[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoStoryCivic");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoStoryCivic");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "ArrayOfTopoService.typeGetCivicoTipoViaType");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoTipoViaType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoTipoViaType");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoTipoViaType");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "ArrayOfTopoService.typeGetViaFCodeCivic");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetViaFCodeCivic[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetViaFCodeCivic");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetViaFCodeCivic");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "ArrayOfTopoService.typeGetVieFTopoStreet");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetVieFTopoStreet[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetVieFTopoStreet");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetVieFTopoStreet");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.EnumReference");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.EnumStatoCivico");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeError");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeError.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCiviciFPointBase");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCiviciFPointBaseCivic");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointBaseCivic.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCiviciFPointExtended");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointExtended.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCiviciFPointExtendedCivic");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointExtendedCivic.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoChange");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoChange.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoChangeCivic");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoChangeCivic.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoFidc");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoFidc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoStory");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoStory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoStoryCivic");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoStoryCivic.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoTipoViaType");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoTipoViaType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetInfo");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetTipoVia");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetTipoVia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetViaFCode");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetViaFCode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetViaFCodeCivic");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetViaFCodeCivic.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetVieFTopo");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetVieFTopo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetVieFTopoStreet");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetVieFTopoStreet.class;
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

    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetInfo sitGetInfo(java.lang.String pToken) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/ITopoService/sitGetInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pToken});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointBase sitGetCiviciFPointBase(java.lang.String pToken, java.lang.Double pCoordinateX, java.lang.Double pCoordinateY, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico pState, java.lang.Integer pNumber, java.lang.Double pDistance) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/ITopoService/sitGetCiviciFPointBase");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetCiviciFPointBase"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pToken, pCoordinateX, pCoordinateY, pReference, pState, pNumber, pDistance});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointBase) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointBase) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointBase.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointExtended sitGetCiviciFPointExtended(java.lang.String pToken, java.lang.Double pCoordinateX, java.lang.Double pCoordinateY, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico pState, java.lang.Integer pNumber, java.lang.Double pDistance) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/ITopoService/sitGetCiviciFPointExtended");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetCiviciFPointExtended"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pToken, pCoordinateX, pCoordinateY, pReference, pState, pNumber, pDistance});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointExtended) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointExtended) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointExtended.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetVieFTopo sitGetVieFTopo(java.lang.String pToken, java.lang.String pToponomy, java.lang.String pType) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/ITopoService/sitGetVieFTopo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetVieFTopo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pToken, pToponomy, pType});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetVieFTopo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetVieFTopo) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetVieFTopo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetViaFCode sitGetViaFCode(java.lang.String pToken, java.lang.Integer pStreetCode, java.lang.String pCivic, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico pState, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/ITopoService/sitGetViaFCode");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetViaFCode"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pToken, pStreetCode, pCivic, pState, pReference});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetViaFCode) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetViaFCode) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetViaFCode.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoFidc sitGetCivicoFidc(java.lang.String pToken, java.lang.Integer pIDC, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/ITopoService/sitGetCivicoFidc");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetCivicoFidc"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pToken, pIDC, pReference});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoFidc) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoFidc) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoFidc.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoStory sitGetCivicoStory(java.lang.String pToken, java.lang.Integer pIDC, java.lang.String pData1, java.lang.String pData2) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/ITopoService/sitGetCivicoStory");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetCivicoStory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pToken, pIDC, pData1, pData2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoStory) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoStory) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoStory.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoChange sitGetCivicoChange(java.lang.String pToken, java.lang.String pData1, java.lang.String pData2) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/ITopoService/sitGetCivicoChange");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetCivicoChange"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pToken, pData1, pData2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoChange) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoChange) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoChange.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetTipoVia sitGetTipoVia(java.lang.String pToken, java.lang.String pType) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/ITopoService/sitGetTipoVia");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "sitGetTipoVia"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pToken, pType});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetTipoVia) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetTipoVia) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetTipoVia.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
