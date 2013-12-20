package scal.guns;

import scal.common.VariableHandler;

public class GunType 
{
	public int ItemID;
	public String TexturePath;
	public String Name;
	public String ShortName;
	
	public int Damage;
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
	public float BulletSpeed;
	
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
	
	public GunType(int itemID, String texturePath, String name, String shortName,
			int damage, float accuracyHip, float accuracyScope, float sightZoom,
			FireType fType, WeaponType wType, float recoil, float bulletDrop, int maxCapacity,
			int reloadTime, int shotInterval, float bulletSpeed,
			String shootSound, String reloadSound, String scopePath)
	{
		this.ItemID = itemID;
		this.TexturePath = texturePath;
		this.Name = name;
		this.ShortName = shortName;
		
		this.Damage = damage;
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
		this.BulletSpeed = bulletSpeed;
		
		this.ShootSound = shootSound;
		this.ReloadSound = reloadSound;
		this.ScopePath = scopePath;
	}

	public static GunType PistolM9 = new GunType(VariableHandler.ItemID, "m9", "M9", "m9",
			4, .4f, .2f, 1.2f,
			FireType.SemiAuto, WeaponType.Pistol, 1.2f, 0.01f, 15,
			32, 3, 19f,
			"m9", "m9", "m9");
}
