package com.example.blpsadminservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDTO {
    private Long id;
    private String name;
    private Double rate;
}
