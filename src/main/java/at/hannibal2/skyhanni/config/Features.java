package at.hannibal2.skyhanni.config;

import at.hannibal2.skyhanni.SkyHanniMod;
import at.hannibal2.skyhanni.config.core.GuiElement;
import at.hannibal2.skyhanni.config.core.GuiScreenElementWrapper;
import at.hannibal2.skyhanni.config.core.config.Config;
import at.hannibal2.skyhanni.config.core.config.Position;
import at.hannibal2.skyhanni.config.core.config.annotations.Category;
import at.hannibal2.skyhanni.config.core.config.gui.GuiPositionEditor;
import at.hannibal2.skyhanni.config.features.*;
import at.hannibal2.skyhanni.features.misc.HideArmor;
import at.hannibal2.skyhanni.features.misc.MarkedPlayerManager;
import com.google.gson.annotations.Expose;
import net.minecraft.client.Minecraft;


public class Features extends Config {
    private void editOverlay(String activeConfig, int width, int height, Position position) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiPositionEditor(position, width, height, () -> {
        }, () -> {
        }, () -> SkyHanniMod.screenToOpen = new GuiScreenElementWrapper(new ConfigEditor(SkyHanniMod.feature, activeConfig))));
    }

    @Override
    public void executeRunnable(String runnableId) {
        String activeConfigCategory = null;
        if (Minecraft.getMinecraft().currentScreen instanceof GuiScreenElementWrapper) {
            GuiScreenElementWrapper wrapper = (GuiScreenElementWrapper) Minecraft.getMinecraft().currentScreen;
            GuiElement element = wrapper.element;
            if (element instanceof ConfigEditor) {
                activeConfigCategory = ((ConfigEditor) element).getSelectedCategoryName();
            }
        }

        if (runnableId.equals("petDisplay")) {
            editOverlay(activeConfigCategory, 200, 16, misc.petDisplayPos);
            return;
        }

        if (runnableId.equals("collectionCounter")) {
            editOverlay(activeConfigCategory, 200, 16, misc.collectionCounterPos);
            return;
        }

        if (runnableId.equals("debugPos")) {
            editOverlay(activeConfigCategory, 200, 16, dev.debugPos);
            return;
        }

        if (runnableId.equals("dungeonMilestonesDisplay")) {
            editOverlay(activeConfigCategory, 200, 16, dungeon.showMileStonesDisplayPos);
            return;
        }

        if (runnableId.equals("dungeonDeathCounter")) {
            editOverlay(activeConfigCategory, 200, 16, dungeon.deathCounterPos);
            return;
        }

        if (runnableId.equals("bestSellMethod")) {
            editOverlay(activeConfigCategory, 200, 16, bazaar.bestSellMethodPos);
            return;
        }

        if (runnableId.equals("ashfangFreezeCooldown")) {
            editOverlay(activeConfigCategory, 200, 16, ashfang.freezeCooldownPos);
            return;
        }

        if (runnableId.equals("ashfangResetCooldown")) {
            editOverlay(activeConfigCategory, 200, 16, ashfang.nextResetCooldownPos);
            return;
        }

        if (runnableId.equals("realTime")) {
            editOverlay(activeConfigCategory, 200, 16, misc.realTimePos);
            return;
        }

        if (runnableId.equals("hopperProfitDisplay")) {
            editOverlay(activeConfigCategory, 200, 16, minions.hopperProfitPos);
            return;
        }

        if (runnableId.equals("summoningMobDisplay")) {
            editOverlay(activeConfigCategory, 200, 16, summonings.summoningMobDisplayPos);
            return;
        }

        if (runnableId.equals("dungeonCopilot")) {
            editOverlay(activeConfigCategory, 200, 16, dungeon.copilotPos);
            return;
        }

        if (runnableId.equals("markOwnPlayer")) {
            MarkedPlayerManager.Companion.toggleOwn();
            return;
        }

        if (runnableId.equals("hideArmor")) {
            HideArmor.Companion.updateArmor();
            return;
        }

        if (runnableId.equals("nonGodPotEffect")) {
            editOverlay(activeConfigCategory, 200, 16, misc.nonGodPotEffectPos);
            return;
        }
    }

    @Expose
    @Category(name = "Chat", desc = "Chat related features.")
    public Chat chat = new Chat();

    @Expose
    @Category(name = "Dungeon", desc = "Features that change the catacombs dungeon experience.")
    public Dungeon dungeon = new Dungeon();

    @Expose
    @Category(name = "Inventory", desc = "Changing the behavior around items and the inventory.")
    public Inventory inventory = new Inventory();

    @Expose
    @Category(name = "Item Abilities", desc = "Stuff about item abilities")
    public ItemAbilities itemAbilities = new ItemAbilities();

    @Expose
    @Category(name = "Summonings", desc = "Ashfang fight in Crimson Isle")
    public Summonings summonings = new Summonings();

    @Expose
    @Category(name = "Ashfang", desc = "Ashfang fight in Crimson Isle")
    public Ashfang ashfang = new Ashfang();

    @Expose
    @Category(name = "Minion", desc = "Stuff about minions")
    public Minions minions = new Minions();

    @Expose
    @Category(name = "Bazaar", desc = "Bazaar settings.")
    public Bazaar bazaar = new Bazaar();

    @Expose
    @Category(name = "Fishing", desc = "Fishing stuff.")
    public Fishing fishing = new Fishing();

    @Expose
    @Category(name = "Damage Indicator", desc = "Better damage overview in combat with bosses of all sorts.")
    public DamageIndicator damageIndicator = new DamageIndicator();

    @Expose
    @Category(name = "Slayer", desc = "Slayer Features.")
    public Slayer slayer = new Slayer();

    @Expose
    @Category(name = "Diana", desc = "Diana's mythological event.")
    public Diana diana = new Diana();

    @Expose
    @Category(name = "Commands", desc = "Enable or disable mod commands")
    public CommandsFeatures commands = new CommandsFeatures();

    @Expose
    @Category(name = "Marked Players", desc = "Players that got marked with /shmarkplayer")
    public MarkedPlayers markedPlayers = new MarkedPlayers();

    @Expose
    @Category(name = "Bingo", desc = "Features for the Bingo mode.")
    public Bingo bingo = new Bingo();

    @Expose
    @Category(name = "Misc", desc = "Settings without a category.")
    public Misc misc = new Misc();

    @Expose
    @Category(name = "Dev", desc = "Debug and test stuff.")
    public DevData dev = new DevData();

    @Expose
    public Hidden hidden = new Hidden();
}
