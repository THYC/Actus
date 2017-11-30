package net.teraoctet.actus.bookmessage;

import java.util.List;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;

@ConfigSerializable
public class Book {
    
    @Setting Text author;
    @Setting Text title;
    @Setting List<Text> pages;
    
    public Book(){}
    
    public Book(Text author, Text title, List<Text> pages){
        this.author = author;
        this.pages = pages;
        this.title = title;
    }
    
    public BookView getBookView(){
        BookView.Builder bv = 
                BookView.builder()
                        .author(author)
                        .title(title);
        
        pages.stream().forEach((page) -> {
            bv.addPage(page);
        });
 
        return bv.build();
    }
    
    public void setAuthor(Text author){
        this.author = author;
    }
    
    public void setTitle(Text title){
        this.title = title;
    } 
    
    public void addPage(Text page){
        this.pages.add(page);
    }
    
    public void setPages(List<Text> pages){
        this.pages = pages;
    }
}