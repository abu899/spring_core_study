package hello.purejava.member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
