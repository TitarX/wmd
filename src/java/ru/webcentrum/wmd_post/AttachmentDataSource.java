package ru.webcentrum.wmd_post;

import org.apache.commons.fileupload.FileItem;

import javax.activation.DataSource;
import java.io.*;

public class AttachmentDataSource implements DataSource
{

    private FileItem item;

    public AttachmentDataSource(FileItem item)
    {
        this.item=item;
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        return item.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException
    {
        return item.getOutputStream();
    }

    @Override
    public String getContentType()
    {
        return item.getContentType();
    }

    @Override
    public String getName()
    {
        return item.getName();
    }
}