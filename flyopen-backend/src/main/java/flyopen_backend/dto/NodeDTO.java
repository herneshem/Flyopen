package flyopen_backend.dto;

import flyopen_backend.entity.Component;
import flyopen_backend.entity.DroneNode;

public record NodeDTO(Long id, Long parentId, String slotType, int quantity,
                      Long componentId, String componentName) {

    public static NodeDTO from(DroneNode n) {
        Component c = n.getComponent();
        return new NodeDTO(n.getId(),
                n.getParent() != null ? n.getParent().getId() : null,
                n.getSlotType(), n.getQuantity(),
                c != null ? c.getId() : null,
                c != null ? c.getName() : null);
    }
}
