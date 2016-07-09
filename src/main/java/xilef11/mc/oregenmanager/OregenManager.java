package xilef11.mc.oregenmanager;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import xilef11.mc.oregenmanager.utils.ModLogger;

@Mod(modid = Refs.MODID, name=Refs.NAME, version = Refs.VERSION,acceptableRemoteVersions="*",guiFactory=Refs.GUI_FACTORY_CLASS)
public class OregenManager
{
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		//register config
		Config.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new Config());
	}
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	if(Config.dumpOres){
    		StringBuilder message = new StringBuilder("Printing registered ores:");
    		for(String name:OreDictionary.getOreNames()){
    			if(name.startsWith("ore")){
    				message.append("\n").append(name);
    				ResourceLocation registryName;
    				for(ItemStack s:OreDictionary.getOres(name, false)){
    					message.append("\n\t");
    					registryName = s.getItem().getRegistryName();
    					message.append(registryName!=null?registryName.toString():"null");
    					message.append("@").append(s.getMetadata());
    				}
    			}
    		}
    		ModLogger.logInfo(message.toString());
    	}
    }
}
