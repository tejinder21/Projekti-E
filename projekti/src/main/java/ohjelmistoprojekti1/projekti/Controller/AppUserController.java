package ohjelmistoprojekti1.projekti.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ohjelmistoprojekti1.projekti.domain.AppUser;
import ohjelmistoprojekti1.projekti.dto.CreateUserRequest;
import ohjelmistoprojekti1.projekti.repository.AppUserRepository;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserRepository appUserRepository;

    public AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest req) {
        if (req.getUsername() == null || req.getUsername().isBlank()
                || req.getPasswordHash() == null || req.getPasswordHash().isBlank()
                || req.getRole() == null || req.getRole().isBlank()) {
            return ResponseEntity.badRequest().body("username, passwordHash and role are required");
        }

        // username unique -> jos sama username löytyy, palautetaan 409
        if (appUserRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(req.getUsername());
        user.setPasswordHash(req.getPasswordHash());
        user.setRole(req.getRole());

        AppUser saved = appUserRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}