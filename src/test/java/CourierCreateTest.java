import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class CourierCreateTest {
    private Courier courier;
    private CourierCredentials courierCredentials;

    @Before
    public void setUp() {
        RestAssured.baseURI = Courier.BASE_URL;
        courier = new Courier();
        courierCredentials = new CourierCredentials("alex", "1234", "kot");
        courierCredentials = new CourierCredentials(courierCredentials.getLogin(), courierCredentials.getPassword());
    }

    @After
    public void deleteCourier() {
        CourierCredentials loginCredentials = courier.getLoginCourier(courierCredentials).as(CourierCredentials.class);
        courier.deleteCourier(loginCredentials.getId());
    }

    @Test
    @DisplayName("Создание нового пользователя")
    @Description("Проверяем создание нового пользователя")
    public void createNewCourier(){
        Response createNewCourier = courier.getCreateCourier(courierCredentials);
        createNewCourier
                .then()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создаем пользователя без login")
    @Description("Проверяем создание пользователя без обязательных полей")
    public void createCourierWithoutLogin(){
        courierCredentials.setLogin("");
        Response CreateCourierWithoutLogin = courier.getCreateCourier(courierCredentials);
        CreateCourierWithoutLogin
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создаем двух одинаковых пользователей")
    @Description("Проверяем создание двух одинаковых пользователей")
    public void createRecurringLogin(){
        courier.createCourier(courierCredentials);
        Response createRecurringLogin = courier.getCreateCourier(courierCredentials);
        createRecurringLogin
                .then()
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}