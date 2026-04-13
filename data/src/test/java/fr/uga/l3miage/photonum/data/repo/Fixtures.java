package fr.uga.l3miage.photonum.data.repo;

import com.github.javafaker.Faker;
import fr.uga.l3miage.photonum.data.domain.*;

import java.util.*;

public class Fixtures {
    private static final Faker FAKER = Faker.instance(new Random(42));

    public static Cadre newCadre() {
        Cadre cadre = new Cadre();
        cadre.setFormat(Impression.Formats.MOYEN);
        cadre.setTaille(FAKER.lorem().characters(10));
        cadre.setQualite(Impression.Qualite.FAIBLE);
        cadre.setPrix(FAKER.random().nextInt(10, 100));
        cadre.setStatut(Cadre.Statut.ENCOURS);
        return cadre;
    }

    public static Calendrier newCalendrier(){
        Calendrier calendrier = new Calendrier();
        calendrier.setFormat(Impression.Formats.GRAND);
        calendrier.setQualite(Impression.Qualite.BONNE);
        calendrier.setPrix(FAKER.random().nextInt(10, 100));
        calendrier.setStatut(Calendrier.Statut.ENCOURS);
        Set<Page> pages = new HashSet<Page>();
        for(int i = 1; i <= 12; i++){
            Page page = newPage();
            page.setNumero(i);
            pages.add(page);
        }
        calendrier.setPagesDeCalendrier(pages);
        return calendrier;
    }

    public static Photo newPhoto (){
        Photo photo = new Photo();
        Image image = newImage();
        photo.setParametres(FAKER.lorem().characters(100));
        photo.setImageSource(image);
        return photo;
    }

    public static Image newImage(){
        Image image = new Image();
        image.setChemin(FAKER.lorem().characters(100));
        image.setEstPartage(FAKER.bool().bool());
        image.setInfo(FAKER.lorem().characters(100));
        image.setResolution(FAKER.random().nextInt(100, 1000));
        image.setProprietaire(newClient());
        return image;
    }

    public static Client newClient(){
        Client client = new Client();
        client.setNom(FAKER.name().lastName());
        client.setPrenom(FAKER.name().firstName());
        client.setAdressePostale(FAKER.address().fullAddress());
        client.setAdresseMail(FAKER.internet().emailAddress());
        client.setMotDePasse(FAKER.internet().password());
        List<Commande> commandes = new ArrayList<>();
        commandes.add(newCommande());
        client.setCommandes(commandes);
        return client;
    }

    public static Commande newCommande(){
        Commande commande = new Commande();
        commande.setStatut(Commande.Statut.ENCOURS);
        commande.setDateDeCommande(FAKER.date().birthday());
        commande.setPrixTotal(FAKER.random().nextInt(10, 100));
        Set<Impression> impressions = new HashSet<>();
        impressions.add(newCadre());
        commande.setArticles(impressions);
        return commande;
    }

    public static Page newPage(){
        Page page = new Page();
        page.setNumero(FAKER.random().nextInt(1,12));
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            photos.add(newPhoto());
        }
        page.setPhotos(photos);
        return page;
    }

    public static Impression newImpression(){
        Impression impression = newCadre();
        impression.setFormat(Impression.Formats.MOYEN);
        impression.setPrix(FAKER.random().nextInt(10, 100));
        impression.setQualite(Impression.Qualite.FAIBLE);
        impression.setStatut(Impression.Statut.ENCOURS);
        return impression;
    }

    public static Album newAlbum(){
        Album album = new Album();
        album.setPhotoDeCouverture(newPhoto());
        album.setFormat(Impression.Formats.MOYEN);
        album.setQualite(Impression.Qualite.MOYENNE);
        album.setTitre(FAKER.lorem().characters(10));
        album.setPrix(FAKER.random().nextInt(10, 100));
        album.setStatut(Album.Statut.ENCOURS);
        Set<Page> pages = new HashSet<>();
        for(int i = 1; i <= 10; i++){
            Page page = newPage();
            page.setNumero(i);
            pages.add(page);
        }
        album.setPagesDAlbums(pages);
        return album;
    }
}