package ismyinternetbroken;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import lombok.SneakyThrows;


public class IsMyInternetBroken {
    
    public static void main(String[] args) throws Exception {
        IsMyInternetBroken isMyInternetBroken = new IsMyInternetBroken();
        isMyInternetBroken.start();  
    }
    
    public IsMyInternetBroken() {
    
    }
    
    public void start() {
        UpdateIconTimerTask updateIcon = new UpdateIconTimerTask();
        
        Timer timer = new Timer();

        //scheduling the task at fixed rate delay
        timer.scheduleAtFixedRate(updateIcon, 500, 4000);   
    }
    
    
    private class UpdateIconTimerTask extends TimerTask {
        
        private final SystemTray tray = SystemTray.getSystemTray();
        
        private final TrayIcon trayIconNoInternet = getTrayIcon("no_internet.png");
        private final TrayIcon tryIconAllWorks = getTrayIcon("all_works.png");
        private final TrayIcon trayIconOnlyRouterWorks = getTrayIcon("only_router_works.png");
        
        private volatile TrayIcon currentlyActiveIcon = trayIconNoInternet; 
                 
        public UpdateIconTimerTask() {
    
        }
        

        @Override
        @SneakyThrows
        public void run() {
            
            var internetAvailable = isTheFollowingAddressReachable("www.google.com");
            
            if (internetAvailable) {
                showIconInTray(tryIconAllWorks);
            } else {
                var routerAvailable = isTheFollowingAddressReachable("192.168.1.1");
               
                if (routerAvailable) {
                    showIconInTray(trayIconOnlyRouterWorks);
                } else {
                    showIconInTray(trayIconNoInternet);
                }
            }
            
        }
        
        @SneakyThrows
        public void showIconInTray(TrayIcon trayIcon) {
            if (currentlyActiveIcon != trayIcon) {
                tray.remove(currentlyActiveIcon);
                tray.add(trayIcon);
                currentlyActiveIcon = trayIcon;
            }
        }
    
    }
     
    
    @SneakyThrows
    private TrayIcon getTrayIcon(String resourcePath) {
        var imageAsResource = IsMyInternetBroken.class.getClassLoader().getResourceAsStream(resourcePath);
        Image image = javax.imageio.ImageIO.read(imageAsResource);
        return new TrayIcon(image);
    }

    // Sends ping request to a provided IP address 
    @SneakyThrows
    private boolean isTheFollowingAddressReachable(String ipAddressOrHost) {
        Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 -t 1 " + ipAddressOrHost);
        int returnVal = p1.waitFor();
        boolean reachable = (returnVal == 0);
        
        return reachable;
        
        
        // -> this java way is not working reliable with www.google.com for instance...
        // More: https://stackoverflow.com/questions/9922543/why-does-inetaddress-isreachable-return-false-when-i-can-ping-the-ip-address
        //        InetAddress inetAddress = InetAddress.getByName(ipAddressOrHost);
        //        return inetAddress.isReachable(1000);
    }

}
