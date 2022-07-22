
public class Staff extends IncomeTaxPayer {

    private int salaryPerHour;
    final private int incomeTaxPercentage = 25;

    public Staff(String name, boolean isCook) {
        super(name);
        if(isCook){
            salaryPerHour = 20;
        }else{
            salaryPerHour = 10;
        }
    }

    public int getSalaryPerHour() {
        return salaryPerHour;
    }

    public int getIncomeTaxPercentage() {
        return incomeTaxPercentage;
    }

    public double workHours(int numHours) {
        double newIncome = salaryPerHour * numHours;
        double totalIncome = this.getIncome() + newIncome;
        this.setIncome(totalIncome);

        return newIncome;
    }

    @Override
    public double calculateIncomeTax() {
        double incomeTax = getIncome() * ((double) incomeTaxPercentage/100);
        return incomeTax;
    }

}
