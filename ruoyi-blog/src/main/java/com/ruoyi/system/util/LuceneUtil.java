package com.ruoyi.system.util;

import com.ruoyi.system.entity.vo.BlogLuceneVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @Author ilvyu.cn
 * @Date 2021/5/13 17:47
 * @Version 520.1314
 */
public class LuceneUtil{
    private static Directory directory;
    private static Analyzer analyzer;
    private static IndexWriter indexWriter;
    private static ScoreDoc afterDoc = null;
    private static final String LUCENE_DIR= "D:/ruoyi/luceneDir";

    static {
        Path path = Paths.get(LUCENE_DIR);
        File file = path.toFile();
        if(!file.exists()) {
            file.mkdirs();
        }
        try {
            directory = FSDirectory.open(path);
            analyzer = new SmartChineseAnalyzer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Directory getDirectory() {
        return directory;
    }

    public static Analyzer getAnalyzer() {
        return analyzer;
    }

    public static IndexWriter indexWriter(){
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        try {
            indexWriter = new IndexWriter(directory, indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexWriter;
    }


    public static Object Document2JavaBean(Document document, Class aClass) {
        try {

            Object obj = aClass.newInstance();
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = document.get(name);
                BeanUtils.setProperty(obj,name,value);
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Highlighter highlight(Query query){
        Formatter formatter = new SimpleHTMLFormatter("<font color='red'>","</font>");
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        Highlighter highlighter = new Highlighter(formatter,scorer);
        highlighter.setTextFragmenter(fragmenter);
        return highlighter;
    }
}
