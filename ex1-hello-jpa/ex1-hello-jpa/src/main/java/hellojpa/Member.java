package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "USER") //if db의 table name과 클래스 명이 다를 경우
//일반적으로 DB의 Table 이름과 클래스 이름이 같을 경우 디폴트로 매핑된다.
public class Member {
//    @Column(name = "username") //if db의 column name과 객체의 필드명이 다를 경우
    private String name;
    @Id
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
