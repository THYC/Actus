package net.teraoctet.actus.plot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.Optional;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.Data.datasource;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.utils.DeSerialize;
import net.teraoctet.actus.utils.Config;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import static org.spongepowered.api.Sponge.getGame;
import static org.spongepowered.api.block.BlockTypes.AIR;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;

public class Plot {
    
    private String plotName;
    private int level;
    private String world;
    private int x1;
    private int y1;
    private int z1;
    private int x2;
    private int y2;
    private int z2;
    private boolean jail;
    private boolean noEnter;
    private boolean noFly;
    private boolean noBuild;
    private boolean noBreak;
    private boolean noTeleport;
    private boolean noInteract;
    private boolean noFire;
    private String message;
    private int mode;
    private boolean noMob;
    private boolean noAnimal;
    private boolean noTNT;
    private boolean noCommand;
    private String uuidOwner;
    private String uuidAllowed;
    private int id_guild;
    private boolean spawnGrave;
    private boolean noPVPplayer;
    private boolean noPVPmonster;
    private boolean noProjectile;
    private boolean noLiquidFlow;
    private boolean autoForest;
    
    public Plot(String plotName, int level, String world, int x1, int y1, int z1, int x2, int y2, int z2, 
    boolean jail, boolean noEnter, boolean noFly, boolean noBuild, boolean noBreak, boolean noTeleport, boolean noInteract, boolean noFire, 
    String message, int mode, boolean noMob, boolean noAnimal, boolean noTNT, boolean noCommand, String uuidOwner, String uuidAllowed, 
    int id_guild, boolean spawnGrave, boolean noPVPplayer, boolean noPVPmonster, boolean noProjectile, boolean noLiquidFlow, boolean autoForest){
        
        this.plotName = plotName;
        this.level = level;
        this.world = world;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.jail = jail;
        this.noEnter = noEnter;
        this.noFly = noFly;
        this.noBuild = noBuild;
        this.noBreak = noBreak;
        this.noTeleport = noTeleport;
        this.noInteract = noInteract;
        this.noFire = noFire;
        this.message = message;
        this.mode = mode;
        this.uuidOwner = uuidOwner;
        this.uuidAllowed = uuidAllowed;
        this.noMob = noMob;
        this.noAnimal = noAnimal;
        this.noTNT = noTNT;
        this.noCommand = noCommand;
        this.id_guild = id_guild;
        this.spawnGrave = spawnGrave;
        this.noPVPplayer = noPVPplayer;
        this.noPVPmonster = noPVPmonster;
        this.noProjectile = noProjectile;
        this.noLiquidFlow = noLiquidFlow;
        this.autoForest = autoForest;
    }
    
    public Plot(String plotName, int level, String world, int x1, int y1, int z1, int x2, int y2, int z2, 
    boolean jail, boolean noEnter, boolean noFly, boolean noBuild, boolean noBreak, boolean noTeleport, boolean noInteract, boolean noFire, 
    String message, int mode, boolean noMob, boolean noAnimal, boolean noTNT, boolean noCommand, String uuidOwner, String uuidAllowed){
        
        this.plotName = plotName;
        this.level = level;
        this.world = world;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.jail = jail;
        this.noEnter = noEnter;
        this.noFly = noFly;
        this.noBuild = noBuild;
        this.noBreak = noBreak;
        this.noTeleport = noTeleport;
        this.noInteract = noInteract;
        this.noFire = noFire;
        this.message = message;
        this.mode = mode;
        this.noMob = noMob;
        this.noAnimal = noAnimal;
        this.noTNT = noTNT;
        this.uuidOwner = uuidOwner;
        this.uuidAllowed = uuidAllowed;
        this.noCommand = noCommand;
        this.id_guild = 0;
        this.spawnGrave = true;
        this.noPVPplayer = false;
        this.noPVPmonster = false;
        this.noProjectile = false;
        this.noLiquidFlow = false;
        this.autoForest = true;
    }
    
    public Plot(String plotName, int level, String world, int x1, int y1, int z1, int x2, int y2, int z2, String uuidOwner, String uuidAllowed){
        this.plotName = plotName;
        this.level = level;
        this.world = world;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.jail = false;
        this.noEnter = false;
        this.noFly = true;
        this.noBuild = true;
        this.noBreak = true;
        this.noTeleport = false;
        this.noInteract = true;
        this.message = "&b-- SECURISED --";
        this.mode = 0;
        this.uuidOwner = uuidOwner;
        this.uuidAllowed = uuidAllowed;
        this.noMob = false;
        this.noAnimal = false;
        this.noTNT = true;
        this.noCommand = false;
        this.spawnGrave = true;
        this.noPVPplayer = false;
        this.noPVPmonster = false;
        this.noProjectile = false;
        this.noLiquidFlow = false;
        this.autoForest = true;
    }
    
