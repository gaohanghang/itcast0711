package cn.ticast.d_processVariables;

import java.io.Serializable;

public class Person implements Serializable{

    private static final long serialVersionUID = 3510248673970180608L;

    private Integer id;//编号
    private String name;//姓名

    private String education;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
