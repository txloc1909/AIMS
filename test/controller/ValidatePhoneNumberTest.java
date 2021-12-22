package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidatePhoneNumberTest {
	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		this.placeOrderController = new PlaceOrderController();
	}


	@ParameterizedTest
	@CsvSource({
			"0123456789, true",
			"03943, false",
			"0sd345785, false",
			"098765432111, false",
			"1234567890, false",
			", false"
	})
	void testValidatePhoneNumber(String phone, boolean expected) {
		boolean isValid = placeOrderController.validatePhoneNumber(phone);
		assertEquals(expected, isValid);
	}

}
