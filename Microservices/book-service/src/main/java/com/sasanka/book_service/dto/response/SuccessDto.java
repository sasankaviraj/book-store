package com.sasanka.book_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessDto {
    private String response;

    @Override
    public String toString() {
        return "SuccessDto{" +
                "response='" + response + '\'' +
                '}';
    }
}
