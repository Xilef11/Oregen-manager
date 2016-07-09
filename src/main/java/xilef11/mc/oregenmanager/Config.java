/**
 *  the Real-Time Clock mod adds the system time to the Minecraft HUD.
 *  Copyright (C) 2015  Xilef11
 *  Licensed under the GNU General Public License version 3
 *
 *  File created by Xilef11 on 2015-04-16
 */
package xilef11.mc.oregenmanager;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.regex.Pattern;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.Level;

import xilef11.mc.oregenmanager.utils.ModLogger;

/**
 * @author Xilef11
 *
 */
public class Config {

	public static Configuration config;

	public static void init(File configFile){

		if(config==null){
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}

	@SubscribeEvent
	public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event){
		if(event.getModID().equals(Refs.MODID)){
			//resync configs
			loadConfiguration();
		}
	}
	public static boolean dumpOres;
	
	private static void loadConfiguration(){
		//read properties
		dumpOres = config.getBoolean("List ores", CATEGORY_GENERAL, true, "Output all \"oreXX\" oreDictionary entries when starting the game");
		if(config.hasChanged()){
			//ModLogger.logInfo("Config has changed");
			config.save();
		}

	}
}
