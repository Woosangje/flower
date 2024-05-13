package com.flower.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;      // 하나의 상품은 여러 주문 상품으로 들어갈 수 있음

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;        // 한번의 주문에 여러개의 상품을 주문할 수 있음

    private int orderPrice; //주문가격

    private int count; //수량

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);    // 주문할 상품과 주문 수량을 세팅
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice()); // 현재 시간 기준으로 상품 가격을 주문 가격으로 셋팅, 상품 가격은 시간에 따라서 달라짐
        // 쿠폰 할인을 적용하는 케이스도 있음
        item.removeStock(count); // 주문 수량 만큼 재고 수량을 감소
        return orderItem;
    }

    public int getTotalPrice(){  // 주문 가격과 주문 수량을 곱해서 해당 상품을 주문한 총 가격을 계산
        
        return orderPrice*count;
    }

    public void cancel() { // 주문 취소 322

        this.getItem().addStock(count);
    }

}