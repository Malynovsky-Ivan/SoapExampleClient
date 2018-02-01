package com.soapexample.consumer;

import com.soapexample.generated.GetFileNamesRequest;
import com.soapexample.generated.GetFileNamesResponse;
import com.soapexample.generated.GetFileRequest;
import com.soapexample.generated.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.activation.DataHandler;
import java.io.*;
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


    public void getVideoFile(String fileName) throws IOException {
        LGR.info("Request to get file: {}", fileName);
        GetFileRequest request = new GetFileRequest();
        request.setFileName(fileName);
        GetFileResponse response = (GetFileResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        System.out.println(response);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getFile().getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        }
          /*  InputStream initialStream = response.getFile().getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);

            File targetFile = new File("src/main/resources/" + request.getFileName());
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
        }*/
    }

