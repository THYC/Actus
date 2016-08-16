package net.teraoctet.actus.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static net.teraoctet.actus.Actus.serverManager;

public class PlayerManager {
    private static final HashMap<String, APlayer> players = new HashMap<>();
    
    @SuppressWarnings("element-type-mismatch")
    public static void addAPlayer(String uuid, APlayer aplayer) { if(!players.containsKey(players))players.put(uuid, aplayer);}
    public static void removeAPlayer(String uuid) { if(players.containsKey(uuid))players.remove(uuid);}
    public static APlayer getAPlayer(String uuid) { return players.containsKey(uuid) ? players.get(uuid) : null; }
    public static HashMap<String, APlayer> getPlayers() { return players; }
    public static boolean isAPlayer(String name){return players.entrySet().stream().anyMatch((p) -> (p.getValue().getName().equalsIgnoreCase(name)));}
    public static Optional<APlayer> getAPlayerName(String name){
        for (Map.Entry<String,APlayer> ap : players.entrySet()) {
            if(ap.getValue().getName().equalsIgnoreCase(name))return Optional.of(ap.getValue());
        }
        return Optional.empty();
    }
    private static final HashMap<String, String> uuids = new HashMap<>();
    public static void addUUID(String name, String uuid) { uuids.put(name, uuid); }
    public static void removeUUID(String name) { if(uuids.containsKey(name)) uuids.remove(name); }
    public static String getUUID(String name) { return uuids.containsKey(name) ? uuids.get(name) : null; }
    
    private static final HashMap<String, Long> firstTime = new HashMap<>();
    public static void addFirstTime(String uuid, Long time) { firstTime.put(uuid, time); }
    public static void removeFirstTime(String uuid) { if(firstTime.containsKey(uuid)) firstTime.remove(uuid); }
    public static long getFirstTime(String uuid) { return firstTime.containsKey(uuid) ? firstTime.get(uuid) : 0; }
}
