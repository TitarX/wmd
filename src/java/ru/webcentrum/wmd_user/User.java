/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_user;

import java.io.Serializable;

/**
 *
 * @author TitarX
 */
public class User implements Serializable
{

    private String WMID="";
    private String email="";
    private String firstname="";
    private String lastname="";
    private String patronymic="";

    public User(String WMID,String email,String firstname,String lastname,String patronymic)
    {
        this.WMID=WMID;
        this.email=email;
        this.firstname=firstname;
        this.lastname=lastname;
        this.patronymic=patronymic;
    }

    public String getWMID()
    {
        return WMID;
    }

    public String getEmail()
    {
        return email;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public String getPatronymic()
    {
        return patronymic;
    }
}
