package com.transport.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    ON_TIME((short) 20),
    LATE((short) -10),
    OWE((short) -20);
    private static final Map<Short, PaymentStatus> LOOKUP;
    private final short val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(PaymentStatus.values())
                .collect(Collectors.toMap(PaymentStatus::getVal, Function.identity())));
    }
}
