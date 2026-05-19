import lombok.With;
import java.time.Instant;

import java.util.List;

public record Order(
        String id,
        @With
        BestellStatus bestellStatus,
        List<Product> products,
        Instant bestellDatum) {
}
