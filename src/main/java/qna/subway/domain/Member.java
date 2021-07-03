package qna.subway.domain;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Favourite> favourites = new ArrayList<>();

    protected Member() {
    }

    public Member(String name) {
        this(null, name);
    }

    public Member(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public void addFavourite(Favourite favourite) {
        favourites.add(favourite);
    }
}
