import java.util.List;

public record Order(
        String id,
        BestellStatus bestellStatus,
        List<Product> products
) {
}
