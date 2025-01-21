package incometaxcalculator.data.management;

public class HeadOfHouseholdTaxpayer extends Taxpayer {

    double[] taxpayerMultipliers = new double[]{0.0535, 0.0705, 0.0705, 0.0785, 0.0985};
    double[] taxpayerAdditions = new double[]{0, 1625.87, 5828.38, 8092.13, 14472.61};
    int [] taxpayerIncomeBorders = new int[]{0, 30390, 90000, 122110, 203390};
   
    public HeadOfHouseholdTaxpayer(String fullname, int taxRegistrationNumber, float income) {
      super(fullname, taxRegistrationNumber, income);
      this.setConstants(taxpayerMultipliers, taxpayerAdditions, taxpayerIncomeBorders);
    }
}
