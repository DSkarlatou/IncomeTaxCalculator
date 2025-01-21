package incometaxcalculator.data.io;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public class XMLInfoWriter extends InfoWriter implements FileWriter{
  
  private TaxpayerManager manager = new TaxpayerManager();
  
  protected String[] infoTaxpayerContentsFormated(int taxRegistrationNumber)
  {
    String[] infoContents = new String[6];
    
    infoContents[0] = taxRegistrationNumber + "_INFO.xml";
    infoContents[1] = "<Name> " + manager.getTaxpayerName(taxRegistrationNumber) + " </Name>";
    infoContents[2] = "<AFM> " + taxRegistrationNumber + " </AFM>";
    infoContents[3] = "<Status> " + manager.getTaxpayerStatus(taxRegistrationNumber) + " </Status>";  
    infoContents[4] = "<Income> " + manager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>";
    infoContents[5] = "<Receipts>";
    
    return infoContents;
  }
  
  protected String[] infoReceiptContentsFormated(Receipt receipt)
  {
    String[] infoContents = new String[9];
    
    infoContents[0] = "<ReceiptID> " + receipt.getId() + " </ReceiptID>";
    infoContents[1] = "<Date> " + receipt.getIssueDate() + " </Date>";
    infoContents[2] = "<Kind> " + receipt.getKind() + " </Kind>";
    infoContents[3] = "<Amount> " + receipt.getAmount() + " </Amount>";
    infoContents[4] = "<Company> " + receipt.getCompany().getName() + " </Company>";
    infoContents[5] = "<Country> " + receipt.getCompany().getCountry() + " </Country>";
    infoContents[6] = "<City> " + receipt.getCompany().getCity() + " </City>";
    infoContents[7] = "<Street> " + receipt.getCompany().getStreet() + " </Street>";
    infoContents[8] = "<Number> " + receipt.getCompany().getNumber() + " </Number>";
    
    return infoContents;
  }


}