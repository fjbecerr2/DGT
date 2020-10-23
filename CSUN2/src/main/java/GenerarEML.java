import java.io.File;
import java.util.ArrayList; // import the ArrayList class
import java.io.IOException;


import java.util.Properties;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;

import javax.mail.BodyPart;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;


import java.io.FileOutputStream;
import java.awt.Desktop;


//Copia al disco
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;

//@Author: Fco J. Becerra
//Created: 21102020
public class GenerarEML{

	//final private String directoryEmailOut ="mailOUT";
	
	private String path;
	private String name;
	private String pathAdjunto;
	private String nameAdjunto;
	private String pathDestinatarios;
	private String nameDestinatarios;
	//private String stReportFinalNameOut ="";
	//private String stReportPathOut;
	private File file;
	
	private ArrayList<String> CabeceraEML =new ArrayList<String>();
	private ArrayList<String> CuerpoEML =new ArrayList<String>();
	
	//¡¡¡¡¡¡MIRAR ESTO!!!!
	private static final String senderEmail = "test@logicbig.com";//change with your sender email
	private static final String senderPassword = "12345678";//change with your sender password

	//Created: 23102020
	private String de;
	private String para;
	private String asunto;
	private String textoMensaje;
	
	//Created: 23102020
	public GenerarEML(String path, String name){
		setPath(path);
		setName(name);
	}
	
	public void GenerarEML(String path, String name, String pathAdjunto, String adjunto){
		setPath(path);
		setName(name);
		setPathAdjunto(pathAdjunto);
		setNameAdjunto(adjunto);
	}
	
	
	//Update: 02/10/2020
	public void setPath(String path){
		this.path =path;
		//setReportPathOut();
	}
	
	//Update: 02/10/2020
	public String getPath(){
		return this.path;
	}

	//Created: 23102020
	public void setName(String name){
		this.name =name;
	}
	//Created: 19102020
	public String getName(){
		return this.name;
	}
	
	//Created: 23102020
	public void setNameAdjunto(String nameAdjunto){
		this.nameAdjunto = nameAdjunto;
	}
	//Created: 23102020
	public String getNameAdjunto(){
		return this.nameAdjunto;
	}
	
	//Created: 23102020
	public void setPathAdjunto(String pathAdjunto){
		this.pathAdjunto =pathAdjunto;
	}
	//Created: 23102020
	public String getPathAdjunto(){
		return this.pathAdjunto;
	}
	
	//Created: 23102020
	public void setDe(String de){
		this.de =de;
	}
	public String getDe(){
		return this.de;
	}
	
	//Created: 23102020
	public void setPara(String para){
		this.para =para;
	}
	public String getPara(){
		return this.para;
	}
	
	//Created: 23102020
	public void setAsunto(String asunto){
		this.asunto =asunto;
	}
	public String getAsunto(){
		return this.asunto;
	}
	
	//Created: 23102020
	public void setTextoMensaje(String textoMensaje){
		this.textoMensaje =textoMensaje;
	}
	public String getTextoMensaje(){
		return this.textoMensaje;
	}
	
	public void generarGenerarMensaje(boolean tieneAdjunto, boolean guardarEmail)throws MessagingException, IOException {
	
		System.out.print("<GENERANDO EMAIL>\t");
		try{
			//Crear mensaje
			Properties props = new Properties();
			//Estos datos están falseados por ser parámetros obligatorios
			props.put("mail.smtp.auth", "true");//Outgoing server requires authentication
			props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
			props.put("mail.smtp.host", "smtp.1and1.com"); //Outgoing server (SMTP) - change it to your SMTP server
			props.put("mail.smtp.port", "587");//Outgoing port

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(senderEmail, senderPassword);
				}
			});
			
			//create message using session
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.de));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.para));
			message.setSubject(this.asunto);
			
			//Cuerpo del mensaje
			//message.setContent(this.textoMensaje, "text/html; charset=utf-8");
			MimeMultipart multipart = new MimeMultipart();
			//Convertir cuerpo en html
			String htmlContent = "<p>"+this.textoMensaje+"</p>";
			final MimeBodyPart  messageBodyPart = new MimeBodyPart();
			// HTML Content
			messageBodyPart.setContent(htmlContent, "text/html;charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);
			// don't forget to add the content to your message.
			message.setContent(multipart);
			
			
			if(tieneAdjunto==true){
				BodyPart texto = new MimeBodyPart();
				texto.setText(this.textoMensaje);
				BodyPart adjunto = new MimeBodyPart();
				//adjunto.setDataHandler(new DataHandler(new FileDataSource(getNameAdjunto())));
				//String nameTMP =;
				adjunto.setDataHandler(new DataHandler(new FileDataSource(getPathAdjunto()+"\\"+getNameAdjunto()))); //Nombre del fichero
				adjunto.setFileName(getNameAdjunto()); //Nombre que se muestra
				MimeMultipart multiParte = new MimeMultipart();
				multiParte.addBodyPart(texto);
				multiParte.addBodyPart(adjunto);
				message.setContent(multiParte);
			}
			
			//Generar el fichero
			file = new File(this.name);
			try (FileOutputStream out = new FileOutputStream(file)) {
				message.writeTo(out);
			}
			
			System.out.println("<OK>\t");
			
			if(guardarEmail==true){
				guardarMensaje();
			}
			
			//Abrir el mensaje
			Desktop.getDesktop().open(file);
		}catch(Exception e){
			System.out.println("<ERROR>\t");
			e.printStackTrace();  
		}
		
		
	}
	
	public void guardarMensaje(){
		try{
			System.out.print("<GUARDANDO MENSAJE>\t");
			//Guardar el mensae
			//File origen = new File(this.file);
			File destino = new File(getPath()+"\\"+getName());
			InputStream in = new FileInputStream(this.file);
			OutputStream out = new FileOutputStream(destino);
			byte[] buf = new byte[1024];
			int len;

			while ((len = in.read(buf)) > 0) {
			  out.write(buf, 0, len);
			}
			in.close();
			out.close();
			System.out.println("<OK>");
		}catch(Exception e){
			System.out.println("<ERROR>\t");
			e.printStackTrace();  
		}
	
	}
	
	
	

}
