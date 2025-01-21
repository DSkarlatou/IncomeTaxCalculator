package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;

import incometaxcalculator.data.management.TaxpayerManager;

public abstract class LogWriter implements FileWriter{
  abstract String[] logContentsFormatted(int taxRegistrationNumber);
  
  public void generateFile(int taxRegistrationNumber) throws IOException {
    TaxpayerManager manager = new TaxpayerManager();
    
    String[] logContents = logContentsFormatted(taxRegistrationNumber);
    
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(logContents[0]));
    
    for(int i = 1; i < 5; i++)
      outputStream.println(logContents[i]);
    
    if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0) 
      outputStream.println(logContents[5]);
    else 
      outputStream.println(logContents[6]);
    
    for(int i = 7; i < 14; i++)
      outputStream.println(logContents[i]);
    
    outputStream.close();
  }
}
