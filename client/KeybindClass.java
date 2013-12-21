package scal.client;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import scal.common.VariableHandler;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class KeybindClass extends KeyHandler
{
	private EnumSet tickTypes = EnumSet.of(TickType.CLIENT);
	public static boolean IsDown = false;
	
	public KeybindClass(KeyBinding[] keyBindings, boolean[] repeatings) 
	{
		super(keyBindings, repeatings);
	}

	@Override
	public String getLabel() 
	{
		return "SCal Guns Key";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) 
	{
		if(kb.keyCode == Keyboard.KEY_Z)
		{
			VariableHandler.KeyZ = true;
		}
		if(kb.keyCode == Keyboard.KEY_X)
		{
			VariableHandler.KeyX = true;
		}
		if(kb.keyCode == Keyboard.KEY_C)
		{
			VariableHandler.KeyC = true;
		}
		if(kb.keyCode == Keyboard.KEY_G)
		{
			VariableHandler.KeyG = true;
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) 
	{
		if(kb.keyCode == Keyboard.KEY_Z)
		{
			VariableHandler.KeyZ = false;
		}
		if(kb.keyCode == Keyboard.KEY_X)
		{
			VariableHandler.KeyX = false;
		}
		if(kb.keyCode == Keyboard.KEY_C)
		{
			VariableHandler.KeyC = false;
		}
		if(kb.keyCode == Keyboard.KEY_G)
		{
			VariableHandler.KeyG = false;
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return this.tickTypes;
	}
}
