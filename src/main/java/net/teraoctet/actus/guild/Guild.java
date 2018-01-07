package net.teraoctet.actus.guild;

import static net.teraoctet.actus.utils.Data.addGuild;
import static net.teraoctet.actus.utils.Data.queue;
import static net.teraoctet.actus.utils.Data.removeGuild;

public class Guild {
    private final int id_guild;
    private String name;
    private String world;
    private int X;
    private int Y;
    private int Z;
    private double money;
    private int point;
    private int kill;
    private int dead;
    
    public Guild(int id_guild, String name, String world, int X, int Y, int Z, double money, int point, int kill, int dead) { 
        this.id_guild = id_guild;
        this.name = name;
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.money = money;
        this.point = point;
        this.kill = kill;
        this.dead = dead;
    }
        
    public void insert() {
        queue("INSERT INTO GUILDS VALUES (" + id_guild + ",'" + name + "', '" + world + "', " + X + ", " + Y + ", " + Z + ", " + money + ", " + point + ", " + kill + ", " + dead + ")");
        addGuild(id_guild, this);
    }

    public void update() {
        queue("UPDATE GUILDS SET name = '" + name + "', world = '" + world + "', X = " + X + ", Y = " + Y + ", Z = " + Z + ", money = " + money + ", point = " + point + ", kills = " + kill + ", dead = " + dead + " WHERE id_guild = " + id_guild);
        removeGuild(id_guild);
        addGuild(id_guild, this);
    }

    public void delete() {
        queue("DELETE FROM GUILDS WHERE id_guild = " + id_guild);
        removeGuild(id_guild);
    }
    
    public Integer getID() { return id_guild; }
    public String getName() { return name; }
    public String getWorld() { return world; }
    public Integer getX() { return X; }
    public Integer getY() { return Y; }
    public Integer getZ() { return Z; }
    public Double getMoney() { return money; }
    public Integer getPoint() { return point; }
    public Integer getKill() { return kill; }
    public Integer getDead() { return dead; }
    
    public void setName(String name) { this.name = name; }
    public void setWorld(String world) { this.world = world; }
    public void setX(Integer X) { this.X = X; }
    public void setY(Integer Y) { this.Y = Y; }
    public void setZ(Integer Z) { this.Z = Z; }
    public void setMoney(Double money) { this.money = money; }
    public void setPoint(Integer point) { this.point = point; }
    public void setKill(Integer kill) { this.kill = kill; }
    public void setDead(Integer dead) { this.dead = dead; }
}
