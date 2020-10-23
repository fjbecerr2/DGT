-- CONSULTA USUARIOS EVENTUMS CSU-N2 
	-- UPDATE:15/10/2020
	-- CONSULTA DE EXTRACCIÓN PARA LISTADO SEMANAL
	-- EVENTUM ASIGNADOS [INFORMADOR] <OK>
	SELECT 
		U.usr_id, U.usr_full_name
		, I.iss_id, I.iss_summary 
		, GR.grp_name
		, 'INFORMADOR' AS ORIGEN
		,  0 AS ttr_time_spent
		, ST.sta_title
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	-- INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	GROUP BY I.iss_id
	ORDER BY U.usr_id, I.iss_id
	
		-- LINEA CSV DESCARTADA
		/*SELECT 
		CONCAT(U.usr_id,";",U.usr_full_name,';',I.iss_id,';')
		, I.iss_summary
		, CONCAT(';',GR.grp_name,';INFORMADOR;','0;', ST.sta_title)
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	-- INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	GROUP BY I.iss_id
	ORDER BY U.usr_id, I.iss_id*/
	
	
	
	-- EVENTUM ASIGNADOS [ASIGNACION]
	SELECT 
		IU.isu_usr_id, U.usr_full_name
		, I.iss_id, I.iss_summary 
		, GR.grp_name
		, 'ASIGNACION' AS ORIGEN
		,  0 AS ttr_time_spent
		, ST.sta_title
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND IU.isu_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	GROUP BY I.iss_id
	ORDER BY U.usr_id, I.iss_id


	-- EVENTUM ASIGNADOS TIEMPO [ASIGNACIONT]
	SELECT 
		U.usr_id, U.usr_full_name
		, I.iss_id, I.iss_summary 
		, GR.grp_name
		, 'ASIGNACIONT' AS ORIGEN
		,  0 AS ttr_time_spent
		, ST.sta_title
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id) -- << ASIGNACION
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	INNER JOIN time_tracking TTR ON (I.iss_id = TTR.ttr_iss_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	-- AND TTR.ttr_usr_id IN (239) -- CSU_N2 
	GROUP BY I.iss_id
	ORDER BY U.usr_id, I.iss_id
	
	-- EVENTUM SIN ASIGNAR EN LOS QUE SE HA INTERVENIDO [TIEMPO]
	SELECT 
		TTR.ttr_usr_id, U.usr_full_name
		, TTR.ttr_iss_id, I.iss_summary 
		, GR.grp_name
		, 'TIEMPO' AS ORIGEN
		,  0 AS ttr_time_spent
		, ST.sta_title
	FROM time_tracking TTR 
	INNER JOIN issue I ON (TTR.ttr_iss_id = I.iss_id)
	INNER JOIN user U ON (TTR.ttr_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	WHERE TTR.ttr_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND TTR.ttr_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	GROUP BY TTR.ttr_iss_id, TTR.ttr_usr_id
	ORDER BY TTR.ttr_usr_id,TTR.ttr_iss_id
	
	-- EVENTUM CON NOTA ASOCIADA [NOTA]
	SELECT 
		U.usr_id, U.usr_full_name
		, I.iss_id, I.iss_summary 
		, GR.grp_name
		, 'NOTA' AS ORIGEN
		,  0 AS ttr_time_spent
		, ST.sta_title
	FROM issue I
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	INNER JOIN note NT ON (I.iss_id=NT.not_iss_id)
	INNER JOIN user U ON (NT.not_usr_id = U.usr_id)
	WHERE NT.not_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND NT.not_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	GROUP BY NT.not_iss_id
	ORDER BY U.usr_id, NT.not_iss_id
	
	
	-- CONSULTAS DE TIEMPO INVERTIDO
	SELECT 
		TTR.ttr_usr_id
		, TTR.ttr_iss_id
		,  SUM(TTR.ttr_time_spent)
	FROM time_tracking TTR 
	INNER JOIN issue I ON (TTR.ttr_iss_id = I.iss_id)
	WHERE TTR.ttr_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND TTR.ttr_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	GROUP BY TTR.ttr_iss_id, TTR.ttr_usr_id
	ORDER BY TTR.ttr_usr_id,TTR.ttr_iss_id
	
	 
	
	
	-- /////// ***** DESCARTADAS ***** /////////
	
	-- EVENTUMS ASIGNADOS A LOS OPERADORES N2 <OK>
	SELECT U.usr_id, U.usr_full_name
	, I.iss_id, I.iss_summary 
	, ST.sta_title, 'ASIGNADO' AS ORIGEN, GR.grp_name, 0 AS ttr_time_spent
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	-- AND I.iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	AND I.iss_usr_id IN (240) -- CSU_N2 
	GROUP BY I.iss_id
	ORDER BY U.usr_id, I.iss_id

	-- EVENTUMS ASIGNADOS POR INTERVENCIÓN <OK>
	SELECT U.usr_id, U.usr_full_name
	, I.iss_id, I.iss_summary 
	, ST.sta_title, 'TRABAJADO' AS ORIGEN, GR.grp_name
	, TTR.ttr_time_spent
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	INNER JOIN time_tracking TTR ON (I.iss_id = TTR.ttr_iss_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	-- AND I.iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	-- AND TTR.ttr_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	AND I.iss_usr_id IN (240) -- CSU_N2 
	AND TTR.ttr_usr_id IN (240) -- CSU_N2 
	GROUP BY I.iss_id
	ORDER BY U.usr_id, I.iss_id
	
	-- EVENTUM SIN ASIGNAR EN LOS QUE SE HA INTERVENIDO
	SELECT TTR.ttr_usr_id, U.usr_full_name
	, I.iss_id, I.iss_summary 
	, ST.sta_title, 'TRABAJADO' AS ORIGEN, GR.grp_name
	, TTR.ttr_time_spent
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id)
	INNER JOIN `group` GR ON (I.iss_grp_id=GR.grp_id)
	INNER JOIN time_tracking TTR ON (I.iss_id = TTR.ttr_iss_id)
	WHERE TTR.ttr_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND TTR.ttr_usr_id IN (240) 
	GROUP BY TTR.ttr_iss_id
	ORDER BY U.usr_id, I.iss_id
	
	

-- UPDATE: 05/10/2020
SELECT COUNT(DISTINCT iss_usr_id) AS NUM_OPERADORES
	FROM issue
	WHERE iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	AND iss_created_date BETWEEN DATE('2020-09-28') AND DATE('2020-09-30') 
	ORDER BY iss_usr_id

-- UPDATE: 05/10/2020
SELECT DISTINCT iss_usr_id AS OPERADOR
	FROM issue
	WHERE iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	AND iss_created_date BETWEEN DATE('2020-09-28') AND DATE('2020-09-30') 
	ORDER BY iss_usr_id
	
-- UPDATE: 05/10/2020
SELECT  DISTINCT U.usr_id, U.usr_full_name
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	WHERE iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
		AND I.iss_created_date BETWEEN DATE('2020-09-28') AND DATE('2020-09-30') 
	ORDER BY I.iss_id
	
-- UPDATE: 05/10/2020
SELECT  COUNT(DISTINCT I.iss_id)
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	WHERE iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
		AND I.iss_created_date BETWEEN DATE('2020-09-28') AND DATE('2020-09-30') 
	ORDER BY U.usr_id, I.iss_id

-- UPDATE: 05/10/2020
SELECT  U.usr_id
	, I.iss_id, I.iss_summary 
	, ST.sta_title
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	WHERE iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
		AND I.iss_created_date BETWEEN DATE('2020-09-28') AND DATE('2020-09-30') 
	ORDER BY U.usr_id, I.iss_id
	
	
-- UPDATE: 13102020
	-- EVENTUM ASIGNADOS
SELECT  U.usr_id, U.usr_full_name
	, I.iss_id, I.iss_summary 
	, ST.sta_title
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	WHERE iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
		AND I.iss_created_date BETWEEN DATE('2020-09-28') AND DATE('2020-10-04') 
	ORDER BY U.usr_id, I.iss_id

	-- EVENTUM PARTICIPADOS	
	SELECT *
		FROM time_tracking
	WHERE ttr_iss_id IN(
	100462,100975, 101001,101107,101146,101354,101426,101535)
	and ttr_usr_id = 240
	-- GROUP BY his_usr_id
	ORDER BY ttr_iss_id

	
	
-----
-- DISEÑO DE CAMPOS
	ISS_ID
	ISS_USR_ID
	ASIGNADA
	
	SELECT I.iss_id, I.iss_usr_id, 'IDD' AS ORIGEN
	FROM issue I
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	-- AND I.iss_usr_id = 240
	
	SELECT TTR.ttr_iss_id, TTR.ttr_usr_id, 'TTR' AS ORIGEN
	FROM time_tracking TTR
	WHERE TTR.ttr_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND TTR.ttr_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	-- AND TTR.ttr_usr_id = 240

	
	SELECT *
	FROM issue I
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (239, 240, 243, 245, 256, 272, 298, 333) -- CSU_N2 
	
	
	-- CONSULTA EVENTUM 
	SELECT  I.iss_id, I.iss_usr_id, 'ASIGNADO' AS ORIGEN
	FROM issue I
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (240) -- CSU_N2 
	
	-- CONSULTA DE ASIGNADOS id=id
	SELECT I.iss_id, I.iss_usr_id, 'ASIGNADO' AS ORIGEN
	FROM issue I
	INNER JOIN issue_user IU ON (I.iss_id=IU.isu_iss_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (240) -- CSU_N2 
	
	-- CONSULTA TRABAJADOS id=id
	SELECT I.iss_id, I.iss_usr_id, 'ASIGNADO' AS ORIGEN
	FROM issue I
	INNER JOIN time_tracking TTR ON (I.iss_id = ttr_iss_id)
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (240) -- CSU_N2 
	GROUP BY I.iss_id

	-- CONSULTA TRABAJADOS usr=usr y fecha
	SELECT I.iss_id, I.iss_usr_id, 'ASIGNADO' AS ORIGEN 
	FROM issue I 
	INNER JOIN time_tracking TTR ON (I.iss_usr_id = TTR.ttr_usr_id) 
	WHERE I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	AND I.iss_usr_id IN (240) 
	GROUP BY I.iss_id
	
	-- CONSULTA TRABAJADOS usr=usr y fecha trabajo
	SELECT TTR.ttr_iss_id, TTR.ttr_usr_id, 'ASIGNADO' AS ORIGEN 
	FROM time_tracking  TTR
	WHERE TTR.ttr_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27')
	AND TTR.ttr_usr_id IN (240) 
	GROUP BY TTR.ttr_iss_id
	
	
	SELECT * FROM time_tracking
	WHERE ttr_iss_id =101107

	
----- PRUEBAS ----
SELECT  U.usr_id, U.usr_full_name
	, I.iss_id, I.iss_summary 
	, ST.sta_title
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	WHERE iss_usr_id IN (240) -- CSU_N2 
		AND I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	ORDER BY U.usr_id, I.iss_id
	
	
<OK> 100462 [1552][Reorganización de DFS en Servicios Centrales][CSUN2] Puesta en marcha carpeta "Gratificaciones"  
	Informador	Sergio Vizcaino Nieto
	Asignación	Edison Javier Yanez Quinatoa
	Alberto Lorido Ramirez	0h 50m <<<PARTICIPADO>>>
<OK>100975 SO_FILESYSTEM_DISCO_E_REAL  
	Informador	José Emilio González González
	Asignación	Alberto Lorido Ramirez <<<ASIGNACION>>>
	Alberto Lorido Ramirez	2h 20m	<<<PARTICIPADO>>>
101001 Caída de conectividad con DGT19EXGUA (192.168.204.226)  
	Informador	Alberto Lorido Ramirez
	Asignación	Juan José Puga Romero del Hombrebueno
<OK>101107 SO_FILESYSTEM_DISCO_E_REAL  
	Informador	José Luis González Cuenca
	Asignación	Alberto Lorido Ramirez <<<ASIGNACION>>>
	Alberto Lorido Ramirez	0h 50m <<<PARTICIPADO>>>
101146 Sin conectividad con DGT94ALC11 (10.50.94.132) [RMON HP Switch]  
	Informador	Alberto Lorido Ramirez
	Asignación	Juan Ramón Castaños Villacieros
101354 Pérdida de Comunicaciones en JPT Granada  
	Informador	Alberto Lorido Ramirez
	Asignación	Eduardo García González
<OK>101426 The interface is disconnected - JPT VIGO  
	Informador	Alberto Lorido Ramirez
	Asignación	Eduardo García González
	Alberto Lorido Ramirez	2h 50m <<<PARTICIPADO>>>
101535 Pérdida de Comunicaciones en JPT Huelva 
	Informador	Alberto Lorido Ramirez
	Asignación	
	
	Nuevos Casos Asignados:  2
	Proceso:              1
	Cerrada:              7
	Total de Casos:         8
	
	SELECT * 
		FROM issue I
	WHERE I.iss_id IN(
	101001, 101146, 101354,101535
	,100462,100975, 101107,101426
	)
	
	
	<<<ASIGNACION>>
	SELECT * 
		FROM `issue_user`
	WHERE isu_iss_id IN(
	100462,100975, 101001,101107,101146,101354,101426,101535)
	
	
	SELECT * 
		FROM `issue_history`
	WHERE his_iss_id IN(
	100462,100975, 101001,101107,101146,101354,101426,101535)
	and his_usr_id = 240
	
	SELECT *
		FROM time_tracking
	WHERE ttr_iss_id IN(
	100462,100975, 101001,101107,101146,101354,101426,101535)
	and ttr_usr_id = 240
	-- GROUP BY his_usr_id
	ORDER BY ttr_iss_id
	
	
	-- PRUEBA
	SELECT *
		FROM time_tracking
	WHERE 
	I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	and ttr_usr_id = 240
	-- GROUP BY his_usr_id
	ORDER BY ttr_iss_id
	GROUP BY 
	
	
	SELECT I.iss_usr_id , I.iss_id, I.iss_summary , I.iss_created_date, I.iss_closed_date 
	FROM issue I 
	LEFT INNER JOIN time_tracking TT ON (I.iss_id=TT.ttr_iss_id)
	WHERE I.iss_usr_id IN (240) -- CSU_N2 
	AND I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	GROUP BY I.iss_usr_id 
	
	
	SELECT  U.usr_id, U.usr_full_name
	, I.iss_id, I.iss_summary 
	, ST.sta_title,  I.iss_created_date, I.iss_closed_date
	FROM issue I
	INNER JOIN user U ON (I.iss_usr_id = U.usr_id)
	INNER JOIN status ST ON (I.iss_sta_id = ST.sta_id)
	WHERE iss_usr_id IN (240) -- CSU_N2 
		AND I.iss_created_date BETWEEN DATE('2020-09-21') AND DATE('2020-09-27') 
	ORDER BY U.usr_id, I.iss_id
	
	
SELECT * FROM issue
WHERE iss_id in (
100958, 101068,101542
,101001,101146,101354,101426

)
AND iss_usr_id IN (240) -- CSU_N2 

PARA LOS QUE NO DEBEN SALIR
iss_pre_id = 1165
iss_pri_id = 37 o 38

SELECT iss_id, iss_pre_id, pre_title
FROM issue
inner join project_release on (issue.iss_pre_id=project_release.pre_id)
WHERE iss_id in (
100958, 101068,101542
,101001,101146,101354,101426
)
AND iss_usr_id IN (240) -- CSU_N2 


SELECT iss_id, iss_pre_id, pre_title
FROM issue
inner join project_release on (issue.iss_pre_id=project_release.pre_id)
WHERE iss_id IN(
	100462,100975, 101001,101107,101146,101354,101426,101535)
	)
