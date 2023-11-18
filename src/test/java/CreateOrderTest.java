import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String[] color;
    public CreateOrderTest(String[] color) {
        this.color = color;
    }
    private static final String ORDER_PATH = "/api/v1/orders";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static Object[][] getOrder() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK, GREY"}},
                {new String[]{""}}
        };
    }
    @Test
    @DisplayName("Создаем заказ")
    @Description("Проверяем создание заказа")
    public void createOrder() {
        Order credentialsOrder = new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", color);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(credentialsOrder)
                .when()
                .post(ORDER_PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}