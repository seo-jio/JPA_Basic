package jpql;

import javax.persistence.*;
import java.util.Collection;
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

            Team teamB = new Team();
            teamB.setName("TEAM_B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            member1.setTeam(teamA);
            member1.setUserType(MemberType.ADMIN);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(20);
            member2.setTeam(teamA);
            member2.setUserType(MemberType.ADMIN);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setAge(30);
            member3.setTeam(teamB);
            member3.setUserType(MemberType.ADMIN);
            em.persist(member3);

            //벌크 연산 수행 시 clear 주의
            em.flush();
//            em.clear();

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

            //경로 표현식
//            String query = "select m.team from Member m"; //단일 값 연관경로(묵시적 조인 발생)
//            String query = "select t.members from Team t"; //컬렉션 값 연관경로(묵시적 조인 발생)
//            String query = "select m.username from Team t join t.members m"; //컬렉션은 탐색이 안되서 명시적 조인과 별칭 사용
//            List<String> result = em.createQuery(query, String.class)
//                            .getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            //패치 조인
            //일반 조인은 select 절에 있는 엔티티만 조회, 패치 조인은 연관된 엔티티도 함께 조회(즉시 로딩)
            //패치 조인은 객체 그래프를 sql 한번에 조회하는 개념
            //일반 조인에서 발생하는 n+1 문제를 해결 가능하다.
            //별칭 사용 X
            //실무에서 글로벌 로딩 전략을 모두 지연로딩으로 지정한 후 최적화가 필요한 곳에서만 패치 조인을 사용하여 성능을 관리한다.
            //여러 테이블을 조회하여 엔티티가 가진 모양이 아닌 새로운 모양을 필요로 한다면 new dto 사용
//            String query = "select m from Member m join fetch m.team"; //기본 패치 조인

            //distinct 사용 시 단순히 sql에서 distinct 역할을 할 뿐만 아니라 식별자가 같은 엔티티를 삭제하는 기능까지 수행한다.
            //컬렉션 패치 조인(일대다 매핑일 경우 테이블 뻥튀기 현상 발생!)
            //컬렉션 패이 조인 사용 시 페이징 사용 불가!
//            String query = "select distinct t from Team t join fetch t.members";
//            List<Team> result = em.createQuery(query, Team.class)
//                            .getResultList();
//
//            for (Team team : result) { //team은 두갠데 member가 3명이라 3개의 출력문 발생
//                System.out.println("team.getName() = " + team.getName() + ", count : " + team.getMembers().size());
//            }

            //엔티티 직접 사용
//            String query = "select m from Member m where m = :member"; //기본 키 사용
//            String query = "select m from Member m where  m.team= :team"; //외래 키 사용
//            List<Member> result = em.createQuery(query, Member.class)
//                    .setParameter("team", teamA)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getUsername() = " + member.getUsername());
//            }

            //named query(실무에선 spring data jpa로 사용)
//            Member result = em.createNamedQuery("Member.findByUsername", Member.class)
//                    .setParameter("username", "member1")
//                    .getSingleResult();
//
//            System.out.println("result = " + result);


            //벌크 연산
            //벌크연산을 먼저 실행 or 벌크 연산 수행 후 영속성 컨텍스트를 초기화 해야한다(db의 값이랑 영속성 컨텍스트의 값의 불일치 가능성이 있어서)
            //jpql을 날렸으므로 flush 자동 호출
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            em.clear(); //벌크 연산 수행 후 clear() 수동 호출 필수!

            Member findMember1 = em.find(Member.class, member1.getId());
            System.out.println("findMember1.getAge() = " + findMember1.getAge());


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
