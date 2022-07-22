
public class Owner extends IncomeTaxPayer {

    final private int incomeTaxPct = 10;
    private double salaryExpenses;

    private FoodPlace foodPlace;

    public Owner(String name) {
        super(name);
    }

    public int getIncomeTaxPct() {
        return incomeTaxPct;
    }

    public double getSalaryExpenses() {
        return salaryExpenses;
    }

    public void setSalaryExpenses(double salaryExpenses) {
        this.salaryExpenses = salaryExpenses;
    }

    public void setFoodPlace(FoodPlace foodPlace) {
        this.foodPlace = foodPlace;
    }

    public FoodPlace getFoodPlace() { return foodPlace; }

    @Override
    public double calculateIncomeTax() {
        double income = this.getIncome();
        double expenses = salaryExpenses + foodPlace.getFixedCosts();
        double profit = income - expenses;
        double incomeTax;
        if(profit>0){
            incomeTax = profit * ((double)incomeTaxPct/100);
        }else{
            incomeTax = 0;
        }
        return incomeTax;
    }
}
