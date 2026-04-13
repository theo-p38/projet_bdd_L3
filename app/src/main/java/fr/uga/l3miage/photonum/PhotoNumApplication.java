package fr.uga.l3miage.photonum;

import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.data.repo.ClientRepository;
import fr.uga.l3miage.photonum.data.repo.ImageRepository;
import fr.uga.l3miage.photonum.service.ClientService;
import fr.uga.l3miage.photonum.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SpringBootApplication
public class PhotoNumApplication implements CommandLineRunner {


    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ImageRepository imageRepository;

    public static void main(String[] args) {
        SpringApplication.run(PhotoNumApplication.class, args);
    }

    @Override
    public void run(String... args) {

/*
        Client bastien = CreateBasingObjectForTest.newClient1();
        Client theo = CreateBasingObjectForTest.newClient2();
        Client levi = CreateBasingObjectForTest.newClient3();
        Client damien = CreateBasingObjectForTest.newClient4();

        Image imBastien1 = CreateBasingObjectForTest.newImage3();
        Image imBastien2 = CreateBasingObjectForTest.newImage5();

        Image imTheo1 = CreateBasingObjectForTest.newImage2();

        Image imLevi1 = CreateBasingObjectForTest.newImage6();

        Image imDamien1 = CreateBasingObjectForTest.newImage1();
        Image imDamien2 = CreateBasingObjectForTest.newImage4();
        Image imDamien3 = CreateBasingObjectForTest.newImage7();

        bastien.addImage(imBastien1);
        bastien.addImage(imBastien2);

        theo.addImage(imTheo1);

        levi.addImage(imLevi1);

        damien.addImage(imDamien1);
        damien.addImage(imDamien2);
        damien.addImage(imDamien3);

        clientRepository.save(bastien);
        clientRepository.save(theo);
        clientRepository.save(levi);
        clientRepository.save(damien);

        imageRepository.save(imBastien1);
        imageRepository.save(imBastien2);
        imageRepository.save(imTheo1);
        imageRepository.save(imLevi1);
        imageRepository.save(imDamien1);
        imageRepository.save(imDamien2);
        imageRepository.save(imDamien3);

        List<Client> clients = clientRepository.all();
        for(Client c: clients) {
            System.out.println(c.getId());
        };
*/
    }
}