package incometaxcalculator.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;


import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;


public class TaxpayerData {

  private JFrame frame;
  private static final short ENTERTAINMENT = 0;
  private static final short BASIC = 1;
  private static final short TRAVEL = 2;
  private static final short HEALTH = 3;
  private static final short OTHER = 4;
  
  private JLabel lblTotalTaxDisplay;
  private JLabel lblTaxVariationDisplay;
  private JLabel lblReceiptsDisplay;
  
  private DecimalFormat f = new DecimalFormat("##.00");
  
  public TaxpayerData(int taxRegistrationNumber, TaxpayerManager taxpayerManager) {
    frame = new JFrame();
    frame.setResizable(false);
    frame.setTitle("Taxpayer : " + taxRegistrationNumber);
    frame.setBounds(100, 100, 444, 545);
    frame.getContentPane().setLayout(null);
    frame.getContentPane().setBackground(new Color(255,255,204));
    JLabel lblName = new JLabel("Name : ");
    lblName.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    lblName.setBounds(10, 12, 102, 24);
    lblName.setBackground(new Color(220, 240, 240));
    lblName.setOpaque(true);
    frame.getContentPane().add(lblName);
    
    JLabel lblTrn = new JLabel("TRN :");
    lblTrn.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    lblTrn.setBounds(10, 48, 102, 24);
    lblTrn.setBackground(new Color(220, 240, 240));
    lblTrn.setOpaque(true);
    frame.getContentPane().add(lblTrn);
    
    JLabel lblStatus = new JLabel("Status :");
    lblStatus.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    lblStatus.setBounds(10, 84, 102, 24);
    lblStatus.setBackground(new Color(220, 240, 240));
    lblStatus.setOpaque(true);
    frame.getContentPane().add(lblStatus);
    
    JLabel lblIncome = new JLabel("Income :");
    lblIncome.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    lblIncome.setBounds(10, 120, 102, 24);
    lblIncome.setBackground(new Color(220, 240, 240));
    lblIncome.setOpaque(true);
    frame.getContentPane().add(lblIncome);
    
    JLabel lblBasicTax = new JLabel("Basic Tax :");
    lblBasicTax.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    lblBasicTax.setBounds(10, 156, 102, 24);
    lblBasicTax.setBackground(new Color(220, 240, 240));
    lblBasicTax.setOpaque(true);
    frame.getContentPane().add(lblBasicTax);
    
    JLabel lblTotalTax = new JLabel("Total Tax :");
    lblTotalTax.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    lblTotalTax.setOpaque(true);
    lblTotalTax.setBackground(new Color(220, 240, 240));
    lblTotalTax.setBounds(10, 191, 102, 24);
    frame.getContentPane().add(lblTotalTax);
    
    JLabel lblTaxVariation = new JLabel("Tax Variation :");
    lblTaxVariation.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    lblTaxVariation.setOpaque(true);
    lblTaxVariation.setBackground(new Color(220, 240, 240));
    lblTaxVariation.setBounds(10, 226, 102, 24);
    frame.getContentPane().add(lblTaxVariation);
    
    JLabel lblReceipts = new JLabel("Receipt count : ");
    lblReceipts.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    lblReceipts.setBounds(10, 260, 102, 24);
    lblReceipts.setBackground(new Color(220, 240, 240));
    lblReceipts.setOpaque(true);
    frame.getContentPane().add(lblReceipts);
    
    JLabel lblReceiptIds = new JLabel("Receipts :");
    lblReceiptIds.setHorizontalAlignment(SwingConstants.CENTER);
    lblReceiptIds.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
    lblReceiptIds.setBounds(164, 294, 102, 24);
    lblReceiptIds.setOpaque(true);
    frame.getContentPane().add(lblReceiptIds);
    
    HashMap<Integer, Receipt> receipts = taxpayerManager.getReceiptHashMap(taxRegistrationNumber);
    
    DefaultTableModel model = new DefaultTableModel();
    JTable table_1 = new JTable(model); 
    model.addColumn("ID"); 
    model.addColumn("AMOUNT"); 
    model.addColumn("KIND"); 
    model.addColumn("DATE"); 
    model.addColumn("COMPANY"); 
    table_1.setBounds(303, 306, 186, 50);
    table_1.setDefaultEditor(Object.class, null);
    table_1.setRowSelectionAllowed(true);
    table_1.setBackground(new Color(204, 255, 255));

    Iterator<HashMap.Entry<Integer, Receipt>> i = receipts.entrySet().iterator();
    while (i.hasNext()) {
      HashMap.Entry<Integer, Receipt> entry = i.next();
      Receipt receipt = entry.getValue();
      model.addRow(new Object[]{receipt.getId(), receipt.getAmount(), receipt.getKind(), receipt.getIssueDate(), receipt.getCompany().getName()});
    }
    JScrollPane sp = new JScrollPane();
    sp.setBounds(10, 319, 410, 176);
    sp.getViewport().setBackground(new Color(170, 220, 220));

    frame.getContentPane().add(sp);
    sp.setViewportView(table_1);

    lblReceiptsDisplay = new JLabel(" "+model.getRowCount() + "");
    lblReceiptsDisplay.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    lblReceiptsDisplay.setBounds(123, 259, 143, 24);
    lblReceiptsDisplay.setBackground(new Color(204, 255, 255));

    lblReceiptsDisplay.setOpaque(true);
    frame.getContentPane().add(lblReceiptsDisplay);
    
    JLabel lblNameDisplay = new JLabel(" "+taxpayerManager.getTaxpayerName(taxRegistrationNumber));
    lblNameDisplay.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    lblNameDisplay.setBounds(123, 11, 143, 24);
    lblNameDisplay.setOpaque(true);
    lblNameDisplay.setBackground(new Color(204, 255, 255));
    frame.getContentPane().add(lblNameDisplay);

    JLabel lblBasicTaxDisplay = new JLabel(" "+f.format(taxpayerManager.getTaxpayerBasicTax(taxRegistrationNumber)));
    lblBasicTaxDisplay.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    lblBasicTaxDisplay.setBounds(123, 155, 143, 24);
    lblBasicTaxDisplay.setOpaque(true);
    lblBasicTaxDisplay.setBackground(new Color(204, 255, 255));
    frame.getContentPane().add(lblBasicTaxDisplay);

    lblTotalTaxDisplay = new JLabel(" "+f.format(taxpayerManager.getTaxpayerTotalTax(taxRegistrationNumber)));
    lblTotalTaxDisplay.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    lblTotalTaxDisplay.setOpaque(true);
    lblTotalTaxDisplay.setBackground(new Color(204, 255, 255));
    lblTotalTaxDisplay.setBounds(123, 190, 143, 24);
    frame.getContentPane().add(lblTotalTaxDisplay);

    lblTaxVariationDisplay = new JLabel(" "+f.format(taxpayerManager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber)));
    lblTaxVariationDisplay.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    lblTaxVariationDisplay.setOpaque(true);
    lblTaxVariationDisplay.setBackground(new Color(204, 255, 255));
    lblTaxVariationDisplay.setBounds(123, 225, 143, 24);
    frame.getContentPane().add(lblTaxVariationDisplay);
    
