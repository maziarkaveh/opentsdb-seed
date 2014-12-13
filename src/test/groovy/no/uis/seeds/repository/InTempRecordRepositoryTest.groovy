package no.uis.seeds.repository

import no.uis.seeds.model.InTempRecord

class InTempRecordRepositoryTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {
    }

    void testLoad() {
        def load = RecordRepository.load(RecordRepository.SQL, 10)
        println InTempRecord.batchToJson(load)
        println InTempRecord.batchToRows(load)

    }
}
