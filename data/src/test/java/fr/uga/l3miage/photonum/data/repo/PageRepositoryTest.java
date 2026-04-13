package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

public class PageRepositoryTest extends Base{

    @Autowired
    PageRepository pageRepository;

    @Test
    void getPhotosFromPage(){
        //given
        Page page = Fixtures.newPage();
        pageRepository.save(page);
        // when
        int size = pageRepository.getPhotosFromPage(page.getId()).size();

        // then
        assertThat(size).isEqualTo(3);
    }
}

