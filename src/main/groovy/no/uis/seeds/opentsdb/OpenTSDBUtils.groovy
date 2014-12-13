package no.uis.seeds.opentsdb

import no.uis.seeds.model.InTempRecord

class OpenTSDBUtils {
    static writeToFileForImport(String path, List<InTempRecord> data) {
        new File(path).text = InTempRecord.batchToRows(data)
    }
}
