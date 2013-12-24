package scal.guns;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import scal.common.VariableHandler;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBullet extends Entity implements IProjectile
{
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	public Entity shootingEntity;
	private int ticksInAir;

	public static BulletType Type;
	public static GunType GunType;

	public EntityBullet(World par1World)
	{
		super(par1World);
		this.renderDistanceWeight = 20.0d;
		this.setSize(0.5F, 0.5F);
	}

	public EntityBullet(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5)
	{
		super(par1World);
		this.renderDistanceWeight = 20.0d;
		this.shootingEntity = par2EntityLivingBase;

		this.posY = par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight() - 0.10000000149011612D;
		double d0 = par3EntityLivingBase.posX - par2EntityLivingBase.posX;
		double d1 = par3EntityLivingBase.boundingBox.minY + (double)(par3EntityLivingBase.height / 3.0F) - this.posY;
		double d2 = par3EntityLivingBase.posZ - par2EntityLivingBase.posZ;
		double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);

		if (d3 >= 1.0E-7D)
		{
			float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			this.setLocationAndAngles(par2EntityLivingBase.posX + d4, this.posY, par2EntityLivingBase.posZ + d5, f2, f3);
			this.yOffset = 0.0F;
			float f4 = (float)d3 * 0.2F;
			this.setThrowableHeading(d0, d1 + (double)f4, d2, par4, par5);
		}
	}

	public EntityBullet(World par1World, EntityLivingBase par2EntityLivingBase, float par3, BulletType type)
	{
		super(par1World);

		this.Type = type;
		this.GunType = GunType.getType(this.Type.Gun);

		this.renderDistanceWeight = 20.0d;
		this.shootingEntity = par2EntityLivingBase;

		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight(), par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * 3.14159f) * MathHelper.cos(this.rotationPitch / 180.0F * 3.14159f));
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * 3.14159f) * MathHelper.cos(this.rotationPitch / 180.0F * 3.14159f));
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * 3.14159f));
		Vector3f movementVector = new Vector3f((float)this.motionX, (float)this.motionY, (float)this.motionZ);
		movementVector.normalise();
		this.motionX = movementVector.x * this.GunType.BulletSpeed;
		this.motionZ = movementVector.z * this.GunType.BulletSpeed;
		this.motionY = movementVector.y * this.GunType.BulletSpeed; 

		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, VariableHandler.IsScoped ? this.GunType.AccuracyScope : this.GunType.AccuracyHip);
	}

	public void setThrowableHeading(double par1, double par3, double par5, float par8)
	{
		this.motionX += ((float)((int)(this.rand.nextGaussian() * 1000000)) / 1000000) * (double)(this.rand.nextBoolean() ? -1 : 1) * (double)par8;
		this.motionY += ((float)((int)(this.rand.nextGaussian() * 1000000)) / 1000000) * (double)(this.rand.nextBoolean() ? -1 : 1) * (double)par8;
		this.motionZ += ((float)((int)(this.rand.nextGaussian() * 1000000)) / 1000000) * (double)(this.rand.nextBoolean() ? -1 : 1) * (double)par8;
		float f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f3) * 180.0D / Math.PI);
	}

	public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
	{

	}

	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		this.setPosition(par1, par3, par5);
		this.setRotation(par7, par8);
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double par1, double par3, double par5)
	{
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		}
	}

	public void onUpdate()
	{
		super.onUpdate();

		if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f)
		{
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
		}

		this.ticksInAir++;
		Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
		Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks_do_do(vec3, vec31, false, true);
		vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
		vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (movingobjectposition != null)
		{
			vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}

		Entity entity = null;
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
		double d0 = 0.0d;
		int l;
		float f1;

		for (l = 0; l < list.size(); ++l)
		{
			Entity entity1 = (Entity)list.get(l);

			if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity))
			{
				f1 = 0.3f;
				AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double)f1, (double)f1, (double)f1);
				MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);

				if (movingobjectposition1 != null)
				{
					double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

					if (d1 < d0 || d0 == 0.0D)
					{
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}

		if (entity != null)
		{
			movingobjectposition = new MovingObjectPosition(entity);
		}

		if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

			if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
			{
				movingobjectposition = null;
			}
		}

		float f2;
		float f3;

		if (movingobjectposition != null)
		{
			if (movingobjectposition.entityHit != null)
			{
				DamageSource damagesource = null;

				if (this.shootingEntity == null)
				{
					damagesource = VariableHandler.causeBulletDamage(this, this);
				}
				else
				{
					damagesource = VariableHandler.causeBulletDamage(this, this.shootingEntity);
				}

				if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
				{
					movingobjectposition.entityHit.setFire(5);
				}

				if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float)this.GunType.Damage))
				{
					ByteArrayDataOutput data = ByteStreams.newDataOutput();
					data.writeByte(2);
					PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("SCal", data.toByteArray()));

					if (movingobjectposition.entityHit instanceof EntityLivingBase)
					{
						EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;

						if (this.shootingEntity != null)
						{
							EnchantmentThorns.func_92096_a(this.shootingEntity, entitylivingbase, this.rand);
						}

						if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
						{
							((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
						}
					}

					if (!(movingobjectposition.entityHit instanceof EntityEnderman))
					{
						this.setDead();
					}
				}
				else
				{
					this.setDead();
				}
			}
			else
			{
				this.setDead();
			}
		}

		if (this.Type.IsTracer)
		{
			for (l = 0; l < 4; ++l)
			{
				this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double)l / 4.0D, this.posY + this.motionY * (double)l / 4.0D, this.posZ + this.motionZ * (double)l / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
		{
			;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
		{
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
		{
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
		{
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float f4 = 0.99f;

		if (this.isInWater())
		{
			for (int j1 = 0; j1 < 4; ++j1)
			{
				f3 = 0.25F;
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f3, this.posY - this.motionY * (double)f3, this.posZ - this.motionZ * (double)f3, this.motionX, this.motionY, this.motionZ);
			}

			f4 = 0.8F;
		}

		this.motionX *= (double)f4;
		this.motionY *= (double)f4;
		this.motionZ *= (double)f4;
		this.motionY -= (double)this.GunType.BulletDrop;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.doBlockCollisions();
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("xTile", (short)this.xTile);
		par1NBTTagCompound.setShort("yTile", (short)this.yTile);
		par1NBTTagCompound.setShort("zTile", (short)this.zTile);
		par1NBTTagCompound.setInteger("bulletTypeID", this.Type.BulletID);
		par1NBTTagCompound.setInteger("gunTypeID", this.GunType.GunID);
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		this.xTile = par1NBTTagCompound.getShort("xTile");
		this.yTile = par1NBTTagCompound.getShort("yTile");
		this.zTile = par1NBTTagCompound.getShort("zTile");

		if (par1NBTTagCompound.hasKey("bulletTypeID"))
		{
			this.Type = BulletType.getType(par1NBTTagCompound.getInteger("bulletTypeID"));
		}
		if (par1NBTTagCompound.hasKey("gunTypeID"))
		{
			this.GunType = GunType.getType(par1NBTTagCompound.getInteger("bulletTypeID"));
		}
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	public boolean canAttackWithItem()
	{
		return false;
	}

	@Override
	protected void entityInit() 
	{

	}
}
