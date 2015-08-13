package me.beastman3226.iq.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import me.beastman3226.iq.ItemQuery;
import me.beastman3226.iq.requisitions.RequisitionManager;
import me.beastman3226.iq.utils.Converter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author beastman3226
 */
public class CommandHandler implements CommandExecutor {

    public static HashMap<String, Inventory> playerMap = new HashMap<String, Inventory>();

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (cs instanceof Player) {
            if (cs.hasPermission(cmnd.getPermission())) {
                if (ItemQuery.instance.db) {
                    if (cmnd.getName().equalsIgnoreCase("request") && strings.length > 0) {
                        return RequisitionManager.Database.createRequisition(strings, (Player) cs);
                    } else if (cmnd.getName().equalsIgnoreCase("retrieve") && strings.length >= 0) {
                        Inventory i = Bukkit.createInventory(null, 56, "Requisition #" + RequisitionManager.Database.getRequisitionID(cs.getName()));
                        String[] vitems = RequisitionManager.Database.getRequisition(cs.getName()).split(",");
                        ItemStack[] items = Converter.convert(vitems);
                        if (items.length > 56) {
                            ArrayList<ItemStack> enough = new ArrayList<ItemStack>(Arrays.asList(items));
                            i.addItem(enough.subList(0, 55).toArray(new ItemStack[]{}));
                            int k = 0;
                            while (k < 56) {
                                enough.remove(k);
                                k++;
                            }
                        } else {
                            i.addItem(items);
                        }
                        playerMap.put(cs.getName(), i);
                        ((Player) cs).openInventory(i);
                        return true;
                    }
                } else {
                    if (cmnd.getName().equalsIgnoreCase("request") && strings.length > 0) {
                        return RequisitionManager.File.createRequisition(strings, (Player) cs);
                    } else if (cmnd.getName().equalsIgnoreCase("retrieve") && strings.length >= 0) {
                        Inventory i = Bukkit.createInventory(null, 56, "Requisition #" + RequisitionManager.Database.getRequisitionID(cs.getName()));
                        String[] vitems = RequisitionManager.File.getRequisition(cs.getName());
                        ItemStack[] items = Converter.convert(vitems);
                        if (items.length > 56) {
                            ArrayList<ItemStack> enough = new ArrayList<ItemStack>(Arrays.asList(items));
                            i.addItem(enough.subList(0, 55).toArray(new ItemStack[]{}));
                            int k = 0;
                            while (k < 56) {
                                enough.remove(k);
                                k++;
                            }
                        } else {
                            i.addItem(items);
                        }
                        playerMap.put(cs.getName(), i);
                        ((Player) cs).openInventory(i);
                        return true;
                    }
                }
            } else {
                cs.sendMessage(ChatColor.translateAlternateColorCodes('&', cmnd.getPermissionMessage()));
                return false;
            }
        } else {
            cs.sendMessage("[ItemQuery]: I need a player name. Will be implementing admin functions soon");
            return false;
        }
        return false;
    }

}
