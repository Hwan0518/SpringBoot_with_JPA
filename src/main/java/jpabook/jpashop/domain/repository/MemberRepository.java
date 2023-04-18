package jpabook.jpashop.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    // 회원 저장
    public void save(Member member) {
        em.persist(member);
    }

    // 회원 조회 : id로
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 회원 조회 : name으로
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where  m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    // 회원 목록 : 모든 회원
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }






}
