package LogIn;

import Search.Country;
import javafx.scene.control.Button;

public class CountryInfo {
        private String CountryName;
        private int count;

        public CountryInfo(Country country) {
            this.CountryName = country.getName();
            this.count = country.getCount();
        }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
