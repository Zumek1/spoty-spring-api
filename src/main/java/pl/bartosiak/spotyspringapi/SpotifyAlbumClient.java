package pl.bartosiak.spotyspringapi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.bartosiak.spotyspringapi.model.SpotifyAlbum;
import pl.bartosiak.spotyspringapi.model.SpotifyAlbumDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SpotifyAlbumClient {


    @GetMapping("/album/{authorName}")
    public List<SpotifyAlbumDto> getAlbumsByAuthor(OAuth2Authentication details, @PathVariable String authorName) {
        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();//pobieranie token

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);// to wziete z dokumentacji spoty
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<SpotifyAlbum> exchange = restTemplate.exchange("https://api.spotify.com/v1/search?q=" + authorName + "&type=track&market=US&limit=10&offset=5",
                HttpMethod.GET,
                httpEntity,
                SpotifyAlbum.class);

        List<SpotifyAlbumDto> spotifyAlbumDtos =
                exchange.getBody()
                .getTracks().getItems().stream()
                .map(item -> new SpotifyAlbumDto(item.getName(),item.getAlbum().getImages().get(0).getUrl()))
                .collect(Collectors.toList());

        for(int i =0;i<spotifyAlbumDtos.size();i++){
            System.out.println(spotifyAlbumDtos.get(i).getTrackName());
            System.out.println(spotifyAlbumDtos.get(i).getImageUrl());
        }


        return spotifyAlbumDtos;
    }
}
