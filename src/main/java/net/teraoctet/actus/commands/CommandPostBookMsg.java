package net.teraoctet.actus.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.actus.Actus.configBook;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.bookmessage.Book;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import static org.spongepowered.api.item.ItemTypes.WRITTEN_BOOK;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.ProviderRegistration;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;

public class CommandPostBookMsg implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("actus.admin.post")) {
            if(src instanceof Player){
                Player player = (Player)src;
                
                Optional<String> optperm = ctx.<String> getOne("perm");
                String perm = "actus.msg";
                
                if(optperm.isPresent()){
                    perm = perm + optperm.get();
                }
                
                ItemStack writableBook = ItemStack.builder().itemType(ItemTypes.WRITABLE_BOOK).build();
                
                Optional<ItemStack> is = player.getItemInHand(HandTypes.MAIN_HAND);
                if(is.isPresent()){
                    if(is.get().getType().equals(WRITTEN_BOOK)){
                        Book book = new Book();
                        if(is.get().get(Keys.BOOK_AUTHOR).isPresent()){
                            book.setAuthor(is.get().get(Keys.BOOK_AUTHOR).get());
                                                                            
                            Optional<ProviderRegistration<UserStorageService>> optprov = Sponge.getServiceManager().getRegistration(UserStorageService.class);

                            if(optprov.isPresent()) {
                                ProviderRegistration<UserStorageService> provreg = optprov.get();
                                UserStorageService uss = provreg.getProvider();
                                
                                int n = 0;
                                Collection<GameProfile> cgp = uss.getAll();
                                for(GameProfile gp : cgp){
                                    if(uss.get(gp.getUniqueId()).get().hasPermission(perm)){
                                        book.setTitle(MESSAGE(gp.getName().get() + "_STAFF" + "_" + sm.dateShortToString()));
                                        String tmp;
                                        List<Text> pages = new ArrayList();

                                        if(!is.get().get(Keys.BOOK_PAGES).isPresent()){
                                            player.sendMessage(MESSAGE("&dEnvoi impossible, ton livre ne contient aucun message !"));
                                            return CommandResult.empty();
                                        }

                                        for (Text text : is.get().get(Keys.BOOK_PAGES).get()) {
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
                                        book.setPages(pages);
                                        configBook.saveBook(book);
                                        n = n+1;
                                    }
                                }
                                player.sendMessage(MESSAGE("&eTa lettre a \351t\351 poste pour : " + n + " joueur(s)"));
                                player.setItemInHand(HandTypes.MAIN_HAND, writableBook);
                            }     
                        }
                    }else{
                        player.sendMessages(MESSAGE("&eTu dois ecrire ton message sur un livre a ecrire"));
                        player.sendMessages(MESSAGE("&epuis tape la commande : &b/postmsg"));
                        player.sendMessages(MESSAGE("&ePour envoyer a des groupes de membres precispuis tape : &b/postmsg &7nomdugroupe"));
                        player.sendMessages(MESSAGE("&etu remplace &7nomdugroupe &epar le nom du groupe inscrit dans le fichier permission"));
                        player.sendMessages(MESSAGE("&ela permission doit ressembler a cela : &7actus.msg &epour tous le monde"));
                        player.sendMessages(MESSAGE("&eet a cela : &7actus.msg.nomdugroupe &epour les groupes specifiques"));               
                    }
                }
            }
            
            return CommandResult.success();
        } 
               
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
