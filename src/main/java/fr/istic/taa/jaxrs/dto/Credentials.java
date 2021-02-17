package fr.istic.taa.jaxrs.dto;

import fr.istic.taa.jaxrs.utils.Hashing;

import java.io.Serializable;

public class Credentials implements Serializable {

    private String email;
    private String password;

    public Credentials(){}

    public Credentials(String email, String password) {
        this.email = email;
        this.password = Hashing.hash(password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}