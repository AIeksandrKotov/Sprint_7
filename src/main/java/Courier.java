import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Courier {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final String LOGIN_PATH = "/api/v1/courier/login";
    private static final String CREATE_PATH = "api/v1/courier";
    private static final String DELETE_PATH = "api/v1/courier";


    @Step("Получаем ответ при авторизации курьера")
    public Response getLoginCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
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
    public void createCourier(Object body) {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(CREATE_PATH);

    }
        @Step("Логинемся")
        public void loginCourier (Object body){
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(body)
                    .when()
                    .post(LOGIN_PATH);
        }

        @Step("Удаляем курьера")
        public void deleteCourier (int id) {
        given().delete(DELETE_PATH + id);
        }
    }
