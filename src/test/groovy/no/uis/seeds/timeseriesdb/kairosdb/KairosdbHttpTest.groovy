package no.uis.seeds.timeseriesdb.kairosdb

import no.uis.seeds.repository.MysqlRecordRepository
import no.uis.seeds.timeseriesdb.Query
import no.uis.seeds.timeseriesdb.opentsdb.OpenTSDBHttp

class KairosdbHttpTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {

    }

    void testPut() {

        new MysqlRecordRepository().fetchAll().findAll { it.valid }.each {
            KairosdbHttp.instance.put(it)

        }
    }

    void testQuery() {
        println KairosdbHttp.instance.query(new Query(start: 1393925311000l, end: 1393999412000l))
    }
}
