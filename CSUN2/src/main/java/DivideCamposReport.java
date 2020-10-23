import java.util.ArrayList; // import the ArrayList class
import java.util.HashSet;
import java.util.Iterator;
import java.util.HashMap;


//Created: 06102020
public class DivideCamposReport{

	private final char elementoSeparadorCSV =';';
	
	private ArrayList<Operador> oContedorOperadores =new ArrayList<Operador>();
	private HashSet<String> tiposEstado =new HashSet<String>();
	private ArrayList<CamposEventumCSV> oContenedorEventums =new ArrayList<CamposEventumCSV>();
	private ArrayList<CamposEventumCSV> oContenedorEventumsSalida =new ArrayList<CamposEventumCSV>();
	private HashSet<String> oContenedoreventumsAsignados = new HashSet<String>();	
	
	HashMap<String, Integer> oContenedorSumatoriosEstados = new HashMap<String, Integer>();
		
	//Created: 16102020
	public void divideLineaEventum(String linea){
		//Campos
		//239;Jesus Galende Calzada;101339;[98944] [Plan de actuación] Datos Pendientes Trans...;CSU N2;INFORMADOR;0;Cerrada
		
		String codOperador ="";
		String nomOperador ="";
		String codEventum ="";
		String resumen ="";
		String grupo ="";
		String tipoAsignacion ="";
		String tiempoTrabajo ="";
		String estado ="";
		
		String eventum = linea;
		String campo;		
		
		//Cortar los campos
		for(int i=0; i<8; i++){
				
			if(i<7){ //Control separador ultimo campo
				campo = eventum.substring(0,eventum.indexOf(elementoSeparadorCSV));					
				eventum = eventum.substring(eventum.indexOf(elementoSeparadorCSV)+1);
			}
			else{
				campo = eventum;
			}
				
			switch(i){
				case 0:
					codOperador = campo;
				break;
				case 1:
					nomOperador =campo;
				break;
				case 2:
					codEventum =campo;
				break;
				case 3:
					resumen =campo;
				break;
				case 4:
					grupo =campo;
				break;
				case 5:
					tipoAsignacion =campo;
				break;
				case 6:
					tiempoTrabajo =campo;
				break;
				case 7:
					estado =campo;
					setEstado(estado); //Inserta estado para sumatorios del detalles
				break;					
			}
			
		}
		
		//if(codOperador.equals("239") || codOperador.equals("240") ){ //TEST
			Operador oOperador =new Operador(codOperador,nomOperador);
			
			this.oContedorOperadores.add(oOperador);
			
			CamposEventumCSV oCamposEventumCSV =new CamposEventumCSV();
			oCamposEventumCSV.setcodOperador(codOperador);
			oCamposEventumCSV.setnomOperador(nomOperador);
			oCamposEventumCSV.setcodEventum(codEventum);
			oCamposEventumCSV.setresumen(resumen);
			oCamposEventumCSV.setgrupo(grupo);
			oCamposEventumCSV.settipoAsignacion(tipoAsignacion);
			oCamposEventumCSV.settiempoTrabajo(tiempoTrabajo);
			oCamposEventumCSV.setestado(estado);
			this.oContenedorEventums.add(oCamposEventumCSV);
		//}	
	}
	
	//Created: 16102020
	public void divideLineaTiempo(String linea){
		String codOperador ="";
		String codEventum ="";
		String tiempoTrabajo ="";
		//239;98950;840
		
		String eventum = linea;
		String campo;
		
		for(int i=0; i<3; i++){
				
			if(i<2){ //Control separador ultimo campo
				campo = eventum.substring(0,eventum.indexOf(elementoSeparadorCSV));					
				eventum = eventum.substring(eventum.indexOf(elementoSeparadorCSV)+1);
			}
			else{
				campo = eventum;
			}
				
			switch(i){
				case 0:
					codOperador = campo;
				break;
				case 1:
					codEventum =campo;
				break;
				case 2:
					tiempoTrabajo =campo;
				break;
			}
		
		}
		
		CamposTiempoCSV oCamposTiempoCSV =new CamposTiempoCSV();
		oCamposTiempoCSV.setcodOperador(codOperador);
		oCamposTiempoCSV.setcodEventum(codEventum);
		oCamposTiempoCSV.settiempoTrabajo(tiempoTrabajo);
		
		//Los actualiza asi por si hay duplicados
		actualizarTiempoEventum(oCamposTiempoCSV);
	
	}
	
