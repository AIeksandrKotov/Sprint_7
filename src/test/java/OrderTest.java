import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderTest {
    private static final String ORDER_PATH = "/api/v1/orders";
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Список заказов")
    @Description("Проверяем список заказов")
    public void getOrder() {
        given()
                .header("Content-type", "application/json")
                .get(ORDER_PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}