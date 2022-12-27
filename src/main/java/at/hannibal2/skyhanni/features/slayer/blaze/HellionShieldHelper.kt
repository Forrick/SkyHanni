package at.hannibal2.skyhanni.features.slayer.blaze

import at.hannibal2.skyhanni.SkyHanniMod
import at.hannibal2.skyhanni.utils.LorenzUtils
import io.github.moulberry.notenoughupdates.events.RenderMobColoredEvent
import io.github.moulberry.notenoughupdates.events.ResetEntityHurtEvent
import io.github.moulberry.notenoughupdates.events.withAlpha
import net.minecraft.entity.EntityLiving
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class HellionShieldHelper {

    companion object {
        val hellionShieldMobs = mutableMapOf<EntityLiving, HellionShield>()
    }

    @SubscribeEvent
    fun onRenderMobColored(event: RenderMobColoredEvent) {
        if (!LorenzUtils.inSkyblock) return
        if (!SkyHanniMod.feature.slayer.blazeColoredMobs) return

        val shield = hellionShieldMobs.getOrDefault(event.entity, null) ?: return
        event.color = shield.color.toColor().withAlpha(80)
    }

    @SubscribeEvent
    fun onResetEntityHurtTime(event: ResetEntityHurtEvent) {
        if (!LorenzUtils.inSkyblock) return
        if (!SkyHanniMod.feature.slayer.blazeColoredMobs) return

        hellionShieldMobs.getOrDefault(event.entity, null) ?: return
        event.shouldReset = true
    }
}

fun EntityLiving.setHellionShield(shield: HellionShield?) {
    if (shield != null) {
        HellionShieldHelper.hellionShieldMobs[this] = shield
    } else {
        HellionShieldHelper.hellionShieldMobs.remove(this)
    }
}