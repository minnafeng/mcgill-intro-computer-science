

public class Customer  {

    private String name;
    private int  targetTipPct;

    public Customer(String name, int targetTipPct) {
        this.name = name;
        this.targetTipPct = targetTipPct;
    }

    public String getName() {
        return name;
    }

    public int getTargetTipPct() {
        return targetTipPct;
    }

    public String getDescriptiveMessage(FoodPlace foodPlace) {
        return this.name + " dined in " + foodPlace.getName();
    }

    public void dineAndPayCheck(FoodPlace foodPlace, double menuPrice ) {
        Check check = new Check(menuPrice);
        double foodPlaceTargetTip = foodPlace.getTipPercentage();
        double tipPct = (targetTipPct + foodPlaceTargetTip)/2 ;
        check.setTipByPct(tipPct);
        foodPlace.distributeIncomeAndSalesTax(check);

    }
}
