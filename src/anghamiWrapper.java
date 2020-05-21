import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import java.util.Set;

class anghamiWrapper {

    //  We make a function here that makes a call to the public API
    //  API KEY: 0965e52bb0605cb551a32c43
    //  APIWrapper must use the GET HTTPS protocol to communicate to the endpoint
    //  Requests (API calls) sent to Anghami servers must include the following headers:
    //  HEADER:  XAT: interns
    //  HEADER: XATH: 0965e52bb0605cb551a32c43
    //  API link: https://bus.anghami.com/public/song?song_id=22451141

    // In order to get an item’s cover art, you
    // need to build its URL. In general the URL will have the following format:
    // https://artworkbus.angham.me/?id=123&size=320 where:
    // id: the cover art’s ID. This can be the value of attributes coverArt, AlbumArt, ArtistArt,
    // covertArtID…
    // ● size: the desired image width. When it comes to sizes, Anghami generates and stores
    // multiple image sizes. The available set of sizes:
    // ○ for square images (width) is {60, 80, 120, 160, 223, 296, 320, 640, 1024}.
    // ○ for rectangular images (width) is {246, 642, 672, 930, 1344, 1854}.

    //Documentation available at: https://s3-eu-west-1.amazonaws.com/anghami.interns2020/Anghami+Public+API+interns2020.pdf

    final private String URL_BASE = "https://bus.anghami.com/public/";
    final private String ART_URL_BASE = "https://artworkbus.angham.me/?";
    final private String TOKEN;
    final private String METHOD = "GET";
    final private String XAT_HEADER = "interns";
    HttpURLConnection connection = null;

