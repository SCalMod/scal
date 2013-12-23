package scal.guns;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import scal.client.KeybindClass;
import scal.common.SCal;
import scal.common.VariableHandler;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
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
		this.setUnlocalizedName(type.ShortName);
	}
	
	@Override
	public boolean getShareTag()
	{
		return true;
	}
	
	public ItemStack getBulletStack(ItemStack gunStack)
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
	
	public void setBulletStack(ItemStack gunStack, ItemStack bulletStack)
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
		lines.add("Z - Stats");
		lines.add("X - Attatchments");
		lines.add("C - Available Attachments");
		
		if(VariableHandler.KeyZ)
		{
			lines.clear();
			lines.add("Stats - Test");
		}
		
		if(VariableHandler.KeyX)
		{
			lines.clear();
			lines.add("Attachments - Test");
		}
		
		if(VariableHandler.KeyC)
		{
			lines.clear();
			lines.add("Available Attachments - Test");
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void onUpdateClient(ItemStack stack, World world, Entity entity, int i, boolean flag)
	{
		if(entity instanceof EntityPlayer && ((EntityPlayer)entity).inventory.getCurrentItem() == stack)
		{
			VariableHandler.LastMouse = VariableHandler.MouseHeld;
			VariableHandler.MouseHeld = Mouse.isButtonDown(1);
		}
		
		if(VariableHandler.MouseHeld && !VariableHandler.LastMouse)
		{
			ByteArrayDataOutput data = ByteStreams.newDataOutput();
			data.writeByte(1);
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("SCal", data.toByteArray()));
			this.clientSideShoot((EntityPlayer)entity, stack);
		}
		
		if(this.Type.FType == GunType.FireType.FullAuto && VariableHandler.MouseHeld)
		{
			this.clientSideShoot((EntityPlayer)entity, stack);
		}
		
		if(this.Type.FType == GunType.FireType.ThreeRound && VariableHandler.MouseHeld)
		{
			if(VariableHandler.ThreeRoundIterator < 3 && VariableHandler.ThreeRoundTimer <= 0)
			{
				this.clientSideShoot((EntityPlayer)entity, stack);
				
				if(VariableHandler.ThreeRoundIterator >= 3)
				{
					VariableHandler.ThreeRoundTimer = Type.ThreeRoundInterval;
					VariableHandler.ThreeRoundIterator = 0;
				}
			}
		}
	}
	
	private void clientSideShoot(EntityPlayer entity, ItemStack stack) 
	{
		if(VariableHandler.ShootInterval <= 0)
		{
			boolean hasAmmo = false;
			
			ItemStack bulletStack = this.getBulletStack(stack);
			
			if(bulletStack != null && bulletStack.getItem() != null && bulletStack.getItemDamage() < bulletStack.getMaxDamage())
			{
				hasAmmo = true;
			}
			
			if(hasAmmo)
			{
				VariableHandler.RecoilLevel += this.Type.Recoil;
				VariableHandler.ShootInterval = this.Type.ShotInterval;
			}
		}
	}

	public boolean reload(ItemStack stack, World world, Entity entity)
	{
		boolean didReload = false;
		
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			
			ItemStack bulletStack = this.getBulletStack(stack);
			
			int magazineSlot = -1;
			int bulletsInSlot = 0;
			
			for(int i = 0; i < player.inventory.getSizeInventory(); i++)
			{
				ItemStack item = player.inventory.getStackInSlot(i);
				
				if(item != null && item.getItem() instanceof ItemBullet && this.Type.isAmmo((ItemBullet)(item.getItem())))
				{
					int bulletsHere = item.getMaxDamage() - item.getItemDamage();
					
					if(bulletsHere > bulletsInSlot)
					{
						magazineSlot = i;
						bulletsInSlot = bulletsHere;
					}
				}
			}
			
			if(magazineSlot != -1)
			{
				ItemStack newStack = player.inventory.getStackInSlot(magazineSlot);
				BulletType bType = ((ItemBullet)newStack.getItem()).Type;
				
				if(bulletStack != null)
				{
					if(bulletStack.getItemDamage() < bulletStack.getMaxDamage())
					{
						int newSize = 0;
						
						if(newStack.getMaxDamage() - newStack.getItemDamage() <= bulletStack.getMaxDamage() - bulletStack.getItemDamage())
						{
							newSize = newStack.getMaxDamage() - newStack.getItemDamage();
						}
						else
						{
							newSize = (newStack.getMaxDamage() - newStack.getItemDamage()) - bulletStack.getItemDamage();
						}

						newStack.damageItem(newSize, player);
						bulletStack.setItemDamage(bulletStack.getItemDamage() - newSize);
						
						this.setBulletStack(stack, bulletStack);
					}
					else
					{
						this.setBulletStack(stack, newStack);
					}
					
					didReload = true;
				}
			}
		}
		
		return didReload;
	}
	
	private void shoot(ItemStack stack, World world, EntityPlayer player)
	{
		//Play sound
		
		if(!world.isRemote)
		{
			for(int i = 0; i < this.Type.NumBullets; i++)
			{
				world.spawnEntityInWorld(new EntitySnowball(world, player));
			}
		}
		
		VariableHandler.ShootInterval = this.Type.ShotInterval;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon("scal" + this.Type.TexturePath);
	}
}
