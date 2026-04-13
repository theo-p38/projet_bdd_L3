package fr.uga.l3miage.photonum.data.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.*;


@Data
@Entity
public class Calendrier extends Impression {

    @Size(min = 12, max = 12)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Column(nullable = false)
    private Set<Page> pagesDeCalendrier;

    public void initPages() {
        this.pagesDeCalendrier = new HashSet<>();
        Page defaultPage;
        for (int i = 1; i <= 12; i++) {
            defaultPage = new Page();
            defaultPage.setNumero(i);
            defaultPage.setDescription(null);
            defaultPage.setPhotos(null);
            this.pagesDeCalendrier.add(defaultPage);
        }
    };

    public void addPhotoAndDescription(Page page, Collection<Photo> photos) {
        List<Page> pagesArray = new ArrayList<>(this.pagesDeCalendrier);
        if (page.getDescription() != null) {
            pagesArray.get(page.getNumero()).setDescription(page.getDescription());
        }
        for (Photo p: photos) {
            boolean flag = true;
            int i = 0;
            while(i < 12 && flag) {
                if (pagesArray.get(i).getNumero() == page.getNumero()) {
                    pagesArray.get(i).getPhotos().add(p);
                    flag = false;
                }
                i++;
            }
        }
    }


}
