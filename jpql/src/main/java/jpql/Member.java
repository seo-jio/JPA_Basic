package jpql;

import javax.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    private String username;
    private int age;

    @Enumerated(EnumType.STRING)
    private MemberType userType;

    public MemberType getUserType() {
        return userType;
    }

    public void setUserType(MemberType userType) {
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //toString 만들 때 연관관계 매핑 되어 있는 값은 빼야함(무한루프를 돌 수도 있음)
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
