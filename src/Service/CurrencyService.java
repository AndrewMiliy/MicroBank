package Service;

import Model.CurrencyModel;
import Model.UserModel;
import Model.UserRole;
import Repository.CurrencyRepository;

import java.util.List;

public class CurrencyService {
    private static CurrencyRepository cR;

    public CurrencyService(CurrencyRepository cR) {
        this.cR = cR;
    }
    public void addCurrency(UserModel admin, CurrencyModel currency) {
        if (admin.getUserRole().equals(UserRole.ADMIN)) {
            cR.addCurrency(currency);
        } else {
            System.out.println("Только администратор может добавить новую валюту.");
        }
    }
    public void deleteCurrency(UserModel admin, String code) {
        if (admin.getUserRole().equals(UserRole.ADMIN)) {
            cR.deleteCurrency(code);
        } else {
            System.out.println("Только администратор может удалить валюту.");
        }
    }
    public void updateCurrency(UserModel admin, String code, CurrencyModel currency) {
        if (admin.getUserRole().equals(UserRole.ADMIN)) {
            cR.updateCurrency(code, currency);
        } else {
            System.out.println("Только администратор может изменять валюту.");
        }
    }
    public static List<CurrencyModel> getAllCurrencies() {
        return cR.getAllCurrencies();
    }
    public CurrencyModel getCurrency(String code) {
        return cR.getCurrency(code);
    }

}
