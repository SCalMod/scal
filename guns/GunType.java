package scal.guns;

import scal.common.VariableHandler;

public class GunType 
{
	public static GunType[] Guns = new GunType[180];
	
	public int GunID;
	public int ItemID;
	public String TexturePath;
	public String Name;
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
	public float BulletSpeed;
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
	
	public GunType(int gunID, int itemID, String shortName, String name,
			int damage, int numBullets, float accuracyHip, float accuracyScope, float sightZoom,
			FireType fType, WeaponType wType, float recoil, float bulletDrop, int maxCapacity,
			int reloadTime, int shotInterval, int threeRoundInterval, float bulletSpeed, int[] bullets)
	{
		this.GunID = gunID;
		this.ItemID = itemID;
		this.TexturePath = shortName;
		this.Name = name;
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
		this.BulletSpeed = bulletSpeed;
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
			0, VariableHandler.ItemID, "m9", "M9",
			4, 1, .1f, .05f, 1.1f,
			FireType.SemiAuto, WeaponType.Pistol, 0.45f, 0.01f, 15,
			32, 3, 0, 4.75f, new int[]{
			0
			});
	
	public static GunType PistolM1911 = new GunType(
			1, VariableHandler.ItemID + 1, "m1911", "M1911",
			5, 1, .12f, .06f, 1.2f,
			FireType.SemiAuto, WeaponType.Pistol, 0.5f, 0.01f, 8,
			40, 4, 0, 3.125f, new int[]{
			1
			});
}
