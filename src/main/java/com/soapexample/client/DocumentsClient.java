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
import java.io.File;
import java.net.MalformedURLException;

import static com.soapexample.ProjectContants.TRUST_STORE_KEY;
import static com.soapexample.ProjectContants.TRUST_STORE_VALUE;

/**
 *Responsible for sending all requests related to counting word coincidences in the selected document
 *
 *@author Igor Faryna
 */
public class DocumentsClient extends WebServiceGatewaySupport {
    private final Logger LOGGER = LoggerFactory.getLogger(DocumentsClient.class);

    @Autowired
    FileService fileService;

    public DocumentsClient() {
        System.setProperty(TRUST_STORE_KEY, TRUST_STORE_VALUE);
    }

    /**
     * <p>Sends request to server to get number of word coincidences in the selected document</p>
     *
     * @see FileService#multipartFileToFile(MultipartFile)
     *
     * @param searchWord a String value that user typed in jsp
     * @param file  MultipartFile document that user has selected
     * @return number of occurrence of the searchWord in the file
     */
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
