package com.soapexample.consumer;

import com.soapexample.generated.GetObjectRequest;
import com.soapexample.generated.GetObjectResponse;
import com.soapexample.generated.GetRandomIntRequest;
import com.soapexample.generated.GetRandomIntResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by Ivan on 28.01.2018.
 */
public class FirstEndpointClient extends WebServiceGatewaySupport {
    public void doStuff() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        GetObjectRequest request = new GetObjectRequest();

        System.out.print("Please, input your name: ");
        try {
            request.setUserName(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.setProperty ("javax.net.ssl.trustStore", "classpath:trust-store.jks");
        System.setProperty ("javax.net.ssl.trustStore", "D:\\git\\SoapExample\\src\\main\\resources\\trust-store.jks");

        GetObjectResponse response = (GetObjectResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://localhost:8080/ws", request,
                        new SoapActionCallback("https://localhost:8080/ws"));
        System.out.println(response.getObject().getRandomString() + " - " + response.getObject().getRandomInt());
        System.out.println("------------------------------------------------------------------");

        GetRandomIntRequest intRequest = new GetRandomIntRequest();
        System.out.print("Please, input max number: ");
        try {
            int number = Integer.parseInt(bufferedReader.readLine());
            intRequest.setRandomIntMax(new BigInteger(String.valueOf(number)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid number format! Will be used default value 100");
            intRequest.setRandomIntMax(new BigInteger(String.valueOf(100)));
        }

        GetRandomIntResponse intResponse = (GetRandomIntResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://localhost:8080/ws", intRequest,
                        new SoapActionCallback("https://localhost:8080/ws"));
        System.out.println("We have got next number: " + intResponse.getRandomInt());
    }
}
