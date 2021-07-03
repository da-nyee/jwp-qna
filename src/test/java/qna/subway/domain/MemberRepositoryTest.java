package qna.subway.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository members;

    @Autowired
    private FavouriteRepository favourites;

    @Test
    void save() {
        Member member = new Member("jason");
        member.addFavourite(favourites.save(new Favourite()));
        members.save(member);
    }

    @AfterEach
    void afterEach() {
        members.flush();
    }
}