package no.uis.seeds.kairosdb

import groovy.transform.Memoized
import groovy.util.logging.Slf4j
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import no.uis.seeds.model.InTempRecord

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.POST

@Slf4j
class KairosdbHttp {
    final String url

    private KairosdbHttp(String url) {
        this.url = url
    }

    @Memoized
    static KairosdbHttp getInstance(String url = 'http://localhost:8022/api/v1/datapoints') {
        new KairosdbHttp(url)
    }

    void put(String jsonBody) {
        new HTTPBuilder(url).request(POST, JSON) { request ->
            body = jsonBody
            response.success = { HttpResponseDecorator response ->

            }
        }
    }

    void put(InTempRecord inTempRecord) {
        new HTTPBuilder(url).request(POST, JSON) { request ->
            body = inTempRecord.forKairosdbTsdbJson()
            response.success = { HttpResponseDecorator response ->
                log.info inTempRecord.formattedDate
            }

        }
    }
}
