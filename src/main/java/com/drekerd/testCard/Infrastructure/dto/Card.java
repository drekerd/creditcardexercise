package com.drekerd.testCard.Infrastructure.dto;

import com.drekerd.testCard.core.cards.BaseCard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card extends BaseCard {

    public static class Builder {
        private String scheme;
        private String type;
        private String bank;

        public Card.Builder withScheme(String scehme) {
            this.scheme = (scehme == null) ? "No Info for this cards" : scehme;
            return this;
        }

        public Card.Builder withType(String type) {
            this.type = (type == null) ? "No Info for this cards" : type;
            return this;
        }

        public Card.Builder withBank(String bank) {
            this.bank = (bank == null) ? "No Info for this cards" : bank;
            return this;
        }

        public Card build() {
            Card card = new Card();

            card.scheme = this.scheme;
            card.type = this.type;
            card.bank = this.bank;
            return card;
        }

    }

    private Card() {

    }

}
