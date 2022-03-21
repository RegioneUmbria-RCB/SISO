package it.marche.regione.pddnica.client;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.ListAttribute;
import javax.swing.text.html.ListView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.siso.isee.obj.Abitazione;
import it.siso.isee.obj.Anagrafica;
import it.siso.isee.obj.AnagraficaAgg;
import it.siso.isee.obj.AnagraficaBase;
import it.siso.isee.obj.Attestazione;
import it.siso.isee.obj.ComponenteAggiuntiva;
import it.siso.isee.obj.ComponenteMinorenne;
import it.siso.isee.obj.ComponenteNucleo;
import it.siso.isee.obj.ComponenteNucleoRistretto;
import it.siso.isee.obj.ComponenteSocioSanitarioRes;
import it.siso.isee.obj.ComponenteSocioSanitarioResRistretto;
import it.siso.isee.obj.CondizioniNucleo;
import it.siso.isee.obj.DatiComponente;
import it.siso.isee.obj.DettaglioGenitoreNonConvivente;
import it.siso.isee.obj.Dichiarazione;
import it.siso.isee.obj.DiscordanzeContestazione;
import it.siso.isee.obj.ElementoNucleo;
import it.siso.isee.obj.Esito;
import it.siso.isee.obj.FoglioComponenteNucleo;
import it.siso.isee.obj.GenitoreNonConvivente;
import it.siso.isee.obj.ISEEAgg;
import it.siso.isee.obj.ISEEBase;
import it.siso.isee.obj.ISEEMin;
import it.siso.isee.obj.ISEEOrdinario;
import it.siso.isee.obj.ISEEUniStudAttratto;
import it.siso.isee.obj.ISEEUniStudAttrattoCompAttratta;
import it.siso.isee.obj.Locazione;
import it.siso.isee.obj.ModalitaCalcoloISEE;
import it.siso.isee.obj.ModelloBase;
import it.siso.isee.obj.NucleoFamiliare;
import it.siso.isee.obj.NucleoRistretto;
import it.siso.isee.obj.OmissioneDifformita;
import it.siso.isee.obj.Operazione;
import it.siso.isee.obj.Ordinario;
import it.siso.isee.obj.Rapporto;
import it.siso.isee.obj.RedditoAE;
import it.siso.isee.obj.Residenza;
import it.siso.isee.obj.Residenziale;
import it.siso.isee.obj.Ridotto;
import it.siso.isee.obj.Sottoscrizione;
import it.siso.isee.obj.StudenteUniversitario;
import it.siso.isee.obj.Universitario;
import it.siso.isee.obj.Valori;
import it.siso.isee.obj.VariazioneAttestazione;
import it.siso.isee.obj.VariazioneDichiarazione;

public class XmlISEEParser {

