package oreillyclock.views;


import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;


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

    private TableViewer viewer;

    @Override
    public void createPartControl(Composite parent) {
        final Canvas clock = new Canvas(parent, SWT.NONE); // custom drawing => Canvas
        clock.addPaintListener(this::drawClock);

        Runnable redraw = () -> {
            while (!clock.isDisposed()) { // extends Resources have to be disposed
                // clock.redraw(); // this generates exception
                clock.getDisplay().asyncExec(clock::redraw); // running on UI thread
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        new Thread(redraw, "TickTock").start();
    }

    private void drawClock(PaintEvent paintEvent) {
        paintEvent.gc.drawArc(paintEvent.x, paintEvent.y, paintEvent.width - 1, paintEvent.height - 1, 0, 360);
        drawSecondHand(paintEvent);
    }

    private void drawSecondHand(PaintEvent paintEvent) {
        int seconds = LocalTime.now().getSecond();
        int arc = (15 - seconds) * 6 % 360;
        Color blue = paintEvent.display.getSystemColor(SWT.COLOR_BLUE);
        paintEvent.gc.setBackground(blue);
        paintEvent.gc.fillArc(paintEvent.x, paintEvent.y, paintEvent.width - 1, paintEvent.height - 1, arc - 1, 2);
    }

    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
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
