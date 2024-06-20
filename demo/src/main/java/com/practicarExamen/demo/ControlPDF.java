package com.practicarExamen.demo;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;
public class ControlPDF {
    public void createPDF(Spaceship ship){
        try {
            Document document = new Document(PageSize.A4, 50, 50, 100, 72);
            PdfWriter.getInstance(document, new FileOutputStream("./Demo/naves/" + ship.getName() + ".pdf"));

            document.open();

            // Escribimos los nuevos datos
            document.add(new Paragraph("Ship information"));
            document.add(new Paragraph("Name: " + ship.getName()));
            document.add(new Paragraph("Model: " + ship.getModel()));
            document.add(new Paragraph("Manufacture: " + ship.getManufacturer()));
            document.add(new Paragraph("Length: " + ship.getLength()));
            document.add(new Paragraph("Crew: " + ship.getCrew()));

            document.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void createPDF2(List<Spaceship> spaceships) {
        try {
            Document document = new Document(PageSize.A4, 50, 50, 100, 72);
            PdfWriter.getInstance(document, new FileOutputStream("./Demo/PDF_Completo/naves_completo.pdf"));

            document.open();

            document.add(new Paragraph("Spaceships Information"));

            for (Spaceship ship : spaceships) {
                document.add(new Paragraph("\n\nShip information"));
                document.add(new Paragraph("Name: " + ship.getName()));
                document.add(new Paragraph("Model: " + ship.getModel()));
                document.add(new Paragraph("Manufacturer: " + ship.getManufacturer()));
                document.add(new Paragraph("Length: " + ship.getLength()));
                document.add(new Paragraph("Crew: " + ship.getCrew()));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
