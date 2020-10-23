import java.util.ArrayList; // import the ArrayList class
import java.io.File;
import java.util.Scanner; // Import the Scanner class to 
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Date;
import java.text.SimpleDateFormat;

//@Autho: Francisco J. Becerra
//Created: 1610202
public class ReportSemanalCSUN2_LeerCSV{
	
	private ArrayList<String> cabecerasCSV =new ArrayList<String>();
	private String stFechaLeerCSV;
	private String stFechaInicialCSV;
	private String stFechaFinalCSV;
	
	private DivideCamposReport odivideCamposReport =new DivideCamposReport();
	
	
	public ReportSemanalCSUN2_LeerCSV(){
		setFechaListado();
		cargarCabecerasCSV();
	};
	
	//Created: 16102020
	public void cargarCabecerasCSV(){
		this.cabecerasCSV.add("<FECHA>");
		this.cabecerasCSV.add("<EVENTUM>");
		this.cabecerasCSV.add("<TIEMPO>");
	}
	
	//Update: 05/10/2020
	public void setFechaListado(){
		Date objDate = new Date(); 
		String strDateFormat = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);		
		
		this.stFechaLeerCSV =objSDF.format(objDate);
	}
	
	public boolean leerLineasCSV(File file){
		System.out.println("<LEYENDO FICHERO CSV>");
		int contadorlineas =0;
		int contadorLineasEventum =0;
		int contadorLineasTiempo =0;
		boolean resultado =false;
			
		try {
			//Scanner myReader = new Scanner(this.fReportFile, "ISO-8859-1");
			Scanner myReader = new Scanner(file, "UTF-8");
			int cabeceraActual =-1;
			
			while (myReader.hasNextLine()) {
				String linea = myReader.nextLine();
				contadorlineas++;
				
				//Asignar Fechas
				if(linea.equals(this.cabecerasCSV.get(0))){
					System.out.println("\t"+this.cabecerasCSV.get(0));
					linea = myReader.nextLine(); //Saltar la linea de cabecera
					contadorlineas++;
					cabeceraActual++;
				}
				
				//Asignar Eventums
				if(linea.equals(this.cabecerasCSV.get(1))){
					System.out.println("\t"+this.cabecerasCSV.get(1));
					linea = myReader.nextLine(); //Saltar la linea de cabecera
					contadorlineas++;
					cabeceraActual++;
				}
				
				//Asignar Tiempos
				if(linea.equals(this.cabecerasCSV.get(2))){
					System.out.println("\t\tLineas Eventums: \t"+contadorLineasEventum); //Sumatorio cambio cabecera
					System.out.println("\t"+this.cabecerasCSV.get(2));
					linea = myReader.nextLine(); //Saltar la linea de cabecera
					contadorlineas++;
					cabeceraActual++;
				}
				
				switch(cabeceraActual){
					case 0:				
						setFechaCSV(linea.substring(0,10),linea.substring(11));
					break;
					case 1:
						this.odivideCamposReport.divideLineaEventum(linea);
						contadorLineasEventum++;
					break;
					case 2:
						this.odivideCamposReport.divideLineaTiempo(linea);
						contadorLineasTiempo++;
					break;
				}
				
			}
			System.out.println("\t\tLineas Eventums: \t"+contadorLineasTiempo); //Sumatorio cambio cabecera
			System.out.println("\t<Lineas Leidas: \t"+contadorlineas+" lineas>\t<OK>");
			
			this.odivideCamposReport.eliminarOperadoresDuplicados();
			resultado =true;
			
			//objdivideFieldsReport.recorrerOperadores(); //TEST
			//this.odivideCamposReport.recorrerEstados(); //TEST
			//objdivideFieldsReport.recorrerEventums();

		} 
		catch(StringIndexOutOfBoundsException    e){
			System.out.println("Fichero IN:\t <ERROR> Linea: "+contadorlineas);
			resultado =false;
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
			resultado =false;
		}
		finally{
			if(resultado==true){
				System.out.println("\t<Carga de datos finalizada>\t<OK>");
			}
			else{
				System.out.println("\t<Carga de datos finalizada>\t<ERROR>");
			}
			return resultado;
		}

	}
	
	
	
	//Update: 16102020
	public String getFechaListado(){
		return this.stFechaLeerCSV;
	}
	
	//Update: 05/10/2020
	public void setFechaCSV(String pFechaInicial, String pFechaFinal){
		this.stFechaInicialCSV =pFechaInicial;
		this.stFechaFinalCSV =pFechaFinal;
	}
	//Update: 05/10/2020
	public String getFechaInicialCSV(){
		return this.stFechaInicialCSV;
	}
	//Update: 05/10/2020
	public String getFechaFinalCSV(){
		return this.stFechaFinalCSV;
	}
	
	//Update: 16102020
	public void mostrarFechas(){
		System.out.println(getFechaListado());
		System.out.println(getFechaInicialCSV());
		System.out.println(getFechaFinalCSV());
	}

	
	//Created: 16102020
	public DivideCamposReport getRegistrosParaCSV(){
		return this.odivideCamposReport;
	}
}
