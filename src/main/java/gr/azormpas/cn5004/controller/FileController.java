package gr.azormpas.cn5004.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileController
{
    private final File file;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public FileController(File file, String name)
        throws IOException
    {
        this.file = new File(file, name);
    }

    public void save(Object object)
        throws IOException
    {
        objectOutputStream = new ObjectOutputStream(new FileOutputStream(this.file));
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public Object load()
        throws IOException, ClassNotFoundException
    {
        objectInputStream = new ObjectInputStream(new FileInputStream(this.file));
        return objectInputStream.readObject();
    }

    public void loadClose()
        throws IOException
    {
        objectInputStream.close();
    }

    public boolean create()
        throws IOException
    {
        return this.file.createNewFile();
    }

    public boolean exists()
    {
        return this.file.exists();
    }

    public boolean isEmpty()
    {
        return (this.file.getTotalSpace() == 0);
    }
}
