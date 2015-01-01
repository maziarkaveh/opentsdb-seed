package no.uis.seeds.timeseriesdb.opentsdb

import no.uis.seeds.model.InTempRecord
import no.uis.seeds.repository.MysqlRecordRepository

class OpenTSDBUtilsTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {

    }

    void testWriteToFileForImport() {
        List<InTempRecord> load = new MysqlRecordRepository().fetchAll()
        OpenTSDBUtils.writeToFileForImport("${System.getProperty('user.home')}/import.txt",load)
    }
}
