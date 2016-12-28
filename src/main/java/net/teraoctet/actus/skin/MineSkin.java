package net.teraoctet.actus.skin;

import com.google.gson.Gson;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.profile.property.ProfileProperty;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

public class MineSkin {
    private String URL_UUID_FROM_NAME = "https://api.mojang.com/users/profiles/minecraft/";
    private String URL_PROFILE_FROM_UUID = "https://api.mineskin.org/generate/user/";
    private Optional<SkinProfile> skinProfile = Optional.empty();
    private SkinUUID skinUUID;

    public MineSkin(String playerName) {
        try {
            skinUUID = new Gson().fromJson(new InputStreamReader(new URL(URL_UUID_FROM_NAME + playerName).openStream()), SkinUUID.class);
            if(skinUUID != null){
                skinProfile = Optional.of(new Gson().fromJson(new InputStreamReader(new URL(URL_PROFILE_FROM_UUID + skinUUID.id).openStream()), SkinProfile.class));
            }
        } catch (IOException e) {}
    }
    
    public String getIdentifier() {return skinProfile.get().data.uuid;}
    public UUID getUUID() {return UUID.fromString(getIdentifier());}
    public String getName() {return skinProfile.get().name;}
    
    public Optional<GameProfile> getGameProfile() { 
        if(skinProfile.isPresent()){
            GameProfile gameProfile = GameProfile.of(UUID.fromString(skinProfile.get().data.uuid),"skin" + skinProfile.get().idMineSkin);
            gameProfile.getPropertyMap().put("textures", ProfileProperty.of("textures", skinProfile.get().data.texture.value, skinProfile.get().data.texture.signature));
            return Optional.of(gameProfile);
        }
        return Optional.empty();
    }
}