    anghamiWrapper(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public void sendRequest(String FINAL_URL, String XATH_TOKEN) throws IOException {
        // init
        URL url = new URL(FINAL_URL);
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
        else if(RETURN_CODE == 400) { //BAD REQUEST
            System.out.println("Bad Request, Revaluate syntax of request");
            CONNECTION_IN = connection.getErrorStream();
        }
        else if(RETURN_CODE == 401) { //ERROR
            System.out.println("Authentication failure");
            CONNECTION_IN = connection.getErrorStream();
        }
        else if(RETURN_CODE == 420) { //ERROR
            System.out.println("Erroneous request");
            CONNECTION_IN = connection.getErrorStream();
        }

        // print resulting stream
        BufferedReader buffer = new BufferedReader(new InputStreamReader(CONNECTION_IN));
        String inputLine;
        while ((inputLine = buffer.readLine()) != null) {/* print json response */
            System.out.println(inputLine); // prints cleartext JSON on one line.
            try {
                JSONParser(inputLine); // prints in formatted style.
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        buffer.close();
    }

    public void JSONParser(String INPUT) throws JSONException {
        JSONObject response = new JSONObject(INPUT);
        JSONArray response_array = response.names(); // gives us the names for the keys.
        for(int i = 0; i < response.length(); i++) {
            System.out.println("Key: " + response_array.get(i) + " || Value: " + response.get((String) response_array.get(i)));
        }
    }


    public void song(String SONG_ID) {
        String URL_APPEND = "song/data?song_id=";
        SONG_ID = SONG_ID.trim();
        String FINAL_URL = URL_BASE + URL_APPEND + SONG_ID;

        //sending json request
        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void album_art(String ID, String SIZE) {
        ID = ID.trim(); SIZE = SIZE.trim();
        String URL_APPEND = "id=" + ID + "&size=" + SIZE;
        String FINAL_URL = ART_URL_BASE + URL_APPEND;

        try {
            sendRequest(FINAL_URL,TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void album(String ALBUM_ID) {
        String URL_APPEND = "album?album_id=";
        ALBUM_ID = ALBUM_ID.trim();
        String FINAL_URL = URL_BASE  + URL_APPEND + ALBUM_ID;

        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void artist(String ARTIST_ID) {
        String URL_APPEND = "artist?artist_id=";
        ARTIST_ID = ARTIST_ID.trim();
        String FINAL_URL = URL_BASE + URL_APPEND + ARTIST_ID;

        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void user(String TYPE) {
        TYPE = TYPE.trim();
        String URL_APPEND = "user/" + TYPE;
        String FINAL_URL = URL_BASE + URL_APPEND;

        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void relations() {
        String URL_APPEND = "relations";
        String FINAL_URL = URL_BASE + URL_APPEND;

        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //overloading to allow an optional parameter
    public void relations(String REQUESTED_USER_ID) {
        REQUESTED_USER_ID = REQUESTED_USER_ID.trim();
        String URL_APPEND = "relations?requested_user_id=" + REQUESTED_USER_ID;
        String FINAL_URL = URL_BASE + URL_APPEND;

        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void tags() {
        String URL_APPEND = "tags/all";
        String FINAL_URL = URL_BASE + URL_APPEND;

        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void tag(String TAG_ID) {
        String URL_APPEND = "tag?tag_id=";
        TAG_ID = TAG_ID.trim();
        String FINAL_URL = URL_BASE + URL_APPEND + TAG_ID;

        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void playlist(String PLAYLIST_ID) {
        String URL_APPEND = "playlist/data?playlist_id=";
        PLAYLIST_ID = PLAYLIST_ID.trim();
        String FINAL_URL = URL_BASE + URL_APPEND + PLAYLIST_ID;

        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void playlist_add(String SONG_ID, String PLAYLIST_ID) {
        SONG_ID = SONG_ID.trim(); PLAYLIST_ID = PLAYLIST_ID.trim();
        String URL_APPEND = "playlist/add?song_id=" + SONG_ID + "&playlist_id=" + PLAYLIST_ID;
        String FINAL_URL = URL_BASE + URL_APPEND;

        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void playlist_remove(String SONG_ID, String PLAYLIST_ID) {
        SONG_ID = SONG_ID.trim(); PLAYLIST_ID = PLAYLIST_ID.trim();
        String URL_APPEND = "playlist/add?song_id=" + SONG_ID + "&playlist_id=" + PLAYLIST_ID;
        String FINAL_URL = URL_BASE + URL_APPEND;

        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void trending(String MUSIC_LANGUAGE) {
        String URL_APPEND = "trending?music_language=";
        MUSIC_LANGUAGE = MUSIC_LANGUAGE.trim();
        String FINAL_URL = URL_BASE + URL_APPEND + MUSIC_LANGUAGE;

        if(MUSIC_LANGUAGE.equals("0")) {
            try {
                sendRequest(FINAL_URL, TOKEN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(MUSIC_LANGUAGE.equals("1")) {
            try {
                sendRequest(FINAL_URL, TOKEN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(MUSIC_LANGUAGE.equals("3")) {
            try {
                sendRequest(FINAL_URL, TOKEN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Please enter an accepted response: " +
                    "\n 0: Returns Arabic and International trending songs " +
                    "\n 1: Returns Arabic trending songs " +
                    "\n 2: Returns International songs");
        }
    }

    public void search(String QUERY, String SEARCH_TYPE, String PAGE) {
        QUERY = QUERY.trim(); SEARCH_TYPE = SEARCH_TYPE.trim(); PAGE = PAGE.trim();
        String URL_APPEND = "search?query=" + QUERY + "&searchtype=" + SEARCH_TYPE + "&page=" + PAGE;
        String FINAL_URL = URL_BASE + URL_APPEND;

        //TODO: add statements to check for invalid input by developer here.
        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //overloading to allow optional parameter
    public void search(String QUERY, String ARTIST_ID, String SEARCH_TYPE, String PAGE) {
        QUERY = QUERY.trim(); ARTIST_ID = ARTIST_ID.trim(); SEARCH_TYPE = SEARCH_TYPE.trim(); PAGE = PAGE.trim();
        String URL_APPEND = "search?query=" + QUERY + "&artistid=" + ARTIST_ID + "&searchtype=" + SEARCH_TYPE + "&page=" + PAGE;
        String FINAL_URL = URL_BASE + URL_APPEND;

        //TODO: add statements to check for invalid input by developer here.
        try {
            sendRequest(FINAL_URL, TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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