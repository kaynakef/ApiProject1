package test.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CommentsPojo {


    private int code;
    private Map<String, Object> meta;
    private List<Map<String, Object>> data;





}
