package com.study.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/* 카푸카에 값을 밀어넣기 위한 실질적인 규격 */
@Data
@AllArgsConstructor
public class KafkaOrderDto implements Serializable {

    private Schema schema;
    private Payload payload;

}
