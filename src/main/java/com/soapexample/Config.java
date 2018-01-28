package com.soapexample;

import com.soapexample.consumer.FirstEndpointClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Created by Ivan on 28.01.2018.
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
        return marshaller;
    }

    @Bean
    public FirstEndpointClient firstEndpointClient(Jaxb2Marshaller marshaller) {
        FirstEndpointClient client = new FirstEndpointClient();

        client.setDefaultUri("https://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }

    @Bean
    public XsdSchema objectsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("example.xsd"));
    }
}
