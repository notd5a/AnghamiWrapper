import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.*;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

class anghamiWrapper {

    //  We make a function here that makes a call to the public API
    //  API KEY: 0965e52bb0605cb551a32c43
    //  APIWrapper must use the GET HTTPS protocol to communicate to the endpoint
    //  Requests (API calls) sent to Anghami servers must include the following headers:
    //  HEADER:  XAT: interns
    //  HEADER: XATH: 0965e52bb0605cb551a32c43
    //  API link: https://bus.anghami.com/public/song?song_id=22451141

    final private String URL_BASE = "https://bus.anghami.com/public/";
    final private String TOKEN = "0965e52bb0605cb551a32c43";
    final private String METHOD = "GET";
    final private String XAT_HEADER = "interns";
    HttpURLConnection connection = null;

    public void sendRequest(String FINURL, String XATH_TOKEN, String METHOD, String XAT_HEADER) throws IOException {
        // init
        URL url = new URL(FINURL);
        connection = (HttpsURLConnection) url.openConnection();

        // setting headers and req method
        connection.setRequestMethod(METHOD);
        connection.setRequestProperty("XATH", XATH_TOKEN);
        connection.setRequestProperty("XAT", XAT_HEADER);

        // executing GET request
        int RETURN_CODE = connection.getResponseCode();
        InputStream CONNECTION_IN = null;

        // checking for error codes
        if(RETURN_CODE == 200) { //OK RESPONSE
            CONNECTION_IN = connection.getInputStream();
        }
        else if(RETURN_CODE == 401) { //ERROR
            System.out.println("Authentication failure");
            CONNECTION_IN = connection.getErrorStream();
        }
        else if(RETURN_CODE == 420) { //ERROR
            System.out.println("Erroneous request");
            CONNECTION_IN = connection.getErrorStream();
        }
        else { //OTHER ERROR RESPONSES
            CONNECTION_IN = connection.getErrorStream();
        }

        // print resulting stream
        BufferedReader buffer = new BufferedReader(new InputStreamReader(CONNECTION_IN));
        String inputLine;
        while ((inputLine = buffer.readLine()) != null) /* print json response */ System.out.println(inputLine);
        buffer.close();
    }





    //Just listing the following endpoints below:
    // SONG
    // song
    // liking and unliking
    // ALBUM
    // album
    // album liking and unlike
    // ARTIST
    // artist
    // USER
    // user / artists
    // user / albums
    // user / playlists
    // user / likes
    // user / downloads
    // RELATIONS
    // TAGS
    // tags / all
    // tag
    // PLAYLIST
    // playlist / data
    // playlist / add / and playlist / remove
    // TRENDING
    // SEARCH

    // Example response from the endpoint may look like this:

            /* {
                     "nofollow": false,
                     "id": "22451141",
                     "title": "Aam Betaala' Feek",
                     "album": "Nancy 9 (Hassa Beek)",
                     "albumID": "2380570",
                     "artist": "Nancy Ajram",
                     "artistID": "680",
                     "track": "13",
                     "year": "2016",
                     "duration": "220.13",
                     "coverArt": "50043866",
                     "ArtistArt": "110473370",
                     "allowoffline": 0,
                     "genre": "Lebanese Pop",
                     "AlbumArt": "50043866",
                     "keywords": ["\u0639\u0645 \u0628\u062a\u0639\u0644\u0651\u0642 \u0641\u064a\u0643",
                    "\u0646\u0627\u0646\u0633\u064a \u0639\u062c\u0631\u0645", "\u0646\u0627\u0646\u0633\u064a 9
                    (\u062d\u0627\u0633\u0651\u0629 \u0628\u064a\u0643)"],
                     "languageid": 1,
                     "bitrates": "24,128,196,256",
                     "hexcolor": "#b3756c",
                     "cleardetails": 1,
                     "bitrate": 64,
                     "size": "1794301",
                     "explicit": "0",
                     "is_podcast": 0,
                     "is_original": 1,
                     "vibes": [205, 502]
                }
*/


}