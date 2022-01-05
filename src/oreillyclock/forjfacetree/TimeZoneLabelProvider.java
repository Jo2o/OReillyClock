package oreillyclock.forjfacetree;

import java.time.ZoneId;
import java.util.Map;
import org.eclipse.jface.viewers.LabelProvider;

public class TimeZoneLabelProvider extends LabelProvider {

    @Override
    public String getText(Object element) {

        if (element instanceof Map) {
            return "Time Zones";

        } else if (element instanceof Map.Entry) {
            return ((Map.Entry) element).getKey().toString();

        } else if (element instanceof ZoneId) {
            return ((ZoneId) element).getId().split("/")[1];

        } else {
            return "Unknown type: " + element.getClass();
        }
    }
}
