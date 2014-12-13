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
        List<InTempRecord> load = RecordRepository.load()
        OpenTSDBUtils.writeToFileForImport('/Users/maziarkaveh/Desktop/import.txt',load)
    }
}
