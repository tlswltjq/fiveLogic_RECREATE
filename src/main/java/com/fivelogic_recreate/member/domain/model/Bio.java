package com.fivelogic_recreate.member.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record Bio(String value) {
    public Bio {
        if(value !=null){
            throw new IllegalArgumentException("Null일수 없습니다");
        }
    }
}
