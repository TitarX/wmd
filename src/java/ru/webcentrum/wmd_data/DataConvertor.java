/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.webcentrum.wmd_data;

/**
 *
 * @author TitarX
 */
public class DataConvertor
{

    public static String fixApostrophe(String data)
    {
        int dataLength=data.length();
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<dataLength;i++)
        {
            char c=data.charAt(i);
            if(c=='\'')
            {
                stringBuilder.append("''");
            }
            else
            {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString().trim();
    }
    
    public static String fixReal(String data)
    {
        String temp="";
        int dataLength=data.length();
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<dataLength;i++)
        {
            char c=data.charAt(i);
            temp=String.valueOf(c);
            if(temp.matches("[0-9\\.]"))
            {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString().trim();
    }
}
