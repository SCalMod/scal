package scal.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import scal.client.EventHook;
import scal.guns.BulletType;
import scal.guns.EntityBullet;
import scal.guns.ItemGun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) 
	{
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		EntityPlayer entityPlayer = (EntityPlayer) player;
		InventoryPlayer inventory = entityPlayer.inventory;
		World world = entityPlayer.worldObj;
		
		try
		{
			int packetType = data.readByte();
			
			if (packetType == 1 && inventory.getCurrentItem() != null && inventory.getCurrentItem().getItem() instanceof ItemGun && !world.isRemote)
			{
				if (inventory.getCurrentItem().getItem() instanceof ItemGun)
				{
					ItemGun gun = (ItemGun)inventory.getCurrentItem().getItem();
					
					//world.playSoundAtEntity(entityPlayer, EventHook.GUN_SHOOT, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F));
					VariableHandler.RecoilLevel += gun.Type.Recoil;
					for (int i = 0; i < gun.Type.NumBullets; i ++)
					{
						world.spawnEntityInWorld(new EntityBullet(world, entityPlayer, BulletType.getType(gun.Type.Bullets[0])));
						VariableHandler.AccuracyAddition = gun.Type.Recoil * 2.5f;
					}
				}
			}
			else if (packetType == 2)
			{
				VariableHandler.HitMarkerTimer = 12;
			}
		}
		catch (IOException exception)
		{
		}
	}
}
