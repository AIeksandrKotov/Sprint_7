import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    private static final String LOGIN_PATH = "api/v1/courier/login";
    CourierCredentials loginCourier = new CourierCredentials("alex", "1234");
    CourierCredentials withoutLogin = new CourierCredentials("", "1234");
    CourierCredentials wrongLogin = new CourierCredentials("alex1111", "1234");

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    // Написал по одному тесту если передать неверный логин или войти без пароля
    @Test
    @DisplayName("Логинимся")
    @Description("Проверяем что курьер может авторизоваться")
    public void loginCourier() {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(LOGIN_PATH)
                .then().assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }
    @Test
    @DisplayName("Входим без пароля")
    @Description("Проверяем что нельзя войти без пароля")
    public void loginCourierWithoutLogin(){
        given()
                .header("Content-type", "application/json")
                .and()
                .body(withoutLogin)
                .when()
                .post(LOGIN_PATH)
                .then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Входим с не правильным логином")
    @Description("Проверяем что нельзя войти если дынные при логине не верные")
    public void loginCourierWrongLogin(){
        given()
                .header("Content-type", "application/json")
                .and()
                .body(wrongLogin)
                .when()
                .post(LOGIN_PATH)
                .then().assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}