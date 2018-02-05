package com.soapexample.client;

import com.soapexample.generated.GetFileNamesRequest;
import com.soapexample.generated.GetFileNamesResponse;
import com.soapexample.generated.GetFileRequest;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;
import static org.springframework.ws.test.client.ResponseCreators.withServerOrReceiverFault;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoFileClientTest {


    @Autowired
    private VideoFileClient client;
    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceServer mockServer;
    private Resource xsdSchema = new ClassPathResource("example.xsd");
    private Jaxb2Marshaller marshaller;

    @Before
    public void createServer() {
        mockServer = MockWebServiceServer.createServer(client);
        marshaller = (Jaxb2Marshaller) applicationContext.getBean("marshaller");
    }

    private List<String> createListOfFilesNames() {
        List<String> listOfFilesNames = new ArrayList<>(Arrays.asList("video1.mp4", "video2.mp4", "video3.mp4"));
        return listOfFilesNames;
    }

    @Test
    public void getExistFilesNames() {
        GetFileNamesRequest request = new GetFileNamesRequest();
        GetFileNamesResponse response = new GetFileNamesResponse();
        response.getFileNamesList().addAll(createListOfFilesNames());
        SourceBuilder<GetFileNamesRequest> requestSourceBuilder = new SourceBuilder<>(marshaller);
        SourceBuilder<GetFileNamesResponse> responseSourceBuilder = new SourceBuilder<>(marshaller);
        mockServer.expect(payload(requestSourceBuilder.buildSource(request)))
                .andRespond(withPayload(responseSourceBuilder.buildSource(response)));
        int listSize = client.getExistFilesNames(true).size();
        assertEquals(3, listSize);
        mockServer.verify();
    }

    @Test
    public void getFileByUnExistingNameTest() {
        GetFileRequest request = new GetFileRequest();
        SourceBuilder<GetFileRequest> sourceBuilder = new SourceBuilder<>(marshaller);
        mockServer.expect(payload(sourceBuilder.buildSource(request)))
                .andRespond(withServerOrReceiverFault(NullPointerException.class.getName(), Locale.ENGLISH));
    }
}
