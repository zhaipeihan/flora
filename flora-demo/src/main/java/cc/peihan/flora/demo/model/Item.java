package cc.peihan.flora.demo.model;

import lombok.Data;

@Data
public class Item {

    private Long id;

    private String name;

    private Double price;

    private Integer isValid;
}
