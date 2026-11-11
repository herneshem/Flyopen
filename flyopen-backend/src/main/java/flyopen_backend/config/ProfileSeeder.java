package flyopen_backend.config;

import flyopen_backend.entity.MissionProfile;
import flyopen_backend.entity.ProfileSlot;
import flyopen_backend.repository.MissionProfileRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.CommandLineRunner;

@org.springframework.stereotype.Component
public class ProfileSeeder implements CommandLineRunner {

    private final MissionProfileRepository profileRepository;

    public ProfileSeeder (MissionProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (profileRepository.count() > 0) {
            return;
        }
        create("SURVEILLANCE", "Vigilancia", "Cámara");
        create("MAPPING", "Cartografía", "Cámara", "GPS");
        create("INSPECTION", "Inspección", "Cámara");
    }

    private void create(String code, String name, String... extraTypes) {
        MissionProfile profile = new MissionProfile();
        profile.setCode(code);
        profile.setName(name);

        addSlot(profile, "Chasis", null, 1);
        addSlot(profile, "Motor", "Chasis", 4);
        addSlot(profile, "Hélice", "Motor", 4);
        addSlot(profile, "ESC", "Chasis", 4);
        addSlot(profile, "Batería", "Chasis", 1);
        addSlot(profile, "Controladora", "Chasis", 1);
        for (String type : extraTypes) {
            addSlot(profile, type, "Chasis", 1);
        }
        profileRepository.save(profile);
    }

    private void addSlot(MissionProfile profile, String type, String parentType, int quantity) {
        ProfileSlot slot = new ProfileSlot();
        slot.setProfile(profile);
        slot.setComponentType(type);
        slot.setParentType(parentType);
        slot.setQuantity(quantity);
        profile.getSlots().add(slot);
    }
}
