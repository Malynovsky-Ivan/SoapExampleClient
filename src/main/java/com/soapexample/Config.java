package com.soapexample;

import com.soapexample.consumer.DocumentsClient;
import com.soapexample.consumer.FirstEndpointClient;
import com.soapexample.consumer.VideoFileClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.xml.transform.TransformerHelper;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.xml.soap.SOAPException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;

import static com.soapexample.ProjectContants.DEFAULT_URI;
import static com.soapexample.ProjectContants.XSD_SCHEMA;

/**
 * Created by Ivan on 28.01.2018.
 *
 * Config class. Here we can find declaration of our beans
 */
@Configuration
public class Config {

    @Bean
    public Jaxb2Marshaller marshaller(XsdSchema objectsSchema) {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setPackagesToScan("com.soapexample.generated");
        //marshaller.setSchema(new ClassPathResource("example.xsd"));
        marshaller.setMtomEnabled(true);
        return marshaller;
    }

    @Bean
    public FirstEndpointClient firstEndpointClient(Jaxb2Marshaller marshaller) {
        FirstEndpointClient client = new FirstEndpointClient();

        client.setDefaultUri(DEFAULT_URI);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }

    @Bean
    public DocumentsClient documentsClient(Jaxb2Marshaller marshaller) {
        DocumentsClient documentsClient = new DocumentsClient();
        documentsClient.setDefaultUri(DEFAULT_URI);
        documentsClient.setMarshaller(marshaller);
        documentsClient.setUnmarshaller(marshaller);

        return documentsClient;
    }

    @Bean
    public VideoFileClient videoFileClient(Jaxb2Marshaller marshaller) {
        VideoFileClient videoFileClient = new VideoFileClient();
        videoFileClient.setDefaultUri(DEFAULT_URI);
        videoFileClient.setMarshaller(marshaller);
        videoFileClient.setUnmarshaller(marshaller);

        return videoFileClient;
    }

    @Bean
    public SaajSoapMessageFactory saajSoapMessageFactory() {
        SaajSoapMessageFactory factory = null;
        try {
            factory = new SaajSoapMessageFactory(javax.xml.soap.MessageFactory.newInstance());
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return factory;
    }

    @Bean
	public Transformer transformer() {
		TransformerHelper transformerHelper = new TransformerHelper();
		Transformer transformer = null;
		try {
			transformer = transformerHelper.createTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}

		return transformer;
	}

    @Bean
    public XsdSchema objectsSchema() {
        return new SimpleXsdSchema(new ClassPathResource(XSD_SCHEMA));
    }

}