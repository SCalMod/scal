package scal.guns;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import scal.common.SCal;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemBullet extends Item
{
	public BulletType Type;
	
	public ItemBullet(BulletType bType) 
	{
		super(bType.ItemID);
		
		this.Type = bType;
		this.setCreativeTab(SCal.gunTab);
		this.setFull3D();
		this.setMaxDamage(GunType.getType(this.Type.Gun).MaxCapacity);
		this.setMaxStackSize(2);
		this.setUnlocalizedName(bType.TexturePath);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon("scal:" + this.Type.TexturePath);
	}
}
