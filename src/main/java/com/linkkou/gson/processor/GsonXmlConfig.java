package com.linkkou.gson.processor;

import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.HashMap;

/**
 * 自定义Gson配置
 * @author lk
 * @version 1.0
 */
public class GsonXmlConfig {

    private String DefaultCom = null;
    private boolean DefaultGroup = true;
    private HashMap<String, String> Groups = new HashMap<>();

    public GsonXmlConfig() {
        final URL resource = GsonProcessor.class.getClassLoader().getResource("gson.xml");
        if (null != resource) {
            //1.创建DocumentBuilderFactory对象
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document d = builder.parse(resource.openStream());
                getNodeGroups(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getNodeGroups(Document d) {
        NodeList sList = d.getElementsByTagName("groups");
        final int length = sList.getLength();
        if (length > 0) {
            final Node item = sList.item(0);
            final String Default = ((DeferredElementImpl) item).getAttribute("default");
            if (!Default.isEmpty()) {
                DefaultGroup = Boolean.valueOf(Default);
            }
            getNodeGroupsChildNodes(item);
        }
    }


    private void getNodeGroupsChildNodes(Node item) {
        final NodeList childNodes = item.getChildNodes();
        final int length = childNodes.getLength();
        for (int i = 0; i < length; i++) {
            final Node item1 = childNodes.item(i);
            if ("default".equals(item1.getNodeName())) {
                getNodeGroupsDefault(item1);
            }
            if ("group".equals(item1.getNodeName())) {
                getNodeGroupsGroup(item1);
            }
        }
    }


    private void getNodeGroupsDefault(Node node) {
        final String bean = ((DeferredElementImpl) node).getAttribute("bean");
        if (!bean.isEmpty()) {
            DefaultCom = bean;
        }
    }

    private void getNodeGroupsGroup(Node node) {
        final String title = ((DeferredElementImpl) node).getAttribute("title");
        final String bean = ((DeferredElementImpl) node).getAttribute("bean");
        Groups.put(title, bean);
    }


    public String getDefaultCom() {
        return DefaultCom;
    }

    public HashMap<String, String> getGroups() {
        return Groups;
    }

    public Boolean getDefaultGroup() {
        return DefaultGroup;
    }
}
