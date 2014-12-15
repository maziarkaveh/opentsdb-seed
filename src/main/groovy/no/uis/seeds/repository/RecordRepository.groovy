package no.uis.seeds.repository

import groovy.sql.Sql
import no.uis.seeds.model.InTempRecord

class RecordRepository {
    static final DEFAULT_TABLE_NAME = 'cipsi_seeds_uis_in_temp'
    static final DEFAULT_SQL        = new DBConfig().buildSqlInstance()
    Sql          sql                = DEFAULT_SQL
    int          size               = 0
    String       tableName          = DEFAULT_TABLE_NAME


    List<InTempRecord> fetchAll() {
        def list = []
        eachRow {
            list << it
        }
        list
    }

    void forEach(Closure closure) {
        eachRow(closure)
    }

    private void eachRow(Closure closure) {
        // group by time,identity
        def query = "select * from cipsi_seeds_uis_in_temp WHERE id > ?  ORDER BY id limit 100000 "
        Integer maxId = 0
        def executeQuery = {
            def oldMaxId = maxId
            sql.eachRow(query, [maxId]) { row ->
                closure(new InTempRecord(row.identity, row.time, row.value))
                maxId = row.id
            }
            maxId != oldMaxId
        }


        while (executeQuery());

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
