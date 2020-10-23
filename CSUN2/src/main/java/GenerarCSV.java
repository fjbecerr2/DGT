import java.util.ArrayList; // import the ArrayList class
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.HashMap;

//@Author: Francisco J. Becerra
//Created: 16102020
public class GenerarCSV{

	private String stFechaInicial="";
	private String stFechaFinal="";
	
	private ArrayList<String> lineasCSV = new ArrayList<String>();
	private DivideCamposReport odivideCamposReport =new DivideCamposReport();
	
	
	private String path;
	private String name;
	private String nameOut;
	//final private String stOutDirectory = "reportOUT";
	//private String stReportPathOut;
	private File file;
	
	public GenerarCSV(){
		getPath();
	}
	
	//Created: 23102020
	public GenerarCSV(String path, String name){
		setPath(path);
		setName(name);
	}
	
	//Update: 02/10/2020
	/*public void getPath(){
		File reportPath = new File (".");
		try {
			setReportPath(reportPath.getCanonicalPath());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }*/
	
	//Update: 02/10/2020
	public void setPath(String path){
		this.path =path;
		//setReportPathOut();
	}

	//Created: 15102020
	/*public void setReportPathOut(){
		this.stReportPathOut = stReportPath+"\\"+stOutDirectory;
	}*/
	
	//Update: 02/10/2020
	public String getPath(){
		return this.path;
	}
	
	//Created: 19102020
	/*public String getstReportPathOut(){
		return this.stReportPathOut;
	}*/
	
	//Created: 19102020
	public void setName(String name){
		this.name =name;
	}
	public String getName(){
		return this.name;
	}
	
	//Created: 23102020
	public String getNameOut(){
		return this.nameOut;
	}
	
	//Created: 15102020
	public void visualizarNombreReportOut(){
		System.out.println("FicheroOut: "+getName());
	}

	public void getFechas(String FechaInicial, String FechaFinal){
		this.stFechaInicial =FechaInicial;
		this.stFechaFinal =FechaFinal;
	}
	
	public String getFechasInforme(){
		return this.stFechaInicial+" - "+this.stFechaFinal;
	}
	
	public void getDatosParaCSV(DivideCamposReport odivideCamposReport){
		this.odivideCamposReport =odivideCamposReport;
		
		System.out.print("\t<Asignando Cabecera>\t");	
		String linea="";
		linea ="Reporte Semanal "+this.stFechaInicial+" - "+this.stFechaFinal;
		guardarLineaReportCSVOut(linea);
		
		insertarLineaContenedorCSV(linea);
		//System.out.println(linea);
		linea ="--------------------------------------- ";
		System.out.println("<OK>");	
		guardarLineaReportCSVOut(linea);
		
		this.odivideCamposReport.organizarEventums();
		ArrayList<CamposEventumCSV> oContenedorEventumsSalida =new ArrayList<CamposEventumCSV>();
		oContenedorEventumsSalida =this.odivideCamposReport.getContenedorEventumsSalida();
		
		System.out.print("\t<Asignando Eventums>\t");
		//Insertar Operadores
		for(Operador operador: this.odivideCamposReport.getOperadores()){
			linea ="Operador:;"+operador.getcodOperador()+" - "+operador.getnomOperador();
			guardarLineaReportCSVOut(linea);
			
			//Recorrer Eventums
			int numEventums =0;
			int numNotas =0;
			int tiempoTotalTrabajado =0;
			linea =";EVENTUM;RESUMEN;ESTADO;TIEMPO;T. ASIGNACION;GRUPO";
			guardarLineaReportCSVOut(linea);
			linea ="";
			
			for(CamposEventumCSV eventum: oContenedorEventumsSalida){
				if(operador.getcodOperador().equals(eventum.getcodOperador())){
					linea =";";
					linea =linea.concat(eventum.getcodEventum());
					linea +=";";
					
					linea =linea.concat(eventum.getresumen());
					linea +=";";
					
					linea =linea.concat(eventum.getestado());
					linea +=";";
					
					linea =linea.concat(eventum.gettiempoTrabajo());
					linea +=";";
					tiempoTotalTrabajado +=Integer.parseInt(eventum.gettiempoTrabajo().trim());
					
					linea =linea.concat(eventum.gettipoAsignacion());
					linea +=";";
					if(eventum.gettipoAsignacion().trim().contains("TIEMPO")){
						numNotas++;
					}
					
					linea =linea.concat(eventum.getgrupo());
					linea +=";";
					
					//Enlace
					//linea+="https://vmeventumpro.trafico.es/eventum/view.php?id="+eventum.getcodEventum();
										
					guardarLineaReportCSVOut(linea);
					
					//Contadores
					numEventums++;
					this.odivideCamposReport.updateCantidadTiposEstadosSumatorio(eventum.getestado());		
				}
			}
						
			
			//Sumatorio por estados
			guardarLineaReportCSVOut("");
			HashMap<String, Integer> oContenedorSumatoriosEstados =this.odivideCamposReport.devolverSumatorioEstados();
			for (String estado : oContenedorSumatoriosEstados.keySet()) {
				if(oContenedorSumatoriosEstados.get(estado)>0){
					guardarLineaReportCSVOut(";"+estado+":;"+oContenedorSumatoriosEstados.get(estado));
				}
			}
			
			
			guardarLineaReportCSVOut("");
			guardarLineaReportCSVOut(";Eventums:;"+numEventums);
			guardarLineaReportCSVOut("");

			guardarLineaReportCSVOut(";Total Notas:;"+numNotas);
			
			guardarLineaReportCSVOut(";Tiempo Total Invertido:;"+devolverCadenaTiempoHoras(tiempoTotalTrabajado));
			guardarLineaReportCSVOut("");
		
			this.odivideCamposReport.clearTiempoTiposEstadosSumatorio();
		
		}
		System.out.println("<OK>");	
	}
	
