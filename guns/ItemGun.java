package scal.guns;

import java.util.List;

import org.lwjgl.input.Keyboard;
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
	
	public ItemGun(GunType type) 
	{
		super(type.ItemID);
		
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
	public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean advancedToolTips)
	{
		switch(this.Type.WType)
		{
			case Pistol:
				lines.add("Pistol");
				break;
				
			case SMG:
				lines.add("Submachine Gun");
				break;
				
			case Shotgun:
				lines.add("Shotgun");
				break;
				
			case AssaultRifle:
				lines.add("Assault Rifle");
				break;
				
			case LMG:
				lines.add("Light Machine Gun");
				break;
				
			case Sniper:
				lines.add("Sniper");
				break;
				
			case Launcher:
				lines.add("Launcher");
				break;
				
			case Other:
				lines.add("Other");
				break;
		}
		
		switch(this.Type.FType)
		{
			case SingleShot:
				lines.add("Single Shot");
				break;
				
			case BoltAction:
				lines.add("Bolt Action");
				break;
				
			case SemiAuto:
				lines.add("Semi Automatic");
				break;
				
			case ThreeRound:
				lines.add("Three Round Burst");
				break;
				
			case FullAuto:
				lines.add("Automatic");
				break;
		}
		
		lines.add("");
		lines.add("1 - Stats");
		lines.add("2 - Attatchments");
		lines.add("3 - Available Attachments");
		
		if(Keyboard.isKeyDown(49))
		{
			lines.clear();
			lines.add("Stats - Test");
		}
		
		if(Keyboard.isKeyDown(50))
		{
			lines.clear();
			lines.add("Attachments - Test");
		}
		
		if(Keyboard.isKeyDown(51))
		{
			lines.clear();
			lines.add("Available Attachments - Test");
		}
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
