package lt.neskelbiu.java.main;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ManualMultipartFile implements MultipartFile {

    private final byte[] fileBytes;
    private final String filename;
    private final String contentType;

    public ManualMultipartFile(byte[] fileBytes, String filename, String contentType) {
        this.fileBytes = fileBytes;
        this.filename = filename;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return filename;
    }

    @Override
    public String getOriginalFilename() {
        return filename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return fileBytes == null || fileBytes.length == 0;
    }

    @Override
    public long getSize() {
        return fileBytes.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileBytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileBytes);
    }

    @Override
    public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
        throw new UnsupportedOperationException("Not implemented");
    }
}