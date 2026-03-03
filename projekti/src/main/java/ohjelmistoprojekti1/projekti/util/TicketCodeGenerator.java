package ohjelmistoprojekti1.projekti.util;

import java.security.SecureRandom;
import java.util.UUID;

public class TicketCodeGenerator {

    private static final SecureRandom random = new SecureRandom();

    // Generoi yksilöllinen ja helposti tarkastettava lippukoodi
    public static String generateCode() {
        // Käyttää UUID:ita ja tarkistussummaa
        String uniquePart = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        String timestampPart = Long.toHexString(System.currentTimeMillis()).toUpperCase();
        String randomPart = String.format("%04X", random.nextInt(65536));
        
        String code = uniquePart + timestampPart + randomPart;
        return code;
    }
}
