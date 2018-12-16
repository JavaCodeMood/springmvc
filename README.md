##SpringMVC数据绑定
###绑定基本数据类型
Java基本数据类型int的默认值是0，在使用int进行url传递参数时，参数key是必须写的，其值也只能是int类型的，否则将会报错。  
比如方法：   

    @RequestMapping(value = "baseType.do")    
        @ResponseBody   //返回的数据放到ResponseBody的数据区中    
        public String baseType(int age){    
            return "age:"+age;    
        }    
请求的url地址：http://localhost:8080/baseType.do?age=10，其中参数age是必传的，其值只能是int类型的；   
如果不传，将会报500错误。比如：http://localhost:8080/baseType.do    
如果传递了，但是数据类型是其他类型，将会报400错误，比如：http://localhost:8080/baseType.do?age=abc     

又比如方法：  

    @RequestMapping(value="baseType1.do")   
        @ResponseBody   
        public String baseType1(@RequestParam(value="xage")int age){    
            return "age: " + age;   
        }   
我们可以使用@RequestParam注解来为传入的参数，定义一个别名参数，
@RequestParam（value="",required = true）value参数别名 required 该参数是否必传，默认为true；     
访问的url地址如下：    
http://localhost:8080/baseType1.do?xage=10    
同样xage这个参数也是必传的，其类型为int。

如果是其他基本数据类型也是如此，其中    
byte,short,int的默认值都为0，long的默认值为0L,float的默认值为0.0f，double的默认值为0.0d     
在进行数据绑定时，其参数值必传，其值的类型为其对应的基本数据类型。


###绑定封装数据类型
Java封装类型Integer的默认值为null，在使用Integer进行url数据传递时，参数key可以不传，对应参数的值就会默认为null。
比如方法：   

     @RequestMapping(value = "baseType2.do")    
        @ResponseBody    
        public String baseType2(Integer age){    
            return "age:"+age;    
        }   
 其url地址参数age可以不传，也可以传，对应的url地址如下：
 http://localhost:8080/baseType2.do?age=10    
 http://localhost:8080/baseType2.do    
 但是如果传递了age参数，其值为其他数据类型，将会报400错误，比如：http://localhost:8080/baseType2.do?age=abc   
 
 数据绑定的数值型接受参数可以是基本类型和包装类型，但二者有区别：   
 1、基本类型不可接受空值，会报错。且输入数值必须在指定数据类型的数据范围内   
 2、包装类型则可以接受空值，具有优势，推荐使用，比如传递的参数是年龄、身高、长度、宽度等，具体情况可根据业务需要进行变更。    


###绑定数组类型
Java数组：它是一个具有相同数据类型，固定大小的对象。     
绑定数组类型的方法如下：
    
    @RequestMapping(value = "array.do")    
        @ResponseBody    
        public String array(String[] name){     
            StringBuilder sbf = new StringBuilder();     
            StringBuffer sb = new StringBuffer();     
            for(String item : name){     
                sbf.append(item).append(" ");    
            }    
            return sbf.toString();     
        }     
在使用url地址传递参数时，一次可以传递多个参数值，值与值之间使用&分开，如下：     
http://localhost:8080/array.do?name=Tom&name=Lucy&name=Jim   


###绑定对象类型
这里以绑定User对象为例，绑定的方法如下：    

    @RequestMapping(value="/user")   
    @ResponseBody   
    public String GetUser(User user){    
         return user.toString();    
    }   
使用url传递参数的方式如下：     
http://localhost:8080/user?name=Tom&age=10     
其中name、age都是User对象的属性。       

对象类型的数据绑定，直接请求时对应其属性即可，不必添加前缀，如User类的属性name，则直接在请求中使用name=xxx，而不是使用user.name=xxx的形式。url如下：  
http://localhost:8080/user?name=Tom&age=10  
但如果是想对对象中对象的属性再赋值的话，则需要使用xxx.xxx的形式，如下的contactInfo.phone表示将User类中的ContactInfo类的phone属性赋值。 如下：   
http://localhost:8080/object.do?name=Tom&age=10&contactInfo.phone=10086         

