package scal.guns;

import scal.common.VariableHandler;

public class GunType 
{
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
	public BulletType[] Bullets;
	
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
	
	public GunType(int itemID, String shortName, String name,
			int damage, int numBullets, float accuracyHip, float accuracyScope, float sightZoom,
			FireType fType, WeaponType wType, float recoil, float bulletDrop, int maxCapacity,
			int reloadTime, int shotInterval, int threeRoundInterval, float bulletSpeed, BulletType[] bullets)
	{
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
	}
	
	public boolean isAmmo(ItemBullet bullet)
	{
		for(int i = 0; i < this.Bullets.length; i++)
		{
			if(bullet.Type == this.Bullets[i])
			{
				return true;
			}
		}
		
		return false;
	}

	//Pistols
	
	public static GunType PistolM9 = new GunType(
			VariableHandler.ItemID, "m9", "M9",
			4, 1, .3f, .6f, 1.1f,
			FireType.SemiAuto, WeaponType.Pistol, 0.45f, 0.01f, 15,
			32, 3, 0, 19f, new BulletType[]{
			BulletType.test
			});
	
	public static GunType PistolM1911 = new GunType(
			VariableHandler.ItemID + 1, "m1911", "M1911",
			5, 1, .4f, .7f, 1.2f,
			FireType.SemiAuto, WeaponType.Pistol, 0.5f, 0.01f, 8,
			40, 4, 0, 12.5f, new BulletType[]{
			BulletType.test
			});
}