	//Created: 16102020
	public void actualizarTiempoEventum(CamposTiempoCSV tiempo){
		for(CamposEventumCSV eventum: this.oContenedorEventums){
			if(eventum.getcodOperador().equals(tiempo.getcodOperador())
				&& eventum.getcodEventum().equals(tiempo.getcodEventum())){
					eventum.settiempoTrabajo(tiempo.gettiempoTrabajo());
			}
		}
	}
	
	//Created: 16102020
	public void recorrerEventums(){
		for(CamposEventumCSV eventum: this.oContenedorEventums){
			System.out.println(eventum.getcodOperador()+";"+eventum.getcodEventum()+";"+eventum.gettipoAsignacion()+";"+eventum.gettiempoTrabajo());
		}
	}
	
	
	//Update: 08102020
	//TODO - SUSTITUIR ESTE CODIGO POR HANSET
	public void eliminarOperadoresDuplicados(){
		System.out.print("\t<Eliminando operadores duplicados>");
		ArrayList<Operador> contenedorOperadores = new ArrayList<Operador>();
		
		//Recorremos el array inicial
		Operador operador =new Operador();
		boolean encontrado =false;
		
		for(int i=0; i<this.oContedorOperadores.size();i++){
			encontrado =false;
			operador =oContedorOperadores.get(i);
			
			//Recorrer el temporal
			for(Operador elemento: contenedorOperadores){
				//Comprobar si ya existe el código
				if(operador.getcodOperador().equals(elemento.getcodOperador())){
					encontrado=true;
				}
			}
			
			if(encontrado==false){
				contenedorOperadores.add(operador);
			}
		}
		
		this.oContedorOperadores.clear();
		this.oContedorOperadores = contenedorOperadores; //Asignar tmp sin duplicados
		
		System.out.println("\t<OK>");
	}
	
	public ArrayList<Operador> getContenedorOperadores(){
		return this.oContedorOperadores;
	}
	
	//Created: 07102020
	public void setEstado(String estado){
		this.tiposEstado.add(estado);
	}

	
	//Created: 19102020
	public void recorrerOperadores(){
		for(Operador operador: this.oContedorOperadores){
			System.out.println("Operador: "+operador.getcodOperador());
		}
	}
	
	//Created: 19102020
	public void recorrerEstados(){
		for(String estado: this.tiposEstado){
			System.out.println("Estado: "+estado);
		}
	}
	
	
	
	//Created: 19102020
	public ArrayList<Operador> getOperadores(){
		return this.oContedorOperadores;
	}
	
	//Created: 19102020
	public ArrayList<CamposEventumCSV> getContenedorEventums(){
		return this.oContenedorEventums;
	}
	
	
	//Created: 20102020
	public void setContenedoreventumsAsignados(){
		for(CamposEventumCSV Eventum: this.oContenedorEventums){
			 this.oContenedoreventumsAsignados.add(Eventum.getcodOperador()+";"+Eventum.getcodEventum()+";"+Eventum.getresumen()+";"+Eventum.getestado());
		}
	}
	
	//Created: 19102020
	//Update: 20102020
	//Description: Filtra repeticions y afina los datos de los evemtun
	public void organizarEventums(){
		setTiposEstadosSumatorio();
		setContenedoreventumsAsignados(); //Asigna los eventum sin repeticiones
		
		//Recorrer Operadores
		for(Operador operador: this.oContedorOperadores){
			//Recorrer los eventums simples
			for(String eventum: this.oContenedoreventumsAsignados){
				//Cortar el operador
				String codOperador =eventum.substring(0,eventum.indexOf(";"));
				int corte =eventum.indexOf(";")+1;
				//Cortar el eventum
				String codEventum =eventum.substring(corte);
				codEventum =codEventum.substring(0,codEventum.indexOf(";"));
				String tipoAsignacion ="";
				String tiempoTrabajo ="";
				String grupo ="";
				String resumen ="";
				String estado ="";
				if(operador.getcodOperador().equals(codOperador)){	
					tipoAsignacion =getTiposdeAsignaciondoContenedorEventums(codOperador,codEventum);
					tiempoTrabajo =gettiempoTrabajoContenedorEventums(codOperador,codEventum);
					grupo =getgrupoContenedorEventums(codOperador,codEventum);
					resumen =getresumenContenedorEventums(codOperador,codEventum);
					estado =getestadoContenedorEventums(codOperador,codEventum);				
		
					//Montar
					CamposEventumCSV EventunSalida =new CamposEventumCSV();
					EventunSalida.setcodOperador(operador.getcodOperador());
					EventunSalida.setnomOperador(operador.getnomOperador());
					EventunSalida.setcodEventum(codEventum);
					EventunSalida.setresumen(resumen);
					EventunSalida.setgrupo(grupo);
					EventunSalida.settipoAsignacion(tipoAsignacion);
					EventunSalida.settiempoTrabajo(tiempoTrabajo);
					EventunSalida.setestado(estado);	
		
					//Guardar datos filtrados
					this.oContenedorEventumsSalida.add(EventunSalida);
				}
			}	
		}
	}
	
	
	//Created: 20102020
	public ArrayList<CamposEventumCSV> getoContenedorEventumsSalida(){
		return this.oContenedorEventumsSalida;
	}

