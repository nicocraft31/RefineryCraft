package me.nicocraft31.refinery.common.energy;

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

	public int getId()
	{
		return this.id;
	}
}