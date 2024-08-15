package URLShortener;

import java.util.Scanner;

public class URLShortenerCLI {
    public static void main(String[] args) {
        URLShortener urlShortener = new URLShortener();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  
            
            if (choice == 1) {
                System.out.print("Enter the long URL: ");
                String longUrl = scanner.nextLine();
                if (urlShortener.isDuplicateLongUrl(longUrl)) {
                    System.out.println("URL is already shortened.");
                } else {
                    String shortUrl = urlShortener.shortenUrl(longUrl);
                    System.out.println("Shortened URL: " + shortUrl);
                }
            } else if (choice == 2) {
                System.out.print("Enter the short URL: ");
                String shortUrl = scanner.nextLine();
                String longUrl = urlShortener.expandUrl(shortUrl);
                System.out.println("Expanded URL: " + longUrl);
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
        
        scanner.close();
    }
}

