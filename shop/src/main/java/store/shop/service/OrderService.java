package store.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.shop.domain.Delivery;
import store.shop.domain.Member;
import store.shop.domain.Order;
import store.shop.domain.OrderItem;
import store.shop.domain.item.Item;
import store.shop.repository.ItemRepository;
import store.shop.repository.MemberRepository;
import store.shop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);

        order.cancel();
    }

    public Order findOne(Long orderId) {
        return orderRepository.findOne(orderId);
    }

    // 검색
    //public List<Order> findOrders(OrderSearch orderSearch) {
    //    return orderRepository.findAll(orderSearch);
    //}
}