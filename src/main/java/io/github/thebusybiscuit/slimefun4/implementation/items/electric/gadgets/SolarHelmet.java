package io.github.thebusybiscuit.slimefun4.implementation.items.electric.gadgets;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.implementation.tasks.ArmorTask;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * The {@link SolarHelmet} can be worn by {@link Player}.
 * As long as that {@link Player} has contact with sunlight, the helmet will charge any
 * {@link Rechargeable} {@link SlimefunItem} that this {@link Player} is currently wearing
 * or holding.
 * 
 * @author TheBusyBiscuit
 * 
 * @see ArmorTask
 * @see Rechargeable
 *
 */
public class SolarHelmet extends SlimefunItem {

    private final ItemSetting<Double> charge = new ItemSetting<>("charge-amount", 0.1);

    public SolarHelmet(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe, null);

        addItemSetting(charge);
    }

    public void chargeItems(Player p) {
        recharge(p.getInventory().getHelmet());
        recharge(p.getInventory().getChestplate());
        recharge(p.getInventory().getLeggings());
        recharge(p.getInventory().getBoots());
        recharge(p.getInventory().getItemInMainHand());
        recharge(p.getInventory().getItemInOffHand());
    }

    private void recharge(ItemStack item) {
        SlimefunItem sfItem = SlimefunItem.getByItem(item);

        if (sfItem instanceof Rechargeable) {
            ((Rechargeable) sfItem).addItemCharge(item, charge.getValue().floatValue());
        }
    }

}
