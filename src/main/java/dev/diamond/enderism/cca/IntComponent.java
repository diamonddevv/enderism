package dev.diamond.enderism.cca;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface IntComponent extends Component {
    int getValue();
    void setValue(int i);
}
