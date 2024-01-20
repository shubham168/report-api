package com.example.records.reportapi.dto;

import lombok.Data;

@Data
public class SearchResponseDto {

    private String name;
    private Long mobile;
    private String email;
    private char gender;
    private Long ssn;
    private String planName;
    private String planStatus;
}
