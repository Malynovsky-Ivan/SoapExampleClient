package com.soapexample.client;

import com.soapexample.generated.GetFileNamesRequest;
import com.soapexample.generated.GetFileNamesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.mime.Attachment;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.List;

import static com.soapexample.ProjectContants.*;

/**
 * Responsible for sending requests to the server to receive list of available files
 * and selected video file
 *
 * @author Igor Faryna
 */
@Service
public class VideoFileClient extends WebServiceGatewaySupport {

    private final Logger LOGGER = LoggerFactory.getLogger(VideoFileClient.class);
	private List<String> fileNames;

	@Autowired
	private Transformer transformer;

    public VideoFileClient() {
        System.setProperty(TRUST_STORE_KEY, TRUST_STORE_VALUE);
    }

	/**
	 *Sends a request to the server to get a list of available files names
	 *
	 * @param forceRequest boolean value representing
	 * @return list of file names that are available on the server
	 */
	public List<String> getExistFilesNames(boolean forceRequest) {
    	if (fileNames != null && !fileNames.isEmpty() && !forceRequest) {
    		return fileNames;
		}
        LOGGER.info("Request to get list of files names");
        GetFileNamesRequest request = new GetFileNamesRequest();
        GetFileNamesResponse response = (GetFileNamesResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        fileNames = response.getFileNamesList();
        LOGGER.info("Received list of files names: {}", fileNames);
        return fileNames;
    }


	/**
	 * Sends to the server name of the file user wants to receive.
	 * And saves the received file in {@link com.soapexample.ProjectContants#RESOURCES_PATH}
	 *
	 * @param fileName String value representing the name of file that user want to receive
	 */
	public void getVideoFile(String fileName) {
        LOGGER.info("Request to get file: {}", fileName);

        try {
			Attachment attachment = getWebServiceTemplate().sendAndReceive(
					webServiceMessage -> transformer
							.transform(new StreamSource(new StringReader(String.format(VIDEO_FILE_REQUEST_BODY, fileName))),
									webServiceMessage.getPayloadResult()),
					message -> {
						SoapMessage soapMessage = (SoapMessage) message;
						SoapFault fault = soapMessage.getSoapBody().getFault();
						if (fault != null) {
							throw new IOException(fault.getFaultStringOrReason());
						}

						return soapMessage.getAttachment("file");
					});

			InputStream inputStream = attachment.getDataHandler().getInputStream();
			byte[] buffer = new byte[inputStream.available()];
			LOGGER.info("{} bytes were read from received file.", inputStream.read(buffer));

			File targetFile = new File(PATH_TO_FILES + fileName);
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);

			inputStream.close();
			outStream.close();
		} catch (IOException e) {
			LOGGER.warn(e.getMessage());
		}
    }
}

