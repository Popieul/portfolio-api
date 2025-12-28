package com.portfolio.controller;

import com.portfolio.model.CvRequest;
import com.portfolio.service.PdfGeneratorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cv")
public class CvController {

    private final PdfGeneratorService pdfGeneratorService;

    public CvController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateCv(@RequestBody CvRequest request) {
        try {
            byte[] pdfBytes = pdfGeneratorService.generateCvPdf(
                request.getProfile(), 
                request.getExperiences()
            );

            String filename = pdfGeneratorService.generateFilename(request.getProfile());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", filename);
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("CV Generator API is running");
    }
}
