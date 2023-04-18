package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 조회 기능에서는 readOnly=true를 하면 성능향상, 아닌곳에서는 따로 트렌젝션 설정
@RequiredArgsConstructor // final이 붙은 필드만 생성자를 만듦. 따라서 따로 생성자를 만들어줄 필요가 없다
public class MemberService {
    private final MemberRepository memberRepository;

/*
    // Repository 설정 : 생성자 인젝션
    @Autowired // 생성자가 하나만 있는경우는 생략해도 됨
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
*/


    /**
     * 회원 가입
     */
    @Transactional // 조회가 아닌 기능에서는 트렌젝션을 따로 설정해주면 됨, 디폴트값은 readOnly=false이다
    public Long join(Member member) {
        validateDuplicateMember(member); //멀티 쓰레드에서 동시에 이 로직을 통과한다면 여전히 중복문제 발생. 따라서 최후의 검사 or Member의 name을 unique제약조건으로..
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 이름으로 검사했을때 이미 존재한다면 중복된것임, 중복된다면 예외 발생
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 회원 1건 조회
     */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }




}
