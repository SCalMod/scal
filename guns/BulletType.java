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
	
	//Snipers

	public static BulletType BulletL96 = new BulletType(2, "bulletl96", 2, true);
	
	public BulletType(int bulletID, String texturePath, int gun, boolean isTracer)
	{
		this.BulletID = bulletID;
		this.TexturePath = texturePath;
		this.Gun = gun;
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
