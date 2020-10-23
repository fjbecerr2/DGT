import java.io.File;

//@Author: Fco. J. Becerra
//Created: 22102020
public class ConfiguracionReportCSUN2{
	
	private String nombreFicheroCSVEntrada;
	private String nombreFicheroCSVSalida;
	private String nombreFicheroCSVDireccionesEmail;
	private String nombreFicheroEmailEml;
	
	private char separadorCSV;
	
	private String pathFicheroEntrada;
	private String pathFicheroSalida;
	private String pathFicheroEmail;
	private String pathRecursos;
		
	public ConfiguracionReportCSUN2(){};
	
	public void setnombreFicheroCSVEntrada(String  nombreFicheroCSVEntrada){ this.nombreFicheroCSVEntrada =nombreFicheroCSVEntrada;};
	public void setnombreFicheroCSVSalida(String  nombreFicheroCSVSalida){ this.nombreFicheroCSVSalida =nombreFicheroCSVSalida;};
	public void setnombreFicheroCSVDireccionesEmail(String  nombreFicheroCSVDireccionesEmail){ this.nombreFicheroCSVDireccionesEmail =nombreFicheroCSVDireccionesEmail;};
	public void setnombreFicheroEmailEml(String  nombreFicheroEmailEml){ this.nombreFicheroEmailEml =nombreFicheroEmailEml;}
	public void setpathFicheroEntrada(String  pathFicheroEntrada){ this.pathFicheroEntrada =pathFicheroEntrada;}
	public void setpathFicheroSalida(String  pathFicheroSalida){ this.pathFicheroSalida =pathFicheroSalida;}
	public void setpathFicheroEmail(String  pathFicheroEmail){ this.pathFicheroEmail =pathFicheroEmail;}
	public void setpathRecursos(String  pathRecursos){ this.pathRecursos =pathRecursos;}
	
	public void setseparadorCSV(char separadorCSV) { this.separadorCSV =separadorCSV;}
	
	public String getnombreFicheroCSVEntrada(){ return this.nombreFicheroCSVEntrada;}
	public String getnombreFicheroCSVSalida() {return this.nombreFicheroCSVSalida;}
	public String getnombreFicheroCSVDireccionesEmail() {return this.nombreFicheroCSVDireccionesEmail;}
	public String getnombreFicheroEmailEml() {return this.nombreFicheroEmailEml;}
	public String getpathFicheroEntrada() { return this.pathFicheroEntrada;}
	public String getpathFicheroSalida() { return this.pathFicheroSalida;}
	public String getpathFicheroEmail() { return this.pathFicheroEmail;}
	public String getpathRecursos() { return this.pathRecursos;}
	
	
	public boolean getExisteFichero(String path, String fichero){
		boolean reportExiste =false;
		
		File fFichero =new File(path+"\\"+fichero);
		
		if (fFichero.exists()) {
			reportExiste =true;
		}
		
		return reportExiste;
	}
	
	public boolean getExistePath(String path){
		boolean reportExiste =false;
		
		File fFichero =new File(path);
		
		if (fFichero.exists()) {
			reportExiste =true;
		}
		
		return reportExiste;
	}
	
	
	public void chequearConfiguracion(){
		System.out.println(" Chequeando Configuracion ");
		System.out.println("--------------------------");
		System.out.println("<RUTAS>");
		System.out.println("\tRuta Entrada:\t"+getExistePath(getpathFicheroEntrada()));
		System.out.println("\tRuta Salida:\t"+getExistePath(getpathFicheroSalida()));
		System.out.println("\tRuta email:\t"+getExistePath(getpathFicheroEmail()));
		System.out.println("\tRuta recursos:\t"+getExistePath(getpathRecursos()));
		System.out.println("<FICHEROS>");
		System.out.println("\tFichero entrada:\t"+getExisteFichero(getpathFicheroEntrada(),getnombreFicheroCSVEntrada()));
		System.out.println("\tFichero direcciones:\t"+getExisteFichero(getpathRecursos(),getnombreFicheroCSVDireccionesEmail()));
	}
		
	
	
	public String toString()
	{
		return "<CONFIGURACION>\n\t"+getnombreFicheroCSVEntrada()+"\n\t"+getnombreFicheroCSVSalida()+"\n\t"+getnombreFicheroEmailEml()+"\n\t"+getnombreFicheroCSVDireccionesEmail()+"\n\t"+getpathFicheroEntrada()+"\n\t"+getpathFicheroSalida()+"\n\t"+getpathFicheroEmail()+"\n\t"+getpathRecursos();
	}
}
