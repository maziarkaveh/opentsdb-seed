package no.uis.seeds.repository

import no.uis.seeds.model.InTempRecord

interface RecordRepository {
    List<InTempRecord> fetchAll()
    void forEach(Closure closure)
}
