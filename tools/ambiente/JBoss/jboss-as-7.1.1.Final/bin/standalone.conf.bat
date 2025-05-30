rem ### -*- batch file -*- ######################################################
rem #                                                                          ##
rem #  JBoss Bootstrap Script Configuration                                    ##
rem #                                                                          ##
rem #############################################################################

rem # $Id: run.conf.bat 88820 2009-05-13 15:25:44Z dimitris@jboss.org $

rem #
rem # This batch file is executed by run.bat to initialize the environment
rem # variables that run.bat uses. It is recommended to use this file to
rem # configure these variables, rather than modifying run.bat itself.
rem #

rem Uncomment the following line to disable manipulation of JAVA_OPTS (JVM parameters)
set PRESERVE_JAVA_OPTS=true

if not "x%JAVA_OPTS%" == "x" (
  echo "JAVA_OPTS already set in environment; overriding default settings with values: %JAVA_OPTS%"
  goto JAVA_OPTS_SET
)

rem #
rem # Specify the JBoss Profiler configuration file to load.
rem #
rem # Default is to not load a JBoss Profiler configuration file.
rem #
rem set "PROFILER=%JBOSS_HOME%\bin\jboss-profiler.properties"

rem #
rem # Specify the location of the Java home directory (it is recommended that
rem # this always be set). If set, then "%JAVA_HOME%\bin\java" will be used as
rem # the Java VM executable; otherwise, "%JAVA%" will be used (see below).
rem #
rem set "JAVA_HOME=C:\opt\jdk1.6.0_23"
echo "JAVA_HOME: %JAVA_HOME%"
set JAVA_HOME=C:\Programmi\Java\jdk1.7.0_60


rem #
rem # Specify the exact Java VM executable to use - only used if JAVA_HOME is
rem # not set. Default is "java".
rem #
rem set "JAVA=C:\opt\jdk1.6.0_23\bin\java"

rem #
rem # Specify options to pass to the Java VM. Note, there are some additional
rem # options that are always passed by run.bat.
rem #

rem # JVM memory allocation pool parameters - modify as appropriate.
set "JAVA_OPTS=-Xms64M -Xmx512M -XX:MaxPermSize=256M"

rem # Reduce the RMI GCs to once per hour for Sun JVMs.
set "JAVA_OPTS=%JAVA_OPTS% -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -Djava.net.preferIPv4Stack=true"

rem # Warn when resolving remote XML DTDs or schemas.
set "JAVA_OPTS=%JAVA_OPTS% -Dorg.jboss.resolver.warning=true"

rem # Make Byteman classes visible in all module loaders
rem # This is necessary to inject Byteman rules into AS7 deployments
set "JAVA_OPTS=%JAVA_OPTS% -Djboss.modules.system.pkgs=org.jboss.byteman"

rem # Set the default configuration file to use if -c or --server-config are not used
set "JAVA_OPTS=%JAVA_OPTS% -Djboss.server.default.config=standalone.xml"

rem # Sample JPDA settings for remote socket debugging
rem set "JAVA_OPTS=%JAVA_OPTS% -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n"

rem # Sample JPDA settings for shared memory debugging
rem set "JAVA_OPTS=%JAVA_OPTS% -Xrunjdwp:transport=dt_shmem,address=jboss,server=y,suspend=n"

rem # Use JBoss Modules lockless mode
rem set "JAVA_OPTS=%JAVA_OPTS% -Djboss.modules.lockless=true"

:JAVA_OPTS_SET
set JAVA_HOME=C:\Programmi\Java\jdk1.7.0_60

REM *************************************************************************************
REM ************* DECOMMENTARE SOLO UNA DELLE DUE OPZIONI FRA 64 E 32 BIT ****************
REM ************* DI DEFAULT E' DECOMMENTATA LA 64 BIT ****************** ****************
REM *************************************************************************************
REM # IMPOSTAZIONI PER SERVER CON 6 GB DI RAM (JVM 64 BIT!!)
set JAVA_OPTS=%JAVA_OPTS% -server -Dhibernate.jdbc.batch_size=3 -Doracle.net.disableOob=true -Dorg.terracotta.quartz.skipUpdateCheck=true  -Xms1024m -Xmx2048m -XX:MaxNewSize=448m -XX:NewSize=448m -XX:SurvivorRatio=6 -XX:MaxPermSize=1024m -XX:PermSize=1024m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSPermGenSweepingEnabled -XX:+CMSClassUnloadingEnabled -XX:-UseGCOverheadLimit 
REM # IMPOSTAZIONI PER SERVER 32 BIT
rem IMPOSTAZIONE MINIMALE
REM set JAVA_OPTS=%JAVA_OPTS% -server -Dhibernate.jdbc.batch_size=3 -Doracle.net.disableOob=true  -XX:MaxPermSize=256m  -XX:+UseParNewGC 
REM IMPOSTAZIONE AVANZATA
REM set JAVA_OPTS=%JAVA_OPTS% -Dhibernate.jdbc.batch_size=3 -Doracle.net.disableOob=true  -Dorg.terracotta.quartz.skipUpdateCheck=true -Xss1024k -Xms256m -Xmx768m -XX:MaxPermSize=256m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSPermGenSweepingEnabled -XX:+CMSClassUnloadingEnabled -XX:-UseGCOverheadLimit 
