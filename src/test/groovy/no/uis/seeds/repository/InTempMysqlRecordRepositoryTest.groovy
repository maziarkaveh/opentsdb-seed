package no.uis.seeds.repository

import no.uis.seeds.model.InTempRecord

class InTempMysqlRecordRepositoryTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {
    }

    void testLoad() {
        def load = new MysqlRecordRepository().fetchAll()
        println InTempRecord.batchToJson(load)
        println InTempRecord.batchToRows(load)

    }

    void testForEach() {
        def load = new MysqlRecordRepository().forEach { InTempRecord InTempRecord ->
            println InTempRecord
        }

    }
}
