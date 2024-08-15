import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class URLShortener implements Serializable {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    private static final String BASE_URL = "http://short.url/";
    private static final String DATA_FILE = "url_data.ser";

    private Map<String, String> urlMap;
    private Map<String, String> shortUrlMap;

    public URLShortener() {
        urlMap = new HashMap<>();
        shortUrlMap = new HashMap<>();
        loadData();
    }

    public String shortenUrl(String longUrl) {
        if (urlMap.containsKey(longUrl)) {
            return BASE_URL + urlMap.get(longUrl);
        }
        
        String shortUrl = generateShortUrl(longUrl);

        while (shortUrlMap.containsKey(shortUrl)) {
            shortUrl = generateShortUrl(longUrl + System.currentTimeMillis());
        }
        
        urlMap.put(longUrl, shortUrl);
        shortUrlMap.put(shortUrl, longUrl);
        saveData();
        return BASE_URL + shortUrl;
    }

    public String expandUrl(String shortUrl) {
        String key = shortUrl.replace(BASE_URL, "");
        return shortUrlMap.getOrDefault(key, "Invalid short URL");
    }

    private String generateShortUrl(String longUrl) {
        int hash = longUrl.hashCode();
        StringBuilder shortUrl = new StringBuilder();
        while (hash != 0) {
            shortUrl.append(BASE62.charAt(Math.abs(hash % BASE62.length())));
            hash /= BASE62.length();
        }
        while (shortUrl.length() < SHORT_URL_LENGTH) {
            shortUrl.insert(0, '0');
        }
        return shortUrl.toString();
    }

    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            urlMap = (Map<String, String>) ois.readObject();
            shortUrlMap = (Map<String, String>) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(urlMap);
            oos.writeObject(shortUrlMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDuplicateLongUrl(String longUrl) {
        return urlMap.containsKey(longUrl);
    }

    public boolean isValidShortUrl(String shortUrl) {
        return shortUrlMap.containsKey(shortUrl.replace(BASE_URL, ""));
    }
}



