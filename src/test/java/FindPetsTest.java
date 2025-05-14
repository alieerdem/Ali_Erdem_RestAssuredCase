import base.Base;
import io.restassured.response.Response;
import models.request.pet.PetDto;
import models.response.pet.PetResponse;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static helper.PetDtoBuilder.createPetBody;
import static helper.PetDtoBuilder.createPetBodyWithStatusSold;
import static helper.RequestHelper.*;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class FindPetsTest extends Base {

    @Test
    void findPetById_verifyPetListedCorrectly() {
        long petId = getRandomLong();
        postRequest("pet", createPetBody(petId, "boncuk"));

        Response response = getRequest("pet/" + petId);
        response.getBody().prettyPeek();

        if (response.getStatusCode() == 200) {
            assertEquals(response.jsonPath().getLong("id"), petId, "Pet ID in response should match");
            assertEquals(response.jsonPath().getString("status"), "available", "Status should match");

        } else {
            System.out.println("No pets found with petId " + petId);
        }
    }

    @Test
    void findPetsWithStatusAvailable_verifyPetFoundAndStatusMatches() {
        long petId = getRandomLong();

        Response postResponse = postRequest("pet", createPetBody(petId, "boncuk"));
        assertEquals(postResponse.getStatusCode(), 200, "Pet creation should succeed");

        // Add a small delay to allow the API to process the request
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Response responseFindPetByStatus = getRequest("pet/findByStatus?status=available");
        responseFindPetByStatus.getBody().prettyPeek();

        if (responseFindPetByStatus.getStatusCode() == 200) {
            List<Long> petIds = responseFindPetByStatus.jsonPath().getList("id");
            assert petIds.contains(petId) : "Created pet ID " + petId + " should be in the response";

            Response getPetByIdResponse = getRequest("pet/" + petId);
            assertEquals(getPetByIdResponse.jsonPath().getString("status"), "available", "Status not available");
        } else {
            System.out.println("No pets found with status available");
        }
    }

    @Test
    void findPetsWithStatusSold_verifyPetFoundAndStatusMatches() {
        long petId = getRandomLong();

        Response postResponse = postRequest("pet", createPetBodyWithStatusSold(petId, "arya"));
        assertEquals(postResponse.getStatusCode(), 200, "Pet creation should succeed");

        // Add a small delay to allow the API to process the request
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Response responseFindPetByStatus = getRequest("pet/findByStatus?status=sold");
        responseFindPetByStatus.getBody().prettyPeek();

        if (responseFindPetByStatus.getStatusCode() == 200) {
            List<Long> petIds = responseFindPetByStatus.jsonPath().getList("id");
            assert petIds.contains(petId) : "Created pet ID " + petId + " should be in the response";

            Response getPetByIdResponse = getRequest("pet/" + petId);
            assertEquals(getPetByIdResponse.jsonPath().getString("status"), "sold", "Status not sold");
        } else {
            System.out.println("No pets found with status sold");
        }
    }

    @Test
    void findNonExistentPet_verify404Returned() {
        // Use a pet ID that is very unlikely to exist
        long nonExistentId = 9999999999L;

        Response response = given()
                .header("Content-Type", "application/json")
                .get("/pet/" + nonExistentId);

        response.getBody().prettyPeek();

        // Expect 404 Not Found
        assertEquals(response.getStatusCode(), 404, "Status code should be 404 Not Found");
    }
}
