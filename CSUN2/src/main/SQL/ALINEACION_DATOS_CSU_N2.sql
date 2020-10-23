-- EVENTUMS ASIGNADOS A LOS OPERADORES N2 <OK>
	SELECT U.usr_id, U.usr_full_name
	, I.iss_id, I.iss_summary 
	, ST.sta_title, 'ASIGNADO' AS ORIGEN, GR.grp_name, 0 AS ttr_time_spent
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	-- INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	-- AND I.iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	AND I.iss_usr_id IN (240) -- CSU_N2 
	-- GROUP BY I.iss_id
	ORDER BY U.usr_id, I.iss_id
	
-- CONSULTAS DE CONTROL DE ALINEACIÓN DE DATOS
	-- CONSULTA DE USUARIOS DE CSU-N2
	SELECT usr_id, usr_full_name
	FROM  user
	WHERE usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 A FECHA 10/2020 //COMPROBAR

	239;Jesus Galende Calzada
	240;Alberto Lorido Ramirez
	243;Sergio Moron Romero
	245;Ruben Diez Romero
	256;Edison Javier Yanez Quinatoa
	272;Daniel Criado Redondo
	298;Edinson Lopez Saldarriaga
	333;Mark Alan Culley García
	
	-- EVENTUM ASIGNADOS (INFORMADOR)
	SELECT I.iss_id, I.iss_usr_id, GR.grp_name,  "Query 1" as QUERY
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	-- INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (333) -- CSU_N2 
	GROUP BY I.iss_id
	ORDER BY U.usr_id, I.iss_id
	
	-- EVENTUM ASIGNADOS (ASIGNADO)
	SELECT I.iss_id, IU.isu_usr_id, GR.grp_name, "Query 2" as QUERY
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND IU.isu_usr_id IN (333) -- CSU_N2 
	-- GROUP BY I.iss_id
	ORDER BY U.usr_id, I.iss_id
	
	
	-- EVENTUM ASIGNADOS TIEMPO
	SELECT I.iss_id, I.iss_usr_id, GR.grp_name, "Query 3" as QUERY
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id) -- << ASIGNACION
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	INNER JOIN time_tracking TTR ON (I.iss_id = TTR.ttr_iss_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (333) -- CSU_N2 
	-- AND TTR.ttr_usr_id IN (239) -- CSU_N2 
	GROUP BY I.iss_id
	ORDER BY U.usr_id, I.iss_id
	
	-- EVENTUM SIN ASIGNAR EN LOS QUE SE HA INTERVENIDO
	SELECT TTR.ttr_iss_id, TTR.ttr_usr_id, GR.grp_name, "Query 4" as QUERY
	FROM time_tracking TTR 
	INNER JOIN issue I ON (TTR.ttr_iss_id = I.iss_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	WHERE TTR.ttr_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND TTR.ttr_usr_id IN (333) 
	GROUP BY TTR.ttr_iss_id
	ORDER BY TTR.ttr_iss_id
	
	-- EVENTUM CON NOTA ASOCIADA
	SELECT NT.not_iss_id, NT.not_usr_id, GR.grp_name,  "Query 5" as QUERY
	FROM issue I
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	INNER JOIN note NT ON (I.iss_id=NT.not_iss_id)
	INNER JOIN user U ON (NT.not_usr_id = U.usr_id)
	WHERE NT.not_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND NT.not_usr_id IN (333) 
	GROUP BY NT.not_iss_id
	ORDER BY NT.not_iss_id
	






