package incometaxcalculator.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import incometaxcalculator.data.io.InfoWriter;
import incometaxcalculator.data.io.TXTInfoWriter;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

class TestTXTInfoWriter {

  @Test
  void test() throws WrongTaxpayerStatusException, IOException, WrongReceiptKindException, WrongReceiptDateException {
    int taxRegistrationNumber = 999999999;
    File file = new File(taxRegistrationNumber+"_INFO.txt");
    file.createNewFile();
    String name = "Danae Scarlett";
    String status = "Single";
    float income = 29080;
    TaxpayerManager taxpayerManager = new TaxpayerManager();
    taxpayerManager.createTaxpayer(name, taxRegistrationNumber, status, income);;
    taxpayerManager.createReceipt(9, "9/9/2009", 999, "Basic", "NINE", "ENNIA", "Kyu", "ahob", 9, 999999999);
    taxpayerManager.createReceipt(6, "6/6/2006", 666, "Health", "SIX", "E3I", "Roku", "yeoseos", 6, 999999999);
   
    InfoWriter writer = new TXTInfoWriter();
    writer.generateFile(taxRegistrationNumber);
    String actual = Files.readString(Path.of(taxRegistrationNumber+"_INFO.txt"));
    String expected = "Name: Danae Scarlett"
                    + "AFM: 999999999"
                    + "Status: Single"
                    + "Income: 29080.0"
                    + "Receipts:"
                    + "Receipt ID: 9"
                    + "Date: 9/9/2009"
                    + "Kind: Basic"
                    + "Amount: 999.0"
                    + "Company: NINE"
                    + "Country: ENNIA"
                    + "City: Kyu"
                    + "Street: ahob"
                    + "Number: 9"
                    + "Receipt ID: 6"
                    + "Date: 6/6/2006"
                    + "Kind: Health"
                    + "Amount: 666.0"
                    + "Company: SIX"
                    + "Country: E3I"
                    + "City: Roku"
                    + "Street: yeoseos"
                    + "Number: 6";
    expected = expected.replaceAll("(\\r|\\n)", "");
    actual = actual.replaceAll("(\\r|\\n)", "");
    assertEquals(expected, actual);
  }

}
