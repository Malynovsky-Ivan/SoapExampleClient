package com.soapexample.consumer;

import com.soapexample.generated.Document;
import com.soapexample.generated.StoreDocumentRequest;
import com.soapexample.generated.StoreDocumentResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Random;

public class DocumentsClient extends WebServiceGatewaySupport {

    public DocumentsClient() {
        System.setProperty("javax.net.ssl.trustStore", "src/main/resources/trust-store.jks");
    }

    public void storeDocument(String searchWord, String filePath) {
        System.out.println(filePath);
        Document document = new Document();
        try {
            document.setContent(new DataHandler(new File("D:/1.txt").toURI().toURL()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        document.setName(searchWord);
        StoreDocumentRequest request = new StoreDocumentRequest();
        request.setDocument(document);
        StoreDocumentResponse response = (StoreDocumentResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        System.out.println();
        System.out.println("There are " + response.getCount() + " such words in the text");
    }
}
