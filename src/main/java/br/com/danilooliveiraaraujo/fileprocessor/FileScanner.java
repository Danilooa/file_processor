package br.com.danilooliveiraaraujo.fileprocessor;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

class FileScanner {

    private final File inDirectory;
    private final Set<FileScannerListener> fileListeners;
    private DirectoryWatcherThread directoryWatcherThread;

    public FileScanner(File inDirectory) {
        this.inDirectory = inDirectory;
        fileListeners = new HashSet<>();
    }

    void start() {
        this.directoryWatcherThread = new DirectoryWatcherThread(this.inDirectory, this.fileListeners);
        this.directoryWatcherThread.start();
    }

    public void addListener(FileScannerListener fileScannerListener) {
        this.fileListeners.add(fileScannerListener);
    }

    public void stop() {
        this.directoryWatcherThread.kill();
    }

}
