package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileEndingException;

public class FileReaderFactory {

  public FileReader fileReader(String format) throws WrongFileEndingException
  {
    if (format.equals("txt")) return new TXTFileReader();
    if (format.equals("xml"))  return new XMLFileReader();  
    else  throw new WrongFileEndingException();
  }
  
}
