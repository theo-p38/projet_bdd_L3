package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.Calendrier;
import fr.uga.l3miage.photonum.data.domain.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CalendrierRepositoryTest extends Base {

    @Autowired
    CalendrierRepository calendrierRepository;
    @Autowired
    PageRepository pageRepository;

    @Test
    void all(){
        // given
        Calendrier calendrier1 = Fixtures.newCalendrier();
        calendrierRepository.save(calendrier1);
        Calendrier calendrier2 = Fixtures.newCalendrier();
        calendrierRepository.save(calendrier2);

        // when
        int taille = calendrierRepository.all().size();

        // then
        assertThat(taille).isEqualTo(2);
    }

    @Test
    void getPagesFromCalendrier(){
        //given
        Calendrier calendrier = Fixtures.newCalendrier();
        calendrierRepository.save(calendrier);
        // when
        int size = calendrierRepository.getPagesFromCalendrier(calendrier.getId()).size();

        // then
        assertThat(size).isEqualTo(12);
    }

    @Test
    void getPageNumberNFromCalendrier(){
        //given
        Calendrier calendrier = Fixtures.newCalendrier();
        calendrierRepository.save(calendrier);
        // when
        Page page = calendrierRepository.getPageNumberNFromCalendrier(calendrier.getId(), 1);

        // then
        assertThat(page.getNumero()).isEqualTo(1);
    }
}
