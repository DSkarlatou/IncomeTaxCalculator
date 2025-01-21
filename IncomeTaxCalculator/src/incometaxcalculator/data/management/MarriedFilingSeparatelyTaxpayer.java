package incometaxcalculator.data.management;

public class MarriedFilingSeparatelyTaxpayer extends Taxpayer {

    double[] taxpayerMultipliers = new double[]{0.0535, 0.0705, 0.0785, 0.0785, 0.0985};
    double[] taxpayerAdditions = new double[]{0, 965.14, 4746.76, 6184.88, 9098.80};
    int[] taxpayerIncomeBorders = new int[]{0, 18040, 71680, 90000, 127120};
  
    public MarriedFilingSeparatelyTaxpayer(String fullname, int taxRegistrationNumber, float income) {
      super(fullname, taxRegistrationNumber, income);
      this.setConstants(taxpayerMultipliers, taxpayerAdditions, taxpayerIncomeBorders);
    }
}