	private String xmlAttestazione = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48RXNpdG9BdHRlc3RhemlvbmUgeG1sbnM6eHNpPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxL1hNTFNjaGVtYS1pbnN0YW5jZSIgeG1sbnM9Imh0dHA6Ly9pbnBzLml0L0lTRUVSaWZvcm1hIj4NCiAgPEF0dGVzdGF6aW9uZSBDb2RpY2VGaXNjYWxlRGljaGlhcmFudGU9IkxWTkZSWjcwUzIzRDQ4OEUiIE51bWVyb1Byb3RvY29sbG9EU1U9IklOUFMtSVNFRS0yMDIwLTAwNTk3MzgxVy0wMCIgRGF0YVByZXNlbnRhemlvbmU9IjIwMjAtMDEtMTUiIERhdGFWYWxpZGl0YT0iMjAyMC0xMi0zMSIgUHJvdG9jb2xsb01pdHRlbnRlPSJDQUYwMDAxOC1NQVIwMDEtMjAyMC1OMDAwNTYzOSI+DQogICAgPE9yZGluYXJpbz4NCiAgICAgIDxOdWNsZW9GYW1pbGlhcmU+DQogICAgICAgIDxDb21wb25lbnRlTnVjbGVvIFJhcHBvcnRvQ29uRGljaGlhcmFudGU9IkQiIENvZ25vbWU9IkFMSVZFTlRJIiBOb21lPSJGQUJSSVpJTyIgQ29kaWNlRmlzY2FsZT0iTFZORlJaNzBTMjNENDg4RSIgLz4NCiAgICAgICAgPENvbXBvbmVudGVOdWNsZW8gUmFwcG9ydG9Db25EaWNoaWFyYW50ZT0iQyIgQ29nbm9tZT0iQUdPU1RJTkVMTEkiIE5vbWU9IkZJT1JFTlpBIiBDb2RpY2VGaXNjYWxlPSJHU1RGTlo3MkE0MkQwMDdSIiAvPg0KICAgICAgICA8Q29tcG9uZW50ZU51Y2xlbyBSYXBwb3J0b0NvbkRpY2hpYXJhbnRlPSJGQyIgQ29nbm9tZT0iQUxJVkVOVEkiIE5vbWU9IlZBTEVOVElOQSIgQ29kaWNlRmlzY2FsZT0iTFZOVk5UOTRSNTRJNjA4TSIgLz4NCiAgICAgICAgPENvbXBvbmVudGVOdWNsZW8gUmFwcG9ydG9Db25EaWNoaWFyYW50ZT0iRkMiIENvZ25vbWU9IkFMSVZFTlRJIiBOb21lPSJEQVZJREUiIENvZGljZUZpc2NhbGU9IkxWTkRWRDAwRDIzSTYwOFEiIC8+DQogICAgICA8L051Y2xlb0ZhbWlsaWFyZT4NCiAgICAgIDxJU0VFT3JkaW5hcmlvIERhdGFSaWxhc2Npbz0iMjAyMC0wMS0xOCI+DQogICAgICAgIDxWYWxvcmkgSVNFPSI0MjkxMS43NSIgU2NhbGFFcXVpdmFsZW56YT0iMi40NiIgSVNFRT0iMTc0NDMuODAiIElTUj0iMzY2NTMuOTUiIElTUD0iMzEyODkuMDAiIC8+DQogICAgICAgIDxNb2RhbGl0YUNhbGNvbG9JU0VFT3JkaW5hcmlvIFNvbW1hUmVkZGl0aUNvbXBvbmVudGk9IjM2NDczLjQwIiBSZWRkaXRvRmlndXJhdGl2b1BhdHJpbW9uaW9Nb2JpbGlhcmU9IjE4MC41NSIgRGV0cmF6aW9uaT0iMC4wMCIgSVNSPSIzNjY1My45NSIgUGF0cmltb25pb01vYmlsaWFyZT0iMjc2ODEuMDAiIERldHJhemlvbmVQYXRyaW1vbmlvTW9iaWxpYXJlPSIxMDAwMC4wMCIgUGF0cmltb25pb0ltbW9iaWxpYXJlPSI2NzcwNC4wMCIgRGV0cmF6aW9uZVBhdHJpbW9uaW9JbW1vYmlsaWFyZT0iNTQwOTYuMDAiIElTUD0iMzEyODkuMDAiIElTRT0iNDI5MTEuNzUiIFBhcmFtZXRyb051Y2xlbz0iMi40NiIgTWFnZ2lvcmF6aW9uaT0iMC4wMCIgU2NhbGFFcXVpdmFsZW56YT0iMi40NiIgLz4NCiAgICAgIDwvSVNFRU9yZGluYXJpbz4NCiAgICAgIDxJU0VFVW5pPg0KICAgICAgICA8U3R1ZGVudGVVbml2ZXJzaXRhcmlvIENvZ25vbWU9IkFMSVZFTlRJIiBOb21lPSJWQUxFTlRJTkEiIENvZGljZUZpc2NhbGU9IkxWTlZOVDk0UjU0STYwOE0iIElTRUU9IjE3NDQzLjgwIj4NCiAgICAgICAgICA8SVNFRU9yZCAvPg0KICAgICAgICA8L1N0dWRlbnRlVW5pdmVyc2l0YXJpbz4NCiAgICAgICAgPFN0dWRlbnRlVW5pdmVyc2l0YXJpbyBDb2dub21lPSJBTElWRU5USSIgTm9tZT0iREFWSURFIiBDb2RpY2VGaXNjYWxlPSJMVk5EVkQwMEQyM0k2MDhRIiBJU0VFPSIxNzQ0My44MCI+DQogICAgICAgICAgPElTRUVPcmQgLz4NCiAgICAgICAgPC9TdHVkZW50ZVVuaXZlcnNpdGFyaW8+DQogICAgICA8L0lTRUVVbmk+DQogICAgPC9PcmRpbmFyaW8+DQogIDwvQXR0ZXN0YXppb25lPg0KICA8UmljZXJjYT4NCiAgICA8UmljZXJjYUNGIENvZGljZUZpc2NhbGU9IkxWTkRWRDAwRDIzSTYwOFEiIERhdGFWYWxpZGl0YT0iMjAyMC0wOS0wOSIgUHJlc3RhemlvbmVEYUVyb2dhcmU9IkExLjE5IiBQcm90b2NvbGxvRG9tYW5kYUVudGVFcm9nYXRvcmU9IklEIERvbWFuZGE6IDEyNzQ2NCIgU3RhdG9kb21hbmRhUHJlc3RhemlvbmU9IkRhIEVyb2dhcmUiIC8+DQogIDwvUmljZXJjYT4NCjwvRXNpdG9BdHRlc3RhemlvbmU+";
	private String xmlDichiarazione ="PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48RXNpdG9EaWNoaWFyYXppb25lIHhtbG5zOnhzaT0iaHR0cDovL3d3dy53My5vcmcvMjAwMS9YTUxTY2hlbWEtaW5zdGFuY2UiIHhtbG5zPSJodHRwOi8vaW5wcy5pdC9JU0VFUmlmb3JtYSI+DQogIDxEaWNoaWFyYXppb25lIENvZGljZUZpc2NhbGVEaWNoaWFyYW50ZT0iR0lPTUdSNzBQNjZBMjcxQiI+DQogICAgPE9wZXJhemlvbmUgVGlwbz0iTiIgQ29kaWNlRmlzY2FsZVJpZmVyaW1lbnRvPSJHSU9NR1I3MFA2NkEyNzFCIiBOdW1lcm9Qcm90b2NvbGxvUmlmZXJpbWVudG89IklOUFMtSVNFRS0yMDIwLTAzMzUyODA0Si0wMCIgLz4NCiAgICA8TnVjbGVvRmFtaWxpYXJlPg0KICAgICAgPENvbXBvbmVudGVOdWNsZW8gUmFwcG9ydG9Db25EaWNoaWFyYW50ZT0iRCIgRmxhZ0Fzc2VuemFSZWRkaXRvPSJmYWxzZSI+DQogICAgICAgIDxGb2dsaW9Db21wb25lbnRlTnVjbGVvIENvZGljZUZpc2NhbGU9IkdJT01HUjcwUDY2QTI3MUIiPg0KICAgICAgICAgIDxEYXRpQ29tcG9uZW50ZSBGbGFnQ29udml2ZW56YUFuYWdyYWZpY2E9ImZhbHNlIiBBdHRpdml0YVNvZ2dldHRvPSJMQVZJTkQiPg0KICAgICAgICAgICAgPEFuYWdyYWZpY2EgQ29nbm9tZT0iR0lPSUEiIE5vbWU9Ik1BUklBIEdSQVpJQSIgRGF0YU5hc2NpdGE9IjE5NzAtMDktMjYiIENpdHRhZGluYW56YT0iSXRhbGlhbmEiIFNlc3NvPSJGIiBQcm92aW5jaWFOYXNjaXRhPSJBTiIgQ29kaWNlQ29tdW5lTmFzY2l0YT0iQTI3MSIgLz4NCiAgICAgICAgICAgIDxSZXNpZGVuemEgSW5kaXJpenpvPSJWSUEgREVMIENPTlZFTlRPIiBDaXZpY289IjMvRiIgQ29kaWNlQ29tdW5lPSJBMDkyIiBQcm92aW5jaWE9IkFOIiBDYXA9IjYwMDIwIiAvPg0KICAgICAgICAgIDwvRGF0aUNvbXBvbmVudGU+DQogICAgICAgICAgPFBhdHJpbW9uaW9Nb2JpbGlhcmUgRmxhZ1Bvc3Nlc3NvUmFwcG9ydG9GaW5hbnppYXJpbz0idHJ1ZSI+DQogICAgICAgICAgICA8UmFwcG9ydG8gVGlwb1JhcHBvcnRvPSIwMSIgSWRlbnRpZmljYXRpdm89IjExMDgwNzU4MDMiIENvZGljZUZpc2NhbGVPcGVyYXRvcmU9IjAwMjA1NjgwNDA4IiBTYWxkbzMxMTI9IjAiIENvbnNpc3RlbnphTWVkaWE9IjAiIC8+DQogICAgICAgICAgICA8UmFwcG9ydG8gVGlwb1JhcHBvcnRvPSIwMyIgSWRlbnRpZmljYXRpdm89IjAwMDA0NDE4ODQ3MiIgQ29kaWNlRmlzY2FsZU9wZXJhdG9yZT0iOTcxMDM4ODA1ODUiIFNhbGRvMzExMj0iMTgiIENvbnNpc3RlbnphTWVkaWE9IjE4IiAvPg0KICAgICAgICAgIDwvUGF0cmltb25pb01vYmlsaWFyZT4NCiAgICAgICAgICA8UGF0cmltb25pb0ltbW9iaWxpYXJlPg0KICAgICAgICAgICAgPFBhdHJpbW9uaW8gVGlwb1BhdHJpbW9uaW89IkYiIENvZGljZUNvbXVuZVN0YXRvPSJBMDkyIiBRdW90YVBvc3NlZHV0YT0iNTAiIFZhbG9yZUltdT0iODMzMjgiIE11dHVvUmVzaWR1bz0iMjY1MzMiIEZsYWdBYml0YXppb25lPSJ0cnVlIiAvPg0KICAgICAgICAgICAgPFBhdHJpbW9uaW8gVGlwb1BhdHJpbW9uaW89IkYiIENvZGljZUNvbXVuZVN0YXRvPSJBMDkyIiBRdW90YVBvc3NlZHV0YT0iNTAiIFZhbG9yZUltdT0iMzQ0NCIgRmxhZ0FiaXRhemlvbmU9ImZhbHNlIiAvPg0KICAgICAgICAgICAgPFBhdHJpbW9uaW8gVGlwb1BhdHJpbW9uaW89IlRBIiBDb2RpY2VDb211bmVTdGF0bz0iQTA5MiIgUXVvdGFQb3NzZWR1dGE9IjciIFZhbG9yZUltdT0iMTQ1IiBGbGFnQWJpdGF6aW9uZT0iZmFsc2UiIC8+DQogICAgICAgICAgPC9QYXRyaW1vbmlvSW1tb2JpbGlhcmU+DQogICAgICAgICAgPFJlZGRpdGlEYURpY2hpYXJhcmUgUmVkZGl0b0ltcFNvc3Q9IjAiIFJlZGRpdGlFc2VudGlJbXBvc3RhPSIwIiBSZWRkaXRpSVJBUD0iMCIgUmVkZGl0aUZvbmRpYXJpPSIyIiBSZWRkaXRpVGFzc2F0aUVzdGVybz0iMCIgUmVkZGl0aUFJUkU9IjAiIFJlZGRpdGlGb25kaWFyaUVzdGVybz0iMCIgVHJhdHRhbWVudGlBc3Npc3RlbnppYWxpPSIwIiAvPg0KICAgICAgICAgIDxWZWljb2xpPg0KICAgICAgICAgICAgPFZlaWNvbG8gVGlwb1ZlaWNvbG89IkEiIFRhcmdhPSJESDcxMk1HIiAvPg0KICAgICAgICAgIDwvVmVpY29saT4NCiAgICAgICAgICA8QWx0cmlSZWRkaXRpIEZsYWdFc29uZXJhdG89ImZhbHNlIiBGbGFnSW50ZWdyYXppb25lPSJmYWxzZSIgRmxhZ1JldHRpZmljYT0iZmFsc2UiIFJlZGRpdG9JUlBFRj0iMzIwNTgiIExhdm9yb0RpcGVuZGVudGU9IjMxNTEzIiAvPg0KICAgICAgICA8L0ZvZ2xpb0NvbXBvbmVudGVOdWNsZW8+DQogICAgICA8L0NvbXBvbmVudGVOdWNsZW8+DQogICAgICA8Q29tcG9uZW50ZU51Y2xlbyBSYXBwb3J0b0NvbkRpY2hpYXJhbnRlPSJDIiBGbGFnQXNzZW56YVJlZGRpdG89ImZhbHNlIj4NCiAgICAgICAgPEZvZ2xpb0NvbXBvbmVudGVOdWNsZW8gQ29kaWNlRmlzY2FsZT0iTFNTRlBQNjlDMjdBMjcxTiI+DQogICAgICAgICAgPERhdGlDb21wb25lbnRlIEZsYWdDb252aXZlbnphQW5hZ3JhZmljYT0iZmFsc2UiIEF0dGl2aXRhU29nZ2V0dG89IkxBVkFVVCI+DQogICAgICAgICAgICA8QW5hZ3JhZmljYSBDb2dub21lPSJBTEVTU0FORFJPTkkiIE5vbWU9IkZJTElQUE8iIERhdGFOYXNjaXRhPSIxOTY5LTAzLTI3IiBDaXR0YWRpbmFuemE9Ikl0YWxpYW5hIiBTZXNzbz0iTSIgUHJvdmluY2lhTmFzY2l0YT0iQU4iIENvZGljZUNvbXVuZU5hc2NpdGE9IkEyNzEiIC8+DQogICAgICAgICAgICA8UmVzaWRlbnphIEluZGlyaXp6bz0iVklBIERFTCBDT05WRU5UTyIgQ2l2aWNvPSIzL0YiIENvZGljZUNvbXVuZT0iQTA5MiIgUHJvdmluY2lhPSJBTiIgQ2FwPSI2MDAyMCIgLz4NCiAgICAgICAgICA8L0RhdGlDb21wb25lbnRlPg0KICAgICAgICAgIDxQYXRyaW1vbmlvTW9iaWxpYXJlIEZsYWdQb3NzZXNzb1JhcHBvcnRvRmluYW56aWFyaW89InRydWUiPg0KICAgICAgICAgICAgPFJhcHBvcnRvIFRpcG9SYXBwb3J0bz0iMDEiIElkZW50aWZpY2F0aXZvPSI1MzMwIiBDb2RpY2VGaXNjYWxlT3BlcmF0b3JlPSIxMTI0MTE0MDE1OCIgU2FsZG8zMTEyPSIxNzYyIiBDb25zaXN0ZW56YU1lZGlhPSIxMzgzIiAvPg0KICAgICAgICAgICAgPFJhcHBvcnRvIFRpcG9SYXBwb3J0bz0iMDEiIElkZW50aWZpY2F0aXZvPSIzMDYzNTciIENvZGljZUZpc2NhbGVPcGVyYXRvcmU9IjAxMzkyOTcwNDA0IiBTYWxkbzMxMTI9IjY2OCIgQ29uc2lzdGVuemFNZWRpYT0iMjg3IiAvPg0KICAgICAgICAgICAgPFJhcHBvcnRvIFRpcG9SYXBwb3J0bz0iMDEiIElkZW50aWZpY2F0aXZvPSIxMTA4MDc1ODAzIiBDb2RpY2VGaXNjYWxlT3BlcmF0b3JlPSIwMDIwNTY4MDQwOCIgU2FsZG8zMTEyPSIwIiBDb25zaXN0ZW56YU1lZGlhPSIwIiAvPg0KICAgICAgICAgICAgPFJhcHBvcnRvIFRpcG9SYXBwb3J0bz0iMDIiIElkZW50aWZpY2F0aXZvPSIzMzAxODIiIENvZGljZUZpc2NhbGVPcGVyYXRvcmU9IjAxMzkyOTcwNDA0IiBWYWxvcmU9IjAiIC8+DQogICAgICAgICAgICA8UmFwcG9ydG8gVGlwb1JhcHBvcnRvPSIwMyIgSWRlbnRpZmljYXRpdm89IjAwMDA0NDE4ODQ3MiIgQ29kaWNlRmlzY2FsZU9wZXJhdG9yZT0iOTcxMDM4ODA1ODUiIFNhbGRvMzExMj0iMTgiIENvbnNpc3RlbnphTWVkaWE9IjE4IiAvPg0KICAgICAgICAgICAgPFJhcHBvcnRvIFRpcG9SYXBwb3J0bz0iOTkiIElkZW50aWZpY2F0aXZvPSJDRVNQSVRJIiBDb2RpY2VGaXNjYWxlT3BlcmF0b3JlPSJMU1NGUFA2OUMyN0EyNzFOIiBWYWxvcmU9IjgwODEiIC8+DQogICAgICAgICAgPC9QYXRyaW1vbmlvTW9iaWxpYXJlPg0KICAgICAgICAgIDxQYXRyaW1vbmlvSW1tb2JpbGlhcmU+DQogICAgICAgICAgICA8UGF0cmltb25pbyBUaXBvUGF0cmltb25pbz0iRiIgQ29kaWNlQ29tdW5lU3RhdG89IkEwOTIiIFF1b3RhUG9zc2VkdXRhPSI1MCIgVmFsb3JlSW11PSI4MzMyOCIgTXV0dW9SZXNpZHVvPSIyNjUzMyIgRmxhZ0FiaXRhemlvbmU9InRydWUiIC8+DQogICAgICAgICAgICA8UGF0cmltb25pbyBUaXBvUGF0cmltb25pbz0iRiIgQ29kaWNlQ29tdW5lU3RhdG89IkEwOTIiIFF1b3RhUG9zc2VkdXRhPSI1MCIgVmFsb3JlSW11PSIzNDQ0IiBGbGFnQWJpdGF6aW9uZT0iZmFsc2UiIC8+DQogICAgICAgICAgICA8UGF0cmltb25pbyBUaXBvUGF0cmltb25pbz0iVEEiIENvZGljZUNvbXVuZVN0YXRvPSJBMDkyIiBRdW90YVBvc3NlZHV0YT0iNyIgVmFsb3JlSW11PSIxNDUiIEZsYWdBYml0YXppb25lPSJmYWxzZSIgLz4NCiAgICAgICAgICA8L1BhdHJpbW9uaW9JbW1vYmlsaWFyZT4NCiAgICAgICAgICA8VmVpY29saT4NCiAgICAgICAgICAgIDxWZWljb2xvIFRpcG9WZWljb2xvPSJBIiBUYXJnYT0iRE41NThMTiIgLz4NCiAgICAgICAgICAgIDxWZWljb2xvIFRpcG9WZWljb2xvPSJBIiBUYXJnYT0iRkoxMDRFTCIgLz4NCiAgICAgICAgICA8L1ZlaWNvbGk+DQogICAgICAgICAgPEFsdHJpUmVkZGl0aSBGbGFnRXNvbmVyYXRvPSJmYWxzZSIgRmxhZ0ludGVncmF6aW9uZT0iZmFsc2UiIEZsYWdSZXR0aWZpY2E9ImZhbHNlIiBSZWRkaXRvSVJQRUY9IjE2NTA5IiBMYXZvcm9EaXBlbmRlbnRlPSIxMzk4IiAvPg0KICAgICAgICA8L0ZvZ2xpb0NvbXBvbmVudGVOdWNsZW8+DQogICAgICA8L0NvbXBvbmVudGVOdWNsZW8+DQogICAgICA8Q29tcG9uZW50ZU51Y2xlbyBSYXBwb3J0b0NvbkRpY2hpYXJhbnRlPSJGIiBGbGFnQXNzZW56YVJlZGRpdG89InRydWUiPg0KICAgICAgICA8Rm9nbGlvQ29tcG9uZW50ZU51Y2xlbyBDb2RpY2VGaXNjYWxlPSJMU1NHUFAwM1AxN0EyNzFSIj4NCiAgICAgICAgICA8RGF0aUNvbXBvbmVudGUgRmxhZ0NvbnZpdmVuemFBbmFncmFmaWNhPSJmYWxzZSIgQXR0aXZpdGFTb2dnZXR0bz0iU1RVRE5UIj4NCiAgICAgICAgICAgIDxBbmFncmFmaWNhIENvZ25vbWU9IkFMRVNTQU5EUk9OSSIgTm9tZT0iR0lVU0VQUEUiIERhdGFOYXNjaXRhPSIyMDAzLTA5LTE3IiBDaXR0YWRpbmFuemE9Ikl0YWxpYW5hIiBTZXNzbz0iTSIgUHJvdmluY2lhTmFzY2l0YT0iQU4iIENvZGljZUNvbXVuZU5hc2NpdGE9IkEyNzEiIC8+DQogICAgICAgICAgICA8UmVzaWRlbnphIEluZGlyaXp6bz0iVklBIERFTCBDT05WRU5UTyIgQ2l2aWNvPSIzL0YiIENvZGljZUNvbXVuZT0iQTA5MiIgUHJvdmluY2lhPSJBTiIgQ2FwPSI2MDAyMCIgLz4NCiAgICAgICAgICA8L0RhdGlDb21wb25lbnRlPg0KICAgICAgICAgIDxBbHRyaVJlZGRpdGkgRmxhZ0Vzb25lcmF0bz0iZmFsc2UiIEZsYWdJbnRlZ3JhemlvbmU9ImZhbHNlIiBGbGFnUmV0dGlmaWNhPSJmYWxzZSIgLz4NCiAgICAgICAgPC9Gb2dsaW9Db21wb25lbnRlTnVjbGVvPg0KICAgICAgPC9Db21wb25lbnRlTnVjbGVvPg0KICAgICAgPENvbXBvbmVudGVOdWNsZW8gUmFwcG9ydG9Db25EaWNoaWFyYW50ZT0iRiIgRmxhZ0Fzc2VuemFSZWRkaXRvPSJ0cnVlIj4NCiAgICAgICAgPEZvZ2xpb0NvbXBvbmVudGVOdWNsZW8gQ29kaWNlRmlzY2FsZT0iTFNTTUxEMDZINjVBMjcxRSI+DQogICAgICAgICAgPERhdGlDb21wb25lbnRlIEZsYWdDb252aXZlbnphQW5hZ3JhZmljYT0iZmFsc2UiIEF0dGl2aXRhU29nZ2V0dG89IkFMVFJPIj4NCiAgICAgICAgICAgIDxBbmFncmFmaWNhIENvZ25vbWU9IkFMRVNTQU5EUk9OSSIgTm9tZT0iTUFUSUxERSIgRGF0YU5hc2NpdGE9IjIwMDYtMDYtMjUiIENpdHRhZGluYW56YT0iSXRhbGlhbmEiIFNlc3NvPSJGIiBQcm92aW5jaWFOYXNjaXRhPSJBTiIgQ29kaWNlQ29tdW5lTmFzY2l0YT0iQTI3MSIgLz4NCiAgICAgICAgICAgIDxSZXNpZGVuemEgSW5kaXJpenpvPSJWSUEgREVMIENPTlZFTlRPIiBDaXZpY289IjMvRiIgQ29kaWNlQ29tdW5lPSJBMDkyIiBQcm92aW5jaWE9IkFOIiBDYXA9IjYwMDIwIiAvPg0KICAgICAgICAgIDwvRGF0aUNvbXBvbmVudGU+DQogICAgICAgICAgPEFsdHJpUmVkZGl0aSBGbGFnRXNvbmVyYXRvPSJmYWxzZSIgRmxhZ0ludGVncmF6aW9uZT0iZmFsc2UiIEZsYWdSZXR0aWZpY2E9ImZhbHNlIiAvPg0KICAgICAgICA8L0ZvZ2xpb0NvbXBvbmVudGVOdWNsZW8+DQogICAgICA8L0NvbXBvbmVudGVOdWNsZW8+DQogICAgICA8Q29tcG9uZW50ZU51Y2xlbyBSYXBwb3J0b0NvbkRpY2hpYXJhbnRlPSJGQyIgRmxhZ0Fzc2VuemFSZWRkaXRvPSJmYWxzZSI+DQogICAgICAgIDxGb2dsaW9Db21wb25lbnRlTnVjbGVvIENvZGljZUZpc2NhbGU9IkxTU0dORTk3TDA4QTI3MVgiPg0KICAgICAgICAgIDxEYXRpQ29tcG9uZW50ZSBGbGFnQ29udml2ZW56YUFuYWdyYWZpY2E9ImZhbHNlIiBBdHRpdml0YVNvZ2dldHRvPSJTVFVETlQiPg0KICAgICAgICAgICAgPEFuYWdyYWZpY2EgQ29nbm9tZT0iQUxFU1NBTkRST05JIiBOb21lPSJFVUdFTklPIiBEYXRhTmFzY2l0YT0iMTk5Ny0wNy0wOCIgQ2l0dGFkaW5hbnphPSJJdGFsaWFuYSIgU2Vzc289Ik0iIFByb3ZpbmNpYU5hc2NpdGE9IkFOIiBDb2RpY2VDb211bmVOYXNjaXRhPSJBMjcxIiAvPg0KICAgICAgICAgICAgPFJlc2lkZW56YSBJbmRpcml6em89IlZJQSBERUwgQ09OVkVOVE8iIENpdmljbz0iMy9GIiBDb2RpY2VDb211bmU9IkEwOTIiIFByb3ZpbmNpYT0iQU4iIENhcD0iNjAwMjAiIC8+DQogICAgICAgICAgPC9EYXRpQ29tcG9uZW50ZT4NCiAgICAgICAgICA8UGF0cmltb25pb01vYmlsaWFyZSBGbGFnUG9zc2Vzc29SYXBwb3J0b0ZpbmFuemlhcmlvPSJ0cnVlIj4NCiAgICAgICAgICAgIDxSYXBwb3J0byBUaXBvUmFwcG9ydG89IjAxIiBJZGVudGlmaWNhdGl2bz0iMTAwNTcxNDk3OTk5IiBDb2RpY2VGaXNjYWxlT3BlcmF0b3JlPSIxMDM1OTM2MDE1MiIgU2FsZG8zMTEyPSI0MzYiIENvbnNpc3RlbnphTWVkaWE9IjM3MCIgLz4NCiAgICAgICAgICA8L1BhdHJpbW9uaW9Nb2JpbGlhcmU+DQogICAgICAgICAgPEFsdHJpUmVkZGl0aSBGbGFnRXNvbmVyYXRvPSJ0cnVlIiBGbGFnSW50ZWdyYXppb25lPSJmYWxzZSIgRmxhZ1JldHRpZmljYT0iZmFsc2UiIFJlZGRpdG9JUlBFRj0iNTAwIiBMYXZvcm9EaXBlbmRlbnRlPSI1MDAiIC8+DQogICAgICAgIDwvRm9nbGlvQ29tcG9uZW50ZU51Y2xlbz4NCiAgICAgIDwvQ29tcG9uZW50ZU51Y2xlbz4NCiAgICAgIDxDb21wb25lbnRlTnVjbGVvIFJhcHBvcnRvQ29uRGljaGlhcmFudGU9IkZDIiBGbGFnQXNzZW56YVJlZGRpdG89ImZhbHNlIj4NCiAgICAgICAgPEZvZ2xpb0NvbXBvbmVudGVOdWNsZW8gQ29kaWNlRmlzY2FsZT0iTFNTTE5aOThTMjBBMjcxUSI+DQogICAgICAgICAgPERhdGlDb21wb25lbnRlIEZsYWdDb252aXZlbnphQW5hZ3JhZmljYT0iZmFsc2UiIEF0dGl2aXRhU29nZ2V0dG89IkFMVFJPIj4NCiAgICAgICAgICAgIDxBbmFncmFmaWNhIENvZ25vbWU9IkFMRVNTQU5EUk9OSSIgTm9tZT0iTE9SRU5aTyIgRGF0YU5hc2NpdGE9IjE5OTgtMTEtMjAiIENpdHRhZGluYW56YT0iSXRhbGlhbmEiIFNlc3NvPSJNIiBQcm92aW5jaWFOYXNjaXRhPSJBTiIgQ29kaWNlQ29tdW5lTmFzY2l0YT0iQTI3MSIgLz4NCiAgICAgICAgICAgIDxSZXNpZGVuemEgSW5kaXJpenpvPSJWSUEgREVMIENPTlZFTlRPIiBDaXZpY289IjMvRiIgQ29kaWNlQ29tdW5lPSJBMDkyIiBQcm92aW5jaWE9IkFOIiBDYXA9IjYwMDIwIiAvPg0KICAgICAgICAgIDwvRGF0aUNvbXBvbmVudGU+DQogICAgICAgICAgPFBhdHJpbW9uaW9Nb2JpbGlhcmUgRmxhZ1Bvc3Nlc3NvUmFwcG9ydG9GaW5hbnppYXJpbz0idHJ1ZSI+DQogICAgICAgICAgICA8UmFwcG9ydG8gVGlwb1JhcHBvcnRvPSIwMSIgSWRlbnRpZmljYXRpdm89IjUzNTYiIENvZGljZUZpc2NhbGVPcGVyYXRvcmU9IjAzMDUzOTIwMTY1IiBTYWxkbzMxMTI9IjAiIENvbnNpc3RlbnphTWVkaWE9IjAiIC8+DQogICAgICAgICAgICA8UmFwcG9ydG8gVGlwb1JhcHBvcnRvPSIwMSIgSWRlbnRpZmljYXRpdm89IjE2MzUxMTEiIENvZGljZUZpc2NhbGVPcGVyYXRvcmU9IjEwNTM2MDQwOTY2IiBTYWxkbzMxMTI9IjkyIiBDb25zaXN0ZW56YU1lZGlhPSI2MjAiIC8+DQogICAgICAgICAgICA8UmFwcG9ydG8gVGlwb1JhcHBvcnRvPSIwMSIgSWRlbnRpZmljYXRpdm89IjMyOTc3NDUxNyIgQ29kaWNlRmlzY2FsZU9wZXJhdG9yZT0iMTA1MzYwNDA5NjYiIFNhbGRvMzExMj0iMjI0NCIgQ29uc2lzdGVuemFNZWRpYT0iMjI0NCIgLz4NCiAgICAgICAgICAgIDxSYXBwb3J0byBUaXBvUmFwcG9ydG89IjIzIiBJZGVudGlmaWNhdGl2bz0iUFJFUEFHQVRBIiBDb2RpY2VGaXNjYWxlT3BlcmF0b3JlPSIxMzYxNTUyMTAwNSIgVmFsb3JlPSIwIiAvPg0KICAgICAgICAgIDwvUGF0cmltb25pb01vYmlsaWFyZT4NCiAgICAgICAgICA8QWx0cmlSZWRkaXRpIEZsYWdFc29uZXJhdG89ImZhbHNlIiBGbGFnSW50ZWdyYXppb25lPSJmYWxzZSIgRmxhZ1JldHRpZmljYT0iZmFsc2UiIFJlZGRpdG9JUlBFRj0iMTU1ODMiIExhdm9yb0RpcGVuZGVudGU9IjE1NTgzIiAvPg0KICAgICAgICA8L0ZvZ2xpb0NvbXBvbmVudGVOdWNsZW8+DQogICAgICA8L0NvbXBvbmVudGVOdWNsZW8+DQogICAgICA8Q29tcG9uZW50ZU51Y2xlbyBSYXBwb3J0b0NvbkRpY2hpYXJhbnRlPSJGQyIgRmxhZ0Fzc2VuemFSZWRkaXRvPSJmYWxzZSI+DQogICAgICAgIDxGb2dsaW9Db21wb25lbnRlTnVjbGVvIENvZGljZUZpc2NhbGU9IkxTU01DSDAxQTY1QTI3MU8iPg0KICAgICAgICAgIDxEYXRpQ29tcG9uZW50ZSBGbGFnQ29udml2ZW56YUFuYWdyYWZpY2E9ImZhbHNlIiBBdHRpdml0YVNvZ2dldHRvPSJTVFVETlQiPg0KICAgICAgICAgICAgPEFuYWdyYWZpY2EgQ29nbm9tZT0iQUxFU1NBTkRST05JIiBOb21lPSJNQVJJQSBDSElBUkEiIERhdGFOYXNjaXRhPSIyMDAxLTAxLTI1IiBDaXR0YWRpbmFuemE9Ikl0YWxpYW5hIiBTZXNzbz0iRiIgUHJvdmluY2lhTmFzY2l0YT0iQU4iIENvZGljZUNvbXVuZU5hc2NpdGE9IkEyNzEiIC8+DQogICAgICAgICAgICA8UmVzaWRlbnphIEluZGlyaXp6bz0iVklBIERFTCBDT05WRU5UTyIgQ2l2aWNvPSIzL0YiIENvZGljZUNvbXVuZT0iQTA5MiIgUHJvdmluY2lhPSJBTiIgQ2FwPSI2MDAyMCIgLz4NCiAgICAgICAgICA8L0RhdGlDb21wb25lbnRlPg0KICAgICAgICAgIDxBbHRyaVJlZGRpdGkgRmxhZ0Vzb25lcmF0bz0idHJ1ZSIgRmxhZ0ludGVncmF6aW9uZT0iZmFsc2UiIEZsYWdSZXR0aWZpY2E9ImZhbHNlIiAvPg0KICAgICAgICA8L0ZvZ2xpb0NvbXBvbmVudGVOdWNsZW8+DQogICAgICA8L0NvbXBvbmVudGVOdWNsZW8+DQogICAgPC9OdWNsZW9GYW1pbGlhcmU+DQogICAgPE1vZGVsbG9CYXNlIFRpcG89Ik9SRCI+DQogICAgICA8Q29uZGl6aW9uaU51Y2xlbyBGbGFnTGF2b3JvR2VuaXRvcmk9ImZhbHNlIiBGbGFnVW5pY29HZW5pdG9yZT0iZmFsc2UiIE51bWVyb0ZpZ2xpPSI1IiBOdW1lcm9GaWdsaUNvbnZpdmVudGk9IjUiIC8+DQogICAgICA8QWJpdGF6aW9uZSBUaXBvQWJpdGF6aW9uZT0iMDAwIj4NCiAgICAgICAgPEluZGlyaXp6b0FiaXRhemlvbmUgSW5kaXJpenpvPSJWSUEgREVMIENPTlZFTlRPIiBDaXZpY289IjMvRiIgQ29kaWNlQ29tdW5lPSJBMDkyIiBQcm92aW5jaWE9IkFOIiBDYXA9IjYwMDIwIiAvPg0KICAgICAgPC9BYml0YXppb25lPg0KICAgICAgPFVuaXZlcnNpdGFyaW8gQ29kaWNlRmlzY2FsZUJlbmVmaWNpYXJpbz0iR0lPTUdSNzBQNjZBMjcxQiIgUHJlc2VuemFHZW5pdG9yaT0iQ29uaXVnYXRpTm9uUHJlc2VudGkiIEZsYWdSZXNpZGVuemFTdHVkZW50ZT0idHJ1ZSIgRmxhZ0NhcGFjaXRhUmVkZGl0b1N0dWRlbnRlPSJ0cnVlIiAvPg0KICAgICAgPFVuaXZlcnNpdGFyaW8gQ29kaWNlRmlzY2FsZUJlbmVmaWNpYXJpbz0iTFNTR05FOTdMMDhBMjcxWCIgUHJlc2VuemFHZW5pdG9yaT0iVHV0dGlQcmVzZW50aSIgRmxhZ1Jlc2lkZW56YVN0dWRlbnRlPSJmYWxzZSIgRmxhZ0NhcGFjaXRhUmVkZGl0b1N0dWRlbnRlPSJmYWxzZSIgLz4NCiAgICAgIDxVbml2ZXJzaXRhcmlvIENvZGljZUZpc2NhbGVCZW5lZmljaWFyaW89IkxTU01DSDAxQTY1QTI3MU8iIFByZXNlbnphR2VuaXRvcmk9IlR1dHRpUHJlc2VudGkiIEZsYWdSZXNpZGVuemFTdHVkZW50ZT0iZmFsc2UiIEZsYWdDYXBhY2l0YVJlZGRpdG9TdHVkZW50ZT0iZmFsc2UiIC8+DQogICAgPC9Nb2RlbGxvQmFzZT4NCiAgICA8U290dG9zY3JpemlvbmUgQ29kaWNlRmlzY2FsZVNvdHRvc2NyaXR0b3JlPSJHSU9NR1I3MFA2NkEyNzFCIiBQcm90b2NvbGxvTWl0dGVudGU9IkNBRjAwMDM1LVFXQUNMSS0yMDIwLU4wMjA2MTE1IiBMdW9nb1NvdHRvc2NyaXppb25lPSJBTkNPTkEiIERhdGFTb3R0b3Njcml6aW9uZT0iMjAyMC0wMi0yOCIgRmxhZ0ltcGVkaW1lbnRvVGVtcG9yYW5lbz0iZmFsc2UiIEZsYWdSYXBwcmVzZW50YW56YUxlZ2FsZT0iZmFsc2UiIEZsYWdQRUM9ImZhbHNlIiBGbGFnQXV0b3JpenphemlvbmVSaXRpcm89InRydWUiIC8+DQogIDwvRGljaGlhcmF6aW9uZT4NCiAgPFJpY2VyY2E+DQogICAgPFJpY2VyY2FDRiBDb2RpY2VGaXNjYWxlPSJMU1NHTkU5N0wwOEEyNzFYIiBEYXRhVmFsaWRpdGE9IjIwMjAtMDktMDkiIFByZXN0YXppb25lRGFFcm9nYXJlPSJBMS4xOSIgUHJvdG9jb2xsb0RvbWFuZGFFbnRlRXJvZ2F0b3JlPSJJRCBEb21hbmRhOiAxMjY1NzUiIFN0YXRvZG9tYW5kYVByZXN0YXppb25lPSJEYSBFcm9nYXJlIiAvPg0KICA8L1JpY2VyY2E+DQo8L0VzaXRvRGljaGlhcmF6aW9uZT4=";
	private String xml =xmlDichiarazione;
	private boolean encoded = true;
	private org.w3c.dom.Document document = null;
	
