package com.drekerd.testCard.infrastructure.core;

import com.drekerd.testCard.infrastructure.dto.Card;
import com.drekerd.testCard.infrastructure.cards.CardResponse;
import com.drekerd.testCard.infrastructure.externalRequests.BinListRequest;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
public class BinList {

    @Value("${binlist.url}")
    private String BINLIST_URL;

    @Autowired
    private BinListRequest binListRequest;

    public Card getCardInfoByCardNumber(String cardNumber) {
        log.info(this.getClass().getName() + "Build URL: " + BINLIST_URL + " with card number: " + cardNumber);
        String URL = BINLIST_URL + cardNumber;
        HttpResponse response = binListRequest.doGetRequest(URL);

        if (response.getStatusLine().getStatusCode() == 200) {
            return convertResponseToObject(response);
        } else {
            throw new RuntimeException("Limit of requests exceeded, you can only do 10 requests per minute");
        }
    }

    private Card convertResponseToObject(HttpResponse httpresponse) {

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
        log.error(this.getClass().getName() + "Error Creating Response ", BINLIST_URL);
        return null;
    }

}
