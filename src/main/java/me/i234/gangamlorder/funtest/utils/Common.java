package me.i234.gangamlorder.funtest.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class Common {
    public static String colorize(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static Inventory makeGUI(String uncoloredName, int size, Map<Integer, ItemStack> buttonMap, ItemStack defaultItem) {
        Inventory inv = Bukkit.createInventory(null, size, Common.colorize((uncoloredName)));
        ItemStack[] contents = inv.getContents();
        for (int index = 0; index < contents.length; index++) {
            if (buttonMap.get(index) != null) {
                contents[index] = buttonMap.get(index);
                continue;
            }
            if (defaultItem == null) {
                defaultItem = new ItemStack(Material.AIR);

            }// else{
//                ItemMeta defaultItemMeta = defaultItem.getItemMeta();
//                defaultItemMeta.setDisplayName("");
//                defaultItem.setItemMeta(defaultItemMeta);
//            }
            contents[index] = defaultItem;
        }
        inv.setContents(contents);
        return inv;
    }

    public static ItemStack makeItem(String uncoloredName, Material itemType, List<String> lore, List<Enchantment> ench, List<Integer> enchLevel, Boolean hideEnch) {
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
            for (int index = 0; index < ench.size(); index++) {
                meta.addEnchant(ench.get(index), enchLevel.get(index), true);

            }
        }
        if (hideEnch) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }
}
