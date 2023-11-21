import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class CourierLoginTest {
    private Courier courier;
    private CourierCredentials courierCredentials;


    @Before
    public void setUp() {
        RestAssured.baseURI = Courier.BASE_URL;
        courier = new Courier();
        courierCredentials = new CourierCredentials("alex", "1234", "kot");
        courier.createCourier(courierCredentials);
        courierCredentials = new CourierCredentials(courierCredentials.getLogin(), courierCredentials.getPassword());
    }
    @After
    public void deleteCourier () {
        CourierCredentials loginCredentials = courier.getLoginCourier(courierCredentials).as(CourierCredentials.class);
        courier.deleteCourier(loginCredentials.getId());
    }

    @Test
    @DisplayName("Логинимся")
    @Description("Проверяем что курьер может авторизоваться")
    public void loginCourier() {
        Response loginCourier = courier.getLoginCourier(courierCredentials);
        loginCourier
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }
    @Test
    @DisplayName("Входим без пароля")
    @Description("Проверяем что нельзя войти без пароля")
    public void loginCourierWithoutLogin(){
        courierCredentials.setLogin("");
        Response response = courier.getLoginCourier(courierCredentials);
        response
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Входим с не правильным логином")
    @Description("Проверяем что нельзя войти если дынные при логине не верные")
    public void loginCourierWrongLogin(){
        courierCredentials.setLogin("qqq");
        Response response = courier.getLoginCourier(courierCredentials);
        response
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}