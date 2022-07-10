package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        //등록, 조회 하기 전까지는 비영속 상태, 그 후는 영속상태(엔티티가 영속성 컨텍스트 안에 존재)라고 부른다.
        //영속성 컨텍스트를 객체와 db 사이에 넣어서 얻을 수 있는 이점
        //1. 1차 캐쉬
        //2. 영속 엔티티의 동일성 보장
        //3. 쓰기 지연 SQL 저장소를 통해 SQL이 트랜잭션 커밋시 db에 날아가도록 한다(버퍼링).
        //4. 변경 감지 : 엔티티 수정시 다시 em.persist(member)를 하지 않아도 jpa가 1차 캐시 내 엔티티와 스냅샷(초기 모습)을 비교하여
        //변경이 있을 경우 sql 저장소에 update query를 날린다.
        try{
//            GeneratedValue 예제
//            Member member1 = new Member();
//            member1.setUsername("Member1");
////            member.setRoleType(RoleType.ADMIN);
//
//            Member member2 = new Member();
//            member2.setUsername("Member1");
//
//            Member member3 = new Member();
//            member3.setUsername("Member1");
//
//            System.out.println("===========");
//            em.persist(member1);
//            em.persist(member2);
//            em.persist(member3);
//
//            System.out.println("member1.getID() = " + member1.getId());
//            System.out.println("member2.getID() = " + member2.getId());
//            System.out.println("member3.getID() = " + member3.getId());
//            System.out.println("===========");

//            db 수정 시em.persist(member)를 사용하지 않는다, beacause 변경 감지
//            em.flush(); //영속성 컨텍스트의 변경 내용을 db에 동기화(컨텍스트를 비우는게 아님!) / transaction commit, jpql 실행 시 자동 flush됨
//            em.clear(); //영속성 컨텍스트를 비워준다(테스트 시 사용)

              //연관관계 매핑 예제
//            Team teamA = new Team();
//            teamA.setName("TeamA");
//            em.persist(teamA);
//
//            Member member1 = new Member();
//            member1.setUsername("mem1");
//            em.persist(member1);
//
//            teamA.addMember(member1); //연관관계 편의 메소드(한쪽에만 생성)
//            em.flush();
//            em.clear();
//
//            Team findTeam = em.find(Team.class, teamA.getId());
//            List<Member> members = findTeam.getMembers();
//
//            System.out.println("================");
//            for (Member member : members) {
//                System.out.println("member.getUsername() = " + member.getUsername());
//            }
//            System.out.println("================");

            //상속관계 매핑 예제
//            Movie movie = new Movie();
//            movie.setActor("aaaa");
//            movie.setDirector("bbbb");
//            movie.setName("바람과 함께 사라지다");
//            movie.setPrice(10000);
//            em.persist(movie);
//            Movie findMovie = em.find(Movie.class, movie.getId());
//            System.out.println("findMovie.getName() = " + findMovie.getName());

            //MappedSuperclass 예제
//            Member member = new Member();
//            member.setUsername("userA");
//            member.setCreatedBy("seojio");
//            member.setCreatedDate(LocalDateTime.now());
//            em.persist(member);
//            em.flush();
//            em.clear();
            
            //Proxy 예제
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member m1 = new Member();
            m1.setUsername("seojio");
            em.persist(m1);

            m1.setTeam(team);git 

            //영속성 컨텍스트 안에 있는 엔티티는 프록시를 부르지 않고 엔티티를 바로 부른다.
            em.flush();
            em.clear();

            Member m = em.find(Member.class, m1.getId());

            System.out.println("m = " +  m.getTeam().getClass());


//            Member refMember = em.getReference(Member.class, m1.getId());
//            System.out.println("findMember.getClass() = " + refMember.getClass());
            //jpa가 실제 값이 필요한 경우에 db에 query를 날린다.
//            System.out.println("findMember.getUsername() = " + refMember.getUsername());

            //영속성 컨텍스트에서 detach, 혹은 영속성 컨텍스트를 clear 해버리면 더 이상 영속성 엔티티가 프록시를 관리하지 않아 오류 발생
//            em.detach(refMember);
//            em.close();
//
//            System.out.println(refMember.getUsername());

            //초기화 여부 확인 메소드
//            refMember.getUsername();
//            System.out.println(emf.getPersistenceUnitUtil().isLoaded(refMember));

            tx.commit(); //db에 반영
        }catch (Exception e){
            tx.rollback();  //이전 트랜젝션으로 롤백
            e.printStackTrace();
        }finally {
            em.close();

        }

        emf.close();
    }
}
