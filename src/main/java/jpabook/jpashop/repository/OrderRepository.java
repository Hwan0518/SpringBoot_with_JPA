package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    //저장
    public void save(Order order) {
        em.persist(order);
    }

    //id로 검색
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    //전체 조회
//    public List<Order> findAll(OrderSearch orderSearch){}

}
