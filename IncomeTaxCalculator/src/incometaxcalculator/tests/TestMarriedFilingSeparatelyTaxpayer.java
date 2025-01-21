package incometaxcalculator.tests;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import incometaxcalculator.data.management.Company;
import incometaxcalculator.data.management.MarriedFilingSeparatelyTaxpayer;
import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.Taxpayer;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;

class TestMarriedFilingSeparatelyTaxpayer {

  @Test //MarriedFilingSeparatelyTaxpayer
  void test() throws  WrongReceiptKindException, WrongReceiptDateException {
    //---addReceipt
    String name = "Matthew Wright";
    int TRN = 111222333;
    float income = 50000;
    
    Company company = new Company("The Getaway Plan", " Australia", "Melbourne", "Kitty", 1);
    MarriedFilingSeparatelyTaxpayer taxpayer = new MarriedFilingSeparatelyTaxpayer(name, TRN, income);
    Receipt receipt = new Receipt(1, "20/01/22", 2000, "Travel", company);
    
    taxpayer.addReceipt(receipt);
    WrongReceiptKindException thrown1 = Assertions.assertThrows(WrongReceiptKindException.class, () -> {
      taxpayer.addReceipt(new Receipt(1, "20/01/22", 2000, "HELLO", company));
    });
    Assertions.assertEquals("Please check receipts kind and try again.", thrown1.getMessage());
    
    taxpayer.addReceipt(new Receipt(2, "20/01/22", 211, "Basic", company));
    taxpayer.addReceipt(new Receipt(3, "20/01/22", 50, "Health", company));
    taxpayer.addReceipt(new Receipt(4, "20/01/22", 3000, "Entertainment", company));
    taxpayer.addReceipt(new Receipt(5, "20/01/22", 1231, "Other", company));
    taxpayer.addReceipt(new Receipt(6, "20/01/22", 1231, "Travel", company));

    assertEquals(3000, taxpayer.getAmountOfReceiptKind((short) 0));
    assertEquals(211, taxpayer.getAmountOfReceiptKind((short) 1));
    assertEquals(3231, taxpayer.getAmountOfReceiptKind((short) 2));
    assertEquals(50, taxpayer.getAmountOfReceiptKind((short) 3));
    assertEquals(1231, taxpayer.getAmountOfReceiptKind((short) 4));
    assertEquals(6, taxpayer.getTotalReceiptsGathered());
    
    //---removeReceipt
    taxpayer.removeReceipt(3);
    assertEquals(3000, taxpayer.getAmountOfReceiptKind((short) 0));
    assertEquals(211, taxpayer.getAmountOfReceiptKind((short) 1));
    assertEquals(3231, taxpayer.getAmountOfReceiptKind((short) 2));
    assertEquals(0, taxpayer.getAmountOfReceiptKind((short) 3));
    assertEquals(1231, taxpayer.getAmountOfReceiptKind((short) 4)); 
    assertEquals(5, taxpayer.getTotalReceiptsGathered());
    
    NullPointerException thrown2 = Assertions.assertThrows(NullPointerException.class, () -> {
      taxpayer.removeReceipt(3);
    });
    Assertions.assertEquals("Cannot invoke \"incometaxcalculator.data.management."
        + "Receipt.getAcceptableReceiptKinds()\" because \"receipt\" is null", 
            thrown2.getMessage());

    
    //---calculateBasicTax
    double tax = Math.round(taxpayer.calculateBasicTax() * 100.0) / 100.0;
    assertEquals(3218.32, tax);
    
    //---getVariationTaxOnReceipts()
    double variationTax = taxpayer.getVariationTaxOnReceipts();
    assertEquals(257.4656, variationTax);

    //---getTotalTax
    assertEquals(257.4656+3218.32, variationTax+tax);
    
  }
 


}
