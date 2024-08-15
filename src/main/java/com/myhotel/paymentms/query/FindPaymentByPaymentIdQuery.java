package com.myhotel.paymentms.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindPaymentByPaymentIdQuery {
    private String paymentId;
}
