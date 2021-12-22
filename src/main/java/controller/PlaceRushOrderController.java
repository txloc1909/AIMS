package controller;

import common.exception.InvalidDeliveryInfoException;
import entity.cart.Cart;
import entity.order.Order;
import entity.order.RushOrder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class PlaceRushOrderController extends PlaceOrderController {

    public void placeOrder() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();

        RushOrder order = createOrder();
    }

    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {
        super.validateDeliveryInfo(info);

        if (!checkIfDeliveryInfoSupportRushOrder(info))
            throw new InvalidDeliveryInfoException();
    }

    public boolean checkIfDeliveryInfoSupportRushOrder(HashMap info) {
        String province = (String) info.get("province");
        if (!checkIfProvinceSupportRushOrder(province))
            return false;

        // TODO: implement a way to check if an address support rush delivery

        return true;
    }

    private boolean checkIfProvinceSupportRushOrder(String province) {
        String[] supportProvinces = {"Hà Nội", "Hồ Chí Minh", "Đà Nẵng"};

        return Arrays.asList(supportProvinces).contains(province);
    }

    public RushOrder createOrder() throws SQLException {
        return (RushOrder) super.createOrder();
    }

    public int calculateShippingFee(Order order) {
        // TODO: implement rush order's shipping fee formula
        return (int)(super.calculateShippingFee(order) * 1.1);
    }

}
