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
import net.minecraft.entity.EntityLivingBase;
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
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int inventoryIndex, boolean flag)
	{
		if (world.isRemote)
		{
			if(!Mouse.isButtonDown(1))
			{
				VariableHandler.IsShooting = false;
			}
			else
			{
				if(entity instanceof EntityPlayer)
				{
					if(this.Type.FType == GunType.FireType.FullAuto)
					{
						this.attemptShot(itemStack, world, (EntityPlayer)entity);
					}
					if(this.Type.FType == GunType.FireType.ThreeRound)
					{
						if(VariableHandler.ThreeRoundIterator < 3)
						{
							this.attemptShot(itemStack, world, (EntityPlayer)entity);
						}
					}
				}
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(!VariableHandler.IsShooting || this.Type.FType == GunType.FireType.FullAuto || this.Type.FType == GunType.FireType.ThreeRound)
		{
			if(this.Type.FType == GunType.FireType.ThreeRound)
			{
				if(VariableHandler.ThreeRoundIterator < 3)
				{
					this.attemptShot(itemStack, world, entityPlayer);
				}
			}
			else
			{
				this.attemptShot(itemStack, world, entityPlayer);
			}
		}
		
		return itemStack;
	}
	
	private boolean attemptShot(ItemStack itemStack, World world, EntityPlayer player)
	{
		if(world.isRemote && VariableHandler.ShootInterval <= 0 && VariableHandler.ReloadInterval <= 0)
		{
			if(this.Type.FType == GunType.FireType.ThreeRound && VariableHandler.ThreeRoundTimer > 0)
			{
				return false;
			}
			
			if(itemStack.getItemDamage() < itemStack.getMaxDamage())
			{
				ByteArrayDataOutput data = ByteStreams.newDataOutput();
				data.writeByte(1);
				PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("SCal", data.toByteArray()));
				VariableHandler.ShootInterval = this.Type.ShotInterval;
				VariableHandler.IsShooting = true;
				
				if(this.Type.FType == GunType.FireType.ThreeRound)
				{
					VariableHandler.ThreeRoundIterator++;
					if(VariableHandler.ThreeRoundIterator >= 3)
					{
						VariableHandler.ThreeRoundTimer = this.Type.ThreeRoundInterval;
						VariableHandler.IsShooting = false;
					}
				}
				
				if(!player.capabilities.isCreativeMode)
				{
					itemStack.damageItem(1, player);
				}
				
				return true;
			}
			else
			{
				if(this.reload(itemStack, world, player))
				{
					VariableHandler.ReloadInterval = this.Type.ReloadTime;
				}
			}
		}
		
		return false;
	}
	
	public boolean reload(ItemStack stack, World world, Entity entity)
	{
		boolean didReload = false;
		
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			
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
				
				if(stack.getItemDamage() < stack.getItem().getMaxDamage())
				{
					int newSize = 0;
					
					if(newStack.getMaxDamage() - newStack.getItemDamage() <= stack.getMaxDamage() - stack.getItemDamage())
					{
						newSize = newStack.getMaxDamage() - newStack.getItemDamage();
					}
					else
					{
						newSize = (newStack.getMaxDamage() - newStack.getItemDamage()) - stack.getItemDamage();
					}

					newStack.damageItem(newSize, player);
					stack.setItemDamage(stack.getItemDamage() - newSize);
				}
				else
				{
					stack.setItemDamage(newStack.getMaxDamage() - newStack.getItemDamage());
					newStack.damageItem(newStack.getMaxDamage() - newStack.getItemDamage(), player);
				}
				
				didReload = true;
			}
		}
		
		return didReload;
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
			lines.add("Stats");
			lines.add("Damage: " + Integer.toString(this.Type.Damage));
			lines.add("Bullets Per Shot: " + Integer.toString(this.Type.NumBullets));
			lines.add("Accuracy Hip: " + Float.toString(this.Type.AccuracyHip));
			lines.add("Scoped Accuracy: " + Float.toString(this.Type.AccuracyScope));
			lines.add("Recoil: " + Float.toString(this.Type.Recoil));
			lines.add("Max Capacity: " + Integer.toString(this.Type.MaxCapacity));
			lines.add("Reload Time: " + Integer.toString(this.Type.ReloadTime));
			lines.add("Rate Of Fire: " + Float.toString((20f / (float)this.Type.ShotInterval) * 60) + " Rounds Per Minute");
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

	@Override
	public boolean onBlockStartBreak(ItemStack itemStack, int x, int y, int z, EntityPlayer entityplayer)
	{
		if (VariableHandler.ZoomLevel > 1.0)
		{
			return true;
		}
		return false;
	}
	
	@Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        return true;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon("scal" + this.Type.TexturePath);
	}
}
