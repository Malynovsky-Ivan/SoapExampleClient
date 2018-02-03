package com.soapexample.consumer;

import com.soapexample.generated.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.io.*;
import java.math.BigInteger;

import static com.soapexample.ProjectContants.DEFAULT_URI;
import static com.soapexample.ProjectContants.TRUST_STORE_KEY;
import static com.soapexample.ProjectContants.TRUST_STORE_VALUE;

/**
 * Created by Ivan on 28.01.2018.
 *
 * Legacy code. Probably will be removed as soon as possible.
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

        System.setProperty (TRUST_STORE_KEY, TRUST_STORE_VALUE);

        GetObjectResponse response = (GetObjectResponse) getWebServiceTemplate()
                .marshalSendAndReceive(DEFAULT_URI, request,
                        new SoapActionCallback(DEFAULT_URI));
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
                .marshalSendAndReceive(DEFAULT_URI, intRequest,
                        new SoapActionCallback(DEFAULT_URI));
        System.out.println("We have got next number: " + intResponse.getRandomInt());
        System.out.println("------------------------------------------------------------------");
        DownloadMessageRequest downloadMessageRequest = new DownloadMessageRequest();
        downloadMessageRequest.setRandomInt(new BigInteger("1"));

        DownloadMessageResponse response3 = (DownloadMessageResponse) getWebServiceTemplate()
                .marshalSendAndReceive(DEFAULT_URI, downloadMessageRequest,
                        new SoapActionCallback(DEFAULT_URI));

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(response3.getDownloadResponse()
                    .getPayLoad().getMessagePayLoad().getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("gottenFile.csv")));

            String line = reader.readLine();
            while(line != null) {
                System.out.println(line);
                writer.write(line);
                writer.newLine();

                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
