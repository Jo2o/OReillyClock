package oreillyclock;

import static java.util.Objects.nonNull;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "OReillyClock";

    private static Activator plugin;

    private TrayItem trayItem;
    private Image image;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        final Display display = Display.getDefault();

        display.asyncExec(() -> {
            image = new Image(display, Activator.class.getResourceAsStream("/icons/sample.png"));
            Tray tray = display.getSystemTray();
            if (nonNull(tray) && nonNull(image)) {
                trayItem = new TrayItem(tray, SWT.NONE);
                trayItem.setToolTipText("Hello Tray!");
                trayItem.setVisible(true);
                trayItem.setText("TrayItem Text!");
                trayItem.setImage(image);
                trayItem.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        Shell shell = new Shell(trayItem.getDisplay(), SWT.NO_TRIM | SWT.ON_TOP); // SWT.APPLICATION_MODAL
                        new ClockWidget(shell, SWT.NONE, new RGB(200, 100, 50));
                        shell.setAlpha(128);
                        shell.setLayout(new FillLayout());
                        shell.pack();
                        shell.open();

                        final Region region = new Region();
                        region.add(circle(50, 50, 50));
                        shell.setRegion(region);

                        shell.addDisposeListener(event -> region.dispose());
                    }
                });
            }
        });
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);

        if (nonNull(trayItem)) {
            Display.getDefault().asyncExec(trayItem::dispose);
        }
        if (nonNull(image)) {
            Display.getDefault().asyncExec(image::dispose);
        }
    }

    private static int[] circle(int r, int offsetX, int offsetY) {
        int[] polygon = new int[8 * r + 4];
        // x^2 + y^2 = r^2
        for (int i = 0; i < 2 * r + 1; i++) {
            int x = i - r;
            int y = (int) Math.sqrt(r * r - x * x);
            polygon[2*i] = offsetX + x;
            polygon[2*i+1] = offsetY + y;
            polygon[8*r - 2*i - 2] = offsetX + x;
            polygon[8 * r - 2 * i - 1] = offsetY - y;
        }
        return polygon;
    }

    public static Activator getDefault() {
        return plugin;
    }
}
