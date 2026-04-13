package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.Impression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

class ImpressionRepositoryTest extends Base {

    @Autowired
    ImpressionRepository impressionRepository;

    @Test
    void all(){
        // given
        Impression impression1 = Fixtures.newImpression();
        impressionRepository.save(impression1);
        Impression impression2 = Fixtures.newImpression();
        impressionRepository.save(impression2);

        // when
        int taille = impressionRepository.all().size();

        // then
        assertThat(taille).isEqualTo(2);
    }
}
