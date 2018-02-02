package com.soapexample.consumer;

import com.soapexample.generated.StoreDocumentRequest;
import com.soapexample.generated.StoreDocumentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.xml.transform.StringSource;

import javax.activation.DataHandler;
import javax.xml.transform.Transformer;
import java.io.File;
import java.net.MalformedURLException;

public class DocumentsClient extends WebServiceGatewaySupport {

	@Autowired
	private Transformer transformer;

    public DocumentsClient() {
        System.setProperty("javax.net.ssl.trustStore", "src/main/resources/trust-store.jks");
    }

    public void storeDocument(String searchWord, File filePath) {
		//AxiomSoapMessageFactory messageFactory = new AxiomSoapMessageFactory();

        System.out.println(filePath);
		StoreDocumentRequest request = new StoreDocumentRequest();
        try {
            request.setContent(new DataHandler(filePath.toURI().toURL()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        request.setWord(searchWord);
        getWebServiceTemplate()
                /*.sendAndReceive(webServiceMessage -> {
                	String responseText = "<gs:storeDocumentRequest xmlns:gs=\"http://generated.soapexample.com\">\n" +
								"<gs:word>%s</gs:word>" +
								"<gs:content xmlns:gs=\"http://generated.soapexample.com\"/>" +
							"</gs:storeDocumentRequest>";
					SaajSoapMessage saajSoapMessage = (SaajSoapMessage) webServiceMessage;
					saajSoapMessage.getEnvelope().getBody();

                    /*AxiomSoapMessage requestMessage = (AxiomSoapMessage) webServiceMessage;
					SOAPMessage axiomMessage = requestMessage.getAxiomMessage();
					SOAPFactory factory = (SOAPFactory) axiomMessage.getOMFactory();
					SOAPBody body = axiomMessage.getSOAPEnvelope().getBody();

					OMNamespace gs = factory.createOMNamespace("http://generated.soapexample.com", "tns");

					OMElement r = factory.createOMElement("storeDocumentRequest", gs);
					body.addChild(r);
					OMElement word = factory.createOMElement("word", gs);
					r.addChild(word);
					word.setText(searchWord);
					OMElement file = factory.createOMElement("content", gs);
					r.addChild(file);
					OMText text = factory.createOMText(request.getContent(), true);
					file.addChild(text);

					OMOutputFormat outputFormat = new OMOutputFormat();
					outputFormat.setSOAP11(true);
					outputFormat.setDoOptimize(true);
					requestMessage.setOutputFormat(outputFormat);

                }, webServiceMessage -> {
					System.out.println("!!!!");

					return null;
				});*/
                .marshalSendAndReceive(request, webServiceMessage -> {
					SaajSoapMessage saajMessage = (SaajSoapMessage) webServiceMessage;

					saajMessage.addAttachment("file", new DataHandler(filePath.toURI().toURL()));
				});
        System.out.println();
        System.out.println("There are " + 0 + " such words in the text");
    }
}
