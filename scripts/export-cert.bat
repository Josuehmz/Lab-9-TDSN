@echo off
REM Exportar el certificado a un archivo (para importarlo en un TrustStore).
REM Ejecutar desde el directorio donde está ecikeystore.p12.

keytool -export -keystore ./ecikeystore.p12 -alias ecikeypair -file ecicert.cer
echo Certificado exportado a ecicert.cer
