package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    NewRepository newRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        assertThat(findMember.getId()).isSameAs(member.getId());
        assertThat(findMember.getUsername()).isSameAs(member.getUsername());
        assertThat(findMember).isEqualTo(member);
            // 같은 트랜잭션 안에서 저장 및 조회를 하면 두 객체는 동일한 영속성 컨텍스트를 가지게 되고,
            // 동일한 영속성 컨텍스트 내에서는 id가 같으면 동일한 엔티티 객체로 인식한다


        // 새로운 엔티티 매니저를 만들어서 비교
        Long NewSavedId = newRepository.save(member);
        Member NewFindMember = newRepository.find(NewSavedId);
        // 다른 엔티티 매니저로 만들어진 객체들을 비교
        assertThat(savedId).isEqualTo(NewSavedId);
        assertThat(savedId).isSameAs(NewSavedId);
        assertThat(findMember).isEqualTo(NewFindMember);
        assertThat(findMember).isSameAs(NewFindMember);
        // >>> 같은 트랜잭션 내에서 수행됐으므로, 다른 EntityManager를 사용하더라도 동일한 영속성 컨텍스트를 가지게 된다!!!


    }




}
