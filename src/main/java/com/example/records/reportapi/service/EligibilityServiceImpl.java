package com.example.records.reportapi.service;

import java.awt.Color;
import java.io.IOException;
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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import jakarta.servlet.ServletOutputStream;
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
        if (planName != null && !planName.equals("")) {
            queryBuilder.setPlanName(planName);
        }

        String planStatus = request.getPlanStatus();
        if (planStatus != null && !planStatus.equals("")) {
            queryBuilder.setPlanStatus(planStatus);
        }

        LocalDate startDate = request.getPlanStartDate();
        if (startDate != null) {
            queryBuilder.setPlanStartDate(startDate);
        }

        LocalDate endDate = request.getPlanEndDate();
        if (endDate != null) {
            queryBuilder.setPlanEndDate(endDate);
        }

        Example<EligibilityDetails> example = Example.of(queryBuilder);
        List<EligibilityDetails> entities = eligRepo.findAll(example);

        for (EligibilityDetails entity : entities) {
            SearchResponseDto sr = new SearchResponseDto();
            BeanUtils.copyProperties(entities, sr);
            response.add(sr);
        }

        return response;

    }

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {
        List<EligibilityDetails> entities = eligRepo.findAll();
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet sheet = workBook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("S.No");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("SSN");
        headerRow.createCell(4).setCellValue("Gender");
        headerRow.createCell(5).setCellValue("Mobile");
        int i = 1;
        for (EligibilityDetails e : entities) {
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(e.getName());
            row.createCell(2).setCellValue(e.getEmail());
            row.createCell(3).setCellValue(e.getSsn());
            row.createCell(4).setCellValue(String.valueOf(e.getGender()));
            row.createCell(5).setCellValue(String.valueOf(e.getMobile()));
            i++;
        }

        ServletOutputStream outputStream = response.getOutputStream();

        workBook.write(outputStream);
        workBook.close();
        outputStream.close();

    }

    @Override
    public void generatePdf(HttpServletResponse response) throws Exception {
        List<EligibilityDetails> entities = eligRepo.findAll();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Search Report", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 3.0f, 1.5f });
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        font = FontFactory.getFont(FontFactory.COURIER);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phone no", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("SSN", font));
        table.addCell(cell);

        for (EligibilityDetails e : entities) {
            table.addCell(e.getName());
            table.addCell(e.getEmail());
            table.addCell(String.valueOf(e.getMobile()));
            table.addCell(String.valueOf(e.getGender()));
            table.addCell(String.valueOf(e.getSsn()));
        }
        document.add(table);
        document.close();

    }

}
