package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest //스프링을 돌려버림
@Transactional //DB의 트랜잭션 활용, 테스트 과정을 롤백
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입테스트() {
        //given
        Member member = new Member();
        member.setName("sungkyu");

        //when
        Long saveId = memberService.join(member);

        //then
        Optional<Member> findMember = memberService.findOne(saveId);
        Assertions.assertThat(member.getId()).isEqualTo(findMember.get().getId());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("sungkyu");
        Member member2 = new Member();
        member2.setName("sungkyu");

        //when
        memberService.join(member1);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //then
//        try {
//            memberService.join(member2);
//            fail();
//        } catch(IllegalArgumentException e) {
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
    }
}