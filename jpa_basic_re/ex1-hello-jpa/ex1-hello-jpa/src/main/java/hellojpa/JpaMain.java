package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        // EntityManagerFactory는 application 로딩 시점에 딱 한개만 생성해서 사용
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 하나의 트랜잭션(작업)마다 생성하여 사용
        // jpa에서 데이터를 변경하는 모든 작업은 트랜젝션 안에서 작업해야 한다.
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin(); // 트랜젝션 시작

        try {
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("helloJpa");
            
            tx.commit(); // db에 변경 사항 반영
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
