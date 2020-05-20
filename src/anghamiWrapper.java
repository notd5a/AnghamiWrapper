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

    //lets build the URL in the below function:
    StringBuilder SB = new StringBuilder();


}