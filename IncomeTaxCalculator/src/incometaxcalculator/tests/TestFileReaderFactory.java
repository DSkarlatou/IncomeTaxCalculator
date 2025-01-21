package incometaxcalculator.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import incometaxcalculator.data.io.FileReader;
import incometaxcalculator.data.io.FileReaderFactory;
import incometaxcalculator.data.io.TXTFileReader;
import incometaxcalculator.data.io.XMLFileReader;
import incometaxcalculator.exceptions.WrongFileEndingException;

class TestFileReaderFactory {

  @Test
  void test() throws WrongFileEndingException{
    FileReaderFactory readerFactory = new FileReaderFactory();
    FileReader reader = readerFactory.fileReader("txt");
    TXTFileReader txt = new TXTFileReader();
    Assertions.assertEquals(txt.getClass(), reader.getClass());
    
    reader = readerFactory.fileReader("xml");
    XMLFileReader xml = new XMLFileReader();
    Assertions.assertEquals(xml.getClass(), reader.getClass());
    
    WrongFileEndingException thrown1 = Assertions.assertThrows(WrongFileEndingException.class, () -> {
      FileReader readerW = readerFactory.fileReader("pdf");
    });
    Assertions.assertEquals("Please check your file ending and try again!", thrown1.getMessage());
    
  }

}
