package net.teraoctet.actus.bookmessage;

import com.google.common.reflect.TypeToken;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.teraoctet.actus.Actus;
import static net.teraoctet.actus.Actus.plugin;
import net.teraoctet.actus.economy.ItemShop;
import static net.teraoctet.actus.economy.ItemShopManager.manager;
import static net.teraoctet.actus.economy.ItemShopManager.serializeItemStack;
import static net.teraoctet.actus.economy.ItemShopManager.shop;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.message;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.DataTranslators;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class BookManager {
    public static File file = new File("config/actus/newbie.conf");
    public static final ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    public static ConfigurationNode book = manager.createEmptyNode(ConfigurationOptions.defaults());
    
    //@Inject public PluginContainer self;
    //public ConfigBook configBook;
     
    public BookManager(){}
    
    public static void init() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                
                List<String> msg = new ArrayList<>();
                book = manager.load();
                
                book.getNode("newbie.author", "color").setValue("red");
                book.getNode("newbie.author", "text").setValue("staff");
                book.getNode("newbie", "pages").setValue("staff");
                
                
                msg = new ArrayList<>();
                msg.add("co");
                msg.add("&eComing soon ..!");
                msg.add("&ePlus d'infos sur &bhttp://craft.teraoctet.net\n");
                message.getNode("newbie.pages","GUIDE_GUILD").setValue(msg);
                
                
                manager.save(book);
            }

        } catch (IOException e) { e.printStackTrace(); }
            //book = manager.load();
	
    }
    
    public static Object serializeBook(BookView bv){
        ConfigurationNode node = DataTranslators.CONFIGURATION_NODE.translate(bv.toContainer());
        return node.getValue();
    }

    public static Optional<ItemStack> getBookView(ConfigurationNode node){
        DataView view = DataTranslators.CONFIGURATION_NODE.translate(node);
        view = (DataView) view.get(DataQuery.of(String.valueOf("item"))).get();
        return Sponge.getDataManager().deserialize(ItemStack.class, view);
    }
    
    public boolean saveBook(BookView bv) throws IOException{
        book.getNode("motd2","book").setValue(serializeBook(bv));
        manager.save(book);
        return true;
    }
    
    private Text getNodeText(String title, String node){
        try {
            List<String> list = new ArrayList<>();
            list = book.getNode(title,node).getList(TypeToken.of(String.class));
            Text msg = MESSAGE("");
            
            for(String s : list) {
                if (s.contains("#")){
                    msg = Text.builder()
                            .append(msg)
                            .append(textWithActions(s))
                            .build();
                }else{
                    msg = Text.builder()
                            .append(msg)
                            .append(MESSAGE(s + "\n"))
                            .build();
                }
            }
            return msg;           
        }catch(ObjectMappingException ex){
            return null;
        } 
    }    
    
    @SuppressWarnings("null")
    private Text textWithActions(String s){
        String arg[] = s.split("#");
        Text textPage = MESSAGE("");
        Player p;
        int i = 0;
        for(String chaine : arg){
            switch(chaine){
                case "oc":
                    textPage = Text.builder()
                            .append(textPage)
                            .append(MESSAGE(arg[i+1]))
                            .onClick(TextActions.runCommand(arg[i+2]))
                            .build();
                    i++;
                    break;
                case "onhover":
                    textPage = Text.builder()
                            .append(textPage)
                            .append(MESSAGE(arg[i+1]))
                            .onHover(TextActions.showText(MESSAGE(arg[i+2])))
                            .build();
                    i++;
                    break;
                default:
                    textPage = Text.builder()
                            .append(textPage)
                            .append(MESSAGE(arg[i]))
                            .build();
                    break;
            }
            i++;
            if(i+1 >= arg.length){break;}
        }
        textPage = Text.builder()
                .append(textPage)
                .append(MESSAGE("\n"))
                .build();
        return textPage;
    }
    
    /**
     * Retourne un objet BookView
     * @param title titre du livre a retourner
     * @return BookView
     */    
    public BookView getBookMessage(String title){ 
        Text author = getNodeText(title,"author");
        BookView.Builder bookMessage = BookView.builder()
                .author(author)
                .title(Text.of(TextColors.GOLD, title));
        int x = 1;
        while(!getNodeText(title, String.valueOf(x)).toPlain().equalsIgnoreCase("")){
            bookMessage.addPage(getNodeText(title,String.valueOf(x)));
            x++;
        }
        return bookMessage.build();
    }
}
