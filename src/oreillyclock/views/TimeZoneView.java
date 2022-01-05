package oreillyclock.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.part.ViewPart;
import oreillyclock.ClockWidget;
import oreillyclock.TimeZoneComparator;

public class TimeZoneView extends ViewPart {

    @Override
    public void createPartControl(Composite parent) {

        CTabFolder tabs = new CTabFolder(parent, SWT.BOTTOM);

        TimeZoneComparator.getTimeZones().forEach((region, zones) -> {

            CTabItem item = new CTabItem(tabs, SWT.NONE);
            item.setText(region);

            ScrolledComposite scrolledComposite = new ScrolledComposite(tabs, SWT.H_SCROLL | SWT.V_SCROLL);

            Composite clocks = new Composite(scrolledComposite, SWT.NONE);

            item.setControl(scrolledComposite);

            scrolledComposite.setContent(clocks);

            clocks.setLayout(new RowLayout());
            clocks.setBackground(clocks.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

            Point size = clocks.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            scrolledComposite.setMinSize(size);
            scrolledComposite.setExpandHorizontal(true);
            scrolledComposite.setExpandVertical(true);


            RGB rgb = new RGB(120, 120, 120);

            zones.forEach(zone -> {
                Group group = new Group(clocks, SWT.SHADOW_ETCHED_IN);
                group.setText(zone.getId().split("/")[1]);
                group.setLayout(new FillLayout());
                new ClockWidget(group, SWT.NONE, rgb).setZoneId(zone);
            });
        });

        tabs.setSelection(0);
    }

    @Override
    public void setFocus() {
        // TODO Auto-generated method stub
    }
}
