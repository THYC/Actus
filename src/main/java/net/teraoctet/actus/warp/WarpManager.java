package net.teraoctet.actus.warp;

import java.util.HashMap;

public class WarpManager {
    
    private static final HashMap<String, Warp> warps = new HashMap<>();
    public static void addWarp(String name, Warp warp) { if(!warps.containsKey(name)) warps.put(name, warp); }
    public static void removeWarp(String name) { if(warps.containsKey(name)) warps.remove(name); }
    public static Warp getWarp(String name) { return warps.containsKey(name) ? warps.get(name) : null; }
    public static HashMap<String, Warp> getWarps() { return warps; }
    
}
