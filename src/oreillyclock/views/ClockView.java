package oreillyclock.views;


import java.time.ZoneId;
import javax.inject.Inject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;
import oreillyclock.ClockWidget;

public class ClockView extends ViewPart {

    public static final String ID = "oreillyclock.views.ClockView";

    private Combo timeZonesCombo;

    @Inject
    IWorkbench workbench;

    @Override
    public void createPartControl(Composite parent) {

        findLeak(parent);

        parent.setLayout(new RowLayout(SWT.HORIZONTAL));

        final ClockWidget clock1 = new ClockWidget(parent, SWT.NONE, new RGB(255, 0, 0));
        final ClockWidget clock2 = new ClockWidget(parent, SWT.NONE, new RGB(30, 255, 0));
        final ClockWidget clock3 = new ClockWidget(parent, SWT.NONE, new RGB(50, 0, 255));

        clock1.setLayoutData(new RowData(100, 100));
        clock2.setLayoutData(new RowData(150, 150));
        clock3.setLayoutData(new RowData(230, 230));

        createTimeZonesCombo(parent);
        registerTimeZonesListener(clock3);
    }

    private void createTimeZonesCombo(Composite parent) {
        timeZonesCombo = new Combo(parent, SWT.DROP_DOWN);
        timeZonesCombo.setVisibleItemCount(5);
        for (String zone : ZoneId.getAvailableZoneIds()) {
            timeZonesCombo.add(zone);
        }
    }

    private void registerTimeZonesListener(ClockWidget clock) {
        timeZonesCombo.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                clock.setZoneId(ZoneId.of(timeZonesCombo.getText()));
                clock.redraw();
            }
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                clock.setZoneId(ZoneId.systemDefault());
                clock.redraw();
            }
        });
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
        timeZonesCombo.setFocus();
    }
}
