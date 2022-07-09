package jpabook.jpashop;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

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



        try{
            //연관관계 매핑 예제
//            Member memberA = new Member();
//            memberA.setName("memA");
//            em.persist(memberA);
//
//            Order order = new Order();
//            order.setMember(memberA);
//
//            order.addOrderItem(new OrderItem());
//            em.persist(order);
//
//            System.out.println("=========");
//            for (OrderItem orderItem : order.getOrderItems()) {
//                System.out.println(orderItem);
//            }
//            System.out.println("=========");

            //상속관계 매핑 예제
            Book book = new Book();
            book.setAuthor("seojio");
            book.setIsbn(1234);
            em.persist(book);

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