	//Created: 08102020
	public void insertarLineaContenedorCSV(String linea){
		this.lineasCSV.add(linea+"\n");
	}
	
	
	//Created: 19102020
	public String getFechaHoraReport(){
		Date objDate = new Date(); 
		String strDateFormat = "ddMMyyyy_HHmmss";
		SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);		
	
		return (objSDF.format(objDate));
	}
	
	//Created: 08102020
	public boolean generarFicheroCSVSalida(){
		boolean estado =false;
		
		setName(this.name+"_"+getFechaHoraReport()+".csv");
		this.nameOut =this.path+"\\"+this.name;
		this.file = new File (this.nameOut);
		try {
			// A partir del objeto File creamos el fichero físicamente
			if (this.file.createNewFile()){
				System.out.println("\n<FICHERO CSV OUT>\t <OK>");
				estado =true;
			}
			else{
				System.out.println("\n<FICHERO CSV OUT>\t <ERROR>");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally{
			return estado;
		}
		
	}
	
	//Created: 19102020
	public void guardarLineaReportCSVOut(String linea){	
		 try {
            if (this.file.exists()) {
				//FileWriter fw = new FileWriter(this.fReportFile);
				FileWriter fw = new FileWriter(this.nameOut, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(linea+"\n");
				bw.close();   
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
			/* try {
							//Cierra instancias de FileWriter y BufferedWriter
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}*/
		}
		
	}
	
	
	//Created: 19102020
	public void asignarDatosSalida(DivideCamposReport divideCamposReport){
		this.odivideCamposReport =divideCamposReport;
		
	}
	
	//Created: 21102020
	public String devolverCadenaTiempoHoras(int pminutos){
		String mensaje="";
		int dias =pminutos/24/60;
		int horas =pminutos/60%24;
		int minutos =pminutos%60;
		
		if(dias>0){
			mensaje =mensaje.concat(String.valueOf(dias)+"d ");
		}
		if(horas>0){
			mensaje =mensaje.concat(String.valueOf(horas)+"h ");
		}
		if(minutos>0){
			mensaje =mensaje.concat(String.valueOf(minutos)+"m ");
		}
		
		//return minutos/24/60 + "d:" + minutos/60%24 + "h:" + minutos%60 +"m";
		return mensaje;
		
		/*String formato = "%02d:%02d";
		long horasReales = TimeUnit.MINUTES.toHours(minutos);
		long minutosReales = TimeUnit.MINUTES.toMinutes(minutos) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutos));
		return String.format(formato, horasReales, minutosReales);*/
	}
	
	
}



