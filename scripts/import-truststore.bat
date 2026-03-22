@echo off
REM Importar el certificado en un TrustStore (para clientes que confíen en este servidor).
REM Ejecutar desde el directorio donde están ecicert.cer y ecikeystore.p12.

keytool -import -file ./ecicert.cer -alias firstCA -keystore myTrustStore
echo TrustStore creado/actualizado: myTrustStore
echo Defina TRUST_STORE_PATH=file:ruta\myTrustStore y TRUST_STORE_PASSWORD en el cliente.
