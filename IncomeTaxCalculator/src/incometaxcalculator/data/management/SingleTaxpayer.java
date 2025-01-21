package incometaxcalculator.data.management;

public class SingleTaxpayer extends Taxpayer {

    double[]  taxpayerMultipliers = new double[]{0.0535, 0.0705, 0.0785, 0.0785, 0.0985};
    double[] taxpayerAdditions = new double[]{0, 1320.38, 5296.58, 5996.80, 10906.19};
    int[] taxpayerIncomeBorders = new int[]{0, 24680, 81080, 90000, 152540};
  
    public SingleTaxpayer(String fullname, int taxRegistrationNumber, float income) {
      super(fullname, taxRegistrationNumber, income);
      this.setConstants(taxpayerMultipliers, taxpayerAdditions, taxpayerIncomeBorders);
    }

}