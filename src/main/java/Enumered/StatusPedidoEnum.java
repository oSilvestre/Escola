package Enumered;

public enum StatusPedidoEnum {

	EM_DIGITACAO("Em digitação"),
	FINALIZADO("Finalizado"),
	CANCELADO("Cancelado");
	
	private String label;
	
	private StatusPedidoEnum(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getOrdinal() {
		return this.ordinal();
	}
}
