package oreillyclock;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;

public class TimeZoneComparator implements Comparator<ZoneId> {

    @Override
    public int compare(ZoneId o1, ZoneId o2) {
        return o1.getId().compareTo(o2.getId());
    }

    public static Map<String, Set<ZoneId>> getTimeZones() {
        Supplier<Set<ZoneId>> sortedZones = () -> new TreeSet<>(new TimeZoneComparator());

        return ZoneId.getAvailableZoneIds().stream()
                .filter(zoneId -> zoneId.contains("/"))
                .map(ZoneId::of)
                .collect(
                        groupingBy(
                                zoneId -> zoneId.getId().split("/")[0],
                                TreeMap::new,
                                toCollection(sortedZones)));
    }
}
