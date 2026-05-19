import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder( List<String> productIds) throws InvalidProductException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                throw new InvalidProductException("Product Id: "+productId);
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), BestellStatus.PROCESSING, products);

        return orderRepo.addOrder(newOrder);
    }

    List<Order> getAllOrdersByOrderState(BestellStatus status){
        return this.orderRepo.getOrders().stream()
                .filter(o -> o.bestellStatus().equals(status))
                .toList();
    }
}
