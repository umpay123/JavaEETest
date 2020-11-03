package org.sang;

import com.alibaba.fastjson.JSON;
import org.sang.bean.Person;
import org.sang.util.AesUtils;
import org.sang.util.Md5Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SpringBootApplication
public class Test20Thymeleaf1Application {

    public static void main(String[] args) {
        SpringApplication.run(Test20Thymeleaf1Application.class, args);
    }

    @RequestMapping("/")
    public String index(Model model) {
        Person single = new Person("aa", 11);
        List<Person> people = new ArrayList<>();
        Person p1 = new Person("zhangsan", 11);
        Person p2 = new Person("lisi", 22);
        Person p3 = new Person("wangwu", 33);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        model.addAttribute("singlePerson", single);
        model.addAttribute("people", people);
        return "myinput";
    }
    @RequestMapping("/createDate")
    @ResponseBody
    public String createDate(HttpServletRequest request) {
        System.out.println(request.getParameter("str"));
        Map<String,Object> map = JSON.parseObject(request.getParameter("str"),Map.class);
        String aesKey = (String) map.get("aesKey");
        if (StringUtils.isEmpty(aesKey)){
            return "aesKey不能为空";
        }
        String md5Key = (String) map.get("md5Key");
        if (StringUtils.isEmpty(md5Key)){
            return "md5Key不能为空";
        }
        String signContent = (String) map.get("signContent");
        if (StringUtils.isEmpty(signContent)){
            return "signContent不能为空";
        }
        String md5Sign= Md5Utils.digestByMd5(signContent+md5Key);
        String signature = md5Sign;
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (String key:map.keySet()){
            if (key.equals("signContent")||key.equals("aesKey")||key.equals("md5Key")){
                continue;
            }
            sb.append("\""+key+"\":\"");
            sb.append(map.get(key)+"\",");
        }
        sb.append("\"signature\":\""+md5Sign+"\"}");
        System.out.println("报文："+sb.toString());
        String desContentJson = AesUtils.encrypt(sb.toString(),aesKey);
        System.out.println("加密后报文："+desContentJson);
        return desContentJson;
    }
    @RequestMapping("/decrypt")
    @ResponseBody
    public String decrypt(HttpServletRequest request) {
       String aesKey = request.getParameter("aesKey");
       String signContent = request.getParameter("signContent");
        System.out.println("加密内容："+signContent+",加密密钥："+aesKey);
        if (StringUtils.isEmpty(aesKey)){
            return "aesKey不能为空";
        }
        if (StringUtils.isEmpty(signContent)){
            return "signContent不能为空";
        }
        String result =   AesUtils.decrypt(signContent,aesKey);
        System.out.println("解密内容："+result);
        return result;
    }
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }
//
//    @Bean
//    public Connector httpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        //Connector监听的http的端口号
//        connector.setPort(8080);
//        connector.setSecure(false);
//        //监听到http的端口号后转向到的https的端口号
//        connector.setRedirectPort(8443);
//        return connector;
//    }
}
