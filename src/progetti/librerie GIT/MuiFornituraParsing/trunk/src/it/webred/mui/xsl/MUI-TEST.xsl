<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.agenziaterritorio.it/ICI.xsd">
  <xsl:output method="text" indent="no"/>

<xsl:template match="//*[name()='DatiOut']">

	<xsl:variable name="numfile" select="*[name()='DatiRichiesta']/*[name()='N_File']"/>

	<xsl:apply-templates select="*[name()='DatiPresenti']/*[name()='DatiGenerali']"/><!-- applica template x RECORD TIPO 1  -->
	<xsl:for-each select="*[name()='DatiPresenti']/*[name()='Variazioni']/*[name()='Variazione']">

		<xsl:apply-templates select="*[name()='Trascrizione']"><!-- applica template x RECORD TIPO 2 -->			
			<xsl:with-param name="vId"><xsl:value-of select="position()"/><xsl:choose><xsl:when test="$numfile != '' and $numfile &lt; 10">0</xsl:when><xsl:otherwise></xsl:otherwise></xsl:choose><xsl:value-of select="$numfile"/></xsl:with-param>
		</xsl:apply-templates>

		<xsl:apply-templates select="*[name()='Soggetti']/*[name()='Soggetto']"><!-- applica template x RECORD TIPO 3  -->
			<xsl:with-param name="vId"><xsl:value-of select="position()"/><xsl:choose><xsl:when test="$numfile != '' and $numfile &lt; 10">0</xsl:when><xsl:otherwise></xsl:otherwise></xsl:choose><xsl:value-of select="$numfile"/></xsl:with-param>
		</xsl:apply-templates>
		
		<!--
		<xsl:apply-templates select="*[name()='Soggetti']/*[name()='Soggetto']/*[name()='PersonaFisica'] | *[name()='Soggetti']/*[name()='Soggetto']/*[name()='PersonaGiuridica']">
			<xsl:with-param name="vId"><xsl:value-of select="position()"/><xsl:choose><xsl:when test="$numfile != '' and $numfile &lt; 10">0</xsl:when><xsl:otherwise></xsl:otherwise></xsl:choose><xsl:value-of select="$numfile"/></xsl:with-param>
		</xsl:apply-templates>
		-->
		
		<xsl:apply-templates select="*[name()='Soggetti']/*[name()='Soggetto']/*[name()='Recapito']"><!-- applica template x RECORD TIPO 5  -->
			<xsl:with-param name="vId"><xsl:value-of select="position()"/><xsl:choose><xsl:when test="$numfile != '' and $numfile &lt; 10">0</xsl:when><xsl:otherwise></xsl:otherwise></xsl:choose><xsl:value-of select="$numfile"/></xsl:with-param>
		</xsl:apply-templates>
		
		<xsl:apply-templates select="*[name()='Immobili']/*[name()='Fabbricato']"><!-- applica template x RECORD TIPO 6  -->
			<xsl:with-param name="vId"><xsl:value-of select="position()"/><xsl:choose><xsl:when test="$numfile != '' and $numfile &lt; 10">0</xsl:when><xsl:otherwise></xsl:otherwise></xsl:choose><xsl:value-of select="$numfile"/></xsl:with-param>
		</xsl:apply-templates>
		
		<xsl:apply-templates select="*[name()='Immobili']/*[name()='Terreno']"><!-- applica template x RECORD TIPO 8  -->
			<xsl:with-param name="vId"><xsl:value-of select="position()"/><xsl:choose><xsl:when test="$numfile != '' and $numfile &lt; 10">0</xsl:when><xsl:otherwise></xsl:otherwise></xsl:choose><xsl:value-of select="$numfile"/></xsl:with-param>
		</xsl:apply-templates>
		
	</xsl:for-each>
	<xsl:apply-templates select="*[name()='DatiPresenti']"/><!-- applica template x RECORD TIPO 9  -->
</xsl:template>