    JLabel lblIncomeDisplay = new JLabel(" "+taxpayerManager.getTaxpayerIncome(taxRegistrationNumber));
    lblIncomeDisplay.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    lblIncomeDisplay.setBounds(123, 119, 143, 24);
    lblIncomeDisplay.setOpaque(true);
    lblIncomeDisplay.setBackground(new Color(204, 255, 255));
    frame.getContentPane().add(lblIncomeDisplay);
    
    JLabel lblStatusDisplay = new JLabel(" "+taxpayerManager.getTaxpayerStatus(taxRegistrationNumber));
    lblStatusDisplay.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    lblStatusDisplay.setBounds(123, 83, 143, 24);
    lblStatusDisplay.setOpaque(true);
    lblStatusDisplay.setBackground(new Color(204, 255, 255));
    frame.getContentPane().add(lblStatusDisplay);
    
    JLabel lblTrnDisplay = new JLabel(" "+taxRegistrationNumber + "");
    lblTrnDisplay.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    lblTrnDisplay.setBounds(123, 47, 143, 24);
    lblTrnDisplay.setOpaque(true);
    lblTrnDisplay.setBackground(new Color(204, 255, 255));
    frame.getContentPane().add(lblTrnDisplay);
    
    JButton addReceipt = new JButton("ADD RECEIPT");
    addReceipt.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    addReceipt.setBorder(null);
    addReceipt.setBackground(new Color(170, 250, 230));
    addReceipt.setBounds(276, 226, 144, 24);
    addReceipt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JPanel receiptImporterPanel = new JPanel(new GridLayout(9, 2));
        JTextField receiptID = new JTextField(16);
        JTextField date = new JTextField(16);
        JTextField amount = new JTextField(16);
        JTextField company = new JTextField(16);
        JTextField country = new JTextField(16);
        JTextField city = new JTextField(16);
        JTextField street = new JTextField(16);
        JTextField number = new JTextField(16);
        int receiptIDValue, numberValue;
        float amountValue;
        String dateValue, companyValue, countryValue;
        String cityValue, streetValue;
        receiptImporterPanel.add(new JLabel("Receipt ID:"));
        receiptImporterPanel.add(receiptID);
        receiptImporterPanel.add(new JLabel("Date:"));
        receiptImporterPanel.add(date);
        receiptImporterPanel.add(new JLabel("Amount:"));
        receiptImporterPanel.add(amount);
        receiptImporterPanel.add(new JLabel("Company:"));
        receiptImporterPanel.add(company);
        receiptImporterPanel.add(new JLabel("Country:"));
        receiptImporterPanel.add(country);
        receiptImporterPanel.add(new JLabel("City:"));
        receiptImporterPanel.add(city);
        receiptImporterPanel.add(new JLabel("Street:"));
        receiptImporterPanel.add(street);
        receiptImporterPanel.add(new JLabel("Number:"));
        receiptImporterPanel.add(number);
        Object[] possibilities = {"Entertainment", "Basic", "Travel", "Health", "Other"};
        String status = (String)JOptionPane.showInputDialog(
            frame,
            receiptImporterPanel,
            "Create Receipt",
            JOptionPane.PLAIN_MESSAGE,
            null,
            possibilities,
            "Basic");
        if(status != null)
        {
          if(receiptID.getText().isEmpty() || date.getText().isEmpty() || amount.getText().isEmpty() ||
              company.getText().isEmpty() || country.getText().isEmpty() || city.getText().isEmpty() ||
              street.getText().isEmpty() || number.getText().isEmpty())
            return;
          receiptIDValue = Integer.parseInt(receiptID.getText());
          dateValue = date.getText();
          amountValue = Float.parseFloat(amount.getText());
          companyValue = company.getText();
          countryValue = country.getText();
          cityValue = city.getText();
          streetValue = street.getText();
          numberValue = Integer.parseInt(number.getText());
          try {
            taxpayerManager.addReceipt(receiptIDValue, dateValue, amountValue, status,
                                       companyValue, countryValue, cityValue, streetValue, 
                                       numberValue, taxRegistrationNumber);
            model.addRow(new Object[]{receiptIDValue, amountValue, status, dateValue, companyValue});
            int receiptCount = table_1.getRowCount();
            updateLabels(taxpayerManager, taxRegistrationNumber, table_1.getRowCount());
          } catch (WrongReceiptKindException e1) {
            JOptionPane.showMessageDialog(null, "Please check receipts kind and try again.");
          } catch (WrongReceiptDateException e1) {
            JOptionPane.showMessageDialog(null,
                "Please make sure your date " + "is DD/MM/YYYY and try again.");
          } catch (IOException e1) {
            e1.printStackTrace();
          } catch (ReceiptAlreadyExistsException e1) {
            JOptionPane.showMessageDialog(null,
                "Receipt already exists.");       
          }
        }  
      }
    });
    frame.getContentPane().add(addReceipt);
    
    JButton deleteReceipt = new JButton("DELETE RECEIPT");
    deleteReceipt.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    deleteReceipt.setBorder(null);
    deleteReceipt.setBackground(new Color(170, 250, 230));
    deleteReceipt.setBounds(276, 259, 144, 24);
    deleteReceipt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      
        if(table_1.getSelectionModel().isSelectionEmpty())
          return;
        
        int row = table_1.getSelectedRow();
        String receiptID = table_1.getModel().getValueAt(row, 0).toString();
        int n = JOptionPane.showConfirmDialog(
            frame,
            "Are you sure you wish to delete receipt "+receiptID + "?",
            "DELETE RECEIPT",
            JOptionPane.YES_NO_OPTION);

        if(n == 0)
        {
          try {
            taxpayerManager.removeReceipt(Integer.parseInt(receiptID));
            ((DefaultTableModel)table_1.getModel()).removeRow(row);
            int receiptCount = table_1.getRowCount();
            lblReceiptsDisplay.setText(" "+receiptCount);
            updateLabels(taxpayerManager, taxRegistrationNumber, table_1.getRowCount());
          } catch (NumberFormatException e1) {
            e1.printStackTrace();
          } catch (IOException e1) {
            JOptionPane.showMessageDialog(null,
                "Problem with opening file ." + Integer.parseInt(receiptID) + "_INFO.txt");
          } catch (WrongReceiptKindException e1) {
            JOptionPane.showMessageDialog(null, "Please check receipt's kind and try again.");
          }
        }
      }
    });
    frame.getContentPane().add(deleteReceipt);
    
    JButton btnViewChart = new JButton("VIEW CHARTS");
    btnViewChart.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    btnViewChart.setBorder(null);
    btnViewChart.setBackground(new Color(170, 250, 230));
    btnViewChart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ChartDisplay.createBarChart(taxpayerManager.getTaxpayerBasicTax(taxRegistrationNumber),
            taxpayerManager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber),
            taxpayerManager.getTaxpayerTotalTax(taxRegistrationNumber));
        ChartDisplay.createPieChart(
            taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT),
            taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC),
            taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL),
            taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH),
            taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER));
      }
    });
    btnViewChart.setBounds(276, 47, 144, 24);
    frame.getContentPane().add(btnViewChart);
    
    JButton btnSaveData = new JButton("SAVE DATA");
    btnSaveData.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
    btnSaveData.setBorder(null);
    btnSaveData.setBackground(new Color(170, 250, 230));
    btnSaveData.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JPanel fileLoaderPanel = new JPanel(new BorderLayout());
        JPanel boxPanel = new JPanel(new BorderLayout());
        JPanel taxRegistrationNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JPanel loadPanel = new JPanel(new GridLayout(1, 2));
        loadPanel.add(taxRegistrationNumberPanel);
        fileLoaderPanel.add(boxPanel, BorderLayout.NORTH);
        fileLoaderPanel.add(loadPanel, BorderLayout.CENTER);
        JCheckBox txtBox = new JCheckBox("Txt file");
        JCheckBox xmlBox = new JCheckBox("Xml file");

        txtBox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            xmlBox.setSelected(false);
          }
        });

        xmlBox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            txtBox.setSelected(false);
          }
        });
        txtBox.doClick();
        boxPanel.add(txtBox, BorderLayout.WEST);
        boxPanel.add(xmlBox, BorderLayout.EAST);

        int answer = JOptionPane.showConfirmDialog(null, fileLoaderPanel, "Choose file format",
            JOptionPane.OK_CANCEL_OPTION);
        if (answer == 0) {
          try {
            if (txtBox.isSelected()) {
              try {
                taxpayerManager.saveLogFile(taxRegistrationNumber, "txt");
              } catch (IOException e1) {
                JOptionPane.showMessageDialog(null,
                    "Problem with opening file ." + taxRegistrationNumber + "_LOG.txt");
              } catch (WrongFileFormatException e1) {
                JOptionPane.showMessageDialog(null, "Wrong file format");
              }
            } else {
              try {
                taxpayerManager.saveLogFile(taxRegistrationNumber, "xml");
              } catch (IOException e1) {
                JOptionPane.showMessageDialog(null,
                    "Problem with opening file ." + taxRegistrationNumber + "_LOG.xml");
              } catch (WrongFileFormatException e1) {
                JOptionPane.showMessageDialog(null, "Wrong file format");
              }
            }
          } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null,
                "The tax registration number must have only digits.");
          }
        }
      }
    });

    btnSaveData.setBounds(276, 136, 144, 24);
    frame.getContentPane().add(btnSaveData);
    
  }

  public void updateLabels(TaxpayerManager taxpayerManager, int taxRegistrationNumber, int receiptCount)
  {
    lblReceiptsDisplay.setText(" "+receiptCount);
    lblTotalTaxDisplay.setText(" "+taxpayerManager.getTaxpayerTotalTax(taxRegistrationNumber));
    lblTaxVariationDisplay.setText(" "+taxpayerManager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber)); 
  }

  
  public void setVisible(boolean b) {
    frame.setVisible(true);
  }
}