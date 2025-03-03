package com.ssomar.score.pack.custom;

import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.logging.Utils;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.UUID;

@Getter
public class PackSettings {

    private UUID uuid;

    private SPlugin sPlugin;

    private String filePath;

    private String customPromptMessage;

    private boolean force;

    private PackHttpInjector injector;

    private HostedPathType hostedPathType;

    private String hostedPath;

    public PackSettings(SPlugin sPlugin, UUID uuid, String filePath, String customPromptMessage, boolean force) {
        this.sPlugin = sPlugin;
        this.uuid = uuid;
        this.filePath = filePath;
        this.customPromptMessage = customPromptMessage;
        this.force = force;
        this.injector = new PackHttpInjector(this);
        //netAddress.getLocalHost().getHostAddress()
        hostedPathType = null;
        hostedPath = null;

    }

    public enum HostedPathType {
        EXTERNAL,
        LOCAL_IP,
        LOCALHOST
    }

    public String getHostedPath() {
        if (hostedPath != null) return hostedPath;
        String url = "http://" + getExternalIP() + ":" + Bukkit.getServer().getPort() + "/score/" + getFileName();
        if((hostedPathType == null || hostedPathType == HostedPathType.EXTERNAL) && isHttpURLReachable(url, 3000)) {
            hostedPathType = HostedPathType.EXTERNAL;
            hostedPath = url;
            Utils.sendConsoleMsg("&7Pack hosted at: &e" + url);
            return url;
        }
        Utils.sendConsoleMsg("&cCannot connect to &6" + url);
        try {
            url = "http://"+InetAddress.getLocalHost().getHostAddress()+":" + Bukkit.getServer().getPort() + "/score/" + getFileName();
            if((hostedPathType == null || hostedPathType == HostedPathType.LOCAL_IP) && isHttpURLReachable(url, 3000)) {
                hostedPathType = HostedPathType.LOCAL_IP;
                hostedPath = url;
                Utils.sendConsoleMsg("&7Pack hosted at: &e" + url);
                return url;
            }
        } catch (UnknownHostException e) {}
        Utils.sendConsoleMsg("&cCannot connect to &6" + url);

        url = "http://localhost:" + Bukkit.getServer().getPort() + "/score/" + getFileName();
        if((hostedPathType == null || hostedPathType == HostedPathType.LOCALHOST) && isHttpURLReachable(url, 3000)) {
            hostedPathType = HostedPathType.LOCALHOST;
            hostedPath = url;
            Utils.sendConsoleMsg("&7Pack hosted at: &e" + url);
            return url;
        }
        Utils.sendConsoleMsg("&cCannot connect to &6" + url);

        return null;
    }

    /**
     * Check if an HTTP URL is reachable
     *
     * @param urlString The URL to check
     * @param timeout Timeout in milliseconds
     * @return true if the URL is reachable, false otherwise
     */
    public static boolean isHttpURLReachable(String urlString, int timeout) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD"); // Use HEAD instead of GET for efficiency

            int responseCode = connection.getResponseCode();
            Utils.sendConsoleMsg("&7Debug response output: &7" + responseCode);
            return (200 <= responseCode && responseCode <= 399); // Success codes

        } catch (IOException e) {
            Utils.sendConsoleMsg("&7Debug &e" + urlString + " &7- &e" + e.getMessage());
            return e.getMessage().contains("Unexpected end of file from server");
        }
    }


    public String getFileName() {
        return getFile().getName().replaceAll(" ", "_").replaceAll(".zip", "");
    }

    public File getFile() {
        return new File(filePath);
    }

    public static String getExternalIP() {
        String externalIP = "null";
        try {
            // Connect to an external service that returns your IP
            URL url = new URL("https://api.ipify.org");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            // Read the response (this will be your public IP)
            externalIP = reader.readLine();
            reader.close();
            //System.out.println("External IP Address: " + externalIP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return externalIP;
    }
}
