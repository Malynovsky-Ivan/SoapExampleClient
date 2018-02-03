package com.soapexample;

/**
 * Created by Ivan.Malynovskyi on 02.02.2018  11:02.
 */
public interface ProjectContants {
	String VIDEO_FILE_REQUEST_BODY = "<gs:getFileRequest xmlns:gs=\"http://generated.soapexample.com\">\n" +
			"<gs:fileName>%s</gs:fileName></gs:getFileRequest>\n";

	String TRUST_STORE_KEY = "javax.net.ssl.trustStore";
	String TRUST_STORE_VALUE = "src/main/resources/trust-store.jks";

	String DEFAULT_URI = "https://localhost:8080/ws";

	String RESOURCES_PATH = "src/main/resources/";

	String XSD_SCHEMA = "example.xsd";
}
