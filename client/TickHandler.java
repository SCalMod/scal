package scal.client;

import java.util.EnumSet;

import scal.common.SCal;
import scal.common.VariableHandler;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements IScheduledTickHandler
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		if(type.contains(TickType.CLIENT))
		{
			if(VariableHandler.HitMarkerTimer > 0)
			{
				VariableHandler.HitMarkerTimer--;
			}
			if(VariableHandler.ReloadInterval > 0)
			{
				VariableHandler.HitMarkerTimer--;
			}
			if(VariableHandler.ShootInterval > 0)
			{
				VariableHandler.HitMarkerTimer--;
			}
			if(VariableHandler.ThreeRoundTimer > 0)
			{
				VariableHandler.ThreeRoundTimer--;
				
				if(VariableHandler.ThreeRoundTimer <= 0)
				{
					VariableHandler.ThreeRoundIterator = 0;
				}
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if (type.contains(TickType.RENDER))
		{
			SCal.Proxy.renderMarker();
			SCal.Proxy.renderSight();
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT, TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		return null;
	}

	@Override
	public int nextTickSpacing()
	{
		return 1;
	}
}
