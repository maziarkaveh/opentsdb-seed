package no.uis.seeds.kairosdb

import no.uis.seeds.repository.RecordRepository

class KairosdbHttpTest extends GroovyTestCase {
    void setUp() {
        super.setUp()

    }

    void tearDown() {

    }

    void testPut() {

        new RecordRepository().fetchAll().findAll { it.valid }.each {
            KairosdbHttp.instance.put(it)

        }
    }

}
