package xilef11.mc.oregenmanager.managers;

import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {
	/* Options:
	 * OreGenEvent - fired when ore is generating. not cancellable but have to set the result to prevent the ore from generating.
	 * OreRegisterEvent - when an ore is registered with the OreDict (probably not what we want)
	 */
	//this is the one responsible for actually generating the ore
	@SubscribeEvent
	public void onOreGeneration(OreGenEvent.GenerateMinable event){
		WorldGenerator generator = event.getGenerator();
		//generator.generate(worldIn, rand, position) position is CHUNK coords
		//there does not seem to be a way to determine what is being generated on a block level
		//we may need to do really hacky stuff...
	}
}
