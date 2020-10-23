//Author: Fco. J. Becerra
//Created: 06102020
//Update: 16102020
public class Operador{

	private String codOperador;
	private String nomOperador;
	
	public Operador(){};
	
	public Operador(String codOperador, String nomOperador){
		setcodOperador(codOperador);
		setnomOperador(nomOperador);
	}
	
	public void setcodOperador(String codOperador){	this.codOperador =codOperador;}
	public void setnomOperador(String nomOperador){	this.nomOperador =nomOperador;}
	public String  getcodOperador(){return this.codOperador;}
	public String getnomOperador(){	return this.nomOperador;}
}
