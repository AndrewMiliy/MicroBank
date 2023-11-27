package Model;

public class CurrencyModel {
        private String name;
        private String codeName;
        private double amount;

    public CurrencyModel(String name, String codeName, double amount) {
        this.name = name;
        this.codeName = codeName;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
