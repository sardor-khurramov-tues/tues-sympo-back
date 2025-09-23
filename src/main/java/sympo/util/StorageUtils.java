package sympo.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import sympo.constant.FileType;
import sympo.constant.ResponseType;
import sympo.exception.CustomBadFileRequestException;
import sympo.exception.CustomBadRequestException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@UtilityClass
@Slf4j
public class StorageUtils {
    public static final String FOLDER = "./files";

    public static void saveImage(MultipartFile multipartFile, String filename) {
        try {
            File filesFolder = new File(FOLDER).getCanonicalFile();

            Path filePath = Files.createFile(
                    filesFolder.toPath().resolve(filename)
            );
            multipartFile.transferTo(filePath);

            validate(filePath.toFile(), List.of(FileType.JPEG));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(String filename) {
        try {
            if (filename.isBlank())
                throw new IOException("filename is blank");

            File file = new File(String.format("%s/%s", FOLDER, filename))
                    .getCanonicalFile();

            Files.delete(file.toPath());
        } catch (IOException e) {
            log.error(String.format("error on deleting image, filename: %s", filename));
            throw new RuntimeException(e);
        }
    }

    public static byte[] getImage(String filename) {
        try {
            File file = new File(String.format("%s/%s", FOLDER, filename))
                    .getCanonicalFile();

            if (!file.exists())
                throw new CustomBadFileRequestException(filename);

            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new CustomBadFileRequestException(filename);
        }
    }

    public byte[] getPdf(String filename) {
        try {
            File file = new File(String.format("%s/%s", FOLDER, filename))
                    .getCanonicalFile();

            if (!file.exists()) log.error(String.format("%s do not exists", filename));

            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBadRequestException(ResponseType.GET_PDF_FAILURE);
        }
    }



    private void validate(File file, List<FileType> fileTypeList) {
        boolean anyMatch = fileTypeList.stream().anyMatch(
                fileType -> fileType.getMagicNumberFileFilter().accept(file)
        );
        if (!anyMatch) {
            try {
                FileUtils.delete(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            throw new CustomBadRequestException(ResponseType.WRONG_FILE_TYPE);
        }
    }

}
