package com.example.records.reportapi.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SearchRequestDto {
    private String planName;
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
}
