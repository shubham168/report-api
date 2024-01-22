package com.example.records.reportapi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.example.records.reportapi.dto.SearchRequestDto;
import com.example.records.reportapi.dto.SearchResponseDto;
import com.example.records.reportapi.entity.EligibilityDetails;
import com.example.records.reportapi.repository.EligibilityDetailsRepo;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class EligibilityServiceImpl implements EligibilityService {

    @Autowired
    private EligibilityDetailsRepo eligRepo;
    @Override
    public List<String> getUniquePlanNames() {
        return eligRepo.findPlanNames();
    }

    @Override
    public List<String> getUniquePlanStatuses() {
       return eligRepo.findPlanStatuses();
    }

    @Override
    public List<SearchResponseDto> search(SearchRequestDto request) {
        List<SearchResponseDto> response = new ArrayList<>();
        EligibilityDetails queryBuilder = new EligibilityDetails();

        String planName = request.getPlanName();
        if(planName != null && !planName.equals("")){
            queryBuilder.setPlanName(planName);
        }

        
        String planStatus = request.getPlanStatus();
        if(planStatus != null && !planStatus.equals("")){
            queryBuilder.setPlanStatus(planStatus);
        }

        LocalDate startDate = request.getPlanStartDate();
        if(startDate != null){
            queryBuilder.setPlanStartDate(startDate);
        }

        LocalDate endDate = request.getPlanEndDate();
        if(endDate != null){
            queryBuilder.setPlanEndDate(endDate);
        }

        Example<EligibilityDetails> example = Example.of(queryBuilder);
        List<EligibilityDetails> entities = eligRepo.findAll(example);

        for(EligibilityDetails entity: entities){
            SearchResponseDto sr = new SearchResponseDto();
            BeanUtils.copyProperties(entities, sr);
            response.add(sr);
        }

        return response;
        
    }

    @Override
    public void generateExcel(HttpServletResponse httpServletResponse) {
        List<EligibilityDetails> entities = eligRepo.findAll();
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet sheet = workBook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("S.No");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("SSN");
        headerRow.createCell(3).setCellValue("Gender");
        headerRow.createCell(4).setCellValue("Mobile");
        int i = 1;
        for(EligibilityDetails e: entities){
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(e.getName());
            row.createCell(2).setCellValue(e.getSsn());
            row.createCell(3).setCellValue(e.getGender());
            row.createCell(4).setCellValue(e.getMobile());
            i++;
        }


    }

    @Override
    public void generatePdf(HttpServletResponse httpServletResponse) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generatePdf'");
    }
    
}
