import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", BestellStatus.PROCESSING, List.of(new Product("1", "Apfel")));
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
        assertEquals(BestellStatus.PROCESSING, actual.bestellStatus());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void getAllOrdersByOrderState_Completed_EmptyOrdersList() {
        //GIVEN
        ShopService shopService = new ShopService();
        //WHEN
        List<Order> actual = shopService.getAllOrdersByOrderState(BestellStatus.COMPLETED);
        //THEN
        assertEquals(new ArrayList<Order>(), actual);
    }

    @Test
    void getAllOrdersByOrderState_Completed_OrdersListwithProcessingStateOrder() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);

        //WHEN
        List<Order> actual = shopService.getAllOrdersByOrderState(BestellStatus.PROCESSING);
        //THEN
        assertEquals(BestellStatus.PROCESSING, actual.getFirst().bestellStatus());
    }
}
