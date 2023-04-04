package me.nicocraft31.refinery.common.energy;

import me.nicocraft31.refinery.common.block.tileentity.TileEntityUtil;

public enum EnergyUnit {
	RF(0),
	KRF(1),
	MRF(2),
	GRF(3);
	
	private int id;
	
	public static int KRF_RATE = 1000;
	public static int MRF_RATE = 1000000;
	
	private EnergyUnit(int id)
	{
		this.id = id;
	}

	public float fromRF(int rf)
	{
		EnergyUnit unit = this;
		
		switch(unit)
		{
		case RF:
			return (float) rf;
		case KRF:
			return TileEntityUtil.toKRF(rf);
		case MRF:
			return TileEntityUtil.toMRF(rf);
		default:
			return 0f;
		}
	}
	
	public int getId()
	{
		return this.id;
	}
}