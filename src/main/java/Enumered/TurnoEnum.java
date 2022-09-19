package Enumered;

public enum TurnoEnum {

	MANHA("Manhã"),
	TARDE("Tarde"),
	NOITE("Noite");
	
	private String label;
	
	private TurnoEnum(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getOrdinal() {
		return this.ordinal();
	}
}