    public Plot(String world, int x1, int y1, int z1, int x2, int y2, int z2){
        this.plotName = "TMP";
        this.level = 0;
        this.world = world;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.jail = false;
        this.noEnter = false;
        this.noFly = true;
        this.noBuild = true;
        this.noBreak = true;
        this.noTeleport = false;
        this.noInteract = true;
        this.message = "&b-- SECURISED --";
        this.mode = 0;
        this.uuidOwner = "";
        this.uuidAllowed = "";
        this.noMob = false;
        this.noAnimal = false;
        this.noTNT = true;
        this.noCommand = false;
        this.spawnGrave = true;
        this.noPVPplayer = false;
        this.noPVPmonster = false;
        this.noProjectile = false;
        this.noLiquidFlow = false;
        this.autoForest = true;
    }
    
    public void insert() {
	Data.queue("INSERT INTO PLOT VALUES ('" + plotName + "', " + level + ", '" + world + "', " + x1 + ", " + y1 + ", " + z1
        + ", " + x2 + ", " + y2 + ", " + z2 + ", " + jail + ", " + noEnter + ", " + noFly + ", " + noBuild + ", " + noBreak + ", " + noTeleport 
        + ", " + noInteract + ", " + noFire + ", '" + message() + "', " + mode + ", " + noMob + ", " + noAnimal + ", " + noTNT + ", " + noCommand 
        + ", '" + uuidOwner + "', '" + uuidAllowed + "', " + id_guild + ", "+ spawnGrave + ", " + noPVPplayer + ", " + noPVPmonster 
        + ", " + noProjectile + ", " + noLiquidFlow + ", " + autoForest + ")");
    }
    
    /**
     * Enregistre une annonce de vente de la parcelle (Plot)
     * dans la base de donnée.
     * @param loc Location  
     */
    public void addSale(Location loc) {
        String location = DeSerialize.location(loc);
	Data.queue("INSERT INTO PLSALE VALUES ('" + plotName + "', '" + location + "')");
    }
    
    /**
     * Supprime l'annonce de vente de la parcelle (Plot) 
     * ainsi que tous les panneaux (Sign) associés si :
     * le paramètre DEL_SIGN_AFTER_SALE = true
     * sinon : supprime le texte uniquement.
     */
    public void delSale() {
        try {
            Connection c = datasource.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM PLSALE WHERE plotName = '" + plotName + "'");
            while(rs.next()) {
                Optional<Location<World>> loc = DeSerialize.getLocation(rs.getString("location"));
                if (loc.get().getBlockType().equals(BlockTypes.STANDING_SIGN) || loc.get().getBlockType().equals(BlockTypes.WALL_SIGN)){
                    if(Config.DEL_SIGN_AFTER_SALE()){
                        loc.get().setBlockType(AIR);
                    } else {
                        Optional<TileEntity> signBlock = loc.get().getTileEntity();
                        TileEntity tileSign = signBlock.get();
                        Sign sign=(Sign)tileSign;
                        Optional<SignData> opSign = sign.getOrCreate(SignData.class);

                        SignData signData = opSign.get();
                        List<Text> sale = new ArrayList<>();
                        sale.add(MESSAGE("&1VENDU"));
                        sale.add(MESSAGE(""));
                        sale.add(MESSAGE(""));
                        sale.add(MESSAGE(""));
                        signData.set(Keys.SIGN_LINES,sale );
                        sign.offer(signData);
                    }
                } 
            }
            s.close();
            c.close();
        } catch (SQLException e) {}
	Data.queue("DELETE FROM PLSALE WHERE plotName = '" + plotName + "'");
    }
    
    public void update() {
	Data.queue("UPDATE PLOT SET plotName = '" + plotName + "', level = " + level + ", world = '" + world 
        + "', X1 = " + x1 + ", Y1 = " + y1 + ", Z1 = " + z1 + ", X2 = " + x2 + ", Y2 = " + y2 + ", Z2 = " + z2 + ", jail = " + jail 
        + ", noEnter = " + noEnter + ", noFly = " + noFly + ", noBuild = " + noBuild + ", noBreak = " + noBreak + ", noTeleport = " + noTeleport 
        + ", noInteract = " + noInteract + ", noFire = " + noFire + ", message = '" + message() + "', mode = " + mode + ", uuidOwner = '" + uuidOwner 
        + "', uuidAllowed = '" + uuidAllowed + "', noMob = " + noMob + ", noAnimal = " + noAnimal + ", noTNT = " + noTNT 
        + ", noCommand = " + noTNT + ", id_guild = " + id_guild + ", spawnGrave = " + spawnGrave + ", noPVPplayer = " + noPVPplayer 
        + ", noPVPmonster = " + noPVPmonster + ", noProjectile = " + noProjectile + ", noLiquidFlow = " + noLiquidFlow + ", autoForest = " + autoForest + " WHERE plotName = '" + plotName + "'");
    }
	
