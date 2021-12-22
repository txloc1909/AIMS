package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CheckProvinceSupportRushOrderTest {
    private PlaceRushOrderController placeRushOrderController;

    @BeforeEach
    void setUp() throws Exception {
        this.placeRushOrderController = new PlaceRushOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "Hà Nội, true",
            "Ha Noi, false",
            "Hưng Yên, false",
            ", false"
    })
    void testCheckProvinceSupportRushOrderTest(String province, boolean expected) {
        boolean isSupported = placeRushOrderController.checkIfProvinceSupportRushOrder(province);
        assertEquals(expected, isSupported);
    }
}
