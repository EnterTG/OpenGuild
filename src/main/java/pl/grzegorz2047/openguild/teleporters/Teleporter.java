package pl.grzegorz2047.openguild.teleporters;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.grzegorz2047.openguild.managers.MsgManager;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Teleporter {


    private HashMap<UUID, TeleportRequest> requests = new HashMap<>();

    public void addRequest(UUID player, Location source, Location destination, int delay) {
        requests.put(player, new TeleportRequest(player, source, destination, delay));
    }

    public void removeRequest(UUID player) {
        requests.remove(player);
    }


    public void checkHomeRequests() {
        Set<UUID> strings = requests.keySet();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (strings.contains(p.getUniqueId())) {
                TeleportRequest request = requests.get(p.getUniqueId());
                if (System.currentTimeMillis() >= request.getTeleportTime()) {
                    p.teleport(request.getDestination());
                    p.sendMessage(MsgManager.get("tptodestinationsuccess"));
                    strings.remove(p.getUniqueId());
                } else {
                    if ((!p.getLocation().getWorld().equals(request.getSource().getWorld())) || p.getLocation().distance(request.getSource()) >= 1) {
                        requests.remove(p.getUniqueId());
                        p.sendMessage(MsgManager.get("tpcan"));
                    }
                }
            }
        }
    }
}
