package hello.purejava.member;

public class MemberServiceImpl implements MemberService{

    // 다형성이지만 OCP 위반, 추상체에도 구현체에도 의존
    private final MemberRepository memberRepository;

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
}
