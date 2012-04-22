package models;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Util {
  public static final boolean DEBUG = false;
  public static final Properties config = getConfig();
  public static final JsonFactory JSON_FACTORY = new GsonFactory();
  public static final HttpTransport TRANSPORT = new NetHttpTransport();
  
  static Properties getConfig() {
    InputStream input = Util.class.getResourceAsStream("config.properties");
    Properties config = new Properties();
    try {
      config.load(input);
    } catch (IOException e) {
      System.exit(1);
    }
    return config;
  }
}
