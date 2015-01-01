package no.uis.seeds.timeseriesdb.opentsdb

import no.uis.seeds.repository.MysqlRecordRepository
import no.uis.seeds.timeseriesdb.Query

class OpenTSDBHttpWriterTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {

    }

    void testPut() {

        new MysqlRecordRepository(sql: MysqlRecordRepository.DEFAULT_SQL).fetchAll().findAll { it.valid }.each {
            OpenTSDBHttp.instance.put(it)

        }
    }

    void testQuery() {
        println OpenTSDBHttp.instance.query(new Query(start: 1393925311000l, end: 1393999412000l))
    }
}
