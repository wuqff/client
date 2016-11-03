package com.huawei.wuqf;

//@Data
public class ESUser {
    private String name;

    private int id;

    private int age;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
//

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getJsonString(ObjectMapper mapper)
//    {
//        String js = null;
//        try
//        {
//            js = mapper.writeValueAsString(this);
//        }
//        catch (JsonProcessingException e)
//        {
//            e.printStackTrace();
//        }
//        return js;
//    }
}
