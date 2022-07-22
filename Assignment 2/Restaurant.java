
import java.util.ArrayList;
import java.util.List;

public class Restaurant extends FoodPlace {

    private Staff cook;
    private Server server;

    public Restaurant(String name, double fixedCosts, Owner owner, Staff cook, Server server) {
        super(name,fixedCosts,owner);
        this.cook = cook;
        this.server = server;
    }

    public Staff getCook() {
        return cook;
    }

    public Server getServer() {
        return server;
    }

    @Override
    public String toString() {
        return "Name of restaurant: " + this.getName() +
                "\n" + "Owner: " + this.getOwner() +
                "\n" + "Cook: " + cook +
                "\n" + "Server: " + server;
    }

    @Override
    public void workShift(int hours) {
        Owner owner = this.getOwner();
        double cookIncome = cook.workHours(hours);
        double serverIncome = server.workHours(hours);
        double ownerExpenses = owner.getSalaryExpenses() + cookIncome + serverIncome;
        owner.setSalaryExpenses(ownerExpenses);
    }

    @Override
    public List<IncomeTaxPayer> getIncomeTaxPayers() {
        ArrayList<IncomeTaxPayer> taxPayerList = new ArrayList<>();
        Owner owner = this.getOwner();
        taxPayerList.add(owner);
        taxPayerList.add(cook);
        taxPayerList.add(server);
        return taxPayerList;
    }

    @Override
    public void distributeIncomeAndSalesTax(Check check) {
        Owner owner = this.getOwner();
        double ownerIncome = owner.getIncome() + check.getMenuPrice();
        owner.setIncome(ownerIncome);
        double serverIncome = server.getIncome() + check.getTip()*0.8;
        server.setIncome(serverIncome);
        double cookIncome = cook.getIncome() + check.getTip()*0.2;
        cook.setIncome(cookIncome);
        double checkSalesTax = check.getMenuPrice()*0.15;
        this.setTotalSalesTax(this.getTotalSalesTax() + checkSalesTax);
    }

    @Override
    public double getTipPercentage() {
        return server.getTargetTipPct();
    }

}
