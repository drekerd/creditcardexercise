package com.drekerd.testCard.infrastructure;


import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class BinListRequest {

    private static HttpClient httpClient;
    private static HttpResponse httpResponse;

    @BeforeAll
    public static void init() {
        httpClient = Mockito.mock(HttpClient.class);
        httpResponse = Mockito.mock(HttpResponse.class);
    }

    @Mock
    BinListRequest binListRequest = new BinListRequest();

    @Test
    public void testResponse() throws IOException {

    }
}
