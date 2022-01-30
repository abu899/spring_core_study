package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id); // 없으면 null이 나와야하지만 optional이라는걸로 감싸서 반환하는걸 선호
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