    public void delete() {
	Data.queue("DELETE FROM PLOT WHERE plotName = '" + plotName + "'");
    }
    
    private String message(){
        String msg = this.message;
        msg = msg.replace("'","\'");
        return msg;
    }
    
    /**
     * Chaine type String comprenant la liste des Flags et de leurs valeurs
     * enregistré sur la parcelle (Plot)
     * @return String
     */
    public String getFlag(){
        String flag = "Jail(prison) : " + this.jail + " | ";
        flag = flag + "noFly(vol) : " + this.noFly + " | ";
        flag = flag + "noEnter(entrée) : " + this.noEnter + " | ";
        flag = flag + "noBuild(construire) : " + this.noBuild + " | ";
        flag = flag + "noBreak(casser) : " + this.noBreak + " | ";
        flag = flag + "notp(téléportation) : " + this.noTeleport + " | ";
        flag = flag + "noInterac(Interaction) : " + this.noInteract + " | ";
        flag = flag + "noFire(Incendie) : " + this.noFire + " | ";
        flag = flag + "Gamemode : " + this.mode + " | ";
        flag = flag + "noMob(monstre) : " + this.noMob + " | ";
        flag = flag + "noAnimal : " + this.noAnimal + " | ";
        flag = flag + "noTNT(TNT) : " + this.noTNT + " | ";
        flag = flag + "noCommand(commande) : " + this.noCommand + " | ";
        return flag;
    }
   
    public void setUuidOwner(String uuidOwner){this.uuidOwner = uuidOwner;}
    public void setUuidAllowed(String uuidAllowed){this.uuidAllowed = uuidAllowed;}
    public void setNoInteract(boolean noInteract){this.noInteract = noInteract;}
    public void setName(String plotName){this.plotName = plotName;}
    public void setLevel(int level){this.level = level;}
    public void setworld(String world){this.world = world;}
    public void setX1(int x1){this.x1 = x1;}
    public void setX2(int x2){this.x2 = x2;}
    public void setY1(int y1){this.y1 = y1;}
    public void setY2(int y2){this.y2 = y2;}
    public void setZ1(int z1){this.z1 = z1;}
    public void setZ2(int z2){this.z2 = z2;}
    public void setNoBreak(boolean noBreak){this.noBreak = noBreak;}
    public void setNoBuild(boolean noBuild){this.noBuild = noBuild;}
    public void setNoFire(boolean noFire){this.noFire = noFire;}
    public void setNoTeleport(boolean noTeleport){this.noTeleport = noTeleport;}
    public void setNoFly(boolean noFly){this.noFly = noFly;}
    public void setJail(boolean jail){this.jail = jail;}
    public void setNoEnter(boolean noEnter){this.noEnter = noEnter;}
    public void setMessage(String message){this.message = message;}
    public void setNoMob(boolean noMob){this.noMob = noMob;}
    public void setNoAnimal(boolean noAnimal){this.noAnimal = noAnimal;}
    public void setNoTNT(boolean noTNT){this.noTNT = noTNT;}
    public void setMode(int mode){this.mode = mode;} 
    public void setNoCommand(boolean noCommand){this.noCommand = noCommand;} 
    public void setIdGuild(int id_guild){this.id_guild = id_guild;}
    public void setSpawnGrave(boolean spawnGrave){this.spawnGrave = spawnGrave;}
    public void setNoPVPplayer(boolean noPVPplayer){this.noPVPplayer = noPVPplayer;}
    public void setNoPVPmonster(boolean noPVPmonster){this.noPVPmonster = noPVPmonster;}
    public void setNoProjectile(boolean noProjectile){this.noProjectile = noProjectile;}
    public void setNoLiquidFlow(boolean noLiquidFlow){this.noLiquidFlow = noLiquidFlow;}
    public void setAutoForest(boolean autoForest){this.autoForest = autoForest;}
    
