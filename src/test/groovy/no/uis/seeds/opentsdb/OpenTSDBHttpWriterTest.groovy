package no.uis.seeds.opentsdb

import no.uis.seeds.repository.RecordRepository

class OpenTSDBHttpWriterTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {

    }

    void testPut() {

        new RecordRepository(sql:RecordRepository.DEFAULT_SQL).fetchAll().findAll { it.valid }.each {
            OpenTSDBHttp.instance.put(it)

        }
    }
}
