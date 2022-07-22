
import java.util.ArrayList;
import java.util.List;

public class FoodStand extends FoodPlace {

    public FoodStand(String name, double fixedCosts, WorkingOwner owner) {
        super(name,fixedCosts,owner);
    }

    @Override
    public String toString() {
        return "Name of FoodStand: " + this.getName() +
                "\n" + "Owner: " + this.getOwner();
    }

    @Override
    public void workShift(int hours) {
        // no salaried workers so do nothing
    }

    @Override
    public List<IncomeTaxPayer> getIncomeTaxPayers() {
        ArrayList<IncomeTaxPayer> taxPayerList = new ArrayList<IncomeTaxPayer>();
        Owner owner = this.getOwner();
        taxPayerList.add(owner);
        return taxPayerList;

    }

    @Override
    public void distributeIncomeAndSalesTax(Check check) {
        Owner owner = this.getOwner();
        double ownerIncome = owner.getIncome() + check.getMenuPrice() + check.getTip();
        owner.setIncome(ownerIncome);
        double checkSalesTax = check.getMenuPrice()*0.15;
        this.setTotalSalesTax(this.getTotalSalesTax() + checkSalesTax);
    }

    @Override
    public double getTipPercentage() {
        WorkingOwner owner = (WorkingOwner) this.getOwner();
        return owner.getTargetTipPct();
    }
}