<!-- RECORD TIPO 1:  DATI GENERALI -->
<xsl:template match="*[name()='DatiPresenti']/*[name()='DatiGenerali']">
1|<xsl:value-of select="*[name()='CodiceAmministrativo']"/>|<xsl:value-of select="*[name()='DataIniziale']"/>|<xsl:value-of select="*[name()='DataFinale']"/>|<xsl:value-of select="*[name()='DataEstrazioneConservatoria']"/>|<xsl:value-of select="*[name()='DataEstrazioneCatasto']"/>|</xsl:template>

<!-- RECORD TIPO 9:  CONTATORI -->
<xsl:template match="*[name()='DatiPresenti']"> 
9|<xsl:value-of select="1+count(//*[name()='DatiGenerali'])+count(//*[name()='Trascrizione'])+count(//*[name()='Soggetto'])+count(//*[name()='Recapito'])+count(//*[name()='Fabbricato'])+count(//*[name()='Terreno'])+count(//*[name()='Titolarita'])+count(//*[name()='Identificativi'])"/>|<xsl:value-of select="count(//*[name()='Nota'])"/>|<xsl:value-of select="count(//*[name()='Nota'][*[name()='EsitoNota']='0'])"/>|<xsl:value-of select="count(//*[name()='Nota'][*[name()='EsitoNota']!='0'])"/>|<xsl:value-of select="count(//*[name()='Fabbricato']) + count(//*[name()='Terreno'])"/>|<xsl:value-of select="count(//*[name()='Terreno'])"/>|<xsl:value-of select="count(//*[name()='Fabbricato'])"/>|</xsl:template>


<!-- RECORD TIPO 2:  INFORMAZIONI  RELATIVE ALLA NOTA DI TRASCRIZIONE -->
<xsl:template match="*[name()='Trascrizione']">
 <xsl:param name="vId">0</xsl:param>
.<xsl:value-of select="$vId"/>|2|<xsl:value-of select="*[name()='Nota']/*[name()='TipoNota']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='NumeroNota']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='ProgressivoNota']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='Anno']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='DataValiditaAtto']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='DataPresentazioneAtto']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='EsitoNota']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='EsitoNotaNonRegistrata']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='DataRegistrazioneInAtti']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='NumeroRepertorio']"/>|<xsl:choose><xsl:when test="count(*[name()='Nota']/*[name()='NotaRettificata']) > 0">1</xsl:when><xsl:otherwise>0</xsl:otherwise></xsl:choose>|<xsl:value-of select="*[name()='Nota']/*[name()='NotaRettificata']/*[name()='TipoNota']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='NotaRettificata']/*[name()='NumeroNota']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='NotaRettificata']/*[name()='DataPresentazioneAtto']"/>|<xsl:value-of select="*[name()='Rogante']/*[name()='CognomeNome']"/>|<xsl:value-of select="*[name()='Rogante']/*[name()='CodiceFiscale']"/>|<xsl:value-of select="*[name()='Rogante']/*[name()='Sede']"/>|<xsl:value-of select="*[name()='RegistrazioneDifferita']"/>|<xsl:value-of select="*[name()='DataInDifferita']"/>|<xsl:value-of select="*[name()='Nota']/*[name()='CodiceAtto']"/>|</xsl:template>


<!-- RECORD TIPO 3: INFORMAZIONI  RELATIVE AI SOGGETTI  -->
<xsl:template match="*[name()='Soggetti']/*[name()='Soggetto']">
 <xsl:param name="vId">0</xsl:param>
.<xsl:value-of select="$vId"/>|3|<xsl:value-of select="*[name()='IdSoggettoNota']"/>|<xsl:value-of select="*[name()='IdSoggettoCatastale']"/>|<xsl:choose><xsl:when test="count(*[name()='PersonaFisica']) > 0">P</xsl:when><xsl:when test="count(*[name()='PersonaGiuridica']) > 0">G</xsl:when><xsl:otherwise></xsl:otherwise></xsl:choose>|<xsl:value-of select="*[name()='PersonaFisica']/*[name()='Cognome']"/>|<xsl:value-of select="*[name()='PersonaFisica']/*[name()='Nome']"/>|<xsl:value-of select="*[name()='PersonaFisica']/*[name()='Sesso']"/>|<xsl:value-of select="*[name()='PersonaFisica']/*[name()='DataNascita']"/>|<xsl:value-of select="*[name()='PersonaFisica']/*[name()='LuogoNascita']"/>|<xsl:value-of select="*[name()='PersonaFisica']/*[name()='CodiceFiscale']"/>|<xsl:value-of select="*[name()='PersonaGiuridica']/*[name()='Denominazione']"/>|<xsl:value-of select="*[name()='PersonaGiuridica']/*[name()='Sede']"/>|<xsl:value-of select="*[name()='PersonaGiuridica']/*[name()='CodiceFiscale']"/>|<xsl:apply-templates select="*[name()='DatiTitolarita']/*[name()='Titolarita']"><!-- applica template x RECORD TIPO 4 -->
		<xsl:with-param name="vId"><xsl:value-of select="$vId"/></xsl:with-param>
  	</xsl:apply-templates>
