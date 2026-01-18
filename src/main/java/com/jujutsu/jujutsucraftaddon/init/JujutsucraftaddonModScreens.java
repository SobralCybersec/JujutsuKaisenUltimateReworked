
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.jujutsu.jujutsucraftaddon.init;

import com.jujutsu.jujutsucraftaddon.client.gui.changer.*;
import com.jujutsu.jujutsucraftaddon.client.gui.misc.BindingVowsGUIScreen;
import com.jujutsu.jujutsucraftaddon.client.gui.misc.KoganeMenuGUIScreen;
import com.jujutsu.jujutsucraftaddon.client.gui.misc.MasteryMenuGUIScreen;
import com.jujutsu.jujutsucraftaddon.client.gui.misc.OptionsMenuGUIScreen;
import com.jujutsu.jujutsucraftaddon.client.gui.quests.*;
import com.jujutsu.jujutsucraftaddon.client.gui.skilltree.StatsPointsTreeGUIScreen;
import com.jujutsu.jujutsucraftaddon.client.gui.skilltree.UpgradeSkillsTreeGUIScreen;
import com.jujutsu.jujutsucraftaddon.client.gui.storage.CursedStorageTojiArmoryGUIScreen;
import com.jujutsu.jujutsucraftaddon.client.gui.storage.YutaRikaStorageGUIScreen;
import com.jujutsu.jujutsucraftaddon.client.gui.storymode.gojo.*;
import com.jujutsu.jujutsucraftaddon.client.gui.storymode.sukuna.*;
import com.jujutsu.jujutsucraftaddon.client.gui.technique.KenjakuYutaMenuBodyChangerGUIScreen;
import com.jujutsu.jujutsucraftaddon.client.gui.technique.YorozuEnchantmentTableGUIScreen;
import com.jujutsu.jujutsucraftaddon.client.gui.training.GojoTechniqueTrainingMenuGUIScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class JujutsucraftaddonModScreens {
    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(JujutsucraftaddonModMenus.RACE_CHANGE.get(), RaceChangerGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.YUTA_STORAGE.get(), YutaRikaStorageGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORAGE_ARMORY.get(), CursedStorageTojiArmoryGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.CLAN_CHANGE.get(), ClanChangerGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.TESTR.get(), BindingVowsGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.WORLD_SKILL.get(), MasteryMenuGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.CT_CHANGER.get(), CTChangerGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.KENJAKU_BODY_SWAP.get(), KenjakuYutaMenuBodyChangerGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.KOGANE_UI.get(), KoganeMenuGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.CURSED_ENERGY_SELECTOR.get(), CursedEnergyImbueGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.PROFESSION_GUI.get(), ProfessionChangerGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.ELEMENTS.get(), ElementChangerGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.SKILL_TREE.get(), UpgradeSkillsTreeGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_1.get(), Quest1GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_2.get(), Quest2GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_3.get(), Quest3GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_4.get(), Quest4GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_5.get(), Quest5GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_6.get(), Quest6GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_7.get(), Quest7GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_8.get(), Quest8GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_9.get(), Quest9GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_10.get(), Quest10GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_11.get(), Quest11GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_12.get(), Quest12GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_13.get(), Quest13GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.QUEST_14.get(), Quest14GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.SKILL_TREE_SP.get(), StatsPointsTreeGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.GOJO_TRAIN.get(), GojoTechniqueTrainingMenuGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_GOJO_1.get(), StoryModeGojo1GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_GOJO_2.get(), StoryModeGojo2GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_GOJO_3.get(), StoryModeGojo3GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_GOJO_4.get(), StoryModeGojo4GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_GOJO_6.get(), StoryModeGojo5GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_SUKUNA_1.get(), StoryModeSukuna1GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_SUKUNA_2.get(), StoryModeSukuna2GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_SUKUNA_3.get(), StoryModeSukuna3GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_SUKUNA_4.get(), StoryModeSukuna4GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_SUKUNA_5.get(), StoryModeSukuna5GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_SUKUNA_6.get(), StoryModeSukuna6GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.STORY_MODE_SUKUNA_7.get(), StoryModeSukuna7GUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.ENCHANT_YOROZU.get(), YorozuEnchantmentTableGUIScreen::new);
            MenuScreens.register(JujutsucraftaddonModMenus.NEW_TAB.get(), OptionsMenuGUIScreen::new);
        });
    }
}
