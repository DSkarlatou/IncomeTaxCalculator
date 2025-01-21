package incometaxcalculator.data.management;

public class MarriedFilingJointlyTaxpayer extends Taxpayer {

    double[] taxpayerMultipliers = new double[]{0.0535, 0.0705, 0.0705, 0.0785, 0.0985};
    double[] taxpayerAdditions = new double[]{0, 1930.28, 5731.64, 9492.82, 18179.69};
    int[]  taxpayerIncomeBorders = new int[] {0, 36080, 90000, 143350, 254240};
    
    public MarriedFilingJointlyTaxpayer(String fullname, int taxRegistrationNumber, float income) {
      super(fullname, taxRegistrationNumber, income);
      this.setConstants(taxpayerMultipliers, taxpayerAdditions, taxpayerIncomeBorders);
    }
}