package store.shop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId(); // id를 반환하는 이유 : 커맨드랑 쿼리랑 분리 (저장을 하고나면 사이드 이펙트를 줄이기 위해 리턴값을 최대한 줄여야 함)
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}