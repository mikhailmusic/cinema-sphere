package rut.miit.cinema.dto;

public class ImageDto {
    private byte[] data;
    private String contentType;
    private String originalFilename;

    public ImageDto(byte[] data, String contentType, String originalFilename) {
        this.data = data;
        this.contentType = contentType;
        this.originalFilename = originalFilename;
    }

    public byte[] getData() {
        return data;
    }

    public String getContentType() {
        return contentType;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }
}