</xsl:template>

<!-- RECORD TIPO 3: INFORMAZIONI  RELATIVE AI SOGGETTI caso di tipo soggetto G = PersonaGiuridica  -->
<xsl:template match="*[name()='Soggetti']/*[name()='Soggetto']/*[name()='PersonaGiuridica']">
 <xsl:param name="vId">0</xsl:param>
.<xsl:value-of select="$vId"/>|3|<xsl:value-of select="../*[name()='IdSoggettoNota']"/>|<xsl:value-of select="../*[name()='IdSoggettoCatastale']"/>|G|<xsl:value-of select="*[name()='Denominazione']"/>|<xsl:value-of select="*[name()='Sede']"/>|<xsl:value-of select="*[name()='CodiceFiscale']"/>|<xsl:apply-templates select="../*[name()='DatiTitolarita']/*[name()='Titolarita']"><!-- applica template x RECORD TIPO 4 -->
		<xsl:with-param name="vId"><xsl:value-of select="$vId"/></xsl:with-param>
  	</xsl:apply-templates>
</xsl:template>


<!-- RECORD TIPO 3: INFORMAZIONI  RELATIVE AI SOGGETTI caso di tipo soggetto P = PersonaFisica  -->
<xsl:template match="*[name()='Soggetti']/*[name()='Soggetto']/*[name()='PersonaFisica']">
 <xsl:param name="vId">0</xsl:param>
.<xsl:value-of select="$vId"/>|3|<xsl:value-of select="../*[name()='IdSoggettoNota']"/>|<xsl:value-of select="../*[name()='IdSoggettoCatastale']"/>|P|<xsl:value-of select="*[name()='Cognome']"/>|<xsl:value-of select="*[name()='Nome']"/>|<xsl:value-of select="*[name()='Sesso']"/>|<xsl:value-of select="*[name()='DataNascita']"/>|<xsl:value-of select="*[name()='LuogoNascita']"/>|<xsl:value-of select="*[name()='CodiceFiscale']"/>|<xsl:apply-templates select="../*[name()='DatiTitolarita']/*[name()='Titolarita']"><!-- applica template x RECORD TIPO 4 -->
		<xsl:with-param name="vId"><xsl:value-of select="$vId"/></xsl:with-param>
  	</xsl:apply-templates>
</xsl:template>

<!-- RECORD TIPO 4: INFORMAZIONI  RELATIVE ALLE TITOLARITA  -->
<xsl:template match="*[name()='DatiTitolarita']/*[name()='Titolarita']">
 <xsl:param name="vId">0</xsl:param>
