package com.soapexample.client;

import com.soapexample.generated.StoreDocumentRequest;
import com.soapexample.generated.StoreDocumentResponse;
import com.soapexample.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.activation.DataHandler;
import java.net.MalformedURLException;

import static com.soapexample.ProjectContants.TRUST_STORE_KEY;
import static com.soapexample.ProjectContants.TRUST_STORE_VALUE;

public class DocumentsClient extends WebServiceGatewaySupport {
    private final Logger LOGGER = LoggerFactory.getLogger(DocumentsClient.class);

    @Autowired
    FileService fileService;

    public DocumentsClient() {
        System.setProperty(TRUST_STORE_KEY, TRUST_STORE_VALUE);
    }

    public int storeDocument(String searchWord, MultipartFile file) {
        StoreDocumentRequest request = new StoreDocumentRequest();
        try {
            request.setContent(new DataHandler(fileService.multipartFileToFile(file).toURI().toURL()));
        } catch (MalformedURLException e) {
            LOGGER.error("Error occurred when tried to add file to request. {}", e.getMessage());
            return 0;
        }
        request.setWord(searchWord);
        StoreDocumentResponse response = (StoreDocumentResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response.getCount().intValue();
    }

}
