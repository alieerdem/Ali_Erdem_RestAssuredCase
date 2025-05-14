package constants;

import static helper.RequestHelper.getRandomLong;

public class Constants {
    public static final String PET_STORE_URL = "https://petstore.swagger.io/v2/";

    public static long petId = getRandomLong();
    public static String fileUrl = "src/test/resources/pictures/kopek.jpg";
}
