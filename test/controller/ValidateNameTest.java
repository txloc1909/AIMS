package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateNameTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        this.placeOrderController = new PlaceOrderController();
    }


    @ParameterizedTest
    @CsvSource({
            "tran xuan loc, true",
            "tr4n xu4n l0c, false",
            "xu#n l$c, false",
            ", false"
    })
    void testValidatePhoneNumber(String name, boolean expected) {
        boolean isValid = placeOrderController.validateName(name);
        assertEquals(expected, isValid);
    }

}
