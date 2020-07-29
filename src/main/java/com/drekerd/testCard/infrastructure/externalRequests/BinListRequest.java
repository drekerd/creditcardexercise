package com.drekerd.testCard.infrastructure.externalRequests;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
public class BinListRequest {

    public HttpResponse doGetRequest(final String URL){
        log.info( "BinListRequest.doGetRequest, Start Request to " + URL);
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        final HttpGet getRequest = new HttpGet(URL);
        try {
           return httpClient.execute(getRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.error("BinListRequest.doGetRequestError getting connecting to External Application ", URL);
        return null;
    }

}
