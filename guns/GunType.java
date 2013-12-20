package scal.guns;

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
	public FireType Type;
	public float Recoil;
	public float BulletDrop;
	public int MaxCapacity;
	public int ReloadTime;
	public int ShotInterval;
	public float BulletSpeed;
	
	public String ShootSound;
	public String ReloadSound;
	public boolean HasScope;
	public String ScopePath;
	
	public enum FireType
	{
		SingleShot,
		BoltAction,
		SemiAuto,
		ThreeRound,
		FullAuto
	}
	
	public GunType(int itemID, String texturePath, String name, String shortName,
			int damage, float accuracyHip, float accuracyScope, float sightZoom,
			FireType type, float recoil, float bulletDrop, int maxCapacity,
			int reloadTime, int shotInterval, float bulletSpeed,
			String shootSound, String reloadSound, boolean hasScope, String scopePath)
	{
		this.ItemID = itemID;
		this.TexturePath = texturePath;
		this.Name = name;
		this.ShortName = shortName;
		
		this.Damage = damage;
		this.AccuracyHip = accuracyHip;
		this.AccuracyScope = accuracyScope;
		this.SightZoom = sightZoom;
		
		this.Type = type;
		this.Recoil = recoil;
		this.BulletDrop = bulletDrop;
		this.MaxCapacity = maxCapacity;
		
		this.ReloadTime = reloadTime;
		this.ShotInterval = shotInterval;
		this.BulletSpeed = bulletSpeed;
		
		this.ShootSound = shootSound;
		this.ReloadSound = reloadSound;
		this.HasScope = hasScope;
		this.ScopePath = scopePath;
	}
}
