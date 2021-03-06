package scal.guns;

import scal.common.VariableHandler;

public class GunType 
{
	public static GunType[] Guns = new GunType[180];
	
	public int GunID;
	public int ItemID;
	public String TexturePath;
	public String ShortName;
	
	public int Damage;
	public int NumBullets;
	public float AccuracyHip;
	public float AccuracyScope;
	public float SightZoom;
	
	public FireType FType;
	public WeaponType WType;
	public float Recoil;
	public float BulletDrop;
	public int MaxCapacity;
	
	public int ReloadTime;
	public int ShotInterval;
	public int ThreeRoundInterval;
	public int[] Bullets;
	
	public String ShootSound;
	public String ReloadSound;
	public String ScopePath;
	
	public enum FireType
	{
		SingleShot,
		BoltAction,
		SemiAuto,
		ThreeRound,
		FullAuto
	}
	public enum WeaponType
	{
		Pistol,
		SMG,
		Shotgun,
		AssaultRifle,
		LMG,
		Sniper,
		Launcher,
		Other
	}
	
	public GunType(int gunID, int itemID, String shortName,
			int damage, int numBullets, float accuracyHip, float accuracyScope, float sightZoom,
			FireType fType, WeaponType wType, float recoil, float bulletDrop, int maxCapacity,
			int reloadTime, int shotInterval, int threeRoundInterval, int[] bullets)
	{
		this.GunID = gunID;
		this.ItemID = itemID;
		this.TexturePath = shortName;
		this.ShortName = shortName;
		
		this.Damage = damage;
		this.NumBullets = numBullets;
		this.AccuracyHip = accuracyHip;
		this.AccuracyScope = accuracyScope;
		this.SightZoom = sightZoom;
		
		this.FType = fType;
		this.WType = wType;
		this.Recoil = recoil;
		this.BulletDrop = bulletDrop;
		this.MaxCapacity = maxCapacity;
		
		this.ReloadTime = reloadTime;
		this.ShotInterval = shotInterval;
		this.ThreeRoundInterval = threeRoundInterval;
		this.Bullets = bullets;
		
		this.ShootSound = shortName;
		this.ReloadSound = shortName;
		this.ScopePath = shortName;
		
		if(Guns[gunID] == null)
		{
			Guns[gunID] = this;
		}
		else
		{
			System.out.println("SCal Guns: Gun ID conflict");
		}
	}
	
	public boolean isAmmo(ItemBullet bullet)
	{
		for(int i = 0; i < this.Bullets.length; i++)
		{
			if(bullet.Type == BulletType.getType(this.Bullets[i]))
			{
				return true;
			}
		}
		
		return false;
	}

	public static GunType getType(int gunID)
	{
		try
		{
			return Guns[gunID];
		}
		finally
		{
			
		}
	}
	
	//Pistols
	
	public static GunType PistolM9 = new GunType(
			0, VariableHandler.ItemID, "m9",
			4, 1, 3.4f, 1.7f, 1.1f,
			FireType.SemiAuto, WeaponType.Pistol, 0.45f, 0.006f, 15,
			32, 3, 0, new int[]{
			0
			});
	
	public static GunType PistolM1911 = new GunType(
			1, VariableHandler.ItemID + 2, "m1911",
			5, 1, 3.6f, 1.8f, 1.1f,
			FireType.SemiAuto, WeaponType.Pistol, 0.5f, 0.006f, 8,
			40, 4, 0, new int[]{
			1
			});
	
	//Snipers
	
	public static GunType SniperL96 = new GunType(
			2, VariableHandler.ItemID + 4, "l96",
			14, 1, 12f, 0.8f, 4.4f,
			FireType.BoltAction, WeaponType.Sniper, 0.8f, 0.004f, 10,
			96, 25, 0, new int[]{
			2
			});
}
