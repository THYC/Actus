package net.teraoctet.actus.bookmessage;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configBook;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class ConfigBook {
        
    private final TypeToken<Book> TOKEN_CONFIG = TypeToken.of(Book.class);
    
    public Optional<Book> load(String fileName) throws FileNotFoundException, ObjectMappingException, IOException {
        File file = new File("config/actus/book/" + fileName + ".conf");
        if(!file.exists())return Optional.empty();
        ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
        ConfigurationNode node;// = manager.createEmptyNode(ConfigurationOptions.defaults());
        node = manager.load();
        Book book = node.getNode("book").getValue(TOKEN_CONFIG);
        return Optional.of(book);
    }
    
    public void saveBook(Book book) throws IOException, ObjectMappingException{
        File file = new File("config/actus/book/" + book.title.toPlain() + ".conf");
        ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
        ConfigurationNode node = manager.createEmptyNode(ConfigurationOptions.defaults());
        node.getNode("book").setValue(TOKEN_CONFIG, book);
        manager.save(node);
    }
    
    public int getCountMessageBook(Player player){
        File rep = new File("config/actus/book"); 
        String [] files = rep.list(); 
        int count = 0;
        for (String file : files){ 
            if(file.contains(player.getName() + "_")){
                count ++;
            }
        } 
        return count;
    }
    
    public Text getMailBook(Player player){
        File rep = new File("config/actus/book"); 
        String [] files = rep.list(); 
        Text fileList = Text.builder().append(MESSAGE("&1  ++ Messagerie ++\n\n")).toText();
        Text tmp; //= Text.EMPTY;
        for (String file : files){ 
            if(file.contains(player.getName() + "_")){
                String[] arg = file.split("_");
                tmp = 
                        Text.builder().append(MESSAGE("&4 [ X ]"))
                        .onClick(TextActions.executeCallback(callDelBook(file)))    
                        .onHover(TextActions.showText(MESSAGE("Supprime"))).toText().concat(
                        Text.builder().append(MESSAGE("&7Date : &1" + arg[2].replace(".conf", "") + "\n&7Expediteur : &1" + arg[1] + "\n"))
                        .onClick(TextActions.executeCallback(callBook(file)))    
                        .onHover(TextActions.showText(MESSAGE("Click pour lire le message"))).toText());
                
                fileList = fileList.concat(tmp);
            }
        } 
        return fileList;
    }
    
    public Consumer<CommandSource> callBook(String file) {
	return (CommandSource src) -> {
            Player player = (Player)src;
            try {
                Optional<Book> book = configBook.load(file.replace(".conf", ""));
                Text home = Text.builder().append(MESSAGE("&1 [Retour liste message]"))
                        .onClick(TextActions.executeCallback(callOpenListBookMessage())).toText();
                
                book.get().addPage(home);
                player.sendBookView(book.get().getBookView());
            } catch (ObjectMappingException | IOException ex) {
                Logger.getLogger(ConfigBook.class.getName()).log(Level.SEVERE, null, ex);
            }

        };
    }
    
    /**
     * Supprime un fichier et met a jour la liste affiche dans le book
     * @param file String du nom de fichier
     * @return 
     */
    public Consumer<CommandSource> callDelBook(String file) {
	return (CommandSource src) -> {
            Player player = (Player)src;
            File book = new File("config/actus/book/" + file); 
            book.delete();
            OpenListBookMessage(player);
        };
    }
    
    /**
     * CallBack pour ouvrir un book contenant la liste des messages du joueur source
     * @return 
     */
    public Consumer<CommandSource> callOpenListBookMessage() {
	return (CommandSource src) -> {
            Player player = (Player)src;
            OpenListBookMessage(player);
        };
    }
    
    public void OpenListBookMessage(Player player){
        BookView.Builder bv = 
                BookView.builder()
                        .author(MESSAGE("STAFF"))
                        .title(MESSAGE("MAIL"))
                        .addPage(configBook.getMailBook(player));
            player.sendBookView(bv.build());
    }
}