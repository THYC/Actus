package net.teraoctet.actus.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configBook;
import net.teraoctet.actus.bookmessage.Book;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import static org.spongepowered.api.item.ItemTypes.WRITTEN_BOOK;
import org.spongepowered.api.text.Text;

public class CommandBookAdd implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.book.add")) {
            Player player = (Player)src;
            if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                if(player.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(WRITTEN_BOOK)){
                    if(!ctx.getOne("title").isPresent()){
                        src.sendMessage(USAGE("/bookadd <title>"));
                        return CommandResult.empty();
                    }

                    Optional<String> title = ctx.<String> getOne("title");
                    
                    try {
                        String tmp;
                        List<Text> pages = new ArrayList();
                        for (Text text : player.getItemInHand(HandTypes.MAIN_HAND).get().get(Keys.BOOK_PAGES).get()) {
                            tmp = text.toString();
                            tmp = tmp.replace("\\\\", "\\");
                            tmp = tmp.replace("Text{{", "");
                            tmp = tmp.replace("Text{", "");
                            tmp = tmp.replace("}}", "");
                            tmp = tmp.replace("}", "");
                            tmp = tmp.replace("\"", "");
                            tmp = tmp.replace("text:", "");
                            tmp = tmp.replace("\\n", "\n");
                            pages.add(MESSAGE(tmp));
                        }
                        Book book = new Book(MESSAGE(
                                "&4" + player.getWorld().getName()),
                                MESSAGE(title.get()),
                                pages);
                        configBook.saveBook(book);
                        player.sendMessage(MESSAGE("&eBook '" + title.get() + "' cr\351\351 avec succes !"));
                        player.sendMessage(MESSAGE("&3Tu peux maintenant cr\351er un panneau avec la cde: /signhelp " + title.get()));
                    } catch (IOException | ObjectMappingException ex) {
                        Logger.getLogger(CommandBookAdd.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
