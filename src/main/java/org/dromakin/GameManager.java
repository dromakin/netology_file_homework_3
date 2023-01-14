/*
 * File:     GameManager
 * Package:  org.dromakin
 * Project:  netology_file_homework_2
 *
 * Created by dromakin as 11.01.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.01.11
 */

package org.dromakin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static org.dromakin.FileConstants.*;

public class GameManager {

    private static final Logger logger = LogManager.getLogger(GameManager.class);

    private static final String CREATED_DIR = "Created all folders: {}";
    private static final String CREATED_FILE = "Created file: {}";

    private GameProgress gameProgress;

    private Path gamePath;
    private Path saveGameDir;

    private int saveCount;

    public GameManager() {
        // no instances
        this.saveCount = 0;
    }

    public void setGameProgress(GameProgress gameProgress) {
        this.gameProgress = gameProgress;
        this.saveCount++;
    }

    protected String getFileSavePathString() {
        return getFileSavePath().toString();
    }

    protected Path getFileSavePath() {
        return Paths.get(this.saveGameDir.toString(), String.format(SAVE_FILE_DAT, saveCount));
    }

    public Path getGamePath() {
        return gamePath;
    }

    protected String getZipFilePathString() {
        return this.getZipFilePath().toString();
    }

    protected Path getZipFilePath() {
        return Paths.get(this.saveGameDir.toString(), ZIP_FILE);
    }

    protected String getPathToExtractFiles() {
        return this.saveGameDir.toString();
    }

    protected Path getRootPathDirectory() {
        return Paths.get(TMP_DIR).toAbsolutePath();
    }

    public boolean isInstalled() {
        return Files.isDirectory(Paths.get(getRootPathDirectory().toString(), GAME_DIR));
    }

