package com.example.binarfud.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_item")
@SQLDelete(sql = "update menu_item set deleted = true where id =?")
@SQLRestriction("deleted = false")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_type")
    private Type type;

    @Column(name = "price_s")
    private Integer priceS;

    @Column(name = "price_m")
    private Integer priceM;

    @Column(name = "price_l")
    private Integer priceL;

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private boolean deleted = Boolean.FALSE;

    public enum Type {
        FOOD,
        BEVERAGE
    }

    public Integer getSizePrice(String size) {
        return switch (size) {
            case "S" -> priceS;
            case "M" -> priceM;
            case "L" -> priceL;
            default -> null;
        };
    }
}
