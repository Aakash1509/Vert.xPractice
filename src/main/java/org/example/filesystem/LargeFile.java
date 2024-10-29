package org.example.filesystem;

import java.io.FileWriter;

public class LargeFile
{
    public static void main(String[] args)
    {
        String fileName = "1GB_file.txt";
        long targetSize = (long) 1024 * 1024 * 1024;
        String chunk = "This is repetitive content to fill up the size for testing.";

        try(FileWriter fileWriter = new FileWriter(fileName))
        {
            long writtenSize = 0;

            while (writtenSize<targetSize)
            {
                fileWriter.write(chunk);
                writtenSize+=chunk.getBytes().length;
            }
            System.out.println("File of 1GB created successfully");
        }
        catch (Exception e)
        {
            System.out.println("An error occurred");
        }
    }
}
