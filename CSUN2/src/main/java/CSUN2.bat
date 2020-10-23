cls
del *.class
javac -cp "javax.mail.jar;" LanzadorReportSemanalCSUN2.java ConfiguracionReportCSUN2.java ReportIn.java ReportSemanalCSUN2_LeerCSV.java DivideCamposReport.java Operador.java CamposEventumCSV.java CamposTiempoCSV.java GenerarCSV.java GenerarEML.java 
REM java LanzadorReportSemanalCSUN2
java -cp "javax.mail.jar;" LanzadorReportSemanalCSUN2