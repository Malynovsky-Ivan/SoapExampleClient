package com.soapexample.consumer;

import com.soapexample.generated.GetFileNamesRequest;
import com.soapexample.generated.GetFileNamesResponse;
import com.soapexample.generated.GetFileRequest;
import com.soapexample.generated.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.mime.Attachment;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.xml.transform.TransformerHelper;

import javax.activation.DataHandler;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Iterator;
import java.util.List;

@Service
public class VideoFileClient extends WebServiceGatewaySupport {

    private final Logger LGR = LoggerFactory.getLogger(VideoFileClient.class);

    public VideoFileClient() {
        System.setProperty("javax.net.ssl.trustStore", "src/main/resources/trust-store.jks");
    }

    public List<String> getExistFilesNames() {
        LGR.info("Request to get list of files names");
        GetFileNamesRequest request = new GetFileNamesRequest();
        GetFileNamesResponse response = (GetFileNamesResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        LGR.info("Received list of files names: {}", response.getFileNamesList());
        return response.getFileNamesList();
    }


    public void getVideoFile(String fileName)
            throws IOException {
        LGR.info("Request to get file: {}", fileName);
        GetFileRequest request = new GetFileRequest();
        request.setFileName(fileName);
        TransformerHelper transformerHelper = new TransformerHelper();
        Transformer transformer = null;
        try {
            transformer = transformerHelper.createTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        //GetFileResponse response = (GetFileResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        Transformer finalTransformer = transformer;
        Attachment iterator = (Attachment) getWebServiceTemplate().sendAndReceive(webServiceMessage -> {
            System.out.println("1");
            finalTransformer.transform(new StreamSource(new StringReader(
                    "        <gs:getFileRequest xmlns:gs=\"http://generated.soapexample.com\">\n" +
                    "            <gs:fileName>file1.txt</gs:fileName>\n" +
                    "        </gs:getFileRequest>\n")), webServiceMessage.getPayloadResult());
            System.out.println("2");
        }, (message -> {
            System.out.println("3");
            SoapMessage message1 = (SoapMessage) message;
            return message1.getAttachment("file");
        }));
        System.out.println("!");

        BufferedReader reader = new BufferedReader(new InputStreamReader(iterator.getDataHandler().getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
          /*  InputStream initialStream = response.getFile().getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);

            File targetFile = new File("src/main/resources/" + request.getFileName());
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
        }*/
    }
}

