package pl.bartosiak.spotyspringapi.model;

public class SpotifyAlbumDto {
    private String trackName;
    private String imageUrl;

    public String getTrackName() {
        return trackName;
    }

    public SpotifyAlbumDto() {
    }

    public SpotifyAlbumDto(String trackName, String imageUrl) {
        this.trackName = trackName;
        this.imageUrl = imageUrl;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