.<xsl:value-of select="$vId"/>|4|<xsl:value-of select="../../*[name()='IdSoggettoNota']"/>|<xsl:value-of select="../../*[name()='IdSoggettoCatastale']"/>|<xsl:choose><xsl:when test="count(../../*[name()='PersonaFisica']) > 0">P</xsl:when><xsl:when test="count(../../*[name()='PersonaGiuridica']) > 0">G</xsl:when><xsl:otherwise></xsl:otherwise></xsl:choose>|<xsl:value-of select="translate(substring(@Ref_Immobile,4), '-', '0')"/>|<xsl:value-of select="*[name()='TipologiaImmobile']"/>|<xsl:choose><xsl:when test="count(*[name()='Cessione']) > 0">C</xsl:when><xsl:otherwise></xsl:otherwise></xsl:choose>|<xsl:value-of select="*[name()='Cessione']/*[name()='CodiceDiritto']"/>|<xsl:value-of select="*[name()='Cessione']/*[name()='QuotaNumeratore']"/>|<xsl:value-of select="*[name()='Cessione']/*[name()='QuotaDenominatore']"/>|<xsl:value-of select="*[name()='Cessione']/*[name()='Regime']"/>|<xsl:value-of select="*[name()='Cessione']/*[name()='SoggettoRiferimento']"/>|<xsl:choose><xsl:when test="count(*[name()='Acquisizione']) > 0">F</xsl:when><xsl:otherwise></xsl:otherwise></xsl:choose>|<xsl:value-of select="*[name()='Acquisizione']/*[name()='CodiceDiritto']"/>|<xsl:value-of select="*[name()='Acquisizione']/*[name()='QuotaNumeratore']"/>|<xsl:value-of select="*[name()='Acquisizione']/*[name()='QuotaDenominatore']"/>|<xsl:value-of select="*[name()='Acquisizione']/*[name()='Regime']"/>|<xsl:value-of select="*[name()='Acquisizione']/*[name()='SoggettoRiferimento']"/>|P|<xsl:value-of select="*[name()='PreRegistrazione']/*[name()='IdTitolarita']"/>|<xsl:value-of select="*[name()='PreRegistrazione']/*[name()='CodiceDiritto']"/>|<xsl:value-of select="*[name()='PreRegistrazione']/*[name()='QuotaNumeratore']"/>|<xsl:value-of select="*[name()='PreRegistrazione']/*[name()='QuotaDenominatore']"/>|<xsl:value-of select="*[name()='PreRegistrazione']/*[name()='TitoloNonCodificato']"/>|<xsl:value-of select="*[name()='PreRegistrazione']/*[name()='Regime']"/>|<xsl:value-of select="*[name()='PreRegistrazione']/*[name()='SoggettoRiferimento']"/>||<xsl:value-of select="*[name()='PostRegistrazione']/*[name()='IdTitolarita']"/>|<xsl:value-of select="*[name()='PostRegistrazione']/*[name()='CodiceDiritto']"/>|<xsl:value-of select="*[name()='PostRegistrazione']/*[name()='QuotaNumeratore']"/>|<xsl:value-of select="*[name()='PostRegistrazione']/*[name()='QuotaDenominatore']"/>|<xsl:value-of select="*[name()='PostRegistrazione']/*[name()='TitoloNonCodificato']"/>|<xsl:value-of select="*[name()='PostRegistrazione']/*[name()='Regime']"/>|<xsl:value-of select="*[name()='PostRegistrazione']/*[name()='SoggettoRiferimento']"/>|</xsl:template>


<!-- RECORD TIPO 5: INFORMAZIONI  RELATIVE AGLI INDIRIZZI  DEI SOGGETTI  -->
<xsl:template match="*[name()='Soggetti']/*[name()='Soggetto']/*[name()='Recapito']">
 <xsl:param name="vId">0</xsl:param>
.<xsl:value-of select="$vId"/>|5|<xsl:value-of select="../*[name()='IdSoggettoNota']"/>|<xsl:value-of select="*[name()='TipoIndirizzo']"/>|<xsl:value-of select="*[name()='Comune']"/>|<xsl:value-of select="*[name()='Provincia']"/>|<xsl:value-of select="*[name()='Indirizzo']"/>|<xsl:value-of select="*[name()='CAP']"/>|</xsl:template>

<!-- RECORD TIPO 6: INFORMAZIONI  RELATIVE ALL'IMMOBILE  FABBRICATI  -->
<xsl:template match="*[name()='Immobili']/*[name()='Fabbricato']">
 <xsl:param name="vId">0</xsl:param>
