package TestNG;

import org.testng.annotations.DataProvider;

public class PropertyListingDataProvider {

    @DataProvider(name = "propertyData")
    public Object[][] providePropertyData() {
        return new Object[][] {
                {"The Breezy Chalet", "5065101", "Egypt", "El Alamein", "5065101", true},  // Valid case
                {"The Breezy Chalet", "5065101", "Egypt", "El Alamein", "1111", false}     // Invalid zip
        };
    }

}


