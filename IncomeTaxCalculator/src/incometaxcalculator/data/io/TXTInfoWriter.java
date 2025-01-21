package incometaxcalculator.data.io;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public class TXTInfoWriter extends InfoWriter implements FileWriter {
  
  TaxpayerManager manager = new TaxpayerManager();

  protected String[] infoTaxpayerContentsFormated(int taxRegistrationNumber)
  {
    String[] infoContents = new String[6];
    
    infoContents[0] = taxRegistrationNumber + "_INFO.txt";
    infoContents[1] = "Name: " + manager.getTaxpayerName(taxRegistrationNumber);
    infoContents[2] = "AFM: " + taxRegistrationNumber;
    infoContents[3] = "Status: " + manager.getTaxpayerStatus(taxRegistrationNumber);  
    infoContents[4] = "Income: " + manager.getTaxpayerIncome(taxRegistrationNumber);
    infoContents[5] = "Receipts:";
    
    return infoContents;
  }
  
  protected String[] infoReceiptContentsFormated(Receipt receipt)
  {
    String[] infoContents = new String[9];
    
    infoContents[0] = "Receipt ID: " + receipt.getId();
    infoContents[1] = "Date: " + receipt.getIssueDate();
    infoContents[2] = "Kind: " + receipt.getKind();
    infoContents[3] = "Amount: " + receipt.getAmount();
    infoContents[4] = "Company: " + receipt.getCompany().getName();
    infoContents[5] = "Country: " + receipt.getCompany().getCountry();
    infoContents[6] = "City: " + receipt.getCompany().getCity();
    infoContents[7] = "Street: " + receipt.getCompany().getStreet();
    infoContents[8] = "Number: " + receipt.getCompany().getNumber();
    
    return infoContents;
  }


  
  


}