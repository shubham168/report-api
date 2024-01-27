package com.example.records.reportapi.service;

import java.io.IOException;
import java.util.List;

import com.example.records.reportapi.dto.SearchRequestDto;
import com.example.records.reportapi.dto.SearchResponseDto;

import jakarta.servlet.http.HttpServletResponse;

public interface EligibilityService {
    public List<String> getUniquePlanNames();

    public List<String> getUniquePlanStatuses();

    public List<SearchResponseDto> search(SearchRequestDto searchRequestDto);

    public void generateExcel(HttpServletResponse httpServletResponse) throws IOException;

    public void generatePdf(HttpServletResponse httpServletResponse) throws Exception;
}
