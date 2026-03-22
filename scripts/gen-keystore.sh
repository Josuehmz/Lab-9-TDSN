#!/bin/sh
# Generar par de llaves y certificado en PKCS12 (apto para certificaciones CA).
# Nota: use "localhost" como nombre del certificado cuando lo pida keytool.
# Salida: ecikeystore.p12 en el directorio actual.

keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore ecikeystore.p12 -validity 3650

echo ""
echo "Copie ecikeystore.p12 a src/main/resources/keystore/ para desarrollo local."
echo "En EC2, copie a /opt/app/keystore/ y defina KEY_STORE_PATH=file:/opt/app/keystore/ecikeystore.p12"
