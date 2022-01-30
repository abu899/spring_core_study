package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    // 실무에서는 static 처럼 공유되는 변수는 concurrent hashmap을 사용하는게 일반적.
    private static Map<Long, Member> store = new HashMap<>();
    // key 값을 선언해주는 애. 동시성 문제를 고려하긴 해야함.
    private static long sequence = 0L;
    @Override
    public Member save(Member member) {
        member.setId(++sequence);;
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
