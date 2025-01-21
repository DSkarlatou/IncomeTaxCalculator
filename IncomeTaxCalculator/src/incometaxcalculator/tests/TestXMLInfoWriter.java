package incometaxcalculator.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import incometaxcalculator.data.io.InfoWriter;
import incometaxcalculator.data.io.XMLInfoWriter;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

class TestXMLInfoWriter {

  @Test
  void test() throws WrongReceiptKindException, WrongReceiptDateException, WrongTaxpayerStatusException, IOException {
    int taxRegistrationNumber = 888888888;
    File file = new File(taxRegistrationNumber+"_INFO.xml");
    file.createNewFile();
    String name = "Danae Scarlett";
    String status = "Single";
    float income = 29080;
    TaxpayerManager taxpayerManager = new TaxpayerManager();
    taxpayerManager.createTaxpayer(name, taxRegistrationNumber, status, income);;
    taxpayerManager.createReceipt(8, "8/8/2008", 888, "Travel", "EIGHT", "OKTO", "HACHI", "yeodeolb", 8, 888888888);
    taxpayerManager.createReceipt(7, "7/7/2007", 777, "Entertainment", "SEVEN", "EPTA", "nana", "ilgob", 7, 888888888);
   
    InfoWriter writer = new XMLInfoWriter();
    writer.generateFile(taxRegistrationNumber);
    String actual = Files.readString(Path.of(taxRegistrationNumber+"_INFO.xml"));
    String expected = "<Name> Danae Scarlett </Name>"
                    + "<AFM> 888888888 </AFM>"
                    + "<Status> Single </Status>"
                    + "<Income> 29080.0 </Income>"
                    + "<Receipts>"
                    + "<ReceiptID> 8 </ReceiptID>"
                    + "<Date> 8/8/2008 </Date>"
                    + "<Kind> Travel </Kind>"
                    + "<Amount> 888.0 </Amount>"
                    + "<Company> EIGHT </Company>"
                    + "<Country> OKTO </Country>"
                    + "<City> HACHI </City>"
                    + "<Street> yeodeolb </Street>"
                    + "<Number> 8 </Number>"
                    + "<ReceiptID> 7 </ReceiptID>"
                    + "<Date> 7/7/2007 </Date>"
                    + "<Kind> Entertainment </Kind>"
                    + "<Amount> 777.0 </Amount>"
                    + "<Company> SEVEN </Company>"
                    + "<Country> EPTA </Country>"
                    + "<City> nana </City>"
                    + "<Street> ilgob </Street>"
                    + "<Number> 7 </Number>";
    expected = expected.replaceAll("(\\r|\\n)", "");
    actual = actual.replaceAll("(\\r|\\n)", "");
    assertEquals(expected, actual);
  }

}
