package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public abstract class InfoWriter implements FileWriter{
  abstract String[] infoTaxpayerContentsFormated(int taxRegistrationNumber);
  abstract String[] infoReceiptContentsFormated(Receipt receipt);
   
  public void generateFile(int taxRegistrationNumber) throws IOException {
    String[] infoContents = infoTaxpayerContentsFormated(taxRegistrationNumber);
    
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(infoContents[0]));
  
    outputStream.println(infoContents[1]);
    outputStream.println(infoContents[2]);
    outputStream.println(infoContents[3]);
    outputStream.println(infoContents[4]);
    outputStream.println();
    outputStream.println(infoContents[5]);
    outputStream.println();
    
    generateTaxpayerReceipts(taxRegistrationNumber, outputStream);
    outputStream.close();
  }
  
  private void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream) {
    TaxpayerManager manager = new TaxpayerManager();
    HashMap<Integer, Receipt> receiptsHashMap = manager.getReceiptHashMap(taxRegistrationNumber);
    Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
    while (iterator.hasNext()) {
      HashMap.Entry<Integer, Receipt> entry = iterator.next();
      Receipt receipt = entry.getValue();
      String[] infoContents = infoReceiptContentsFormated(receipt);
      
      for(int i = 0; i < infoContents.length; i++)
        outputStream.println(infoContents[i]);
      outputStream.println();
      
    }
  }
}
