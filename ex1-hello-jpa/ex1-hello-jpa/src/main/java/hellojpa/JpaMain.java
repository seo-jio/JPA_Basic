package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            Member member1 = new Member();
            member1.setUsername("Member1");
//            member.setRoleType(RoleType.ADMIN);

            Member member2 = new Member();
            member2.setUsername("Member1");

            Member member3 = new Member();
            member3.setUsername("Member1");

            System.out.println("===========");
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            System.out.println("member1.getID() = " + member1.getId());
            System.out.println("member2.getID() = " + member2.getId());
            System.out.println("member3.getID() = " + member3.getId());
            System.out.println("===========");

//            db 수정 시em.persist(member)를 사용하지 않는다, beacause 변경 감지
//            em.flush(); //영속성 컨텍스트의 변경 내용을 db에 동기화(컨텍스트를 비우는게 아님!) / transaction commit, jpql 실행 시 자동 flush됨
//            em.clear(); //영속성 컨텍스트를 비워준다(테스트 시 사용)
            tx.commit(); //db에 반영
        }catch (Exception e){
            tx.rollback();  //이전 트랜젝션으로 롤백
        }finally {
            em.close();
        }

        emf.close();
    }
}
