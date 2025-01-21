/* Danae Skarlatou, 2908 */

package incometaxcalculator.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;

import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GUI {

  private JFrame frame;
  private TaxpayerManager taxpayerManager = new TaxpayerManager();
  private DefaultListModel<String> taxRegisterNumberModel = new DefaultListModel<String>();
  private List<String> taxpayersLoaded = new ArrayList<String>();
  private JList TRNs;
  
  public static void main(String[] args) {
    UIManager.put("nimbusBase", new Color(150, 40,40));
    UIManager.put("nimbusBlueGrey", new Color(120, 170, 170));
    UIManager.put("control", new Color(255,255,204));
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          UIManager.setLookAndFeel ("javax.swing.plaf.nimbus.NimbusLookAndFeel");
          UIManager.put("TextField.background", Color.WHITE);
          UIManager.put("TextField.border", BorderFactory.createCompoundBorder(
                  new CustomeBorder(), 
                  new EmptyBorder(new Insets(4,4,4,4))));

          GUI window = new GUI();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public GUI() {
    initialize();
  }

  private void initialize() {
    frame = new JFrame();    
    frame.setResizable(false);
    frame.getContentPane().setBackground(new Color(255,255,204));
    frame.setBounds(100, 100, 438, 506);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    frame.setTitle("Income Tax Calculator");
    ImageIcon img = new ImageIcon("tax_icon.png");
    frame.setIconImage(img.getImage());

    JLabel lblNewLabel = new JLabel("Tax Registration Number:");
    lblNewLabel.setBounds(10, 0, 240, 25);
    lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
    lblNewLabel.setForeground(new Color(25, 25, 132));
    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
    frame.getContentPane().add(lblNewLabel);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBounds(10, 23, 240, 433);
    frame.getContentPane().add(scrollPane);
    
    TRNs = new JList(taxRegisterNumberModel);
    TRNs.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
    TRNs.setBorder(new LineBorder(new Color(0, 0, 0)));
    TRNs.setVisibleRowCount(50);
    TRNs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    TRNs.setBackground(new Color(204,255,255));
    scrollPane.setViewportView(TRNs);
    
    JButton btnSelect = new JButton("SELECT TAXPAYER");
    btnSelect.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
    btnSelect.setBackground(new Color(170, 250, 230));
    btnSelect.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {selectTaxpayerAction();}});
    btnSelect.setBounds(259, 280, 151, 40);
    frame.getContentPane().add(btnSelect);
    
    TRNs.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
          JList list = (JList)evt.getSource();
          if (evt.getClickCount() == 2) {
              int index = list.locationToIndex(evt.getPoint());
              int trn = Integer.parseInt((String) TRNs.getSelectedValue());
              String filename = trn + "_INFO.txt";
              File f = new File(filename);
              if(!f.exists()) { 
                  filename = trn + "_INFO.xml";
              }
              
              try {
                selectTaxpayer(filename, trn);
              } catch (NumberFormatException | IOException | WrongFileFormatException
                  | WrongFileEndingException | WrongTaxpayerStatusException | WrongReceiptKindException
                  | WrongReceiptDateException e1) {
                e1.printStackTrace();
              }
          }
      }
  });
    
    JButton btnDeleteTaxpayer = new JButton("DELETE TAXPAYER");
    btnDeleteTaxpayer.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
    btnDeleteTaxpayer.setBackground(new Color(170, 250, 230));
    btnDeleteTaxpayer.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {deleteTaxpayerAction();}});
    btnDeleteTaxpayer.setBounds(259, 331, 151, 40);
    frame.getContentPane().add(btnDeleteTaxpayer);
    
    JButton btnLoad = new JButton("LOAD TAXPAYER");
    btnLoad.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
    btnLoad.setBackground(new Color(170, 250, 230));
    btnLoad.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) { loadSpecificAction();}});
    btnLoad.setBounds(258, 159, 151, 40);
    frame.getContentPane().add(btnLoad);
    
    JButton btnLoadAll = new JButton("LOAD ALL");
    btnLoadAll.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
    btnLoadAll.setBackground(new Color(170, 250, 230));
    btnLoadAll.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {loadAllAction();}});
    btnLoadAll.setBounds(258, 210, 152, 40);
    frame.getContentPane().add(btnLoadAll);
    
    JTextField textField = createTextField();
    textField.setBounds(260, 42, 151, 31);
    frame.getContentPane().add(textField);
    textField.setColumns(10);
    
    JLabel lblSearchTaxpayer = new JLabel("SEARCH TAXPAYER :");
    lblSearchTaxpayer.setHorizontalAlignment(SwingConstants.CENTER);
    lblSearchTaxpayer.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
    lblSearchTaxpayer.setBounds(260, 23, 152, 14);
    frame.getContentPane().add(lblSearchTaxpayer);
    
    JButton btnCreateTaxpayer = new JButton("CREATE TAXPAYER");
    btnCreateTaxpayer.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) { createTaxpayerAction();}});
    btnCreateTaxpayer.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
    btnCreateTaxpayer.setBackground(new Color(170, 250, 230));
    btnCreateTaxpayer.setBounds(260, 93, 151, 40);
    frame.getContentPane().add(btnCreateTaxpayer);
    

  }
  
  private JTextField createTextField() {
    final JTextField field = new JTextField(15);
    
    field.getDocument().addDocumentListener(new DocumentListener(){
        @Override public void insertUpdate(DocumentEvent e) { filter(); }
        @Override public void removeUpdate(DocumentEvent e) { filter(); }
        @Override public void changedUpdate(DocumentEvent e) {}
        private void filter() {
            String filter = field.getText();
            filterModel(taxRegisterNumberModel, filter);
        }
    });

    return field;
}

  public void filterModel(DefaultListModel<String> model, String filter) {
    for (String s : taxpayersLoaded) {
        if (!s.startsWith(filter)) {
            if (model.contains(s)) {
                model.removeElement(s);
            }
        } else {
            if (!model.contains(s)) {
                model.addElement(s);
            }
        }
    }
  }

  public void selectTaxpayerAction()
  {
    if(TRNs.getSelectedValue() == null)
      return;
    int trn = Integer.parseInt((String) TRNs.getSelectedValue());
    String filename = trn + "_INFO.txt";
    File f = new File(filename);
    if(!f.exists()) { 
        filename = trn + "_INFO.xml";
    }
    
    try {
      selectTaxpayer(filename, trn);
    } catch (NumberFormatException | IOException | WrongFileFormatException
        | WrongFileEndingException | WrongTaxpayerStatusException | WrongReceiptKindException
        | WrongReceiptDateException e1) {
      e1.printStackTrace();
    }
  }
  
  public void deleteTaxpayerAction()
  {
    if (taxpayerManager.containsTaxpayer()) {
      if(TRNs.getSelectedValue() == null)
        return;
      String trn = TRNs.getSelectedValue().toString();
      int taxRegistrationNumber;
      int n = JOptionPane.showConfirmDialog(
          frame, 
          "Are you sure you wish to delete taxpayer :" + trn +"?",
          "DELETE TAXPAYER",
          JOptionPane.YES_NO_OPTION);
      if(n == 0)
      {
        try {
          taxRegistrationNumber = Integer.parseInt(trn);
          if (taxpayerManager.containsTaxpayer(taxRegistrationNumber)) {
            taxpayerManager.removeTaxpayer(taxRegistrationNumber);
            taxRegisterNumberModel.removeElement(trn);
            taxpayersLoaded.remove(trn);
          }
        } catch (NumberFormatException e) {

        }
      }

    } else {
      JOptionPane.showMessageDialog(null,
          "There isn't any taxpayer loaded. Please load one first.");
    }
  }
  
  public void loadSpecificAction()
  {
    JFileChooser chooser = new JFileChooser();
    
    FileFilter infoTXT = new FileFilter() {
      public boolean accept(File f) {
        if (f.isDirectory() || f.getName().endsWith("_INFO.txt")) 
          return true;
        return false;
      }

      public String getDescription(){
          return "TXT FILES";
      }
    };
    
    FileFilter infoXML = new FileFilter() {
      public boolean accept(File f) {
        if (f.isDirectory() || f.getName().endsWith("_INFO.xml")) 
          return true;
        return false;
      }

      public String getDescription(){
          return "XML FILES";
      }
    };
    
    FileFilter infoALL = new FileFilter() {
      public boolean accept(File f) {
        if (f.isDirectory() || f.getName().endsWith("_INFO.xml") || f.getName().endsWith("_INFO.txt")) 
          return true;
        return false;
      }

      public String getDescription(){
          return "ALL FILES";
      }
    };

    File f = null;
    try {
      f = new File(new File(".").getCanonicalPath());
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    chooser.setAcceptAllFileFilterUsed(false);
    chooser.setFileFilter(infoXML);
    chooser.setFileFilter(infoTXT);
    chooser.setFileFilter(infoALL);   
    chooser.setCurrentDirectory(f);

    int result = chooser.showOpenDialog(frame);   
    if (result == JFileChooser.APPROVE_OPTION) {
      f = chooser.getSelectedFile();
      String fileName = f.getName();
      String tail = fileName.substring(9, fileName.length() - 4);
      if(tail.equals("_LOG"))
        return;
      String taxRegistrationNumber = fileName.substring(0, 9);
      int trn = Integer.parseInt(taxRegistrationNumber);

      if (taxpayerManager.containsTaxpayer(trn)) {
        JOptionPane.showMessageDialog(null, "This taxpayer is already loaded.");
      } else {
        try {
          taxpayerManager.loadTaxpayer(fileName);
        } catch (NumberFormatException e1) {
          JOptionPane.showMessageDialog(null,
              "The tax registration number must have only digits.");              
               e1.printStackTrace();
        } catch (IOException e1) {
          JOptionPane.showMessageDialog(null, "The file doesn't exists.");
        } catch (WrongFileFormatException e1) {
          JOptionPane.showMessageDialog(null, "Please check your file format and try again.");
        } catch (WrongFileEndingException e1) {
          JOptionPane.showMessageDialog(null, "Please check your file ending and try again.");
        } catch (WrongTaxpayerStatusException e1) {
          JOptionPane.showMessageDialog(null, "Please check taxpayer's status and try again.");
        } catch (WrongReceiptKindException e1) {
          JOptionPane.showMessageDialog(null, "Please check receipts kind and try again.");
        } catch (WrongReceiptDateException e1) {
          JOptionPane.showMessageDialog(null,
              "Please make sure your date is " + "DD/MM/YYYY and try again.");
        }
        taxRegisterNumberModel.addElement(taxRegistrationNumber);
        taxpayersLoaded.add(taxRegistrationNumber);
      } 
    }
  }
  
  public void loadAllAction()
  {
    File folder = null;
    try {
      folder = new File(new File(".").getCanonicalPath());
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    
    File[] listOfFiles = folder.listFiles();

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        int len = listOfFiles[i].getName().length();
        if(len == 18)
        {
          String tail = listOfFiles[i].getName().substring(9, len - 4);
          if(tail.equals("_INFO"))
          {
            String taxRegistrationNumber = listOfFiles[i].getName().substring(0, 9);
            int trn = Integer.parseInt(taxRegistrationNumber);

            if (!taxpayerManager.containsTaxpayer(trn)) {
                try {
                  taxpayerManager.loadTaxpayer(listOfFiles[i].getName());
                } catch (NumberFormatException | IOException | WrongFileFormatException
                    | WrongFileEndingException | WrongTaxpayerStatusException
                    | WrongReceiptKindException | WrongReceiptDateException e1) {
                  e1.printStackTrace();
                }
              taxRegisterNumberModel.addElement(taxRegistrationNumber);
              taxpayersLoaded.add(taxRegistrationNumber);
          }
        }   
      } 
    }
  }
  }
  
  public void createTaxpayerAction()
  {
    JPanel taxpayerImporterPanel = new JPanel(new GridLayout(4, 2));
    JTextField name = new JTextField(30);
    JTextField income = new JTextField(16);
    JTextField TRN = new JTextField(10);
    int createdTaxRegistrationNumber = 0;
    String createdTaxpayerName = null;
    float createdIncome = 0;
    taxpayerImporterPanel.add(new JLabel("Name : "));
    taxpayerImporterPanel.add(name);
    taxpayerImporterPanel.add(new JLabel("Tax Registration Number : "));
    taxpayerImporterPanel.add(TRN);
    taxpayerImporterPanel.add(new JLabel("Income : "));
    taxpayerImporterPanel.add(income);
    Object[] possibilities = {"Married Filing Jointly", "Married Filing Separately", "Single", "Head of Household"};

    String status = (String)JOptionPane.showInputDialog(
        frame,
        taxpayerImporterPanel,
        "Create Taxpayer",
        JOptionPane.PLAIN_MESSAGE,
        null,
        possibilities,
        "Single");
    
    if(status != null)
    {
      if(name.getText().isEmpty() || income.getText().isEmpty() || TRN.getText().isEmpty())
      {
        JOptionPane.showMessageDialog(null, "Please fill all the fields in the form", "EMPTY FIELD",JOptionPane.OK_OPTION);
        return;
      }
      
      createdTaxpayerName = name.getText();
      
      try {
        createdIncome = Float.parseFloat(income.getText());
        createdTaxRegistrationNumber = Integer.parseInt(TRN.getText());  
      } catch (NumberFormatException e) {
        
        JOptionPane.showMessageDialog(null, "Income and Tax Registration Number must not contain characters. Try again", "WRONG FIELD", JOptionPane.OK_OPTION);
      }
      
      int len = (createdTaxRegistrationNumber+"").length();
      if(len != 9) return;
      if (taxpayerManager.containsTaxpayer(createdTaxRegistrationNumber)) {
        int op2 = JOptionPane.showConfirmDialog(null, "This taxpayer is already loaded. Do you wish to update their file?",
            "ALREADY LOADED", JOptionPane.OK_CANCEL_OPTION);
        if(op2 == 0)
        {
          try {
            taxpayerManager.createTaxpayer(createdTaxpayerName, createdTaxRegistrationNumber, status, createdIncome);
            taxpayerManager.updateFiles(createdTaxRegistrationNumber);                 
          } catch (WrongTaxpayerStatusException e1) {
            e1.printStackTrace();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
          return;
        }   
      } 
      
      if(new File(createdTaxRegistrationNumber + "_INFO.txt").exists() || 
         new File(createdTaxRegistrationNumber + "_INFO.xml").exists() )
      {
        int op2 = JOptionPane.showConfirmDialog(null, "Taxpayer already exists, do you want to proceed?", "TAXPAYER EXISTS",
            JOptionPane.OK_CANCEL_OPTION);
            if(op2 != 0) return;  
      }
        try {
          File f = new File(createdTaxRegistrationNumber+"_INFO.txt");
          if(!f.exists()) { 
            f.createNewFile();
          }
          
          taxpayerManager.createTaxpayer(createdTaxpayerName, createdTaxRegistrationNumber, status, createdIncome);
          taxpayerManager.updateFiles(createdTaxRegistrationNumber);
          taxpayerManager.loadTaxpayer(createdTaxRegistrationNumber+"_INFO.txt");
        } catch (NumberFormatException | IOException | WrongFileFormatException
            | WrongFileEndingException | WrongTaxpayerStatusException
            | WrongReceiptKindException | WrongReceiptDateException e1) {
          e1.printStackTrace();
        }
        taxRegisterNumberModel.addElement(createdTaxRegistrationNumber+"");
        taxpayersLoaded.add(createdTaxRegistrationNumber+"");
      }    
  }
  
  void selectTaxpayer(String taxRegistrationNumberFile, int trn) throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException
  {
    if (taxpayerManager.containsTaxpayer()) {
      
        try {
          if (taxpayerManager.containsTaxpayer(trn)) {
            TaxpayerData taxpayerData = new TaxpayerData(trn, taxpayerManager);
            taxpayerData.setVisible(true);
          } else {
            JOptionPane.showMessageDialog(null, "This tax registration number isn't loaded.");
          }
        } 
        catch (NumberFormatException e1) {
          JOptionPane.showMessageDialog(null, "You must give a tax registation number.");
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      
    } else {
      JOptionPane.showMessageDialog(null,
          "There isn't any taxpayer loaded. Please load one first.");
    }
  }
  
  @SuppressWarnings("serial")
  public static class CustomeBorder extends AbstractBorder{
      @Override
      public void paintBorder(Component c, Graphics g, int x, int y,
              int width, int height) {
          super.paintBorder(c, g, x, y, width, height);
          Graphics2D g2d = (Graphics2D)g;
          Shape shape = new RoundRectangle2D.Float(0, 0, c.getWidth()-1, c.getHeight()-1,9, 9);
          g2d.draw(shape);
      }
  }
}
