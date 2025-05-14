package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import static constants.Constants.PET_STORE_URL;

public class Base {

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = PET_STORE_URL;
    }
}
