package echovibe.Config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
public class GenericDataDto {
    private int responseCode;
    private String responseMessage;
    private Object data = null;
    private List<Object> datalist = new ArrayList<>();
}