    public String getUuidOwner(){return this.uuidOwner;}
    public String getUuidAllowed(){return this.uuidAllowed;}
    public boolean getNoInteract(){return this.noInteract;}
    public String getName(){return this.plotName;}
    public int getLevel(){return this.level;}
    public String getworldName(){return this.world;}
    public int getX1(){return this.x1;}
    public int getX2(){return this.x2;}
    public int getY1(){return this.y1;}
    public int getY2(){return this.y2;}
    public int getZ1(){return this.z1;}
    public int getZ2(){return this.z2;}
    public boolean getNoBreak(){return this.noBreak;}
    public boolean getNoBuild(){return this.noBuild;}
    public boolean getNoFire(){return this.noFire;}
    public boolean getNoTeleport(){return this.noTeleport;}
    public boolean getNoFly(){return this.noFly;}
    public boolean getJail(){return this.jail;}
    public boolean getNoEnter(){return this.noEnter;}
    public String getMessage(){return this.message;}
    public boolean getNoMob(){return this.noMob;}
    public boolean getNoAnimal(){return this.noAnimal;}
    public boolean getNoTNT(){return this.noTNT;}
    public int getMode(){return this.mode;} 
    public boolean getNoCommand(){return this.noCommand;}
    public int getIdGuild(){return this.id_guild;}
    public boolean getSpawnGrave(){return this.spawnGrave;}
    public boolean getNoPVPplayer(){return this.noPVPplayer;}
    public boolean getNoPVPmonster(){return this.noPVPmonster;}
    public boolean getNoProjectile(){return this.noProjectile;} 
    public boolean getNoLiquidFlow(){return this.noLiquidFlow;}
    public boolean getAutoForest(){return this.autoForest;}
    
    /**
     * Retourne l'objet World correspondant
     * @return World
     */
    public Optional<World> getWorld(){
        Optional<World> w = getGame().getServer().getWorld(world);
        if(w.isPresent()){
            return w;
        }else{
            return Optional.empty();
        }
    }
    
    /**
     * Nom du propriétaire de la parcelle (Plot)
     * @return String
     */
    public String getNameOwner(){
        if (uuidOwner.equalsIgnoreCase("ADMIN")){ 
            return "ADMIN";
        }else{
            if(!isNull(getAPlayer(uuidOwner))){
                return getAPlayer(uuidOwner).getName();
            }else{
                return uuidOwner;
            }
        }
    }
    
    /**
     * Chaine type String comprenant les noms des joueurs autorisés 
     * à utiliser la parcelle (Plot)
     * @return Chaine type String
     */
    public String getNameAllowed(){
        String[] UUID = this.uuidAllowed.split(" ");
        String NameAllowed = "";
        for(String uuid : UUID){
            if (uuid.equalsIgnoreCase("ADMIN")) return "ADMIN";
            if(getAPlayer(uuid) == null)return "ADMIN";
            NameAllowed = NameAllowed + " " + getAPlayer(uuid).getName();
        }
        return NameAllowed;
    }
    
    /**
     * List comprenant les noms des joueurs autorisés 
     * à utiliser la parcelle (Plot)
     * @return List type String
     */
    public List<String> getListUuidAllowed(){
        String[] UUID = this.uuidAllowed.split(" ");
        List<String> nameAllowed = new ArrayList();
        nameAllowed.addAll(Arrays.asList(UUID));
        return nameAllowed;
    }
    
    /**
     * Objet Location correspondant au point X1, Y1, Z1
     * @return Location
     */
    public Location getLocX1Y1Z1()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x1, this.y1, this.z1);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X2, Y2, Z2
     * @return Location
     */
    public Location getLocX2Y2Z2()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x2, this.y2, this.z2);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X1, Y1, Z2
     * @return Location
     */
    public Location getLocX1Y1Z2()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x1, this.y1, this.z2);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X2, Y2, Z1
     * @return Location
     */
    public Location getLocX2Y2Z1()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x2, this.y2, this.z1);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X1, Y2, Z2
     * @return Location
     */
    public Location getLocX1Y2Z2()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x1, this.y2, this.z2);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X2, Y1, Z2
     * @return Location
     */
    public Location getLocX2Y1Z2()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x2, this.y1, this.z1);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X2, Y1, Z1
     * @return Location
     */
    public Location getLocX2Y1Z1()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x2, this.y1, this.z2);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X1, Y2, Z1
     * @return Location
     */
    public Location getLocX1Y2Z1()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x1, this.y2, this.z1);
        return location;
    }
    
    /**
     * Objet Location correspondant au centre de la parcelle (Plot)
     * @return l'objet Location
     */
    public Optional<Location> getSpawnPlot(){
        Optional<World> worldopt = getWorld();
        if(worldopt.isPresent()){
            int locX = (getX1() + getX2())/2;
            int locZ = (getZ1() + getZ2())/2;
            int locY = getYSpawn(locX,locZ);
            Location location = new Location(worldopt.get(),locX, locY, locZ);
            return Optional.of(location);
        } else {
            return Optional.empty();
        }
    }
    
    /**
     * Point Y correspondant au premier bloc le plus haut, au niveau du sol
     * @param X coordonnée X du point à calculer
     * @param Z coordonnée Z du point à calculer
     * @return le point Y
     */
    public int getYSpawn(int X, int Z){
        Optional<World> worldopt = getWorld();
        if(worldopt.isPresent()){
            Location location = new Location(worldopt.get(), X, 250, Z);
            while (location.getBlockType().equals(AIR)){
                location = location.add(0,-1,0);
            }
            location = location.add(0,1,0);
            return location.getBlockY();
        }
        return 0;
    }
        
}
