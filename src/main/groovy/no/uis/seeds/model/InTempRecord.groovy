package no.uis.seeds.model

import groovy.transform.Memoized
import groovy.transform.ToString
import groovy.util.logging.Slf4j

import java.sql.Timestamp
import java.text.SimpleDateFormat


@ToString(includePackage = false, includeNames = true)
@Slf4j
class InTempRecord {
    Room   room
    Long   timestamp
    Number value


    InTempRecord(Integer identify, Timestamp timestamp, Number value) {
        this.room = Room.findByIdentify(identify)
        this.timestamp = timestamp.time
        this.value = value
    }

    String getMetric() {
        'seeds.temp.in'
    }

    Map getTags() {
        room?.toMap()
    }

    String getTagsAsJson() {
        """
            {
                "room": "${room.room}",
                "floor": ${room.floor},
                "locationType": "${room.locationType.name().toLowerCase()}"
            }
        """
    }

    boolean isValid() {
        this.room && timestamp && value
    }

    String toRow() {
        "$metric $timestamp $value room=${room.room} floor=${room.floor} locationType=${room.locationType.name().toLowerCase()}"
    }

    String forOpenTsdbJson() {
        assert valid: "Not valid values for $this"
        """
        {
            "metric": "$metric",
            "timestamp": $timestamp,
            "value": $value,
            "tags":  $tagsAsJson
        }
        """
    }
  String forKairosdbTsdbJson() {
        assert valid: "Not valid values for $this"
        """
        {
            "name": "$metric",
            "timestamp": $timestamp,
            "value": $value,
            "tags":  $tagsAsJson
        }
        """
    }

    String getFormattedDate() {
        new SimpleDateFormat().format(new Date(timestamp))
    }

    static String batchToRows(List<InTempRecord> inTempRecordList) {
        log.info "all =${inTempRecordList.size()}"
        def valid = inTempRecordList.findAll { it.valid }
        log.info "valid =${valid.size()}"
        valid.collect { it.toRow() }.join('\n')
    }

    static String batchToJson(List<InTempRecord> inTempRecordList) {
        def join = inTempRecordList.findAll { it.valid }.collect { it.forOpenTsdbJson() }.join(',')
        "[$join\n]"
    }


    static enum LocationType {
        CONFERENCE_ROOM,
        OFFICE,
        CORRIDOR,
        CLASSROOM,
        AUDITORIUM
    }

    static enum Room {
        E_101(identity: 27, node: '00:13:A2:00:40:BA:B7:EF', room: 'KE-E101', floor: 1, locationType: LocationType.AUDITORIUM),
        E_102(identity: 33, node: '00:13:A2:00:40:BA:B7:F5', room: 'KE-E102', floor: 1, locationType: LocationType.AUDITORIUM),
        E_162(identity: 39, node: '00:13:A2:00:40:B4:B7:F6', room: 'KE-E162', floor: 1, locationType: LocationType.CLASSROOM),
        E_164(identity: 45, node: '00:13:A2:00:40:BA:B7:F1', room: 'KE-E164', floor: 1, locationType: LocationType.AUDITORIUM),
        E_166(identity: 51, node: '00:13:A2:00:40:B4:B7:D9', room: 'KE-E166', floor: 1, locationType: LocationType.AUDITORIUM),
        E_260(identity: 74, node: '00:13:A2:00:40:BA:21:E8', room: 'KE-E260', floor: 2, locationType: LocationType.CORRIDOR),
        E_260B(identity: 69, node: '00:13:A2:00:40:BA:21:CA', room: 'KE-E260B', floor: 2, locationType: LocationType.CORRIDOR),
        E_262(identity: 57, node: '00:13:A2:00:40:BA:22:21', room: 'KE-E262', floor: 2, locationType: LocationType.CLASSROOM),
        E_264(identity: 63, node: '00:13:A2:00:40:BA:21:EB', room: 'KE-E264', floor: 2, locationType: LocationType.CLASSROOM),
        E_270(identity: 109, node: '00:13:A2:00:40:BA:22:1F', room: 'KE-E270', floor: 2, locationType: LocationType.CLASSROOM),
        E_272A(identity: 119, node: '00:13:A2:00:40:BA:21:D6', room: 'KE-E272A', floor: 2, locationType: LocationType.CLASSROOM),
        E_362(identity: 129, node: '00:13:A2:00:40:BA:21:FC', room: 'KE-E362', floor: 3, locationType: LocationType.OFFICE),
        E_364(identity: 139, node: '00:13:A2:00:40:BA:21:E0', room: 'KE-E364', floor: 3, locationType: LocationType.OFFICE),
        E_374(identity: 179, node: '00:13:A2:00:40:BA:21:DD', room: 'KE-E374', floor: 3, locationType: LocationType.CONFERENCE_ROOM),
        E_423A(identity: 259, node: '00:13:A2:00:40:BA:21:FF', room: 'KE-E423A', floor: 4, locationType: LocationType.OFFICE),
        E_423B(identity: 264, node: '00:13:A2:00:40:BA:21:F4', room: 'KE-E423B', floor: 4, locationType: LocationType.OFFICE),
        E_425A(identity: 289, node: '00:13:A2:00:40:BA:23:54', room: 'KE-E425A', floor: 4, locationType: LocationType.OFFICE),
        E_425B(identity: 294, node: '00:13:A2:00:40:BA:23:42', room: 'KE-E425B', floor: 4, locationType: LocationType.OFFICE),
        E_427(identity: 304, node: '00:13:A2:00:40:BA:23:4E', room: 'KE-E427', floor: 4, locationType: LocationType.OFFICE),
        E_429(identity: 314, node: '00:13:A2:00:40:BA:23:57', room: 'KE-E429', floor: 4, locationType: LocationType.CONFERENCE_ROOM),
        E_460(identity: 369, node: '00:13:A2:00:40:BA:23:65', room: 'KE-E460', floor: 4, locationType: LocationType.CORRIDOR),
        E_460A(identity: 399, node: '00:13:A2:00:40:BA:23:64', room: 'KE-E460A', floor: 4, locationType: LocationType.CORRIDOR),
        E_462(identity: 374, node: '00:13:A2:00:40:BA:23:66', room: 'KE-E462', floor: 4, locationType: LocationType.CLASSROOM),
        E_462B(identity: 379, node: '00:13:A2:00:40:BA:23:6E', room: 'KE-E462B', floor: 4, locationType: LocationType.CLASSROOM),
        E_464(identity: 384, node: '00:13:A2:00:40:BA:23:6F', room: 'KE-E464', floor: 4, locationType: LocationType.CLASSROOM),
        E_466(identity: 389, node: '00:13:A2:00:40:BA:23:87', room: 'KE-E466', floor: 4, locationType: LocationType.CLASSROOM),
        E_468(identity: 404, node: '00:13:A2:00:40:BA:23:74', room: 'KE-E468', floor: 4, locationType: LocationType.CLASSROOM),
        E_470(identity: 409, node: '00:13:A2:00:40:BA:23:82', room: 'KE-E470', floor: 4, locationType: LocationType.CLASSROOM),
        E_472(identity: 424, node: '00:13:A2:00:40:BA:23:60', room: 'KE-E472', floor: 4, locationType: LocationType.CLASSROOM)
        Integer      identity
        String       node
        String       room
        Integer      floor
        LocationType locationType

        @Memoized
        static findByIdentify(Integer identity) {
            values().find { it.identity == identity }
        }

        Map toMap() {
            [room: room, floor: floor, locationType: LocationType.locationType.name()]
        }
    }
}