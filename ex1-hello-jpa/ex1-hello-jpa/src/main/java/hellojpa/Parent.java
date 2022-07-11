package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    //영속성 전이는 특정 클래스가 딱 하나의 주인만을 가질 때(참조하는 곳이 하나일 경우) 사용한다. ex)게시글과 첨부파일
    //orphanRemoval을 사용하면 컬렉션에서 삭제 시 엔티티를 db에서 삭제한다. ex) parent 삭제 시 자식들 또한 삭제
    //영속성 전이와 orphanRemoval 모두 사용 시 주인이 자식 엔티티의 생명주기를 관리한다.
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child){  //연관관계 편의 메소드
        childList.add(child);
        child.setParent(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
