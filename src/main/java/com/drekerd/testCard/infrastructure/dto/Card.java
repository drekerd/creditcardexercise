package com.drekerd.testCard.infrastructure.dto;

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

        public Card.Builder withScheme(final String scheme) {
            this.scheme = (scheme == null || scheme.isEmpty() || scheme.isBlank()) ? "No Info for this cards" : scheme;
            return this;
        }

        public Card.Builder withType(final String type) {
            this.type = (type == null || type.isEmpty() || type.isBlank()) ? "No Info for this cards" : type;
            return this;
        }

        public Card.Builder withBank(final String bank) {
            this.bank = (bank == null || bank.isEmpty() || bank.isBlank()) ? "No Info for this cards" : bank;
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
