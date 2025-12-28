package com.portfolio.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.portfolio.model.CvProfile;
import com.portfolio.model.Experience;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class PdfGeneratorService {

    private final TemplateEngine templateEngine;

    public PdfGeneratorService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generateCvPdf(CvProfile profile, List<Experience> experiences) throws Exception {
        // 1. Render HTML with Thymeleaf
        Context context = new Context();
        context.setVariable("profile", profile);
        context.setVariable("experiences", experiences);
        context.setVariable("currentYear", LocalDate.now().getYear());

        String htmlContent = templateEngine.process("cv-template", context);

        // 2. Convert HTML to PDF with OpenHTMLtoPDF
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, getClass().getResource("/templates/").toExternalForm());
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();
        }
    }

    public String generateFilename(CvProfile profile) {
        String date = LocalDate.now().toString();
        return String.format("CV_%s_%s_%s.pdf",
                profile.getFirstName(),
                profile.getLastName(),
                date);
    }
}
