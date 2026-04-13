package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.Cadre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

public class CadreRepositoryTest extends Base {

    @Autowired
    CadreRepository cadreRepository;

    /*
    @Test
    void update() {
        // given
        final String tailleUpdate = "30x40";
        Cadre c1 = Fixtures.newCadre();
        c1.addPhoto(Fixtures.newPhoto());
        cadreRepository.save(c1);

        // when
        c1.setTaille(tailleUpdate);
        cadreRepository.save(c1);

        // then
        assertThat(c1.getTaille()).isEqualTo(tailleUpdate);
    }

    @Test
    void all(){
        // given
        Cadre cadre1 = Fixtures.newCadre();
        cadreRepository.save(cadre1);
        Cadre cadre2 = Fixtures.newCadre();
        cadreRepository.save(cadre2);


        // when
        int taille = cadreRepository.all().size();

        // then
        assertThat(taille).isEqualTo(2);
    }

     */
}
