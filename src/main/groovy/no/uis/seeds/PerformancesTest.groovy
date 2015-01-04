package no.uis.seeds

import no.uis.seeds.model.InTempRecord
import no.uis.seeds.repository.MysqlRecordRepository
import no.uis.seeds.timeseriesdb.Query
import no.uis.seeds.timeseriesdb.TimeSeriesDBHttpApi
import no.uis.seeds.timeseriesdb.kairosdb.KairosdbHttp
import no.uis.seeds.timeseriesdb.opentsdb.OpenTSDBHttp
import org.apache.commons.lang.math.RandomUtils

class PerformancesTest {
    TimeSeriesDBHttpApi kairosDB = KairosdbHttp.instance
    TimeSeriesDBHttpApi openTSDB = OpenTSDBHttp.instance

    static final benchmark = { closure ->
        def start = System.currentTimeMillis()
        closure.call()
        def now = System.currentTimeMillis()
        now - start
    }

    static void main(String[] args) {
        def test = new PerformancesTest()
//        test.testQuery()
        test.testQueryWithTags()
//        test.testInsert()

    }

    void testQueryWithTags() {
        def tags = [floor: [1, 2, 3, 4], locationType: InTempRecord.LocationType.values()*.name()*.toLowerCase(), room: InTempRecord.Room.values()*.name()]
        def randomTags = {
            def n = RandomUtils.nextInt(3)
            def list = [:]
            for (int i = 0; i < n; i++) {
                def key = tags*.getKey().get(i)
                def size = tags[key].size()
                list << [(key):tags[key].get(RandomUtils.nextInt(size))]
            }
            list

        }
        List timeKairo = []
        List timeOpentsdb = []
        for (int i = 0; i < 2000; i++) {
            def query = new Query(start: 1393925311000l + RandomUtils.nextInt(30000), end: 1393999412000l + RandomUtils.nextInt(30000),tags:randomTags())
            timeKairo << benchmark { println kairosDB.query(query) }
            timeOpentsdb << benchmark { println openTSDB.query(query) }
        }
        def process = { it.groupBy { it }.collectEntries { [(it.key): it.value.size()] }.sort { it.key } }

        println process(timeOpentsdb)
        println process(timeKairo)
        writeToFile(process(timeOpentsdb), process(timeKairo))
    }

    void writeToFile(d1, d2) {

        def i = 0
        def s1 = d1.collect { "${it.key}\n" * it.value }.join('\n').replace('\n\n', '\n')
        def s2 = d2.collect { "${it.key}\n" * it.value }.join('\n').replace('\n\n', '\n')

        new File('/Users/maziarkaveh/Downloads/data.dat').text = "id\n$s1"
        new File('/Users/maziarkaveh/Downloads/data2.dat').text = "id\n$s2"
        println "id\n$s1"
        def p = {
            i++
            println "x$i=c(${it*.key.join(',')})";
            println "y$i=c(${it*.value.join(',')})";
        }
        p(d1)

        p(d2)


    }

    void testQuery() {
        List timeKairo = []
        List timeOpentsdb = []
        for (int i = 0; i < 1000; i++) {
            def query = new Query(start: 1393925311000l + RandomUtils.nextInt(30000), end: 1393999412000l + RandomUtils.nextInt(30000))
            timeKairo << benchmark { kairosDB.query(query) }
            timeOpentsdb << benchmark { openTSDB.query(query) }
        }
        def process = { it.groupBy { it }.collectEntries { [(it.key): it.value.size()] }.sort { it.key } }

        println process(timeOpentsdb)
        println process(timeKairo)
    }

    void testInsert() {
//        List<InTempRecord> all = new MysqlRecordRepository(sql: MysqlRecordRepository.DEFAULT_SQL).fetchAll().findAll {
//            it.valid
//        }
        List timeKairo = []
        List timeOpentsdb = []
        def process = { it.groupBy { it }.collectEntries { [(it.key): it.value.size()] }.sort { it.key } }

        new MysqlRecordRepository(sql: MysqlRecordRepository.DEFAULT_SQL, tableName: 'cipsi_seeds_uis_in_temp').forEach { InTempRecord record ->
            if (record?.valid) {
                timeKairo << benchmark { kairosDB.put(record.forKairosdbTsdbJson()) }
                timeOpentsdb << benchmark { openTSDB.put(record.forOpenTsdbJson()) }
            }
            println process(timeOpentsdb)
            println process(timeKairo)
        }


    }

}
/**
 * h1=read.table("/Users/maziarkaveh/Downloads/data.dat", header=TRUE, sep=",")
 h2=read.table("/Users/maziarkaveh/Downloads/data2.dat", header=TRUE,sep=",")
 d1 <- density(h1$id)
 d2 <- density(h2$id)
 plot(d1, main="Comparing query time latency",col="blue",xlim=c(0,500))
 lines(d2, xlim=c(0,500),col="red")
 legend(370.5,0.006, c("OpenTSDB","KairoDB"),  lty=c(1,1) ,lwd=c(2.5,2.5),col=c("red","blue"))



 */


