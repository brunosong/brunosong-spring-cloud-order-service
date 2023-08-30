package com.study.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/* 테이블에 저장되는 컬럼에 정보를 넣는다. */
@Data
@AllArgsConstructor
public class Field {

    private String type;
    private boolean optional;
    private String field;

}
