package com.xzm.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Bradish7Y on 15/8/18.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        new Test().object2Json();
    }

    public void object2Json() throws Exception{
        User user = new User();
        user.setUser_name("小民");
        user.setEmail("xiaomin@sina.com");
        user.setAge(20);

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthday(dateformat.parse("1996-10-01"));

        /**
         * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
         * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
         * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
         * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
         * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
         * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
         */
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        //User类转JSON
        //输出结果：{"name":"小民","age":20,"birthday":844099200000,"email":"xiaomin@sina.com"}
        String json = mapper.writeValueAsString(user);
        System.out.println(json);

        //Java集合转JSON
        //输出结果：[{"name":"小民","age":20,"birthday":844099200000,"email":"xiaomin@sina.com"}]
        List<User> users = Lists.newArrayList() ;
        users.add(user);
        String jsonlist = mapper.writeValueAsString(users);

        System.out.println(jsonlist);
    }

    public void json2Object() throws Exception{
        String json = "{\"User_name\":\"小民\",\"age\":20,\"birthday\":844099200000,\"email\":\"xiaomin@sina.com\"}";

        /**
         * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
         */
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);
        System.out.println(user);
    }
}
