package fr.uga.l3miage.photonum.data.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Tirage extends Impression {

    @OneToMany(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private Set<SelectionTirage> selections;

    public void addSelection(SelectionTirage selectionTirage) {
        if (this.selections == null) {
            this.selections = new HashSet<>() {
            };
        }
        this.selections.add(selectionTirage);
    }

}
