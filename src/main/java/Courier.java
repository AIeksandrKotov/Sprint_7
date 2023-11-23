import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class Courier {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final String LOGIN_PATH = "/api/v1/courier/login";
    private static final String CREATE_PATH = "api/v1/courier";
    private static final String DELETE_PATH = "api/v1/courier";
    public Courier() {
        RestAssured.baseURI = BASE_URL;}

    @Step("Получаем ответ при авторизации курьера")
    public Response getLoginCourier(CourierCredentials credentials) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(credentials)
                .when()
                .post(LOGIN_PATH);
    }

    @Step("Получаем ответ при создании курьера")
    public Response getCreateCourier(CourierCredentials credentials) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(credentials)
                .when()
                .post(CREATE_PATH);
    }

    @Step("Создаем курьера")
    public void createCourier(CourierCredentials credentials) {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(credentials)
                .when()
                .post(CREATE_PATH);

    }
        @Step("Логинемся")
        public void loginCourier (CourierCredentials credentials){
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(credentials)
                    .when()
                    .post(LOGIN_PATH);
        }

        @Step("Удаляем курьера")
        public void deleteCourier (int id) {
        given().delete(DELETE_PATH + "/" + id);
        }

    public static String randomString(int length) {
        Random random = new Random();
        int leftLimit = 97;
        int rightLimit = 122;
        StringBuilder buffer = new StringBuilder(length);

        for(int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (float)(rightLimit - leftLimit + 1));
            buffer.append(Character.toChars(randomLimitedInt));
        }
        return buffer.toString();
    }
    public static CourierCredentials randomCourier( ) {
        return new CourierCredentials()
                .setLogin(randomString(7))
                .setPassword(randomString(7))
                .setFirstName(randomString(7));
    }
}
