package com.drekerd.testCard.infrastructure.core;

import com.drekerd.testCard.infrastructure.dto.Card;
import com.drekerd.testCard.infrastructure.cards.CardResponse;
import com.drekerd.testCard.infrastructure.externalRequests.BinListRequest;
import com.drekerd.testCard.utils.exceptions.BinlistRequestsExceededException;
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

    private final int STATUS_OK = 200;
    private final int STATUS_REQUEST_EXCEEDED = 249;
    private final int STATUS_BAD_REQUEST = 400;
    private final int STATUS_UNAUTHORIZED = 401;
    private final int STATUS_FORBIDDEN = 403;
    private final int STATUS_NOT_FOUND = 404;
    private final int STATUS_INTERNAL_SERVER_ERROR = 500;

    @Autowired
    private BinListRequest binListRequest;

    public Card getCardInfoByCardNumber(final String cardNumber) {
        log.info(this.getClass().getName() + "Build URL: " + BINLIST_URL + " with card number: " + cardNumber);
        String URL = BINLIST_URL + cardNumber;
        HttpResponse response = binListRequest.doGetRequest(URL);

        return handleResponse(response);
    }

    private Card handleResponse(final HttpResponse response) {
        if (response.getStatusLine().getStatusCode() == STATUS_OK) {
            return convertResponseToObject(response);
        } else if (response.getStatusLine().getStatusCode() == STATUS_REQUEST_EXCEEDED) {
            throw new BinlistRequestsExceededException("Limit of requests exceeded, you can only do 10 requests per minute");
        } else if (response.getStatusLine().getStatusCode() == STATUS_BAD_REQUEST) {
            throw new RuntimeException("Something with your request went wrong");
        } else if (response.getStatusLine().getStatusCode() == STATUS_UNAUTHORIZED) {
            throw new RuntimeException("Incorrectly Authenticated");
        } else if (response.getStatusLine().getStatusCode() == STATUS_FORBIDDEN) {
            throw new RuntimeException("You have no permissions to this information");
        } else if (response.getStatusLine().getStatusCode() == STATUS_NOT_FOUND) {
            throw new RuntimeException("The url you entered does not exist, check with your admin");
        } else if (response.getStatusLine().getStatusCode() == STATUS_INTERNAL_SERVER_ERROR) {
            throw new RuntimeException("Some undefined error has occurred, please check with your admin. " + response.getStatusLine().getReasonPhrase());
        } else {
            throw new RuntimeException("Some undefined error has occurred, please check with your admin. " + response.getStatusLine().getReasonPhrase());
        }
    }

    private Card convertResponseToObject(final HttpResponse httpresponse) {

        try {
            final String response = EntityUtils.toString(httpresponse.getEntity());
            log.info(this.getClass().getName() + "convertResponseToObject : responseEntity " + response);
            final CardResponse cardResponseFromAPI = new Gson().fromJson(response, CardResponse.class);
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
