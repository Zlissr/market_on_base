cdo $$
declare
	dDateStart timestamp := date_trunc('month', now());
	dDateTo timestamp;
	iMonthes integer := 5*12;
	vSql varchar(128);
begin
	for i in 1..iMonthes
	loop
		dDateTo := dDateStart + interval '1 month';
		vSql := 'CREATE TABLE model__pa' ||to_char(dDateStart, 'yyyy_mm') || ' PARTITION OF model FOR VALUES FROM ('''
			|| to_char(dDateStart, 'yyyy.mm.dd hh24:mi:ss') || ''') TO (''' || to_char(dDateTo, 'yyyy.mm.dd hh24:mi:ss') ||''')';
		--RAISE NOTICE '%', vSql;
		execute vSql;
		dDateStart := dDateStart + interval '1 month';
	end loop;
end $$;
