package net.teraoctet.actus.utils;

public class Warp {
	
    private String name;
    private String world;
    private int x;
    private int y;
    private int z;
    private String message;
	
    public Warp(String name, String world, int x, int y, int z, String message) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.message = message;
    }

    public void insert() {
            Data.queue("INSERT INTO WARPS VALUES ('" + name + "', '" + world + "', " + x + ", " + y + ", " + z + ", '" + message() + "')");
    }

    public void update() {
            Data.queue("UPDATE WARPS SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + ", message = '" + message() + "' WHERE name = '" + name + "'");
    }

    public void delete() {
            Data.queue("DELETE FROM WARPS WHERE name = '" + name + "'");
    }

    private String message(){
        String msg = this.message;
        msg = msg.replace("'","\'");
        return msg;
    }
	
    public void setName(String name) { this.name = name; }
    public void setWorld(String world) { this.world = world; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setZ(int z) { this.z = z; }
    public void setMessage(String message) { this.message = message; }

    public String getName() { return name; }
    public String getWorld() { return world; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
    public String getMessage() { return message; }

}
