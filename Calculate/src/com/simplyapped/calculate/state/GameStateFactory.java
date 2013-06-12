package com.simplyapped.calculate.state;

public class GameStateFactory
{
	public enum GameStateType
	{
		IN_MEMORY,
		PERSISTENT
	}
	
	private static GameStateType Type = GameStateType.IN_MEMORY;
	private static GameState state;
	
	private GameStateFactory(){}

	public static GameStateType getType()
	{
		return Type;
	}

	public static void setType(GameStateType type)
	{
		Type = type;
	} 
	
	public static GameState getInstance()
	{
		if (state == null)
		{
			switch (Type)
			{
				case PERSISTENT:
					state = new PersistentGameState();
					break;
				default:
					state = new InMemoryGameState();
			}
		}
		return state;
	}
}
