package flyopen_backend.dto;

import flyopen_backend.entity.Component;
import java.math.BigDecimal;

public record ComponentDTO(
        Long id,
        String name,
        String reference,
        String material,
        BigDecimal weightKg,
        Integer stock,
        String description) {

    public static ComponentDTO from(Component c) {
        return new ComponentDTO(c.getId(), c.getName(), c.getReference(),
                c.getMaterial(), c.getWeightKg(), c.getStock(), c.getDescription());
    }
}
