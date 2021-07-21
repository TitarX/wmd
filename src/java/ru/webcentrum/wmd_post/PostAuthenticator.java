/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_post;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author TitarX
 */
class PostAuthenticator extends Authenticator
{
    
    private String login;
    private String password;
    
    PostAuthenticator(String login,String password)
    {
        this.login=login;
        this.password=password;
    }
    
    @Override
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(login,password);
    }
}
