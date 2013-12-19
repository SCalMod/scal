package scal.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "SCal", name = "SCal", version = "0.1.0")
@NetworkMod(channels = {"SCal"}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class SCal 
{
	@Instance(value = "SCal")
	public static SCal Instance;
	
	@SidedProxy(clientSide = "scal.client.ClientProxy", serverSide = "scal.common.CommonProxy")
	public static CommonProxy Proxy;
}
