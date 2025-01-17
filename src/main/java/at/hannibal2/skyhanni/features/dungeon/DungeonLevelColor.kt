package at.hannibal2.skyhanni.features.dungeon

import at.hannibal2.skyhanni.SkyHanniMod
import at.hannibal2.skyhanni.utils.InventoryUtils
import at.hannibal2.skyhanni.utils.ItemUtils.getLore
import at.hannibal2.skyhanni.utils.LorenzUtils
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.regex.Pattern

class DungeonLevelColor {

    private val pattern = Pattern.compile(" §.(.*)§f: §e(.*)§b \\(§e(.*)§b\\)")

    @SubscribeEvent
    fun onItemTooltip(event: ItemTooltipEvent) {
        if (!LorenzUtils.inSkyBlock) return
        if (!SkyHanniMod.feature.dungeon.partyFinderColoredClassLevel) return

        if (event.toolTip == null) return
        val chestName = InventoryUtils.openInventoryName()
        if (chestName != "Party Finder") return

        val stack = event.itemStack
        var index = 0
        for (line in stack.getLore()) {
            index++
            val matcher = pattern.matcher(line)
            if (!matcher.matches()) continue

            val playerName = matcher.group(1)
            val className = matcher.group(2)
            val level = matcher.group(3).toInt()
            val color = getColor(level)
            event.toolTip[index] = " §b$playerName§f: §e$className $color$level"
        }
    }

    private fun getColor(level: Int): String {
        if (level >= 50) return "§c§l"
        if (level >= 45) return "§c"
        if (level >= 40) return "§d"
        if (level >= 35) return "§6"
        if (level >= 30) return "§5"
        if (level >= 25) return "§9"
        if (level >= 20) return "§a"
        if (level >= 10) return "§f"
        return "§7"
    }
}
