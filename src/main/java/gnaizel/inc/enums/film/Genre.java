package gnaizel.inc.enums.film;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Genre implements Serializable {
    private int id;
    private String name;
}