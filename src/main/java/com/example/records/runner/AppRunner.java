package com.example.records.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.records.reportapi.entity.EligibilityDetails;
import com.example.records.reportapi.repository.EligibilityDetailsRepo;

@Component
public class AppRunner implements ApplicationRunner {
    @Autowired
    EligibilityDetailsRepo eligRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Inside app");
        EligibilityDetails entity1 = new EligibilityDetails();
        entity1.setEligId(1);
        entity1.setName("John");
        entity1.setMobile(1234222221L);
        entity1.setGender('M');
        entity1.setEmail("John@gmail.com");
        entity1.setPlanName("NEWARK Plus");
        entity1.setSsn(12126162L);
        entity1.setPlanStatus("APPROVED");

        eligRepo.save(entity1);

        EligibilityDetails entity2 = new EligibilityDetails();
        entity2.setEligId(2);
        entity2.setName("Will");
        entity2.setMobile(54151151545L);
        entity2.setGender('M');
        entity2.setEmail("Will@gmail.com");
        entity2.setPlanName("Ohio Plus");
        entity2.setSsn(74841551L);
        entity2.setPlanStatus("Denied");

        eligRepo.save(entity2);
    }

}
