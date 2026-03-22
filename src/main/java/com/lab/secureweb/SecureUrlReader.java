package com.lab.secureweb;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.stream.Collectors;

public final class SecureUrlReader {

    private static final String TRUST_STORE_PATH_ENV = "TRUST_STORE_PATH";
    private static final String TRUST_STORE_PASSWORD_ENV = "TRUST_STORE_PASSWORD";
    private static final String TRUST_STORE_TYPE_ENV = "TRUST_STORE_TYPE";
    private static final String DEFAULT_STORE_TYPE = "PKCS12";

    private SecureUrlReader() {
    }

    public static void configureDefaultSslContext() throws Exception {
        String path = System.getenv(TRUST_STORE_PATH_ENV);
        String password = System.getenv(TRUST_STORE_PASSWORD_ENV);

        if (path == null || path.isBlank() || password == null) {
            return;
        }

        String storeType = System.getenv(TRUST_STORE_TYPE_ENV);
        if (storeType == null || storeType.isBlank()) {
            storeType = DEFAULT_STORE_TYPE;
        }

        KeyStore trustStore = KeyStore.getInstance(storeType);
        char[] pass = password.isEmpty() ? null : password.toCharArray();

        if (path.startsWith("classpath:")) {
            String resource = path.substring("classpath:".length());
            try (InputStream is = SecureUrlReader.class.getClassLoader().getResourceAsStream(resource)) {
                if (is == null) {
                    throw new FileNotFoundException("Resource not found: " + resource);
                }
                trustStore.load(is, pass);
            }
        } else {
            String filePath = path.startsWith("file:") ? path.substring(5) : path;
            try (FileInputStream fis = new FileInputStream(filePath)) {
                trustStore.load(fis, pass);
            }
        }

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
        SSLContext.setDefault(sslContext);
    }

    public static String readUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        try (InputStream is = url.openStream();
             Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(reader)) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }

    public static void main(String[] args) {
        try {
            configureDefaultSslContext();
            String port = System.getenv("PORT");
            int p = (port != null && !port.isBlank()) ? Integer.parseInt(port.trim()) : 5000;
            System.out.println("Reading https://localhost:" + p + "/hello");
            System.out.println(readUrl("https://localhost:" + p + "/hello"));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
