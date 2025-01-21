package incometaxcalculator.data.io;

import java.io.File;


public class InfoWriterFactory {

  public InfoWriter infoWriter(int taxRegistrationNumber) 
  {
    if(new File(taxRegistrationNumber + "_INFO.txt").exists()) 
      return new TXTInfoWriter();
    else if(new File(taxRegistrationNumber + "_INFO.xml").exists()) 
      return new XMLInfoWriter();
    else return new TXTInfoWriter();
  }
  
}
