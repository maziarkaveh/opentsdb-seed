package no.uis.seeds.repository

import groovy.sql.Sql
import no.uis.seeds.model.InTempRecord

class RecordRepository {
    static final SQL = new DBConfig().buildSqlInstance()

    static List<InTempRecord> load(Sql sql = SQL, int size = 0) {
        sql.rows("select * from cipsi_seeds_uis_in_temp group by time,identity order by time asc" + (size ? " limit $size" : '')).collect {
            new InTempRecord(it.identity, it.time, it.value)
        }
    }

    static class DBConfig {
        String dbName     = 'seeds'
        String dbServer   = '127.0.0.1'
        String dbPort     = '3306'
        String dbUserName = 'root'
        String dbPassword = ''
        String dbDriver   = 'com.mysql.jdbc.Driver'


        Sql buildSqlInstance() {
            Sql.newInstance("jdbc:mysql://$dbServer:$dbPort/$dbName?createDatabaseIfNotExist=true&amp;", dbUserName,
                    dbPassword, dbDriver)
        }

    }

}
