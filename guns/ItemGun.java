package scal.guns;

import org.lwjgl.input.Mouse;

import scal.common.SCal;
import scal.common.VariableHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemGun extends Item
{
	public GunType Type;
	
	public ItemGun(int par1, GunType type) 
	{
		super(par1);
		
		this.Type = type;
		this.setCreativeTab(SCal.gunTab);
		this.setFull3D();
		this.setMaxDamage(type.MaxCapacity);
		this.setMaxStackSize(1);
		this.setTextureName(type.TexturePath);
		this.setUnlocalizedName(type.ShortName);
	}
	
	@Override
	public boolean getShareTag()
	{
		return true;
	}
	
	public ItemStack getBulletStack(ItemStack gunStack, int id)
	{
		if(!gunStack.hasTagCompound())
		{
			gunStack.stackTagCompound = new NBTTagCompound("Tag");
			return null;
		}
		
		if(!gunStack.stackTagCompound.hasKey("AmmoStack"))
		{
			gunStack.stackTagCompound.setCompoundTag("AmmoStack", new NBTTagCompound());
			
			return null;
		}
		
		NBTTagCompound ammoTag = gunStack.stackTagCompound.getCompoundTag("AmmoStack");
		
		if(!ammoTag.hasKey("ItemID"))
		{
			return null;
		}
		
		return new ItemStack(ammoTag.getShort("ItemID"), ammoTag.getShort("NumItems"), ammoTag.getShort("Damage"));
	}
	
	public void setBulletStack(ItemStack gunStack, ItemStack bulletStack, int id)
	{
		if(!gunStack.hasTagCompound())
		{
			gunStack.stackTagCompound = new NBTTagCompound("Tag");
		}
		
		if(!gunStack.stackTagCompound.hasKey("AmmoStack"))
		{
			NBTTagCompound ammoTag = new NBTTagCompound();
			
			gunStack.stackTagCompound.setCompoundTag("AmmoStack", ammoTag);
		}
		
		NBTTagCompound ammoTag = gunStack.stackTagCompound.getCompoundTag("Tag");
		
		if(bulletStack == null)
		{
			ammoTag.removeTag("ItemID");
			ammoTag.removeTag("NumItems");
			ammoTag.removeTag("Damage");
		}
		
		ammoTag.setShort("ItemID", (short)bulletStack.itemID);
		ammoTag.setShort("NumItems", (short)bulletStack.stackSize);
		ammoTag.setShort("Damage", (short)bulletStack.getItemDamage());
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int inventoryIndex, boolean flag)
	{
		if(world.isRemote && !Mouse.isButtonDown(1))
		{
			VariableHandler.HasShot = false;
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if(world.isRemote && VariableHandler.ShootInterval <= 0 && VariableHandler.ReloadInterval <= 0 && !VariableHandler.HasShot)
		{
			
		}
		
		return itemStack;
	}
}
