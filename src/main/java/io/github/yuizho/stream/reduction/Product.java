package io.github.yuizho.stream.reduction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode()
public class Product {
    private final int type;
    @Setter
    private int value;

    public Product(int type, int value) {
        this.type = type;
        this.value = value;
    }
}
