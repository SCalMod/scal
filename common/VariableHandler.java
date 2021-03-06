package scal.common;

import cpw.mods.fml.client.FMLClientHandler;
import scal.guns.EntityBullet;
import scal.guns.GunType;
import scal.guns.ItemGun;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class VariableHandler
{
	public static int HitMarkerTimer = 0;
	public static int ShootInterval = 0;
	public static int ReloadInterval = 0;
	public static int ThreeRoundIterator = 0;
	public static int ThreeRoundTimer = 0;
	public static float RecoilLevel = 0f;
	public static float AntiRecoil = 0f;
	public static float ZoomLevel = 1.0f;
	public static float ReticleScale = 1.0f;
	public static boolean IsShooting = false;
	public static boolean IsScoped = false;
	public static float AccuracyAddition = 0f;
	
	public static int ItemID = 15000;
	public static boolean BreaksGlass = true;
	
	public static boolean KeyZ = false;
	public static boolean KeyX = false;
	public static boolean KeyC = false;
	public static boolean KeyG = false;
	
	public static DamageSource causeBulletDamage(EntityBullet bulletEntity, Entity damagingEntity)
    {
        return (new EntityDamageSourceIndirect("bullet", bulletEntity, damagingEntity)).setProjectile();
    }
	
	public static float getRealAccuracy()
	{
		Minecraft client = FMLClientHandler.instance().getClient();
		
		if(client.thePlayer != null)
		{
			InventoryPlayer inventory = client.thePlayer.inventory;

			if(inventory.getCurrentItem() != null && inventory.getCurrentItem().getItem() instanceof ItemGun)
			{
				ItemGun gun = (ItemGun)inventory.getCurrentItem().getItem();
				GunType type = gun.Type;
				return VariableHandler.IsScoped ? type.AccuracyScope + VariableHandler.AccuracyAddition : type.AccuracyHip + VariableHandler.AccuracyAddition;
			}
		}
		
		return 0f;
	}
}