package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // 수동빈으로 넣어주는 방식과는 다르게 컴포넌트 스캔은 연관관계 주입이 부족하므로 Autowired필수
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Autowired // 컴포넌트 스캔의 경우 의존관계 부분을 자동으로 넣어주라는 어노테이션이 필수다
    // MemberRepository의 구현체인 memoryMemberRepository를 주입해준다(즉 해당 가상체를 구현한 클래스가 후보군이다 여럿이면 에러)
    // getBean(MemberRepository.class)처럼 조회한다고 생각하자
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트용
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
