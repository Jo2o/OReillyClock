package oreillyclock.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import oreillyclock.TimeZoneComparator;
import oreillyclock.forjfacetree.TimeZoneContentProvider;
import oreillyclock.forjfacetree.TimeZoneLabelProvider;

public class TimeZoneTreeView {

    @Inject
    private ISharedImages images;

    private TreeViewer treeViewer;

    @PostConstruct
    public void create(Composite parent) {

        var resourceManager = JFaceResources.getResources();
        var localResourceManager = new LocalResourceManager(resourceManager, parent);
        var imageRegistry = new ImageRegistry(localResourceManager);

        imageRegistry.put("sample", ImageDescriptor.createFromURL(getClass().getResource("/icons/sample.png")));

        treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        treeViewer.setLabelProvider(
                new DelegatingStyledCellLabelProvider(new TimeZoneLabelProvider(images, imageRegistry)));
        treeViewer.setContentProvider(new TimeZoneContentProvider());
        treeViewer.setInput(new Object[] {TimeZoneComparator.getTimeZones()});
    }

    @Focus
    public void focus() {
        treeViewer.getControl().setFocus();
    }
}
