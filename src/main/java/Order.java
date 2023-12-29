import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class Order {
    private static final String ORDER_PATH = "/api/v1/orders";
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    public Order() {
        RestAssured.baseURI = BASE_URL;}

    @Step("Получаем ответ при создании заказа")
    public Response getOrder (OrderCredentials credentials) {
        return given().log().all()
                .header("Content-type", "application/json")
                .and()
                .body(credentials)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Создаем заказ")
    public void createOrder (OrderCredentials credentials) {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(credentials)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Получить список заказов")
    public Response getOrderList() {
        return given()
                .get(ORDER_PATH);
    }
}