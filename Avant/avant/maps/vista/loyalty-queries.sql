SELECT *, CASE WHEN mes_nacimiento != '' AND mes_nacimiento is not null AND ISNUMERIC(mes_nacimiento) = 0 AND ISNUMERIC(dia_nacimiento) = 1 AND ISNUMERIC(siglo_nacimiento) = 1 AND CAST(siglo_nacimiento as int) > 18 AND  ISNUMERIC(ano_nacimiento) = 1 THEN CAST( (mes_nacimiento + ' ' + dia_nacimiento + ' ' + siglo_nacimiento + ano_nacimiento) as date) ELSE NULL END as fecha_nacimiento, ROW_NUMBER() OVER (ORDER BY created_at) as row_number
FROM
	(SELECT cp.person_id as persona_id
      ,cp.person_firstName as nombre
      ,cp.person_lastName as apellido
      ,cp.person_email as email
      ,cp.person_userName as username
      ,cp.person_mobilePhone as fono_celular
      ,cp.person_address as direccion
      ,cp.person_suburb as comuna
      ,cp.person_town as ciudad
      ,cm.card_cardNumber as rut
      ,cp.person_gender as sexo
      ,cinema.cinemaOperator_headOfficeCode as codigo_cine
      ,cp.person_birthdayDate as dia_nacimiento
      ,cp.person_birthdayMonth as mes_nacimiento
      ,cp.person_centuryOfBirth as siglo_nacimiento
      ,cp.person_yearOfBirth as ano_nacimiento
      ,cp.person_ageInYearsAtCreationDate as edad_encreacion
      ,cp.person_maritalStatus as estado_civil
      ,cp.person_faxphone as fono_fijo
      ,education.educationLevel_name as nivel_educacional
      ,cp.person_householdMembers as n_miembros_hogar
      ,occupation.occupation_name as ocupacion
      ,cp.person_creationDate as created_at
      ,cp.person_lastChangeDate as updated_at
      , ROW_NUMBER() OVER (PARTITION BY cp.person_email ORDER BY cp.person_lastChangeDate desc) as person_row
	FROM cognetic_core_person cp
	JOIN cognetic_members_membership cmm ON cp.person_id = cmm.membership_personid
	JOIN cognetic_members_card cm ON cmm.membership_id=cm.card_membershipid
	LEFT JOIN cognetic_data_cinemaOperator cinema ON cinema.cinemaOperator_id = cp.person_preferredComplexid
	LEFT JOIN cognetic_members_educationLevel education ON education.educationLevel_id = cp.person_educationLevel
	LEFT JOIN cognetic_members_occupation occupation ON occupation.occupation_id = cp.person_occupation
	WHERE cp.person_lastChangeDate > #fecha_desde#
	AND cp.person_email LIKE '%_@_%_.__%' 
	) as X
WHERE person_row = 1


SELECT cinema.cinemaOperator_headOfficeCode as cinema_code, X.*, (spend + tax) as total_amount_paid, DATEPART(weekday, session_time) as session_day, DATEPART(hour, session_time) as session_hour
FROM
	(SELECT 
       	tr.transaction_id as transaccion_id
      	, tr.transaction_membershipid as persona_id
      	, cp.person_email as email
      	, tr.transaction_complexid
      	, tr.transaction_time as bought_time
      	, tr.transaction_cardNumber as rut
      	, tr.transaction_workstationID as workstation_id
      	, tri.transactionItem_sessionTime as session_time
      	, m.movie_code 
      	, m.movie_name
      	, i.item_name as item_name
		, sum(tri.transactionItem_quantity) as quantity
		, sum(tri.transactionItem_spend) as spend
		, sum(tri.transactionItem_tax) as tax
		, tri.transactionItem_cinemaOperator
		, ROW_NUMBER() OVER (ORDER BY tr.transaction_time) as row_number
 	FROM cognetic_data_transaction tr 
   	INNER JOIN cognetic_core_person cp ON tr.transaction_membershipid = cp.person_id
   	INNER JOIN cognetic_data_transactionItem tri ON tri.transactionItem_transactionid=tr.transaction_id
   	INNER JOIN cognetic_data_item i ON tri.transactionItem_itemid = i.item_id
   	INNER JOIN cognetic_rules_movie m ON tri.transactionItem_movieid = m.movie_id
   
	WHERE i.item_itemClassid in (6,4) /*6=TicketType, 4=Unknown; ambas pertenecen a grupo 'BOX OFFICE'*/
	AND cp.person_email LIKE '%_@_%_.__%' 
	AND transaction_complexid is not null
	AND transaction_cardNumber is not null
	AND transaction_time > #fecha_desde#
	AND transaction_time < GETDATE()
	GROUP BY tr.transaction_id, tr.transaction_membershipid, cp.person_email, tr.transaction_complexid, tr.transaction_time, tr.transaction_cardNumber, tr.transaction_workstationID, tri.transactionItem_sessionTime, m.movie_code, m.movie_name, i.item_name, tri.transactionItem_cinemaOperator
  	) as X
JOIN (SELECT * FROM cognetic_data_cinemaOperator ) cinema ON cinema.cinemaOperator_id = X.transactionItem_cinemaOperator