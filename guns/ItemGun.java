package scal.guns;

import net.minecraft.item.Item;

public class ItemGun extends Item
{
	public GunType Type;
	
	public ItemGun(int par1, GunType type) 
	{
		super(par1);
		
		this.Type = type;
		this.setFull3D();
		this.setHasSubtypes(true);
		this.setMaxDamage(type.MaxCapacity);
		this.setMaxStackSize(1);
		this.setTextureName(type.TexturePath);
		this.setUnlocalizedName(type.ShortName);
	}
}
