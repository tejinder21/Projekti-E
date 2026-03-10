package ohjelmistoprojekti1.projekti.dto;

public class CreateUserRequest {
    private String username;
    private String passwordHash; // testiin, ei oikea hashaus tässä
    private String role;         // ADMIN / LIPUNMYYJÄ / OVITARKASTAJA

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}