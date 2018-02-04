package com.soapexample.client;

import com.soapexample.generated.StoreDocumentRequest;
import com.soapexample.generated.StoreDocumentResponse;
import com.soapexample.util.SourceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.client.MockWebServiceServer;

import javax.activation.DataHandler;
import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.Locale;

import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;
import static org.springframework.ws.test.client.ResponseCreators.withServerOrReceiverFault;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentsClientTest {

    @Autowired
    private DocumentsClient client;
    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceServer mockWebServiceServer;
    private Resource xsdSchema = new ClassPathResource("example.xsd");
    private Jaxb2Marshaller marshaller;

    @Before
    public void createServer() {
        mockWebServiceServer = MockWebServiceServer.createServer(client);
        marshaller = (Jaxb2Marshaller) applicationContext.getBean("marshaller");
    }

    @Test
    public void storeDocumentTest() {
        StoreDocumentRequest request = new StoreDocumentRequest();
        StoreDocumentResponse response = new StoreDocumentResponse();
        response.setCount(new BigInteger(String.valueOf(1)));
        request.setWord("Test");
        setFile(request);
        SourceBuilder<StoreDocumentRequest> requestSourceBuilder = new SourceBuilder<>(marshaller);
        SourceBuilder<StoreDocumentResponse> responseSourceBuilder = new SourceBuilder<>(marshaller);
        mockWebServiceServer.expect(payload(requestSourceBuilder.buildSource(request)))
                .andRespond(withPayload(responseSourceBuilder.buildSource(response)));
    }

    @Test
    public void storeDocumentThrowSoapFaultTest() {
        StoreDocumentRequest requestObject = new StoreDocumentRequest();
        requestObject.setWord("Test");
        SourceBuilder<StoreDocumentRequest> requestSourceBuilder = new SourceBuilder<>(marshaller);
        mockWebServiceServer.expect(payload(requestSourceBuilder.buildSource(requestObject)))
                .andRespond(withServerOrReceiverFault(NullPointerException.class.getName(), Locale.ENGLISH));
    }

    private void setFile(StoreDocumentRequest request) {
        File file = new File("src/test/resources/file.txt");
        try {
            request.setContent(new DataHandler(file.toURI().toURL()));
        } catch (MalformedURLException e) {
            e.getMessage();
        }
    }
}
