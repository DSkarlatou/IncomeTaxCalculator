package incometaxcalculator.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import incometaxcalculator.data.management.Company;
import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.SingleTaxpayer;
import incometaxcalculator.data.management.Taxpayer;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;

class TestSingleTaxpayer {

  @Test
  void test() throws WrongReceiptKindException, WrongReceiptDateException {
    //---addReceipt
    String name = "Kitty Hart";
    int TRN = 111222333;
    float income = 200000;
    
    Company company = new Company("Young Heretics", " Australia", "Melbourne", "Kitty", 1);
    Taxpayer taxpayer = new SingleTaxpayer(name, TRN, income);
    Receipt receipt = new Receipt(1, "20/01/22", 8000, "Travel", company);
    
    taxpayer.addReceipt(receipt);
    WrongReceiptKindException thrown1 = Assertions.assertThrows(WrongReceiptKindException.class, () -> {
      taxpayer.addReceipt(new Receipt(1, "20/01/22", 2000, "HELLO", company));
    });
    Assertions.assertEquals("Please check receipts kind and try again.", thrown1.getMessage());
    
    taxpayer.addReceipt(new Receipt(2, "20/01/22", 2000, "Basic", company));
    taxpayer.addReceipt(new Receipt(3, "20/01/22", 2000, "Health", company));
    taxpayer.addReceipt(new Receipt(4, "20/01/22", 2000, "Entertainment", company));
    taxpayer.addReceipt(new Receipt(5, "20/01/22", 1000, "Other", company));
    taxpayer.addReceipt(new Receipt(6, "20/01/22", 5000, "Travel", company));
    taxpayer.addReceipt(new Receipt(6, "20/01/22", 80000, "Entertainment", company));

    //---calculateBasicTax
    double tax = Math.round(taxpayer.calculateBasicTax() * 100.0) / 100.0;
    assertEquals(15581, tax);
    
    //---getVariationTaxOnReceipts()
    double variationTax = taxpayer.getVariationTaxOnReceipts();
    assertEquals(-2337.15, variationTax);

    //---getTotalTax
    assertEquals(15581-2337.15, variationTax+tax);
  }

}
