package oreillyclock.forjfacetree;

import java.time.ZoneId;
import java.util.Map;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;

public class TimeZoneLabelProvider extends LabelProvider {

    private final ISharedImages images;
    private final ImageRegistry imageRegistry;

    public TimeZoneLabelProvider(ISharedImages images, ImageRegistry imageRegistry) {
        this.images = images;
        this.imageRegistry = imageRegistry;
    }

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

    @Override
    public Image getImage(Object element) {
        if (element instanceof Map.Entry) {
            return images.getImage(ISharedImages.IMG_OBJ_FOLDER);

        } else if (element instanceof ZoneId) {
            return imageRegistry.get("sample");
        }

        return super.getImage(element);
    }
}
