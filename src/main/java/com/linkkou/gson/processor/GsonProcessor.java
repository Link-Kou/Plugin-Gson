package com.linkkou.gson.processor;


import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import com.sun.tools.javac.util.Pair;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.*;


/**
 * 基于JDK动态代码生成
 *
 * @author lk
 * @version 1.0
 * @date 2019/9/2 19:09
 *
 */
@SupportedAnnotationTypes({"com.linkkou.gson.processor.GsonAutowired"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GsonProcessor extends AbstractProcessor {

    private static final Class<GsonAutowired> GSON_AUTOWIRED = GsonAutowired.class;
    private Trees trees;
    private TreeMaker make;
    private Name.Table names;
    private Context context;
    private Elements mElementsUtils;
    private GsonXmlConfig gsonXmlConfig = new GsonXmlConfig();

    /**
     * 初始化
     *
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementsUtils = processingEnv.getElementUtils();
        trees = Trees.instance(processingEnv);
        context = ((JavacProcessingEnvironment)
                processingEnv).getContext();
        make = TreeMaker.instance(context);
        names = Names.instance(context).table;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 依据相关注解解析
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(GSON_AUTOWIRED.getCanonicalName());
    }

    /**
     * {@inheritDoc}
     *
     * @param annotations
     * @param roundEnv
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //从注解中找到所有的类,以及对应的注解的方法
        List<TypeElement> targetClassMap = findAnnoationElement(roundEnv);
        //生成每一个类和方法
        for (TypeElement item : targetClassMap) {
            //获取包名
            String packageName = mElementsUtils.getPackageOf(item).getQualifiedName().toString();
            //获取类名称
            String className = item.getSimpleName().toString();

            JCTree tree = (JCTree) trees.getTree(item);
            TreeTranslator visitor = new Inliner();
            tree.accept(visitor);
        }
        return true;
    }


    /**
     * 查询所有带有{@link GsonAutowired HTTPRequestTest注解的}
     *
     * @param roundEnvironment
     * @return
     */
    private List<TypeElement> findAnnoationElement(RoundEnvironment roundEnvironment) {
        List<TypeElement> targetClassMap = new ArrayList<>();
        //找到所有跟AnDataCollect注解相关元素
        Collection<? extends Element> anLogSet = roundEnvironment.getElementsAnnotatedWith(GSON_AUTOWIRED);
        //遍历所有元素
        for (Element e : anLogSet) {
            if (e.getKind() != ElementKind.FIELD) {
                continue;
            }
            //此处找到的是类的描述类型，因为我们的AnDataCollect的注解描述是method的所以closingElement元素是类
            TypeElement enclosingElement = (TypeElement) e.getEnclosingElement();

            //对类做一个缓存
            targetClassMap.add(enclosingElement);
        }
        return targetClassMap;
    }

    /**
     * 文本构建FieldAccess对象
     */
    private JCTree.JCFieldAccess buildJCFieldAccess(String path) {
        final String com = path;
        final String[] split = com.split("\\.");
        List<Name> nameList = new ArrayList<>();
        for (String key : split) {
            nameList.add(names.fromString(key));
        }
        //遍历的对象路径必须大于1
        final int size = nameList.size();
        if (size >= 2) {
            List<JCTree.JCFieldAccess> jcIdentList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    jcIdentList.add(make.Select(make.Ident(nameList.get(i)), nameList.get(i + 1)));
                } else if (i >= 2) {
                    jcIdentList.add(make.Select(jcIdentList.get(i - 2), nameList.get(i)));
                }
            }
            return jcIdentList.get(jcIdentList.size() - 1);
        }
        return null;
    }

    /**
     * 代码树结构修改
     */
    private class Inliner extends TreeTranslator {

        /**
         * 变量的处理
         *
         * @param var1
         */
        @Override
        public void visitVarDef(JCTree.JCVariableDecl var1) {
            super.visitVarDef(var1);

            if (var1.mods.getAnnotations() == null || var1.mods.getAnnotations().size() == 0) {
                return;
            }


            String group = null;
            for (JCTree.JCAnnotation jcAnnotation : var1.mods.getAnnotations()) {
                if (!jcAnnotation.getAnnotationType().type.toString().equals(GSON_AUTOWIRED.getName())) {
                    return;
                } else {
                    for (Pair<Symbol.MethodSymbol, Attribute> pair : jcAnnotation.attribute.values) {
                        group = (String) pair.snd.getValue();
                    }
                }
            }
            //com.linkkou.gson.GsonBuild.getGson
            /*JCTree.JCFieldAccess getGson = make.Select(
                    make.Select(
                            make.Select(
                                    make.Select(
                                            make.Ident(names.fromString("com")), names.fromString("linkkou"))
                                    , names.fromString("gson")
                            ), names.fromString("GsonBuild")
                    ), names.fromString("getGson")
            );*/

            //region 读取xml根据配置进行自定义生成
            JCTree.JCFieldAccess getGson = null;
            JCTree.JCFieldAccess getDefaultGson = buildJCFieldAccess("com.linkkou.gson.GsonBuild.getGson");
            if (null == group && null == gsonXmlConfig.getDefaultCom()) {
                getGson = getDefaultGson;
            } else {
                if (null != group) {
                    final String s = gsonXmlConfig.getGroups().get(group);
                    if (null != s) {
                        getGson = buildJCFieldAccess(s);
                    } else {
                        if (gsonXmlConfig.getDefaultGroup()) {
                            getGson = getDefaultGson;
                        }
                    }
                } else {
                    getGson = buildJCFieldAccess(gsonXmlConfig.getDefaultCom());
                }
            }
            //endregion

            if (null != getGson) {
                /*final List<JCTree.JCExpression> JCLiteralList = new ArrayList<>();
                GsonAutoWiredAttrList.stream().forEach((x) -> {
                    JCLiteralList.add(make.Literal(x));
                });*/
                //进行构造 com.linkkou.gson.GsonBuild.getGson()
                JCTree.JCMethodInvocation getGsonArgs = make.Apply(
                        com.sun.tools.javac.util.List.nil(),
                        getGson,
                        com.sun.tools.javac.util.List.nil()
                );
                JCTree.JCVariableDecl jcv = make.VarDef(
                        var1.getModifiers(),
                        var1.name,
                        var1.vartype,
                        //构建 -> (TestInterface) ***
                        make.TypeCast(var1.vartype, getGsonArgs)
                );
                this.result = jcv;
            }


        }
    }
}