    public void uninstalling() throws GameManagerException {
        logger.info("Uninstalling Game...");
        if (isInstalled()) {
            try {
                Files.walk(Paths.get(getRootPathDirectory().toString(), GAME_DIR))
                        .sorted(Comparator.reverseOrder())
                        .forEach(x -> {
                            try {
                                Files.deleteIfExists(x);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            } catch (IOException e) {
                throw new GameManagerException("Can't uninstalling game!");
            }
        }
        logger.info("Game has removed!");
    }

    public void installing() throws GameManagerException {
        logger.info("Start installing Game...");

        Path rootPath = Paths.get(TMP_DIR).toAbsolutePath();
        logger.info("Find root path: {}", rootPath);

        gamePath = Paths.get(rootPath.toString(), GAME_DIR);
        logger.info("Get Games path: {}", gamePath);

        // Games/
        Path srcDir = Paths.get(gamePath.toString(), SRC_DIR);
        logger.info("Get src dir path: {}", srcDir);

        Path resDir = Paths.get(gamePath.toString(), RES_DIR);
        logger.info("Get res dir path: {}", resDir);

        this.saveGameDir = Paths.get(gamePath.toString(), SAVE_GAME_DIR);
        logger.info("Get savegames dir path: {}", this.saveGameDir);

        Path fullDir;
        try {
            fullDir = Files.createDirectories(saveGameDir);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GameManagerException("Can't create savegames dir with path: " + saveGameDir);
        }
        logger.info(CREATED_DIR, saveGameDir);

        Path tempDir = Paths.get(gamePath.toString(), TEMP_DIR);
        logger.info("Get temp dir path: {}", tempDir);

        try {
            fullDir = Files.createDirectories(tempDir);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GameManagerException("Can't create temp dir with path: " + tempDir);
        }
        logger.info(CREATED_DIR, tempDir);

        Path tempFilePath = Paths.get(tempDir.toString(), TEMP_FILE);
        logger.info("Get temp.txt file path: {}", tempFilePath);

        try {
            fullDir = Files.createFile(tempFilePath);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GameManagerException("Can't create temp.txt with path: " + tempFilePath);
        }
        logger.info(CREATED_FILE, tempFilePath);

        // Games/src
        Path mainDir = Paths.get(srcDir.toString(), MAIN_DIR);
        logger.info("Get main dir path: {}", mainDir);

        try {
            fullDir = Files.createDirectories(mainDir);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GameManagerException("Can't create main dir with path: " + mainDir);
        }
        logger.info(CREATED_DIR, fullDir);

        Path testDir = Paths.get(srcDir.toString(), TEST_DIR);
        logger.info("Get test dir path: {}", testDir);

        try {
            fullDir = Files.createDirectories(testDir);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GameManagerException("Can't create test dir with path: " + testDir);
        }
        logger.info(CREATED_DIR, fullDir);

        // Games/src/main
        Path mainFilePath = Paths.get(mainDir.toString(), MAIN_FILE);
        logger.info("Get main file path: {}", mainFilePath);

        try {
            fullDir = Files.createFile(mainFilePath);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GameManagerException("Can't create Main.java with path: " + mainFilePath);
        }
        logger.info(CREATED_FILE, fullDir);

        Path utilFilePath = Paths.get(mainDir.toString(), UTIL_FILE);
        logger.info("Get util file path: {}", utilFilePath);

        try {
            fullDir = Files.createFile(utilFilePath);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GameManagerException("Can't create Utils.java with path: " + utilFilePath);
        }
        logger.info(CREATED_FILE, fullDir);

        // Games/res/
        Path drawablePath = Paths.get(resDir.toString(), DRAWABLES_DIR);
        logger.info("Get drawables dir path: {}", drawablePath);

        try {
            fullDir = Files.createDirectories(drawablePath);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GameManagerException("Can't create drawable dir with path: " + drawablePath);
        }
        logger.info(CREATED_DIR, fullDir);

        Path vectorPath = Paths.get(resDir.toString(), VECTORS_DIR);
        logger.info("Get vector dir path: {}", vectorPath);

        try {
            fullDir = Files.createDirectories(vectorPath);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GameManagerException("Can't create vector dir with path: " + vectorPath);
        }
        logger.info(CREATED_DIR, fullDir);

        Path iconsPath = Paths.get(resDir.toString(), ICONS_DIR);
        logger.info("Get icons dir path: {}", iconsPath);

        try {
            fullDir = Files.createDirectories(iconsPath);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GameManagerException("Can't create vector dir with path: " + iconsPath);
        }
        logger.info(CREATED_DIR, fullDir);

        logger.info("Game installed!");
    }

    public void saveGame(String... paths) throws GameManagerException {
        logger.info("Saving game process start...");
        for (String path : paths) {
            try (
                    FileOutputStream fout = new FileOutputStream(path);
                    ObjectOutputStream out = new ObjectOutputStream(fout)
            ) {
                out.writeObject(this.gameProgress);
                out.flush();
            } catch (IOException e) {
                throw new GameManagerException("Can't Serialize object!", e);
            }
        }
        logger.info("Game progress saved!");
    }

    public void deleteFilesSaveGame(String... paths) throws GameManagerException {
        try {
            for (String path : paths) {
                boolean result = Files.deleteIfExists(Paths.get(path));
                if (!result) {
                    throw new GameManagerException("Can't delete file!");
                }
            }
        } catch (IOException e) {
            throw new GameManagerException(e.getMessage(), e);
        }
    }

    public void zipFiles(String zipFilePath, String... paths) throws GameManagerException {
        logger.info("Game progress compressing...");
        try (
                FileOutputStream fos = new FileOutputStream(zipFilePath);
                ZipOutputStream zipOut = new ZipOutputStream(fos)
        ) {
            for (String path : paths) {

                File fileToZip = new File(path);

                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new GameManagerException("File not found!", e);
        } catch (IOException e) {
            throw new GameManagerException(e.getMessage(), e);
        }
        logger.info("Game progress compressed!");
    }

    public void openZip(String zipFilePath, String pathToExtractFiles, boolean deleteZipFile) throws GameManagerException {
        logger.info("Unzipping game progress...");
        byte[] buffer = new byte[1024];

        Path path = Paths.get(zipFilePath);
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(path))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                Path newFile = Paths.get(pathToExtractFiles, zipEntry.getName());

                // write file content
                try (FileOutputStream fos = new FileOutputStream(newFile.toString())) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }

                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();

            // optional: delete zip files
            if (deleteZipFile) {
                boolean result = Files.deleteIfExists(path);
                if (!result) {
                    throw new GameManagerException("Can't delete file *.zip!");
                }
            }

        } catch (IOException e) {
            throw new GameManagerException(e.getMessage(), e);
        }
        logger.info("Unzipping game finished!");
    }

    public GameProgress openProgress(String pathToSaveFile) throws GameManagerException {
        logger.info("Opening game progress!");
        GameProgress gameProgress;
        try (
                FileInputStream fileInputStream = new FileInputStream(pathToSaveFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            gameProgress = (GameProgress) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new GameManagerException(e.getMessage(), e);
        }

        return gameProgress;
    }

}
