import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

        Instant bz = LocalDateTime.now().toLocalDate().atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();
        Order newOrder = new Order(UUID.randomUUID().toString(), BestellStatus.PROCESSING, products, bz);

        return orderRepo.addOrder(newOrder);
    }

    List<Order> getAllOrdersByOrderState(BestellStatus status){
        return this.orderRepo.getOrders().stream()
                .filter(o -> o.bestellStatus().equals(status))
                .toList();
    }

    Optional<Order> updateOrder(BestellStatus status, String orderId){
        Optional<Order> oldStateOrder;

        oldStateOrder = this.orderRepo.getOrders()
                .stream()
                .filter(o -> o.id().equals(orderId))
                .findFirst();

        if(oldStateOrder.isEmpty()){
            return oldStateOrder;
        }
        else {
            orderRepo.removeOrder(oldStateOrder.get().id());
            return Optional.ofNullable(orderRepo.addOrder(oldStateOrder.get().withBestellStatus(status)));
        }
    }
}
