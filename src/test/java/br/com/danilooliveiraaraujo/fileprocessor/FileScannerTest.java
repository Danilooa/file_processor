package br.com.danilooliveiraaraujo.fileprocessor;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class FileScannerTest {

    private static final String IN_DIRECTORY_PATH = System.getProperty("user.home") + "/data/in/";

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(IN_DIRECTORY_PATH));
    }

    @Test
    public void fileScannerShouldIdentifyNewFiles() throws IOException {
        Long timeoutToFail = System.currentTimeMillis() + 1000;
        final AtomicInteger callsCount = new AtomicInteger(0);
        final FileScanner fileScanner = new FileScanner(new File(IN_DIRECTORY_PATH));
        FileScannerListener fileScannerListener = new FileScannerListener() {
            public void listen(File file) {
                callsCount.addAndGet(1);
                System.out.println("File " + file.getName() + " was added");
            }
        };
        fileScanner.addListener(fileScannerListener);
        fileScanner.start();
        this.putFileInTheInDirectory();
        this.putFileInTheInDirectory();
        this.putFileInTheInDirectory();
        while (callsCount.get() < 3 && System.currentTimeMillis() <= timeoutToFail) {
            System.out.println("Waiting for the three calls ...");
        }
        fileScanner.stop();
        Assert.assertEquals("Not all files were scanned ", 3, callsCount.get());
    }

    private void putFileInTheInDirectory() throws IOException {
        File inDirectory = new File(IN_DIRECTORY_PATH);
        System.out.println("In directory was created: " + inDirectory.mkdirs());
        System.out.println("File was created: " + File.createTempFile("in_file_", ".dat", inDirectory));
    }

}
