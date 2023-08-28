package com.study.orderservice.vo;

import lombok.Data;

@Data
public class RequestOrder {

    private String productId;
    private Integer qty;
    private Integer unitPrice;   // 사용자로 부터 단가까지 부여 받는다고 설정... 이러면 안되긴 함.

}