	//Created: 20102020
	public String getTiposdeAsignaciondoContenedorEventums(String codOperador, String codEventum){
		String tipoAsignacion ="";
		for(CamposEventumCSV eventum :this.oContenedorEventums){
			if(eventum.getcodOperador().equals(codOperador)
				&&
				eventum.getcodEventum().equals(codEventum)){
	
				tipoAsignacion =tipoAsignacion.concat(eventum.gettipoAsignacion()+" ");
			}
		}	
		
		return tipoAsignacion;
	}
	
	//Created: 20102020
	public String gettiempoTrabajoContenedorEventums(String codOperador, String codEventum){
		String tiempoTrabajo ="";
		boolean encontrado =false;
		for(CamposEventumCSV eventum :this.oContenedorEventums){
			if(eventum.getcodOperador().equals(codOperador)
				&&
				eventum.getcodEventum().equals(codEventum)){
	
				if(encontrado==false){
					tiempoTrabajo =tiempoTrabajo.concat(eventum.gettiempoTrabajo()+" ");
					encontrado =true;
				}
			}
		}	
		return tiempoTrabajo;
		
	}
	
	
	//Created: 20102020
	//Description: Localizar un eventum y devolver el grupo
	public String getgrupoContenedorEventums(String codOperador, String codEventum){
		String grupo ="";
		boolean encontrado =false;
		for(CamposEventumCSV eventum :this.oContenedorEventums){
			if(eventum.getcodOperador().equals(codOperador)
				&&
				eventum.getcodEventum().equals(codEventum)){
	
				if(encontrado==false){
					grupo =grupo.concat(eventum.getgrupo()+" ");
					encontrado =true;
				}
			}
		}	
		return grupo;
		
	}
	
	//Created: 20102020
	//Description: Localizar un eventum y devolver resumen
	public String getresumenContenedorEventums(String codOperador, String codEventum){
		String resumen ="";
		boolean encontrado =false;
		for(CamposEventumCSV eventum :this.oContenedorEventums){
			if(eventum.getcodOperador().equals(codOperador)
				&&
				eventum.getcodEventum().equals(codEventum)){
	
				if(encontrado==false){
					resumen =resumen.concat(eventum.getresumen()+" ");
					encontrado =true;
				}
			}
		}	
		return resumen;
	}
	
	//Created: 20102020
	//Description: Localizar un Eventum y devolver su estado
	public String getestadoContenedorEventums(String codOperador, String codEventum){
		String estado ="";
		boolean encontrado =false;
		for(CamposEventumCSV eventum :this.oContenedorEventums){
			if(eventum.getcodOperador().equals(codOperador)
				&&
				eventum.getcodEventum().equals(codEventum)){
	
				if(encontrado==false){
					estado =estado.concat(eventum.getestado()+" ");
					encontrado =true;
				}
			}
		}	
		
		return estado;
	}
	
	//Created: 20102020
	public ArrayList<CamposEventumCSV> getContenedorEventumsSalida(){
		return this.oContenedorEventumsSalida;
	}				
	
	//Created: 21102020
	public void setTiposEstadosSumatorio(){
		for(String estado: tiposEstado){
		 this.oContenedorSumatoriosEstados.put(estado.trim(), 0);
		}
	}
	
	//Created: 21102020
	public void updateCantidadTiposEstadosSumatorio(String estado){
				
		//Localizar el valor
		Integer suma = this.oContenedorSumatoriosEstados.get(estado.trim());
		suma++;
		this.oContenedorSumatoriosEstados.put(estado.trim(), suma);

	}
	
	//Created: 21102020
	public void clearTiempoTiposEstadosSumatorio(){
		for (String estado : this.oContenedorSumatoriosEstados.keySet()) {
				this.oContenedorSumatoriosEstados.put(estado.trim(), 0);
		}
	}
	
	//Created: 21102020
	public HashMap<String, Integer> devolverSumatorioEstados(){
		return this.oContenedorSumatoriosEstados;
	}
					
	
}