	public XmlISEEParser(String _xml, boolean _encoded) {
		this.xml = _xml;
		this.encoded = _encoded;
	}
	protected String decodeBase64(String encodedStr) {
    	// Decode data on other side, by processing encoded data
    	
    	byte[] valueDecoded = Base64.decodeBase64(encodedStr.getBytes());
    	String strDencoded = new String(valueDecoded);
    	System.out.println("Decoded value is " + strDencoded);
    	return strDencoded;
    }
	public Attestazione estraiAttestazione() throws EccezioneClient{
		try {
		if(encoded)
			xml = decodeBase64(xml);
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			InputSource is = new InputSource(new StringReader(xml));
	        document = builder.parse(is);
			 
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			return  getAttestazione(xpath);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			 EccezioneClient excClient = new EccezioneClient("Eccezione estrazione Attestazione ISEE", ex, -3000);
			 throw excClient;
		}
	
	}
	
	
	public Dichiarazione estraiDichiarazione() throws EccezioneClient {
		try {
		if(encoded)
			xml = decodeBase64(xml);
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			InputSource is = new InputSource(new StringReader(xml));
	        document = builder.parse(is);
			 
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			return getDichiarazione(xpath);
		}
		catch(Exception ex) {
			 EccezioneClient excClient = new EccezioneClient("Eccezione estrazione dichiarazione ISEE", ex, -2000);
			 throw excClient;
		}
		 
	}
	
