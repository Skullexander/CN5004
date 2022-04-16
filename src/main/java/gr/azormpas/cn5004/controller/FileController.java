package gr.azormpas.cn5004.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileController
{
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public FileController (File file)
        throws IOException
    {
        fileInputStream = new FileInputStream(file);
        fileOutputStream = new FileOutputStream(file);
        objectInputStream = new ObjectInputStream(fileInputStream);
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
    }
}
