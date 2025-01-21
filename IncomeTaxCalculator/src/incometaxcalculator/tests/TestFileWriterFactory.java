package incometaxcalculator.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import incometaxcalculator.data.io.FileWriter;
import incometaxcalculator.data.io.LogWriterFactory;
import incometaxcalculator.data.io.TXTLogWriter;
import incometaxcalculator.data.io.XMLLogWriter;
import incometaxcalculator.exceptions.WrongFileFormatException;

class TestFileWriterFactory {

  @Test
  void test() throws WrongFileFormatException {
    LogWriterFactory factory = new LogWriterFactory();
    FileWriter writer = factory.logWriter("txt");
    TXTLogWriter txt = new TXTLogWriter();
    Assertions.assertEquals(txt.getClass(), writer.getClass());   
    
    writer = factory.logWriter("xml");
    XMLLogWriter xml = new XMLLogWriter();
    Assertions.assertEquals(xml.getClass(), writer.getClass());   
    
    WrongFileFormatException thrown1 = Assertions.assertThrows(WrongFileFormatException.class, () -> {
      FileWriter writerW = factory.logWriter("pdf");
    });
    Assertions.assertEquals("Please check your file format and try again!", thrown1.getMessage());
    

  
  }

}
