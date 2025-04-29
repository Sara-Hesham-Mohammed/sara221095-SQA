package TestNG;

import org.testng.annotations.DataProvider;

public class PropertyListingDataProvider {


    @DataProvider(name = "addPropertyPositive")
    public static Object[][] addPropertyPositive() {
        return new Object[][]{
                {
                        "Homes", "Entire Place", "Chalet", "One Chalet",
                        "My property isn't listed on any other websites",
                        "The Breezy Chalet", "5065101", "Egypt", "El Alamein", "5065101"
                }
        };
    }

    @DataProvider(name = "addPropertyInvalidPostcode")
    public static Object[][] addPropertyInvalidPostcode() {
        return new Object[][]{
                {
                        "Homes", "Entire Place", "Chalet", "One Chalet",
                        "My property isn't listed on any other websites",
                        "The Breezy Chalet", "5065101", "Egypt", "El Alamein", "1111"
                }
        };
    }

    @DataProvider(name = "addPropertyInsufficientPhotos")
    public static Object[][] addPropertyInsufficientPhotos() {
        return new Object[][]{
                {
                        new String[]{
                                "photo1.jpg",
                                "photo2.jpg",
                                "photo3.jpg",
                                "photo4.jpg"
                        }
                }
        };
    }

    @DataProvider(name = "removeProperty")
    public static Object[][] removeProperty() {
        return new Object[][]{
                {
                        "My property is no longer available",
                        "I’m closing it permanently"
                }
        };
    }
}


