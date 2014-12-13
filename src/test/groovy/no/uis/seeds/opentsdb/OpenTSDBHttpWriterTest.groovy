package no.uis.seeds.opentsdb

import no.uis.seeds.repository.RecordRepository

class OpenTSDBHttpWriterTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {

    }

    void testPut() {

        RecordRepository.load(RecordRepository.SQL, 100).findAll { it.valid }.each {
            OpenTSDBHttp.instance.put(it)

        }
    }
}
