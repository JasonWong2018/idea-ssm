package example;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class Dom4jDemo {

    public static void main(String[] args) {

    }

    @Test
    public void readXml() throws DocumentException {
        //创建saxReader对象
        SAXReader reader = new SAXReader();
        //读取xml文件 转换成Document对象
        Document document = reader.read(new File("E:\\idea-ssm\\ssm-manage\\ssm-web\\test\\example\\dom4jDemo.xml"));
        //获取根节点元素对象
        Element element = document.getRootElement();
        //遍历所有的元素节点
        elementMethod(element);

    }

    private void elementMethod(Element node) {
        //获取element第一个且只能获取一个
        Element toUserName = node.element("class");
        System.out.println(toUserName.getText());

        List<Element> elements = node.elements();
        for (Element element : elements) {
            if(element.getName().equals("user")){

                Element other = element.element("other");
                Iterator iterator = other.elementIterator();
                while (iterator.hasNext()){
                    Element sElement = (Element) iterator.next();
                    System.out.println("-->"+sElement.getText());
                }

                String name = element.element("name").getText();
                String age = element.element("age").getText();
                System.out.println(name);
                System.out.println(age);
            }else{
                System.out.println(element.getText());
            }
        }
    }
}
