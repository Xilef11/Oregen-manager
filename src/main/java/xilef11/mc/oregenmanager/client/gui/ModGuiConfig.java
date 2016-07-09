/**
 *  the Real-Time Clock mod adds the system time to the Minecraft HUD.
 *  Copyright (C) 2015  Xilef11
 *  Licensed under the GNU General Public License version 3
 *
 *  File created by Xilef11 on 2015-04-17
 */
package xilef11.mc.oregenmanager.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import xilef11.mc.oregenmanager.Config;
import xilef11.mc.oregenmanager.Refs;

/**
 * @author Xilef11
 *
 */
public class ModGuiConfig extends GuiConfig {

	public ModGuiConfig(GuiScreen screen) {
		super(screen, new ConfigElement(
				Config.config
				.getCategory(Configuration.CATEGORY_GENERAL))
		.getChildElements(), Refs.MODID, false, false,
		GuiConfig.getAbridgedConfigPath(Config.config.toString()));

	}

}
