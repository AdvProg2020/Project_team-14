package Model;

import java.util.ArrayList;

public class IP {
    private static ArrayList<IP> allIPs = new ArrayList<>();
    private String ip;
    private ArrayList<Long> tenRecentTimes = new ArrayList<>();
    private static final int BOUND = 10;

    public IP(String ip) {
        this.ip = ip;
        this.tenRecentTimes.add(System.currentTimeMillis());
        allIPs.add(this);
    }

    public static boolean addIp(String Ip) {
        IP ip = getIp(Ip);
        if (ip == null) {
            new IP(Ip);
            return false;
        }
        if (ip.ip.equals(Ip)) {
            if (ip.tenRecentTimes.size() >= BOUND) {
                ip.tenRecentTimes.remove(0);
                ip.tenRecentTimes.add(System.currentTimeMillis());
                return ip.tenRecentTimes.get(BOUND-1) - ip.tenRecentTimes.get(0) < 100;
            } else {
                ip.tenRecentTimes.add(System.currentTimeMillis());
            }
        }
        return false;
    }

    private static IP getIp(String ipString) {
        for (IP ip : allIPs) {
            if (ip.ip.equals(ipString)) {
                return ip;
            }
        }
        return null;
    }

}
