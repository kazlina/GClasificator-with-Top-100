package models;

import java.util.*;
//import javax.persistence.*;
import java.io.*;

import com.google.api.client.auth.oauth2.draft10.AccessTokenResponse;

public class Auth {
  /**
   * OAuth client ID.
   */
  public static String CLIENT_ID = Util.config.getProperty("oauth_client_id").trim();

  /**
   * OAuth client secret.
   */
  public static String CLIENT_SECRET = Util.config.getProperty("oauth_client_secret").trim();

  /**
   * OAuth client secret.
   */
  public static String GOOGLE_API_KEY =Util.config.getProperty("google_api_key").trim();

  /**
   * OAuth redirect URI.
   */
  private static String REDIRECT_URI = Util.config.getProperty("oauth_redirect_uri").trim();

  /**
   * Space separated list of OAuth scopes.
   */
  private static String SCOPES = Util.config.getProperty("oauth_scopes").trim();

  private static AccessTokenResponse accessTokenResponse;

  public static String getRefreshToken() {
    return accessTokenResponse.refreshToken;
  }

  public static String getAccessToken() {
    return accessTokenResponse.accessToken;
  }
}