package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    //ManyToOne, OneToOne은 디폴트가 EAGER(즉시로딩)이므로 Lazy로 수동으로 바꿔줘야 한다.
    @ManyToOne(fetch = FetchType.LAZY)  // 연관관계의 주인은 외래 키의 위치를 기준으로 정한다! (외래키가 있으면 주인)
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

//    @Embedded
//    private Period workPeriod;
//
//    @Embedded
//    private Address homeAddress;

    //값 타입 컬렉션
    //지연로딩 전략을 사용하고 영속성전이, 고아객체 기능을 필수로 가짐
//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>();

    //실무에서는 값 타입 컬렉션이 아닌 엔티티를 사용해 일대다 관계로 풀어낸다.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

    //    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "city", column = @Column("WORK_CITY")),
//            @AttributeOverride(name = "street", column = @Column("WORK_STREET")),
//            @AttributeOverride(name = "zipcode", column = @Column("WORK_ZIPCODE"))
//    })
//    private Address workAddress;


    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }

//    public Period getWorkPeriod() {
//        return workPeriod;
//    }
//
//    public void setWorkPeriod(Period workPeriod) {
//        this.workPeriod = workPeriod;
//    }
//
//    public Address getHomeAddress() {
//        return homeAddress;
//    }
//
//    public void setHomeAddress(Address homeAddress) {
//        this.homeAddress = homeAddress;
//    }

    //    @Enumerated(EnumType.STRING)//무조건 EnumType.STRING 사용!
//    private RoleType roleType;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;

    //이전에는 위에 보이는 @Temporal을 사용하였지만 현재는 LocalDateTime을 사용
//    private LocalDate testLocalDate;
//    private LocalDateTime testLocalDateTime;


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


}
