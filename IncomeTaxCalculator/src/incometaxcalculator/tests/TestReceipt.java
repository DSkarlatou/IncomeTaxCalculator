package incometaxcalculator.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import incometaxcalculator.data.management.Company;
import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;

class TestReceipt {

  @Test
  void test() throws WrongReceiptDateException {
    Company company = new Company("The Getaway Plan", " Australia", "Melbourne", "Mitty", 1);
    Receipt receipt = new Receipt(1,"12/12/20",50,"Other",company);
    
    WrongReceiptDateException thrown1 = Assertions.assertThrows(WrongReceiptDateException.class, () -> {
      Receipt receipt1 = new Receipt(1,"12-12-20",50,"Other",company);
    });
    Assertions.assertEquals("Please make sure your date is DD/MM/YYYY and try again.", thrown1.getMessage());
    
    WrongReceiptDateException thrown2 = Assertions.assertThrows(WrongReceiptDateException.class, () -> {
      Receipt receipt2 = new Receipt(1,"12 12 20",50,"Other",company);
    });
    Assertions.assertEquals("Please make sure your date is DD/MM/YYYY and try again.", thrown2.getMessage());
    
    WrongReceiptDateException thrown3 = Assertions.assertThrows(WrongReceiptDateException.class, () -> {
      Receipt receipt3 = new Receipt(1,"-12-20",50,"Other",company);
    });
    Assertions.assertEquals("Please make sure your date is DD/MM/YYYY and try again.", thrown3.getMessage());
   
    WrongReceiptDateException thrown4 = Assertions.assertThrows(WrongReceiptDateException.class, () -> {
      Receipt receipt4 = new Receipt(1,"1",50,"Other",company);
    });
    Assertions.assertEquals("Please make sure your date is DD/MM/YYYY and try again.", thrown4.getMessage());
    
  }

}
