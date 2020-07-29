package com.drekerd.testCard.core;


import com.drekerd.testCard.infrastructure.core.BinList;
import com.drekerd.testCard.infrastructure.dto.Card;
import com.drekerd.testCard.utils.exceptions.NotReadableCardNumberException;
import com.drekerd.testCard.entrypoint.dto.CardControllerDTO;
import com.drekerd.testCard.entrypoint.dto.ResponseWithCardInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CardService {

    @Autowired
    BinList binListRequest;

    public ResponseWithCardInfo validateCard(String cardNumber) {
        log.info(this.getClass().getName() + "Validate cards Enters with cards number: " + cardNumber);
        cardNumberBasicvaildations(cardNumber);
        return createOkResponse(binListRequest.getCardInfoByCardNumber(cardNumber));
    }

    private void cardNumberBasicvaildations(String cardNumber) {
        validateCardNumberIsNotNull(cardNumber);
        validateNotNumeric(cardNumber);
    }

    private void validateNotNumeric(String cardNumber) {
        if (!(cardNumber.matches("[0-9]+") && cardNumber.length() > 2)) {
            throw new NotReadableCardNumberException("The cards number must contain only numbers");
        }
    }

    private ResponseWithCardInfo createOkResponse(Card card) {
        log.info(this.getClass().getName() + "createOkResponse with cards: " + card.toString());
        CardControllerDTO cardControllerDTO = new CardControllerDTO.Builder()
                .withScheme(card.getScheme())
                .withType(card.getType())
                .withBank(card.getBank())
                .build();
        ResponseWithCardInfo response = new ResponseWithCardInfo();
        response.setPayload(cardControllerDTO);
        response.setSucess(true);

        return response;

    }

    private void validateCardNumberIsNotNull(String cardNumber) {
        if (null == cardNumber || cardNumber.isEmpty()) {
            throw new NotReadableCardNumberException("Not possible to read the cards number provided");
        }
    }

}
