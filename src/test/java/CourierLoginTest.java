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
    private CourierCredentials randomCourierCredentials;

    private Courier courierUrl;

    @Before
    public void setUp() {
        courierUrl = new Courier();
        courier = new Courier();
        courierCredentials = Courier.randomCourier();
        courier.createCourier(courierCredentials);
        randomCourierCredentials = new CourierCredentials(Courier.randomString(7), Courier.randomString(7));
    }


    @After
    public void deleteCourier () {
        CourierCredentials loginCredentials = courier.getLoginCourier(courierCredentials).as(CourierCredentials.class);
        courier.deleteCourier(loginCredentials.getId());
    }

        @Test
        @DisplayName("Логинимся")
        @Description("Проверяем что курьер может авторизоваться")
        public void loginCourier () {
            Response loginResponse = courier.getLoginCourier(courierCredentials);
            loginResponse
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue());
        }
        @Test
        @DisplayName("Входим без пароля")
        @Description("Проверяем что нельзя войти без пароля")
        public void loginCourierWithoutLogin () {
            CourierCredentials credentialsWithoutPassword = new CourierCredentials(courierCredentials.getLogin(), "");
            Response response = courier.getLoginCourier(credentialsWithoutPassword);
            response
                    .then()
                    .assertThat()
                    .statusCode(400)
                    .body("message", equalTo("Недостаточно данных для входа"));
        }
        @Test
        @DisplayName("Входим с не правильным логином")
        @Description("Проверяем что нельзя войти если дынные при логине не верные")
        public void loginCourierWrongLogin () {
            Response response = courier.getLoginCourier(randomCourierCredentials);
            response
                    .then()
                    .assertThat()
                    .statusCode(404)
                    .body("message", equalTo("Учетная запись не найдена"));
        }
}
