package me.i234.gangamlorder.funtest.utils;

import me.i234.gangamlorder.funtest.FunTest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Common {
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Create a inventory from params
     *
     * @param uncoloredName Name of the inventory.
     * @param size          The size.
     * @param buttonMap     map of the the slot to the button.
     * @param defaultItem   Default item type of the menu. Will use <code>Material.AIR</code> if null is specified.
     * @return an Inventory
     */

    public static Inventory makeGUI(String uncoloredName, int size, Map<Integer, ItemStack> buttonMap, @Nullable ItemStack defaultItem) {
        Inventory inv = Bukkit.createInventory(null, size, Common.colorize((uncoloredName)));
        ItemStack[] contents = inv.getContents();
        for (int index = 0; index < contents.length; index++) {
            if (buttonMap.get(index) != null) {
                contents[index] = buttonMap.get(index);
                continue;
            }
            if (defaultItem == null) {
                defaultItem = new ItemStack(Material.AIR);

            }/* else {
                ItemMeta defaultItemMeta = defaultItem.getItemMeta();
                defaultItemMeta.setDisplayName(" ");
                defaultItem.setItemMeta(defaultItemMeta);
            }*/
            contents[index] = defaultItem;
        }
        inv.setContents(contents);
        return inv;
    }

    //TODO make Map<Enchantment, Integer> for enchants.
    public static ItemStack makeItem(String uncoloredName, Material itemType, List<String> lore, Map<Enchantment, Integer> ench, Boolean hideEnch) {
        ItemStack item = new ItemStack(itemType);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Common.colorize(uncoloredName));
        if (lore != null) {
            for (String string : lore) {
                lore.set(lore.indexOf(string), colorize(string));
            }
            meta.setLore(lore);
        }
        if (ench != null) {
            for (Enchantment enchantment : ench.keySet()) {
                meta.addEnchant(enchantment, ench.get(enchantment), true);

            }
        }
        if (hideEnch) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static void log(Level level, String message) {
        FunTest.getInstance().getServer().getLogger().log(level, colorize(message));
    }
}
