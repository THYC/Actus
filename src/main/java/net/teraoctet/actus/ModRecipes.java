package net.teraoctet.actus;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
    
    public static void init() {
	craftingRecipes();
    }

    private static void craftingRecipes() {
        // Vanilla items recipes
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.DIRT, 1, 1),
                        new Object[] { new ItemStack(Blocks.DIRT, 1, 0), Items.ROTTEN_FLESH });

        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.DIRT, 1, 0),
                        new Object[] { new ItemStack(Blocks.DIRT, 1, 1), new ItemStack(Items.DYE, 1, 15) });

        GameRegistry.addRecipe(new ItemStack(Items.SADDLE),
                        "XXX", 
                        "XYX", 
                        "Y Y", 
                        'X', Items.LEATHER, 'Y', Items.IRON_INGOT);

        GameRegistry.addRecipe(new ItemStack(Blocks.WEB), 
                        "X X", 
                        " X ", 
                        "X X", 
                        'X', Items.STRING);

        GameRegistry.addRecipe(new ItemStack(Blocks.PACKED_ICE), 
                        "XX", 
                        "XX", 
                        'X', Blocks.ICE);

        // Mod items recipes
        //GameRegistry.addRecipe(new ItemStack(ModItems.slimeChunkDetector),
                       // " X ",
                       // "XYX",
                        //" X ",
                       // 'X', Blocks.slime_block, 'Y', Items.redstone);
    }
}

