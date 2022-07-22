
import java.util.ArrayList;
import java.util.List;

public class FastFood extends FoodPlace {

    private List<Staff> staff = new ArrayList<>();

    public FastFood(String name, double fixedCosts, Owner owner, List<Staff> staff) {
        super(name,fixedCosts,owner);
        this.staff = new ArrayList<>(staff);
    }

    public List<Staff> getStaff() {
        return staff;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name of FastFood: " + this.getName() +
                "\n" + "Owner: " + this.getOwner());
        int index = 1;
        for (Staff staff: staff) {
            builder.append("\n" + "Staff " + index++ + " : " + staff );
        }
        return builder.toString();
    }

    @Override
    public void workShift(int hours) {
        double employeeIncome;
        Owner owner = this.getOwner();
        double ownerExpenses = owner.getSalaryExpenses();
        for(int i=0; i<staff.size(); i++){
            Staff employee = staff.get(i);
            employeeIncome = employee.workHours(hours);
            ownerExpenses += employeeIncome;
        }
        owner.setSalaryExpenses(ownerExpenses);
    }

    @Override
    public List<IncomeTaxPayer> getIncomeTaxPayers() {
        ArrayList<IncomeTaxPayer> taxPayerList = new ArrayList<IncomeTaxPayer>();
        Owner owner = this.getOwner();
        taxPayerList.add(owner);
        for(int i=0; i<staff.size(); i++){
            Staff employee = staff.get(i);
            taxPayerList.add(employee);
        }
        return taxPayerList;
    }

    @Override
    public void distributeIncomeAndSalesTax(Check check) {
        Owner owner = this.getOwner();
        double ownerIncome = owner.getIncome() + check.getMenuPrice();
        owner.setIncome(ownerIncome);
        int n = staff.size();
        double tipPerStaff = check.getTip() / n;
        for(int i=0; i<staff.size(); i++) {
            Staff employee = staff.get(i);
            double employeeIncome = employee.getIncome() + tipPerStaff;
            employee.setIncome(employeeIncome);
        }
        double checkSalesTax = check.getMenuPrice()*0.15;
        this.setTotalSalesTax(this.getTotalSalesTax() + checkSalesTax);
    }

    @Override
    public double getTipPercentage() {
        return 0;
    }
}
