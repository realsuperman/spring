package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args){
        //AppConfig appConfig = new AppConfig();
        //MemberService memberService = appConfig.memberService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 스프링은 모든게 ApplicationContext로 시작함(참고로 이건 스프링 컨테이너라고 생각하자)
        // new 뒤에 AnnotationConfigApplicationContext로 선언 후 인자에 설정정보가 있는 클래스를 적는다
        // 이렇게 설정하면 객체를 생성 후 스프링 컨테이너에 넣어준다
        MemberService memberService = applicationContext.getBean("memberService",MemberService.class); // 스프링 컨테이너에서 memberService라는 빈을 꺼낸다 두번째 인자는 타입이다

        Member member = new Member(1L,"memberA", Grade.VIP);
        memberService.join(member);
        Member findMember = memberService.findMember(1L);
        if(member.getName().equals(findMember.getName())) System.out.println("같다");
    }
}
