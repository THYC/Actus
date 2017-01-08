package net.teraoctet.actus.bookmessage;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configBook;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

public class CallBackBook {   
    
    String book;
    public Consumer<CommandSource> callHelpBook(String bookName) {
	return (CommandSource src) -> {
            Player player = (Player)src;
            Optional<Book> optbook;
            try {
                optbook = configBook.load(bookName);
                if(optbook.isPresent())player.sendBookView(optbook.get().getBookView());
            } catch (ObjectMappingException | IOException ex) {Logger.getLogger(CallBackBook.class.getName()).log(Level.SEVERE, null, ex);}         
        };
    }
}
