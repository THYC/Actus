package net.teraoctet.actus.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerManager {
    private static final HashMap<String, APlayer> PLAYERS = new HashMap<>();
    
    @SuppressWarnings("element-type-mismatch")
    public static void addAPlayer(String uuid, APlayer aplayer) { if(!PLAYERS.containsKey(PLAYERS))PLAYERS.put(uuid, aplayer);}
    public static void removeAPlayer(String uuid) { if(PLAYERS.containsKey(uuid))PLAYERS.remove(uuid);}
    public static APlayer getAPlayer(String uuid) { return PLAYERS.containsKey(uuid) ? PLAYERS.get(uuid) : null; }
    public static HashMap<String, APlayer> getPlayers() { return PLAYERS; }
    public static boolean isAPlayer(String name){return PLAYERS.entrySet().stream().anyMatch((p) -> (p.getValue().getName().equalsIgnoreCase(name)));}
    
    public static Optional<APlayer> getAPlayerName(String name){
        for (Map.Entry<String,APlayer> ap : PLAYERS.entrySet()) {
            if(ap.getValue().getName().equalsIgnoreCase(name))return Optional.of(ap.getValue());
        }
        return Optional.empty();
    }
    private static final HashMap<String, String> UUIDS = new HashMap<>();
    public static void addUUID(String name, String uuid) { UUIDS.put(name, uuid); }
    public static void removeUUID(String name) { if(UUIDS.containsKey(name)) UUIDS.remove(name); }
    public static String getUUID(String name) { return UUIDS.containsKey(name) ? UUIDS.get(name) : null; }
    public void clear(){
        PLAYERS.clear();
    }
    
    private static final HashMap<String, Long> FIRST_TIME = new HashMap<>();
    public static void addFirstTime(String uuid, Long time) { FIRST_TIME.put(uuid, time); }
    public static void removeFirstTime(String uuid) { if(FIRST_TIME.containsKey(uuid)) FIRST_TIME.remove(uuid); }
    public static long getFirstTime(String uuid) { return FIRST_TIME.containsKey(uuid) ? FIRST_TIME.get(uuid) : 0; }
    
}
