package incometaxcalculator.data.management;

import java.util.Arrays;
import java.util.HashMap;

import incometaxcalculator.exceptions.WrongReceiptKindException;

public abstract class Taxpayer {

  protected final String fullname;
  protected final int taxRegistrationNumber;
  protected final float income;
  private float amountPerReceiptsKind[] = new float[5];
  private int totalReceiptsGathered = 0;
  private HashMap<Integer, Receipt> receiptHashMap = new HashMap<Integer, Receipt>(0);
  private double[] taxpayerMultipliers;
  private double[] taxpayerAdditions;
  private int[] taxpayerIncomeBorders;

  protected Taxpayer(String fullname, int taxRegistrationNumber, float income) {
    this.fullname = fullname;
    this.taxRegistrationNumber = taxRegistrationNumber;
    this.income = income;
  }

  public void addReceipt(Receipt receipt) throws WrongReceiptKindException {

    if(!Arrays.asList(receipt.getAcceptableReceiptKinds()).contains(receipt.getKind())) throw new WrongReceiptKindException();
    for(int i = 0; i < 5; i++)
      if(receipt.getKind().equals(receipt.getAcceptableReceiptKinds()[i])) amountPerReceiptsKind[i] += receipt.getAmount();
    
    receiptHashMap.put(receipt.getId(), receipt);
    totalReceiptsGathered++;
  }

  public void removeReceipt(int receiptId) throws WrongReceiptKindException {
    Receipt receipt = receiptHashMap.get(receiptId);
    
    if(!Arrays.asList(receipt.getAcceptableReceiptKinds()).contains(receipt.getKind())) throw new WrongReceiptKindException();
    
    for(int i = 0; i < 5; i++)
      if(receipt.getKind().equals(receipt.getAcceptableReceiptKinds()[i])) amountPerReceiptsKind[i] -= receipt.getAmount();
    
    
    totalReceiptsGathered--;
    receiptHashMap.remove(receiptId);
  }

  public String getFullname() {
    return fullname;
  }

  public int getTaxRegistrationNumber() {
    return taxRegistrationNumber;
  }

  public float getIncome() {
    return income;
  }

  public HashMap<Integer, Receipt> getReceiptHashMap() {
    return receiptHashMap;
  }

  public double getVariationTaxOnReceipts() {
    float totalAmountOfReceipts = getTotalAmountOfReceipts();
    
    if (totalAmountOfReceipts < 0.2 * income)  return calculateBasicTax() * 0.08;
    if (totalAmountOfReceipts < 0.4 * income)  return calculateBasicTax() * 0.04;
    if (totalAmountOfReceipts < 0.6 * income)  return -calculateBasicTax() * 0.15;
    
    return -calculateBasicTax() * 0.3;
  }

  private float getTotalAmountOfReceipts() {
    int sum = 0;
    for (int i = 0; i < 5; i++) {
      sum += amountPerReceiptsKind[i];
    }
    return sum;
  }

  public int getTotalReceiptsGathered() {
    return totalReceiptsGathered;
  }

  public float getAmountOfReceiptKind(short kind) {
    return amountPerReceiptsKind[kind];
  }

  public double getTotalTax() {
    return calculateBasicTax() + getVariationTaxOnReceipts();
  }

  public double getBasicTax() {
    return calculateBasicTax();
  }

  public double calculateBasicTax() {
  
    for(int i  = 1; i < 5; i++)
      if(income < taxpayerIncomeBorders[i]) 
        return taxpayerAdditions[i-1] + taxpayerMultipliers[i-1] * (income - taxpayerIncomeBorders[i-1]); 
    return taxpayerAdditions[4] + taxpayerMultipliers[4] * (income - taxpayerIncomeBorders[4]);
  
  }

  public void setConstants(double[] multipliers, double[] additions, int[] borders)
  {
    this.taxpayerMultipliers = multipliers;
    this.taxpayerAdditions = additions;
    this.taxpayerIncomeBorders = borders;
  }
}