package helper;

import io.restassured.response.Response;
import models.request.pet.PetDto;
import java.io.File;
import java.util.Random;

import static helper.PetDtoBuilder.createPetBody;
import static io.restassured.RestAssured.given;

public class RequestHelper {

    public static Response getRequest(String path){
        return given()
                .header("Content-Type", "application/json")
                .get(path)
                .thenReturn();
    }

    public static Response postRequest(String path, PetDto body){
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .post(path);
    }
    
    public static Response postRequest(String path, String body){
        return given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(body)
                .post(path);
    }
    
    public static Response postRequestWithFile(String path, String fileUrl) {
        return given()
                .multiPart("file", new File(fileUrl), "text/html")
                .post(path);
    }

    public static Response putRequest(String path, PetDto body){
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .put(path);
    }

    public static Response deleteRequest(String path){
        return given()
                .header("Content-Type", "application/json")
                .header("api_key", "test_key")
                .delete(path);
    }

    public static long getRandomLong() {
        return new Random().nextLong(10000000);
    }

    public static Response createPet(long petId){
        PetDto petDto = createPetBody(petId, "bekir");

        return given()
                .header("Content-Type", "application/json")
                .body(petDto)
                .post("pet");
    }
}