###两个对象同属性进行数据绑定
这里以User对象和Admin对象为例，这两个对象具有相同的属性name，age，这两个对象进行数据绑定的方法如下：    

    @RequestMapping(value = "object.do")   
    @ResponseBody    
    public String object(User user,Admin admin){   
        return user.toString()+" "+admin.toString();    
    }    
 
在这里是有Spring注解@InitBinder来初始化一个对象。      
对于两个不同的类，遇到同名属性的情况，SpringMVC会默认针对所有类的同名属性赋值，如下面url传递的name，age参数将是两个对象共有的：   
http://localhost:8080/object.do?name=Tom&age=10

如果想要区别对待，直接使用xxx.xxx的形式是不行的，还需要在对应的Controller中定义方法，
以 @InitBinder 注解标记属性名，形参使用 WebDataBinder 来定义请求参数前缀，若没加前缀则是共有的。方法定义如下：   

    @InitBinder("user")  //@InitBinder 初始化一个对象    
    public void initUser(WebDataBinder binder){     
        binder.setFieldDefaultPrefix("user.");      
    }      
    @InitBinder("admin")    
    public void initAdmin(WebDataBinder binder){   
        binder.setFieldDefaultPrefix("admin.");    
    }    
使用url传递参数，如下：    
http://localhost:8080/object.do?user.name=Tom&admin.name=Lucy&age=10   
其中age参数是两个对象共用的。
    

###绑定List对象
Java中List集合类型：List是一个有序，可重复的的线性表。    
在绑定List对象数据时，首先要将对象封装为List对象，并设置它的get/set方法，以User对象为例，封装如下：     
private List<User> users;      
然后controller下对应的方法定义如下：   
  
    @RequestMapping(value = "list.do")     
    @ResponseBody    
    public String list(UserListForm userListForm){     
         return "listSize:"+userListForm.getUsers().size() + "  " + userListForm.toString();     
    }   
其中UserListForm是封装的List对象类，使用url传递参数的方式如下：    
http://localhost:8080/list.do?users[0].name=Tom&users[1].name=Lucy

Controller中List参数不能直接传值，需要一个包裹类，类中有需要传的List作为属性和对应的get,set方法。 传值时用users[0].name = Tom    users[1].name = Luce 一定不要跳跃传值，如users[0].name = Tom&users[20].name = Lucy 这样中间的1~19也会占用资源属性值为空。   
http://localhost:8080/list.do?users[0].name=Tom&users[1].name=Lucy&users[20].name=Jim   

###绑定Map对象
Java中Map集合对象：Map是一个基于kay-value键值对的集合类型，它是无序的，其中键key不可重复，值value可以重复。    
在绑定Map对象数据时，首先要将对象封装为Map对象，并设置它的get/set方法，以User对象为例，封装如下：    
 
    private Map<String,User> users;   
  
 然后controller下对应的方法定义如下： 
    
     @RequestMapping(value = "map.do")    
     @ResponseBody    
     public String map(UserMapForm userMapForm){    
         return userMapForm.toString();    
     }  
 其中UserMapForm是封装的Map对象，使用url传递参数的方式如下：
 http://localhost:8080/map.do?users['X'].name=Tom&users['X'].age=10&users['Y'].name=Lucy   
 其中X,Y是key，类型是String类型。


###绑定Set对象
Java中Set集合对象：Set集合无序，且不可重复，因为它重写了hashCode()方法和equals()方法；   
Set集合在实际的应用中，常用于对象的重复判断或者排除重复。   
在SpringMVC中绑定Set数据类型，接口的参数形式和绑定list是类似的，都是通过索引。但是不同的在于，Set必须初始化，它必须先包含了初始化对象，也即是说必须提前手动分配好空间，才能进行赋值，而使用List则没有这个要求。   

