import java.io.File;

//Author: Fco. J. Becerra
//Update: 15102020
public class ReportIn{
	
	private String path;
	private String name;

	private File file;
	
	public ReportIn(){ //getPath();
	};
	
	//Created: 22102020
	public ReportIn(String path, String file){
		setPath(path);
		setName(file);
		setFile(path,file);
	};
	
	//Created: 22102020
	public void setPath(String path){ this.path =path; }
	public void setName(String name){ this.name =name; }
	public String getPath() { return this.path; }
	public String getName() { return this.name; }
	
	
	public void setFile(String path,String file){ this.file =new File(path+"\\"+file); }
	public File getFile() { return this.file;}
	
	
	//Update: 16102020
	public boolean getExisteReportIn(){
		boolean reportExiste =false;
		
		String stfile = getPath()+"\\"+getName();
		
		this.file =new File(stfile);
		
		if (this.file.exists()) {
			reportExiste =true;
		}
		
		return reportExiste;
	}
	
	//Created: 16102020
	public boolean getFicheroVacio(){
		boolean ficheroVacio =true;
		
		try{
			if(this.file.length()>0){
				ficheroVacio =false;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			return ficheroVacio;
		}
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
	/*public void setReportPath(String pReportPath){
		this.stReportPath =pReportPath;
		//setReportPathIn();
		//this.stReportPathIn =pReportPath;
	}*/

	//Created: 15102020
	/*public void setReportPathIn(){
		this.stReportPathIn = stReportPath+"\\"+stInDirectory;
		//this.stReportPathIn = stReportPath;
	}*/
	
	//Update: 02/10/2020
	/*public String getReportPath(){
		return this.stReportPath;
	}*/
	
	//Created: 15102020
	/*public String getReportPathIn(){
		return this.stReportPathIn;
	}*/
	
	//Created: 15102020
	/*public void visualizarPathIn(){
		System.out.println("PathIn: "+getReportPathIn());
	}*/

	//Update: 02/10/2020
	/*public void setReportNameIn(String pReportName){
		this.stReportNameIn =pReportName;
	}*/
	//Update: 02/10/2020
	/*public String getReportNameIn(){
		return this.stReportNameIn;
	}	*/
	
	//Created: 15102020
	/*public void visualizarNombreReportIn(){
		System.out.println("FicheroIN: "+getReportNameIn());
	}*/
	

	
	
	
	
	//Created: 16102020
	/*public File getFicheroCSVIn(){
		return this.fReportFileIn;
	}*/
	
}
