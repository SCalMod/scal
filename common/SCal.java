package scal.common;

import org.lwjgl.input.Keyboard;

import scal.client.KeybindClass;
import scal.client.TickHandler;
import scal.guns.BulletType;
import scal.guns.EntityBullet;
import scal.guns.GunType;
import scal.guns.ItemBullet;
import scal.guns.ItemGun;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "SCal Guns", name = "SCal Guns Mod", version = "0.1.0")
@NetworkMod(channels = {"SCal"}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class SCal 
{
	@Instance(value = "SCal Guns")
	public static SCal Instance;
	
	@SidedProxy(clientSide = "scal.client.ClientProxy", serverSide = "scal.common.CommonProxy")
	public static CommonProxy Proxy;
	
	private Configuration _config;
	
	public static CreativeTabs gunTab = new CreativeTabs("gunTab");
	
	//Pistols
	
	public static ItemGun m9 = new ItemGun(GunType.PistolM9);
	public static ItemBullet bulletm9 = new ItemBullet(BulletType.BulletM9);
	public static ItemGun m1911 = new ItemGun(GunType.PistolM1911);
	public static ItemBullet bulletm1911 = new ItemBullet(BulletType.BulletM1911);
	
	//Snipers
	
	public static ItemGun l96 = new ItemGun(GunType.SniperL96);
	public static ItemBullet bulletl96 = new ItemBullet(BulletType.BulletL96);
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		this._config = new Configuration(event.getSuggestedConfigurationFile());
		this._config.load();
		VariableHandler.ItemID = this._config.get("General", "ItemIDs", 15000).getInt(15000);
		VariableHandler.BreaksGlass = this._config.get("General", "BreaksGlass", false).getBoolean(false);
		this._config.save();
		
		event.getModMetadata().autogenerated = false;
		event.getModMetadata().url = "http://scalmod.wikia.com/wiki/SCalMod_Wiki";
		event.getModMetadata().credits = "YoSoCal";
		event.getModMetadata().description = "Have fun!";
		
		this.Proxy.registerSounds();
		TickRegistry.registerScheduledTickHandler(new TickHandler(), Side.CLIENT);
	}
	
	@EventHandler
	public void Load(FMLInitializationEvent event)
	{
		KeyBinding[] key = {
				new KeyBinding("BUTTON_Z", Keyboard.KEY_Z),
				new KeyBinding("BUTTON_X", Keyboard.KEY_X),
				new KeyBinding("BUTTON_C", Keyboard.KEY_C),
				new KeyBinding("BUTTON_G", Keyboard.KEY_G)
				};
		boolean[] repeat = {
				false,
				false,
				false,
				false
				};
		KeyBindingRegistry.registerKeyBinding(new KeybindClass(key, repeat));
		EntityRegistry.registerModEntity(EntityBullet.class, "Bullet", 1, this, 40, 80, true);
		LanguageRegistry.instance().addStringLocalization("entity.SCal Guns.EntityBullet.name", "en_US", "Bullet");
		this.Proxy.RegisterRenders();
		
		//Pistols
		
		LanguageRegistry.addName(m9, "M9");
		LanguageRegistry.addName(bulletm9, "M9 Ammo");
		LanguageRegistry.addName(m1911, "M1911");
		LanguageRegistry.addName(bulletm1911, "M1911 Ammo");
		
		//Snipers
		
		LanguageRegistry.addName(l96, "L96");
		LanguageRegistry.addName(bulletl96, "L96 Ammo");
		
		LanguageRegistry.instance().addStringLocalization("itemGroup.gunTab", "en_US", "SCal Mod");
	}
}
