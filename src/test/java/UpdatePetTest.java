import base.Base;
import io.restassured.response.Response;
import models.response.pet.PetResponse;
import org.testng.annotations.Test;

import static constants.Constants.petId;
import static helper.PetDtoBuilder.createPetBody;
import static helper.RequestHelper.*;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UpdatePetTest extends Base {

    @Test
    void updatePetName_verifyNameUpdated() {
        String updatedPetName = "dog";
        Response response = putRequest("pet", createPetBody(petId,updatedPetName));
        PetResponse petUpdateResponse = response.as(PetResponse.class);
        response.getBody().prettyPeek();
        
        assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        assertEquals(petUpdateResponse.getId(), petId, "Pet ID in response should match");
        assertEquals(petUpdateResponse.getName(), updatedPetName, "Pet name is not updated");
    }

    @Test
    void updatePetStatus_verifyStatusUpdated() {
        long petId = getRandomLong();
        createPet(petId);

        // Add a small delay to allow the API to process the request 
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = postRequest("pet/"+ petId,"status=sold");
        response.getBody().prettyPeek();
        
        assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        assertEquals(response.jsonPath().getString("message"), String.valueOf(petId), "Message field on response not returned pet ID");
    }
    
    @Test
    void updateNonExistentPet_checkForError() {
        // Use a pet ID that is unlikely to exist
        long nonExistentPetId = 999999999;
        
        Response response = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("name=test&status=test")
                .post("/pet/" + nonExistentPetId);
        
        response.getBody().prettyPeek();
        
        // API might return 404 or other error code
        assertTrue(response.getStatusCode() >= 400, "Status code should indicate an error");
    }
}
