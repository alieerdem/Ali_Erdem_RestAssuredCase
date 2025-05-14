import base.Base;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static constants.Constants.petId;
import static helper.RequestHelper.*;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DeletePetTest extends Base {
    
    @Test
    void deletePet_verifyPetDeleted() {
        long petId = getRandomLong();
        createPet(petId);

        Response response = deleteRequest("pet/" + petId);
        response.getBody().prettyPeek();

        assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        assertEquals(response.jsonPath().getString("message"), String.valueOf(petId), "Response message should contain pet ID");
    }
    
    @Test
    void deleteNonExistentPet_verifyErrorReturned() {
        // Use a pet ID that is very unlikely to exist
        long nonExistentId = 9999999999L;
        
        Response response = given()
                .header("Content-Type", "application/json")
                .header("api_key", "test_key")
                .delete("/pet/" + nonExistentId);
                
        response.getBody().prettyPeek();
        
        // The API might return 404 or other error code
        assertTrue(response.getStatusCode() >= 400, "Status code should indicate an error");
    }
}
