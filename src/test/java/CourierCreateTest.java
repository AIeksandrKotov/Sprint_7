import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class CourierCreateTest {
    private static final String COURIER_PATH = "api/v1/courier";

    CourierCredentials createCourier = new CourierCredentials("alex", "1234", "kot");
    CourierCredentials CreateWithoutLogin = new CourierCredentials("", "1234", "kot");

    @Before
    public void setUp() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Создание нового пользователя")
    @Description("Проверяем создание нового пользователя")
    public void createNewCourier(){
        given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true))
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создаем пользователя без login")
    @Description("Проверяем создание пользователя без обязательных полей")
    public void CreateCourierWithoutLogin(){
        given()
                .header("Content-type", "application/json")
                .and()
                .body(CreateWithoutLogin)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создаем двух одинаковых пользователей")
    @Description("Проверяем создание двух одинаковых пользователей")
    public void CreateRecurringLogin(){
        given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post(COURIER_PATH);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}