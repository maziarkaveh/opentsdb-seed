package no.uis.seeds.timeseriesdb

class Query {
    Long                start
    Long                end
    String              aggregate = 'avg'
    String              metric    = 'seeds.temp.in'
    Map<String, String> tags      = [:]


    Map toOpenTSDBFormat() {
        assert start
        assert aggregate
        assert metric
        def tags = tags.collect { "$it.key=$it.value" }.join(',')
        def subQuery = "$aggregate:10m-$aggregate:$metric{$tags}"
        [start: start, end: end, m: subQuery].findAll { it.value }
    }

    String toKairosDBFormat() {
        assert start
        assert aggregate
        assert metric
        def end = end ? "\"end_absolute\": $end," : ''
        def tags = !tags ? '' : """"tags": {
                            ${tags.collect {"\"$it.key\":[\"$it.value\"]"}.join(',')}
                        },"""
        """
            {
                "start_absolute": $start,
                $end
                "metrics": [
                    {
                        $tags
                        "name": "$metric",
                        "aggregators": [
                            {
                                "name": "$aggregate",
                                "sampling": {
                                    "value": 10,
                                    "unit": "minutes"
                                }
                            }
                        ]
                    }
                ]
            }
        """
    }
}
