import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateOrderTest {
   private Order order;
    private final String[] color;
    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Courier.BASE_URL;
        order = new Order();

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
        OrderCredentials orderCredentials  = new OrderCredentials ("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", color);
        Response response = order.getOrder(orderCredentials);
        response.then()
                .statusCode(201)
                .assertThat().body("track", Matchers.notNullValue());

    }
}