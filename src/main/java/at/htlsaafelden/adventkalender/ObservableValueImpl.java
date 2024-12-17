package at.htlsaafelden.adventkalender;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObservableValueImpl<T> implements ObservableValue<T> {
    private List<ChangeListener<? super T>> changeListeners = new ArrayList<>();
    private List<InvalidationListener> invalidationListeners = new ArrayList<>();

    private T value;

    public ObservableValueImpl() {
        this.value = null;
    }

    public ObservableValueImpl(T value) {
        this.value = value;
    }

    @Override
    public void addListener(ChangeListener<? super T> changeListener) {
        this.changeListeners.add(changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> changeListener) {
        this.changeListeners.remove(changeListener);
    }

    @Override
    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        T oldValue = this.value;
        T newValue = value;

        if(Objects.deepEquals(oldValue, newValue)) {
            return;
        }

        this.value = value;

        this.changeListeners.forEach((changeListener) -> changeListener.changed(this, oldValue, newValue));
        this.invalidationListeners.forEach((invalidationListener -> invalidationListener.invalidated(this)));
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        this.invalidationListeners.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        this.invalidationListeners.remove(invalidationListener);
    }
}
