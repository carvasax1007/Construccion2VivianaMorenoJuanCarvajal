package app.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelOrderRequest {
    private long veterinaryDocument;
    private long orderId;
} 