	public Dichiarazione getDichiarazione(XPath xpath) throws Exception
	{
		Dichiarazione dichiarazione = new Dichiarazione();
		XPathExpression expr = xpath.compile("//Dichiarazione");
		
		Node nl = (Node) expr.evaluate(document, XPathConstants.NODE);
		
		dichiarazione.setCodiceFiscale(getTextAttributeNode(nl, "CodiceFiscaleDichiarante"));
	  
		Node nOperazione =	getChildElement(nl, "Operazione"); 
		if(nOperazione != null)
			dichiarazione.setOperazione(getOperazione(nOperazione));
		
		 
		/**
		  * <!-- Ammesso solamente per l'operazione "C" -->
		  */
		List<Node> listNModBase = getChildElements(nl, "ModelloBase");
		if(listNModBase != null ) {
			for(int j=0; j<listNModBase.size(); j++)
				dichiarazione.addModelloBase(getModelloBase(listNModBase.get(j)));  
		}
		 	
		List<Node> listNSottoscrizione = getChildElements(nl, "Sottoscrizione");
		if(listNSottoscrizione != null ) {
			for(int j=0; j<listNModBase.size(); j++)
				dichiarazione.addSottoscrizione(getSottoscrizione(listNSottoscrizione.get(j)));  
		}
		for(int i=0; i< nl.getChildNodes().getLength(); i++) {
			
			if( nl.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE){
				Node nodo1 = nl.getChildNodes().item(i);
				if(nodo1.getNodeName().equals("NucleoFamiliare")) {
					/**
					  * <!-- Nucleo familiare -->
						<!-- Contiene tutti i componenti del nucleo familiare -->
						<!-- (Quadro A) del modello Base -->
					  */
				  	dichiarazione.setNucleoFamiliare(getNucleoFamiliare( nodo1)); // solo presente elemento ordinario
				}
				if(nodo1.getNodeName().equals("ISEECorrente")) {
					/**
					  * <!-- Ammesso solamente per l'operazione "C" -->
					  */
					List<Node> listNVariazioni = getChildElements(nodo1, "Variazione");
					if(listNVariazioni != null ) {
						for(int j=0; j<listNVariazioni.size(); j++)
							dichiarazione.addIseeCorrente(getISEECorrente(listNVariazioni.get(j)));  
					}
				}	
				
				
				 
			}
			
		}
		/***
		 * N.B. da vaultare se inserire le vwerie sezioni di Integrazione
		 * IntegrazioneRedditi
		 * IntegrazioneResidenziale
		 * IntegrazioneComponenteAggiuntiva
		 */
		
		return dichiarazione;
	}
	
	/**
	 * elemento obbligatorio, ripetuto tante volte quante sottoscrizioni sono necessarie alla presentazione della DSU
	 * @param nodo
	 * @return
	 */
	private Sottoscrizione getSottoscrizione(Node nodo) {
		Sottoscrizione sottoscrizione = new Sottoscrizione();
		sottoscrizione.setCodiceFiscaleSottoscrittore(getTextAttributeNode(nodo, "CodiceFiscaleSottoscrittore"));
		sottoscrizione.setDataSottoscrizione(getTextAttributeNode(nodo, "DataSottoscrizione"));
		//Delegato non implementato!!
		//sottoscrizione.setDelegato(delegato); 
		sottoscrizione.setFlagAutorizzazioneRitiro(getBooleanTextAttributeNode(nodo, "FlagAutorizzazioneRitiro"));
		sottoscrizione.setFlagImpedimentoTemporaneo(getBooleanTextAttributeNode(nodo, "FlagImpedimentoTemporaneo"));
		sottoscrizione.setFlagPEC(getBooleanTextAttributeNode(nodo, "FlagPEC"));
		sottoscrizione.setFlagRappresentanzaLegale(getBooleanTextAttributeNode(nodo, "FlagRappresentanzaLegale"));
		sottoscrizione.setIndirizzoPEC(getTextAttributeNode(nodo, "IndirizzoPEC"));
		sottoscrizione.setLuogoSottoscrizione(getTextAttributeNode(nodo, "LuogoSottoscrizione"));
		sottoscrizione.setNoteSottoscrizione(getTextAttributeNode(nodo, "NoteSottoscrizione"));
		sottoscrizione.setNumeroRicevuta(getTextAttributeNode(nodo, "NumeroRicevuta"));
		sottoscrizione.setProtocolloMittente(getTextAttributeNode(nodo, "ProtocolloMittente"));
		
		return sottoscrizione;
	}
	
	private CondizioniNucleo getCondizioniNucleo(Node nodo) {
		if(nodo == null)
			return null;
		CondizioniNucleo cn = new CondizioniNucleo();
		cn.setFlagLavoroGenitori(getBooleanTextAttributeNode(nodo, "FlagLavoroGenitori"));
		cn.setFlagUnicoGenitore(getBooleanTextAttributeNode(nodo, "FlagUnicoGenitore"));
		cn.setNumeroFigli(getIntegerTextAttributeNode(nodo, "NumeroFigli"));
		cn.setNumeroFigliConviventi(getIntegerTextAttributeNode(nodo, "NumeroFigliConviventi"));
		return cn;
	}
	private Abitazione getAbitazione(Node nodo) {
		if(nodo == null)
			return null;
		Abitazione abitazione = new Abitazione();
		Node nodoAbit = getChildElement(nodo, "IndirizzoAbitazione");
		if(nodoAbit != null)
			abitazione.setIndirizzoAbitazione(getResidenza(nodoAbit));
		abitazione.setResidenzaRiferimento(getTextAttributeNode(nodo, "ResidenzaRiferimento"));
		abitazione.setTipoAbitazione(getTextAttributeNode(nodo, "TipoAbitazione"));
		Node nodoLocazione = getChildElement(nodo, "Locazione");
			abitazione.setLocazione(getIndirizzoLocazione(nodoLocazione));
		return abitazione;
	}
	
	private Locazione getIndirizzoLocazione(Node nodo) {
		if(nodo == null)
			return null;
		
		Locazione locazione = new Locazione();
		locazione.setCanone(getIntegerTextAttributeNode(nodo, "Canone"));
		locazione.setCodUfficio(getTextAttributeNode(nodo, "CodUfficio"));
		locazione.setData(getTextAttributeNode(nodo, "Data"));
		List<Node> listIntestatari = getChildElements(nodo, "Intestatario");
	
		for(int i =0; i< listIntestatari.size(); i++) {
			Node nBeneficiario = listIntestatari.get(i);
			String cf = getTextAttributeNode(nBeneficiario, "CodiceFiscale");
			locazione.addIntestario(cf);
		}
		 
		return locazione;
	}
	private ModelloBase getModelloBase(Node nodo) {
		if(nodo == null)
			return null;
		ModelloBase modelloBase = new ModelloBase();
		Node nodoAbitazione = getChildElement(nodo, "Abitazione");
		if(nodoAbitazione != null)
			modelloBase.setAbitazione(getAbitazione(nodoAbitazione));
		
		Node nodoCondNucleo = getChildElement(nodo, "CondizioniNucleo");
		if(nodoCondNucleo != null)
			modelloBase.setCondizioniNucleo(getCondizioniNucleo(nodoCondNucleo));
	
		Node nodoResidenziale = getChildElement(nodo, "Residenziale");
		if(nodoResidenziale != null)
		    modelloBase.setResidenziale(getResidenziale(nodoResidenziale));
		
		modelloBase.setTipo(getTextAttributeNode(nodo, "Tipo"));
		
		List<Node> listNodeUniversitario = getChildElements(nodo, "Universitario");
		if(listNodeUniversitario != null)
			modelloBase.setUniversitari(getUniversitario(listNodeUniversitario));
		return modelloBase;
				
		
	}
	
