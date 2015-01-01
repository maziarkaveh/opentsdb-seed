package no.uis.seeds.timeseriesdb

import no.uis.seeds.model.InTempRecord

interface TimeSeriesDBHttpApi {
    void put(InTempRecord inTempRecord)
    def query(Query query)
}