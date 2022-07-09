package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50)

public class Member extends BaseEntity{
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) MySql에서 주로 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, //Oracle에서 주로 사용
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;


    //DB 설계 시 단방향으로 먼저 해놓고 필요시 양방향으로 변환한다
    @ManyToOne  // 연관관계의 주인은 외래 키의 위치를 기준으로 정한다! (외래키가 있으면 주인)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Column(name = "name", nullable = false)
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)//무조건 EnumType.STRING 사용!
    private RoleType roleType;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;

    //이전에는 위에 보이는 @Temporal을 사용하였지만 현재는 LocalDateTime을 사용
//    private LocalDate testLocalDate;
//    private LocalDateTime testLocalDateTime;

    @Lob //문자는 Clob, 나머지는 Blob
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
