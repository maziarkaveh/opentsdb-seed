package no.uis.seeds.opentsdb

import no.uis.seeds.model.InTempRecord
import no.uis.seeds.repository.RecordRepository

class OpenTSDBUtilsTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {

    }

    void testWriteToFileForImport() {
        List<InTempRecord> load = new RecordRepository().fetchAll()
        OpenTSDBUtils.writeToFileForImport("${System.getProperty('user.home')}/import.txt",load)
    }
}
