package com.study.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/* 테이블에 이름과 필드들에 정보 및 타입 정보를 넣는다. */
@Data
@Builder
public class Schema {

    private String type;
    private List<Field> fields;
    private boolean optional;
    private String name;

}
