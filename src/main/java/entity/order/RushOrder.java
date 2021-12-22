package entity.order;

public class RushOrder extends Order {
    private String scheduledDeliveryTime;

    public String getScheduledDeliveryTime() { return this.scheduledDeliveryTime; }
    public void setScheduledDeliveryTime(String scheduledDeliveryTime) {
        this.scheduledDeliveryTime = scheduledDeliveryTime;
    }
}
