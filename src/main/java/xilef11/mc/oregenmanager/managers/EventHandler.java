package xilef11.mc.oregenmanager.managers;

import xilef11.mc.oregenmanager.data.OreMapData;
import xilef11.mc.oregenmanager.utils.ModLogger;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

public class EventHandler {
	/* Options:
	 * OreGenEvent - fired when ore is generating. not cancellable but have to set the result to prevent the ore from generating.
	 * OreRegisterEvent - when an ore is registered with the OreDict (probably not what we want)
	 */
	//this is the one responsible for actually generating the ore
	@SubscribeEvent
	public void onOreGeneration(OreGenEvent.GenerateMinable event){
		WorldGenerator generator = event.getGenerator();
		ModLogger.logInfo("OreGenEvent triggered for WorldGenerator: "+generator.getClass().getCanonicalName()+" with type "+event.getType());
		if(event.getType()==EventType.CUSTOM){//not vanilla
			if(generator instanceof WorldGenMinable){//this will probably hit very few mods, even if they fire the event
				WorldGenMinable gm = (WorldGenMinable)generator;
				IBlockState toGen = ReflectionHelper.getPrivateValue(WorldGenMinable.class, gm, "field_175920_a","oreBlock");
				OreMapData map = OreMapData.get(event.getWorld());
				Block block = toGen.getBlock();
				ItemStack stack = new ItemStack(block,1,block.getMetaFromState(toGen));
				boolean accepted=false;
				for(int id:OreDictionary.getOreIDs(stack)){
					String name = OreDictionary.getOreName(id);
					ModLogger.logInfo("Processing OreDict name: "+name);
					if(name.startsWith("ore")){
						if(map.isAccepted(name, toGen)){
							accepted=true;
							break;
						}
					}
				}
				
			}
			//generator.generate(worldIn, rand, position) position is CHUNK coords
			//there does not seem to be a way to determine what is being generated on a block level
			//we may need to do really hacky stuff...
		}
	}
}
