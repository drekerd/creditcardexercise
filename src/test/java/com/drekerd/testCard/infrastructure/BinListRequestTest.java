package com.drekerd.testCard.infrastructure;


import com.drekerd.testCard.infrastructure.bank.Bank;
import com.drekerd.testCard.infrastructure.cards.CardResponse;
import com.drekerd.testCard.infrastructure.core.BinList;
import com.drekerd.testCard.infrastructure.country.Country;

import com.drekerd.testCard.infrastructure.dto.Card;
import com.drekerd.testCard.infrastructure.externalRequests.BinListRequest;
import com.google.gson.Gson;
import org.apache.http.*;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BinListRequestTest {

    @InjectMocks
    BinList binList;

    @Mock
    BinListRequest binListRequest;

    public CardResponse createNewCard() {
        Country country = new Country();
        country.setNumeric(840);
        country.setAlpha2("US");
        country.setName("United States of America");
        country.setEmoji("");
        country.setCurrency("USD");
        country.setLatitude(38);
        country.setLongitude(-97);

        Bank bank = new Bank();
        bank.setName("PNC BANK, N.A.");
        bank.setUrl("www.pnc.com");
        bank.setPhone("888-762-2265");

        CardResponse cardResponse = new CardResponse();
        cardResponse.setScheme("cenas");
        cardResponse.setType("debit");
        cardResponse.setCountry(country);
        cardResponse.setBank(bank);

        return cardResponse;
    }

    @Test
    public void testResponse() throws IOException {
        Gson gson = new Gson();

        CardResponse newCard = createNewCard();
        String jsonInString = gson.toJson(newCard);
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.getEntity()).thenReturn(EntityBuilder.create().setText(jsonInString).build());

        Card card = binList.getCardInfoByCardNumber("121323123123");

        assertEquals(newCard.getScheme(), card.getScheme());
        assertEquals(newCard.getBank().getName(), card.getBank());
        assertEquals(newCard.getType(), card.getType());
    }

    @Test
    public void testResponseLimit() throws IOException {
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_BAD_REQUEST);

        assertThatThrownBy(() -> binList.getCardInfoByCardNumber("121323123123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Limit of requests exceeded, you can only do 10 requests per minute");
    }
}
