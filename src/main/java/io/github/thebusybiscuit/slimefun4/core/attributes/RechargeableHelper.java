package io.github.thebusybiscuit.slimefun4.core.attributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.thebusybiscuit.cscorelib2.chat.ChatColors;
import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.utils.PatternUtils;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;

/**
 * This is just a simple helper class to provide static methods to the {@link Rechargeable}
 * interface.
 * 
 * @author TheBusyBiscuit
 * 
 * @see Rechargeable
 *
 */
final class RechargeableHelper {

    private static final NamespacedKey CHARGE_KEY = new NamespacedKey(SlimefunPlugin.instance, "item_charge");
    private static final String LORE_PREFIX = ChatColors.color("&c&o&8\u21E8 &e\u26A1 &7");

    private RechargeableHelper() {}

    static void setCharge(ItemMeta meta, float charge, float capacity) {
        BigDecimal decimal = BigDecimal.valueOf(charge).setScale(2, RoundingMode.HALF_UP);
        float value = decimal.floatValue();

        if (SlimefunPlugin.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_14)) {
            meta.getPersistentDataContainer().set(CHARGE_KEY, PersistentDataType.FLOAT, value);
        }

        List<String> lore = meta.getLore();
        for (int i = 0; i < lore.size(); i++) {
            String line = lore.get(i);

            if (line.startsWith(LORE_PREFIX)) {
                lore.set(i, LORE_PREFIX + value + " / " + capacity + " J");
                meta.setLore(lore);
                return;
            }
        }
    }

    static float getCharge(ItemMeta meta) {
        if (SlimefunPlugin.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_14)) {
            Float value = meta.getPersistentDataContainer().get(CHARGE_KEY, PersistentDataType.FLOAT);

            // If no persistent data exists, we will just fall back to the lore
            if (value != null) {
                return value;
            }
        }

        if (meta.hasLore()) {
            for (String line : meta.getLore()) {
                if (line.startsWith(LORE_PREFIX) && line.contains(" / ") && line.endsWith(" J")) {
                    return Float.parseFloat(PatternUtils.SLASH_SEPARATOR.split(line)[0].replace(LORE_PREFIX, ""));
                }
            }
        }

        return 0;
    }

}
