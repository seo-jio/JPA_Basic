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

        tx.begin();
        try{
            Team teamA = new Team();
            teamA.setName("TEAM_A");
            em.persist(teamA);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setTeam(teamA);
            member.setUserType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

            //파라미터 바인딩, jpql은 엔티티 프로젝션 시 영속성 컨텍스트로 관리한다.
//            List<Member> result = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                            .setParameter("username", "seojio")
//                            .getResultList();

            //스칼라 타입 new를 통해 받아오기(클래스 생성)
//            List<MemberDTO> resultDTO =  em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
//                    .getResultList();
//            for (MemberDTO memberDTO : resultDTO) {
//                System.out.println("memberDTO = " + memberDTO);
//            }

            //페이징
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                            .setFirstResult(0)
//                            .setMaxResults(10)
//                            .getResultList();

            //조인
//            String query = "select m from Member m inner join m.team t"; //inner join
//            String query = "select m from Member m left join m.team t"; //left join
//            String query = "select m from Member m, Team t where m.username = t.name"; //seta join
//            String query = "select m from Member m left join m.team t on t.name = 'TEAM_A'"; //join on
//
            //sub query
//            String query = "select m from Member m where m.age > (select avg(m2.age) from Member m2)";

            //jpql 한계 : from 절에 서브 쿼리 사용 불가
//            String query = "select o from Order o where o.orderAmount > all (select p.stockAmount from Product p)"; //exist, any all, not
//            List<Member> result = em.createQuery(query,Member.class)
//                            .getResultList();

            //jpql 타입 표현과 기타식
//            String query = "select m.username, 'Hello', true from Member m where m.userType = :userType";
//            List<Object[]> result = em.createQuery(query)
//                            .setParameter("userType", MemberType.ADMIN)
//                            .getResultList();
//
//            for (Object[] objects : result) {
//                System.out.println("objects[0] = " + objects[0]);
//                System.out.println("objects[0] = " + objects[1]);
//                System.out.println("objects[0] = " + objects[2]);
//            }

            //조건식
//            String query = "select " +
//                                "case when m.age <= 10 then '학생요금' " +
//                                "     when m.age >= 60 then '경로 요금' " +
//                                "     else '일반요금' " +
//                                "end " +
//                        "from Member m";
//
//            String query = "select coalesce(m.username, '이름없는 회원') from Member m";
//            List<String> result = em.createQuery(query, String.class)
//                            .getResultList();
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            //jpql 함수(ClassCastExceptino 오류 발생)
//            String query = "select size(t.members) from Team t";
//            List<Integer> result = em.createQuery(query, Integer.class)
//                    .getResultList();
//
//          
//          for (Integer integer : result) {
//                System.out.println("integer = " + integer);
//            }


            
            tx.commit();
        }catch (Exception e){
            tx.rollback();  //이전 트랜젝션으로 롤백
            e.printStackTrace();
        }finally {
            em.close();

        }

        emf.close();
    }
}
