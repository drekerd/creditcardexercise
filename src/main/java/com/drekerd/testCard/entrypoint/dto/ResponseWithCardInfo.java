package com.drekerd.testCard.entrypoint.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWithCardInfo {

    private CardControllerDTO payload;
    private boolean sucess;

}
