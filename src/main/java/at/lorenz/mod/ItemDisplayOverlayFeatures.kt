package at.lorenz.mod

import at.lorenz.mod.config.LorenzConfig
import at.lorenz.mod.events.GuiRenderItemEvent
import at.lorenz.mod.utils.ItemUtils
import at.lorenz.mod.utils.ItemUtils.Companion.cleanName
import at.lorenz.mod.utils.LorenzUtils
import at.lorenz.mod.utils.LorenzUtils.Companion.between
import at.lorenz.mod.utils.LorenzUtils.Companion.matchRegex
import at.lorenz.mod.utils.NumberUtil.romanToDecimal
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ItemDisplayOverlayFeatures {

    @SubscribeEvent
    fun onRenderItemOverlayPost(event: GuiRenderItemEvent.RenderOverlayEvent.Post) {
        val item = event.stack ?: return

        //TODO add
//        if (!Utils.inSkyblock || item.stackSize != 1 || item.tagCompound?.hasKey("SkytilsNoItemOverlay") == true) return
        if (item.stackSize != 1 || item.tagCompound?.hasKey("SkytilsNoItemOverlay") == true) return

        val stackTip = getStackTip(item)

        if (stackTip.isNotEmpty()) {
            GlStateManager.disableLighting()
            GlStateManager.disableDepth()
            GlStateManager.disableBlend()
            event.fr.drawStringWithShadow(
                stackTip,
                (event.x + 17 - event.fr.getStringWidth(stackTip)).toFloat(),
                (event.y + 9).toFloat(),
                16777215
            )
            GlStateManager.enableLighting()
            GlStateManager.enableDepth()
        }

    }

    private fun getStackTip(item: ItemStack): String {
        val name = item.cleanName()

        if (LorenzConfig.lorenzItemDisplayMasterStarSkullNumber) {
            when (name) {
                "First Master Star" -> return "1"
                "Second Master Star" -> return "2"
                "Third Master Star" -> return "3"
                "Fourth Master Star" -> return "4"
                "Fifth Master Star" -> return "5"
            }

            if (name.matchRegex("(.*)Master Skull - Tier .")) {
                return name.substring(name.length - 1)
            }

        }

        if (LorenzConfig.lorenzItemDisplayDungeonHeadNumber) {
            if (name.contains("Golden ") || name.contains("Diamond ")) {
                when {
                    name.contains("Bonzo") -> return "1"
                    name.contains("Scarf") -> return "2"
                    name.contains("Professor") -> return "3"
                    name.contains("Thorn") -> return "4"
                    name.contains("Livid") -> return "5"
                    name.contains("Sadan") -> return "6"
                    name.contains("Necron") -> return "7"
                }
            }
        }

        if (LorenzConfig.lorenzItemDisplayNewYearCakeNumber) {
            if (name.startsWith("New Year Cake")) {
                return "§b" + name.split("(Year ", ")")[1]
            }
        }

        if (LorenzConfig.lorenzItemDisplayPetLevel) {
            if (ItemUtils.isPet(name)) {
                try {
                    val level = name.between("Lvl ", "] ").toInt()
                    if (level != ItemUtils.maxPetLevel(name)) {
                        return "$level"
                    }
                } catch (e: java.lang.NumberFormatException) {
                    e.printStackTrace()
                    LorenzDebug.log("name: '$name'")
                    LorenzUtils.warning("NumberFormatException at lorenzItemDisplayPetLevel")
                }
            }
        }

        if (LorenzConfig.lorenzItemSackNameDisplay) {
            if (ItemUtils.isSack(name)) {
                val split = name.split(" ")
                val sackName = split[split.size - 2]
                return (if (name.contains("Enchanted")) "§5" else "") + sackName.substring(0, 2)
            }
        }

        if (LorenzConfig.lorenzItemDisplayMinionTier) {
            if (name.contains(" Minion ")) {
                val array = name.split(" ")
                val last = array[array.size - 1]
                return last.romanToDecimal().toString()
            }
        }

        return ""
    }
}