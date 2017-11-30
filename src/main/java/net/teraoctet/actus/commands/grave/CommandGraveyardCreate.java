package net.teraoctet.actus.commands.grave;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configGraveyard;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.grave.Graveyard;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.PlotSelection;
import static net.teraoctet.actus.utils.Config.LEVEL_ADMIN;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;

public class CommandGraveyardCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            if(player.hasPermission("actus.admin.grave") || aplayer.getLevel() == LEVEL_ADMIN()){
                PlotSelection plotSelect = ptm.getPlotSel(player);
                            
                if(!plotSelect.getMinPos().isPresent() || !plotSelect.getMaxPos().isPresent()){
                    player.sendMessage(MESSAGE("&7Faite d'abord un Click gauche avec la pelle sur le coffre"));
                    player.sendMessage(MESSAGE("&7Puis faite un Click droit avec la pelle sur le panneau"));
                    return CommandResult.empty();
                }
                String id = String.valueOf(configGraveyard.getmaxID());
                Graveyard graveyard = new Graveyard(id, plotSelect.getMinPosLoc().get(),plotSelect.getMinPosLoc().get(),plotSelect.getMaxPosLoc().get());
                try {
                    configGraveyard.save(graveyard);
                    player.sendMessages(MESSAGE("&eCaveau enregistr\351 !"));
                    
                    Optional<TileEntity> signBlock = plotSelect.getMaxPosLoc().get().getTileEntity();
                    TileEntity tileSign = signBlock.get();
                    Sign sign=(Sign)tileSign;
                    Optional<SignData> opSign = sign.getOrCreate(SignData.class);
                    SignData signData = opSign.get();
                    signData.setElement(1, MESSAGE("++"));
                    sign.offer(signData);
                } catch (IOException | ObjectMappingException ex) {
                    Logger.getLogger(CommandGraveyardCreate.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return CommandResult.success();
              
            } else {
                src.sendMessage(NO_PERMISSIONS());
            }
            
        } else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