.<xsl:value-of select="$vId"/>|6|<xsl:value-of select="*[name()='TipologiaImmobile']"/>|<xsl:value-of select="*[name()='FlagGraffato']"/>|<xsl:value-of select="translate(substring(@Ref_Immobile,4), '-', '0')"/>|<xsl:value-of select="*[name()='IdCatastaleImmobile']"/>|<xsl:value-of select="*[name()='CodiceEsito']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Natura']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Zona']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Categoria']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Classe']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Vani']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='MC']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='MQ']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Superficie']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='RenditaEuro']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Lotto']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Edificio']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Scala']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Interno1']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Interno2']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Piano1']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Piano2']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Piano3']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Piano4']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Toponimo']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Indirizzo']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Civico1']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Civico2']"/>|<xsl:value-of select="*[name()='UbicazioneNota']/*[name()='Civico3']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Lotto']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Edificio']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Scala']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Interno1']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Interno2']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Piano1']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Piano2']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Piano3']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Piano4']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Toponimo']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Indirizzo']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Civico1']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Civico2']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Civico3']"/>|<xsl:value-of select="*[name()='UbicazioneCatasto']/*[name()='Annotazioni']"/>|<xsl:apply-templates select="*[name()='Identificativi']"><!-- applica template x RECORD TIPO 7 -->
		<xsl:with-param name="vId"><xsl:value-of select="$vId"/></xsl:with-param>
  	</xsl:apply-templates>
</xsl:template>

<!-- RECORD TIPO 7: INFORMAZIONI  RELATIVE ALL'IMMOBILE  FABBRICATI  -->
<xsl:template match="*[name()='Identificativi']">
 <xsl:param name="vId">0</xsl:param>
.<xsl:value-of select="$vId"/>|7|<xsl:value-of select="translate(substring(../@Ref_Immobile,4), '-', '0')"/>|<xsl:value-of select="../*[name()='IdCatastaleImmobile']"/>|<xsl:value-of select="*[name()='IdentificativoDefinitivo']/*[name()='SezioneCensuaria']"/>|<xsl:value-of select="*[name()='IdentificativoDefinitivo']/*[name()='SezioneUrbana']"/>|<xsl:value-of select="*[name()='IdentificativoDefinitivo']/*[name()='Foglio']"/>|<xsl:value-of select="*[name()='IdentificativoDefinitivo']/*[name()='Numero']"/>|<xsl:value-of select="*[name()='IdentificativoDefinitivo']/*[name()='Denominatore']"/>|<xsl:value-of select="*[name()='IdentificativoDefinitivo']/*[name()='Subalterno']"/>|<xsl:value-of select="*[name()='IdentificativoDefinitivo']/*[name()='Edificiali']"/>|<xsl:value-of select="*[name()='IdentificativoProvvisorio']/*[name()='TipoDenuncia']"/>|<xsl:value-of select="*[name()='IdentificativoProvvisorio']/*[name()='NumeroProtocollo']"/>|<xsl:value-of select="*[name()='IdentificativoProvvisorio']/*[name()='Anno']"/>|</xsl:template>

<!-- RECORD TIPO 8: INFORMAZIONI  RELATIVE ALL'IMMOBILE TERRENI  -->
<xsl:template match="*[name()='Terreno']">
 <xsl:param name="vId">0</xsl:param>
.<xsl:value-of select="$vId"/>|8|<xsl:value-of select="*[name()='TipologiaImmobile']"/>|<xsl:value-of select="translate(substring(@Ref_Immobile,4), '-', '0')"/>|<xsl:value-of select="*[name()='IdCatastaleImmobile']"/>|<xsl:value-of select="*[name()='CodiceEsito']"/>|<xsl:value-of select="*[name()='Identificativo']/*[name()='SezioneCensuaria']"/>|<xsl:value-of select="*[name()='Identificativo']/*[name()='Foglio']"/>|<xsl:value-of select="*[name()='Identificativo']/*[name()='Numero']"/>|<xsl:value-of select="*[name()='Identificativo']/*[name()='Denominatore']"/>|<xsl:value-of select="*[name()='Identificativo']/*[name()='Subalterno']"/>|<xsl:value-of select="*[name()='Identificativo']/*[name()='Edificialita']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Natura']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Qualita']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Classe']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Ettari']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Are']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='Centiare']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='FlagReddito']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='DominicaleEuro']"/>|<xsl:value-of select="*[name()='Classamento']/*[name()='AgrarioEuro']"/>|<xsl:value-of select="*[name()='Partita']"/>|</xsl:template>


</xsl:stylesheet>
