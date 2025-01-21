package incometaxcalculator.data.io;

import incometaxcalculator.data.management.TaxpayerManager;

public class TXTLogWriter extends LogWriter implements FileWriter {
  
  private TaxpayerManager manager = new TaxpayerManager();
  
  private static final short ENTERTAINMENT = 0;
  private static final short BASIC = 1;
  private static final short TRAVEL = 2;
  private static final short HEALTH = 3;
  private static final short OTHER = 4;

  protected String[] logContentsFormatted(int taxRegistrationNumber)
  {
    String[] logContents = new String[14];
    logContents[0] = taxRegistrationNumber + "_LOG.txt";
    logContents[1] = "Name: " + manager.getTaxpayerName(taxRegistrationNumber);
    logContents[2] = "AFM: " + taxRegistrationNumber;
    logContents[3] = "Income: " + manager.getTaxpayerIncome(taxRegistrationNumber);
    logContents[4] = "Basic Tax: " + manager.getTaxpayerBasicTax(taxRegistrationNumber);
    logContents[5] = "Tax Increase: " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber);
    logContents[6] = "Tax Decrease: " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber);
    logContents[7] = "Total Tax: " + manager.getTaxpayerTotalTax(taxRegistrationNumber);
    logContents[8] = "TotalReceiptsGathered: " + manager.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber);
    logContents[9] = "Entertainment: " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT);
    logContents[10] = "Basic: " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC);    
    logContents[11] = "Travel: " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL);
    logContents[12] = "Health: " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH);
    logContents[13] = "Other: " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER);
    
    return logContents;
  }


}
