package incometaxcalculator.data.io;

import incometaxcalculator.data.management.TaxpayerManager;

public class XMLLogWriter extends LogWriter implements FileWriter {

  TaxpayerManager manager = new TaxpayerManager();

  private static final short ENTERTAINMENT = 0;
  private static final short BASIC = 1;
  private static final short TRAVEL = 2;
  private static final short HEALTH = 3;
  private static final short OTHER = 4;

  protected String[] logContentsFormatted(int taxRegistrationNumber)
  {
    String[] logContents = new String[14];
    logContents[0] = taxRegistrationNumber + "_LOG.xml";
    logContents[1] = "<Name> " + manager.getTaxpayerName(taxRegistrationNumber) + " </Name>";
    logContents[2] = "<AFM> " + taxRegistrationNumber + " </AFM>";
    logContents[3] = "<Income> " + manager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>";
    logContents[4] = "<BasicTax> " + manager.getTaxpayerBasicTax(taxRegistrationNumber) + " </BasicTax>";
    logContents[5] = "<TaxIncrease> "+ manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + " </TaxIncrease>";
    logContents[6] = "<TaxDecrease> "+ manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + " </TaxDecrease>";
    logContents[7] = "<TotalTax> " + manager.getTaxpayerTotalTax(taxRegistrationNumber) + " </TotalTax>";
    logContents[8] = "<Receipts> " + manager.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber) + " </Receipts>";
    logContents[9] = "<Entertainment> " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT) + " </Entertainment>";
    logContents[10] = "<Basic> " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC) + " </Basic>";    
    logContents[11] = "<Travel> " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL) + " </Travel>";
    logContents[12] = "<Health> " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH) + " </Health>";
    logContents[13] = "<Other> " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER) + " </Other>";
    
    return logContents;
  }
  

}
