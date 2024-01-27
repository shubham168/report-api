package com.example.records.reportapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.records.reportapi.dto.SearchRequestDto;
import com.example.records.reportapi.dto.SearchResponseDto;
import com.example.records.reportapi.service.EligibilityService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class EligibilityServiceController {

    @Autowired
    EligibilityService service;

    @GetMapping("/plans")
    public ResponseEntity<List<String>> getPlanNames() {
        List<String> planNames = service.getUniquePlanNames();
        return new ResponseEntity<>(planNames, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<List<String>> getPlanStatuses() {
        List<String> planStatuses = service.getUniquePlanStatuses();
        return new ResponseEntity<>(planStatuses, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<SearchResponseDto>> search(@RequestBody SearchRequestDto searchRequestDto) {
        List<SearchResponseDto> result = service.search(searchRequestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/excel")
    public void excelReport(HttpServletResponse response) {
        response.setContentType("application/octate-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=data.xls";

        response.setHeader(headerKey, headerValue);

        try {
            service.generateExcel(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/pdf")
    public void pdfReport(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=data.pdf";
        response.setHeader(headerKey, headerValue);

        service.generatePdf(response);
    }

}
