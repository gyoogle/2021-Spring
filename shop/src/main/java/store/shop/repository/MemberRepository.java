package store.shop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.shop.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository // 스프링 Bean으로 등록
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member); // 영속성 컨텍스트에 member 주입
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 회원 검색 - JPQL 사용
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
    
    // 이름으로 조회
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}