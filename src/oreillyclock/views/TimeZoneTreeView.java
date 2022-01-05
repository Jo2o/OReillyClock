package oreillyclock.views;

import javax.annotation.PostConstruct;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class TimeZoneTreeView {

    private TreeViewer treeViewer;

    @PostConstruct
    public void create(Composite parent) {
        treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
    }

}
