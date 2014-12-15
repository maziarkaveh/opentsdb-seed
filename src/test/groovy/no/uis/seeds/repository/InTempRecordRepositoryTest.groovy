package no.uis.seeds.repository

import no.uis.seeds.model.InTempRecord

class InTempRecordRepositoryTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {
    }

    void testLoad() {
        def load = new RecordRepository().fetchAll()
        println InTempRecord.batchToJson(load)
        println InTempRecord.batchToRows(load)

    }

    void testForEach() {
        def load = new RecordRepository().forEach { InTempRecord InTempRecord ->
            println InTempRecord
        }

    }
}
