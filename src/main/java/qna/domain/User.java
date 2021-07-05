package qna.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.lang.NonNull;
import qna.exception.UnAuthorizedException;

@Entity
public class User {

    @NonNull
    @Column(nullable = false)
    private final LocalDateTime created_at = LocalDateTime.now();

    private final LocalDateTime updated_at = LocalDateTime.now();

    @OneToMany(mappedBy = "writer", orphanRemoval = true)
    private final List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "deletedUser", orphanRemoval = true)
    private final List<DeleteHistory> deleteHistories = new ArrayList<>();

    @OneToMany(mappedBy = "writer", orphanRemoval = true)
    private final List<Question> questions = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(length = 20, nullable = false)
    private String userId;

    @NonNull
    @Column(length = 20, nullable = false)
    private String password;

    @NonNull
    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50)
    private String email;

    protected User() {
    }

    public User(String userId, String password, String name, String email) {
        this(null, userId, password, name, email);
    }

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void update(User target) {
        if (!matchUserId(this.userId)) {
            throw new UnAuthorizedException();
        }

        if (!matchPassword(target.password)) {
            throw new UnAuthorizedException();
        }

        this.name = target.name;
        this.email = target.email;
    }

    public boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchPassword(String targetPassword) {
        return this.password.equals(targetPassword);
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return name.equals(user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", created_at=" + created_at +
            ", updated_at=" + updated_at +
            '}';
    }
}
