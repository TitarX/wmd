/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author TitarX
 */
public class ConnectionFactory
{

    private DataGetter dataGetter=new DataGetter();
    private String clientDriver=dataGetter.getData("/data/db/driver/text()");
    private String urlDB=dataGetter.getData("/data/db/url/text()");
    private String user=dataGetter.getData("/data/db/user/text()");
    private String password=dataGetter.getData("/data/db/password/text()");

    public Connection getConnection() throws ClassNotFoundException,InstantiationException,IllegalAccessException,SQLException
    {
        Class.forName(clientDriver).newInstance();
        return DriverManager.getConnection(urlDB,user,password);
    }
}
