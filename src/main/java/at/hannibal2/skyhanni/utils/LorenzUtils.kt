package at.hannibal2.skyhanni.utils

import at.hannibal2.skyhanni.SkyHanniMod
import at.hannibal2.skyhanni.data.HyPixelData
import at.hannibal2.skyhanni.data.IslandType
import at.hannibal2.skyhanni.features.dungeon.DungeonData
import at.hannibal2.skyhanni.utils.StringUtils.removeColor
import net.minecraft.client.Minecraft
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.util.ChatComponentText
import org.intellij.lang.annotations.Language
import java.text.DecimalFormat
import java.text.SimpleDateFormat

object LorenzUtils {

    val isHyPixel: Boolean
        get() = HyPixelData.hypixel && Minecraft.getMinecraft().thePlayer != null

    val inSkyBlock: Boolean
        get() = isHyPixel && HyPixelData.skyBlock

    val inDungeons: Boolean
        get() = inSkyBlock && DungeonData.inDungeon()

    val skyBlockIsland: IslandType
        get() = HyPixelData.skyBlockIsland

    //TODO add cache
    val skyBlockArea: String
        get() = HyPixelData.readSkyBlockArea()

    val inKuudraFight: Boolean
        get() = skyBlockIsland == IslandType.KUUDRA_ARENA

    val noTradeMode: Boolean
        get() = HyPixelData.noTrade

    val isBingoProfile: Boolean
        get() = inSkyBlock && HyPixelData.bingo

    const val DEBUG_PREFIX = "[SkyHanni Debug] §7"
    private val log = LorenzLogger("chat/mod_sent")

    fun debug(message: String) {
        if (SkyHanniMod.feature.dev.debugEnabled) {
            if (internalChat(DEBUG_PREFIX + message)) {
                consoleLog("[Debug] $message")
            }
        } else {
            consoleLog("[Debug] $message")
        }
    }

    fun warning(message: String) {
        internalChat("§cWarning! $message")
    }

    fun error(message: String) {
        internalChat("§4$message")
    }

    fun chat(message: String) {
        internalChat(message)
    }

    private fun internalChat(message: String): Boolean {
        log.log(message)
        val minecraft = Minecraft.getMinecraft()
        if (minecraft == null) {
            consoleLog(message.removeColor())
            return false
        }

        val thePlayer = minecraft.thePlayer
        if (thePlayer == null) {
            consoleLog(message.removeColor())
            return false
        }

        thePlayer.addChatMessage(ChatComponentText(message))
        return true
    }

    //TODO move into StringUtils
    fun String.matchRegex(@Language("RegExp") regex: String): Boolean = regex.toRegex().matches(this)


    fun SimpleDateFormat.formatCurrentTime(): String = this.format(System.currentTimeMillis())

    fun stripVanillaMessage(originalMessage: String): String {
        var message = originalMessage

        while (message.startsWith("§r")) {
            message = message.substring(2)
        }
        while (message.endsWith("§r")) {
            message = message.substring(0, message.length - 2)
        }
        return message
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }

    fun String.between(start: String, end: String): String = this.split(start, end)[1]

    val EntityLivingBase.baseMaxHealth: Double
        get() = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).baseValue

    fun formatPercentage(percentage: Double): String = formatPercentage(percentage, "0.00")

    fun formatPercentage(percentage: Double, format: String?): String =
        DecimalFormat(format).format(percentage * 100).replace(',', '.') + "%"

    fun formatInteger(i: Int): String = DecimalFormat("#,##0").format(i.toLong()).replace(',', '.')

    fun formatDouble(d: Double, format: String?): String =
        DecimalFormat(format).format(d).replace(',', 'x').replace('.', ',').replace('x', '.')

    fun formatDouble(d: Double): String = formatDouble(d, "#,##0.0")

    fun consoleLog(text: String) {
        SkyHanniMod.consoleLog(text)
    }

    // Taken and modified from https://stackoverflow.com/a/11306854/5507634
    fun getCallerClass(vararg skip: String): String? {
        val stElements = Thread.currentThread().stackTrace
        for (i in 1 until stElements.size) {
            val ste = stElements[i]
            val className = ste.className
            if (className != LorenzUtils::class.java.name && className.indexOf("java.lang.Thread") != 0) {
                if (className !in skip) {
                    return className
                }
            }
        }
        return null
    }
}