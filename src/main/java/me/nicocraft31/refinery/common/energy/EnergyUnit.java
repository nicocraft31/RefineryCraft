package me.nicocraft31.refinery.common.energy;

public enum EnergyUnit {
	RF  (0, "RF" ),
	KRF (1, "kRF"),
	MRF (2, "MRF"),
	GRF (3, "GRF");

	private int id;
	private String string;
	
	public static int KRF_RATE = 1000;
	public static int MRF_RATE = 1000000;
	
	private EnergyUnit(int id, String string)
	{
		this.id = id;
		this.string = string;
	}

	public float fromRF(int rf)
	{
		switch(id)
		{
		case 0:
			return (float) rf;
		case 1:
			return EnergyUtil.toKRF(rf);
		case 2:
			return EnergyUtil.toMRF(rf);
		case 3:
			return EnergyUtil.toGRF(rf);
		default:
			return 0.f;
		}
	}
	
	@Override
	public String toString() {
		return getString();
	}
	
	public String getString()
	{
		return this.string;
	}
	
	public int getId()
	{
		return this.id;
	}
}