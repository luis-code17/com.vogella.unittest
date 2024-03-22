package email;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidatorTest {

    @Test
    void ensureThatEmailValidatorReturnsTrueForValidEmail() {
        assertTrue(email.EmailValidator.isValidEmail("lars.vogel@gmail.com"));
    }

    @Test
    void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(email.EmailValidator.isValidEmail("lars.vogel@analytics.gmail.com"));
    }

    @Test
    void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(email.EmailValidator.isValidEmail("lars.vogel@gmail"));
    }

    @Test
    void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertTrue(email.EmailValidator.isValidEmail("lars..vogel@gmail.com"));
        assertFalse(email.EmailValidator.isValidEmail("lars..vogel@gmail..com"));
    }

    @Test
    void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(email.EmailValidator.isValidEmail("@gmail.com"));
    }

    @Test
    void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(email.EmailValidator.isValidEmail(""));
    }

    @Test
    void emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(email.EmailValidator.isValidEmail(null));
    }

}