	private VariazioneDichiarazione getISEECorrente(Node nodo) {
		VariazioneDichiarazione vd = new VariazioneDichiarazione();
		vd.setCodiceFiscale(getTextAttributeNode(nodo, "setCodiceFiscale"));
		vd.setDataVariazione(getTextAttributeNode(nodo, "DataVariazione"));
		vd.setDocumentazione(getTextAttributeNode(nodo, "Documentazione"));
		vd.setRedditoAutonomo12Mesi(getIntegerTextAttributeNode(nodo, "RedditoAutonomo12Mesi"));
		vd.setRedditoAutonomo2Mesi(getIntegerTextAttributeNode(nodo, "RedditoAutonomo2Mesi"));
		vd.setRedditoDipendente12Mesi(getIntegerTextAttributeNode(nodo, "RedditoDipendente12Mesi"));
		vd.setTipovariazione(getTextAttributeNode(nodo, "Tipovariazione"));
		vd.setTrattamentiAssistenziali12Mesi(getIntegerTextAttributeNode(nodo, "TrattamentiAssistenziali12Mesi"));
		vd.setTrattamentiAssistenziali2Mesi(getIntegerTextAttributeNode(nodo, "TrattamentiAssistenziali2Mesi"));
		
		return vd;
	}
	
	private DettaglioGenitoreNonConvivente getDettaglioGenitoreNonConvivente(Node nodo) {
		DettaglioGenitoreNonConvivente dettaglioGenitoreNonConvivente = new DettaglioGenitoreNonConvivente();
		Node nodoFoglioComponenteGenitore = getChildElement(nodo, "FoglioComponenteGenitore");
		if(nodoFoglioComponenteGenitore != null)
			dettaglioGenitoreNonConvivente.setComponenteGenitore(getDatiComponente(nodo));
		
		Node nodoComponenteAggiuntivaGenitore = getChildElement(nodo, "ComponenteAggiuntivaGenitore");
		if(nodoComponenteAggiuntivaGenitore != null) {
			List<ComponenteAggiuntiva> listNodoCa = getComponenteAggiuntiva(nodoComponenteAggiuntivaGenitore);
			if(listNodoCa!= null)
				dettaglioGenitoreNonConvivente.setComponenteAggiuntivaGenitore( listNodoCa.get(0) );
		}
		dettaglioGenitoreNonConvivente.setCodiceFiscale(getTextAttributeNode(nodo, "CodiceFiscale"));
		
		
		return dettaglioGenitoreNonConvivente;
	}
	
	private List<GenitoreNonConvivente> getGenitoreNonConvivente(List<Node> listNGenitoreNonConvivente){
		List<GenitoreNonConvivente> listGenitoreNonConvivente = new ArrayList<GenitoreNonConvivente>();
		if(listNGenitoreNonConvivente == null)
			return null;
		for(int i=0; i<listGenitoreNonConvivente.size(); i++) {
			Node nodo = listNGenitoreNonConvivente.get(i);
			GenitoreNonConvivente genitoreNonConvivente = new GenitoreNonConvivente();
			
			List<Node> listBeneficiario = getChildElements(nodo, "Beneficiario");
			for(int j=0; j<listBeneficiario.size(); j++)
				genitoreNonConvivente.addBeneficiario(listBeneficiario.get(j).getNodeValue());
			genitoreNonConvivente.setCodiceFiscale(getTextAttributeNode(nodo, "CodiceFiscale"));
			genitoreNonConvivente.setCognome(getTextAttributeNode(nodo, "Cognomee"));
			genitoreNonConvivente.setNome(getTextAttributeNode(nodo, "Nome"));
			genitoreNonConvivente.setNumeroProtocolloRiferimento(getTextAttributeNode(nodo, "NumeroProtocolloRiferimento"));
			genitoreNonConvivente.setFlagAlcunaCondizione(getBooleanTextAttributeNode(nodo, "FlagAlcunaCondizione"));
			genitoreNonConvivente.setFlagConiugatoConPersonaDiversa(getBooleanTextAttributeNode(nodo, "FlagConiugatoConPersonaDiversa"));
			genitoreNonConvivente.setFlagGenitoreEscluso(getBooleanTextAttributeNode(nodo, "FlagGenitoreEscluso"));
			
			Node nodoDettGenNonConv = getChildElement(nodo, "DettaglioGenitoreNonConvivente");
			if(nodoDettGenNonConv != null)
				genitoreNonConvivente.setDettaglioGenitoreNonConvivente(getDettaglioGenitoreNonConvivente(nodoDettGenNonConv));
			
			genitoreNonConvivente.setFlagConiugatoConPersonaDiversa(getBooleanTextAttributeNode(nodo, "FlagConiugatoConPersonaDiversa"));
		}
		
		 
		return listGenitoreNonConvivente;
	}
	/***
	 * Metodo che è sempre presente nella DICHIARAZIONE
	 * @param nodo
	 * @return
	 */
	private Residenziale getResidenziale(Node nodo) {
		
		Residenziale residenziale = new Residenziale();
		
		residenziale.setCodiceFiscaleBeneficiario(getTextAttributeNode(nodo, "CodiceFiscaleBeneficiario"));
		residenziale.setDataRichiestaRicovero(getTextAttributeNode(nodo, "DataRichiestaRicovero"));
		residenziale.setFlagFigliNonCompresi(getBooleanTextAttributeNode(nodo, "FlagFigliNonCompresi"));
		residenziale.setFlagDonazioni(getBooleanTextAttributeNode(nodo, "FlagDonazioni"));
		residenziale.setFlagDonazioniSuccessive(getBooleanTextAttributeNode(nodo, "FlagDonazioniSuccessive"));
		residenziale.setFlagDonazioniPrecedentiAlimenti(getBooleanTextAttributeNode(nodo, "FlagDonazioniPrecedentiAlimenti"));
		residenziale.setFlagDonazioniPrecedentiNoAlimenti(getBooleanTextAttributeNode(nodo, "FlagDonazioniPrecedentiNoAlimenti"));
		
		/***
		 * Attenzione devono essere implementati ancora : 
		 * FiglioNonCompresoNelNucleo
		 * Donazione
		 */
		return residenziale;
	}
	private String getTextAttributeNode(org.w3c.dom.Node nodo, String tagAttributeName) {
		NamedNodeMap  attributes = nodo.getAttributes();
		Node nl1 = attributes.getNamedItem(tagAttributeName);
		return (nl1 != null ? nl1.getTextContent() : null);
	}
	
	private Integer getIntegerTextAttributeNode(org.w3c.dom.Node nodo, String tagAttributeName) {
		String result = getTextAttributeNode( nodo,  tagAttributeName);
		if(result != null)
		return Integer.valueOf(result);
		else return null;
	}
	
	
	private Boolean getBooleanTextAttributeNode(org.w3c.dom.Node nodo, String tagAttributeName) {
		String result = getTextAttributeNode( nodo,  tagAttributeName);
		if(result != null)
		return Boolean.valueOf(result);
		else return false;
	}
	public Attestazione getAttestazione(XPath xpath) throws Exception
	{
		Attestazione attestazione = new Attestazione();
		XPathExpression expr = xpath.compile("//Attestazione");
		
		Node nl = (Node) expr.evaluate(document, XPathConstants.NODE);
		
		attestazione.setCodiceFiscaleDichiarante(getTextAttributeNode(nl, "CodiceFiscaleDichiarante"));
		attestazione.setDataPresentazione(getTextAttributeNode(nl, "DataPresentazione"));  
		attestazione.setDataValidita(getTextAttributeNode(nl, "DataValidita")); 
		attestazione.setNumeroProtocolloDSU(getTextAttributeNode(nl, "NumeroProtocolloDSU")); 
		attestazione.setProtocolloMittente(getTextAttributeNode(nl, "ProtocolloMittente")); 
		//getNucleoFamiliare( xpath,  nl);
		for(int i=0; i< nl.getChildNodes().getLength(); i++) {
			
			if( nl.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE){
				Node nodo1 = nl.getChildNodes().item(i);
				if(nodo1.getNodeName().equals("Ordinario"))
					attestazione.setOrdinario(getOrdinario( nodo1)); // solo presente elemento ordinario
				if(nodo1.getNodeName().equals("Ridotto")) {
					List<Node> listRidotto = getChildElements(nodo1, "Ridotto");
					attestazione.setRidotto(getRidotto(listRidotto)); 
				}
				if(nodo1.getNodeName().equals("Variazione"))
					attestazione.setVariazione(getVariazione(nodo1));
				if(nodo1.getNodeName().equals("OmissioneDifformita")) {
					List<Node> listOmissioneDiff = getChildElements(nodo1, "OmissioneDifformita");
					attestazione.setOmissioneDifforma(getOmissioneDifformita(listOmissioneDiff));
				}
			}
			
		}
		
		return attestazione;
	}
	
	private List<Universitario> getUniversitario(List<Node> listNodoUniversitario){
		    
		if(listNodoUniversitario == null)
			return null;
		 
		List<Universitario> listUnivesitario = new ArrayList<Universitario>();
			
		for(int i=0; i< listNodoUniversitario.size(); i++) {
			
			Universitario universitario = new Universitario();
			universitario.setCodiceFiscaleBeneficiario(getTextAttributeNode(listNodoUniversitario.get(i), "CodiceFiscaleBeneficiario"));
			universitario.setCodiceFiscaleGenitore(getTextAttributeNode(listNodoUniversitario.get(i), "CodiceFiscaleGenitore")); 	
			universitario.setFlagCapacitaRedditoStudente(getBooleanTextAttributeNode(listNodoUniversitario.get(i), "FlagCapacitaRedditoStudente"));
			universitario.setFlagResidenzaStudente(getBooleanTextAttributeNode(listNodoUniversitario.get(i), "FlagResidenzaStudente"));
			universitario.setNumeroProtocolloRifGenitore(getTextAttributeNode(listNodoUniversitario.get(i), "FlagResidenzaStudente"));
			universitario.setPresenzaGenitori(getTextAttributeNode(listNodoUniversitario.get(i), "PresenzaGenitori"));
			listUnivesitario.add(universitario);
			}
		return listUnivesitario;
	}
	private List<ComponenteNucleoRistretto> getComponenteNucleoRistretto(Node nodo){
		 
		List<ComponenteNucleoRistretto> listCompNucleoRistretto = new ArrayList<ComponenteNucleoRistretto>();
		List<Node> listaNodi = getChildElements(nodo, "ComponenteNucleoRistretto");
			if(listaNodi == null || listaNodi.size() ==0 )
				return null;
			for(int i=0; i< listaNodi.size(); i++) {
				Node nodo1 = listaNodi.get(i);
				ComponenteNucleoRistretto componenteNucleoRistretto = new ComponenteNucleoRistretto();
				
				componenteNucleoRistretto.setAnagrafica(getAnagraficaBase(nodo1));
				componenteNucleoRistretto.setRelazioneConBeneficiario(getTextAttributeNode(nodo1, "RelazioneConBeneficiario"));
				
				listCompNucleoRistretto.add(componenteNucleoRistretto);
			}
		return listCompNucleoRistretto;
	}
	
