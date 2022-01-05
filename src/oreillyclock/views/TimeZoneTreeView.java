package oreillyclock.views;

import javax.annotation.PostConstruct;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import oreillyclock.TimeZoneComparator;
import oreillyclock.forjfacetree.TimeZoneContentProvider;
import oreillyclock.forjfacetree.TimeZoneLabelProvider;

public class TimeZoneTreeView {

    private TreeViewer treeViewer;

    @PostConstruct
    public void create(Composite parent) {
        treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        treeViewer.setLabelProvider(new TimeZoneLabelProvider());
        treeViewer.setContentProvider(new TimeZoneContentProvider());
        treeViewer.setInput(new Object[] {TimeZoneComparator.getTimeZones()});
    }

    @Focus
    public void focus() {
        treeViewer.getControl().setFocus();
    }
}
