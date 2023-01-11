ECHO OFF

CD..
CD "standalone\deployments"
DEL *.dodeploy /Q
DEL *.isdeploying /Q
DEL *.deployed /Q
DEL *.failed /Q
DEL *.undeployed /Q
DEL *.pending /Q
DEL *.isundeploying /Q

type NUL > CT_Service_Client.ear.dodeploy



ECHO Pulizia Cartella Deploy Standalone Effettuata!