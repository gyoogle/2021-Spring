package store.shop.repository;

import lombok.Getter;
import lombok.Setter;
import store.shop.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}