	private ISEEBase getISEEBase(Node nodo, String modalitaDiCalcolo) {
		ISEEBase iseeBase = new ISEEBase();
		
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		iseeBase.setDataRilascio(dataRilascio);
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
					    	iseeBase.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals(modalitaDiCalcolo)) {
					    	iseeBase.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    
					}
				}
		
		return iseeBase;
	}
	
	private ISEEAgg getISEEAgg(Node nodo, String modalitaDiCalcolo) {
		ISEEAgg iseeAgg = new ISEEAgg();
		try {
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		iseeAgg.setDataRilascio(dataRilascio);
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
					    	iseeAgg.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals(modalitaDiCalcolo)) {
					    	iseeAgg.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    if(n1.getNodeName().equals("ComponenteAgguntiva")) {
					    	iseeAgg.setComponenteAggiuntiva(getComponenteAggiuntiva(n1));
						 }
					    
					}
				}
		}catch(Exception ex){
			//Gestire messaggio di errore parziale.. 
		}
		return iseeAgg;
	}
	private List<Ridotto> getRidotto(List<Node> listNodeRidotto){
		
		List<Ridotto> listRidotto = new ArrayList<Ridotto>();
		 
		    for (int i= 0; i< listNodeRidotto.size(); i++) {
		    	 Node n1 = listNodeRidotto.get(i);
				
		    	 Ridotto ridotto = new Ridotto();
		    	 if( n1.getNodeName().equals("NucleoRistretto")){
		    		 ridotto.setNucleoRistretto(getComponenteNucleoRistretto(n1)); 
				 }
		    	 if( n1.getNodeName().equals("ISEERistretto")){
		    		 ridotto.setIseeRistretto(getISEEBase(n1,"ModalitaCalcoloISEERistretto")); 
				 }
		    	 if( n1.getNodeName().equals("ISEESocioSanResRistretto")){
		    		 ridotto.setIseeSocioSanResRistretto(getComponenteSocioSanitarioResRistretto(n1)); 
				 }
		    }		
		
		return listRidotto;
	}
	
	private ComponenteSocioSanitarioResRistretto getComponenteSocioSanitarioResRistretto(Node nodo) {
	
		ComponenteSocioSanitarioResRistretto componenteSocioSanRistretto = new ComponenteSocioSanitarioResRistretto();
		NodeList nl = nodo.getChildNodes();
		    for (int i= 0; i< nl.getLength(); i++) {
		    	
				if( nl.item(i).getNodeType() == Node.ELEMENT_NODE ){
					 Node n1 = nl.item(i);
					
					if(n1.getNodeName().equals("ISEERistretto"))
						componenteSocioSanRistretto.setIseeOrd( n1.getNodeValue()); 
					if(n1.getNodeName().equals("ISEESocioSanResRistretto"))
						componenteSocioSanRistretto.setIseeSocioSanResRistretto( getISEEBase(n1, "ModalitaCalcoloISEESocioSanResRistretto")); 
					if(n1.getNodeName().equals("ISEERistrettoAgg"))
						componenteSocioSanRistretto.setIseeRistrettoAgg( getISEEAgg(n1, "ModalitaCalcoloISEERistrettoAgg")); 
					if(n1.getNodeName().equals("ISEESocioSanResRistrettoAgg"))
						componenteSocioSanRistretto.setIseeSocioSanResRistrettoAgg(getISEEAgg(n1, "ModalitaCalcoloISEERistrettoAgg")); 
					
				}
		    }
		return componenteSocioSanRistretto;
	}
	
	private List<DiscordanzeContestazione> getDiscordanze(List<Node> nodi) {
		if(nodi== null || nodi.size() ==0)
			return null;
		List<DiscordanzeContestazione>  listDiscordanze = new ArrayList<DiscordanzeContestazione> ();
		
		for(int i=0; i< nodi.size(); i++) {
			Node nodo = nodi.get(i);
			DiscordanzeContestazione discordanzeContestazione = new DiscordanzeContestazione();
			discordanzeContestazione.setCDDiscordanza(getTextAttributeNode(nodo, "CDDiscordanza"));
			discordanzeContestazione.setTipologiaDiscordanza(getTextAttributeNode(nodo, "TipologiaDiscordanza"));
			discordanzeContestazione.setSpecificaDiscordanza(getTextAttributeNode(nodo, "SpecificaDiscordanza"));
			listDiscordanze.add(discordanzeContestazione);
		}
		return listDiscordanze;
	}
	
	private VariazioneAttestazione getVariazione(Node nodo) {
		
		VariazioneAttestazione variazioneAttestazione = new VariazioneAttestazione();
		List<Node> listDiscordanze = getChildElements(nodo, "DiscordanzeContestazione"); 
	    variazioneAttestazione.setDiscordanzaContestazione(getDiscordanze(listDiscordanze));
	     
		 NodeList nl = nodo.getChildNodes();
	    for (int i= 0; i< nl.getLength(); i++) {
	    	
			if( nl.item(i).getNodeType() == Node.ELEMENT_NODE && nl.item(i).getNodeName().equals("DiscordanzeContestazione")){
				 Node n1 = nl.item(i);
				 variazioneAttestazione.setDataPresentazioneDSUVariata(getTextAttributeNode(n1, "DataPresentazioneDSUVariata"));
				 variazioneAttestazione.setDataVariazione(getTextAttributeNode(n1, "DataVariazione"));
				 variazioneAttestazione.setNoteVariazione(getTextAttributeNode(n1, "NoteVariazione"));
				 variazioneAttestazione.setNumeroProtocolloDSUVariata(getTextAttributeNode(n1, "NumeroProtocolloDSUVariata"));
				 variazioneAttestazione.setNumeroProtocolloMittenteDSUVariata(getTextAttributeNode(n1, "NumeroProtocolloMittenteDSUVariata"));
				 variazioneAttestazione.setTipovariazione(getTextAttributeNode(n1, "Tipovariazione"));
			}
	    }
	
		return variazioneAttestazione;
	}
	
	private List<Rapporto> getRapporto(List<Node> nodi) {
		List<Rapporto> listRapporto = new ArrayList<Rapporto>();
		 
	    for (int i= 0; i< nodi.size(); i++) {
	    	Node n1 = nodi.get(i);
			if(n1.getNodeType() == Node.ELEMENT_NODE){
				
					 if(n1.getNodeName().equals("Rapporto")) {
						 Rapporto rapporto = new Rapporto();
						 rapporto.setCodiceFiscaleOperatore(getTextAttributeNode(n1, "CodiceFiscale"));
						 rapporto.setConsistenzaMedia(getIntegerTextAttributeNode(n1, "CodiceFiscale"));
						 rapporto.setDataInizio(getTextAttributeNode(n1, "DataInizio"));
						 rapporto.setDataFine(getTextAttributeNode(n1, "DataFine"));
						 rapporto.setDescrizioneOperatoreFinanziario(getTextAttributeNode(n1, "DescrizioneOperatoreFinanziario"));
						 rapporto.setIdentificativo(getTextAttributeNode(n1, "Identificativo"));
						 rapporto.setSaldo3112(getIntegerTextAttributeNode(n1, "Saldo3112"));
						 rapporto.setTipoRapporto(getTextAttributeNode(n1, "TipoRapporto"));
						 rapporto.setValore(getIntegerTextAttributeNode(n1, "Valore"));
							listRapporto.add(rapporto);
					 }
				 }
	    }
		
		return listRapporto;
	}
	
	private List<OmissioneDifformita> getOmissioneDifformita( List<Node> nodo) throws Exception
	{
 		List<OmissioneDifformita> listOmissioneDifformita = new ArrayList<OmissioneDifformita>();
 		//NodeList nl = nodo.getChildNodes();
	    for (int i= 0; i< nodo.size(); i++) {
	    	
			if( nodo.get(i).getNodeType() == Node.ELEMENT_NODE){
				 Node n1 = nodo.get(i);
					 if(n1.getNodeName().equals("OmissioneDifformita")) {
						 OmissioneDifformita cn = new OmissioneDifformita();
							cn.setCodiceFiscale(getTextAttributeNode(n1, "CodiceFiscale"));
							cn.setDataControlloAE(getTextAttributeNode(n1, "DataControlloAE"));
							cn.setTipoOmissioneDifformita(getTextAttributeNode(n1, "TipoOmissioneDifformita"));
							List<Node> listRapporto = getChildElements(n1, "Rapporto");
							if(listRapporto != null)
								 cn.setRapporto(getRapporto(listRapporto));
							Node nodeRedditoAE = getChildElement(n1, "RedditoAE");
							if(nodeRedditoAE != null)
								 cn.setRedditoAE(getRedditoAE(nodeRedditoAE));
							listOmissioneDifformita.add(cn);
					 }
				 }
	    }
			
			
		 
		return listOmissioneDifformita;
	}
	
	public RedditoAE getRedditoAE( Node nodo) throws Exception
	{
			RedditoAE redditoAE = new RedditoAE();
		
 		    redditoAE.setRedditoAgrarioAE(getTextAttributeNode(nodo, "RedditoAgrarioAE"));
 		   	redditoAE.setRedditoPensioneAE(getTextAttributeNode(nodo, "RedditoPensioneAE"));
 		    redditoAE.setRedditoAgrarioAE(getTextAttributeNode(nodo, "RedditoAgrarioAE"));
 		    redditoAE.setRedditoImpostaSostitutivaAE(getTextAttributeNode(nodo, "RedditiImpostaSostitutivaAE"));
 		    redditoAE.setTipoModelloAE(getTextAttributeNode(nodo, "TipoModelloAE"));
 		    redditoAE.setRedditoLavoroDipendenteAE(getTextAttributeNode(nodo, "RedditoLavoroDipendenteAE"));
			return redditoAE;	 
	  }
	
	public List<ComponenteNucleo> getNucleoFamiliare( Node nodo) throws Exception
	{
 		List<ComponenteNucleo> nucleoFamiliare = new ArrayList<ComponenteNucleo>();
 		NodeList nl = nodo.getChildNodes();
	    for (int i= 0; i< nl.getLength(); i++) {
	    	
			if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
				 Node n1 = nl.item(i);
					 if(n1.getNodeName().equals("ComponenteNucleo")) {
						 ComponenteNucleo cn = new ComponenteNucleo();
							cn.setCodiceFiscale(getTextAttributeNode(n1, "CodiceFiscale"));
							cn.setCognome(getTextAttributeNode(n1, "Cognome"));
							cn.setNome( getTextAttributeNode(n1, "Nome"));
							cn.setRapportoConDichiarante(getTextAttributeNode(n1, "RapportoConDichiarante"));
							cn.setFlagAssenzaReddito(getBooleanTextAttributeNode(n1, "FlagAssenzaReddito"));
						    Node nodoFoglioCompNucleo = 	getChildElement(n1, "FoglioComponenteNucleo");
							
						    if(nodoFoglioCompNucleo != null)
						    	cn.setFoglioCompopneteNucleo(getFoglioComponenteNucleo(nodoFoglioCompNucleo));
							nucleoFamiliare.add(cn);
					 }
				 }
	    }
			
			
		 
		return nucleoFamiliare;
	}
	/***
	 * 			<!-- Dati Componente -->
			<!-- (Quadro FC1) -->
	 * <!-- Dati del componente del nucleo o della persona aggiuntiva -->
	 * @param nodo
	 * @return
	 */
	private FoglioComponenteNucleo getFoglioComponenteNucleo(Node nodo) {
		FoglioComponenteNucleo foglioCompNucleo = new FoglioComponenteNucleo();
		  if(nodo == null)
				return null;
		   Node nodoDatiCOmp =	getChildElement(nodo, "DatiComponente");
		   if(nodoDatiCOmp != null) {
			   foglioCompNucleo.setDatiComponete(getDatiComponente(nodoDatiCOmp));
			  
		   }
		   foglioCompNucleo.setCodiceFiscale(getTextAttributeNode(nodo, "CodiceFiscale"));
		   return foglioCompNucleo;
	}
	
	/***
	 * <!-- (Quadro FC1) -->
	 * @param nodo
	 * @return
	 */
	private DatiComponente getDatiComponente(Node nodo) {
		
		if(nodo == null)
			return null;
		DatiComponente  datiComp = new DatiComponente();
		datiComp.setAttivitaSoggetto(getTextAttributeNode(nodo, "AttivitaSoggetto"));
		datiComp.setFlagConvivenzaAnagrafica(getBooleanTextAttributeNode(nodo, "FlagConvivenzaAnagrafica"));
		Node nodoAnag = getChildElement(nodo, "Anagrafica");
				if(nodoAnag != null)
						datiComp.setAnagrafica(getAnagrafica(nodoAnag));
		Node nodoInd = getChildElement(nodo, "Indirizzo");
				if(nodoInd != null)
						datiComp.setResidenza(getResidenza(nodoInd));
			 nodoInd = getChildElement(nodo, "Residenza");
				if(nodoInd != null)
						datiComp.setResidenza(getResidenza(nodoInd));
		return datiComp;
	}
	
	
	public Residenza getResidenza(Node nodo) {
		
		if(nodo == null)
			return null;
		Residenza  residenza = new Residenza();
		residenza.setCap(getTextAttributeNode(nodo, "Cap"));
		residenza.setCellulare(getTextAttributeNode(nodo, "Cellulare"));
		residenza.setCivico(getTextAttributeNode(nodo, "Civico"));
		residenza.setComune(getTextAttributeNode(nodo, "Comune"));
		residenza.setIndirizzo(getTextAttributeNode(nodo, "Indirizzo"));
		residenza.setIndirizzoEmail(getTextAttributeNode(nodo, "IndirizzoEmail"));
		residenza.setProvincia(getTextAttributeNode(nodo, "Provincia"));
		residenza.setTelefono(getTextAttributeNode(nodo, "Telefono"));
 		return residenza;
	}
	
	/***
	 * trattasi di ISEE Corrente
	 * @param nodo
	 * @return
	 * @throws Exception
	 */
	public Ordinario getOrdinario( Node nodo) throws Exception { //input ordinario
		
 
		Ordinario ordinario = new Ordinario();
		//Attrbuti falcotativi :
		NamedNodeMap attributi = nodo.getAttributes();
		if(attributi != null && attributi.getLength() > 0) {
			ordinario.setCorrente(getTextAttributeNode(nodo, "Corrente"));
			ordinario.setDataPresentazioneModuloSostituivo(getTextAttributeNode(nodo, "DataPresentazioneModuloSostitutivo"));
			ordinario.setProtocolloDSUModuloSostitutivo(getTextAttributeNode(nodo, "ProtocolloDSUModuloSostitutivo"));
			
		}
		//Fine attributi falcoltativi
		
		NodeList nl = nodo.getChildNodes();
	    for (int i= 0; i< nl.getLength(); i++) {
	    	
			if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
				 Node n1 = nl.item(i);
				 if(n1.getNodeName().equals("NucleoFamiliare")) {
					 ordinario.setComponenteNucleo(getNucleoFamiliare( n1));
				 }
				  if(n1.getNodeName().equals("ISEEOrdinario")) {
					  ordinario .setIseeOrdinario(getIseeOrdinario( n1));
				 }
			   if(n1.getNodeName().equals("ISEEUni")) {
				   ordinario.setStudenteUniversitario(getStudenteUniversitario( n1));
				}
				// 	<!-- ISEE Minorenne -->
			   if(n1.getNodeName().equals("ISEEMin")) {
				   ordinario.setIseeMin(getIseeMin( n1));
				}
			   /***
			    * <!-- ISEE socio sanitario - residenziale -->
				 <!-- casi 1) 8) 10) 12 14) nucleo ordinario gli altri casi sono esplicitati nell'elemento Ridotto -->
			    */
			   if(n1.getNodeName().equals("ISEESocioSanRes")) {
				   ordinario.setComponenteSocioSanitarioRes(getISEESocioSanRes(n1));
				}
			}
			
		}
	    return ordinario;
		
	}
	
	public ISEEMin getISEEMin( Node nodo) throws Exception { //input ordinario
		
		 
		ISEEMin iseeMinore = new ISEEMin();
		 List<ComponenteMinorenne> listComponenteMinore = new ArrayList<ComponenteMinorenne>();
		
		NodeList nl = nodo.getChildNodes();
	    for (int i= 0; i< nl.getLength(); i++) {
	    	
			if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
				 Node n1 = nl.item(i);
				 if(n1.getNodeName().equals("ComponenteMinorenne")) {
					 listComponenteMinore.add(getComponenteMinore(n1));
				 }
			 	
			}
			
		}
	    iseeMinore.setComponentiMinoorenni(listComponenteMinore);
	    return iseeMinore;
		
	}
	private ComponenteMinorenne getComponenteMinore( Node nodo) throws Exception {
			
			
		ComponenteMinorenne componenteMinore = new ComponenteMinorenne();
		 
			 if(nodo.getNodeName().equals("ComponenteMinorenne")) {
				 
			   componenteMinore.setAttributi(getAnagraficaAgg(nodo));
			   
				NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
						 
						 if(n1.getNodeName().equals("ISEEMinAttr")) {
							 componenteMinore.setIseeMinAttr(getISEEMinAttr(nodo));
						 }
						 if(n1.getNodeName().equals("ISEEMinAgg")) {
							 componenteMinore.setIseeMinAgg(getISEEMinAgg(nodo));
						 }
						
					}
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){ // Da verificare se l'estrazione dell'elemento avviene cosi' 
						 if(n1.getNodeName().equals("ISEEOrd")) {
							 componenteMinore.setIseeOrd(n1.getNodeValue()); 
						 }
						 if(n1.getNodeName().equals("ISEENonCalcolabile")) {
							 componenteMinore.setIseeNonCalcolabile(n1.getNodeValue()); 
							 //Caso del minore con genitore non convivente che entra come componente aggiuntiva,
							 //ma i dati della componente aggiuntiva non sono stai forniti.
						 }
						
					}
					
				}
				 
				 
				
			  }
				 
			 return componenteMinore;
		}
	
	private List<ComponenteAggiuntiva> getComponenteAggiuntiva( Node nodo)  {
	
		
		List<ComponenteAggiuntiva> listComponenteAggiuntiva = new ArrayList<ComponenteAggiuntiva>();
		
		 
		ComponenteAggiuntiva componenteAggiuntiva = new ComponenteAggiuntiva();
				NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
						 
						 if(n1.getNodeName().equals("Cognome")) {
							 componenteAggiuntiva.setCognome(n1.getNodeValue());
						 }
						 if(n1.getNodeName().equals("Nome")) {
							 componenteAggiuntiva.setNome(n1.getNodeValue());
						 }
						 if(n1.getNodeName().equals("CodiceFiscale")) {
							 componenteAggiuntiva.setCodiceFiscale(n1.getNodeValue());
						 }
						 if(n1.getNodeName().equals("ValoreComponenteAggiuntiva")) { // Valore non obbligatorio
							 componenteAggiuntiva.setValoreComponenteAggiuntiva(n1.getNodeValue());
						 }
					}
			 		
				}
				 
			    listComponenteAggiuntiva.add(componenteAggiuntiva);
		 
			 return listComponenteAggiuntiva;
		}
	/***
	 * Caso del minore con genitore non convivente che entra come componente attratta,
	 * @param nono
	 * @return
	 * @throws Exception 
	 */
	private ISEEAgg getISEEMinAttr(Node nodo) throws Exception {
		ISEEAgg iseeMinAttr = new ISEEAgg();
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
							 iseeMinAttr.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals("ModalitaCalcoloISEEMinAttr")) {
							 iseeMinAttr.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    if(n1.getNodeName().equals("ComponenteAttratta")) {
					    	
							 iseeMinAttr.setComponenteAggiuntiva(getComponenteAggiuntiva(n1.getFirstChild()));
						 }
					}
				}
		return iseeMinAttr;
	}
	/*
	 * <!-- ISEE Uni con componente attratta -->
	 * 
	 */
	private ISEEAgg getISEEUniAttr(Node nodo) {
		ISEEAgg iseeMinAttr = new ISEEAgg();
		
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		iseeMinAttr.setDataRilascio(dataRilascio);
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
							 iseeMinAttr.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals("ModalitaCalcoloISEEUniAttr")) {
							 iseeMinAttr.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    if(n1.getNodeName().equals("ComponenteAttratta")) {
					    	
							 iseeMinAttr.setComponenteAggiuntiva(getComponenteAggiuntiva(n1.getFirstChild()));
						 }
					}
				}
		return iseeMinAttr;
	}
	
	private ISEEBase getElementISEESocioSanRes(Node nodo) {
		ISEEBase iseeBase = new ISEEBase();
		
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		iseeBase.setDataRilascio(dataRilascio);
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
					    	iseeBase.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals("ModalitaCalcoloISEESocioSanResRes")) {
					    	iseeBase.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    
					}
				}
		return iseeBase;
	}
	
	private ISEEAgg getISEESocioSanResAgg(Node nodo) throws Exception {
		ISEEAgg iseeMinAttr = new ISEEAgg();
		
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		iseeMinAttr.setDataRilascio(dataRilascio);
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
							 iseeMinAttr.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals("ModalitaCalcoloIISEESocioSanResAgg")) {
							 iseeMinAttr.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    if(n1.getNodeName().equals("ComponenteAttratta")) {
					    	
							 iseeMinAttr.setComponenteAggiuntiva(getComponenteAggiuntiva(n1.getFirstChild()));
						 }
					}
				}
		return iseeMinAttr;
	}
	
	/*
	 * <!-- ISEE Uni con componente aggiuntiva -->
	 */
	private ISEEAgg getISEEUniAgg(Node nodo) {
		ISEEAgg iseeMinAttr = new ISEEAgg();
		
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		iseeMinAttr.setDataRilascio(dataRilascio);
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
							 iseeMinAttr.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals("ModalitaCalcoloISEEUniAgg")) {
							 iseeMinAttr.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    if(n1.getNodeName().equals("ComponenteAttratta")) {
					    	
							 iseeMinAttr.setComponenteAggiuntiva(getComponenteAggiuntiva(n1.getFirstChild()));
						 }
					}
				}
		return iseeMinAttr;
	}
	
	private ISEEAgg getISEEOrdAgg(Node nodo) throws Exception {
		ISEEAgg iseeMinAttr = new ISEEAgg();
		
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		iseeMinAttr.setDataRilascio(dataRilascio);
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
							 iseeMinAttr.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals("ModalitaCalcoloISEEOrdAgg")) {
							 iseeMinAttr.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    if(n1.getNodeName().equals("ComponenteAttratta")) {
					    	
							 iseeMinAttr.setComponenteAggiuntiva(getComponenteAggiuntiva(n1.getFirstChild()));
						 }
					}
				}
		return iseeMinAttr;
	}
	private ElementoNucleo getElementoNucleo(Node nodo) {
		
		ElementoNucleo elementoNucleo = new ElementoNucleo();
		if(nodo == null)
			return null;
		elementoNucleo.setCodiceFiscale (getTextAttributeNode(nodo, "CodiceFiscale"));
		elementoNucleo.setCognome(getTextAttributeNode(nodo, "Cognome"));
		elementoNucleo.setNome (getTextAttributeNode(nodo, "Nome"));
		elementoNucleo.setRapportoConDichiarante (getTextAttributeNode(nodo, "RapportoConDichiarante"));
		return elementoNucleo;
	}
	
	/*
	 * <!-- ISEE universitario con studente attratto -->
	 */
	private ISEEUniStudAttratto getISEEUniStudAttratto(Node nodo)  {
		ISEEUniStudAttratto iseeUniStudAttr = new ISEEUniStudAttratto();
		
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		iseeUniStudAttr.setDataRilascio(dataRilascio);
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
					    	iseeUniStudAttr.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals("ModalitaCalcoloISEEUniStudAtt")) {
					    	iseeUniStudAttr.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    if(n1.getNodeName().equals("ComponenteAttratta")) {
					    	
					    	iseeUniStudAttr.setComponenteAggiuntiva(getComponenteAggiuntiva(n1.getFirstChild()));
						 }
					    
					    if(n1.getNodeName().equals("NucleoFamiliareAttratto")) {
					    	
					        List<Node> listaNodi2 =	getChildElements(n1, "ElementoNucleoAttratto");
					        for(int j=0; j< listaNodi2.size(); j++) {
					        	Node nodeLiv2 =	listaNodi2.get(j);
					        	iseeUniStudAttr.addElementoNucleo(getElementoNucleo(nodeLiv2));
					        }
							
						 }
					}
				}
		return iseeUniStudAttr;
	}
	
	/*
	 * <!-- ISEE universitario con studente attratto nel nucleo di un genitore e l'altro che entra come componente aggiuntiva-->
	 */
	private ISEEUniStudAttratto getISEEUniStudAttrattoCompAggiuntiva(Node nodo) {
		ISEEUniStudAttratto iseeUniStudAttr = new ISEEUniStudAttratto();
		
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		iseeUniStudAttr.setDataRilascio(dataRilascio);
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
					    	iseeUniStudAttr.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals("ModalitaCalcoloISEEUniStudAttrattoCompAggiuntiva")) {
					    	iseeUniStudAttr.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    if(n1.getNodeName().equals("ComponenteAttratta")) {
					    	
					    	iseeUniStudAttr.setComponenteAggiuntiva(getComponenteAggiuntiva(n1.getFirstChild()));
						 }
					    
					    if(n1.getNodeName().equals("NucleoFamiliareAttratto")) {
					    	
					        List<Node> listaNodi2 =	getChildElements(n1, "ElementoNucleoAttratto");
					        for(int j=0; j< listaNodi2.size(); j++) {
					        	Node nodeLiv2 =	listaNodi2.get(j);
					        	iseeUniStudAttr.addElementoNucleo(getElementoNucleo(nodeLiv2));
					        }
							
						 }
					}
				}
		return iseeUniStudAttr;
	}
	/*
	 * //<!-- ISEE universitario con studente attratto nel nucleo di un genitore e l'altro che entra come componente attratta-->
					  
	 */
	private ISEEUniStudAttratto getISEEUniStudAttrattoCompAttratta(Node nodo) {
		ISEEUniStudAttratto iseeUniStudAttr = new ISEEUniStudAttratto();
		
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		iseeUniStudAttr.setDataRilascio(dataRilascio);
		  		NodeList nl = nodo.getChildNodes();
			    for (int i= 0; i< nl.getLength(); i++) {
			    	Node n1 = nl.item(i);
					if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					    if(n1.getNodeName().equals("Valori")) {
					    	iseeUniStudAttr.setValori(getValori(n1));
						 }
					    if(n1.getNodeName().equals("ModalitaCalcoloISEEUniStudAttrattoCompAttratta")) {
					    	iseeUniStudAttr.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
						 }
					    if(n1.getNodeName().equals("ComponenteAttratta")) {
					    	
					    	iseeUniStudAttr.setComponenteAggiuntiva(getComponenteAggiuntiva(n1.getFirstChild()));
						 }
					    
					    if(n1.getNodeName().equals("NucleoFamiliareAttratto")) {
					    	
					        List<Node> listaNodi2 =	getChildElements(n1, "ElementoNucleoAttratto");
					        for(int j=0; j< listaNodi2.size(); j++) {
					        	Node nodeLiv2 =	listaNodi2.get(j);
					        	iseeUniStudAttr.addElementoNucleo(getElementoNucleo(nodeLiv2));
					        }
							
						 }
					}
				}
		return iseeUniStudAttr;
	}
	
	/***
	 * Caso del minore con genitore non convivente che entra come componente aggiuntiva
	 * @param nono
	 * @return
	 * @throws Exception 
	 */
	private ISEEAgg getISEEMinAgg(Node nodo) throws Exception {
		ISEEAgg iseeMinAttr = new ISEEAgg();
		
		NodeList nl = nodo.getChildNodes();
	    for (int i= 0; i< nl.getLength(); i++) {
	    	Node n1 = nl.item(i);
			if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
			    if(n1.getNodeName().equals("Valori")) {
					 iseeMinAttr.setValori(getValori(n1));
				 }
			    if(n1.getNodeName().equals("ModalitaCalcoloISEEMinAggr")) {
					 iseeMinAttr.setModalitaCalcolo(getModalitaCalcoloISEE(n1));
				 }
			    if(n1.getNodeName().equals("ComponenteAggiuntiva")) {
			    	
					 iseeMinAttr.setComponenteAggiuntiva(getComponenteAggiuntiva(n1));
				 }
			}
		}
		
		return iseeMinAttr;
	}
	
	private  List<Node> getChildElements(Node node, String filter) {
		  if(node == null)
			  return null;
		  NodeList nl = node.getChildNodes();
		  List<Node> childEles = new ArrayList<>();
		  for (int i = 0; i < nl.getLength(); i++) {
		    Node node1 = nl.item(i);
		    if (node1 instanceof Element  && node1.getNodeName().equals(filter)) {
		      childEles.add(node1);
		    }
		  }
		  return childEles;
		}
	private  Node getChildElement(Node node, String filter) {
		  
		  NodeList nl = node.getChildNodes();
		  
		  for (int i = 0; i < nl.getLength(); i++) {
		    Node node1 = nl.item(i);
		    if (node1 instanceof Element &&  node1.getNodeName().equals(filter)) {
		      return (Node) node1;
		    }
		  }
		  return null;
		}
	public ISEEMin getIseeMin( Node nodo) throws Exception
	{
		ISEEMin iseeMin = null;
		ISEEBase iseeBase = null;
		if(nodo != null) {
			iseeMin = new ISEEMin();
			iseeBase = new ISEEBase();
		}
		List<ComponenteMinorenne> listComponentiMinori = new ArrayList<ComponenteMinorenne>();
		List< Node> xmlNodes= getChildElements(nodo, "ComponenteMinorenne");
		for(int i = 0; i < xmlNodes.size(); i++) {
			 
			listComponentiMinori.add(getComponenteMinore(xmlNodes.get(i)));
		}
		iseeMin.setComponentiMinoorenni(listComponentiMinori);
		return iseeMin;
 
	}
	
	public ISEEOrdinario getIseeOrdinario( Node nodo) throws Exception
	{
		ISEEOrdinario iseeOrdinario = null;
		ISEEBase iseeBase = null;
		if(nodo != null) {
			iseeOrdinario = new ISEEOrdinario();
			iseeBase = new ISEEBase();
		}
		String dataRilascio = (getTextAttributeNode(nodo, "DataRilascio"));
		NodeList nl = nodo.getChildNodes();
	    for (int i= 0; i< nl.getLength(); i++) {
	    	
			if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
				 Node n1 = nl.item(i);
			 
				
			    iseeBase.setDataRilascio(dataRilascio);
			    if(n1.getNodeName().equals("ModalitaCalcoloISEEOrdinario")) { //DetrazionePatrimonioMobiliare
			    	iseeBase.setModalitaCalcolo(getModalitaCalcoloISEE( n1));
			    }
			    if(n1.getNodeName().equals("Valori")) {
			    	iseeBase.setValori(getValori( n1));
			    }
		 	}
	    }
	    iseeOrdinario.setDatiIsee(iseeBase);
	   
		return iseeOrdinario;
	}
	
	private Valori getValori( Node nodo) {
		
		
		Valori valori = new Valori();
	 
		 if(nodo.getNodeName().equals("Valori")) {
			 
			 valori.setISE(getTextAttributeNode(nodo, "ISE"));
			 valori.setISEE(getTextAttributeNode(nodo, "ISEE"));
			 valori.setISP(getTextAttributeNode(nodo, "ISP"));
			 valori.setISR(getTextAttributeNode(nodo, "ISR"));
			 valori.setScalaEquivalenza(getTextAttributeNode(nodo, "ScalaEquivalenza"));
		 }
			 
		 return valori;
	}
	
	private ModalitaCalcoloISEE getModalitaCalcoloISEE( Node nodo)  {
		

		 ModalitaCalcoloISEE modalitaCalcoloISEE = new ModalitaCalcoloISEE();
	 
			 //if(nodo.getNodeName().equals("ModalitaCalcoloISEEOrdinario")) {
				 modalitaCalcoloISEE.setDetrazioni(getTextAttributeNode(nodo, "Detrazioni"));
				 modalitaCalcoloISEE.setDetrazioniPatrimonioMobiliare(getTextAttributeNode(nodo, "DetrazionePatrimonioMobiliare"));
				 modalitaCalcoloISEE.setISEE(getTextAttributeNode(nodo, "ISEE"));
				 modalitaCalcoloISEE.setISP(getTextAttributeNode(nodo, "ISP"));
				 modalitaCalcoloISEE.setISR(getTextAttributeNode(nodo, "ISR"));
				 modalitaCalcoloISEE.setMaggiorazioni(getTextAttributeNode(nodo, "Maggiorazioni"));
				 modalitaCalcoloISEE.setParametroNucleo(getTextAttributeNode(nodo, "ParametroNucleo"));
				 modalitaCalcoloISEE.setPatrimonioMobiliare(getTextAttributeNode(nodo, "PatrimonioMobiliare"));
				 modalitaCalcoloISEE.setRedditoFigurativoPatrimonioMobiliare(getTextAttributeNode(nodo, "RedditoFigurativoPatrimonioMobiliare"));
				 modalitaCalcoloISEE.setScalaEquivalenza(getTextAttributeNode(nodo, "ScalaEquivalenza"));
				 modalitaCalcoloISEE.setSommaRedditiComponenti(getTextAttributeNode(nodo, "SommaRedditiComponenti"));
				 modalitaCalcoloISEE.setSommaRedditiComponentiCorrente(getTextAttributeNode(nodo, "SommaRedditiComponentiCorrente"));
			// }
		 
		
		 return modalitaCalcoloISEE;
	}
	private List<StudenteUniversitario> getStudenteUniversitario( Node nodo) {
 	List<StudenteUniversitario> listStudenteUniversitario = new ArrayList<StudenteUniversitario>();
		 NodeList nl = nodo.getChildNodes();
		 for(int i=0; i<nl.getLength(); i++ ) {
			 if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
				 Node n1 = nl.item(i);
				 
				 if(n1.getNodeName().equals("StudenteUniversitario")) {
					 StudenteUniversitario su = new StudenteUniversitario();
					 	su.setAttributi(getAnagraficaAgg(n1));
					 	
					   Node nodeRes = null;
					   nodeRes =  getChildElement(n1, "ISEEOrd");
					   if(nodeRes != null) {
						   su.setIseeOrd(nodeRes.getTextContent());
					   }
					   nodeRes =  getChildElement(n1, "ISEEUniAttr");
					   if(nodeRes != null) {
						   su.setIseeUniAttr(getISEEUniAttr(nodeRes));
					   }
					   nodeRes =  getChildElement(n1, "ISEEUniAgg");
					   if(nodeRes != null) {
						   su.setIseeUniAgg(getISEEUniAgg(nodeRes));
					   }
					   nodeRes =  getChildElement(n1, "ISEEUniStudAttratto");
					   if(nodeRes != null) {
						   su.setIseeUniStudAttratto(getISEEUniStudAttratto(nodeRes));
					   }
					 		 	
					   nodeRes =  getChildElement(n1, "ISEEUniStudAttrattoCompAttratta");
					   if(nodeRes != null) {
						   su.setIseeUniStudAttrattoCompAttratta(getISEEUniStudAttrattoCompAttratta(nodeRes));
					   }
					   nodeRes =  getChildElement(n1, "ISEEUniStudAttrattoCompAggiuntiva");
					   if(nodeRes != null) {
						   su.setIseeUniStudAttrattoCompAttratta(getISEEUniStudAttrattoCompAggiuntiva(nodeRes));
					   }
					   nodeRes =  getChildElement(n1, "ISEENonCalcolabile");
					   if(nodeRes != null) {
						   su.setIseeNonCalcolabile(nodeRes.getTextContent());
					   }
					  listStudenteUniversitario.add(su);
					 }
				 }
		 }
	
			
		 
		return listStudenteUniversitario;
	}
	
	private  List<ComponenteSocioSanitarioRes>  getISEESocioSanRes( Node nodo) throws Exception {
	 	List<ComponenteSocioSanitarioRes> listComponenteSocioSanitarioRes = new ArrayList<ComponenteSocioSanitarioRes>();
			 NodeList nl = nodo.getChildNodes();
			 for(int i=0; i<nl.getLength(); i++ ) {
				 if( nl.item(i).getNodeType() == Node.ELEMENT_NODE){
					 Node n1 = nl.item(i);
					 
					 if(n1.getNodeName().equals("StudenteUniversitario")) {
						 ComponenteSocioSanitarioRes su = new ComponenteSocioSanitarioRes();
						 	su.setAttributi(getAnagraficaAgg(n1));
						 	
						   Node nodeRes = null;
						   nodeRes =  getChildElement(n1, "ISEEOrd");
						   if(nodeRes != null) {
							   su.setIseeOrd(nodeRes.getTextContent());
						   }
						   nodeRes =  getChildElement(n1, "ISEESocioSanRes");
						   if(nodeRes != null) {
							   su.setIseeSocioSanRes(getElementISEESocioSanRes(nodeRes));
						   }
						   nodeRes =  getChildElement(n1, "ISEEOrdAgg");
						   if(nodeRes != null) {
							   su.setIseeOrdAgg(getISEEOrdAgg(nodeRes));
						   }
						   nodeRes =  getChildElement(n1, "ISEESocioSanResAgg");
						   if(nodeRes != null) {
							   su.setIseeSocioSanResAgg(getISEESocioSanResAgg(nodeRes));
						   }
					 
						   listComponenteSocioSanitarioRes.add(su);
						 }
					 }
			 }
		
				
			 
			return listComponenteSocioSanitarioRes;
		}
	
	private AnagraficaAgg getAnagraficaAgg(Node nodo) {
		AnagraficaAgg anagraficaAgg = new AnagraficaAgg();
		anagraficaAgg.setCodiceFiscale(getTextAttributeNode(nodo, "CodiceFiscale"));
		anagraficaAgg.setCognome(getTextAttributeNode(nodo, "Cognome"));
		anagraficaAgg.setISEE(getTextAttributeNode(nodo, "ISEE"));
		anagraficaAgg.setNome(getTextAttributeNode(nodo, "Nome"));
		return anagraficaAgg;
	}
	
	private Operazione getOperazione(Node nodo) {
		Operazione operazione = new Operazione();
		operazione.setTipo(getTextAttributeNode(nodo, "Tipo"));
		operazione.setCodiceFiscaleRiferimento(getTextAttributeNode(nodo, "CodiceFiscaleRiferimento"));
		operazione.setNumeroProtocolloRiferimento(getTextAttributeNode(nodo, "NumeroProtocolloRiferimento"));
	 
		return operazione;
	}
	private AnagraficaBase getAnagraficaBase(Node nodo) {
		AnagraficaBase anagraficaBase = new AnagraficaBase();
		anagraficaBase.setCodiceFiscale(getTextAttributeNode(nodo, "CodiceFiscale"));
		anagraficaBase.setCognome(getTextAttributeNode(nodo, "Cognome"));
		anagraficaBase.setNome(getTextAttributeNode(nodo, "Nome"));
		return anagraficaBase;
	}
	
	private Anagrafica getAnagrafica(Node nodo) {
		Anagrafica anagrafica = new Anagrafica();
		anagrafica.setCodiceFiscale(getTextAttributeNode(nodo, "CodiceFiscale"));
		anagrafica.setCognome(getTextAttributeNode(nodo, "Cognome"));
		anagrafica.setNome(getTextAttributeNode(nodo, "Nome"));
		anagrafica.setDataNascita(getTextAttributeNode(nodo, "DataNascita"));
		anagrafica.setSesso(getTextAttributeNode(nodo, "Sesso"));
		anagrafica.setCodiceComuneNascita(getTextAttributeNode(nodo, "CodiceComuneNascita"));
		anagrafica.setProvinciaNascita(getTextAttributeNode(nodo, "ProvinciaNascita"));
		anagrafica.setCittadinanza(getTextAttributeNode(nodo, "Cittadinanza"));
		return anagrafica;
	}
}
