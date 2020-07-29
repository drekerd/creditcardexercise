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
    public void testResponse(){
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
    public void testResponse_BankName_Empty(){
        Gson gson = new Gson();

        CardResponse newCard = createNewCard();
        newCard.getBank().setName("");

        String jsonInString = gson.toJson(newCard);
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.getEntity()).thenReturn(EntityBuilder.create().setText(jsonInString).build());

        Card card = binList.getCardInfoByCardNumber("121323123123");

        assertEquals(newCard.getScheme(), card.getScheme());
        assertEquals("No Info for this cards", card.getBank());
        assertEquals(newCard.getType(), card.getType());
    }

    @Test
    public void testResponse_BankName_Null(){
        Gson gson = new Gson();

        CardResponse newCard = createNewCard();
        newCard.getBank().setName(null);

        String jsonInString = gson.toJson(newCard);
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.getEntity()).thenReturn(EntityBuilder.create().setText(jsonInString).build());

        Card card = binList.getCardInfoByCardNumber("121323123123");

        assertEquals(newCard.getScheme(), card.getScheme());
        assertEquals("No Info for this cards", card.getBank());
        assertEquals(newCard.getType(), card.getType());
    }

    @Test
    public void testResponse_BankName_Blank(){
        Gson gson = new Gson();

        CardResponse newCard = createNewCard();
        newCard.getBank().setName(" ");

        String jsonInString = gson.toJson(newCard);
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.getEntity()).thenReturn(EntityBuilder.create().setText(jsonInString).build());

        Card card = binList.getCardInfoByCardNumber("121323123123");

        assertEquals(newCard.getScheme(), card.getScheme());
        assertEquals("No Info for this cards", card.getBank());
        assertEquals(newCard.getType(), card.getType());
    }

    @Test
    public void testResponse_Scheme_Empty(){
        Gson gson = new Gson();

        CardResponse newCard = createNewCard();
        newCard.setScheme("");

        String jsonInString = gson.toJson(newCard);
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.getEntity()).thenReturn(EntityBuilder.create().setText(jsonInString).build());

        Card card = binList.getCardInfoByCardNumber("121323123123");

        assertEquals("No Info for this cards", card.getScheme());
        assertEquals(newCard.getBank().getName(), card.getBank());
        assertEquals(newCard.getType(), card.getType());
    }

    @Test
    public void testResponse_Scheme_Null(){
        Gson gson = new Gson();

        CardResponse newCard = createNewCard();
        newCard.setScheme(null);

        String jsonInString = gson.toJson(newCard);
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.getEntity()).thenReturn(EntityBuilder.create().setText(jsonInString).build());

        Card card = binList.getCardInfoByCardNumber("121323123123");

        assertEquals("No Info for this cards", card.getScheme());
        assertEquals(newCard.getBank().getName(), card.getBank());
        assertEquals(newCard.getType(), card.getType());
    }

    @Test
    public void testResponse_Scheme_Blank(){
        Gson gson = new Gson();

        CardResponse newCard = createNewCard();
        newCard.setScheme(" ");

        String jsonInString = gson.toJson(newCard);
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.getEntity()).thenReturn(EntityBuilder.create().setText(jsonInString).build());

        Card card = binList.getCardInfoByCardNumber("121323123123");

        assertEquals("No Info for this cards", card.getScheme());
        assertEquals(newCard.getBank().getName(), card.getBank());
        assertEquals(newCard.getType(), card.getType());
    }

    @Test
    public void testResponse_Type_Empty(){
        Gson gson = new Gson();

        CardResponse newCard = createNewCard();
        newCard.setType("");

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
        assertEquals("No Info for this cards", card.getType());
    }

    @Test
    public void testResponse_Type_Null(){
        Gson gson = new Gson();

        CardResponse newCard = createNewCard();
        newCard.setType(null);

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
        assertEquals("No Info for this cards", card.getType());
    }

    @Test
    public void testResponse_Type_Blank(){
        Gson gson = new Gson();

        CardResponse newCard = createNewCard();
        newCard.setType(" ");

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
        assertEquals("No Info for this cards", card.getType());
    }

    @Test
    public void testResponseLimit(){
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(249);

        assertThatThrownBy(() -> binList.getCardInfoByCardNumber("121323123123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Limit of requests exceeded, you can only do 10 requests per minute");
    }

    @Test
    public void testBadRequest(){
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_BAD_REQUEST);

        assertThatThrownBy(() -> binList.getCardInfoByCardNumber("121323123123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Something with your request went wrong");
    }

    @Test
    public void testUnauthorized(){
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_UNAUTHORIZED);

        assertThatThrownBy(() -> binList.getCardInfoByCardNumber("121323123123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Incorrectly Authenticated");
    }

    @Test
    public void testForbidden(){
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_FORBIDDEN);

        assertThatThrownBy(() -> binList.getCardInfoByCardNumber("121323123123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("You have no permissions to this information");
    }

    @Test
    public void testNotFound(){
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_NOT_FOUND);

        assertThatThrownBy(() -> binList.getCardInfoByCardNumber("121323123123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The url you entered does not exist, check with your admin");
    }

    @Test
    public void testInternalServerError(){
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(statusLine.getReasonPhrase()).thenReturn("Error");

        assertThatThrownBy(() -> binList.getCardInfoByCardNumber("121323123123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Some undefined error has occurred, please check with your admin. Error");
    }

    @Test
    public void testUndefinedError(){
        HttpResponse mockedResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(binListRequest.doGetRequest(anyString())).thenReturn(mockedResponse);
        when(mockedResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_GATEWAY_TIMEOUT);
        when(statusLine.getReasonPhrase()).thenReturn("Error");

        assertThatThrownBy(() -> binList.getCardInfoByCardNumber("121323123123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Some undefined error has occurred, please check with your admin. Error");
    }
}
