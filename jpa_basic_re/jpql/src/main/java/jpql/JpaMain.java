package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜젝션 시작

        try {
//            for (int i = 0; i < 100; i++) {
//                Member member = new Member();
//                member.setUsername("memeber" + i);
//                member.setAge(i);
//                em.persist(member);
//            }
//
//            em.flush();
//            em.clear();
//
//            List<MemberDto> result = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member as m order by m.age desc", MemberDto.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            System.out.println("res = " + result.size());
//            for (MemberDto memberDto : result) {
//                System.out.println("memberDto = " + memberDto);
//            }

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(1);

            Team team = new Team();
            team.setName("teamA");

            member.changeTeam(team);

            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member m join m.team t", Member.class)
                    .getResultList();

            System.out.println("result.size() = " + result.size());

            tx.commit(); // db에 변경 사항 반영
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
