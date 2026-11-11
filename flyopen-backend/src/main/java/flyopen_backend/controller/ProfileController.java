package flyopen_backend.controller;


import flyopen_backend.dto.ProfileDTO;
import flyopen_backend.service.ProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService){
        this.profileService = profileService;

    }

    @GetMapping
    public List<ProfileDTO> list() {
        return profileService.findAll();
    }
}
