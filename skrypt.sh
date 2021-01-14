#!/bin/bash
DERBY_BIN="/home/student/JavaTools/db-derby-10.14.2.0-bin/bin"
aab=$(
${DERBY_BIN}/ij<<EOF
connect 'jdbc:derby://localhost:1527/WM;create=true;user=WM;password=WM';
select count(*) from WM.ACCOUNT;
exit;
EOF
)
aab=$(($(echo -e "$aab" | tail -n 4 | head -n 1)))

if [ $aab = 0 ]; then
	${DERBY_BIN}/ij<<EOF
		connect 'jdbc:derby://localhost:1527/WM;create=true;user=WM;password=WM';
		run './target/classes/initDB.sql';
		exit;
EOF
fi;
