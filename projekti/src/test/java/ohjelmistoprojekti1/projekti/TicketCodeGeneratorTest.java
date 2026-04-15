package ohjelmistoprojekti1.projekti;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ohjelmistoprojekti1.projekti.util.TicketCodeGenerator;

class TicketCodeGeneratorTest {

    @Test
    void shouldGenerateNonNullCode() {
        String code = TicketCodeGenerator.generateCode();

        assertNotNull(code);
        assertFalse(code.isBlank());
    }

    @Test
    void shouldGenerateUniqueCodes() {
        String code1 = TicketCodeGenerator.generateCode();
        String code2 = TicketCodeGenerator.generateCode();

        assertNotEquals(code1, code2);
    }
}