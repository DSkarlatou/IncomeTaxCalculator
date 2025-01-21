package incometaxcalculator.data.io;

public class TXTFileReader extends FileReader {

  protected int checkForReceiptBasedOnFormat(String values[])
  {
    if (values[0].equals("Receipt")) 
      if (values[1].equals("ID:")) 
        return Integer.parseInt(values[2].trim());
    return -1;
  }
  
  protected String valueOfFieldBasedOnFormat(String fieldsLine)
  {
    String values[] = fieldsLine.split(" ", 2);
    return values[1].trim();
  }

}