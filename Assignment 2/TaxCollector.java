
import java.util.ArrayList;
import java.util.List;

public class TaxCollector {

    private List<FoodPlace> foodPlaces = new ArrayList<>();

    private double incomeTaxCollected;
    private double salesTaxCollected;

    public TaxCollector(List<FoodPlace> foodPlaces) {
        this.foodPlaces = foodPlaces;
    }

    public List<FoodPlace> getFoodPlaces() {
        return foodPlaces;
    }

    public double getIncomeTaxCollected() {
        return incomeTaxCollected;
    }

    public double getSalesTaxCollected() {
        return salesTaxCollected;
    }

    public void collectTax() {
        for(int i=0; i<foodPlaces.size(); i++){
            FoodPlace foodplace = foodPlaces.get(i);
            List<IncomeTaxPayer> incomeTaxPayers = foodplace.getIncomeTaxPayers();
            salesTaxCollected += foodplace.getTotalSalesTax();
            for(int m=0; m<incomeTaxPayers.size(); m++){
                IncomeTaxPayer incomeTaxPayer = incomeTaxPayers.get(m);
                incomeTaxCollected += incomeTaxPayer.calculateIncomeTax();
            }
        }
    }

    public String toString() {
        return "TaxCollector: income tax collected: " + incomeTaxCollected + ", sales tax collected: " + salesTaxCollected;
    }

}