另外的一个坑在于，初始化Set时需要留意对象的equals方法，假如我们在提前分配两个对象空间时，两个对象通过equals方法判断为相同，则我们期望的Set的size为2，最后实际因为去重变成了1，导致在数据绑定时很容易出现数组越界的异常。    

Set集合类型：我们一般用来排重 使用Set的时候需要先进行初始化 要使用Set的排重功能必须在对象中覆写hashcode和equals方法。 SpringMVC对Set支持并不太好，初始化进行排重时会导致size变小，致使无法接受更多的数据而抛出异常，所以我们开发一般优先使用List。

在绑定Set对象数据时，首先要将对象封装为Set对象，并设置它的get/set方法，并提供构造方法，封装如下：   

    private Set<User> users;   
    
    private UserSetForm(){   
        users = new LinkedHashSet<User>();   
        users.add(new User());    //定义两个对象    
        users.add(new User());   
    }   
其次还要重写User对象的hashCode()方法和equals()方法   
然后controller下对应的方法定义如下：
    
    @RequestMapping(value = "set.do")   
    @ResponseBody   
    public String set(UserSetForm userSetForm){    
        return userSetForm.toString();   
    }    
其中UserSetForm是封装的Set对象，使用url传递参数的方式如下：   
http://localhost:8080/set.do?users[0].name=Tom&users[1].name=Lucy   
且不可跳范围传值，如下：   
http://localhost:8080/set.do?users[0].name=Tom&users[20].name=Lucy   将会报错。   
 

###绑定XML对象
Java绑定XML对象：XML是一种扩展标记语言，常用来存储或传输数据。   
对于xml类型的数据绑定，需要在方法形参上增加注解 @RequestBody，并且在Post请求时请求头为Content-Type: application/xml。   
这样SpringMVC就会调用对应的解析器去解析，所以我们同时还需要在pom中添加xml解析的相关依赖 spring-oxm ，如下：    
\<dependency>   
   \<groupId>org.springframework\</groupId>   
   \<artifactId>spring-oxm\</artifactId>  
   \<version>4.0.0.RELEASE\</version>  
\</dependency>

将xml对应的实体类进行注解标注，根节点放在类名，并使用name属性设定对应的xml中的根节点名称，其他节点同理。    
对于Admin对象的name，age属性定义为xml，格式如下：
<?xml version="1.0" encoding="UTF-8" ?>
<admin>
    <name>Jim</name>
    <age>16</age>
</admin>
xml 数据绑定：必须在实体类里面加注解@XmlRootElement,在属性上添加XmlElement。定义如下：   

    @XmlRootElement(name="admin")    
    public class Admin {    
    private String name;   
    private Integer age;   

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
    }     
     
ex:@XmlElement(name="age"):此时就会将xml 里面对应的age数据添加到实体类中的age属性中去。    
然后controller下对应的方法定义如下：  

    @RequestMapping(value = "xml.do")  
    @ResponseBody
    public String xml(@RequestBody Admin admin){
       return admin.toString();
    }
url请求地址为：   
http://localhost:8080/xml.do, 请求头为Content-Type: application/xml.   


###绑定Json对象
Java绑定Json对象：JSON 是轻量级的文本数据交换格式,常用于存储和交换文本信息。     
SpringMVC接受http中body的json格式内容为参数，在方法的形参前加上注解 @RequestBody，用以调用解析器进行转换，值得注意的是：    
1、在参数中加注解@RequestBody（ @RequestBody User user）。@RequestBody是把传过来的Json数据反序列化绑定到控制器参数上。    
controller中方法定义如下：   

    @RequestMapping(value = "json.do")
        @ResponseBody
        public String json(@RequestBody User user){
            return user.toString();
        }
  
2、pom.xml文件中引入依赖jar包，如下：  
\<dependency>   
    \<groupId>org.codehaus.jackson\</groupId>   
    \<artifactId>jackson-mapper-asl\</artifactId>   
    \<version>1.9.9\</version>   
\</dependency>   
3、http请求头：Content-Type: application/json。    
url请求地址为：   
http://localhost:8080/json.do
