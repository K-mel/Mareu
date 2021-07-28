package fr.camel.mareu2.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Camel Benmoussa on 28/07/2021.
 */
public class User {

    private String firstName;
    private String lastName;
    private static String emailAddress = "@service.com";

    private User(String firstName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        User.emailAddress = emailAddress;
    }

    private static List<User> DUMMY_USER = Arrays.asList(
            new User("Philippe","Ginenet", emailAddress),
            new User("Raphaël","Cardaimont", emailAddress),
            new User("Gabriel","Alirral", emailAddress),
            new User("Arthur","Creleilles", emailAddress)

    );

    public static List listParticipants = adressGenerator();

    private static List adressGenerator() {
        List<String> tab = new ArrayList<>();
        for (User utilisateur : DUMMY_USER) {
            tab.add(utilisateur.firstName + utilisateur.lastName + emailAddress);
        }
        return tab;
    }
}
