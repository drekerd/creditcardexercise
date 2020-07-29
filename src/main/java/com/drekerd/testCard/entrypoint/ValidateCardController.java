package com.drekerd.testCard.entrypoint;

import com.drekerd.testCard.core.CardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards-scheme")
@Log4j2
public class ValidateCardController {

    @Autowired
    CardService cardService;

    @GetMapping("/verify/{cardNumber}")
    public ResponseEntity<Object> validateCard(@PathVariable final String cardNumber) {
        log.info(this.getClass().getName() + " validateCard ENTER: with cards number: " + cardNumber);
        return new ResponseEntity<>(cardService.validateCard(cardNumber), HttpStatus.OK);
    }

}
