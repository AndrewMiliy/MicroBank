package Model;

import java.io.Serial;
import java.io.Serializable;

public class CurrencyModel  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name; // название
    private String codeName; // аббревиатура

    public CurrencyModel(String name, String codeName) {
        this.name = name;
        this.codeName = codeName;
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

}
