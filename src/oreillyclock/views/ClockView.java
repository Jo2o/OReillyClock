package oreillyclock.views;


import javax.inject.Inject;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;
import oreillyclock.ClockWidget;


/**
 * This sample class demonstrates how to plug-in a new workbench view. The view shows data obtained
 * from the model. The sample creates a dummy model on the fly, but a real implementation would
 * connect to the model available either in this or another plug-in (e.g. the workspace). The view
 * is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be presented in the view. Each
 * view can present the same model objects using different labels and icons, if needed.
 * Alternatively, a single label provider can be shared between views in order to ensure that
 * objects of the same type are presented in the same way everywhere.
 * <p>
 */

public class ClockView extends ViewPart {

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "oreillyclock.views.ClockView";

    @Inject
    IWorkbench workbench;

    @Override
    public void createPartControl(Composite parent) {

        parent.setLayout(new RowLayout(SWT.HORIZONTAL));

        final ClockWidget clock1 = new ClockWidget(parent, SWT.NONE, new RGB(255, 0, 0));
        final ClockWidget clock2 = new ClockWidget(parent, SWT.NONE, new RGB(30, 255, 0));
        final ClockWidget clock3 = new ClockWidget(parent, SWT.NONE, new RGB(50, 0, 255));

        clock1.setLayoutData(new RowData(100, 100));
        clock2.setLayoutData(new RowData(150, 150));
        clock3.setLayoutData(new RowData(230, 230));

        findLeak(parent);
    }

    private void findLeak(Composite parent) {
        Object[] objects = parent.getDisplay().getDeviceData().objects;
        int count = 0;
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof Color) {
                count++;
            }
        }
        System.out.println(">>>>>>>>>>>>> " + count + " COLOR INSTANCES <<<<<<<<<<<<<<<<<<");
    }

    @Override
    public void setFocus() {
        // viewer.getControl().setFocus();
    }

    class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
        @Override
        public String getColumnText(Object obj, int index) {
            return getText(obj);
        }

        @Override
        public Image getColumnImage(Object obj, int index) {
            return getImage(obj);
        }

        @Override
        public Image getImage(Object obj) {
            return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
        }
    }

}
