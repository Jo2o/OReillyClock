package oreillyclock.forjfacetree;

import java.util.Collection;
import java.util.Map;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class TimeZoneContentProvider implements ITreeContentProvider {

    @Override
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof Object[]) {
            return (Object[]) inputElement;

        } else {
            return new Object[0];
        }
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof Map) {
            return ((Map) parentElement).entrySet().toArray();

        } else if (parentElement instanceof Map.Entry) {
            return getChildren(((Map.Entry) parentElement).getValue());

        } else if (parentElement instanceof Collection) {
            return ((Collection) parentElement).toArray();

        } else {
            return new Object[0];
        }
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof Map) {
            return !((Map) element).isEmpty();

        } else if (element instanceof Map.Entry) {
            return hasChildren(((Map.Entry) element).getValue());

        } else if (element instanceof Collection) {
            return !((Collection) element).isEmpty();

        }
        return false;
    }
}
