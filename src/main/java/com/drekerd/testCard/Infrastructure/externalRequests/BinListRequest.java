package com.drekerd.testCard.Infrastructure.externalRequests;

import com.drekerd.testCard.Infrastructure.dto.Card;
import com.drekerd.testCard.Infrastructure.cards.CardResponse;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
public class BinListRequest {

    @Value("${binlist.url}")
    private String BINLIST_URL;

    public Card getCardInfoByCardNumber(String cardNumber) {
        log.info(this.getClass().getName() + "getCardInfoByCardNumber: " + cardNumber);
        return getRequestByCardNumber(cardNumber);
    }

    // How to mock this if i change it to private
    public Card getRequestByCardNumber(String cardNumber) {
        log.info(this.getClass().getName() + "getRequestByCardNumber, Start Request to " + BINLIST_URL + " with cards number" + cardNumber);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(BINLIST_URL + cardNumber);
        try {
            HttpResponse httpresponse = httpClient.execute(getRequest);
            return convertResponseToObject(httpresponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(this.getClass().getName() + "Error getting connecting to External Application ", BINLIST_URL);
        return null;
    }

    // How to mock this if i change it to private
    public Card convertResponseToObject(HttpResponse httpresponse) {

        try {
            final String response = EntityUtils.toString(httpresponse.getEntity());
            log.info(this.getClass().getName() + "convertResponseToObject : responseEntity " + response);
            CardResponse cardResponseFromAPI = new Gson().fromJson(response, CardResponse.class);
            log.info("cards Converted: ", cardResponseFromAPI);
            return new Card.Builder()
                    .withScheme(cardResponseFromAPI.getScheme())
                    .withType(cardResponseFromAPI.getType())
                    .withBank(cardResponseFromAPI.getBank().getName())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(this.getClass().getName() + "Error Creating Response ", BINLIST_URL);
        return null;
    }

}
