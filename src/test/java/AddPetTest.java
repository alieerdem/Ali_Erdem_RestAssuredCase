import base.Base;
import io.restassured.response.Response;
import models.response.pet.PetResponse;
import org.testng.annotations.Test;

import static constants.Constants.*;
import static helper.PetDtoBuilder.createPetBody;
import static helper.RequestHelper.postRequest;
import static helper.RequestHelper.postRequestWithFile;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AddPetTest extends Base{
 

    @Test
    void addPetToTheStore_verifyPetAdded() {
        Response response = postRequest("pet", createPetBody(petId, "bekir"));
        response.getBody().prettyPeek();

        PetResponse petCreateResponse = postRequest("/pet", createPetBody(petId, "bekir"))
                .as(PetResponse.class);

        assertEquals(petCreateResponse.getId(), petId, "Pet ID in response should match");
        assertEquals(petCreateResponse.getName(), "bekir", "Pet name should match");
    }

    @Test
    void addImageToPet_verifyImageUploaded() {
        Response response = postRequestWithFile("pet/" + petId + "/uploadImage", fileUrl);
        response.getBody().prettyPeek();

        assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        assertTrue(response.jsonPath().getString("message").contains("uploaded"), "Response should confirm upload");
    }

    @Test
    void addPetWithEmptyBody_verifyErrorReturned() {
        // Use invalid data format: passing an empty string as body
        Response response = given()
                .header("Content-Type", "application/json")
                .body("")
                .post("pet");
        response.getBody().prettyPeek();

        // Expect a 400 or 500 error status code
        assertTrue(response.getStatusCode() >= 400, "Status code should be an error code (4xx or 5xx)");
    }

}
