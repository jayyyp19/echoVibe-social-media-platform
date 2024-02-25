package echovibe.Config;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileUtility {

    private static final String MODULE = " [File Utility ] ";

    public String saveFileToServer(MultipartFile argFile, String path) throws IOException {
        String SUBMODULE = MODULE + " [saveFileToServer()] ";
        Assert.notNull(path, "Path should not be empty");
        String fileName = "Test";
        if (null != argFile) {
            fileName = (null != argFile.getOriginalFilename())
                    ? argFile.getOriginalFilename().replace("/", "_").trim()
                    : fileName;
        }
        File file = new File(path + System.currentTimeMillis() + "_" + fileName);
        File directory = new File(path);
        try {
            if (!directory.exists()) {
                directory.mkdir();
            }
            boolean isCreated = file.createNewFile();
            if (!isCreated) {
                throw new EOFException();
            }
            if (null != argFile) {
                FileOutputStream fout = new FileOutputStream(file);
                fout.write(argFile.getBytes());
                fout.close();
            }
            return file.getName();
        } catch (IOException e) {
//            ApplicationLogger.logger.error(SUBMODULE + e.getMessage(), e);
            throw new EOFException();
        }
    }

    public boolean removeFileAtServer(String argFile, String path) {
        boolean isFileDeleted = false;
        String SUBMODULE = MODULE + " [saveFileToServer()] ";
        Assert.notNull(path, "Path should not be empty");
        try {
            File file = new File(path + argFile);
            if (null == file) {
//                ApplicationLogger.logger.debug(SUBMODULE + "File not found with name" + file.getName());
            }
            if (null != file && file.delete()) {
                isFileDeleted = true;
            }
            return isFileDeleted;
        } catch (Exception ex) {
//            ApplicationLogger.logger.error(SUBMODULE + ex.getMessage(), ex);
            throw ex;
        }
    }

    public File getFileFromServer(String argFile, String path) {
        String SUBMODULE = MODULE + "[Save File To Server()]";
        Assert.notNull(path, "Path should not be empty");
        File file = null;
        try {
            File file1 = new File(path + argFile);
            ;
            if (file1 == null) {
//                ApplicationLogger.logger.debug(SUBMODULE + "File not found with name" + file1.getName());
                file1 = null;
                file = file1;
            }
            if (file1 != null) {
                file = file1;
            }
        } catch (Exception e) {
//            ApplicationLogger.logger.error(SUBMODULE + e.getMessage(), e);
        }
        return file;
    }
    }
