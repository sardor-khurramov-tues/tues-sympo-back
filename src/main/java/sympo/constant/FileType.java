package sympo.constant;

import lombok.Getter;
import org.apache.commons.io.filefilter.MagicNumberFileFilter;

@Getter
public enum FileType {
    PDF(".pdf", new MagicNumberFileFilter(
            new byte[] { (byte) 0x25, (byte) 0x50, (byte) 0x44, (byte) 0x46, (byte) 0x2D }
    )),
    JPEG(".jpeg", new MagicNumberFileFilter(
            new byte[] { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF }
    )),
    PNG(".png", new MagicNumberFileFilter(
            new byte[] { (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47, (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A }
    ));

    private final String extension;
    private final MagicNumberFileFilter magicNumberFileFilter;

    FileType(String extension, MagicNumberFileFilter magicNumberFileFilter) {
        this.extension = extension;
        this.magicNumberFileFilter = magicNumberFileFilter;
    }

}
