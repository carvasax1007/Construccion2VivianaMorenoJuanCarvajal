package app.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelOrderRequest {
    private long veterinaryDocument;
    private long orderId;

    public long getOrderId() { return orderId; }
    public long getVeterinaryDocument() { return veterinaryDocument; }
} 