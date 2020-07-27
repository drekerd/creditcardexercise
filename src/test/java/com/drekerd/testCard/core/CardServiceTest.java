package com.drekerd.testCard.core;

import com.drekerd.testCard.Infrastructure.dto.Card;
import com.drekerd.testCard.Infrastructure.externalRequests.BinListRequest;
import com.drekerd.testCard.Utils.ExceptionUtils.NotReadableCardNumberException;
import com.drekerd.testCard.entrypoint.dto.ResponseWithCardInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CardServiceTest {

    @Mock
    BinListRequest binListRequest;

    @InjectMocks
    CardService cardService = new CardService();

    Card card = new Card.Builder()
            .withScheme("Visa")
            .withType("Credit")
            .withBank("Bank")
            .build();

    @Test
    public void testCard(){
        when(binListRequest.getCardInfoByCardNumber(any()))
                .thenReturn(card);
        ResponseWithCardInfo responseWithCardInfo = cardService.validateCard("123456789");
        assertEquals(responseWithCardInfo.getPayload().getScheme(), "Visa");
    }

    @Test()
    public void testInvalidCardOneNumber(){
        Assertions.assertThrows(NotReadableCardNumberException.class, () -> {
            ResponseWithCardInfo responseWithCardInfo = cardService.validateCard("1");
        });
    }

    @Test()
    public void testInvalidCardTwoNumbers(){
        Assertions.assertThrows(NotReadableCardNumberException.class, () -> {
            ResponseWithCardInfo responseWithCardInfo = cardService.validateCard("12");
        });
    }

    @Test()
    public void testInvalidCardString(){
        Assertions.assertThrows(NotReadableCardNumberException.class, () -> {
            ResponseWithCardInfo responseWithCardInfo = cardService.validateCard("String");
        });
    }

    @Test()
    public void testInvalidCardSpecialCharacters(){
        Assertions.assertThrows(NotReadableCardNumberException.class, () -> {
            ResponseWithCardInfo responseWithCardInfo = cardService.validateCard("123234-12");
        });
    }
}
