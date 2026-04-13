package fr.uga.l3miage.photonum;

import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Image;


public class CreateBasingObjectForTest {

    public static Client newClient1() {
        Client client = new Client();
        client.setNom("monsieur");
        client.setPrenom("trebuche");
        client.setAdresseMail("trebuche@outlook.fr");
        client.setAdressePostale("36 rue du tacos");
        client.setMotDePasse("abricot");
        return client;
    }

    public static Client newClient2() {
        Client client = new Client();
        client.setNom("monsieur");
        client.setPrenom("trebuche2");
        client.setAdresseMail("trebuche2@outlook.fr");
        client.setAdressePostale("36 rue du tacos");
        client.setMotDePasse("abricot");
        return client;
    }

    public static Client newClient3() {
        Client client = new Client();
        client.setNom("monsieur");
        client.setPrenom("trebuche3");
        client.setAdresseMail("trebuche3@outlook.fr");
        client.setAdressePostale("36 rue du tacos");
        client.setMotDePasse("abricot");
        return client;
    }

    public static Client newClient4() {
        Client client = new Client();
        client.setNom("monsieur");
        client.setPrenom("trebuche");
        client.setAdresseMail("trebuche4@outlook.fr");
        client.setAdressePostale("36 rue du tacos");
        client.setMotDePasse("abricot");
        return client;
    }


    public static Image newImage1() {
        Image image = new Image();
        image.setChemin("https://test.gif");
        image.setResolution(250);
        image.setInfo("smurl");
        return image;
    }

    public static Image newImage2() {
        Image image = new Image();
        image.setChemin("https://test2.gif");
        image.setResolution(250);
        image.setInfo("smurl");
        return image;
    }

    public static Image newImage3() {
        Image image = new Image();
        image.setChemin("https://test3.gif");
        image.setResolution(250);
        image.setInfo("smurl");
        return image;
    }
    public static Image newImage4() {
        Image image = new Image();
        image.setChemin("https://test4.gif");
        image.setResolution(250);
        image.setInfo("smurl");
        return image;
    }

    public static Image newImage5() {
        Image image = new Image();
        image.setChemin("https://test5.gif");
        image.setResolution(250);
        image.setInfo("smurl");
        return image;
    }

    public static Image newImage6() {
        Image image = new Image();
        image.setChemin("https://test6.gif");
        image.setResolution(250);
        image.setInfo("smurl");
        return image;
    }

    public static Image newImage7() {
        Image image = new Image();
        image.setChemin("https://test7.gif");
        image.setResolution(250);
        image.setInfo("smurl");
        return image;
    }
//
}