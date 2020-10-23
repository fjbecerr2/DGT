//@Author: Francisco J. Becerra
//Created: 16102020

public class CamposEventumCSV{

	private String codOperador ="";
	private String nomOperador ="";
	private String codEventum ="";
	private String resumen ="";
	private String grupo ="";
	private String tipoAsignacion ="";
	private String tiempoTrabajo ="";
	private String estado ="";

	public CamposEventumCSV(){}
	
	public void setcodOperador(String codOperador){ this.codOperador =codOperador;}
	public String getcodOperador(){ return this.codOperador;	}
	
	public void setnomOperador(String nomOperador){ this.nomOperador =nomOperador;}
	public String getnomOperador(){ return this.nomOperador;	}
	
	public void setcodEventum(String codEventum){ this.codEventum =codEventum;}
	public String getcodEventum(){ return this.codEventum;}
	
	public void setresumen(String resumen){this.resumen =resumen;}
	public String getresumen(){ return this.resumen;}
	
	public void setgrupo(String grupo){ this.grupo =grupo;}
	public String getgrupo(){ return this.grupo; }
	
	public void settipoAsignacion(String tipoAsignacion){ this.tipoAsignacion =tipoAsignacion;}
	public String gettipoAsignacion(){ return this.tipoAsignacion;}
	
	public void settiempoTrabajo(String tiempoTrabajo){ this.tiempoTrabajo =tiempoTrabajo;}
	public String gettiempoTrabajo(){ return this.tiempoTrabajo;}
	
	public void setestado(String estado){ this.estado =estado;}
	public String getestado(){ return this.estado;}
		
}
