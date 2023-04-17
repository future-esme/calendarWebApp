package utm.tmps.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TagCreateDTO {
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @Size(max = 50)
    private String iconName;

    @NotNull
    @Size(max = 50)
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "TagCreateDTO{" +
            "name='" + name + '\'' +
            ", iconName='" + iconName + '\'' +
            ", color='" + color + '\'' +
            '}';
    }
}
