/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astro.ui;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 *
 * @author Rayan Ivaturi
 */
public class TextToPdfConverter {

    public static void convert(String textFile, String pdfFile) throws Exception {

        Font courier_Bold_16 = FontFactory.getFont(FontFactory.COURIER_BOLD, 16.0f);        
        Font times_Bold_Italic_20 = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 20.0f);

        Document document = new Document(PageSize.A3);

        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        document.open();

        BufferedReader br = new BufferedReader(new FileReader(textFile));

        String str = null;               

        while ((str = br.readLine()) != null) {
            if (str.contains("Horoscope of")) {
                document.add(new Phrase(str, times_Bold_Italic_20));
            } else {
                document.add(new Phrase(str, courier_Bold_16));
            }

            document.add(new Phrase(Chunk.NEWLINE));

            if (str.trim().endsWith("SUBBARAYAN")) {
                document.add(new Phrase(Chunk.NEXTPAGE));
            }
        }

        br.close();

        document.close();
    }
}
