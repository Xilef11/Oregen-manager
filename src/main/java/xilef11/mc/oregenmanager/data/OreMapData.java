package xilef11.mc.oregenmanager.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapStorage;
import xilef11.mc.oregenmanager.Refs;
import xilef11.mc.oregenmanager.utils.ModLogger;

public class OreMapData extends WorldSavedData {
	private static final String DATA_NAME = Refs.MODID + "_OreMapData";

	public OreMapData(String name) {
		super(name);
	}
	public OreMapData(){
		super(DATA_NAME);
	}
	public static OreMapData get(World world) {
		// The IS_GLOBAL constant is there for clarity, and should be simplified into the right branch.
		MapStorage storage = world.getMapStorage();
		OreMapData instance = (OreMapData) storage.getOrLoadData(OreMapData.class, DATA_NAME);

		if (instance == null) {
			instance = new OreMapData();
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}
	//Key is oreDict id, Val is the accepted blockstate. ex: oreIron->minecraft:iron_ore@0
	private Map<String,String> oremap = new HashMap<String, String>();
	/**
	 * Returns wether the given IBlockState is the correct ore to generate for this world.
	 * @param oreID the OreDictionary name of the ore to generate
	 * @param state the IBlockState that wants to generate
	 * @return	true if <b>state</b> should be generated
	 */
	public boolean isAccepted(String oreID, IBlockState state){
		String stored = oremap.get(oreID);
		String blockStr = getID(state);
		if(stored==null){
			stored=blockStr;
			//TODO this cause a FCFS way of managing the oregen. we may want to change that in the future
			oremap.put(oreID, blockStr);
		}
		boolean accepted = blockStr.equals(stored);
		ModLogger.logInfo(blockStr+(accepted?" ACCEPTED ":" NOT ACCEPTED ")+"for OreID "+oreID);
		return accepted;
		
	}
	private String getID(IBlockState state){
		Block block = state.getBlock();
		ResourceLocation loc = block.getRegistryName();
		int meta = block.getMetaFromState(state);
		return (loc!=null?loc.toString():"null")+"@"+meta;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		oremap.clear();
		NBTTagList keys = (NBTTagList) nbt.getTag(DATA_NAME);
		for(int i=0;i<keys.tagCount();i++){
			NBTTagCompound tag = keys.getCompoundTagAt(i);
			String k = tag.getString("KEY");
			String v = tag.getString("VALUE");
			oremap.put(k, v);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		NBTTagList keys = new NBTTagList();
		for(String s:oremap.keySet()){
			NBTTagCompound pair = new NBTTagCompound();
			pair.setString("KEY", s);
			pair.setString("VAL", oremap.get(s));
			keys.appendTag(pair);
		}
		tag.setTag(DATA_NAME, keys);
		return tag;
	}


}
