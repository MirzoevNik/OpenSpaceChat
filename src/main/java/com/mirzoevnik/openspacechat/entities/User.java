package com.mirzoevnik.openspacechat.entities;

import com.mirzoevnik.openspacechat.dao.Identified;

/**
 * @author MirzoevNik
 */
public class User implements Identified<Integer> {

    protected Integer id;
    protected String login;
    protected String password;
    protected String name;
    protected String surname;
    protected String country;

    public User() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "" + id + '\t'
                + login + '\t'
                + password + '\t'
                + name + '\t'
                + surname + '\t'
                + country;
    }
}
