package scal.guns;

import java.io.IOException;

import scal.common.VariableHandler;

public class BulletType 
{
	public int BulletID;
	public String TexturePath;
	public int Gun;
	public boolean IsTracer;

	public static BulletType[] Bullets = new BulletType[260];
	
	//Pistols
	
	public static BulletType BulletM9 = new BulletType(0, "bulletm9", 0, false);
	public static BulletType BulletM1911 = new BulletType(1, "bulletm1911", 1, false);
	
	public BulletType(int bulletID, String texturePath, int gun, boolean isTracer)
	{
		System.out.println("BulletInit begin");
		System.out.println("BulletID = " + bulletID);
		this.BulletID = bulletID;
		System.out.println("TexturePath = " + texturePath);
		this.TexturePath = texturePath;
		System.out.println("Gun = " + GunType.getType(gun));
		this.Gun = gun;
		System.out.println("isTracer = " + isTracer);
		this.IsTracer = isTracer;
		
		if(Bullets[bulletID] == null)
		{
			Bullets[bulletID] = this;
		}
		else
		{
			System.out.println("SCal Guns: Bullet ID conflict");
		}
	}
	
	public static BulletType getType(int bulletID)
	{
		try
		{
			return Bullets[bulletID];
		}
		finally
		{
			
		}
	}
}
