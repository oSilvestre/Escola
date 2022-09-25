package Model;

public class Empresa extends Entidade {

	private String razaoSocial;
	private String nomeFantasia;
	private Long cnpj;
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public Long getCnpj() {
		return cnpj;
	}
	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}
	
	
}
