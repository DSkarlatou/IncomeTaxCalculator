package incometaxcalculator.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;



class TestTaxpayerManager {
  
  @Test 
  void test() throws WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException, IOException, ReceiptAlreadyExistsException, WrongFileFormatException {
    //---createTaxpayer
    TaxpayerManager taxpayerManager = new TaxpayerManager();
    String name = "Danae Skarlatou";
    int TRN = 123123123;
    String status = "Single";
    float income = 12000;
    
    taxpayerManager.createTaxpayer(name, TRN, status, income);
    assertEquals("Danae Skarlatou",taxpayerManager.getTaxpayerName(TRN));
    assertEquals("Single",taxpayerManager.getTaxpayerStatus(TRN));
    assertEquals("12000.0", taxpayerManager.getTaxpayerIncome(TRN));

    String name1 = "Danae Scarlett";
    int TRN1 = 123123123;
    String status1 = "Single yet Married";
    float income1 = 12;
    
    WrongTaxpayerStatusException thrown = Assertions.assertThrows(WrongTaxpayerStatusException.class, () -> {
      taxpayerManager.createTaxpayer(name1, TRN1, status1, income1); //this should pass
    });
    Assertions.assertEquals("Please check taxpayer's status and try again!", thrown.getMessage());

    //---addReceipt
    int id = 1;
    String date = "12/12/22";
    float amount = 300;
    String kind = "Entertainment";
    String companyName = "Australian Magpies";
    String country = "Australia";
    String city = "Melbourne";
    String street = "Mitty";
    int number = 6;
    
    int id2 = 2;
    String date2 = "12/12/22";
    float amount2 = 300;
    String kind2 = "Entertainment";
    String companyName2 = "Australian Magpies";
    String country2 = "Australia";
    String city2 = "Melbourne";
    String street2 = "Mitty";
    int number2 = 4;
    
    taxpayerManager.addReceipt(id, date, amount, kind, companyName, country, city, street, number, TRN);
    taxpayerManager.addReceipt(id2, date2, amount2, kind2, companyName2, country2, city2, street2, number2, TRN);
    assertEquals(2, taxpayerManager.getTaxpayerTotalReceiptsGathered(TRN));
    
    ReceiptAlreadyExistsException thrown1 = Assertions.assertThrows(ReceiptAlreadyExistsException.class, () -> {
      taxpayerManager.addReceipt(id, date, amount, kind, companyName, country, city, street, number, TRN);
    });
    Assertions.assertEquals("Receipt already exists.", thrown1.getMessage());
    
    String dateF = "12-12-22";
    int id3 = 3;
    WrongReceiptDateException thrown2 = Assertions.assertThrows(WrongReceiptDateException.class, () -> {
      taxpayerManager.addReceipt(id3, dateF, amount, kind, companyName, country, city, street, number, TRN);
    });
    Assertions.assertEquals("Please make sure your date is DD/MM/YYYY and try again.", thrown2.getMessage()); 
   
    int id4 = 4;
    String kind3 = "Hello, how are you?";
    WrongReceiptKindException thrown3 = Assertions.assertThrows(WrongReceiptKindException.class, () -> {
      taxpayerManager.addReceipt(id4, date, amount, kind3, companyName, country, city, street, number, TRN);
    });
    Assertions.assertEquals("Please check receipts kind and try again.", thrown3.getMessage());  
    
    //---removeReceipt
    assertEquals(true, taxpayerManager.containsReceipt(1));
    taxpayerManager.removeReceipt(1);
    assertEquals(false, taxpayerManager.containsReceipt(1));

    //---saveLogFile throws WrongFileFormatException
    String formatTXT = "txt";
    taxpayerManager.saveLogFile(TRN, "txt");
    taxpayerManager.saveLogFile(TRN, "xml");

    BufferedReader br = new BufferedReader(new FileReader(TRN+"_LOG.txt"));
    String line;
    ArrayList<String> contentsÔ×Ô = new ArrayList<String>();
    String expectedContentsÔ×Ô = "[Name: Danae Skarlatou, AFM: 123123123, Income: 12000.0, Basic Tax: 642.0, Tax Increase: 51.36, Total Tax: 693.36, TotalReceiptsGathered: 1, Entertainment: 300.0, Basic: 0.0, Travel: 0.0, Health: 0.0, Other: 0.0]";
    while ((line = br.readLine()) != null) {
      contentsÔ×Ô.add(line);
    }
    assertEquals(contentsÔ×Ô.toString(), expectedContentsÔ×Ô);
    
    br = new BufferedReader(new FileReader(TRN+"_LOG.xml"));
    ArrayList<String> contentsXML = new ArrayList<String>();
    String expectedContentsXML = "[<Name> Danae Skarlatou </Name>, <AFM> 123123123 </AFM>, <Income> 12000.0 </Income>, <BasicTax> 642.0 </BasicTax>, <TaxIncrease> 51.36 </TaxIncrease>, <TotalTax> 693.36 </TotalTax>, <Receipts> 1 </Receipts>, <Entertainment> 300.0 </Entertainment>, <Basic> 0.0 </Basic>, <Travel> 0.0 </Travel>, <Health> 0.0 </Health>, <Other> 0.0 </Other>]"
        + "";
    while ((line = br.readLine()) != null) {
      contentsXML.add(line);
    }
    assertEquals(contentsXML.toString(), expectedContentsXML);
    
    WrongFileFormatException thrown4 = Assertions.assertThrows(WrongFileFormatException.class, () -> {
      taxpayerManager.saveLogFile(TRN, "xmll");
    });
    Assertions.assertEquals("Please check your file format and try again!", thrown4.getMessage()); 

    //---removeTaxpayer
    assertEquals(1,taxpayerManager.getTaxpayerHashMap().size());
    taxpayerManager.removeTaxpayer(TRN);
    assertEquals(0,taxpayerManager.getTaxpayerHashMap().size());

    
  }
}
