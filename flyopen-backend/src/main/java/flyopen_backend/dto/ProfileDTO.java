package flyopen_backend.dto;

import flyopen_backend.entity.MissionProfile;

import java.util.List;

public record ProfileDTO(Long id, String code, String name, List<SlotDTO> slots) {

    public static ProfileDTO from(MissionProfile p) {
        return new ProfileDTO(p.getId(), p.getCode(), p.getName(),
                p.getSlots().stream()
                        .map(s -> new SlotDTO(s.getComponentType(), s.getParentType(), s.getQuantity()))
                        .toList());
    }
}
