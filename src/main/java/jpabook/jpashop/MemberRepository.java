package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    // 저장 기능
    public Long save(Member member) {
        em.persist(member);
        return member.getId(); // 커맨드와 쿼리를 분리해라 >>
    }

    // 조회 기능
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
