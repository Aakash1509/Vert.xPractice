package org.example.filesystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;

public class ReadResume extends AbstractVerticle {

    private static final String FILE_PATH = "1GB_file.txt";  // Path to the file to read
    private static final String OFFSET_FILE_PATH = "offset.txt";  // Path to store offset
    private static final int CHUNK_SIZE = 1024;  // Size of each chunk to read

    @Override
    public void start() {
        FileSystem fileSystem = vertx.fileSystem();
        AtomicLong startOffset = new AtomicLong(readLastOffset());

        // Open the file for reading
        fileSystem.open(FILE_PATH, new OpenOptions().setRead(true), openResult -> {
            if (openResult.succeeded()) {
                AsyncFile file = openResult.result();
                System.out.println("Reading from offset: " + startOffset.get());

                // Read the first chunk
                readNextChunk(file, startOffset);

            } else {
                System.out.println("Failed to open the file: " + openResult.cause().getMessage());
            }
        });
    }

    // Recursive method to read chunks
    private void readNextChunk(AsyncFile file, AtomicLong startOffset) {
        file.read(Buffer.buffer(CHUNK_SIZE), 0, startOffset.get(), CHUNK_SIZE, readResult -> {
            if (readResult.succeeded()) {
                Buffer buffer = readResult.result();

                if (buffer.length() > 0) {
                    System.out.println("Read chunk: " + buffer.toString());

                    // Update the offset
                    startOffset.addAndGet(buffer.length());

                    // Write the last offset to the file
                    writeLastOffset(startOffset.get());

                    // Recursively read the next chunk
                    readNextChunk(file, startOffset);
                } else {
                    System.out.println("Finished reading the file.");
                    file.close();
                }
            } else {
                System.err.println("Failed to read chunk: " + readResult.cause().getMessage());
            }
        });
    }

    // Read the last saved offset from the file
    private long readLastOffset() {
        try {
            File offsetFile = new File(OFFSET_FILE_PATH);
            if (offsetFile.exists()) {
                String offsetStr = new String(Files.readAllBytes(Paths.get(OFFSET_FILE_PATH)));
                return Long.parseLong(offsetStr.trim());
            }
        } catch (Exception e) {
            System.out.println("Failed to read last offset: " + e.getMessage());
        }
        return 0;  // Start from the beginning if no offset is saved
    }

    // Write the current offset to the file
    private void writeLastOffset(long offset) {
        try {
            Files.write(Paths.get(OFFSET_FILE_PATH), String.valueOf(offset).getBytes());
        } catch (Exception e) {
            System.err.println("Failed to write last offset: " + e.getMessage());
        }
    }

    // Main method to deploy the Verticle
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ReadResume());
    }
}
