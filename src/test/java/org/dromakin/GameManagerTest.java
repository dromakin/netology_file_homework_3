package org.dromakin;

import org.junit.jupiter.api.AfterEach;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.dromakin.FileConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.hamcrest.io.FileMatchers.anExistingDirectory;
import static org.hamcrest.io.FileMatchers.anExistingFile;
import static org.hamcrest.CoreMatchers.is;

class GameManagerTest {

    GameManager gameManager;
    GameProgress gameProgress;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws GameManagerException {
        this.gameManager = new GameManager();
        this.gameManager.uninstalling(); // need to clear after running Main.java
        this.gameManager.installing();
        this.gameProgress = new GameProgress(100, 1, 1, 100.0);
        this.gameManager.setGameProgress(this.gameProgress);
    }

    @org.junit.jupiter.api.Test
    void installing() {
        Path gamePath = this.gameManager.getGamePath();
        assertThat(gamePath.toAbsolutePath().toFile(), anExistingDirectory());

        Path srcDir = Paths.get(gamePath.toString(), SRC_DIR);
        assertThat(srcDir.toAbsolutePath().toFile(), anExistingDirectory());

        Path resDir = Paths.get(gamePath.toString(), RES_DIR);
        assertThat(resDir.toAbsolutePath().toFile(), anExistingDirectory());

        Path saveGameDir = Paths.get(gamePath.toString(), SAVE_GAME_DIR);
        assertThat(saveGameDir.toAbsolutePath().toFile(), anExistingDirectory());

        Path tempDir = Paths.get(gamePath.toString(), TEMP_DIR);
        assertThat(tempDir.toAbsolutePath().toFile(), anExistingDirectory());

        Path tempFilePath = Paths.get(tempDir.toString(), TEMP_FILE);
        assertThat(tempFilePath.toAbsolutePath().toFile(), anExistingFile());

        Path mainDir = Paths.get(srcDir.toString(), MAIN_DIR);
        assertThat(mainDir.toAbsolutePath().toFile(), anExistingDirectory());

        Path testDir = Paths.get(srcDir.toString(), TEST_DIR);
        assertThat(testDir.toAbsolutePath().toFile(), anExistingDirectory());

        Path mainFilePath = Paths.get(mainDir.toString(), MAIN_FILE);
        assertThat(mainFilePath.toAbsolutePath().toFile(), anExistingFile());

        Path utilFilePath = Paths.get(mainDir.toString(), UTIL_FILE);
        assertThat(utilFilePath.toAbsolutePath().toFile(), anExistingFile());

        Path drawablePath = Paths.get(resDir.toString(), DRAWABLES_DIR);
        assertThat(drawablePath.toAbsolutePath().toFile(), anExistingDirectory());

        Path vectorPath = Paths.get(resDir.toString(), VECTORS_DIR);
        assertThat(vectorPath.toAbsolutePath().toFile(), anExistingDirectory());

        Path iconsPath = Paths.get(resDir.toString(), ICONS_DIR);
        assertThat(iconsPath.toAbsolutePath().toFile(), anExistingDirectory());
    }

    @org.junit.jupiter.api.Test
    void saveGame() throws GameManagerException {
        this.gameManager.saveGame(this.gameManager.getFileSavePathString());
        assertThat(this.gameManager.getFileSavePath().toFile(), anExistingFile());
    }

    @org.junit.jupiter.api.Test
    void deleteFilesSaveGame() throws GameManagerException {
        this.gameManager.saveGame(this.gameManager.getFileSavePathString());
        this.gameManager.zipFiles(this.gameManager.getZipFilePathString(), this.gameManager.getFileSavePathString());
        assertThat(this.gameManager.getFileSavePath().toFile(), anExistingFile());
        this.gameManager.deleteFilesSaveGame(this.gameManager.getFileSavePathString());
        assertThat(this.gameManager.getFileSavePath().toFile().exists(), is(false));
    }

    @org.junit.jupiter.api.Test
    void zipFiles() throws GameManagerException {
        this.gameManager.saveGame(this.gameManager.getFileSavePathString());
        this.gameManager.zipFiles(this.gameManager.getZipFilePathString(), this.gameManager.getFileSavePathString());
        assertThat(this.gameManager.getZipFilePath().toFile(), anExistingFile());
    }

    @org.junit.jupiter.api.Test
    void openZip() throws GameManagerException {
        this.gameManager.saveGame(this.gameManager.getFileSavePathString());
        this.gameManager.zipFiles(this.gameManager.getZipFilePathString(), this.gameManager.getFileSavePathString());
        this.gameManager.openZip(this.gameManager.getZipFilePathString(), this.gameManager.getPathToExtractFiles(), false);
        assertThat(this.gameManager.getZipFilePath().toFile(), anExistingFile());
    }

    @org.junit.jupiter.api.Test
    void openProgress() throws GameManagerException {
        this.gameManager.saveGame(this.gameManager.getFileSavePathString());
        this.gameManager.zipFiles(this.gameManager.getZipFilePathString(), this.gameManager.getFileSavePathString());
        this.gameManager.openZip(this.gameManager.getZipFilePathString(), this.gameManager.getPathToExtractFiles(), false);
        GameProgress progress = this.gameManager.openProgress(this.gameManager.getFileSavePathString());
        assertThat(this.gameProgress, equalTo(progress));
    }

    @org.junit.jupiter.api.Test
    void openProgress_givenBean_whenHasCorrectValue_thenCorrect() throws GameManagerException {
        this.gameManager.saveGame(this.gameManager.getFileSavePathString());
        this.gameManager.zipFiles(this.gameManager.getZipFilePathString(), this.gameManager.getFileSavePathString());
        this.gameManager.openZip(this.gameManager.getZipFilePathString(), this.gameManager.getPathToExtractFiles(), false);
        GameProgress progress = this.gameManager.openProgress(this.gameManager.getFileSavePathString());
        assertThat(this.gameProgress, samePropertyValuesAs(progress));
    }

    @AfterEach
    void tearDown() throws GameManagerException {
        this.gameManager.uninstalling();
    }
}