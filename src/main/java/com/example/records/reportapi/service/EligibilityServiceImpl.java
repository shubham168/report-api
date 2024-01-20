package com.example.records.reportapi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
       return eligRepo.findPlanNameStatuses();
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateExcel'");
    }

    @Override
    public void generatePdf(HttpServletResponse httpServletResponse) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generatePdf'");
    }
    
}
