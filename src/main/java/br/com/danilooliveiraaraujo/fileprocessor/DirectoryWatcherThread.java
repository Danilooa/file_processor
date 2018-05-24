package br.com.danilooliveiraaraujo.fileprocessor;

import java.io.File;
import java.nio.file.*;
import java.util.Set;

class DirectoryWatcherThread extends Thread {

    private final File inDirectory;
    private final Set<FileScannerListener> fileListeners;
    private Boolean started;

    public DirectoryWatcherThread(File inDirectory, Set<FileScannerListener> fileListeners) {
        this.inDirectory = inDirectory;
        this.started = false;
        this.fileListeners = fileListeners;
    }

    @Override
    public void run() {
        try {
            this.started = true;
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path inPath = inDirectory.toPath();
            inPath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey watchKey;
            while ((watchKey = watcher.take()) != null) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    if (!this.started) {
                        break;
                    }
                    WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                    if (!StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
                        continue;
                    }
                    for (FileScannerListener fileScannerListener : fileListeners) {
                        fileScannerListener.listen(pathEvent.context().toFile());
                    }
                    watchKey.reset();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void kill() {
        this.started = false;
    }

}
