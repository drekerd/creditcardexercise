package com.drekerd.testCard.entrypoint.dto;


import com.drekerd.testCard.core.cards.BaseCard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardControllerDTO extends BaseCard {

    public static class Builder {
        private String scheme;
        private String type;
        private String bank;

        public Builder withScheme(String scehme) {
            this.scheme = scehme;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withBank(String bank) {
            this.bank = bank;
            return this;
        }

        public CardControllerDTO build() {
            CardControllerDTO cardControllerDTO = new CardControllerDTO();

            cardControllerDTO.scheme = this.scheme;
            cardControllerDTO.type = this.type;
            cardControllerDTO.bank = this.bank;
            return cardControllerDTO;
        }

    }

    private CardControllerDTO() {

    }
}
