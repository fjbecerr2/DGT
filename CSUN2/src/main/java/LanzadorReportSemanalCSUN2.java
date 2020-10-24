//@Authot: Francisco J. Becerra
//Update: 15102020
public class LanzadorReportSemanalCSUN2{
	public static void main (String [ ] args) {
		System.out.println ("------------------------");
        System.out.println ("CSU Report Semanal CSU N2");
		System.out.println ("Version: 4.5");
		System.out.println ("Update Version: 23102020");
		System.out.println ("------------------------");
		
				
		//Configurar aplicacion
		ConfiguracionReportCSUN2 configuracionReportCSUN2 =new ConfiguracionReportCSUN2();
		
		configuracionReportCSUN2.setnombreFicheroCSVEntrada("ReportSemanalCSUN2_IN.csv");
		configuracionReportCSUN2.setnombreFicheroCSVSalida("ReportSemanalCSUN2_OUT");
		configuracionReportCSUN2.setnombreFicheroCSVDireccionesEmail("EmailList.csv");
		configuracionReportCSUN2.setnombreFicheroEmailEml("Email_ReportSemanalCSUN2.eml");
		configuracionReportCSUN2.setpathFicheroEntrada("C:\\Users\\25587143\\Desktop\\Operativas_SQL\\CSUN2\\src\\main\\java\\reportIN");
		configuracionReportCSUN2.setpathFicheroSalida("C:\\Users\\25587143\\Desktop\\Operativas_SQL\\CSUN2\\src\\main\\java\\reportOUT");
		configuracionReportCSUN2.setpathFicheroEmail("C:\\Users\\25587143\\Desktop\\Operativas_SQL\\CSUN2\\src\\main\\java\\mailOUT");
		configuracionReportCSUN2.setpathRecursos("C:\\Users\\25587143\\Desktop\\Operativas_SQL\\CSUN2\\src\\main\\java\\resources");
		configuracionReportCSUN2.setseparadorCSV(';');
		
		
		configuracionReportCSUN2.chequearConfiguracion();
		System.out.println(configuracionReportCSUN2.toString());
		
		//Fichero de Entrada
		ReportIn ficheroEntrada =new ReportIn(configuracionReportCSUN2.getpathFicheroEntrada(),configuracionReportCSUN2.getnombreFicheroCSVEntrada());
		//Fichero de direcciones
		ReportIn ficheroDirecciones =new ReportIn(configuracionReportCSUN2.getpathRecursos(),configuracionReportCSUN2.getnombreFicheroCSVDireccionesEmail());
		
		boolean ficheroVacio =false;
		if (ficheroEntrada.getFicheroVacio()==true){
			System.out.println("Fichero entrada\t <ERROR> <Fichero vacio>");
			ficheroVacio =true;
		}
		
		if (ficheroDirecciones.getFicheroVacio()==true){
			System.out.println("Fichero direcciones\t <ERROR> <Fichero vacio>");
			ficheroVacio =true;
		}
		
		if(ficheroVacio==true){
			System.out.println("Carga anulada");
		}
		else{
			System.out.println("\nCargando fichero de datos");
			System.out.println("-------------------------");
			ReportSemanalCSUN2_LeerCSV lineasCSV =new ReportSemanalCSUN2_LeerCSV();
			lineasCSV.leerLineasCSV(ficheroEntrada.getFile());
			
			GenerarCSV generarCSV =new GenerarCSV(configuracionReportCSUN2.getpathFicheroSalida(), configuracionReportCSUN2.getnombreFicheroCSVSalida());
			generarCSV.generarFicheroCSVSalida();
			generarCSV.getFechas(lineasCSV.getFechaInicialCSV(),lineasCSV.getFechaInicialCSV());
			generarCSV.getDatosParaCSV(lineasCSV.getRegistrosParaCSV());
			configuracionReportCSUN2.setnombreFicheroCSVSalida(generarCSV.getName());
			
			//Generar el email
			GenerarEML generaEML =new GenerarEML(configuracionReportCSUN2.getpathFicheroEmail(), configuracionReportCSUN2.getnombreFicheroEmailEml());
			
			//Generador de email
			try{
				generaEML.setDe("fbecerra.altran@d.es");
				generaEML.setPara("merino.altran@d.es");
				generaEML.setAsunto("Prueba email automatico");
				generaEML.setTextoMensaje("Mensaje del email");
				
				//generaEML.generarGenerarMensaje(false); //Sin adjunto
				generaEML.setPathAdjunto(configuracionReportCSUN2.getpathFicheroSalida());
				generaEML.setNameAdjunto(configuracionReportCSUN2.getnombreFicheroCSVSalida());
				generaEML.generarGenerarMensaje(true, true); //Con adjunto
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}

    } 
	
	
}
