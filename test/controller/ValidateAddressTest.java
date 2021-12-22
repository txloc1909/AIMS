package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ValidateAddressTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        this.placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "Hanoi, true",
            "So 1 Dai Co Viet, true",
            "S0 ! D@i C& Viet, false",
            ", false"
    })
    void testValidateAddress(String address, boolean expected) {
        boolean isValid = placeOrderController.validateAddress(address);
        assertEquals(expected, isValid);
    }
}
